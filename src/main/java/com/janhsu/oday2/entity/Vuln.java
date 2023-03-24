package com.janhsu.oday2.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Vuln implements Serializable {
    private int id;
    private String uuid;
    private String cmsName;
    private String vulnName;
    private String vulnType;
    private String vulnIntro;
    private String vulnTime;
    private String pocPath;
    private String pocMethod;
    private String pocHeaders;
    private String pocCt;
    private String pocParam;
    private String resMethod;
    private String resCode;
    private String resWord;
    private String resAndor;
    private int noExp;
    private String expPath;
    private String expMethod;
    private String expHeaders;
    private String expCt;
    private String expGuide;
    private String expParam;
    private String rceParam;
    private int shellCheck;
    private String shellPath;
    private String shellResWord;
}
