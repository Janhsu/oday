package com.janhsu.oday2.service;

import com.janhsu.oday2.dao.GetVulnTableDao;
import com.janhsu.oday2.dao.SearchVulnDao;
import com.janhsu.oday2.entity.ShowVulnTable;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class SearchVulnService {
    GetVulnTableDao getVulnTableDao = new GetVulnTableDao();
    SearchVulnDao searchVulnDao =  new SearchVulnDao();

    public ObservableList<ShowVulnTable> searchByVulnName(String vulnName) throws SQLException, ClassNotFoundException{
        if (vulnName != null && vulnName.length() != 0){
            return searchVulnDao.searchByVulnName(vulnName);
        }
        else {
            ObservableList list = getVulnTableDao.getList();
            return list;

        }
    }
}
