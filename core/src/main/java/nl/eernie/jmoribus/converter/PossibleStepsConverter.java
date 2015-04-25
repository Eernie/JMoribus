package nl.eernie.jmoribus.converter;

import nl.eernie.jmoribus.matcher.PossibleStep;
import nl.eernie.jmoribus.to.PossibleStepTO;

import java.util.ArrayList;
import java.util.List;

public final class PossibleStepsConverter
{
    private PossibleStepsConverter()
    {
    }

    public static List<PossibleStepTO> convert(List<PossibleStep> source)
    {
        ArrayList<PossibleStepTO> target = new ArrayList<>(source.size());
        for (PossibleStep possibleStep : source)
        {
            target.add(convert(possibleStep));
        }
        return target;
    }

    private static PossibleStepTO convert(PossibleStep possibleStep)
    {
        return new PossibleStepTO(possibleStep.getStep(), possibleStep.getStepType(), possibleStep.getCategories(), possibleStep.getRequiredVariables(), possibleStep.getOutputVariables());
    }
}
