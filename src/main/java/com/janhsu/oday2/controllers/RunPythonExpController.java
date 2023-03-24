package com.janhsu.oday2.controllers;

import com.janhsu.oday2.entity.ShowPythonExpTable;
import com.janhsu.oday2.utils.Loading;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class RunPythonExpController {


    private ShowPythonExpTable showPythonExpTable;

    @FXML
    private TextField paramTextField;
    @FXML
    private TextArea resultTextArea;
    @FXML
    private AnchorPane runPyExpRoot;

    Properties pro = new Properties();//创建一个properties对象

    private String pyCmd;

    @FXML
    private void initialize() throws IOException {
        InputStream prois = Files.newInputStream(Paths.get("config/settings.properties"));
        //将文件取出 传入一个 输出流
        pro.load(prois);
        if (showPythonExpTable.getExpMethod().equals("Python2")){
            pyCmd = pro.getProperty("py2Cmd");
        }
        else if(showPythonExpTable.getExpMethod().equals("Python3")){
            pyCmd = pro.getProperty("py3Cmd");
        }
        prois.close();

        resultTextArea.setText("脚本位置:"+showPythonExpTable.getExpPath()+"\n"+"使用方法:"+showPythonExpTable.getExpUsage()+"\n");

    }

    @FXML
    void runPythonExp(ActionEvent event) throws IOException {
        Stage mainStage = (Stage) runPyExpRoot.getScene().getWindow();//获取主窗口
        Loading loading = new Loading(mainStage,"运行中");
        loading.show();//加载动画开启
        new Thread(() -> {
            String output = null;
            try {
                output = runPythonExp(pyCmd, showPythonExpTable.getExpPath(), paramTextField.getText());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                this.resultTextArea.setText(output);
                loading.closeStage();
            }
        }).start();
    }

    public static String runPythonExp(String pythonCmd, String pythonScript, String params){
        StringBuilder output = new StringBuilder();
        String command = pythonCmd+" "+pythonScript+" "+params;
        try {
            Process pro = Runtime.getRuntime().exec(command);
            InputStream is1 = pro.getInputStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is1,"GBK"));
            String line = null;
            while ((line = buf.readLine()) != null) {
                output.append(line+"\n");
            }
        } catch (Exception e) {
            output.append("运行时出现错误");
        }
        return output.toString();
    }


    public void setShowPythonExpTable(ShowPythonExpTable showPythonExpTable) {
        this.showPythonExpTable = showPythonExpTable;
    }
}
