package com.janhsu.oday2.entity;

import javafx.beans.property.SimpleStringProperty;


public class ShowPythonExpTable {
    private int id;
    private String uuid;

    private String expName;
    private SimpleStringProperty expNameString;
    private String expPath;
    private SimpleStringProperty expPathString;
    private String expUsage;
    private SimpleStringProperty expUsageString;
    private String expTime;
    private SimpleStringProperty expTimeString;
    private String expAuthor;
    private SimpleStringProperty expAuthorString;
    private String expMethod;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExpMethod() {
        return expMethod;
    }

    public void setExpMethod(String expMethod) {
        this.expMethod = expMethod;
    }


    public String getExpName() {
        return expName;
    }
    public void setExpName(String expName) {
        this.expName = expName;
        this.expNameString = new SimpleStringProperty(String.valueOf(expName));
    }
    public SimpleStringProperty expNameStringProperty() {//Perperty()方法的命名，一定要按照 “SimpleStringProperty变量名”+Property() 这样的格式命名，而且该变量名必须要与实体类中定义的SimpleStringProperty属性一致。
        return expNameString;
    }



    public String getExpPath() {
        return expPath;
    }
    public void setExpPath(String expPath) {
        this.expPath = expPath;
        this.expPathString = new SimpleStringProperty(String.valueOf(expPath));
    }
    public SimpleStringProperty expPathStringProperty() {//Perperty()方法的命名，一定要按照 “SimpleStringProperty变量名”+Property() 这样的格式命名，而且该变量名必须要与实体类中定义的SimpleStringProperty属性一致。
        return expPathString;
    }


    public String getExpUsage() {
        return expUsage;
    }
    public void setExpUsage(String expUsage) {
        this.expUsage = expUsage;
        this.expUsageString = new SimpleStringProperty(String.valueOf(expUsage));
    }
    public SimpleStringProperty expUsageStringProperty() {//Perperty()方法的命名，一定要按照 “SimpleStringProperty变量名”+Property() 这样的格式命名，而且该变量名必须要与实体类中定义的SimpleStringProperty属性一致。
        return expUsageString;
    }



    public String getExpTime() {
        return expTime;
    }
    public void setExpTime(String expTime) {
        this.expTime = expTime;
        this.expTimeString = new SimpleStringProperty(String.valueOf(expTime));
    }
    public SimpleStringProperty expTimeStringProperty() {//Perperty()方法的命名，一定要按照 “SimpleStringProperty变量名”+Property() 这样的格式命名，而且该变量名必须要与实体类中定义的SimpleStringProperty属性一致。
        return expTimeString;
    }



    public String getExpAuthor() {
        return expAuthor;
    }
    public void setExpAuthor(String expAuthor) {
        this.expAuthor = expAuthor;
        this.expAuthorString = new SimpleStringProperty(String.valueOf(expAuthor));
    }
    public SimpleStringProperty expAuthorStringProperty() {//Perperty()方法的命名，一定要按照 “SimpleStringProperty变量名”+Property() 这样的格式命名，而且该变量名必须要与实体类中定义的SimpleStringProperty属性一致。
        return expAuthorString;
    }
}
