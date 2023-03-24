package com.janhsu.oday2.utils;

import com.janhsu.oday2.entity.ConnResult;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VulnScanUtils {

    /**
     * headers多参数处理
     */
    public HashMap<String,String> handleParams(String text){
        HashMap<String,String> headers = new HashMap<String,String>();
        String[] strs=text.split("~");
        for(int i=0,len=strs.length;i<len;i++){
            String[] strss = strs[i].split(":");
            headers.put(strss[0],strss[1]);
        }

        return headers;

    }
    /**
     * 按行读取文本转换为LIST
     */
    public List<String> textToList(String text) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line = br.readLine();
        while (line != null) {
            list.add(line);
            // read next line
            line = br.readLine();
        }
        return list;
    }
    /**
     * 按行读取文件转换为LIST
     */
    public ArrayList<String> readFile(File file){
        ArrayList<String> urlList = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
//                System.out.println(line);
                urlList.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlList;
    }

    /**
     * 检查返回包是否存在漏洞特征
     */
    public Boolean checkResBody(ConnResult connResult, String resMethod, String resAndor, String resCode, String resWord){
        boolean flag = false;
        switch (resMethod){
            case "CODE":
                flag = String.valueOf(connResult.getStatusCode()).equals(resCode);
                return flag;
            case "BODY":
                flag = connResult.getResBody().contains(resWord);
                return flag;
            case "CODE+BODY":
                if (resAndor.equals("AND")){
                    flag =  String.valueOf(connResult.getStatusCode()).equals(resCode) && connResult.getResBody().contains(resWord);
                    return flag;
                }
                else if(resAndor.equals("OR")){
                    flag = String.valueOf(connResult.getStatusCode()).equals(resCode) || connResult.getResBody().contains(resWord);
                    return flag;
                }
                break;
            case "Header":
                flag = connResult.getResHeaders().contains(resWord);
                return flag;
            case "CODE+Header":
                if (resAndor.equals("AND")){
                    flag =  String.valueOf(connResult.getStatusCode()).equals(resCode) && connResult.getResHeaders().contains(resWord);
                    return flag;
                }
                else if(resAndor.equals("OR")){
                    flag = String.valueOf(connResult.getStatusCode()).equals(resCode) || connResult.getResHeaders().contains(resWord);
                    return flag;
                }
                break;
        }
        return flag;
    }
}
