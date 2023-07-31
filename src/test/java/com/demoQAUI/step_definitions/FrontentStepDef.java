package com.demoQAUI.step_definitions;

import com.demoQAUI.pages.BookStorePage;
import com.demoQAUI.pages.HomePage;
import com.demoQAUI.pages.ProfilePage;
import com.demoQAUI.utilities.BrowserUtils;
import com.demoQAUI.utilities.ConfigurationReader;
import com.demoQAUI.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;

public class FrontentStepDef {

    HomePage homePage = new HomePage();
    BookStorePage bookStorePage = new BookStorePage();
    ProfilePage profilePage = new ProfilePage();

    @Given("User navigates to the DemoQA website")
    public void user_navigates_to_the_demo_qa_website() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
    }
    @Given("User clicks to Bookstore module")
    public void user_clicks_to_bookstore_module() {
        BrowserUtils.clickWithJS(homePage.bookStoreAppModule);
    }
    @Then("Bookstore Application is displayed")
    public void bookstore_application_is_displayed() {
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("book"));
    }
    @When("User clicks the Login button")
    public void user_clicks_the_login_button() {
        BrowserUtils.clickWithJS(bookStorePage.loginButton);
    }
    @When("User pass the valid username")
    public void user_pass_the_valid_username() {
        bookStorePage.userNameBox.sendKeys(ConfigurationReader.getProperty("username"));
    }
    @When("User pass the valid password")
    public void user_pass_the_valid_password() {
        bookStorePage.passwordBox.sendKeys(ConfigurationReader.getProperty("password"));
    }
    @Then("User is successfully logged in")
    public void user_is_successfully_logged_in() {
        Assert.assertEquals(ConfigurationReader.getProperty("username"),bookStorePage.displayedUserName.getText());
    }


    @When("User is logged in")
    public void user_is_logged_in() {
       // BrowserUtils.waitFor(2);
        bookStorePage.login(ConfigurationReader.getProperty("username"),ConfigurationReader.getProperty("password"));
    }
    @When("User clicks the Go To Book Store button")
    public void user_clicks_the_go_to_book_store_button() {
        BrowserUtils.waitFor(2);
        BrowserUtils.clickWithJS(bookStorePage.bookStoreButton);
    }
    @When("User clicks the following {string}")
    public void user_clicks_the_following_books(String bookName) {
       // BrowserUtils.waitFor(3);
        for (WebElement eachBook : bookStorePage.books) {
            if(eachBook.getText().contains(bookName)){
                BrowserUtils.clickWithJS(eachBook);
                break;
            }
        }
        //BrowserUtils.waitFor(3);
    }
    @When("User clicks the Add To Your Collection button")
    public void user_clicks_the_add_to_your_collection_button() {
        BrowserUtils.clickWithJS(bookStorePage.addToYourCollectionButton);
        BrowserUtils.waitFor(2);
    }
    @Then("User should see the information pop up message")
    public void user_should_see_the_information_pop_up_message() {
        Alert alert = Driver.getDriver().switchTo().alert();
        Assert.assertTrue(alert.getText().contains("added"));
        alert.accept();
    }
    @When("User clicks the Profile module")
    public void user_clicks_the_profile_module() {
        BrowserUtils.waitFor(2);
        BrowserUtils.clickWithJS(bookStorePage.profileButton);
    }
    @Then("User checks the {int} of books in their account")
    public void user_checks_the_number_of_books_in_their_account(int numberOfBook) {
        int actualNumberOfBooks = profilePage.addedBooks.size();
        Assert.assertEquals(numberOfBook,actualNumberOfBooks);
       // BrowserUtils.waitFor(2);
    }


    @Then("User navigates to the book details page for each book in their account")
    public void userNavigatesToTheBookDetailsPageForEachBookInTheirAccount() {
        Assert.assertTrue(bookStorePage.booksInfo("title").isDisplayed());
       // BrowserUtils.waitFor(2);
    }


    @And("Book details {string}, {string}, {string}, {string} match the following data for each book")
    public void bookDetailsTotalPageMatchTheFollowingDataForEachBook(String author, String page, String isbn, String subTitle) {
        Assert.assertEquals(author,bookStorePage.booksInfo("author").getText());
        Assert.assertEquals(page,bookStorePage.booksInfo("pages").getText());
        Assert.assertEquals(isbn,bookStorePage.booksInfo("ISBN").getText());
        Assert.assertEquals(subTitle,bookStorePage.booksInfo("sub").getText());

    }

    @And("User clicks delete all books button")
    public void userClicksDeleteAllBooksButton() {
        // I did not create a new user. In order to run this test suit over and over again for your comfortable I have to write this code.
        // deleting all books from user (johnWick) profile
        BrowserUtils.clickWithJS(profilePage.deleteAllBooks);
        profilePage.okButton.click();

    }
}
