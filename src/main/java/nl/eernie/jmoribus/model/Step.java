package nl.eernie.jmoribus.model;

public class Step {
    private String value;
    private Scenario scenario;
    private Feature.StepType stepType;

    public Step(String value, Feature.StepType stepType) {
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

    public Feature.StepType getStepType() {
        return stepType;
    }

    public void setStepType(Feature.StepType stepType) {
        this.stepType = stepType;
    }
}
