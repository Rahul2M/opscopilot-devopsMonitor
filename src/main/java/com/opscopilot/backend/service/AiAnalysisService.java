package com.opscopilot.backend.service;

import java.util.List;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.opscopilot.backend.model.AnalysisResponse;
@Service
public class AiAnalysisService {

    public AnalysisResponse analyzeWithAI(String log) {

        AnalysisResponse res = new AnalysisResponse();
        String lowerLog = log.toLowerCase();

        // 🔹 Case 1: Timeout issue
        if (lowerLog.contains("timeout")) {
            res.setSummary("Timeout occurred");
            res.setRootCause("Service did not respond in time");
            res.setFixSteps(List.of(
                    "Check service availability",
                    "Increase timeout settings",
                    "Verify network latency"
            ));
            res.setSeverity("HIGH");
        }

        // 🔹 Case 2: Connection refused
        else if (lowerLog.contains("connection refused")) {
            res.setSummary("Connection refused");
            res.setRootCause("Target service is not reachable");
            res.setFixSteps(List.of(
                    "Check if service is running",
                    "Verify port configuration",
                    "Check firewall rules"
            ));
            res.setSeverity("HIGH");
        }

        // 🔹 Case 3: Disk full
        else if (lowerLog.contains("no space left")) {
            res.setSummary("Disk space issue");
            res.setRootCause("Server disk is full");
            res.setFixSteps(List.of(
                    "Clean unnecessary files",
                    "Check disk usage using df -h",
                    "Increase disk size"
            ));
            res.setSeverity("HIGH");
        }

        // 🔹 Case 4: Port already in use
        else if (lowerLog.contains("address already in use")) {
            res.setSummary("Port already in use");
            res.setRootCause("Another process is using the port");
            res.setFixSteps(List.of(
                    "Find process using port (netstat -tulnp)",
                    "Kill the process",
                    "Use a different port"
            ));
            res.setSeverity("MEDIUM");
        }

        // 🔹 Default case (unknown error)
        else {
            res.setSummary("Unknown error detected");
            res.setRootCause("Could not identify exact issue from logs");
            res.setFixSteps(List.of(
                    "Check full logs carefully",
                    "Verify configurations",
                    "Retry pipeline",
                    "Check dependencies"
            ));
            res.setSeverity("MEDIUM");
        }

        // 🔹 Common prevention tips
        res.setPreventionTips(List.of(
                "Enable detailed logging",
                "Monitor system metrics",
                "Use health checks",
                "Automate alerts"
        ));

        return res;
    }
}