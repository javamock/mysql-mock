package org.javamock.mysqlmock.jdbc;

import org.javamock.mysqlmock.engine.MockConstants;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

// 基础类: 不会自动注册到 DriverManager
public class NonRegisteringDriver implements java.sql.Driver {

    public NonRegisteringDriver() {
    }

    // 大版本号: 这个版本号实际上应该用构建工具注入;
    @Override
    public int getMajorVersion() {
        return MockConstants.VERSION_MAJOR;
    }

    // 小版本号; 这个版本号实际上应该用构建工具注入;
    @Override
    public int getMinorVersion() {
        return MockConstants.VERSION_MINOR;
    }

    // 是否可以接受某个url
    @Override
    public boolean acceptsURL(String url) throws SQLException {
        // 具体的前缀参见: com.mysql.cj.conf.ConnectionUrl.Type
        final String mysqlJDBCPrefix1 = "jdbc:mysql:";
        final String mockMysqlJDBCPrefix1 = "mock:jdbc:mysql:";
        if (url.startsWith(mysqlJDBCPrefix1)){
            return true;
        }
        if (url.startsWith(mockMysqlJDBCPrefix1)){
            return true;
        }

        return false;
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return null;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
