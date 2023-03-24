package com.janhsu.oday2.service;

import com.janhsu.oday2.dao.GetVulnTableDao;
import com.janhsu.oday2.entity.ShowVulnTable;
import javafx.collections.ObservableList;

import java.sql.SQLException;


public class GetVulnTableService {
    GetVulnTableDao getVulnTableDao = new GetVulnTableDao();
    public ObservableList<ShowVulnTable> getShowList() throws SQLException, ClassNotFoundException {
        return getVulnTableDao.getList();
    }
}
