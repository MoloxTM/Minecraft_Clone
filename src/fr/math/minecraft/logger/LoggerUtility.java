package fr.math.minecraft.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class LoggerUtility {

    private static final String CLIENT_TEXT_LOG_CONFIG = "log/client/log4j-text.properties";
    private static final String CLIENT_HTML_LOG_CONFIG = "log/client/log4j-html.properties";
    private static final String SERVER_TEXT_LOG_CONFIG = "log/server/log4j-text.properties";
    private static final String SERVER_HTML_LOG_CONFIG = "log/server/log4j-html.properties";
    private static final Date currentDate = new Date();

    public static Logger getClientLogger(Class<?> logClass, LogType logType) {
        Properties properties = new Properties();
        try {
            if (logType == LogType.HTML) {
                properties.load(new FileInputStream(CLIENT_HTML_LOG_CONFIG));
            } else if (logType == LogType.TXT) {
                properties.load(new FileInputStream(CLIENT_TEXT_LOG_CONFIG));
            } else {
                throw new IllegalArgumentException("Type de trace inconnue.");
            }

            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(currentDate);
            String logFileName = properties.getProperty("log4j.appender.file.File");
            logFileName = logFileName.replace(".log", "-" + timestamp + ".log");

            properties.setProperty("log4j.appender.file.File", logFileName);

            PropertyConfigurator.configure(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Logger.getLogger(logClass.getName());
    }

    public static Logger getServerLogger(Class<?> logClass, LogType logType) {
        Properties properties = new Properties();
        try {
            if (logType == LogType.HTML) {
                properties.load(new FileInputStream(SERVER_HTML_LOG_CONFIG));
            } else if (logType == LogType.TXT) {
                properties.load(new FileInputStream(SERVER_TEXT_LOG_CONFIG));
            } else {
                throw new IllegalArgumentException("Type de trace inconnue.");
            }

            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(currentDate);
            String logFileName = properties.getProperty("log4j.appender.file.File");
            logFileName = logFileName.replace(".log", "-" + timestamp + ".log");

            properties.setProperty("log4j.appender.file.File", logFileName);

            PropertyConfigurator.configure(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Logger.getLogger(logClass.getName());
    }

}