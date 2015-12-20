package nl.eernie.jmoribus.context;

public abstract class AbstractContextProvider<T extends RunContext> implements ContextProvider<T>
{
	private ThreadLocal<T> tlRunContext = new ThreadLocal<T>()
	{
		@Override
		protected T initialValue()
		{
			return getInitialValue();
		}
	};

	@Override
	public T get()
	{
		return tlRunContext.get();
	}

	protected abstract T getInitialValue();
}
