package com.janhsu.oday2.controllers;

import com.janhsu.oday2.entity.Vuln;
import com.janhsu.oday2.service.AddVulnService;
import com.janhsu.oday2.service.GetVulnInfoService;
import com.janhsu.oday2.utils.ResultMsg;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddVulnController {
    @FXML
    private TextField cmsName;
    @FXML
    private TextField vulnName;
    @FXML
    private TextArea vulnIntro;
    @FXML
    private ComboBox<String> vulnType;
    @FXML
    private TextField pocPath;
    @FXML
    private ComboBox<String> pocMethod;
    @FXML
    private TextArea pocParam;
    @FXML
    private TextField pocHeaders;
    @FXML
    private TextField pocCt;
    @FXML
    private TextField resCode;
    @FXML
    private TextField resWord;
    @FXML
    private ComboBox<String> resMethod;
    @FXML
    private ComboBox<String> andor;

    @FXML
    private Button submitBtn;

    @FXML
    private Label andLable;
    @FXML
    private Label ctLable;

    String editUuid = null;
    @FXML
    private CheckBox shellCheck;
    @FXML
    private TextField shellPathText;
    @FXML
    private TextField shellResWordText;

    GetVulnInfoService getVulnInfoService = new GetVulnInfoService();
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {

        resMethod.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
         public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            if (newValue.equals("CODE+BODY")){
                andLable.setVisible(true);
                andor.setVisible(true);
                resWord.setDisable(false);
                resCode.setDisable(false);
            }
            else if (newValue.equals("CODE+Header")){
                andLable.setVisible(true);
                andor.setVisible(true);
                resWord.setDisable(false);
                resCode.setDisable(false);
            }
            else {
                andLable.setVisible(false);
                andor.setVisible(false);
                if (newValue.equals("BODY")){
                    resCode.setDisable(true);
                    resWord.setDisable(false);
                }
                if (newValue.equals("CODE")){
                    resWord.setDisable(true);
                    resCode.setDisable(false);
                }
            }
        }});
        pocMethod.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue.equals("GET")){
                    ctLable.setVisible(false);
                    pocCt.setVisible(false);

                }
                else {
                    ctLable.setVisible(true);
                    pocCt.setVisible(true);
                }
            }});




        if (editUuid != null){//编辑模式，填入编辑信息
            Vuln editInfo = new Vuln();
            editInfo = getVulnInfoService.getEditInfo(editUuid);
            cmsName.setText(editInfo.getCmsName());
            vulnName.setText(editInfo.getVulnName());
            vulnIntro.setText(editInfo.getVulnIntro());
            pocPath.setText(editInfo.getPocPath());
            pocHeaders.setText(editInfo.getPocHeaders());
            pocCt.setText(editInfo.getPocCt());
            pocParam.setText(editInfo.getPocParam());
            resCode.setText(editInfo.getResCode());
            resWord.setText(editInfo.getResWord());
            vulnType.setValue(editInfo.getVulnType());
            pocMethod.setValue(editInfo.getPocMethod());
            resMethod.setValue(editInfo.getResMethod());
            andor.setValue(editInfo.getResAndor());
            shellPathText.setText(editInfo.getShellPath());
            shellResWordText.setText(editInfo.getShellResWord());
            if (editInfo.getShellCheck()==1){
                shellCheck.setSelected(true);
            }

        }

    }
    Vuln vulninfo = new Vuln();
    AddVulnService addVulnService = new AddVulnService();
    @FXML
    void submit(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        vulninfo.setCmsName(cmsName.getText().trim());
        vulninfo.setVulnName(vulnName.getText().trim());
        vulninfo.setVulnIntro(vulnIntro.getText());
        vulninfo.setVulnType((String) vulnType.getSelectionModel().selectedItemProperty().getValue());
        vulninfo.setPocPath(pocPath.getText());
        vulninfo.setPocMethod((String) pocMethod.getSelectionModel().selectedItemProperty().getValue());
        vulninfo.setPocParam(pocParam.getText());
        vulninfo.setPocHeaders(pocHeaders.getText());
        vulninfo.setPocCt(pocCt.getText());
        vulninfo.setResCode(resCode.getText());
        vulninfo.setResWord(resWord.getText());
        vulninfo.setResMethod((String) resMethod.getSelectionModel().selectedItemProperty().getValue());
        vulninfo.setResAndor((String) andor.getSelectionModel().selectedItemProperty().getValue());
        vulninfo.setNoExp(0);
        vulninfo.setShellPath(shellPathText.getText());
        vulninfo.setShellResWord(shellResWordText.getText());
        if (shellCheck.isSelected()){
            vulninfo.setShellCheck(1);
        }
        ResultMsg resultMsg;
        if (editUuid == null){
            resultMsg = addVulnService.addVuln(vulninfo);
        }
        else {
            resultMsg = addVulnService.updateVulnInfo(vulninfo,editUuid);
        }
        if (resultMsg.getCode() == 200||resultMsg.getCode() == 202) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText(resultMsg.getMsg());
            alert.showAndWait();

            Stage stage = (Stage) submitBtn.getScene().getWindow();//获取当前stage并关闭
            stage.close();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText(resultMsg.getMsg());
            alert.showAndWait();
        }

    }


    public String getEditId() {
        return editUuid;
    }

    public void setEditUuid(String editUuid) {
        this.editUuid = editUuid;
    }

}
