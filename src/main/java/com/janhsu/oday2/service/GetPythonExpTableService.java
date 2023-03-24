package com.janhsu.oday2.service;

import com.janhsu.oday2.dao.GetPythonExpTableDao;
import com.janhsu.oday2.entity.PythonExp;
import com.janhsu.oday2.entity.ShowPythonExpTable;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class GetPythonExpTableService {
    GetPythonExpTableDao getPythonExpTableDao = new GetPythonExpTableDao();
    public ObservableList<ShowPythonExpTable> getShowList() throws SQLException, ClassNotFoundException {
        return getPythonExpTableDao.getList();
    }

    public ObservableList<ShowPythonExpTable> searchByExpName(String expName) throws SQLException, ClassNotFoundException{
        if (expName != null && expName.length() != 0){
            return getPythonExpTableDao.searchByExpName(expName);
        }
        else {
            ObservableList list = getPythonExpTableDao.getList();
            return list;
        }
    }
}
