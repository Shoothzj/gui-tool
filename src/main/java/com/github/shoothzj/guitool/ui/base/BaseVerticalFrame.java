package com.github.shoothzj.guitool.ui.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public abstract class BaseVerticalFrame extends BaseFrame {

    private static final Logger log = LoggerFactory.getLogger(BaseVerticalFrame.class);

    public BaseVerticalFrame() {
        super();
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    @Override
    protected abstract void initView();

    @Override
    protected abstract void addView();

    @Override
    protected abstract void viewBind();
}
