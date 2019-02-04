package com.github.shoothzj.guitool;

import com.github.shoothzj.guitool.module.CaDTO;
import com.github.shoothzj.guitool.module.CsrDTO;
import com.github.shoothzj.guitool.module.PrivateKeyDTO;
import com.github.shoothzj.guitool.tool.OpensslTool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpensslTest {

    private static final Logger log = LoggerFactory.getLogger(OpensslTest.class);

    @Test
    public void opensslTest() {
        UiTool.runOpenssl("openssl", "req", "-x509", "-sha256", "-nodes", "-days", "365", "-newkey", "rsa:2048", "-keyout", UiTool.getRelativePrefix() + "privateKey.key", "-out", UiTool.getRelativePrefix() + "certificate.crt" ,"-subj", "/CN=www.mydom.com/O=My Company Name LTD./C=US");
    }

    /**
     * 此方法可以生成整套的证书
     */
    @Test
    public void generateCert() {


    }

}
