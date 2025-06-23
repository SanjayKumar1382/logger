package com.project.logger.utils;

import com.project.logger.enums.LogLevel;
import com.project.logger.models.Message;
import com.project.logger.service.Sink;
import lombok.Data;

import java.util.*;


@Data
public class SinkRouter {

    private final Map<LogLevel, List<Sink>> routeMap;
    private final Set<Sink> sinkSet;

    SinkRouter() {
        routeMap = new HashMap<>();
        sinkSet = new HashSet<>();
    }

    public void register(Sink sink) {
        for (LogLevel level : sink.getSupportedLevels()) {
            routeMap.computeIfAbsent(level, k -> new ArrayList<>()).add(sink);
        }
        sinkSet.add(sink);
    }

    public void route(Message message) {
        for (LogLevel level : routeMap.keySet()) {
            if (message.getLevel().getSeverity() == level.getSeverity()) {
                for (Sink sink : routeMap.get(level)) {
                    sink.write(message);
                }
            }
        }
    }

    public void flush() {
        for (Sink sink : sinkSet) {
            sink.flush();
        }
    }
}
