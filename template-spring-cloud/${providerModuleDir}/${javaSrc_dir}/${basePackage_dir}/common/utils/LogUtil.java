package ${basePackage}.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/29 10:20
 */

public class LogUtil {

    public Logger logger;

    public static LogUtil build(Class clazz) {
        return new LogUtil(clazz);
    }

    private LogUtil(Class clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public void debug(String api, String sessionid, String message) {
        String msg = "sessionid: " + sessionid + " ,methord: " + api + " ,message: " + message;
        logger.debug(msg);
    }

    public void debug(String api, String sessionid, String message, Object... var) {
        String msg = "sessionid: " + sessionid + " ,methord: " + api + " ,message: " + message;
        logger.debug(msg, var);
    }

    public void info(String api, String message) {
        String msg = "methord: " + api + " ,message: " + message;
        logger.info(msg);
    }

    public void info(String api, String sessionid, String message) {
        String msg = "sessionid: " + sessionid + " ,methord: " + api + " ,message: " + message;
        logger.info(msg);
    }

    public void info(String api, String sessionid, String message, Object... var) {
        String msg = "sessionid: " + sessionid + " ,methord: " + api + " ,message: " + message;
        logger.info(msg, var);
    }

    public void error(String api, Exception e) {
        String msg = "methord: " + api;
        logger.error(msg, e);
    }

    public void error(String api, String message) {
        String msg = "methord: " + api + " ,message: " + message;
        logger.error(msg);
    }

    public void error(String api, String sessionid, Exception e) {
        String msg = "sessionid: " + sessionid + " ,methord: " + api;
        logger.error(msg, e);
    }

    public void error(String api, String sessionid, String message) {
        String msg = "sessionid: " + sessionid + " ,methord: " + api + " ,message: " + message;
        logger.error(msg);
    }

    public void error(String api, String sessionid, String message, Exception e) {
        String msg = "sessionid: " + sessionid + " ,methord: " + api + " ,message: " + message;
        logger.error(msg, e);
    }

    public void error(String api, String sessionid, String message, Object... var) {
        String msg = "sessionid: " + sessionid + " ,methord: " + api + " ,message: " + message;
        logger.error(msg, var);
    }

}
