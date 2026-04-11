package com.opscopilot.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.opscopilot.backend.model.Rule;

@Service
public class RuleEngineService {

    public Optional<Rule> matchRule(String log) {

        List<Rule> rules = getRules();

        for (Rule rule : rules) {
            if (log.toLowerCase().matches(".*" + rule.getPattern() + ".*")) {
                return Optional.of(rule);
            }
        }
        return Optional.empty();
    }

   private List<Rule> getRules() {

    List<Rule> rules = new ArrayList<>();

    // 🔹 Rule 1: Docker permission
    Rule dockerRule = new Rule();
    dockerRule.setPattern("permission denied.*docker.sock");
    dockerRule.setSummary("Docker permission issue");
    dockerRule.setRootCause("User does not have access to Docker daemon");
    dockerRule.setFixSteps(List.of(
            "sudo usermod -aG docker <user>",
            "sudo systemctl restart docker"
    ));
    dockerRule.setSeverity("HIGH");
    rules.add(dockerRule);

    // 🔹 Rule 2: Command not found
    Rule commandRule = new Rule();
    commandRule.setPattern("command not found");
    commandRule.setSummary("Command not found");
    commandRule.setRootCause("The required command or tool is not installed");
    commandRule.setFixSteps(List.of(
            "Install the required package",
            "Check PATH environment variable"
    ));
    commandRule.setSeverity("HIGH");
    rules.add(commandRule);

    // 🔹 Rule 3: Exit code 127
    Rule exitRule = new Rule();
    exitRule.setPattern("exit code 127");
    exitRule.setSummary("Command execution failed");
    exitRule.setRootCause("Command not found or not executable");
    exitRule.setFixSteps(List.of(
            "Verify the command exists",
            "Install missing dependency"
    ));
    exitRule.setSeverity("HIGH");
    rules.add(exitRule);

    // 🔹 Rule 4: Maven build failure
    Rule mavenRule = new Rule();
    mavenRule.setPattern("build failure");
    mavenRule.setSummary("Maven build failed");
    mavenRule.setRootCause("Compilation error or test failure");
    mavenRule.setFixSteps(List.of(
            "Check logs above for errors",
            "Run mvn clean install locally"
    ));
    mavenRule.setSeverity("MEDIUM");
    rules.add(mavenRule);

    return rules;
}
}
