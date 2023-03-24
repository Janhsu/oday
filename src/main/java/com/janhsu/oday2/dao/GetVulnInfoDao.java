package com.janhsu.oday2.dao;

import com.janhsu.oday2.entity.Vuln;
import com.janhsu.oday2.entity.VulnExploitInfo;
import com.janhsu.oday2.entity.VulnScanInfo;
import com.janhsu.oday2.utils.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetVulnInfoDao {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    /**
     * 获取编辑漏洞信息
     */
    public Vuln getEditInfo(String uuid) throws SQLException, ClassNotFoundException {
        Vuln editInfo = new Vuln();
        String sql;
        con = DbUtil.getSqliteCon();
        sql = "SELECT cms_name,vuln_name,vuln_intro,poc_path,poc_headers,poc_ct,poc_param,res_code,res_word,exp_path,exp_headers,exp_ct,exp_param,exp_guide,rce_param,vuln_type,poc_method,res_method,res_andor,exp_method,no_exp,shell_check,shell_path,shell_resword FROM oday_vuln WHERE uuid = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1,uuid);

        rs = ps.executeQuery();

        if (rs.next()){
            editInfo.setCmsName(rs.getString(1));
            editInfo.setVulnName(rs.getString(2));
            editInfo.setVulnIntro(rs.getString(3));
            editInfo.setPocPath(rs.getString(4));
            editInfo.setPocHeaders(rs.getString(5));
            editInfo.setPocCt(rs.getString(6));
            editInfo.setPocParam(rs.getString(7));
            editInfo.setResCode(rs.getString(8));
            editInfo.setResWord(rs.getString(9));
            editInfo.setExpPath(rs.getString(10));
            editInfo.setExpHeaders(rs.getString(11));
            editInfo.setExpCt(rs.getString(12));
            editInfo.setExpParam(rs.getString(13));
            editInfo.setExpGuide(rs.getString(14));
            editInfo.setRceParam(rs.getString(15));
            editInfo.setVulnType(rs.getString(16));
            editInfo.setPocMethod(rs.getString(17));
            editInfo.setResMethod(rs.getString(18));
            editInfo.setResAndor(rs.getString(19));
            editInfo.setExpMethod(rs.getString(20));
            editInfo.setNoExp(rs.getInt(21));
            editInfo.setShellCheck(rs.getInt(22));
            editInfo.setShellPath(rs.getString(23));
            editInfo.setShellResWord(rs.getString(24));
        }
        DbUtil.close(rs,ps, con);// 关闭连接
        return editInfo;
    }


    /**
     * 获取漏洞扫描信息
     */
    public List getVulnScanInfo() throws SQLException, ClassNotFoundException {
        String sql;
        List<VulnScanInfo> list = new ArrayList<>();     //该集合必须是符合javafx形式的集合
        con = DbUtil.getSqliteCon();

        //装载sql语句
        sql = "SELECT id,vuln_name,poc_path,poc_method,poc_headers,poc_ct,poc_param,res_method,res_code,res_word,res_andor,shell_check,shell_path,shell_resword FROM oday_vuln";
        ps = con.prepareStatement(sql);

        //执行sql语句
        rs = ps.executeQuery();

        while (rs.next()) {// 存在数据，封装返回;多条数据，用while循环装入每个pocShowList对象
            VulnScanInfo vulnScanInfo = new VulnScanInfo();                  //必须new对象，否则传回空指针
            vulnScanInfo.setId(rs.getInt(1));
            vulnScanInfo.setVulnName(rs.getString(2));
            vulnScanInfo.setPocPath(rs.getString(3));
            vulnScanInfo.setPocMethod(rs.getString(4));
            vulnScanInfo.setPocHeaders(rs.getString(5));
            vulnScanInfo.setPocCt(rs.getString(6));
            vulnScanInfo.setPocParam(rs.getString(7));
            vulnScanInfo.setResMethod(rs.getString(8));
            vulnScanInfo.setResCode(rs.getString(9));
            vulnScanInfo.setResWord(rs.getString(10));
            vulnScanInfo.setResAndor(rs.getString(11));
            vulnScanInfo.setShellCheck(rs.getInt(12));
            vulnScanInfo.setShellPath(rs.getString(13));
            vulnScanInfo.setShellResWord(rs.getString(14));
            list.add(vulnScanInfo);//将每个pocShowList对象装入list集合
        }
        DbUtil.close(rs,ps, con);// 关闭连接
        return list;
    }


    /**
     * 获取漏洞利用信息
     */
    public List getVulnExploitInfo() throws SQLException, ClassNotFoundException {
        String sql;
        List<VulnExploitInfo> list = new ArrayList<>();     //该集合必须是符合javafx形式的集合
        con = DbUtil.getSqliteCon();

        //装载sql语句
        sql = "SELECT id,vuln_name,exp_path,exp_method,exp_headers,exp_ct,exp_param,exp_guide,rce_param FROM oday_vuln WHERE no_exp = 0";
        ps = con.prepareStatement(sql);

        //执行sql语句
        rs = ps.executeQuery();
        while (rs.next()) {// 存在数据，封装返回;多条数据，用while循环装入每个pocShowList对象
            VulnExploitInfo vulnExploitInfo = new VulnExploitInfo();                  //必须new对象，否则传回空指针
            vulnExploitInfo.setId(rs.getInt(1));
            vulnExploitInfo.setVulnName(rs.getString(2));
            vulnExploitInfo.setExpPath(rs.getString(3));
            vulnExploitInfo.setExpMethod(rs.getString(4));
            vulnExploitInfo.setExpHeaders(rs.getString(5));
            vulnExploitInfo.setExpCt(rs.getString(6));
            vulnExploitInfo.setExpParam(rs.getString(7));
            vulnExploitInfo.setExpGuide(rs.getString(8));
            vulnExploitInfo.setRceParam(rs.getString(9));
            list.add(vulnExploitInfo);//将每个pocShowList对象装入list集合
        }
        DbUtil.close(rs,ps, con);// 关闭连接
        return list;
    }

    /**
     * 获取cms去重信息
     */
    public List getCmsInfoList() throws SQLException, ClassNotFoundException {
        String sql;//该集合必须是符合javafx形式的集合
        con = DbUtil.getSqliteCon();
        List<String> list = new ArrayList<>();
        //装载sql语句
        sql = "SELECT distinct cms_name FROM oday_vuln ";
        ps = con.prepareStatement(sql);
        //执行sql语句
        rs = ps.executeQuery();
        while (rs.next()) {// 存在数据，封装返回;多条数据，用while循环装入每个pocShowList对象
            list.add(rs.getString(1));//将每个pocShowList对象装入list集合
        }
        DbUtil.close(rs,ps, con);// 关闭连接
        return list;
    }

    /**
     * 按照cms获取漏洞扫描信息
     */
    public List getVulnScanInfoByCmsName(String cmsName) throws SQLException {
        String sql;
        List<VulnScanInfo> list = new ArrayList<>();     //该集合必须是符合javafx形式的集合
        con = DbUtil.getSqliteCon();

        //装载sql语句
        sql = "SELECT id,vuln_name,poc_path,poc_method,poc_headers,poc_ct,poc_param,res_method,res_code,res_word,res_andor,shell_check,shell_path,shell_resword FROM oday_vuln WHERE cms_name = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1,cmsName);

        //执行sql语句
        rs = ps.executeQuery();

        while (rs.next()) {// 存在数据，封装返回;多条数据，用while循环装入每个pocShowList对象
            VulnScanInfo vulnScanInfo = new VulnScanInfo();                  //必须new对象，否则传回空指针
            vulnScanInfo.setId(rs.getInt(1));
            vulnScanInfo.setVulnName(rs.getString(2));
            vulnScanInfo.setPocPath(rs.getString(3));
            vulnScanInfo.setPocMethod(rs.getString(4));
            vulnScanInfo.setPocHeaders(rs.getString(5));
            vulnScanInfo.setPocCt(rs.getString(6));
            vulnScanInfo.setPocParam(rs.getString(7));
            vulnScanInfo.setResMethod(rs.getString(8));
            vulnScanInfo.setResCode(rs.getString(9));
            vulnScanInfo.setResWord(rs.getString(10));
            vulnScanInfo.setResAndor(rs.getString(11));
            vulnScanInfo.setShellCheck(rs.getInt(12));
            vulnScanInfo.setShellPath(rs.getString(13));
            vulnScanInfo.setShellResWord(rs.getString(14));
            list.add(vulnScanInfo);//将每个pocShowList对象装入list集合
        }
        DbUtil.close(rs,ps, con);// 关闭连接
        return list;
    }
}
