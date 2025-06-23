package com.project.logger.service;

import com.project.logger.enums.LogLevel;
import com.project.logger.models.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface Sink {

    void write(Message message);

    List<LogLevel> getSupportedLevels();

    void flush();

    default boolean isRotationRequired(Path logFile, long maxSize) throws IOException {
        if (!Files.exists(logFile)) {
            return false;
        }
        return Files.size(logFile) >= maxSize;
    }
}
