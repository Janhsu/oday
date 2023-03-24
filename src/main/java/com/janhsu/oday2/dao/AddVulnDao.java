package com.janhsu.oday2.dao;

import com.janhsu.oday2.entity.Vuln;
import com.janhsu.oday2.utils.DbUtil;
import com.janhsu.oday2.utils.ResultMsg;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddVulnDao {

    private PreparedStatement ps;
    private ResultSet rs;
    Connection con;

    public ResultMsg insertVulnDetail(Vuln vuln) throws SQLException {
        String sql;
        con = DbUtil.getSqliteCon();
        sql = "insert into oday_vuln(uuid,cms_name,vuln_name,vuln_type,vuln_intro,vuln_time,poc_path,poc_method,poc_headers,poc_ct,poc_param,res_method,res_code,res_word,res_andor,exp_path,exp_method,exp_headers,exp_ct,exp_param,no_exp,exp_guide,rce_param,shell_check,shell_path,shell_resword)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, vuln.getUuid());
            ps.setString(2, vuln.getCmsName());
            ps.setString(3,vuln.getVulnName());
            ps.setString(4, vuln.getVulnType());
            ps.setString(5, vuln.getVulnIntro());
            ps.setString(6, vuln.getVulnTime());
            ps.setString(7, vuln.getPocPath());
            ps.setString(8, vuln.getPocMethod());
            ps.setString(9, vuln.getPocHeaders());
            ps.setString(10, vuln.getPocCt());
            ps.setString(11, vuln.getPocParam());
            ps.setString(12,vuln.getResMethod());
            ps.setString(13,vuln.getResCode());
            ps.setString(14, vuln.getResWord());
            ps.setString(15, vuln.getResAndor());
            ps.setString(16, vuln.getExpPath());
            ps.setString(17, vuln.getExpMethod());
            ps.setString(18, vuln.getExpHeaders());
            ps.setString(19, vuln.getExpCt());
            ps.setString(20, vuln.getExpParam());
            ps.setInt(21,vuln.getNoExp());
            ps.setString(22,vuln.getExpGuide());
            ps.setString(23,vuln.getRceParam());
            ps.setInt(24,vuln.getShellCheck());
            ps.setString(25,vuln.getShellPath());
            ps.setString(26,vuln.getShellResWord());
            if (!ps.execute()) {
                System.out.println("插入成功");
                DbUtil.close(ps,con);// 关闭连接
                return ResultMsg.ADD_SUCCESS;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultMsg.ADD_ERROR;
    }
    /**
     * 更新漏洞信息
     */
    public ResultMsg updateVulnInfo(Vuln vuln,String uuid) throws SQLException{
        String sql;
        con = DbUtil.getSqliteCon();
        sql = "UPDATE oday_vuln set cms_name=?,vuln_name=?,vuln_type=?,vuln_intro=?,vuln_time=?,poc_path=?,poc_method=?,poc_headers=?,poc_ct=?,poc_param=?,res_method=?,res_code=?,res_word=?,res_andor=?,exp_path=?,exp_method=?,exp_headers=?,exp_ct=?,exp_param=?,no_exp=?,exp_guide=?,rce_param=?,shell_check=?,shell_path=?,shell_resword=? WHERE uuid=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, vuln.getCmsName());
            ps.setString(2,vuln.getVulnName());
            ps.setString(3, vuln.getVulnType());
            ps.setString(4, vuln.getVulnIntro());
            ps.setString(5, vuln.getVulnTime());
            ps.setString(6, vuln.getPocPath());
            ps.setString(7, vuln.getPocMethod());
            ps.setString(8, vuln.getPocHeaders());
            ps.setString(9, vuln.getPocCt());
            ps.setString(10, vuln.getPocParam());
            ps.setString(11,vuln.getResMethod());
            ps.setString(12,vuln.getResCode());
            ps.setString(13, vuln.getResWord());
            ps.setString(14, vuln.getResAndor());
            ps.setString(15, vuln.getExpPath());
            ps.setString(16, vuln.getExpMethod());
            ps.setString(17, vuln.getExpHeaders());
            ps.setString(18, vuln.getExpCt());
            ps.setString(19, vuln.getExpParam());
            ps.setInt(20,vuln.getNoExp());
            ps.setString(21,vuln.getExpGuide());
            ps.setString(22,vuln.getRceParam());
            ps.setInt(23,vuln.getShellCheck());
            ps.setString(24,vuln.getShellPath());
            ps.setString(25,vuln.getShellResWord());
            ps.setString(26, uuid);
            if (!ps.execute()) {
                System.out.println("更新成功");
                DbUtil.close(ps,con);// 关闭连接
                return ResultMsg.UPDATE_SUCCESS;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultMsg.ADD_ERROR;
    }




    public ResultMsg deleteByUuid(String uuid) throws SQLException {
        con = DbUtil.getSqliteCon();
        String sql = "DELETE from oday_vuln where uuid=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            if (!ps.execute()) {// 删除成功
                DbUtil.close(rs,ps,con);// 关闭连接
                return ResultMsg.DELETE_SUCCESS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultMsg.DELETE_FAILED;
    }


}
