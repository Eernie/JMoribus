package nl.eernie.jmoribus.context;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunContext
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RunContext.class);

    private final Map<String, String> properties = new HashMap<>();
    private Map<String, String> currentExampleRow;

    public String get(String variableName)
    {
        return properties.get(variableName);
    }

    public void set(String variableName, String value)
    {
        if (properties.containsKey(variableName))
        {
            LOGGER.warn("Overriding variable [{}] and value [{}] with new value [{}]", variableName, get(variableName), value);
        }
        properties.put(variableName, value);
    }

    public boolean isVariableSet(String variableName)
    {
        return properties.containsKey(variableName);
    }

    public void setCurrentExampleRow(Map<String, String> exampleRow)
    {
        currentExampleRow = exampleRow;
    }

    public void removeCurrentExampleRow(Map<String, String> exampleRow)
    {
        currentExampleRow = null;
    }

    public Map<String, String> getCurrentExampleRow()
    {
        return currentExampleRow;
    }
}
