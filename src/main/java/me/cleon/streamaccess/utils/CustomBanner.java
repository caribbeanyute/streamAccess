package me.cleon.streamaccess.utils;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;

public class CustomBanner implements Banner {
    @Override
    public void printBanner(Environment environment, Class<?> aClass, PrintStream printStream) {
        printStream.println("|==================================|");
        printStream.println("|-----------streamaccess-----------|");
        printStream.println("|==================================|");
        printStream.println("|>>> Java Home  : " + environment.getProperty("MYSQL_HOST"));
        printStream.println("|>>> Process Id : " + ManagementFactory.getRuntimeMXBean().getName());
    }
}