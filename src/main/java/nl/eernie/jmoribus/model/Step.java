package nl.eernie.jmoribus.model;

import java.util.ArrayList;
import java.util.List;

public class Step  {

    private StepType stepType;
    private List<StepLine> stepLines = new ArrayList<>();

    public Step(StepType stepType) {
        this.stepType = stepType;
    }

    public StepType getStepType() {
        return stepType;
    }

    public StepLine getFirstStepLine()
    {
        return stepLines.get(0);
    }

    public List<StepLine> getStepLines()
    {
        return stepLines;
    }
}
