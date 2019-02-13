package com.github.shoothzj.guitool.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

public class CertTool {

    private static final Logger log = LoggerFactory.getLogger(CertTool.class);

    private static final CertificateFactory factory;

    static {
        try {
            factory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new RuntimeException();
        }
    }

    public static void check(String file) throws CertificateException {
        try (FileInputStream fis = new FileInputStream(file)) {
            X509Certificate cer = (X509Certificate) factory.generateCertificate(fis);
            log.info("Cert Subject is {}", cer.getSubjectDN());
            log.info("NotBefore is {}", cer.getNotBefore());
            log.info("NotAfter is {}", cer.getNotAfter());
            log.info("is CA is {}", isCA(cer));
            log.info("is Valid is {}", isValid(cer));
        } catch (IOException e) {
            log.error("open file error, exception is {}", e);
        }
    }

    public static boolean isValid(X509Certificate certificate) {
        try {
            certificate.checkValidity();
            return true;
        } catch (CertificateExpiredException e) {
            log.error("cert expire, exception is {}", e);
        } catch (CertificateNotYetValidException e) {
            log.error("cert not yet valid {}", e);
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isCA(X509Certificate certificate) {
        int basicConstraints = certificate.getBasicConstraints();
        boolean[] usage = certificate.getKeyUsage();
        return basicConstraints != 1 && null != usage && usage.length > 5 && usage[5];
    }

}
