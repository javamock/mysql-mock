package org.javamock.test.jdbc;

import org.javamock.mysqlmock.utils.ThreadUtils;
import org.javamock.test.common.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import static org.javamock.mysqlmock.utils.ThreadUtils.currentThreadName;

// 驱动相关的代码单元测试
public class TestJDBCDriver extends BaseTest {

    public static String DRIVER_CLASS_NAME = "org.javamock.mysqlmock.jdbc.Driver";

    @Test
    public void testDriver() {
        // 1. 多次轮流执行
        doTestDriverRegister();
        doTestDeregisterDriver();

        doTestDriverRegister();
        doTestDeregisterDriver();

        // 2. 多次反复执行
        doTestDriverRegister();
        doTestDriverRegister();

        doTestDeregisterDriver();
        doTestDeregisterDriver();

        // 3. 还原驱动,避免其他地方需要;
        doTestDriverRegister();

    }

    public void doTestDriverRegister() {
        println("Test: 准备开始注册驱动测试;");
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

        // 查找驱动;
        Driver driver = findTheDriver();
        // 手动注册
        if (Objects.isNull(driver)) {
            println("Test: 查找驱动为空, 尝试手动注册驱动;");
            try {
                Method registerDriverM = clazz.getDeclaredMethod("registerDriver");
                registerDriverM.invoke(clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            driver = findTheDriver();
        }
        println("Test: 注册驱动测试逻辑执行结束;");
        Assertions.assertNotNull(driver, DRIVER_CLASS_NAME + " 对应的 driver 不能为空");
    }

    public Driver findTheDriver() {
        // 遍历已注册的驱动;
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        List<Driver> driverList = new ArrayList<>();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            driverList.add(driver);
        }

        println("已注册JDBC驱动数量: " + driverList.size() + "; threadName=" + currentThreadName());
        for (Driver driver : driverList) {
            Class<? extends Driver> driverClazz = driver.getClass();
            String clazzName = driverClazz.getName();
            if (DRIVER_CLASS_NAME.equals(clazzName)) {
                // 相等
                return driver;
            }
        }
        return null;
    }

    // maven 新的测试组件有坑; 以  test 开头的方法在单测时会自动执行
    public void doTestDeregisterDriver() {

        println("doTestDeregisterDriver: run;");
        Driver driver = findTheDriver();
        if (Objects.isNull(driver)) {
            println("查找的的驱动为空, 不再解除驱动注册; threadName = " + currentThreadName());
            return;
        }
        Assertions.assertNotNull(driver, DRIVER_CLASS_NAME + " 对应的 driver 不能为空");

        println("准备开始解除驱动注册; threadName = " + currentThreadName());
        try {
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            throw new RuntimeException("[错误]解除驱动注册失败: " + DRIVER_CLASS_NAME, e);
        }
        // 此处, 这个方法实际上不影响业务逻辑; 没什么作用;
        ThreadUtils.sleepMillis(1);
        //
        Driver driverAfter = findTheDriver();
        Assertions.assertNull(driverAfter, DRIVER_CLASS_NAME + " 解除驱动注册之后的 driver 应该为空");
    }
}
