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

    String uid = "";
    public static String token = "";
    String JSON = "application/json";

    //Before method
    @BeforeMethod
    public void get() {
        token = "Bearer BQDLqpsVAzQOIz1nVTgNMbHW7Lmd7lBnevmLoO9Gy-DFcVKjQ7F6ldvXTb3g5CtLYGwCsgwlbVYkyymT4lndeNax9VdbIxv3fuZaYbOcNqNiDGGoGQt_XKmXT7ill8a1ovHta9eAz83SV1aMN7Rw6P517wJ4esQTfbKKc8eso_hbLLXnvZTFkjEhzlQXO_Y8ERurYehjXYJ_bMx9FqFO5w_BNOG11MQ8Brcu0dBqT71taq0AtBci7Zgqtj7-9kZ_tfqZ0UgX3zOLqXEn4eIvbjU-df_S55iU";
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
        uid = response.path("id");
        response.then().assertThat().statusCode(200);
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
        String name = response.path("display_name");
        response.then().assertThat().statusCode(200);
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
        response.then().assertThat().statusCode(200);
        String profile = response.getBody().asString();
        System.out.println(profile);
    }

    //to create a new playlist
    @Test(priority = 3)
    public void create_PlayList() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .body("{\"name\": \"Siddhesh1\",\"description\": \"New playlist description\",\"public\": true}")
                .when()
                .post(" https://api.spotify.com/v1/users/" + uid + "/playlists");
//        response.then().assertThat().statusCode(200);
    }
}
