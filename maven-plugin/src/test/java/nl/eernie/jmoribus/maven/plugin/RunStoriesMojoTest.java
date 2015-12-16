package nl.eernie.jmoribus.maven.plugin;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

public class RunStoriesMojoTest extends AbstractMojoTestCase
{
    public void testValidProject() throws Exception
    {
        File pom = getTestFile("src/test/resources/pom.xml");

        RunStoriesMojo mojo = (RunStoriesMojo) lookupMojo("run-stories", pom);
        assertNotNull(mojo);

        try
        {
            mojo.execute();
        }
        catch (MojoFailureException mfe)
        {
            assertEquals("Run finished with 8 successful, 0 failure, 0 error, 17 skipped an 10 pending", mfe.getMessage());
        }

        assertTrue(new File("target/jmoribus/TEST-MultiScenarioTitle.xml").exists());
        assertTrue(new File("target/jmoribus/TEST-second title.xml").exists());
        assertTrue(new File("target/jmoribus/TEST-testTitle.xml").exists());
    }
}
