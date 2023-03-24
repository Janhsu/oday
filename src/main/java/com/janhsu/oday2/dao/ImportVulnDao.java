package com.janhsu.oday2.dao;

import com.janhsu.oday2.entity.Vuln;
import com.janhsu.oday2.utils.DbUtil;
import com.janhsu.oday2.utils.ResultMsg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ImportVulnDao {
    private Connection sqliteCon;
    private PreparedStatement ps;
    private ResultSet rs;
    /**
     * sqlite插入漏洞信息
     */
    public ResultMsg insertTable(Vuln vulnInfo) throws SQLException {
        String sql;
        sqliteCon = DbUtil.getSqliteCon();
        sql = "insert into oday_vuln(uuid,cms_name,vuln_name,vuln_type,vuln_intro,vuln_time,poc_path,poc_method,poc_headers,poc_ct,poc_param,res_method,res_code,res_word,res_andor,exp_path,exp_method,exp_headers,exp_ct,exp_param,no_exp,exp_guide,rce_param,shell_check,shell_path,shell_resword)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = sqliteCon.prepareStatement(sql);
            ps.setString(1, vulnInfo.getUuid());
            ps.setString(2, vulnInfo.getCmsName());
            ps.setString(3,vulnInfo.getVulnName());
            ps.setString(4, vulnInfo.getVulnType());
            ps.setString(5, vulnInfo.getVulnIntro());
            ps.setString(6, vulnInfo.getVulnTime());
            ps.setString(7, vulnInfo.getPocPath());
            ps.setString(8, vulnInfo.getPocMethod());
            ps.setString(9, vulnInfo.getPocHeaders());
            ps.setString(10, vulnInfo.getPocCt());
            ps.setString(11, vulnInfo.getPocParam());
            ps.setString(12,vulnInfo.getResMethod());
            ps.setString(13,vulnInfo.getResCode());
            ps.setString(14, vulnInfo.getResWord());
            ps.setString(15, vulnInfo.getResAndor());
            ps.setString(16, vulnInfo.getExpPath());
            ps.setString(17, vulnInfo.getExpMethod());
            ps.setString(18, vulnInfo.getExpHeaders());
            ps.setString(19, vulnInfo.getExpCt());
            ps.setString(20, vulnInfo.getExpParam());
            ps.setInt(21,vulnInfo.getNoExp());
            ps.setString(22,vulnInfo.getExpGuide());
            ps.setString(23,vulnInfo.getRceParam());
            ps.setInt(24,vulnInfo.getShellCheck());
            ps.setString(25,vulnInfo.getShellPath());
            ps.setString(26,vulnInfo.getShellResWord());
            if (!ps.execute()) {
                DbUtil.close(ps,sqliteCon);// 关闭连接
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultMsg.ADD_ERROR;
    }

    /**
     * 判断漏洞uuid是否存在
     */
    public Boolean ifVulnIdExist(String uuid) throws SQLException{

        String sql;
        sqliteCon = DbUtil.getSqliteCon();
        //装载sql语句
        sql = "SELECT uuid FROM oday_vuln WHERE uuid = ?";
        ps = sqliteCon.prepareStatement(sql);
        ps.setString(1,uuid);
        //执行sql语句
        rs = ps.executeQuery();

        if (rs.next()) {
            DbUtil.close(rs,ps,sqliteCon);// 关闭连接
            return true;//存在漏洞
        }
        else {
            DbUtil.close(rs,ps,sqliteCon);// 关闭连接
            return false;//不存在漏洞
        }
    }
}
