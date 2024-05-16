package org.javamock.mysqlmock.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

// 显式实现 java.sql.Driver 接口: 这样做的好处是, 防止某些反射代码遍历时没考虑深度的问题;
public class Driver extends NonRegisteringDriver implements java.sql.Driver {

    // 只要有 Class.forName() 调用, 就会自动执行 static 代码, 注册对应的驱动
    // JDBC 4 之后, 系统会自动扫描 `java.sql.Driver` 的实现类;
    static {
        try {
            System.out.println("准备注册JDBC驱动: " + Driver.class.getName());
            DriverManager.registerDriver(new Driver());
            System.out.println("注册JDBC驱动完成: " + Driver.class.getName());
        } catch (SQLException e) {
            throw new RuntimeException("注册JDBC驱动失败:" + Driver.class.getName(), e);
        }
    }

    // 这里声明异常的好处是: 可能会经常报错, 调用者必须明确如何处理异常
    public Driver() throws SQLException {
    }
}
