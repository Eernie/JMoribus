package nl.eernie.jmoribus.maven.plugin;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Assert;

import java.io.File;

public class RunStoriesMojoTest extends AbstractMojoTestCase
{
    public void testInvalidProject() throws Exception
    {
        File pom = getTestFile("src/test/resources/pom.xml");
        Assert.assertNotNull(pom);
        Assert.assertTrue(pom.exists());

        RunStoriesMojo mojo = (RunStoriesMojo) lookupMojo("run-stories", pom);
        Assert.assertNotNull(mojo);
        mojo.execute();

        Assert.assertTrue(new File("target/jmoribus/TEST-MultiScenarioTitle.xml").exists());
        Assert.assertTrue(new File("target/jmoribus/TEST-second title.xml").exists());
        Assert.assertTrue(new File("target/jmoribus/TEST-testTitle.xml").exists());
    }
}
