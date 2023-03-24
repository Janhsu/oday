package com.janhsu.oday2.entity;

import lombok.Data;

@Data
public class VulnScanInfo {
    private int id;
    private String vulnName;
    private String pocPath;
    private String pocMethod;
    private String pocHeaders;
    private String pocCt;
    private String pocParam;
    private String resMethod;
    private String resCode;
    private String resWord;
    private String resAndor;
    private int shellCheck;
    private String shellPath;
    private String shellResWord;
}
