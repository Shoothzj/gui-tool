package com.github.shoothzj.guitool.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTool {

    private static final Logger log = LoggerFactory.getLogger(FileTool.class);

    public static String getFileType(String fileName) {
        int lastIndexOf = fileName.lastIndexOf('.');
        return fileName.substring(lastIndexOf + 1);
    }

}
