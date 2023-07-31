package com.demoQAUI.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProfilePage extends HomePage{

    @FindBy(xpath = "//span//a")
    public List<WebElement> addedBooks;

    @FindBy(xpath = "(//button[.='Delete All Books'])[1]")
    public WebElement deleteAllBooks;

    @FindBy(xpath = "//button[.='OK']")
    public WebElement okButton;


}
