package nl.eernie.jmoribus.parser;

import junit.framework.TestCase;
import nl.eernie.jmoribus.model.Table;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
}