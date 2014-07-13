package nl.eernie.jmoribus.models;

import nl.eernie.jmoribus.StepType;

public class Step {
    private String value;
    private Scenario scenario;
    private StepType stepType;

    public Step(String value, StepType stepType) {
        this.value = value;
        this.stepType = stepType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public StepType getStepType() {
        return stepType;
    }

    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }
}
