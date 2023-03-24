package com.janhsu.oday2.dao;

import com.janhsu.oday2.entity.PythonExp;
import com.janhsu.oday2.entity.ShowPythonExpTable;
import com.janhsu.oday2.utils.DbUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetPythonExpTableDao {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    public ObservableList getList() throws SQLException, ClassNotFoundException {
        String sql;
        ObservableList<ShowPythonExpTable> list = FXCollections.observableArrayList();     //该集合必须是符合javafx形式的集合
        con = DbUtil.getSqliteCon();

        //装载sql语句
        sql = "SELECT id,uuid,exp_name,exp_usage,exp_time,exp_author,exp_path,exp_method FROM oday_pyexp ORDER BY exp_time DESC";
        ps = con.prepareStatement(sql);

        //执行sql语句
        rs = ps.executeQuery();

        while (rs.next()) {// 存在数据，封装返回;多条数据，用while循环装入每个pocShowList对象
            ShowPythonExpTable showList = new ShowPythonExpTable();                  //必须new对象，否则传回空指针
            showList.setId(rs.getInt(1));
            showList.setUuid(rs.getString(2));
            showList.setExpName(rs.getString(3));
            showList.setExpUsage(rs.getString(4));
            showList.setExpTime(rs.getString(5));
            showList.setExpAuthor(rs.getString(6));
            showList.setExpPath(rs.getString(7));
            showList.setExpMethod(rs.getString(8));
            list.add(showList);//将每个pocShowList对象装入list集合
        }
        DbUtil.close(rs,ps, con);// 关闭连接
        return list;
    }

    /**
     * 根据POC名搜索POC
     */
    public ObservableList<ShowPythonExpTable> searchByExpName(String expName) throws SQLException, ClassNotFoundException{
        String sql;
        ObservableList<ShowPythonExpTable> list = FXCollections.observableArrayList();     //该集合必须是符合javafx形式的集合
        con = DbUtil.getSqliteCon();
        //装载sql语句
        sql = "SELECT id,uuid,exp_name,exp_usage,exp_time,exp_author,exp_path,exp_method FROM oday_pyexp WHERE exp_name like ?";
        ps = con.prepareStatement(sql);
        ps.setString(1,'%'+expName+'%');

        //执行sql语句
        rs = ps.executeQuery();

        while (rs.next()) {// 存在数据，封装返回;多条数据，用while循环装入每个pocShowList对象
            ShowPythonExpTable showList = new ShowPythonExpTable();                  //必须new对象，否则传回空指针
            showList.setId(rs.getInt(1));
            showList.setUuid(rs.getString(2));
            showList.setExpName(rs.getString(3));
            showList.setExpUsage(rs.getString(4));
            showList.setExpTime(rs.getString(5));
            showList.setExpAuthor(rs.getString(6));
            showList.setExpPath(rs.getString(7));
            showList.setExpMethod(rs.getString(8));
            list.add(showList);//将每个pocShowList对象装入list集合
        }
        DbUtil.close(rs,ps, con);// 关闭连接
        return list;
    }
}
