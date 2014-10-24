Feature: Selenium story


Scenario: method gets webdriver
Given the method has a Webdriver in the signature

Scenario: method gets multiple vars and webdriver
Given the method has a WebDriver and more vars in the signature

Scenario: Steps use Selenium to test google
When the user navigates to news.ycombinator.com/news
Then the pagetop should be available
And the user closes the browser
