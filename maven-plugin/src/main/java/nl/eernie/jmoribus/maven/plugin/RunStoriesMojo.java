package nl.eernie.jmoribus.maven.plugin;

import nl.eernie.jmoribus.junit.JunitTestRunner;
import nl.eernie.jmoribus.reporter.JunitReporter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "run-stories", requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class RunStoriesMojo extends AbstractMojo
{
    @Parameter(required = true, defaultValue = "${project.build.directory}")
	String outputDirectory;

	@Parameter(required = true, defaultValue = "${project.compileClasspathElements}")
	List<String> compileClasspathElements;

	@Parameter(required = true)
	String runClass;

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		try
		{
			URLClassLoader classLoader = new URLClassLoader(classpathURLs(compileClasspathElements), getClass().getClassLoader());
            Thread.currentThread().setContextClassLoader(classLoader);
			Class<?> runClass = classLoader.loadClass(this.runClass);
			if (JunitTestRunner.class.isAssignableFrom(runClass))
			{
				JunitTestRunner testRunner = (JunitTestRunner) runClass.newInstance();
				getLog().debug("Using directory " + outputDirectory);
				testRunner.getConfiguration().addReporter(new JunitReporter(outputDirectory));
				MojoReporter reporter = new MojoReporter();
				testRunner.getConfiguration().addReporter(reporter);
				testRunner.runStories();

				String message = String.format("Run finished with %d successful, %d failure, %d error, %d skipped an %d pending", reporter.getSuccess(), reporter.getFailure(), reporter.getError(), reporter.getSkipped(), reporter.getPending());
				getLog().info(message);
				if (reporter.getError() > 0 || reporter.getFailure() > 0 || (testRunner.getConfiguration().isFailOnPending() && reporter.getPending() > 0))
				{
					throw new MojoFailureException(message);
				}
			}
			else
			{
				String message = "class " + runClass + "is not an instance of " + JunitTestRunner.class;
				getLog().error(message);
				throw new MojoExecutionException(message);
			}
		}
		catch (MalformedURLException e)
		{
			getLog().error("Could not create the right classLoader", e);
			throw new MojoExecutionException("Could not create the right classLoader", e);
		}
		catch (ClassNotFoundException e)
		{
			getLog().error("Could not find the run class", e);
			throw new MojoExecutionException("Could not find the run class", e);
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			getLog().error("Could not create the run class", e);
			throw new MojoExecutionException("Could not create the run class", e);
		}
	}

	private static URL[] classpathURLs(List<String> elements) throws MalformedURLException
	{
		List<URL> urls = new ArrayList<>();
		if (elements != null)
		{
			for (String element : elements)
			{
				urls.add(toURL(element));
			}
		}
		return urls.toArray(new URL[urls.size()]);
	}

	private static URL toURL(String element) throws MalformedURLException
	{
		return new File(element).toURI().toURL();
	}
}
