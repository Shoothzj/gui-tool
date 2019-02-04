package com.github.shoothzj.guitool.ui.frame;

import com.github.shoothzj.guitool.UiTool;
import com.github.shoothzj.guitool.tool.FileTool;
import com.github.shoothzj.guitool.ui.base.BaseButton;
import com.github.shoothzj.guitool.ui.base.BaseFileChooser;
import com.github.shoothzj.guitool.ui.base.BaseVerticalFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CaChooseFrame extends BaseVerticalFrame {

    private static final Logger log = LoggerFactory.getLogger(CaChooseFrame.class);

    private BaseButton uploadButton;

    public CaChooseFrame() {

    }

    @Override
    protected void initView() {
        uploadButton = new BaseButton();
        uploadButton.setText("上传文件");
    }

    @Override
    protected void addView() {
        add(uploadButton);
    }

    @Override
    protected void viewBind() {
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BaseFileChooser fileChooser = new BaseFileChooser(UiTool.getRelativePrefix());
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    parseFile(selectedFile);
                }
            }
        });
    }

    private void parseFile(File file) {
        String name = file.getName();
        String fileType = FileTool.getFileType(name);
        switch (fileType) {
            case "cer":
                try {
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    X509Certificate certificate = (X509Certificate) cf.generateCertificate(new FileInputStream(file));
                    certificate.checkValidity();
                    log.info("{}", new String(certificate.getSignature()));
                } catch (Exception e) {
                    log.error("parse error, exception is ", e);
                }
                break;
        }
    }

}
