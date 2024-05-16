package org.javamock.test.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class TestJDBCDriver {

    public static String DRIVER_CLASS_NAME = "org.javamock.mysqlmock.jdbc.Driver";

    @Test
    public void testDriverRegister() {
        System.out.println("Test: 准备开始注册驱动测试;");
        Class<?> clazz = null;
        try {
            // forName 默认会执行类初始化
            clazz = Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //
        Assertions.assertNotNull(clazz);
        Assertions.assertEquals(DRIVER_CLASS_NAME, clazz.getName(), "驱动名称和类名称必须相等");

        testDeregisterDriver();

    }


    public void testDeregisterDriver() {
        System.out.println("准备开始取消注册驱动测试;");
        // 第一轮测试;
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            Class<? extends Driver> driverClazz = driver.getClass();
            String clazzName = driverClazz.getName();
            if (DRIVER_CLASS_NAME.equals(clazzName)) {
                // 相等
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    throw new RuntimeException("取消注册驱动失败: " + DRIVER_CLASS_NAME, e);
                }
            }
        }
        //

    }
}
