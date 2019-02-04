package com.github.shoothzj.guitool.tool;

import com.github.shoothzj.guitool.UiTool;
import com.github.shoothzj.guitool.module.CaDTO;
import com.github.shoothzj.guitool.module.CsrDTO;
import com.github.shoothzj.guitool.module.MixDTO;
import com.github.shoothzj.guitool.module.PrivateKeyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpensslTool {

    private static final Logger log = LoggerFactory.getLogger(OpensslTool.class);

    //利用openssl生成私钥
    public static PrivateKeyDTO generatePrivateKey(String fileName, String keyPwd) {
        String realPath = UiTool.getRelativePrefix() + fileName;
        PrivateKeyDTO privateKeyDTO = new PrivateKeyDTO(realPath, keyPwd);
        UiTool.runOpenssl("openssl", "genrsa", "-aes256", "-out",
                realPath, "-passout", "pass:" + keyPwd, "2048");
        return privateKeyDTO;
    }

    //生成一个csr请求
    public static CsrDTO generateCsrRequest(String fileName, String commonName, PrivateKeyDTO priKey) {
        String realPath = UiTool.getRelativePrefix() + fileName;
        UiTool.runOpenssl("openssl", "req", "-new", "-key", priKey.fileName,
                "-out", realPath, "-subj", "/C=CN/ST=BJ/L=BJ/O=zlex/OU=zlex/CN=*.abc.org",
                "-passin", "pass:" + priKey.pwd);
        return new CsrDTO(realPath, commonName);
    }

    //签发一个证书,自签发
    public static CaDTO selfSignCa(String cerName, PrivateKeyDTO priKey, CsrDTO csrDTO) {
        String realCerName = UiTool.getRelativePrefix() + cerName;
        UiTool.runOpenssl("openssl", "x509", "-req", "-days", "360", "-sha1", "-extensions",
                "v3_ca", "-signkey", priKey.fileName, "-in", csrDTO.fileName,
                "-out", realCerName, "-passin", "pass:" + priKey.pwd);
        return new CaDTO(realCerName);
    }

    public static CaDTO signCa(String cerName, PrivateKeyDTO caKey, CaDTO caDTO, CsrDTO csrDTO, boolean isFirst) {
        String realCerName = UiTool.getRelativePrefix() + cerName;
        if (isFirst) {
            UiTool.runOpenssl("openssl", "x509", "-req", "-days", "360", "-sha1", "-extensions",
                    "v3_req", "-CA", caDTO.cerName, "-CAkey", caKey.fileName,
                    "-CAserial", "ca.srl", "-CAcreateserial", "-in", csrDTO.fileName, "-out",
                    realCerName, "-passin", "pass:" + caKey.pwd);
        } else {
            UiTool.runOpenssl("openssl", "x509", "-req", "-days", "360", "-sha1", "-extensions",
                    "v3_req", "-CA", caDTO.cerName, "-CAkey", caKey.fileName,
                    "-CAserial", "ca.srl", csrDTO.fileName, "-out",
                    realCerName, "-passin", "pass:" + caKey.pwd);
        }
        return new CaDTO(realCerName);
    }

    public static MixDTO mixPriPub(String mixName, CaDTO caDTO, PrivateKeyDTO priKey, String newPwd) {
        String realPath = UiTool.getRelativePrefix() + mixName;
        UiTool.runOpenssl("openssl", "pkcs12", "-export", "-cacerts", "-inkey", priKey.fileName,
                "-in", caDTO.cerName, "-out", realPath,
                "-passin", "pass:" + priKey.pwd, "-password", "pass:" + newPwd);
        return new MixDTO(realPath, newPwd);
    }

}
