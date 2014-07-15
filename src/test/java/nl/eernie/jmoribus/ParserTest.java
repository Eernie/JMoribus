package nl.eernie.jmoribus;

import nl.eernie.jmoribus.parser.StoryParser;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ParserTest {

    @Test
    public void testParse() throws FileNotFoundException {
        InputStream fileInputStream = getClass().getResourceAsStream("/test.story");
        StoryParser.parseStory(fileInputStream);
    }
}
