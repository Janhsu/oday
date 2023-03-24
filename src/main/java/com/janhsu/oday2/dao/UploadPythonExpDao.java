package com.janhsu.oday2.dao;

import com.janhsu.oday2.entity.PythonExp;
import com.janhsu.oday2.entity.Vuln;
import com.janhsu.oday2.utils.DbUtil;
import com.janhsu.oday2.utils.ResultMsg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UploadPythonExpDao {
    private Connection sqliteCon;
    private PreparedStatement ps;
    private ResultSet rs;

    public ResultMsg insert(PythonExp pythonExp) throws SQLException, ClassNotFoundException, IOException {
        String sql;
        sqliteCon = DbUtil.getSqliteCon();
        sql = "insert into oday_pyexp(uuid,exp_name,exp_usage,exp_time,exp_author,exp_path,exp_method,exp_b64str)values(?,?,?,?,?,?,?,?)";
        try {
            ps = sqliteCon.prepareStatement(sql);
            ps.setString(1, pythonExp.getUuid());
            ps.setString(2, pythonExp.getExpName());
            ps.setString(3, pythonExp.getExpUsage());
            ps.setString(4, pythonExp.getExpTime());
            ps.setString(5, pythonExp.getExpAuthor());
            ps.setString(6, pythonExp.getExpPath());
            ps.setString(7, pythonExp.getExpMethod());
            ps.setString(8,pythonExp.getExpB64String());
            if (!ps.execute()) {
                DbUtil.close(ps,sqliteCon);// 关闭连接
                return ResultMsg.ADD_SUCCESS;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultMsg.ADD_ERROR;
    }

    public ResultMsg update(PythonExp pythonExp, String uuid) throws SQLException, ClassNotFoundException, IOException {
        String sql;
        sqliteCon = DbUtil.getSqliteCon();
        sql = "UPDATE oday_pyexp set exp_name=?,exp_usage=?,exp_time=?,exp_author=?,exp_path=?,exp_method=?,exp_b64str=? WHERE uuid=?";
        try {
            ps = sqliteCon.prepareStatement(sql);
            ps.setString(1, pythonExp.getExpName());
            ps.setString(2, pythonExp.getExpUsage());
            ps.setString(3, pythonExp.getExpTime());
            ps.setString(4, pythonExp.getExpAuthor());
            ps.setString(5, pythonExp.getExpPath());
            ps.setString(6, pythonExp.getExpMethod());
            ps.setString(7,pythonExp.getExpB64String());
            ps.setString(8, uuid);
            if (!ps.execute()) {
                DbUtil.close(ps,sqliteCon);// 关闭连接
                return ResultMsg.ADD_SUCCESS;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultMsg.ADD_ERROR;
    }
    /**
     * 判断漏洞uuid是否存在
     */
    public Boolean ifExpUuidExist(String uuid) throws SQLException, ClassNotFoundException{
        String sql;
        sqliteCon = DbUtil.getSqliteCon();
        //装载sql语句
        sql = "SELECT uuid FROM oday_pyexp WHERE uuid = ?";
        ps = sqliteCon.prepareStatement(sql);
        ps.setString(1,uuid);
        //执行sql语句
        rs = ps.executeQuery();
        if (rs.next()) {
            DbUtil.close(rs,ps,sqliteCon);// 关闭连接
            return true;//存在漏洞名
        }
        else {
            return false;//不存在漏洞名
        }
    }

    /**
     * 获取编辑信息
     */
    public PythonExp getEditInfo(String uuid) throws SQLException, ClassNotFoundException, IOException {
        PythonExp editInfo = new PythonExp();
        String sql;
        sqliteCon = DbUtil.getSqliteCon();
        sql = "SELECT exp_name,exp_usage,exp_author,exp_b64str,exp_method FROM oday_pyexp WHERE uuid = ?";
        ps = sqliteCon.prepareStatement(sql);
        ps.setString(1,uuid);

        rs = ps.executeQuery();

        if (rs.next()){
            editInfo.setExpName(rs.getString(1));
            editInfo.setExpUsage(rs.getString(2));
            editInfo.setExpAuthor(rs.getString(3));
            editInfo.setExpB64String(rs.getString(4));
            editInfo.setExpMethod(rs.getString(5));
        }
        DbUtil.close(rs,ps, sqliteCon);// 关闭连接
        return editInfo;
    }
}
