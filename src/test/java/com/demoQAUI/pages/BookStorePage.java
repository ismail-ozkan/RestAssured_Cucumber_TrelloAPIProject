package com.demoQAUI.pages;

import com.demoQAUI.utilities.BrowserUtils;
import com.demoQAUI.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BookStorePage extends HomePage {

    @FindBy(xpath = "//button[@id='login']")
    public WebElement loginButton;

    @FindBy(xpath = "//input[@id='userName']")
    public WebElement userNameBox;

    @FindBy(xpath = "//input[@id='password']")
    public WebElement passwordBox;

    @FindBy(id = "userName-value")
    public WebElement displayedUserName;

    public void login(String username, String password){
        BookStorePage bookStorePage = new BookStorePage();
        bookStorePage.loginButton.click();
        bookStorePage.userNameBox.sendKeys(username);
        bookStorePage.passwordBox.sendKeys(password);
        BrowserUtils.clickWithJS(bookStorePage.loginButton);
    }

    @FindBy(xpath = "//span[.='Book Store']")
    public WebElement bookStoreButton;

    @FindBy(xpath = "//div[@class='rt-table']//a")
    public List<WebElement> books;

    @FindBy(xpath = "(//button[@id='addNewRecordButton'])[2]")
    public WebElement addToYourCollectionButton;

    @FindBy(xpath = "//span[.='Profile']")
    public WebElement profileButton;

    public WebElement booksInfo(String bookInfo){
        return Driver.getDriver().findElement(By.xpath("//label[contains(@id,'"+bookInfo+"')]/../following-sibling::div//label"));
    }



}
