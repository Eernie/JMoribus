package nl.eernie.jmoribus.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class StepParser {


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
        this(StepParser.DEFAULT_PREFIX, DEFAULT_CHARACTER_CLASS);
    }

    public StepParser(String prefix, String characterClass) {
        this.prefix = prefix;
        this.characterClass = characterClass;
    }

    public RegexStepMatcher parseStep(String stepPattern) {
        String escapingPunctuation = escapingPunctuation(stepPattern);
        List<Parameter> parameters = findParameters(escapingPunctuation);
        Pattern regexPattern = buildPattern(escapingPunctuation, parameters);
        return new RegexStepMatcher(regexPattern);
    }

    private Pattern buildPattern(String stepPattern, List<Parameter> parameters) {
        String regex = parameterCapturingRegex(stepPattern, parameters);
        return Pattern.compile(regex, Pattern.DOTALL);
    }

    private List<Parameter> findParameters(String pattern) {
        List<Parameter> parameters = new ArrayList<>();
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
        return pattern.replaceAll("([\\[\\]\\{\\}\\?\\^\\.\\*\\(\\)\\+\\\\])", "\\\\$1");
    }

    private String ignoringWhitespace(String pattern) {
        return pattern.replaceAll("\\s+", "\\\\s+");
    }

    private String parameterCapturingRegex(String stepPattern, List<Parameter> parameters) {
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
