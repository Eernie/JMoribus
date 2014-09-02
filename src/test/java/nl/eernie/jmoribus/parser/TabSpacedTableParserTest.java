package nl.eernie.jmoribus.parser;

import junit.framework.TestCase;
import nl.eernie.jmoribus.model.StepType;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.model.Table;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TabSpacedTableParserTest extends TestCase {

    @Test
    public void testParse() throws Exception {

        List<String> storyLines = new ArrayList<String>();

        storyLines.add("Kolom a\tKolom b");
        storyLines.add("Waarde a\tWaarde b");

        TabSpacedTableParser tstp = new TabSpacedTableParser();
        Table parseResult = tstp.parse(storyLines);

        assertEquals("Kolom a", parseResult.getHeader().get(0));
        assertEquals("Kolom b", parseResult.getHeader().get(1));

        assertEquals("Waarde a", parseResult.getRows().get(0).get(0));
        assertEquals("Waarde b", parseResult.getRows().get(0).get(1));
    }

    @Test
    public void testNullTable()
    {
        TabSpacedTableParser tstp = new TabSpacedTableParser();
        Table parseResult = tstp.parse(null);
        assertNull(parseResult);
    }

    @Test
    public void testTableWithOnlyHeader()
    {
        List<String> storyLines = new ArrayList<String>();

        storyLines.add("Kolom a\tKolom b");

        TabSpacedTableParser tstp = new TabSpacedTableParser();
        Table parseResult = tstp.parse(storyLines);

        assertEquals("Kolom a", parseResult.getHeader().get(0));
        assertEquals("Kolom b", parseResult.getHeader().get(1));

        assertEquals(true, parseResult.getRows().isEmpty());
    }

    @Test
    public void testParametrizedStory() throws IOException {
        InputStream fileInputStream = getClass().getResourceAsStream("/storyWithExampleTable.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "Parametrized story", "storyWithExampleTable.story");
        Story story = StoryParser.parseStory(parseableStory);
        Assert.assertEquals(story.getScenarios().size(), 2);
        int numberOfSteps = story.getScenarios().get(0).getSteps().size();
        Assert.assertNotNull(story.getScenarios().get(0).getExamplesTable());
    }
}