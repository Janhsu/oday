package com.janhsu.oday2.service;

import com.janhsu.oday2.dao.AddVulnDao;
import com.janhsu.oday2.entity.Vuln;
import com.janhsu.oday2.utils.*;

import java.io.IOException;
import java.sql.SQLException;

public class AddVulnService {


    AddVulnUtils addVulnUtils = new AddVulnUtils();
    AddVulnDao addVulnDao = new AddVulnDao();
    public ResultMsg addVuln(Vuln submitInfo) throws SQLException, ClassNotFoundException, IOException {
        if (!addVulnUtils.ifNull(submitInfo)) {
            return ResultMsg.INFO_NOT_NULL;
        }
        submitInfo.setUuid(addVulnUtils.getUUID());
        submitInfo.setVulnTime(new AddVulnUtils().getNowTime());

        return addVulnDao.insertVulnDetail(submitInfo);

    }
    public ResultMsg updateVulnInfo(Vuln editInfo,String uuid) throws SQLException, ClassNotFoundException, IOException {
        if (!addVulnUtils.ifNull(editInfo)) {
            return ResultMsg.INFO_NOT_NULL;
        }
            editInfo.setVulnTime(new AddVulnUtils().getNowTime());
            return addVulnDao.updateVulnInfo(editInfo,uuid);
    }

    public ResultMsg deleteByUuid(String uuid) throws SQLException {
        return addVulnDao.deleteByUuid(uuid);
    }
}