package nl.eernie.jmoribus.parser;

import java.io.InputStream;

public class ParseableStory {

    private InputStream stream;
    private String title;
    private String uniqueIdentifier;

    public ParseableStory(InputStream stream, String uniqueIdentifier, String title) {
        this.stream = stream;
        this.title = title;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public InputStream getStream() {
        return stream;
    }

    public String getTitle() {
        return title;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }
}
