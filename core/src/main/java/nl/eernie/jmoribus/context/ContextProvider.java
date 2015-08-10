package nl.eernie.jmoribus.context;

import java.util.Map;

public interface ContextProvider
{

    String get(String variableName);

    void set(String variableName, String value);

    boolean isVariableSet(String variableName);

    void setCurrentExampleRow(Map<String, String> exampleRow);
}
