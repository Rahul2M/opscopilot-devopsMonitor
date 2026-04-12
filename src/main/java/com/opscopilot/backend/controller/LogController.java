package com.opscopilot.backend.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.opscopilot.backend.model.AnalysisResponse;
import com.opscopilot.backend.model.Rule;
import com.opscopilot.backend.service.AiAnalysisService;
import com.opscopilot.backend.service.RuleEngineService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LogController {

    @Autowired
    private RuleEngineService ruleEngine;

    @Autowired
    private AiAnalysisService aiService;

    @PostMapping("/analyze")
    public AnalysisResponse analyzeLog(@RequestBody Map<String, String> request) {

        String log = request.get("log");

        // 🔴 Safety check
        if (log == null || log.trim().isEmpty()) {
            return buildErrorResponse();
        }

        Optional<Rule> rule = ruleEngine.matchRule(log);

        if (rule.isPresent()) {
            return convertRuleToResponse(rule.get());
        }

        return aiService.analyzeWithAI(log);
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