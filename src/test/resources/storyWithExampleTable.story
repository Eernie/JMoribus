Feature: Some awesome title
In order to realize a named business value
As a explicit system actor
I want to gain some beneficial outcome which furthers the goal

Background:
Given this step is played once in the begin of the story

!-- awesome comment!!!!

Scenario: parametrized scenario
Given a system state named <systemStateName>
When I do <action>
Then system is in state <newSystemStateName>

Examples:
systemStateName     action      newSystemStateName
currentState        nothing     currentState
currentState        pushButton  newState