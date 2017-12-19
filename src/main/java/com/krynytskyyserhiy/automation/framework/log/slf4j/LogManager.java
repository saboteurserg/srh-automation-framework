package com.krynytskyyserhiy.automation.framework.log.slf4j;


//import org.slf4j.Logger;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class LogManager {

    static Logger rootLogger;

    public synchronized static Logger getInstance() {

        if (rootLogger == null) {
            rootLogger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        }

        return rootLogger;
    }

}