package com.demoQAAPI.test;

import com.demoQAAPI.pojo.*;
import com.demoQAAPI.pojo.PostBook;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tests extends BaseTest {

    static User user1 = new User();
    static String userID;
    static String token;

    @DisplayName("Create a User")
    @Test
    @Order(1)
    public void createUserTest(){

        Faker faker = new Faker();
        String userName = faker.name().firstName() + faker.number().numberBetween(1, 10);
        System.out.println("userName = " + userName);
        String password = "Pass471!";

        user1.setUserName(userName);
        user1.setPassword(password);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(user1).log().all()
                .when().post("/Account/v1/User")
                .then().statusCode(201).extract().response();

        response.prettyPrint();
        userID = response.jsonPath().getString("userID");
        System.out.println("userID = " + userID);

        assertThat(userID,is(notNullValue()));
    }

    @DisplayName("Generate Authentication Token")
    @Test
    @Order(2)
    public void generateAuthenticationTest(){

         token = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(user1).log().all()
                .when().post("/Account/v1/GenerateToken")
                .then().statusCode(200).extract().response()
                .jsonPath().getString("token");

        System.out.println("token = " + token);

    }

    @DisplayName("Get List of Books")
    @Test
    @Order(3)
    public void getListOfBooksTest(){

        AllBooks books = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().get("/BookStore/v1/Books")
                .then().statusCode(200).extract().response().as(AllBooks.class);

        System.out.println("books = " + books);

        for (Book book : books.getBooks()) {
            assertThat(book.getTitle(), is(notNullValue()));
            assertThat(book.getAuthor(), is(notNullValue()));
            assertThat(book.getIsbn(), is(notNullValue()));
            assertThat(book.getDescription(), is(notNullValue()));
            assertThat(book.getPages(), is(notNullValue()));
            assertThat(book.getWebsite(), is(notNullValue()));
            assertThat(book.getSubTitle(), is(notNullValue()));
            assertThat(book.getPublish_date(), is(notNullValue()));
            assertThat(book.getPublisher(), is(notNullValue()));
        }
    }

    @DisplayName("Filter by Publisher or Author")
    @Order(4)
    @ParameterizedTest
    @CsvSource({"Richard E. Silverman",
            "Axel Rauschmayer",
            "Kyle Simpson" })
    public void FilterByPublisherOrAuthorTest(String authorName){

        AllBooks books = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().get("/BookStore/v1/Books")
                .then().statusCode(200).extract().response().as(AllBooks.class);

        List<Book> filteredBooksByAuthor = new ArrayList<>();
        for (Book book : books.getBooks()) {
            if(book.getAuthor().equals(authorName)){
                filteredBooksByAuthor.add(book);
            }
        }
        System.out.println("filteredBooksByAuthor.get(0) = " + filteredBooksByAuthor.get(0));

        assertThat(filteredBooksByAuthor.get(0).getAuthor(),is(authorName));
    }

    @DisplayName("Post Books to the User in Context")
    @Test
    @Order(5)
    public void postBooksToTheUserInContext(){

        PostBook addBook = new PostBook();
        addBook.setUserId(userID);
        Isbn isbn = new Isbn();
        isbn.setIsbn("9781491904244");
        List<Isbn> booksList = new ArrayList<>();
        booksList.add(isbn);
        addBook.setCollectionOfIsbns(booksList);

        System.out.println("user1.getUserName() = " + user1.getUserName());

        given().accept(ContentType.JSON)
                 //.and().auth().basic(user1.getUserName(),user1.getPassword())
                 .and().header("Authorization","Bearer "+token)
                 .and().contentType(ContentType.JSON)
                 .body(addBook)
                 .when().post("/BookStore/v1/Books")
                 .then().statusCode(201).extract().response().prettyPrint();

        UserInformation userInfo = given().accept(ContentType.JSON)
                //.and().auth().basic(user1.getUserName(),user1.getPassword())
                .and().header("Authorization","Bearer "+token)
                .and().pathParam("UUID", userID)
                .when().get("/Account/v1/User/{UUID}")
                .then().statusCode(200)
                .log().all()
                .extract().response().as(UserInformation.class);

        String actualIsbn = userInfo.getBooks().get(0).getIsbn();
        assertThat(actualIsbn,is(isbn.getIsbn()));

    }
}
