package org.javamock.mysqlmock.utils;

import java.util.concurrent.TimeUnit;

public class ThreadUtils {


    // 睡眠毫秒数
    public static void sleepMillis(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 当前线程名字
    public static String currentThreadName() {
        return Thread.currentThread().getName();
    }
}
