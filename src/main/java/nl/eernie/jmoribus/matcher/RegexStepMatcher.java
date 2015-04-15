package nl.eernie.jmoribus.matcher;

import nl.eernie.jmoribus.model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexStepMatcher
{
    private final Pattern regexPattern;
    private Matcher matcher;

    public RegexStepMatcher(Pattern regexPattern)
    {
        this.regexPattern = regexPattern;
    }

    public boolean matches(String stepWithoutStartingWord)
    {
        matcher(stepWithoutStartingWord);
        return matcher.matches();
    }

    private void matcher(String patternToMatch)
    {
        matcher = regexPattern.matcher(patternToMatch);
    }

    public List<String> getParameterValues(Step step)
    {
        Matcher matcher = regexPattern.matcher(step.getCombinedStepLines());
        matcher.find();
        List<String> parameters = new ArrayList<>();
        if (matcher.groupCount() > 0)
        {
            for (int i = 1; i <= matcher.groupCount(); i++)
            {
                parameters.add(matcher.group(i));
            }
        }
        return parameters;
    }
}
