package com.opscopilot.backend.controller;


// import org.apache.el.stream.Optional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public AnalysisResponse analyzeLog(@RequestBody String log) {

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
    res.setFixSteps(rule.getFixSteps());
    res.setSeverity(rule.getSeverity());

    return res;
}
}