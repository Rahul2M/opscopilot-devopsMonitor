package com.opscopilot.backend.model;

import java.util.List;

public class Rule {

    private String pattern;
    private String summary;
    private String rootCause;
    private List<String> fixSteps;
    private String severity;
    
    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getRootCause() {
        return rootCause;
    }
    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }
    public List<String> getFixSteps() {
        return fixSteps;
    }
    public void setFixSteps(List<String> fixSteps) {
        this.fixSteps = fixSteps;
    }
    public String getSeverity() {
        return severity;
    }
    public void setSeverity(String severity) {
        this.severity = severity;
    }

   
    
}