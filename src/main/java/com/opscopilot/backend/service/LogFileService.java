package com.opscopilot.backend.service;




import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class LogFileService {

    public List<String> readLogs() {

        try {
            InputStream input = getClass()
                    .getClassLoader()
                    .getResourceAsStream("logs/sample.log");

            if (input == null) {
                throw new RuntimeException("Log file not found in resources!");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            return reader.lines().collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error reading log file: " + e.getMessage());
        }
    }
}