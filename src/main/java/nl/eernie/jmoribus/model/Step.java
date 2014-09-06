package nl.eernie.jmoribus.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Step {

    private StepType stepType;
    private List<StepLine> stepLines = new ArrayList<>();
    private StepContainer stepContainer;

    public Step(StepType stepType) {
        this.stepType = stepType;
    }

    public StepType getStepType() {
        return stepType;
    }

    public StepLine getFirstStepLine() {
        return stepLines.get(0);
    }

    public List<StepLine> getStepLines() {
        return stepLines;
    }

    public StepContainer getStepContainer() {
        return stepContainer;
    }

    public void setStepContainer(StepContainer stepContainer) {
        this.stepContainer = stepContainer;
    }

    public String getCombinedStepLines() {
        StringBuilder builder = new StringBuilder();
        for (StepLine stepLine : stepLines) {
            if (StringUtils.isNotBlank(builder.toString())) {
                builder.append(" ");
            }
            if (stepLine instanceof Table) {
                int index = stepLines.indexOf(stepLine);
                builder.append("TABLE").append(index);
            } else {
                builder.append(stepLine.getText());
            }
        }
        return builder.toString();
    }
}
