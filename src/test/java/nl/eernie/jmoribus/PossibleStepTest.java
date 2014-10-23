package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.Category;
import nl.eernie.jmoribus.annotation.Then;
import nl.eernie.jmoribus.annotation.When;
import nl.eernie.jmoribus.configuration.DefaultConfiguration;
import nl.eernie.jmoribus.model.StepType;
import nl.eernie.jmoribus.reporter.DefaultReporter;
import nl.eernie.jmoribus.to.PossibleStepTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PossibleStepTest {

    @Test
    public void testPossibleSteps() {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();

        steps.add(new Object() {
            @Category({"User", "Login"})
            @When("aMethod")
            @Then({"first title","a second title"})
            public void aMethod() {

            }
        });

        defaultConfiguration.addSteps(steps);
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        List<PossibleStepTO> possibleSteps = jMoribus.getPossibleSteps();

        Assert.assertEquals(3, possibleSteps.size());
        PossibleStepTO possibleStepTO = possibleSteps.get(0);
        Assert.assertEquals(new String[]{"User", "Login"}, possibleStepTO.getCategories());
        Assert.assertEquals("aMethod", possibleStepTO.getStep());
        Assert.assertEquals(StepType.WHEN, possibleStepTO.getStepType());

    }

    @Test
    public void testMultiplePossibleSteps() {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.addReporter(new DefaultReporter());
        ArrayList<Object> steps = new ArrayList<Object>();

        steps.add(new Object() {
            @Category({"User", "Login"})
            @When("aMethod")
            public void aMethod() {

            }

            @Category({"test"})
            @Then("bMethod")
            public void bMethod() {

            }
        });

        defaultConfiguration.addSteps(steps);
        JMoribus jMoribus = new JMoribus(defaultConfiguration);
        List<PossibleStepTO> possibleSteps = jMoribus.getPossibleSteps();

        Assert.assertEquals(2, possibleSteps.size());
    }
}
