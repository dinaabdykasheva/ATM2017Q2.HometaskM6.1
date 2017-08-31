package core.utils;

import org.apache.log4j.Logger;

/**
 * Created by User on 08.07.2017.
 */
public class MyLogger {
    public static Logger logger = Logger.getLogger(MyLogger.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
