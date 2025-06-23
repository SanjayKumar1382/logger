package com.project.logger.models;

import com.project.logger.enums.LogLevel;
import com.project.logger.enums.LogNameSpace;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private final String content;
    private final LogLevel level;
    private final LogNameSpace namespace;
    private final LocalDateTime timestamp;
}
