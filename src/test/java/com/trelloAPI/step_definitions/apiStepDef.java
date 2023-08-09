package com.trelloAPI.step_definitions;

import com.trelloAPI.pojo.Board;
import com.trelloAPI.pojo.Member;
import com.trelloAPI.pojo.MemberShips;
import com.trelloAPI.pojo.Notification;
import com.trelloAPI.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import java.util.List;

import static com.trelloAPI.step_definitions.Hooks.*;
import static io.restassured.RestAssured.*;

public class apiStepDef {

    public static String boardId;
    public static String idOfNewMember;
    public static String idOfAdmin;

    @When("{string} can create a {string} board")
    public void canCreateABoard(String username, String boardName) {

        Board board1 = given()
                .spec(getUserRequestSpec(username))
                .queryParam("name", boardName)
                .when()
                .post("boards/")
                .then()
                .spec(responseSpec)
                .extract().body().as(Board.class);

        System.out.println("board1.getId() = " + board1.getId());
        boardId = board1.getId();
        System.out.println("board1.getName() = " + board1.getName());
    }

    @And("{string} can invite {string} in recently created board")
    public void canInviteInRecentlyCreatedBoard(String user1Name, String user2Name) {
        JsonPath jsonPath = given()
                .spec(getUserRequestSpec(user1Name))
                .queryParam("email", ConfigurationReader.getProperty(user2Name + "Email"))
                .when()
                .put("boards/" + boardId + "/members")
                .then()
                .spec(responseSpec)
                .extract().body().jsonPath();
        //List<BoardMembers> membersOfBoard = jsonPath.getList("members");

        List<MemberShips> membershipsOfBoard = jsonPath.getList("memberships", MemberShips.class);
        System.out.println("membershipsOfBoard.get(0) = " + membershipsOfBoard.get(0).toString());


        for (MemberShips memberShips : membershipsOfBoard) {
            if (memberShips.getMemberType().equals("normal")) {
                idOfNewMember = memberShips.getIdMember();
                System.out.println("idOfNewMember = " + idOfNewMember);
            } else if (memberShips.getMemberType().equals("admin")) {
                idOfAdmin = memberShips.getIdMember();
            }
        }

        Member newMember = given()
                .spec(getUserRequestSpec(user2Name))
                .pathParam("id", idOfNewMember)
                .when()
                .get("members/{id}")
                .then()
                .spec(responseSpec)
                .extract().body().as(Member.class);

        String expectedID = idOfNewMember;
        String actualID = newMember.getId();
        Assert.assertEquals(expectedID, actualID);

    }

    @And("{string} can see Board invitation request sent by {string}")
    public void canSeeBoardInvitationRequestSentBy(String user2Name, String user1Name) {
//members/:id/notifications
        List<Notification> notifications = given()
                .spec(getUserRequestSpec(user2Name))
                .when()
                .get("members/" + idOfNewMember + "/notifications")
                .then()
                .statusCode(200).extract().jsonPath().getList("", Notification.class);

        Assert.assertTrue(notifications.get(0).getUnread());
        String idOfBoardCreator = notifications.get(0).getIdMemberCreator();
        Assert.assertEquals(idOfAdmin, idOfBoardCreator);
    }

    @Then("{string} can delete recently created Board {string}")
    public void canDeleteRecentlyCreatedBoard(String user1Name, String boardName) {
        //boards/{id}
        given()
                .spec(getUserRequestSpec(user1Name))
                .pathParam("id", boardId)
                .when()
                .delete("boards/{id}")
                .then()
                .spec(responseSpec);



    }

}
