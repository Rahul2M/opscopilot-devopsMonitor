package com.opscopilot.backend.model;

import java.util.List;

public class AnalysisResponse {

    private String summary;
    private String rootCause;
    private List<String> fixSteps;
    private List<String> preventionTips;
    private String severity;

    // getters and setters
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
    public List<String> getPreventionTips() {
        return preventionTips;
    }
    public void setPreventionTips(List<String> preventionTips) {
        this.preventionTips = preventionTips;
    }
    public String getSeverity() {
        return severity;
    }
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
}
