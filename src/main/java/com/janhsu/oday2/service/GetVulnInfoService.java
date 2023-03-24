package com.janhsu.oday2.service;

import com.janhsu.oday2.dao.GetVulnInfoDao;
import com.janhsu.oday2.entity.Vuln;
import java.sql.SQLException;
import java.util.List;

public class GetVulnInfoService {
    GetVulnInfoDao getVulnInfoDao = new GetVulnInfoDao();
    public Vuln getEditInfo(String uuid) throws SQLException, ClassNotFoundException {
        return getVulnInfoDao.getEditInfo(uuid);
    }

    public List getVulnScanInfo() throws SQLException, ClassNotFoundException {
        return getVulnInfoDao.getVulnScanInfo();
    }

    public List getVulnExploitInfo() throws SQLException, ClassNotFoundException {
        return getVulnInfoDao.getVulnExploitInfo();
    }
    public List getCmsInfoList() throws SQLException, ClassNotFoundException {
        return getVulnInfoDao.getCmsInfoList();
    }

    public List getVulnScanInfoByCmsName(String cmsName) throws SQLException {
        return getVulnInfoDao.getVulnScanInfoByCmsName(cmsName);
    }
}
