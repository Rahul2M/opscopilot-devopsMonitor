package com.opscopilot.backend.service;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class LogFileService {

    public List<String> readLogs() throws IOException {
        return Files.readAllLines(Path.of("logs/sample.log"));
    }
}