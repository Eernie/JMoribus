package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Step  {

    private String value;
    private StepType stepType;
    private StepTeller stepTeller;
    private List<Table> tables = new ArrayList<>();

    public Step(String value, StepType stepType) {
        this.value = value;
        this.stepType = stepType;
    }

    public Step(Table table, StepType stepType){
        tables.add(table);
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

    public List<Table> getTables() {
        return tables;
    }
}
