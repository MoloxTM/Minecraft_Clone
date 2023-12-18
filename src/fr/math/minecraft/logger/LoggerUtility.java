package fr.math.minecraft.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtility {

    private static final String CLIENT_TEXT_LOG_CONFIG = "log/client/log4j-text.properties";
    private static final String CLIENT_HTML_LOG_CONFIG = "log/client/log4j-html.properties";
    private static final String SERVER_TEXT_LOG_CONFIG = "log/server/log4j-text.properties";
    private static final String SERVER_HTML_LOG_CONFIG = "log/server/log4j-html.properties";

    public static Logger getClientLogger(Class<?> logClass, LogType logType) {
        if (logType == LogType.HTML) {
            PropertyConfigurator.configure(CLIENT_HTML_LOG_CONFIG);
        } else if (logType == LogType.TXT) {
            PropertyConfigurator.configure(CLIENT_TEXT_LOG_CONFIG);
        } else {
            throw new IllegalArgumentException("Type de trace inconnue.");
        }
        return Logger.getLogger(logClass.getName());
    }

    public static Logger getServerLogger(Class<?> logClass, LogType logType) {
        if (logType == LogType.HTML) {
            PropertyConfigurator.configure(SERVER_HTML_LOG_CONFIG);
        } else if (logType == LogType.TXT) {
            PropertyConfigurator.configure(SERVER_TEXT_LOG_CONFIG);
        } else {
            throw new IllegalArgumentException("Type de trace inconnue.");
        }
        return Logger.getLogger(logClass.getName());
    }

}