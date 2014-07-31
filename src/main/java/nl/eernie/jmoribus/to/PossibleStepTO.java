package nl.eernie.jmoribus.to;

import nl.eernie.jmoribus.model.StepType;

public class PossibleStepTO {

    private String step;
    private StepType stepType;
    private String[] categories;

    public PossibleStepTO(String step, StepType stepType, String[] categories) {
        this.step = step;
        this.stepType = stepType;
        this.categories = categories;
    }

    public String getStep() {
        return step;
    }

    public StepType getStepType() {
        return stepType;
    }

    public String[] getCategories() {
        return categories;
    }
}
