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
        StringBuffer buffer = new StringBuffer();
        for(StepLine stepLine: stepLines){
            if(StringUtils.isNotBlank(buffer.toString())){
                buffer.append(" ");
            }
            if(stepLine instanceof Table){
                int index = stepLines.indexOf(stepLine);
                buffer.append("TABLE"+index);
            }else{
                buffer.append(stepLine.getText());
            }
        }
        return buffer.toString();
    }
}
