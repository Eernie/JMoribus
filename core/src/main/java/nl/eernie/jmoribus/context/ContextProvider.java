package nl.eernie.jmoribus.context;

public interface ContextProvider<T extends RunContext>
{
	T get();
}
