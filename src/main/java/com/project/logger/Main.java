package com.project.logger;

import com.project.logger.enums.LogLevel;
import com.project.logger.enums.LogNameSpace;
import com.project.logger.enums.SinkType;
import com.project.logger.utils.LogConfig;
import com.project.logger.utils.Logger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LogConfig fileLogConfig = LogConfig.builder()
                .timeFormat("dd-MM-yyyy HH:mm:ss")
                .logLevel(LogLevel.INFO)
                .fileLocation("logs/file.app.log")
                .fileSizeBytes(5 * 1024 * 1024)
                .build();

        LogConfig consoleLogConfig = LogConfig.builder()
                .timeFormat("dd-MM-yyyy HH:mm:ss")
                .sinkType(SinkType.CONSOLE)
                .logLevel(LogLevel.DEBUG)
                .fileLocation("logs/console.app.log")
                .fileSizeBytes(5 * 1024 * 1024)
                .build();

        Logger logger = new Logger(fileLogConfig, consoleLogConfig);

        for (int i = 0; i < 10; i++) {
            logger.log(LogLevel.DEBUG, "Debug Error for order", LogNameSpace.ORDER_SERVICE);
            logger.log(LogLevel.INFO, "Info error for order", LogNameSpace.ORDER_SERVICE);
            logger.log(LogLevel.FATAL, "Fatal error for user", LogNameSpace.USER_SERVICE);

            System.out.println("Running case  " + i);
            Thread.sleep(1000);
        }

        logger.flush();
    }
}