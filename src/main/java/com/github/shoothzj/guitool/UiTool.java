package com.github.shoothzj.guitool;

import com.github.shoothzj.guitool.ui.base.BaseFrame;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;

public class UiTool {

    private static final Logger log = LoggerFactory.getLogger(UiTool.class);

    public static final Stack<BaseFrame> frameStack = new Stack<>();

    private static Executor uiThread = Executors.newSingleThreadExecutor();

    public static void runUi(Runnable runnable) {
        uiThread.execute(runnable);
    }

    public static String getRelativePrefix() {
//        return System.getProperty("user.dir") + File.separator;
        return "/Users/akka/Yoshiko/temp/";
    }

    public static void runOpenssl(String... commandArray) {
        try {
            log.info("exec commandArray {}", Arrays.stream(commandArray).collect(toList()));
            Process exec = Runtime.getRuntime().exec(commandArray);
            String normalInfo = IOUtils.toString(exec.getInputStream(), "utf-8");
            String errorInfo = IOUtils.toString(exec.getErrorStream(), "utf-8");
            log.info("output is {}", normalInfo);
            if (StringUtils.isNotEmpty(errorInfo)) {
                log.error("error is {}", errorInfo);
            }
        } catch (Exception e) {
            log.error("exec command error", e);
        }
    }

}
