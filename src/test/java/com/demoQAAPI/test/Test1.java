package com.demoQAAPI.test;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Test1 {

    @Test
    public void test1(){
        baseURI="https://api.trello.com/1";
        given()
                .queryParam("name","Board14")
                .queryParam("key","8c4b8f065def9eef70fed2f09f045cf4")
                .queryParam("token","ATTA50dd1dd83617072c31161041d7b0df1d55503b7a36c01dc34347629decf2c6f1835FD41B")
                .when()
                .post("/boards/")
                .then()
                .log().all();
    }
}
