package com.janhsu.oday2.service;

import com.janhsu.oday2.dao.ExportVulnDao;
import com.janhsu.oday2.entity.Vuln;
import com.janhsu.oday2.utils.ResultMsg;

import java.io.IOException;
import java.sql.SQLException;

public class ExportVulnService {
    ExportVulnDao exportVuLnDao = new ExportVulnDao();

    public Vuln getExportVuln(String uuid) throws SQLException, IOException, ClassNotFoundException {
         return exportVuLnDao.getExportVuln(uuid);
    }
}
