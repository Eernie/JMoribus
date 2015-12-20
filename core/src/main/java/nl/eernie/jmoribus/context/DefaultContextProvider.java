package nl.eernie.jmoribus.context;

public class DefaultContextProvider extends AbstractContextProvider<RunContext>
{
	@Override
	protected RunContext getInitialValue()
	{
		return new RunContext();
	}
}
