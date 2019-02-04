package com.github.shoothzj.guitool;

import com.github.shoothzj.guitool.ui.frame.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Boot {

    private static final Logger log = LoggerFactory.getLogger(Boot.class);

    public static void main(String[] args) {
        UiTool.runUi(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.showFrame();
        });
    }

}
