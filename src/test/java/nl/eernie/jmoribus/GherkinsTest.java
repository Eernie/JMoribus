package nl.eernie.jmoribus;


import nl.eernie.jmoribus.GherkinsParser;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.*;
import nl.eernie.jmoribus.parser.GherkinsListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class GherkinsTest {
    @Test
    public void test() throws IOException {

        InputStream fileInputStream = getClass().getResourceAsStream("/Gherkins.story");
        GherkinsLexer lexer = new GherkinsLexer(new ANTLRInputStream(fileInputStream));

        CommonTokenStream token = new CommonTokenStream(lexer);
        GherkinsParser parser = new GherkinsParser(token);
        GherkinsListener listener = new GherkinsListener();
        parser.addParseListener(listener);
        ParseTree tree = parser.story();
        System.out.println(tree.toStringTree(parser));
        Story story = listener.getStory();


    }
}
