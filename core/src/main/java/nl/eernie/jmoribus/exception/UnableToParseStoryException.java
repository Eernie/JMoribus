package nl.eernie.jmoribus.exception;

public class UnableToParseStoryException extends RuntimeException
{
    public UnableToParseStoryException(String message, Exception e)
    {
        super(message, e);
    }
}
