package com.janhsu.oday2.service;

import com.janhsu.oday2.dao.ImportVulnDao;
import com.janhsu.oday2.entity.Vuln;
import com.janhsu.oday2.utils.ResultMsg;

import java.sql.SQLException;

public class ImportVulnService {
    ImportVulnDao importVulnDao = new ImportVulnDao();
    public ResultMsg insert(Vuln vulnInfo) throws SQLException {
        return importVulnDao.insertTable(vulnInfo);
    }

    public Boolean ifVulnIdExist(String uuid) throws SQLException, ClassNotFoundException {
        return importVulnDao.ifVulnIdExist(uuid);
    }
}
