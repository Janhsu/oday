package com.janhsu.oday2.entity;


import lombok.Data;

@Data
public class PythonExp {
    private int id;
    private String uuid;
    private String expName;
    private String expMethod;
    private String expPath;
    private String expUsage;
    private String expTime;
    private String expAuthor;
    private String expB64String;
}
