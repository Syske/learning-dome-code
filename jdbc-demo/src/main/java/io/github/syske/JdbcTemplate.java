package io.github.syske;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: jdbc-demo
 * @description: JDBC模板类
 * @author: syske
 * @create: 2020-02-18 14:42
 */
public class JdbcTemplate {
    /**
     * 查询的封装方法
     */
    public <T> List<T> queryForList(RowMapper<T> mapper, String sql, Object[] params) {
        List<T> returnResult = new ArrayList<T>();
        Connection conn = null; // 一个数据库连接
        PreparedStatement pre = null; // 预编译对象
        ResultSet rs = null; // 结果集

        try {
            conn = DbUtil.getConnection();
            pre = conn.prepareStatement(sql);
            //设置sql所需要的参数
            setParams(params, pre);
            rs = pre.executeQuery();
            int rownum = 0;
            while (rs.next()) {
                rownum++;
                returnResult.add(mapper.mappingRow(rs, rownum));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeConnection(conn, pre, rs);
        }
        return returnResult;
    }

    private void setParams(Object[] params, PreparedStatement pre) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pre.setObject(i + 1, params[i]);
            }
        }
    }
}
