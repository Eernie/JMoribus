package nl.eernie.jmoribus.exception;

import java.io.IOException;

public class UnableToParseStoryException extends RuntimeException {
    public UnableToParseStoryException(String message, Exception e) {
        super(message,e);
    }
}
