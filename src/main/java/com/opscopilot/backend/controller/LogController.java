package com.opscopilot.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.opscopilot.backend.model.AnalysisResponse;
import com.opscopilot.backend.model.Rule;
import com.opscopilot.backend.service.AiAnalysisService;
import com.opscopilot.backend.service.RuleEngineService;
import com.opscopilot.backend.service.LogFileService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LogController {

    @Autowired
    private RuleEngineService ruleEngine;

    @Autowired
    private AiAnalysisService aiService;

    @Autowired
    private LogFileService logFileService;

    // 🔹 Existing API (Manual Input)
    @PostMapping("/analyze")
    public AnalysisResponse analyzeLog(@RequestBody Map<String, String> request) {

        String log = request.get("log");

        // Safety check
        if (log == null || log.trim().isEmpty()) {
            return buildErrorResponse();
        }

        Optional<Rule> rule = ruleEngine.matchRule(log);

        if (rule.isPresent()) {
            return convertRuleToResponse(rule.get());
        }

        return aiService.analyzeWithAI(log);
    }

    // 🔥 NEW API (Log File Integration)
    @GetMapping("/logs/analyze")
    public List<AnalysisResponse> analyzeLogsFromFile() throws IOException {

        List<String> logs = logFileService.readLogs();

        List<AnalysisResponse> responses = new ArrayList<>();

        for (String log : logs) {

            if (log == null || log.trim().isEmpty()) continue;

            Optional<Rule> rule = ruleEngine.matchRule(log);

            if (rule.isPresent()) {
                responses.add(convertRuleToResponse(rule.get()));
            } else {
                responses.add(aiService.analyzeWithAI(log));
            }
        }

        return responses;
    }

    private AnalysisResponse convertRuleToResponse(Rule rule) {

        AnalysisResponse res = new AnalysisResponse();

        res.setSummary(rule.getSummary());
        res.setRootCause(rule.getRootCause());
        res.setFixSteps(rule.getFixSteps() != null ? rule.getFixSteps() : List.of());
        res.setPreventionTips(List.of("Monitor logs regularly", "Add alerts for similar issues"));
        res.setSeverity(rule.getSeverity());

        return res;
    }

    private AnalysisResponse buildErrorResponse() {
        AnalysisResponse res = new AnalysisResponse();

        res.setSummary("Invalid input");
        res.setRootCause("Log input is empty or missing");
        res.setFixSteps(List.of("Provide valid log input"));
        res.setPreventionTips(List.of("Ensure logs are not empty before submitting"));
        res.setSeverity("LOW");

        return res;
    }
}