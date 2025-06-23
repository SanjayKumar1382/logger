package com.project.logger.service.implementation;

import com.project.logger.service.LogConfigValidator;
import com.project.logger.utils.LogConfig;
import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class ConsoleLogConfigValidator implements LogConfigValidator {
    private final LogConfig logConfig;

    public boolean isValid() {

        if (logConfig.getFileSizeBytes() > LogConfig.MAX_FILE_SIZE_BYTE) {
            System.out.println("Max File size exceed");
            return false;
        }
        try {
            Path path = Path.of(logConfig.getFileLocation());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(logConfig.getTimeFormat());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid log config for sink type : " + logConfig.getSinkType() + " Error : " + e.getMessage());
            return false;
        }
        System.out.println("Log config is valid for sink type : " + logConfig.getSinkType());

        return true;
    }
}
