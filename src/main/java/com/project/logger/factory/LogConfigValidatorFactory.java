package com.project.logger.factory;

import com.project.logger.service.LogConfigValidator;
import com.project.logger.service.implementation.ConsoleLogConfigValidator;
import com.project.logger.service.implementation.CustomLogConfigValidator;
import com.project.logger.service.implementation.FileLogConfigValidator;
import com.project.logger.utils.LogConfig;

public class LogConfigValidatorFactory {

    public static LogConfigValidator getValidator(LogConfig config) {
        switch (config.getSinkType()) {
            case FILE:
                return FileLogConfigValidator.builder()
                        .logConfig(config)
                        .build();
            case CONSOLE:
                return ConsoleLogConfigValidator.builder()
                        .logConfig(config)
                        .build();
            case CUSTOM:
                return CustomLogConfigValidator.builder()
                        .logConfig(config)
                        .build();
            default:
                return null;
        }
    }
}
