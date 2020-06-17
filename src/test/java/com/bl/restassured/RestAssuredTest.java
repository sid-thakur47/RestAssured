package com.bl.restassured;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredTest {

    /*
    @param uid:ID of user
    @param TOKEN: Bearer token
    @param JSON:Request Header
     */

    public static String token = "";
    String uid = "";
    String JSON = "application/json";

    //Before method
    @BeforeMethod
    public void get() {
        token = "Bearer BQBm1g5bLdQxRRhKbC9jm-LcLlXangi3yYlfS-pufEwbJ7ThzVUOdUnoDZ0sOfLJCX26_tPQRriO_w1mj7BmZn-Jd488PsRJLs8MptFDRoe3HyDAv1diQohgWBeqoDlijTOKTFZfmmHDo7Kwcuv5a8Wc0qjxs-FXrJyJCMrC_oNEQGyFowxjcrbbl9YgadzyE4QQmwlKmE0Lw7XBe0LfudLMUo8s3tlIiwvKVchKDVCqak8LbJnkmwoYp5gbv1lp8do_Hu6jMIRzluBlSM601W8gN9e3zhxx";
    }

    //to get user id
    @Test(priority = 1)
    public void getUser_Id() {
        Response response = given()
                .contentType(JSON)
                .accept(JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/me");
        uid = response.path("id"); //get user id
        response.then().assertThat().statusCode(200); //to check for response code
        System.out.println(uid);
    }

    //to get user name
    @Test
    public void getUser_Name() {
        Response response = given()
                .contentType(JSON)
                .accept(JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/me");
        String name = response.path("display_name"); //get user display name
        response.then().assertThat().statusCode(200); //check for status code
        System.out.println(name);
    }

    //to get user profile
    @Test(priority = 2)
    public void get_UserProfile() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/users/" + uid + "/");
        response.then().assertThat().statusCode(200); //check for status code
        String profile = response.getBody().asString(); //get user profile in string format
        System.out.println(profile);
    }

    //to create a new playlist
    @Test(priority = 3)
    public void create_PlayList() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .body("{\"name\": \"Siddhesh2\",\"description\": \"New playlist description\",\"public\": true}")
                .when()
                .post(" https://api.spotify.com/v1/users/" + uid + "/playlists");
        response.then().assertThat().statusCode(201); //check for status code
    }
}
