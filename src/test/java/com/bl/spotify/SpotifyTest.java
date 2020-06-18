package com.bl.spotify;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SpotifyTest {

    /*
    @param uid:ID of user
    @param TOKEN: Bearer token
    @param JSON:Request Header
     */

    public static String token = "";
    String uid = "";
    String playlist_Id = "";
    String JSON = "application/json";
    int totalPlayList[];
    private int[] playlists;

    //Before method
    @BeforeMethod
    public void get() {
        token = "Bearer BQAwXirLGpxfRhceoYzPwRCXkeoRPUWD1pY5jLsfzpgh5Ubd1AzElEiSqJAONff2QsQUG4KFan5vjAlkjC7XX45VRhoDzTQVbA62GJBbQvaPzcoNpLOg-Ta_k0vUYBg_1oQFvl3VhjtxQ4W2TjoEyVQNRo4fMFWa-zLgVfr30kz43HuWa8s_yN7k1cinMR7_sWma2911z-QAu1XIO64L5lllTsNG33S3jccf9li1TMMpp-9yKS8src3aHtil_umpYpo0vqanCWiLbS3melOwgz38ioSgv33j";
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
        response.prettyPrint(); //print current user profile
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

    //get user's playlist details
    @Test(priority = 4)
    public void getUserPlayListInfo() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/users/" + uid + "/playlists");
        response.then().assertThat().statusCode(200);
        playlist_Id = response.path("uri");
        System.out.println("play;lisyt" + playlist_Id);
        response.prettyPrint();
    }
}
