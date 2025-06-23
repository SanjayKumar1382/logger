package com.project.logger.utils;

import com.project.logger.enums.LogLevel;
import com.project.logger.enums.LogNameSpace;
import com.project.logger.factory.SinkFactory;
import com.project.logger.models.Message;
import com.project.logger.service.Sink;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;

public class Logger {
    private final SinkRouter router;

    public Logger(LogConfig... logConfigs) {
        router = new SinkRouter();
        for (LogConfig config : logConfigs) {
            if (!config.isValid()) {
                System.out.println("Invalid log config for log type : " + config.getSinkType());
                continue;
            }
            Sink sink = SinkFactory.getSink(config);
            if (ObjectUtils.isEmpty(sink)) {
                System.out.println("Unsupported sink type : " + config.getSinkType());
                continue;
            }
            router.register(sink);
            System.out.println("Sink registered for type: " + config.getSinkType());
        }
        System.out.println("Route Map : " + router.getRouteMap());
    }

    public void log(LogLevel level, String content, LogNameSpace namespace) {
        Message message = new Message(content, level, namespace, LocalDateTime.now());
        System.out.println("Routing the message to the sink");
        router.route(message);
    }

    public void flush() {
        System.out.println("Flushing the message to the sink");
        router.flush();
    }
}
