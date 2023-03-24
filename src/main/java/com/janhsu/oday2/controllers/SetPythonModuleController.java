package com.janhsu.oday2.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class SetPythonModuleController {

    @FXML
    private TextField py2CmdText;

    @FXML
    private TextField py3CmdText;


    Properties pro = new Properties();//创建一个properties对象
    @FXML
    private void initialize() throws IOException {
        //创建一个输出流
        InputStream prois = Files.newInputStream(Paths.get("config/settings.properties"));
        //将文件取出 传入一个 输出流
        pro.load(prois);
        this.py2CmdText.setText((String) pro.get("py2Cmd"));
        this.py3CmdText.setText((String) pro.get("py3Cmd"));
        prois.close();
    }
    @FXML
    void saveSet(ActionEvent event) throws IOException {
//创建一个输出流 里面路径填写文件的路径
        OutputStream proos = Files.newOutputStream(Paths.get("config/settings.properties"));
        pro.setProperty("py2Cmd",this.py2CmdText.getText());
        pro.setProperty("py3Cmd",this.py3CmdText.getText());
//将数据储存到文件中，第一个参数是 输出流，第二个参数是注释
        pro.store(proos,"Settings");
        proos.close();

        Stage stage = (Stage) py2CmdText.getScene().getWindow();//获取当前stage并关闭
        stage.close();
    }

}
