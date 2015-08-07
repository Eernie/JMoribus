package nl.eernie.jmoribus.reporter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Prologue;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;

import org.json.JSONException;
import org.json.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTMLPrintStreamOutput implements Reporter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(HTMLPrintStreamOutput.class);

    private long currentStoryStarttime = 0;
    private String currentStoryOutcome = null;
    private String currentScenario = null;
    private long currentScenarioStarttime = 0;
    private String currentScenarioOutcome = null;
    private long currentStepStarttime = 0;
    private String currentStepOutcome = null;
    private final PrintStream output;
    private final JSONWriter jsonWriter;
    private final OutputStreamWriter outputStreamWriter;
    private final BufferedReader bufferedReader;

    private static final String HTMLFILENAME = HTMLPrintStreamOutput.class.getSimpleName() + ".html";

    protected HTMLPrintStreamOutput() throws FileNotFoundException
    {
        output = new PrintStream("");
        outputStreamWriter = new OutputStreamWriter(output);
        this.jsonWriter = new JSONWriter(outputStreamWriter);

        InputStream is = this.getClass().getResourceAsStream(this.getClass().getSimpleName() + ".html");
        InputStreamReader isr = new InputStreamReader(is);
        bufferedReader = new BufferedReader(isr);

        try
        {
            String input;
            while ((input = bufferedReader.readLine()) != null && !input.equals("//@@JSONDATA@@//"))
            {
                output.println(input);
            }
            output.print("var dataStr = '");
        }
        catch (IOException e)
        {
            LOGGER.error("Error reading first part of {}", HTMLFILENAME, e);
        }
    }

    @Override
    public void beforeStory(Story story)
    {
        this.currentStoryOutcome = "success";

        try
        {
            this.jsonWriter.object().key("stories").array();

            this.jsonWriter.object().key("name").value(story.getTitle()).key("scenarios").array();
        }
        catch (JSONException e)
        {
            LOGGER.error("Error while writing JSON", e);
        }

        this.currentStoryStarttime = System.currentTimeMillis();
    }

    @Override
    public void afterStory(Story story)
    {
        long runtime = System.currentTimeMillis() - this.currentStoryStarttime;

        LOGGER.info("afterStory()");
        try
        {
            this.jsonWriter.endArray().key("outcome").value(this.currentStoryOutcome).key("runtime").value(runtime).endObject();

            this.jsonWriter.endArray().endObject();
        }
        catch (JSONException e)
        {
            LOGGER.error("Error while writing JSON", e);
        }

        try
        {
            this.outputStreamWriter.flush();
        }
        catch (IOException e)
        {
            LOGGER.error("Error while flushing JSON", e);
        }

        try
        {
            output.println("';");
            String input;
            while ((input = bufferedReader.readLine()) != null)
            {
                output.println(input);
            }
        }
        catch (IOException e)
        {
            LOGGER.error("Error reading last part of {}", HTMLFILENAME, e);
        }

        if (output != System.out)
        {
            output.close();
        }
    }

    @Override
    public void failedStep(Step step, AssertionError e)
    {

    }

    @Override
    public void beforeScenario(Scenario scenario)
    {

        this.currentScenario = scenario.getTitle();
        this.currentScenarioOutcome = "success";

        try
        {
            this.jsonWriter.object().key("name").value(currentScenario).key("steps").array();
        }
        catch (JSONException e)
        {
            LOGGER.error("Error while writing JSON", e);
        }

        this.currentScenarioStarttime = System.currentTimeMillis();
    }

    @Override
    public void afterScenario(Scenario scenario)
    {
        long runtime = System.currentTimeMillis() - this.currentScenarioStarttime;

        LOGGER.info("afterScenario()");

        this.currentScenario = null;

        try
        {
            this.jsonWriter.endArray().key("outcome").value(this.currentScenarioOutcome).key("runtime").value(runtime).endObject();
        }
        catch (JSONException e)
        {
            LOGGER.error("Error while writing JSON", e);
        }
    }

    @Override
    public void beforeStep(Step step)
    {
        LOGGER.info("beforeStep({}) in givenStoryContext", step);
        this.currentStepOutcome = "success";
        try
        {
            this.jsonWriter.object().key("name").value(step);
        }
        catch (JSONException e)
        {
            LOGGER.error("Error while writing JSON", e);
        }

        this.currentStepStarttime = System.currentTimeMillis();
    }

    @Override
    public void successStep(Step step)
    {
        LOGGER.info("successful({})", step);

        long runtime = System.currentTimeMillis() - this.currentStepStarttime;

        this.currentStepOutcome = "successful";

        try
        {
            this.jsonWriter.key("outcome").value("success").key("runtime").value(runtime).endObject();
        }
        catch (JSONException e)
        {
            LOGGER.error("Error while writing JSON", e);
        }
    }

    @Override
    public void pendingStep(Step step)
    {
        // There is no beforeStep for this method
        LOGGER.info("pending({})", step);

        long runtime = System.currentTimeMillis() - this.currentStepStarttime;

        this.currentStepOutcome = "pending";
        this.currentScenarioOutcome = "pending";
        this.currentStoryOutcome = "pending";

        try
        {
            this.jsonWriter.object().key("name").value(step).key("outcome").value(currentStepOutcome).key("runtime").value(runtime).endObject();
        }
        catch (JSONException e)
        {
            LOGGER.error("Error while writing JSON", e);
        }
    }

    @Override
    public void errorStep(Step step, Throwable cause)
    {
        LOGGER.info("failed({})", step);

        long runtime = System.currentTimeMillis() - this.currentStepStarttime;

        this.currentStepOutcome = "failed";
        this.currentScenarioOutcome = "failed";
        this.currentStoryOutcome = "failed";

        try
        {
            this.jsonWriter.key("outcome").value("failed").key("runtime").value(runtime).endObject();
        }
        catch (JSONException e)
        {
            LOGGER.error("Error while writing JSON", e);
        }
    }

    @Override
    public void errorStep(Step step, String cause)
    {

    }

    @Override
    public void feature(Feature feature)
    {

    }

    @Override
    public void beforePrologue(Prologue prologue)
    {

    }

    @Override
    public void afterPrologue(Prologue prologue)
    {

    }

    @Override
    public void beforeReferringScenario(StepContainer stepContainer, Scenario scenario)
    {

    }

    @Override
    public void afterReferringScenario(StepContainer stepContainer, Scenario scenario)
    {

    }
}
