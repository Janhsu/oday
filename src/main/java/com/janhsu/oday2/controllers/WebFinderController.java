package com.janhsu.oday2.controllers;

import com.janhsu.oday2.utils.Loading;
import com.janhsu.oday2.utils.WebFinderRequest;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebFinderController {
    double completed = 0;//任务完成数
    double taskNum = 0;//任务总数

    String res = "";
    public void start(List<String> urlList, int threads, Stage mainStage, TextArea textArea){
        taskNum = urlList.size();
        Loading loading = new Loading(mainStage,"识别中");
        loading.show();//加载动画开启
        new Thread(() -> {
            try {
                /**
                 * TODO 处理耗时操作。
                 * 注意：如果期间涉及到更新UI的操作，需要Platform.runLater(() -> {// TODO })处理。
                 */
                ExecutorService executor = Executors.newFixedThreadPool(threads);
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
            } catch (Exception e) {
                e.printStackTrace();// 3
            } finally {
                loading.closeStage();// 4
                textArea.setText(res);
            }
        }).start();
    }

    public void sendRequest(String url) {
        completed++;
        String resCode;
        resCode = new WebFinderRequest().sendGet(url);
        if (resCode.equals("400")){
            res+="https://"+url+"\n";
        }
        else if (resCode.equals("200")||resCode.equals("302")||resCode.equals("301")||resCode.equals("404")||resCode.equals("403")||resCode.equals("405")){
            res+="http://"+url+"\n";
        }
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
}
