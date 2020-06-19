package com.bridgelabz.spotify;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SpotifyTest {

    /*
    @param uid:ID of user
    @param TOKEN: Bearer token
    @param JSON:Request
    @param playlists:to store all playlist
    @param: trackId:to store all track ids
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
        token = "Bearer BQAdii_RvAKoaWFUrPbUDbYJmIaXR_QOMAr_cm7XEbkWoqBY1f_GLYTMkNqn5Fn6keWPJRTIZ1cjW-NIjwMCXD6ZcnyE7gNwhEvIr3CPm_LmYZCg6B9pd2AmajDQxp0U1Ir648Pb2NazRRs_tS3RVoAq2ofm7O0dMJZe3th38r7f9Rob8BbKl59-AwmBGA_vEBlRRW5-hlO8c7QHeigfM2flTHlvaqnu79XlXeYg9B0ZuWS0tXD-k4qTxl9BkkLr0ox3KD4OopcsATN7TuPDTn-ozkGdihcf";
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
    @Test(priority = 10)
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
    @Test(priority = 3)
    public void getUserPlayListInfo() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/users/" + uid + "/playlists");
        response.then().assertThat().statusCode(200);
        totalPlayList = response.path("total"); //get total playlist
        System.out.println("Total PlayList:" + totalPlayList);
        //response.prettyPrint();
    }

    //get List of users PlayList
    @Test(priority = 4)
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
            playlists[i] = response.path("items[" + i + "].id"); //get playlist id
        }
        for(String id : playlists) { //print play list
            System.out.println("PlayList Id" + id);
        }
    }

    //to change Playlist Name
    @Test(priority = 11)
    public void changeDetails() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .body("{\"name\": \"SiddheshThakur\",\"description\": \"New playlist description\",\"public\": true}")
                .when()
                .put("https://api.spotify.com/v1/playlists/" + playlists[1] + "");
        response.then().assertThat().statusCode(200);
    }

    //get List of items in playList
    @Test(priority = 5)
    public void getAllPlayListItem() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .when().accept(JSON)
                .get("https://api.spotify.com/v1/playlists/" + playlists[0] + "/tracks");
        int totalTracks = response.path("total");
        trackId = new String[totalTracks];
        for(int i = 0; i < trackId.length; i++) {
            trackId[i] = response.path("items[" + i + "].track.uri"); //get uri of track
        }
        for(String track : trackId) {
            System.out.println("Tracks:" + track); //print track uri
        }
    }

    //delete a track from playlist
    @Test(priority = 7)
    public void deleteTrackFromList() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .body("{\"uris\": [\"" + trackId[1] + "\"]}")
                .when()
                .delete("https://api.spotify.com/v1/playlists/" + playlists[0] + "/tracks/");
        System.out.println("SnapShot Id is:" + response.path("snapshot_id"));
    }

    //add track to play list
    @Test(priority = 6)
    public void addTrackToList() {
        Response response = given()
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", token)
                .body("{\"uris\": [\"" + trackId[1] + "\"]}")
                .when()
                .post("https://api.spotify.com/v1/playlists/" + playlists[1] + "/tracks/");
        System.out.println("SnapShot Id is:" + response.path("snapshot_id"));
    }
}