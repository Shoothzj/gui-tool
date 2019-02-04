package com.github.shoothzj.guitool.ui.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class BaseFileChooser extends JFileChooser {

    private static final Logger log = LoggerFactory.getLogger(BaseFileChooser.class);

    public BaseFileChooser(String path) {
        super(path);
    }

}
