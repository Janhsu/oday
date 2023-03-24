package com.janhsu.oday2.controllers;

import com.janhsu.oday2.dao.UploadPythonExpDao;
import com.janhsu.oday2.entity.PythonExp;
import com.janhsu.oday2.service.GetPythonExpTableService;
import com.janhsu.oday2.service.UploadPythonExpService;
import com.janhsu.oday2.utils.AddVulnUtils;
import com.janhsu.oday2.utils.Base64Utils;
import com.janhsu.oday2.utils.ResultMsg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class UploadPythonExpController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField filePathText;

    @FXML
    private ComboBox<String> methodCombox;

    @FXML
    private TextField expNameText;

    @FXML
    private TextArea expUsageText;

    @FXML
    private TextField expAuthorText;

    String b64string="";

    String editUuid = null;
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException, IOException {
        if (editUuid!=null){
            PythonExp pythonExp = new UploadPythonExpService().getEditInfo(editUuid);
            expNameText.setText(pythonExp.getExpName());
            expUsageText.setText(pythonExp.getExpUsage());
            expAuthorText.setText(pythonExp.getExpAuthor());
            methodCombox.setValue(pythonExp.getExpMethod());
            b64string = pythonExp.getExpB64String();//不上传文件时填入原来base64字符串
        }

    }

    @FXML
    void importFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PYTHON files (*.py)", "*.py");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        filePathText.setText(String.valueOf(file));
        b64string = new Base64Utils().encryptToBase64(String.valueOf(file));
    }

    @FXML
    void upload(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);

        if(this.methodCombox.getSelectionModel().selectedItemProperty().getValue()==null||b64string==null){
            alert.setContentText("请补全信息!");
            alert.showAndWait();
            return;
        }
        PythonExp pythonExp = new PythonExp();
        pythonExp.setExpName(this.expNameText.getText());
        pythonExp.setExpMethod(this.methodCombox.getSelectionModel().selectedItemProperty().getValue().toString());
        pythonExp.setExpUsage(this.expUsageText.getText());
        pythonExp.setExpAuthor(this.expAuthorText.getText());
        pythonExp.setExpB64String(b64string);
        pythonExp.setExpTime(new AddVulnUtils().getNowTime());

        ResultMsg resultMsg;
        if (editUuid == null){
            pythonExp.setUuid(new AddVulnUtils().getUUID());
            resultMsg = new UploadPythonExpService().insert(pythonExp);
        }
        else {
            resultMsg = new UploadPythonExpService().update(pythonExp,editUuid);
        }

        if (resultMsg.getCode() == 200||resultMsg.getCode() == 202) {
            alert.setContentText(resultMsg.getMsg());
            alert.showAndWait();
            Stage stage = (Stage) expAuthorText.getScene().getWindow();//获取当前stage并关闭
            stage.close();

        } else {
            alert.setContentText(resultMsg.getMsg());
            alert.showAndWait();
        }
    }


    public void setEditUuid(String editUuid) {
        this.editUuid = editUuid;
    }
}
