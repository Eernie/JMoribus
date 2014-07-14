package nl.eernie.jmoribus.matcher;

import nl.eernie.jmoribus.matcher.Parameter;
import nl.eernie.jmoribus.matcher.RegexStepMatcher;
import nl.eernie.jmoribus.model.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by erwin on 10/07/14.
 */
public class StepParser {


    /**
     * The default prefix to identify parameter names
     */
    private static final String DEFAULT_PREFIX = "$";
    /**
     * The default character class to match the parameter names.
     */
    private static final String DEFAULT_CHARACTER_CLASS = "[\\p{L}\\p{N}\\p{Pc}]";

    private final String prefix;
    private final String characterClass;

    public StepParser() {
        this(DEFAULT_PREFIX);
    }

    public StepParser(String defaultPrefix) {
        this(defaultPrefix, DEFAULT_CHARACTER_CLASS);
    }

    public StepParser(String prefix, String characterClass) {
        this.prefix = prefix;
        this.characterClass = characterClass;
    }

    public RegexStepMatcher parseStep(Feature.StepType stepType, String stepPattern) {
        String escapingPunctuation = escapingPunctuation(stepPattern);
        List<Parameter> parameters = findParameters(escapingPunctuation);
        Pattern regexPattern = buildPattern(escapingPunctuation, parameters);
        return new RegexStepMatcher(stepType, escapingPunctuation, regexPattern,
                parameterNames(parameters));
    }

    private Pattern buildPattern(String stepPattern, List<Parameter> parameters) {
        return Pattern.compile(
                parameterCapturingRegex(stepPattern, parameters),
                Pattern.DOTALL);
    }

    private String[] parameterNames(List<Parameter> parameters) {
        List<String> names = new ArrayList<String>();
        for (Parameter parameter : parameters) {
            names.add(parameter.getName());
        }
        return names.toArray(new String[names.size()]);
    }

    private List<Parameter> findParameters(String pattern) {
        List<Parameter> parameters = new ArrayList<Parameter>();
        Matcher findingAllParameterNames = findingAllParameterNames().matcher(
                pattern);
        while (findingAllParameterNames.find()) {
            parameters.add(new Parameter(pattern, findingAllParameterNames
                    .start(), findingAllParameterNames.end(),
                    findingAllParameterNames.group(2), prefix));
        }
        return parameters;
    }

    private Pattern findingAllParameterNames() {
        return Pattern.compile("(\\" + prefix + characterClass + "*)(\\W|\\Z)",
                Pattern.DOTALL);
    }

    private String escapingPunctuation(String pattern) {
        return pattern.replaceAll("([\\[\\]\\{\\}\\?\\^\\.\\*\\(\\)\\+\\\\])",
                "\\\\$1");
    }

    private String ignoringWhitespace(String pattern) {
        return pattern.replaceAll("\\s+", "\\\\s+");
    }

    private String parameterCapturingRegex(String stepPattern,
                                           List<Parameter> parameters) {
        String regex = stepPattern;
        String capture = "(.*)";
        for (int i = parameters.size(); i > 0; i--) {
            Parameter parameter = parameters.get(i - 1);
            String start = regex.substring(0, parameter.getStart());
            String end = regex.substring(parameter.getEnd());
            String whitespaceIfAny = parameter.getWhitespaceIfAny();
            regex = start + capture + whitespaceIfAny + end;
        }
        return ignoringWhitespace(regex);
    }
}
