package com.janhsu.oday2.dao;


import com.janhsu.oday2.entity.Vuln;
import com.janhsu.oday2.utils.DbUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportVulnDao {
    private Connection sqliteCon;
    private PreparedStatement ps;
    private ResultSet rs;

    public Vuln getExportVuln(String uuid) throws SQLException, ClassNotFoundException, IOException {
        Vuln vulnInfo = new Vuln();
        String sql;
        sqliteCon = DbUtil.getSqliteCon();
        //装载sql语句
        sql = "SELECT * FROM oday_vuln WHERE uuid = ?";
        ps = sqliteCon.prepareStatement(sql);
        ps.setString(1, uuid);
        //执行sql语句
        rs = ps.executeQuery();
        while (rs.next()) {// 存在数据，封装返回;多条数据，用while循环装入每个pocShowList对象
            vulnInfo.setId(rs.getInt(1));
            vulnInfo.setUuid(rs.getString(2));
            vulnInfo.setCmsName(rs.getString(3));
            vulnInfo.setVulnName(rs.getString(4));
            vulnInfo.setVulnType(rs.getString(5));
            vulnInfo.setVulnIntro(rs.getString(6));
            vulnInfo.setVulnTime(rs.getString(7));
            vulnInfo.setPocPath(rs.getString(8));
            vulnInfo.setPocMethod(rs.getString(9));
            vulnInfo.setPocHeaders(rs.getString(10));
            vulnInfo.setPocCt(rs.getString(11));
            vulnInfo.setPocParam(rs.getString(12));
            vulnInfo.setResMethod(rs.getString(13));
            vulnInfo.setResCode(rs.getString(14));
            vulnInfo.setResWord(rs.getString(15));
            vulnInfo.setResAndor(rs.getString(16));
            vulnInfo.setNoExp(rs.getInt(17));
            vulnInfo.setExpPath(rs.getString(18));
            vulnInfo.setExpMethod(rs.getString(19));
            vulnInfo.setExpHeaders(rs.getString(20));
            vulnInfo.setExpCt(rs.getString(21));
            vulnInfo.setExpParam(rs.getString(22));
            vulnInfo.setExpGuide(rs.getString(23));
            vulnInfo.setRceParam(rs.getString(24));
            vulnInfo.setShellCheck(rs.getInt(25));
            vulnInfo.setShellPath(rs.getString(26));
            vulnInfo.setShellResWord(rs.getString(27));
        }
        DbUtil.close(rs, ps, sqliteCon);// 关闭连接
        return vulnInfo;
    }
}
