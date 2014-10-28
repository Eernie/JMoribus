package nl.eernie.jmoribus;

import nl.eernie.jmoribus.annotation.Given;
import nl.eernie.jmoribus.annotation.Then;
import nl.eernie.jmoribus.annotation.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumSteps {

    @Given("the method has a Webdriver in the signature")
    public void webDriverMethod(WebDriver driver) {
        Assert.assertNotNull(driver);
    }

    @Given("the method has a WebDriver and more vars in the $signature")
    public void webDriverMethod(WebDriver driver, String var) {
        Assert.assertNotNull(driver);
        Assert.assertEquals("signature", var);
    }

    @When("the user navigates to $url")
    public void navigate(WebDriver driver, String url) {
        driver.navigate().to("http://" + url);
    }

    @Then("the pagetop should be available")
    public void assertPageTop(WebDriver driver) {
        WebElement pagetop = driver.findElement(By.className("pagetop"));
        Assert.assertEquals("span", pagetop.getTagName());
    }

    @Then("the user closes the browser")
    public void closeDriver(WebDriver driver) {
        driver.close();
    }
}
