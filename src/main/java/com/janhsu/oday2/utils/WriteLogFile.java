package com.janhsu.oday2.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteLogFile {
    public void write(String text){
        try {
            File file = new File("log/result_log.txt");
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
            out.write(text);
            out.close();
        } catch (IOException e) {
        }
    }
}
