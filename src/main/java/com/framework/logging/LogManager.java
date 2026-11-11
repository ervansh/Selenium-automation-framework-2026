package com.framework.logging;

import org.apache.logging.log4j.Logger;

/** Central logger factory; configuration is maintained in log4j2.xml. */
public final class LogManager {
    private LogManager() { }
    public static Logger getLogger(Class<?> type) { return org.apache.logging.log4j.LogManager.getLogger(type); }
}