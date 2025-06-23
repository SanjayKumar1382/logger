package com.project.logger.service.implementation;

import com.project.logger.enums.LogLevel;
import com.project.logger.models.Message;
import com.project.logger.service.Sink;
import lombok.Builder;
import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
public class ConsoleSinkImpl implements Sink {
    private final Path logFile;
    private final List<LogLevel> levels;
    private final long maxSize;
    private final DateTimeFormatter formatter;
    private long sequence;

    @Override
    public synchronized void write(Message message) {
        try {
            if (isRotationRequired(logFile, maxSize)) {
                rotate();
            }
            String logEntry = String.format("%s [%s] [%s] %s%n",
                    message.getLevel().name(),
                    message.getTimestamp().format(formatter),
                    message.getNamespace().name(),
                    message.getContent());
            Files.createDirectories(logFile.getParent());
            Files.write(
                    logFile,
                    logEntry.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rotate() throws IOException {
        if (!Files.exists(logFile)) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile.toFile()))) {
            String line;
            System.out.println("Log for sequence : " + sequence);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        Files.write(logFile, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
        sequence++;
    }

    @Override
    public List<LogLevel> getSupportedLevels() {
        return levels;
    }

    @Override
    public void flush() {
        try {
            rotate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
