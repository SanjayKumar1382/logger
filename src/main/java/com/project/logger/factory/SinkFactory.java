package com.project.logger.factory;

import com.project.logger.service.Sink;
import com.project.logger.service.implementation.ConsoleSinkImpl;
import com.project.logger.service.implementation.FileSinkImpl;
import com.project.logger.utils.LogConfig;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public class SinkFactory {

    public static Sink getSink(LogConfig config) {
        Path path = Path.of(config.getFileLocation());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(config.getTimeFormat());
        switch (config.getSinkType()) {
            case FILE:
                return FileSinkImpl.builder()
                        .logFile(path)
                        .levels(config.getEnabledLevels())
                        .maxSize(config.getFileSizeBytes())
                        .formatter(formatter)
                        .build();
            case CONSOLE:
                return ConsoleSinkImpl.builder()
                        .logFile(path)
                        .levels(config.getEnabledLevels())
                        .maxSize(config.getFileSizeBytes())
                        .formatter(formatter)
                        .build();
            case CUSTOM:
                return config.getSink();
            default:
                return null;
        }
    }
}
