package nl.eernie.jmoribus;

import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.Story;
import nl.eernie.jmoribus.parser.ParseableStory;
import nl.eernie.jmoribus.parser.StoryParser;
import nl.eernie.jmoribus.reporter.DefaultTestReporter;
import nl.eernie.jmoribus.reporter.Reporter;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SeleniumDriverTest {

    @Test
    public void testMethodSignature() {
        InputStream fileInputStream = getClass().getResourceAsStream("/selenium.story");
        ParseableStory parseableStory = new ParseableStory(fileInputStream, "selenium.story");

        Story story = StoryParser.parseStory(parseableStory);

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        Reporter reporter = Mockito.mock(DefaultTestReporter.class);
        defaultConfiguration.addReporter(reporter);
        defaultConfiguration.setWebDriver(new WebdriverDummy());
        defaultConfiguration.addSteps(Arrays.<Object>asList(new SeleniumSteps()));

        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        jMoribus.runStories(Arrays.asList(story));

        Mockito.verify(reporter, new Times(0)).errorStep(Mockito.<Step>any(), Mockito.<Exception>any());
    }

    class WebdriverDummy implements WebDriver {
        @Override
        public void get(String url) {

        }

        @Override
        public String getCurrentUrl() {
            return null;
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public List<WebElement> findElements(By by) {
            return null;
        }

        @Override
        public WebElement findElement(By by) {
            return null;
        }

        @Override
        public String getPageSource() {
            return null;
        }

        @Override
        public void close() {

        }

        @Override
        public void quit() {

        }

        @Override
        public Set<String> getWindowHandles() {
            return null;
        }

        @Override
        public String getWindowHandle() {
            return null;
        }

        @Override
        public TargetLocator switchTo() {
            return null;
        }

        @Override
        public Navigation navigate() {
            return null;
        }

        @Override
        public Options manage() {
            return null;
        }
    }
}
