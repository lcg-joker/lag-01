package com.lcg.test;

import java.sql.*;

/**
 * JDBC
 *
 * @author lichenggang
 * @date 2020/2/23 10:46 下午
 * @description
 */
public class JDBCTest {


    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //1.加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.通过驱动 获取数据库连接对象
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8", "root", "root");
            //3.构造查询SQL
            String sql = "select * from user where userName=?";
            //4.通过连接获取预处理对象
            preparedStatement = connection.prepareStatement(sql);
            //5.设置参数
            preparedStatement.setString(1, "lcg");
            //6.查询数据库
            resultSet = preparedStatement.executeQuery();
            //7.遍历查询结果，封装成对象
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String userName = resultSet.getString("userName");
                System.out.println("id:" + id + ",userName=" + userName);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.cancel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
