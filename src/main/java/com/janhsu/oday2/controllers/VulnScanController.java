package com.janhsu.oday2.controllers;

import com.janhsu.oday2.entity.ConnResult;
import com.janhsu.oday2.entity.VulnScanInfo;
import com.janhsu.oday2.utils.*;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VulnScanController {
    VulnScanInfo vulnScanInfo = new VulnScanInfo();

    VulnScanUtils vulnScanUtils = new VulnScanUtils();
    HashMap<String,String> headers = new HashMap<>();//headers
    double completed = 0;//任务完成数
    double taskNum = 0;//任务总数
    String resultText = ">检测结果<\n";

    Integer vulnNum = 0;

    WriteLogFile writeLogFile = new WriteLogFile();

    /**
     * 漏洞扫描入口方法
     */
    public void vulnScan(VulnScanInfo vulnScanInfo, List<String> urlList, Integer threadNum, Stage mainStage, TextArea textArea) {
        String resLogText = String.format("---------->%s,来自[单项扫描]模块的结果记录:\n", new AddVulnUtils().getNowTime());
        writeLogFile.write(resLogText);
        this.vulnScanInfo = vulnScanInfo;//赋值给类里的对象
        taskNum = urlList.size();
        if (this.vulnScanInfo.getPocHeaders()!=null&&this.vulnScanInfo.getPocHeaders().length()!=0){//headers不为空时进行处理
            headers = vulnScanUtils.handleParams(this.vulnScanInfo.getPocHeaders());
        }
        Loading loading = new Loading(mainStage,"检测中");
        loading.show();//加载动画开启
        new Thread(() -> {
            try {
                /**
                 * TODO 处理耗时操作。
                 * 注意：如果期间涉及到更新UI的操作，需要Platform.runLater(() -> {// TODO })处理。
                 */
                    long start = System.currentTimeMillis();
                    ExecutorService executor = Executors.newFixedThreadPool(threadNum);
                    final CountDownLatch countDownLatch = new CountDownLatch(urlList.size());
                    for (String url : urlList) {
                        executor.execute(new Task(url, loading, countDownLatch));//传入loading对象，为了task任务能调用改变进度信息
                    }
                    try {
                        countDownLatch.await();
                    } catch (Exception e) {

                    } finally {
                        executor.shutdown();
                    }
                    long end = System.currentTimeMillis();
                    resultText+= String.format("[*]共发现:%d个URL存在[%s]漏洞\n",vulnNum,vulnScanInfo.getVulnName());
                    resultText+= String.format("[*]完成任务:%s 个\n",completed);
                    resultText+= String.format("[*]共耗时:%d ms\n",end-start);
                    writeLogFile.write("---------->end\n\n");
            } catch (Exception e) {
                e.printStackTrace();// 3
            } finally {
                loading.closeStage();// 4
                textArea.setStyle("-fx-text-fill:grey;");
                textArea.setText(resultText);
            }
        }).start();
    }

    /**
     * 多线程TASK类
     */
    public  class Task implements  Runnable {
        private String url;
        private CountDownLatch countDownLatch;
        private Loading loading;
        public Task(String url, Loading loading,CountDownLatch countDownLatch) {//只能先定义私有变量再赋值
            this.url = url;
            this.countDownLatch = countDownLatch;
            this.loading = loading;
        }
        @Override
        public void run() {
            try {
                sendRequest(url);
                loading.showMessage(String.format("%.1f",((completed/taskNum)*100))+"%");//更新进度信息
            } catch (Exception e) {

            } finally {
                countDownLatch.countDown();
            }
        }

    }

    /**
     * 发送请求
     */
    public void sendRequest(String url) {
        completed++;//每调用一次该方法，任务完成数+1
        ConnResult connResult = new ConnResult();
        switch (this.vulnScanInfo.getPocMethod()){
            case "POST":
                connResult = new ProcessHttp().sendPost(url,this.vulnScanInfo.getPocPath(),
                        this.vulnScanInfo.getPocCt(),
                        headers,
                        this.vulnScanInfo.getPocParam());
                break;
            case "GET":
                connResult  = new ProcessHttp().sendGet(url,this.vulnScanInfo.getPocPath(),
                        this.vulnScanInfo.getPocParam(),
                        headers);
                break;
            case "PUT":
                connResult = new ProcessHttp().sendPut(url,this.vulnScanInfo.getPocPath(),
                        this.vulnScanInfo.getPocCt(),
                        headers,
                        this.vulnScanInfo.getPocParam());
                break;
        }
        boolean flag = new VulnScanUtils().checkResBody(connResult,
                this.vulnScanInfo.getResMethod(),
                this.vulnScanInfo.getResAndor(),
                this.vulnScanInfo.getResCode(),
                this.vulnScanInfo.getResWord());
        if (flag){//已经满足第一次请求的条件了
            if (vulnScanInfo.getShellCheck()==1){//进行shell验证
                String resBody = new ShellCheckRequest().getShellCheck(url,vulnScanInfo.getShellPath());
                if (resBody.contains(vulnScanInfo.getShellResWord())){
                    resultText += "[+]"+url + "可能存在漏洞\n";
                    writeLogFile.write("[+]"+url + "可能存在["+vulnScanInfo.getVulnName()+"]漏洞\n");
                    vulnNum++;//漏洞数量统计
                }
            }
            else {
                resultText += "[+]"+url + "可能存在漏洞\n";
                writeLogFile.write("[+]"+url + "可能存在["+vulnScanInfo.getVulnName()+"]漏洞\n");
                vulnNum++;//漏洞数量统计
            }
        }
    }




}
