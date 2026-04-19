package com.opscopilot.backend.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogFileService {

    public List<String> readLogs() {
        try {
            ClassPathResource resource = new ClassPathResource("logs/sample.log");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream())
            );

            return reader.lines().collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error reading log file: " + e.getMessage());
        }
    }
}