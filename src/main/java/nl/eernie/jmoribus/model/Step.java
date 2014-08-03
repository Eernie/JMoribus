package nl.eernie.jmoribus.model;

public class Step  {


    private String value;
    private StepType stepType;
    private StepTeller stepTeller;

    public Step(String value, StepType stepType) {
        this.value = value;
        this.stepType = stepType;
    }

    public StepTeller getStepTeller() {
        return stepTeller;
    }

    public void setStepTeller(StepTeller stepTeller) {
        this.stepTeller = stepTeller;
    }

    public String getValue() {
        return value;
    }

    public StepType getStepType() {
        return stepType;
    }
}
