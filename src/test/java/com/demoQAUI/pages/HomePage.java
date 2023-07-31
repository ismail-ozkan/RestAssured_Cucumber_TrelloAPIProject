package com.demoQAUI.pages;

import com.demoQAUI.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public HomePage() {
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//h5[contains(text(),'Book')]")
    public WebElement bookStoreAppModule;

}
