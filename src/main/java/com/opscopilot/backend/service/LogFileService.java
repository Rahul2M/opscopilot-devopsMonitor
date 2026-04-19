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

            // ✅ If file NOT found → fallback
            if (!resource.exists()) {
                System.out.println("⚠️ File NOT found in Render, using fallback logs");

                return List.of(
                        "exit code 127",
                        "disk space issue",
                        "unknown error"
                );
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream())
            );

            return reader.lines().collect(Collectors.toList());

        } catch (Exception e) {

            System.out.println("❌ ERROR reading log file: " + e.getMessage());

            // ✅ Never crash → fallback
            return List.of(
                    "exit code 127",
                    "fallback error"
            );
        }
    }
}