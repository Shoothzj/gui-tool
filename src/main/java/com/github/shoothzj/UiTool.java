package com.github.shoothzj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UiTool {

    private static final Logger log = LoggerFactory.getLogger(UiTool.class);

    private static Executor uiThread = Executors.newSingleThreadExecutor();

    public static void runUi(Runnable runnable) {
        uiThread.execute(runnable);
    }

}
