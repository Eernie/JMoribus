Feature: Some awsome title
  In order to realize a named business value
  As a explicit system actor
  I want to gain some beneficial outcome which furthers the goal

Prologue:
Given this step is played once in the begin of the story


Scenario: scenario
Given a system state named dasdf
Then system is in a different state
Given failing step
When error step

Scenario: scenario 2
Referring scenario
Then success step
When error step