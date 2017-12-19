package com.krynytskyyserhiy.automation.framework.log.slf4j;

import org.slf4j.MDC;

/**
 * Created by serhiy.krynytskyy on 06.09.2016.
 */
public class MDCHelper {
    public static final String TEST_NAME = "testname";

    public static void startSeparateFileLogging(String fileName){
        MDC.put(TEST_NAME, fileName);
    }

    public static void finishSeparateFileLogging(){
        MDC.remove(TEST_NAME);
    }

}
