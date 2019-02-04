package com.github.shoothzj.guitool.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsrDTO {

    public String fileName;

    public String CommonName;

    public CsrDTO(String fileName, String commonName) {
        this.fileName = fileName;
        CommonName = commonName;
    }
}
