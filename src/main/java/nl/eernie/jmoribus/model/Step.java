package nl.eernie.jmoribus.model;

public class Step {
    private String value;
    private StepTeller stepTeller;
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

    public StepTeller getStepTeller() {
        return stepTeller;
    }

    public void setStepTeller(StepTeller stepTeller) {
        this.stepTeller = stepTeller;
    }

    public StepType getStepType() {
        return stepType;
    }

    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }
}
