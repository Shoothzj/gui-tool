package com.github.shoothzj.guitool.ui.frame;

import com.github.shoothzj.guitool.UiTool;
import com.github.shoothzj.guitool.module.CaDTO;
import com.github.shoothzj.guitool.module.CsrDTO;
import com.github.shoothzj.guitool.module.PrivateKeyDTO;
import com.github.shoothzj.guitool.tool.OpensslTool;
import com.github.shoothzj.guitool.ui.base.BaseButton;
import com.github.shoothzj.guitool.ui.base.BaseVerticalFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends BaseVerticalFrame {

    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    BaseButton parseCaButton;

    BaseButton generateSelfSignedButton;

    BaseButton generateFullCertButton;

    BaseButton judgeVerifyButton;

    public MainFrame() {

    }

    @Override
    protected void initView() {
        parseCaButton = new BaseButton();
        parseCaButton.setText("解析证书文件");
        generateSelfSignedButton = new BaseButton();
        generateSelfSignedButton.setText("生成一套自签名证书");
        generateFullCertButton = new BaseButton();
        generateFullCertButton.setText("生成一套证书");
        judgeVerifyButton = new BaseButton();
        judgeVerifyButton.setText("校验证书的配套关系");
    }

    @Override
    protected void addView() {
        this.add(parseCaButton);
        this.add(generateSelfSignedButton);
        this.add(generateFullCertButton);
        this.add(judgeVerifyButton);
    }

    @Override
    protected void viewBind() {
        parseCaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CaChooseFrame frame = new CaChooseFrame();
                frame.showFrame();
            }
        });
        generateSelfSignedButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiTool.runOpenssl("openssl", "req", "-x509", "-sha256",
                        "-nodes", "-days", "365", "-newkey", "rsa:2048", "-keyout",
                        UiTool.getRelativePrefix() + "privateKey.key",
                        "-out", UiTool.getRelativePrefix() + "certificate.crt" ,
                        "-subj", "/CN=www.mydom.com/O=My Company Name LTD./C=US");

            }
        });
        generateFullCertButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //root ca
                PrivateKeyDTO rootKeyDTO = OpensslTool.generatePrivateKey("ca_key.pem", "capwd");
                //csr request
                CsrDTO rootCsr = OpensslTool.generateCsrRequest("ca.csr", "*.abc.org", rootKeyDTO);

                //self sign
                CaDTO rootCaDTO = OpensslTool.selfSignCa("ca.cer", rootKeyDTO, rootCsr);

                OpensslTool.mixPriPub("ca.p12", rootCaDTO, rootKeyDTO, "cap12pwd");

                PrivateKeyDTO serverKeyDTO = OpensslTool.generatePrivateKey("server_key.pem", "serverpwd");

                CsrDTO serverCsrDTO = OpensslTool.generateCsrRequest("server.csr", "*.abc.org", serverKeyDTO);

                CaDTO serverCa = OpensslTool.signCa("server.cer", rootKeyDTO, rootCaDTO, serverCsrDTO, true);

                OpensslTool.mixPriPub("server.p12", serverCa, serverKeyDTO, "server12pwd");

                PrivateKeyDTO clientKeyDTO = OpensslTool.generatePrivateKey("client_key.pem", "clientpwd");

                CsrDTO clientCsrDTO = OpensslTool.generateCsrRequest("client.csr", "*.abc.org", clientKeyDTO);

                CaDTO clientCa = OpensslTool.signCa("client.cer", rootKeyDTO, rootCaDTO, clientCsrDTO, true);

                OpensslTool.mixPriPub("client.p12", clientCa, clientKeyDTO, "client12pwd");
            }
        });
    }

}
