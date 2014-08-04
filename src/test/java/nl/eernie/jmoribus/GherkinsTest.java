package nl.eernie.jmoribus;


import nl.eernie.jmoribus.GherkinsParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class GherkinsTest {
    @Test
    public void test() throws IOException {

        InputStream fileInputStream = getClass().getResourceAsStream("/test.story");
        nl.eernie.jmoribus.GherkinsLexer lexer = new nl.eernie.jmoribus.GherkinsLexer(new ANTLRInputStream(fileInputStream));

        CommonTokenStream token = new CommonTokenStream(lexer);
        GherkinsParser parser = new GherkinsParser(token);
        GherkinsParser.FeatureContext feature = parser.feature();
        GherkinsParser.BackgroundContext background = parser.background();
        parser.scenario();

    }
}
