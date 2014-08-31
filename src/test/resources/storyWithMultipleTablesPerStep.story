Scenario: Scenario with steps containing multiple tables
Given a system state STATE1
When I do something
Then the system should have changed to STATE2
And the system should have the following state transitions:
  |ID|PREVIOUS_STATE|NEXT_STATE|ACTION   |
  | 1|              |    STATE1|initial  |
  | 2|        STATE1|    STATE2|something|
  and the following states should be present:
  |ID|STATE |
  | 1|STATE1|
  | 2|STATE2|