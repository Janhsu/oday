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

public class AllVulnScanController {

    VulnScanUtils vulnScanUtils = new VulnScanUtils();
    WriteLogFile writeLogFile = new WriteLogFile();

    double completed = 0;//任务完成数
    double taskNum = 0;//任务总数
    String resultText = ">检测结果<\n";
    Integer vulnNum = 0;

    public void allVulnScan(List<VulnScanInfo> vulnScanInfoList, List<String> urlList, Integer threadNum, Stage mainStage, TextArea textArea,String modeText){
        taskNum = vulnScanInfoList.size()*urlList.size();
        String resLogText = String.format("---------->%s,来自[%s]模块的结果记录:\n", new AddVulnUtils().getNowTime(),modeText);
        writeLogFile.write(resLogText);

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
                final CountDownLatch countDownLatch = new CountDownLatch(urlList.size()*vulnScanInfoList.size());
                for (String url : urlList) {
                    for (VulnScanInfo vulnScanInfo:vulnScanInfoList){
                        executor.execute(new Task(url,vulnScanInfo, loading, countDownLatch));//传入loading对象，为了task任务能调用改变进度信息
                    }
                }
                try {
                    countDownLatch.await();
                } catch (Exception e) {

                } finally {
                    executor.shutdown();
                }
                long end = System.currentTimeMillis();
                resultText+= String.format("[*]共发现:%d个URL存在漏洞\n",vulnNum);
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

        private VulnScanInfo vulnScanInfo;
        private CountDownLatch countDownLatch;
        private Loading loading;
        public Task(String url, VulnScanInfo vulnScanInfo,Loading loading,CountDownLatch countDownLatch) {//只能先定义私有变量再赋值
            this.url = url;
            this.vulnScanInfo = vulnScanInfo;
            this.countDownLatch = countDownLatch;
            this.loading = loading;
        }
        @Override
        public void run() {
            try {
                sendRequest(url,vulnScanInfo);
                loading.showMessage(String.format("%.1f",((completed/taskNum)*100))+"%");//更新进度信息
            } catch (Exception e) {

            } finally {
                countDownLatch.countDown();
            }
        }

    }

    public void sendRequest(String url,VulnScanInfo vulnScanInfo) {
        completed++;//每调用一次该方法，任务完成数+1
        HashMap<String,String> headers = new HashMap<>();//headers
        if (vulnScanInfo.getPocHeaders()!=null&&vulnScanInfo.getPocHeaders().length()!=0){//headers不为空时进行处理
            headers = vulnScanUtils.handleParams(vulnScanInfo.getPocHeaders());
        }
        ConnResult connResult = new ConnResult();
        switch (vulnScanInfo.getPocMethod()){
            case "POST":
                connResult = new ProcessHttp().sendPost(url,vulnScanInfo.getPocPath(),
                        vulnScanInfo.getPocCt(),
                        headers,
                        vulnScanInfo.getPocParam());
                break;
            case "GET":
                connResult  = new ProcessHttp().sendGet(url,vulnScanInfo.getPocPath(),
                        vulnScanInfo.getPocParam(),
                        headers);
                break;
            case "PUT":
                connResult = new ProcessHttp().sendPut(url,vulnScanInfo.getPocPath(),
                        vulnScanInfo.getPocCt(),
                        headers,
                        vulnScanInfo.getPocParam());
                break;
        }
        boolean flag = new VulnScanUtils().checkResBody(connResult,
                vulnScanInfo.getResMethod(),
                vulnScanInfo.getResAndor(),
                vulnScanInfo.getResCode(),
                vulnScanInfo.getResWord());
        if (flag){//已经满足第一次请求的条件了
            if (vulnScanInfo.getShellCheck()==1){//进行shell验证
                String resBody = new ShellCheckRequest().getShellCheck(url,vulnScanInfo.getShellPath());
                if (resBody.contains(vulnScanInfo.getShellResWord())){
                    resultText += "[+]"+url + "可能存在:["+vulnScanInfo.getVulnName()+"]漏洞\n";
                    vulnNum++;//漏洞数量统计
                    writeLogFile.write("[+]"+url + "可能存在:["+vulnScanInfo.getVulnName()+"]漏洞\n");
                }
            }
            else {
                resultText += "[+]"+url + "可能存在:["+vulnScanInfo.getVulnName()+"]漏洞\n";
                vulnNum++;//漏洞数量统计
                writeLogFile.write("[+]"+url + "可能存在:["+vulnScanInfo.getVulnName()+"]漏洞\n");
            }
        }

    }
}
