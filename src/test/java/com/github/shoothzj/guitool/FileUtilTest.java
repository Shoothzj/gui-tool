package com.github.shoothzj.guitool;

import com.github.shoothzj.guitool.tool.FileTool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtilTest {

    private static final Logger log = LoggerFactory.getLogger(FileUtilTest.class);

    @Test
    public void test() {
        System.out.println(FileTool.getFileType("ca.key.pem"));
    }

}
