package nl.eernie.jmoribus.parser;

import java.io.InputStream;

public class ParseableStory {

    private InputStream stream;
    private String uniqueIdentifier;

    public ParseableStory(InputStream stream, String uniqueIdentifier) {
        this.stream = stream;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public InputStream getStream() {
        return stream;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }
}
