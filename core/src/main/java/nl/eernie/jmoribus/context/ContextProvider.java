package nl.eernie.jmoribus.context;

public interface ContextProvider
{

    String get(String variableName);

    void set(String variableName, String value);

    boolean isVariableSet(String variableName);
}
