package nl.eernie.jmoribus.exception;

import java.util.List;

public class MissingVariablesException extends Exception
{
	public MissingVariablesException(List<String> missingRequiredVariables)
	{
		super("Missing variables: " + missingRequiredVariables);
	}
}
