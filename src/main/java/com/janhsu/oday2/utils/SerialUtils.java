package com.janhsu.oday2.utils;

import com.janhsu.oday2.entity.Vuln;

import java.io.*;

public class SerialUtils {
    public void vulnToFile(File file,Vuln vuln){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(file));
            oos.writeObject(vuln);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Vuln fileToVuln(File file) {
        Vuln vuln = new Vuln();
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(file));
            vuln = (Vuln) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return vuln;
    }
}
