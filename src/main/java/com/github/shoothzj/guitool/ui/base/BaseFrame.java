package com.github.shoothzj.guitool.ui.base;

import com.github.shoothzj.guitool.UiTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EmptyStackException;

public abstract class BaseFrame extends JFrame implements WindowListener{

    private static final Logger log = LoggerFactory.getLogger(BaseFrame.class);

    public BaseFrame() {
        this.addWindowListener(this);
        this.setSize(800, 1000);
        initView();
        addView();
        viewBind();
    }

    protected abstract void initView();

    protected abstract void addView();

    protected abstract void viewBind();

    public void showFrame() {
        this.setVisible(true);
        try {
            BaseFrame peek = UiTool.frameStack.peek();
            peek.setVisible(false);
        } catch (EmptyStackException e) {
            log.info("do nothing");
        }
        UiTool.frameStack.push(this);
    }

    public void close() {
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        UiTool.frameStack.pop();
        try {
            BaseFrame peek = UiTool.frameStack.peek();
            peek.setVisible(true);
        } catch (EmptyStackException ex) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
