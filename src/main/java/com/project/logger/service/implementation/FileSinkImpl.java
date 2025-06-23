package com.project.logger.service.implementation;

import com.project.logger.enums.LogLevel;
import com.project.logger.models.Message;
import com.project.logger.service.Sink;
import lombok.Builder;
import lombok.Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@Data
@Builder
public class FileSinkImpl implements Sink {
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

        String compressedName = String.format(
                "%s_%d_%d%s",
                logFile,
                System.currentTimeMillis(),
                sequence,
                ".gz"
        );

        try (
                FileInputStream fis = new FileInputStream(logFile.toFile());
                FileOutputStream fos = new FileOutputStream(compressedName);
                GZIPOutputStream gzipOS = new GZIPOutputStream(fos)
        ) {
            fis.transferTo(gzipOS);
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
