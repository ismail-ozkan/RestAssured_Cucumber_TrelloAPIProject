package com.demoQAUI.step_definitions;

import com.demoQAUI.utilities.ConfigurationReader;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public abstract class BaseTest {

    public static RequestSpecification userReqSpec;
    public static ResponseSpecification responseSpec;

    static {
        //baseURI = ConfigurationReader.getProperty("baseUrl");
    }
    @BeforeAll
    public static void init() {
        //baseURI = ConfigurationReader.getProperty("baseUrl");


    }

    @AfterAll
    public static void teardown() {
        reset();
    }

    /*public RequestSpecification getUserRequestSpec(String userName) {

        String key = "";
        String token = "";
        switch (userName) {
            case "User1":
                key = ConfigurationReader.getProperty("user1ApiKey");
                token = ConfigurationReader.getProperty("user1ApiToken");
                break;
            case "user2":
                key = ConfigurationReader.getProperty("user2ApiKey");
                token = ConfigurationReader.getProperty("user2ApiToken");
                break;
        }
        return given()
                .accept(ContentType.JSON)
                .queryParams("key",key,"token",token)
                .log().all();

    }*/
}