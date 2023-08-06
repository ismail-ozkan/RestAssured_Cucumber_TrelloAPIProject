package com.demoQAUI.step_definitions;

import com.demoQAUI.utilities.ConfigurationReader;
import com.demoQAUI.utilities.Driver;
import io.cucumber.java.*;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;

public class Hooks {

    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;

    @Before(value = "@ui")
    public void setup() {
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Before(value = "@api")
    public void setupApi() {
        baseURI = ConfigurationReader.getProperty("baseUrl");
        responseSpec = expect()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .logDetail(LogDetail.ALL);
    }

    @After(value = "@ui")
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        Driver.closeDriver();
    }

    @After(value = "@api")
    public static void teardown() {
        reset();
    }

    public static RequestSpecification getUserRequestSpec(String userName) {

        requestSpec = given()
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .queryParams("key", ConfigurationReader.getProperty(userName + "ApiKey"),
                        "token", ConfigurationReader.getProperty(userName + "ApiToken"));
        return requestSpec;

    }

    public static Map<String, String> getUserKeyAndToken(String username) {
        String key = "";
        String token = "";
        switch (username) {
            case "User1":
                key = ConfigurationReader.getProperty("user1ApiKey");
                token = ConfigurationReader.getProperty("user1ApiToken");
                break;
            case "User2":
                key = ConfigurationReader.getProperty("user2ApiKey");
                token = ConfigurationReader.getProperty("user2ApiToken");
                break;
        }
        Map<String, String> keyAndToken = new HashMap<>();
        keyAndToken.put("key", key);
        keyAndToken.put("token", token);
        return keyAndToken;
    }

        /*if (userName.equals("User1")){
            reqSpec = given()
                    .accept(ContentType.JSON)
                    .queryParams("key", key,
                            "token", token);
        }

        String key = "";
        String token = "";
        switch (userName) {
            case "User1":
                key = ConfigurationReader.getProperty("user1ApiKey");
                token = ConfigurationReader.getProperty("user1ApiToken");
                break;
            case "User2":
                key = ConfigurationReader.getProperty("user2ApiKey");
                token = ConfigurationReader.getProperty("user2ApiToken");
                break;
        }
        reqSpec = given()
                .accept(ContentType.JSON)
                .queryParams("key", key, "token", token);*/
}
