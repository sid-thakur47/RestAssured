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
    public String playlists[];
    public int totalPlayList;
    public String trackId[];
    String uid = "";
    String JSON = "application/json";

    //Before method
    @BeforeMethod
    public void get() {
        token = "Bearer BQArudaWxG-bPd8bCVEBH_HMIKmKpmjO5mgZtFpl6FGhG7GhXhs9HA22BXsBfcla42RYQsBwXfJWEQQiz7YGkJpw2V2Poryun74swRnqewzYnL29ufCDPlwHnXLQnOYHzpM9Q3v2TLY4b27qB0YRRm2yBRaUlmi-NdJMCHCpkWlc4olmd2GZVb0PC_QLG1CeA5Xr1fogS62fT0tXda9tuNlPZZCCvg0_9rv4Eue3uxGNA9saOw-JT2Wj5_LKlHi6nRBLLG9ypTUMeZmE1KPcdT2l_M6lgJtZ";
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
        String path = response.path("owner.display_name");
        System.out.println("PlayList Name:" + path);
        response.then().assertThat().statusCode(201); //check for status code
        //response.prettyPrint();
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
        totalPlayList = response.path("total");
        System.out.println("Total PlayList:" + totalPlayList);
        //response.prettyPrint();
    }

    //get List of users PlayList
    @Test(priority = 5)
    public void getUserPlayList() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/users/" + uid + "/playlists");
        //response.prettyPrint();
        playlists = new String[totalPlayList];
        for(int i = 0; i < playlists.length; i++) {
            playlists[i] = response.path("items[" + i + "].id");
        }
        for(String id : playlists) {
            System.out.println("PlayList Id" + id);
        }
    }
}
