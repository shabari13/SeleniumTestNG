package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static final String SEPARATOR = "================================================================================";
    
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
    
    public static void logInfo(Logger logger, String message) {
        logger.info(message);
    }
    
    public static void logDebug(Logger logger, String message) {
        logger.debug(message);
    }
    
    public static void logError(Logger logger, String message) {
        logger.error(message);
    }
    
    public static void logError(Logger logger, String message, Throwable throwable) {
        logger.error(message, throwable);
    }
    
    public static void logWarn(Logger logger, String message) {
        logger.warn(message);
    }
    
    public static void logStep(Logger logger, String stepDescription) {
        String formattedStep = ">>> STEP: " + stepDescription;
        logger.info(formattedStep);
    }
    
    public static void logTestStart(Logger logger, String testName) {
        String separator = "=".repeat(80);
        logger.info(separator);
        logger.info("STARTING TEST: " + testName);
        logger.info(separator);
    }
    
    public static void logTestEnd(Logger logger, String testName, String status) {
        logger.info(SEPARATOR);
        logger.info("TEST COMPLETED: " + testName + " - Status: " + status);
        logger.info(SEPARATOR);
        System.out.println(SEPARATOR);
        System.out.println("TEST COMPLETED: " + testName + " - Status: " + status);
        System.out.println(SEPARATOR);
    }
}