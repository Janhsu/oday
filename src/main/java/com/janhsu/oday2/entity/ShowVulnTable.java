package com.janhsu.oday2.entity;

import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

@Data
public class ShowVulnTable {
    private int id;
    private String uuid;

    private String cmsName;
    private SimpleStringProperty cmsNameString;

    private String vulnName;
    private SimpleStringProperty vulnNameString;

    private String vulnType;
    private SimpleStringProperty vulnTypeString;

    private String vulnIntro;
    private SimpleStringProperty vulnIntroString;

    private String vulnTime;
    private SimpleStringProperty vulnTimeString;


    //cms名 set、get方法------------------------------------------------
    public String getCmsName() {
        return cmsName;
    }
    public void setCmsName(String cmsName) {
        this.cmsName = cmsName;
        this.cmsNameString = new SimpleStringProperty(String.valueOf(cmsName));
    }
    public SimpleStringProperty cmsNameStringProperty() {//Perperty()方法的命名，一定要按照 “SimpleStringProperty变量名”+Property() 这样的格式命名，而且该变量名必须要与实体类中定义的SimpleStringProperty属性一致。
        return cmsNameString;
    }

    //漏洞名 set、get方法------------------------------------------------
    public String getVulnName() {
        return vulnName;
    }
    public void setVulnName(String vulnName) {
        this.vulnName = vulnName;
        this.vulnNameString = new SimpleStringProperty(String.valueOf(vulnName));
    }
    public SimpleStringProperty vulnNameStringProperty() {
        return vulnNameString;
    }

    //漏洞类型 set、get方法------------------------------------------------
    public String getVulnType() {
        return vulnType;
    }
    public void setVulnType(String vulnType) {
        this.vulnType = vulnType;
        this.vulnTypeString = new SimpleStringProperty(String.valueOf(vulnType));
    }
    public SimpleStringProperty vulnTypeStringProperty() {
        return vulnTypeString;
    }

    //漏洞信息 set、get方法------------------------------------------------
    public String getVulnIntro() {
        return vulnIntro;
    }
    public void setVulnIntro(String vulnIntro) {
        this.vulnIntro = vulnIntro;
        this.vulnIntroString = new SimpleStringProperty(String.valueOf(vulnIntro));
    }
    public SimpleStringProperty vulnIntroStringProperty() {
        return vulnIntroString;
    }

    //漏洞时间 set、get方法------------------------------------------------
    public String getVulnTime() {
        return vulnTime;
    }
    public void setVulnTime(String vulnTime) {
        this.vulnTime = vulnTime;
        this.vulnTimeString = new SimpleStringProperty(vulnTime);
    }
    public SimpleStringProperty vulnTimeStringProperty() {
        return vulnTimeString;
    }
}
