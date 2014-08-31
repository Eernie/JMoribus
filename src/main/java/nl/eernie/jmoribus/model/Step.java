package nl.eernie.jmoribus.model;

import org.apache.commons.lang3.StringUtils;

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

    public String getCombinedStepLines(){
        String combined = "";
        for(StepLine stepLine: stepLines){
            if(StringUtils.isNotBlank(combined)){
                combined = combined + " ";
            }
            if(stepLine instanceof Table){
                int index = stepLines.indexOf(stepLine);
                combined = combined + "TABLE"+index;
            }else{
                combined = combined + stepLine.getText();
            }
        }
        return combined;
    }
}
