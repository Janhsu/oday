package com.janhsu.oday2.service;

import com.janhsu.oday2.dao.UploadPythonExpDao;
import com.janhsu.oday2.entity.PythonExp;
import com.janhsu.oday2.utils.Base64Utils;
import com.janhsu.oday2.utils.ResultMsg;

import java.io.IOException;
import java.sql.SQLException;

public class UploadPythonExpService {
    String pathPrefix="extension/poc/";
    UploadPythonExpDao uploadPythonExpDao = new UploadPythonExpDao();

    public ResultMsg insert(PythonExp pythonExp) throws SQLException, ClassNotFoundException, IOException {
        String  storagePath = "";
        storagePath += pathPrefix+pythonExp.getUuid()+".py";
        new Base64Utils().decryptByBase64(pythonExp.getExpB64String(),storagePath);
        pythonExp.setExpPath(storagePath);
        return uploadPythonExpDao.insert(pythonExp);
        }

        public ResultMsg update(PythonExp pythonExp,String uuid) throws SQLException, IOException, ClassNotFoundException {
            String  storagePath = "";
            storagePath += pathPrefix+uuid+".py";
            new Base64Utils().decryptByBase64(pythonExp.getExpB64String(),storagePath);
            pythonExp.setExpPath(storagePath);
            return uploadPythonExpDao.update(pythonExp,uuid);
        }

        public PythonExp getEditInfo(String uuid) throws SQLException, ClassNotFoundException, IOException {
            return uploadPythonExpDao.getEditInfo(uuid);
        }
}
