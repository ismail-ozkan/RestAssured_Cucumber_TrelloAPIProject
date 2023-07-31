#
#  • Navigate to the DemoQA Website: Initiate a browser session and navigate to the DemoQA website.
#  • Access the Bookstore Application: Navigate to the bookstore application in the DemoQA website.
#  • Login with User in Context: Use the user credentials from the context to login to the bookstore application.
#  • Verify Number of Books Added to User: Compare the number of books displayed in the user's account on the
#  website with the number of books stored in context.
#  • Verify Book Details: For each book displayed in the user's account, navigate into the book details page and
#  verify the details (author, publisher, number of pages, etc.) against the data stored in context.

@wip
Feature: Bookstore Application Functionality

  Background:
    Given User navigates to the DemoQA website
    And User clicks to Bookstore module

  Scenario: Access the Bookstore Application
    Then Bookstore Application is displayed

  Scenario: Login with valid credentials
    When User clicks the Login button
    And User pass the valid username
    And User pass the valid password
    And User clicks the Login button
    Then User is successfully logged in

  Scenario Outline: Verify Number of Books Added to User
    When User is logged in
    And User clicks the Go To Book Store button
    And User clicks the following "<books>"
    And User clicks the Add To Your Collection button
    Then User should see the information pop up message
    When User clicks the Profile module
    Then User checks the <number> of books in their account

    Examples:
      | books               | number |
      | Git Pocket Guide    | 1      |
      | Speaking JavaScript | 2      |
      | You Don't Know JS   | 3      |

  Scenario Outline: Verify Book Details
    When User is logged in
    And User clicks the following "<books>"
    Then User navigates to the book details page for each book in their account
    And Book details "<author>", "<totalPage>", "<ISBN>", "<SubTitle>" match the following data for each book

    Examples:
      | books               | author               | totalPage | ISBN          | SubTitle                          |
      | Git Pocket Guide    | Richard E. Silverman | 234       | 9781449325862 | A Working Introduction            |
      | Speaking JavaScript | Axel Rauschmayer     | 460       | 9781449365035 | An In-Depth Guide for Programmers |
      | You Don't Know JS   | Kyle Simpson         | 278       | 9781491904244 | ES6 & Beyond                      |


#I created this scenario  In order to run this test suit over and over again for your comfortable
# Deleting all books from user (johnWick) profile
    Scenario: Deleting all book in profile
      When User is logged in
      And User clicks the Profile module
      And User clicks delete all books button
