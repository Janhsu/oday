package com.janhsu.oday2.dao;

import com.janhsu.oday2.entity.ShowVulnTable;
import com.janhsu.oday2.utils.DbUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVulnTableDao {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public ObservableList getList() throws SQLException, ClassNotFoundException {
        ShowVulnTable showList = null;
        String sql;
        ObservableList<ShowVulnTable> list = FXCollections.observableArrayList();     //该集合必须是符合javafx形式的集合
        con = DbUtil.getSqliteCon();

        //装载sql语句
        sql = "SELECT id,uuid,cms_name,vuln_name,vuln_type,vuln_time,vuln_intro FROM oday_vuln ORDER BY vuln_time DESC";
        ps = con.prepareStatement(sql);

        //执行sql语句
        rs = ps.executeQuery();

        while (rs.next()) {// 存在数据，封装返回;多条数据，用while循环装入每个pocShowList对象
            showList = new ShowVulnTable();                  //必须new对象，否则传回空指针
            showList.setId(rs.getInt(1));
            showList.setUuid(rs.getString(2));
            showList.setCmsName(rs.getString(3));
            showList.setVulnName(rs.getString(4));
            showList.setVulnType(rs.getString(5));
            showList.setVulnTime(rs.getString(6));
            showList.setVulnIntro(rs.getString(7));
            list.add(showList);//将每个pocShowList对象装入list集合
        }
        DbUtil.close(rs,ps, con);// 关闭连接
        return list;
    }
}
