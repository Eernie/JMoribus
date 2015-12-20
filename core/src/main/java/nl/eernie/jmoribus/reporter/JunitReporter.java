package nl.eernie.jmoribus.reporter;

import nl.eernie.jmoribus.model.Feature;
import nl.eernie.jmoribus.model.Prologue;
import nl.eernie.jmoribus.model.Scenario;
import nl.eernie.jmoribus.model.Step;
import nl.eernie.jmoribus.model.StepContainer;
import nl.eernie.jmoribus.model.Story;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.report.ObjectFactory;
import org.junit.report.Testsuite;
import org.junit.report.Testsuite.Testcase;
import org.junit.report.Testsuite.Testcase.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class JunitReporter implements Reporter
{
	private static final Logger LOGGER = LoggerFactory.getLogger(JunitReporter.class);
	private static final BigDecimal THOUSAND = BigDecimal.valueOf(1000);

	private Testsuite testsuite;
	private long startTimeTestSuite;

	private Testcase testcase;
	private long startTimeTestCase;
	private final String outputDirectory;
	private Map<String, String> exampleRow;

	public JunitReporter(String outputDirectory)
	{

		this.outputDirectory = outputDirectory;
	}

	@Override
	public void beforeStory(Story story)
	{
		testsuite = new Testsuite();
		testsuite.setName(story.getUniqueIdentifier());
		testsuite.setTests(story.getScenarios().size());
		testsuite.setTimestamp(new Date());
		startTimeTestSuite = System.currentTimeMillis();
	}

	@Override
	public void beforeScenario(Scenario scenario)
	{
		startTimeTestCase = System.currentTimeMillis();
		testcase = new Testcase();
		String title = scenario.getTitle();
		if (exampleRow != null)
		{
			title = title + exampleRow;
		}
		testcase.setName(title);
		testcase.setClassname(scenario.getStory().getUniqueIdentifier());
		testsuite.getTestcase().add(testcase);
	}

	@Override
	public void beforeStep(Step step)
	{
	}

	@Override
	public void successStep(Step step)
	{
	}

	@Override
	public void pendingStep(Step step)
	{
	}

	@Override
	public void afterScenario(Scenario scenario)
	{
		BigDecimal testCaseTookTime = BigDecimal.valueOf(System.currentTimeMillis() - startTimeTestCase).setScale(3, BigDecimal.ROUND_HALF_DOWN);
		testcase.setTime(testCaseTookTime.divide(THOUSAND, BigDecimal.ROUND_HALF_UP));
	}

	@Override
	public void afterStory(Story story)
	{
		BigDecimal testSuiteTook = BigDecimal.valueOf(System.currentTimeMillis() - startTimeTestSuite).setScale(3, BigDecimal.ROUND_HALF_DOWN);
		testsuite.setTime(testSuiteTook.divide(THOUSAND, BigDecimal.ROUND_HALF_UP));
		testsuite.setSystemErr("");
		testsuite.setSystemOut("");
		writeToFile();
	}

	private void writeToFile()
	{
		String fileName = testsuite.getName().replace('/', '_').replace('\\', '_');
		String path = outputDirectory + File.separator + "jmoribus" + File.separator + "TEST-" + fileName + ".xml";

		File output = new File(path);

		if (!output.getParentFile().exists())
		{
			output.getParentFile().mkdirs();
		}

		try (OutputStream os = new FileOutputStream(path))
		{
			JAXBContext jaxbCtx = JAXBContext.newInstance(Testsuite.class);
			Marshaller marshaller = jaxbCtx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(new ObjectFactory().createTestsuite(testsuite), os);
		}
		catch (JAXBException | IOException e)
		{
			LOGGER.error("Something went wrong while saving the file: " + path, e);
		}
	}

	@Override
	public void failedStep(Step step, AssertionError e)
	{
		Failure failure = new Failure();
		failure.setMessage(e.getMessage());
		failure.setValue(ExceptionUtils.getStackTrace(e));
		failure.setType(e.getClass().getCanonicalName());
		testcase.setFailure(failure);
		testsuite.setFailures(testsuite.getFailures() + 1);
	}

	@Override
	public void errorStep(Step step, Exception e)
	{
		Testcase.Error error = new Testcase.Error();
		error.setMessage(e.getMessage());
		error.setValue(ExceptionUtils.getStackTrace(e));
		error.setType(e.getClass().getCanonicalName());
		testcase.setError(error);
		testsuite.setErrors(testsuite.getErrors() + 1);
	}

	@Override
	public void skipStep(Step step)
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

	@Override
	public void beforeExamplesTable(Scenario scenario)
	{

	}

	@Override
	public void beforeExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
		this.exampleRow = exampleRow;
	}

	@Override
	public void afterExampleRow(Scenario scenario, Map<String, String> exampleRow)
	{
		this.exampleRow = null;
	}

	@Override
	public void afterExamplesTable(Scenario scenario)
	{

	}
}
