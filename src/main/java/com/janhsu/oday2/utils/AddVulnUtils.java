package com.janhsu.oday2.utils;

import com.janhsu.oday2.entity.Vuln;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AddVulnUtils {

    /**
     * 获取当前时间
     */
    public  String getNowTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    /**
     * 检查表单内容是否为空
     */
    public Boolean ifNull(Vuln entity){
        if (entity.getCmsName()==null||entity.getCmsName().length()==0){
            return false;
        }
        if (entity.getVulnName()==null||entity.getVulnName().length()==0){
            return false;
        }
        if(entity.getVulnIntro()==null||entity.getVulnIntro().length()==0){
            return false;
        }
        if (entity.getVulnType()==null||entity.getVulnType().length()==0){
            return false;
        }
        if (entity.getPocPath()==null||entity.getPocPath().length()==0){
            return false;
        }
        if (entity.getPocMethod()==null||entity.getPocMethod().length()==0){
            return false;
        }
        if (entity.getNoExp()==0){
            if (entity.getExpPath()==null||entity.getExpPath().length()==0){
                return false;
            }
            if (entity.getExpMethod()==null||entity.getExpMethod().length()==0){
                return false;
            }
            if (entity.getExpParam()==null||entity.getExpParam().length()==0){
                return false;
            }
        }
        if (entity.getShellCheck()!=0){
            if (entity.getShellPath()==null||entity.getShellPath().length()==0){
                return false;
            }
            if (entity.getShellResWord()==null||entity.getShellResWord().length()==0){
                return false;
            }
        }
        return true;
    }

    /**
     * 获得一个随机UUID
     * @return String UUID
     */
    public  String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
