package nl.eernie.jmoribus.parser;

import nl.eernie.jmoribus.model.Story;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoryParser {

    public static List<Story> parseStories(List<InputStream> streams){
        List<Story> stories = new ArrayList<Story>();
        for (InputStream stream : streams) {
            stories.add(parseStory(stream));
        }
        return stories;
    }

    public static Story parseStory(InputStream stream) {
        try {
            StringWriter stringWriter = new StringWriter();
            IOUtils.copy(stream,stringWriter);
            String completeStory = stringWriter.toString();

            System.out.println(completeStory);

            Pattern pattern = Pattern.compile("(^Given|^Then|^When|^Feature|^Scenario).*$"); //TODO: refactor, this gives problems with multi row steps
            Matcher matcher = pattern.matcher(completeStory);
            while (matcher.find()){
                System.out.println(matcher.group());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
