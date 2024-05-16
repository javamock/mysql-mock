package org.javamock.mysqlmock.jdbc;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

// 基础类: 不会自动注册到 DriverManager
public class NonRegisteringDriver implements java.sql.Driver{

    public NonRegisteringDriver(){
    }

    // 大版本号: 这个版本号实际上应该用构建工具注入;
    @Override
    public int getMajorVersion() {
        return 1;
    }

    // 小版本号; 这个版本号实际上应该用构建工具注入;
    @Override
    public int getMinorVersion() {
        return 1;
    }
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return null;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return false;
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
