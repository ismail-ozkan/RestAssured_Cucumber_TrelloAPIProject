@api
Feature: User1 create a board and invite User2,
  User 2 see notification of invitation,
  User 1 delete the board

  @api
  Scenario: Create a board add a member then delete the board
    When "User1" can create a "TestBoardLast" board
    And "User1" can invite "User2" in recently created board
    And "User2" can see Board invitation request sent by "User1"
    Then "User1" can delete recently created Board "TestBoardLast"