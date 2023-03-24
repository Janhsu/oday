package com.janhsu.oday2.controllers;

import com.janhsu.oday2.utils.ProxyConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SetProxyController {
    @FXML
    private RadioButton openProxy;

    @FXML
    private ToggleGroup proxyGroup;

    @FXML
    private RadioButton closeProxy;

    @FXML
    private TextField proxyUrl;

    @FXML
    private TextField proxyPort;


    @FXML
    private void initialize(){
        openProxy.setUserData(Boolean.TRUE);
        closeProxy.setUserData(Boolean.FALSE);
        this.proxyUrl.setText(ProxyConfig.IP_ADDR);
        this.proxyPort.setText(String.valueOf(ProxyConfig.PORT_ADDR));
        if (ProxyConfig.IS_PROXY.equals("true")){
            openProxy.setSelected(true);
        }
        else {
            closeProxy.setSelected(true);
        }

    }

    @FXML
    void setProxyConfig(ActionEvent event) {
        ProxyConfig.IP_ADDR = this.proxyUrl.getText();
        ProxyConfig.PORT_ADDR = Integer.parseInt(this.proxyPort.getText());
        ProxyConfig.IS_PROXY = proxyGroup.getSelectedToggle().getUserData().toString();
        Stage stage = (Stage) openProxy.getScene().getWindow();//获取当前stage并关闭
        stage.close();
    }
}
