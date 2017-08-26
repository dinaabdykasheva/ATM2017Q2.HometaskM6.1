package core.utils;

import org.slf4j.LoggerFactory;

/**
 * Created by User on 08.07.2017.
 */
public class Logger {
    public static org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

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
