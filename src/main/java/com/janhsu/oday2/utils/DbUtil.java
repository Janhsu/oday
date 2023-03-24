package com.janhsu.oday2.utils;

import javafx.scene.control.Alert;
import java.sql.*;

public class DbUtil {

    private static String DB_PATH = "db/database.db";
    private static String sqliteURL="jdbc:sqlite:" + DB_PATH;
    //驱动名称
    private  static  String jdbcNameSqlite = "org.sqlite.JDBC";

    public static  Connection getSqliteCon() throws SQLException {
        try {
            Class.forName(jdbcNameSqlite);
            Connection conn = null;
            conn = DriverManager.getConnection(sqliteURL);
            return conn;
        } catch (Exception e){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
//        alert.setContentText(ResultMsg.DB_SQLITE_ERROR.getMsg());
        alert.setContentText(String.valueOf(e));
        alert.showAndWait();
        e.printStackTrace();
    }
        return null;
    }

    /**
     * 关闭连接
     *
     * @throws Exception
     */
    public static void close(ResultSet rs, PreparedStatement st, Connection con) throws SQLException {
        if (rs != null) {
            rs.close();
            if (st != null) {
                st.close();
                if (con != null) {
                    con.close();
                }
            }
        }
    }

    //关闭连接和 执行 的打开资源
    public static void close(PreparedStatement st, Connection con) throws SQLException {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
