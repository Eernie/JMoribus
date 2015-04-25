package nl.eernie.jmoribus.maven.plugin;

import nl.eernie.jmoribus.junit.JunitTestRunner;
import nl.eernie.jmoribus.reporter.JunitReporter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


@Mojo(name = "run-stories")
public class RunStoriesMojo extends AbstractMojo
{
    @Parameter(required = true, defaultValue = "${project.build.outputDirectory}")
    String outputDirectory;

    @Parameter(required = true, defaultValue = "${project.compileClasspathElements}")

    List<String> compileClasspathElements;

    @Parameter(required = true)
    String runClass;

    public void execute() throws MojoExecutionException
    {
        try
        {
            URLClassLoader classLoader = new URLClassLoader(classpathURLs(compileClasspathElements), getClass().getClassLoader());
            Class<?> runClass = classLoader.loadClass(this.runClass);
            if (JunitTestRunner.class.isAssignableFrom(runClass))
            {
                JunitTestRunner testRunner = (JunitTestRunner) runClass.newInstance();
                getLog().debug("Using directory " + outputDirectory);
                testRunner.getConfiguration().addReporter(new JunitReporter(outputDirectory));
                testRunner.runStories();
            }
            else
            {
                String message = "class " + runClass + "is not an instance of " + JunitTestRunner.class;
                getLog().error(message);
                throw new IllegalArgumentException(message);
            }
        }
        catch (MalformedURLException e)
        {
            getLog().error("Could not create the right classLoader", e);
        }
        catch (ClassNotFoundException e)
        {
            getLog().error("Could not find the run class", e);
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            getLog().error("Could not create the run class", e);
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
