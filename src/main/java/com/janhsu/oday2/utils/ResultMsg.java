package com.janhsu.oday2.utils;

public enum ResultMsg {
    VULN_ALREADY_EXIST(500,"漏洞名已存在!"),
    ADD_SUCCESS(200,"添加成功!"),
    UPDATE_SUCCESS(202,"更新成功！"),
    ADD_ERROR(500,"添加信息异常!"),
    INFO_NOT_NULL(403,"信息不能为空!"),
    DELETE_SUCCESS(203,"删除成功!"),
    DELETE_FAILED(204,"删除失败!");




    public Integer getCode()
    {
        return code;
    }
    public String getMsg()
    {
        return msg;
    }

    private Integer code;
    private String msg;



    ResultMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
