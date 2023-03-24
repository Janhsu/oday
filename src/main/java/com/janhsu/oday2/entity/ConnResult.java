package com.janhsu.oday2.entity;

import lombok.Data;

@Data
public class ConnResult {
    private String statusCode;
    private String resHeaders;
    private String resBody;
}
