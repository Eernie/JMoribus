package nl.eernie.jmoribus.context;

import java.util.HashMap;
import java.util.Map;

public class DefaultContextProvider implements ContextProvider
{
    private ThreadLocal<java.util.Map<String, String>> tlVariablesMap = new ThreadLocal<Map<String, String>>()
    {
        @Override
        protected Map<String, String> initialValue()
        {
            return new HashMap<>();
        }
    };

    @Override
    public String get(String variableName)
    {
        return tlVariablesMap.get().get(variableName);
    }

    @Override
    public void set(String variableName, String value)
    {
        tlVariablesMap.get().put(variableName, value);
    }

    @Override
    public boolean isVariableSet(String variableName)
    {
        return tlVariablesMap.get().containsKey(variableName);
    }

    @Override
    public void setCurrentExampleRow(Map<String, String> exampleRow)
    {

    }
}
