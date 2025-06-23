package com.project.logger.utils;

import com.project.logger.enums.LogLevel;
import com.project.logger.enums.SinkType;
import com.project.logger.factory.LogConfigValidatorFactory;
import com.project.logger.service.LogConfigValidator;
import com.project.logger.service.Sink;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@RequiredArgsConstructor
public class LogConfig {

//    public static final String KEY_TIME_FORMAT = "ts_format";
//    public static final String KEY_LOG_LEVEL = "log_level";
//    public static final String KEY_SINK_TYPE = "sink_type";
//    public static final String KEY_FILE_LOCATION = "file_location";

    private static final String DEFAULT_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private static final LogLevel DEFAULT_LOG_LEVEL = LogLevel.INFO;
    private static final SinkType DEFAULT_SINK_TYPE = SinkType.FILE;
    private static final String DEFAULT_FILE_LOCATION = "logs/file.app.log";
    private static final long DEFAULT_FILE_SIZE_BYTE = 5 * 1024 * 1024;
    public static final long MAX_FILE_SIZE_BYTE = 10 * 1024 * 1024;

    private final String timeFormat;
    private final LogLevel logLevel;
    private final SinkType sinkType;
    private final String fileLocation;
    private final long fileSizeBytes;
    private Sink sink;


    public List<LogLevel> getEnabledLevels() {
        return Arrays.stream(LogLevel.values())
                .filter(l -> l.getSeverity() >= logLevel.getSeverity())
                .collect(Collectors.toList());
    }

    public boolean isValid() {
        LogConfigValidator validator = LogConfigValidatorFactory.getValidator(this);
        if (ObjectUtils.isEmpty(validator)) {
            System.out.println("Invalid sink type");
            return false;
        }

        return validator.isValid();
    }

    public static class LogConfigBuilder {
        public LogConfig build() {
            String timeFormat = StringUtils.isBlank(this.timeFormat) ? DEFAULT_TIME_FORMAT : this.timeFormat;
            LogLevel logLevel = ObjectUtils.isEmpty(this.logLevel) ? DEFAULT_LOG_LEVEL : this.logLevel;
            SinkType sinkType = ObjectUtils.isEmpty(this.sinkType) ? DEFAULT_SINK_TYPE : this.sinkType;
            String fileLocation = StringUtils.isBlank(this.fileLocation) ? DEFAULT_FILE_LOCATION : this.fileLocation;
            long fileSizeBytes = this.fileSizeBytes == 0 ? DEFAULT_FILE_SIZE_BYTE : this.fileSizeBytes;

            return new LogConfig(timeFormat, logLevel, sinkType, fileLocation, fileSizeBytes);
        }
    }
}
