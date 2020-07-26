package com.bridgelabz;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Spotify {
	
	String userid;
    String Token;
    String plid1,plid2,plid3;
    public String[] tracks;
    
    @BeforeMethod
    public void setup() {
    	
   Token = "Bearer BQBel82GfMLhqW2zmlI7fq_luMDnVurZpLSaSGBagLK1tcRS2L5ESX8uJGet2Ap-G7_RSA3xAO1jqjp406FEZscNrPovk7HReJmt7bSvpVpIj7_p83iaUlWb0v4ZgGlCupw8uC5lzOiWdf4aBFVrghywwGHmEs5K2SOIT7urP2DmNEJ4cRN2CtFivEwZz0zX8H2FZR_VYQ-sc9P-8H0BljHoa106ZxPw0lnQMg5rs4d_DO6T3uC5SXhHSI0Pv4qn-XOrtVlzb0yqPh12QaKHz05u0nUyQ5DM"; 
   }
	
	@Test
  public void totesttheApisofspotifyAndcheckresponse () {
		//to get user id and user profile
		Response response =  RestAssured.given()
				.header("Authorization", Token)
                .when()
				.get("https://api.spotify.com/v1/me");
		 userid = response.path("id");
	        System.out.println("userId=" + userid);
	        System.out.println(response.getBody().asString());
	
	//to create a new playlist        
	/*	Response addplaylist =  RestAssured.given()
				.accept("application/json")
                .contentType("application/json")               
				.header("Authorization", Token)
				.body("{\"name\": \" Hollywood  \",\"description\": \"English songs\",\"public\": false}")
				.pathParam("user_id",userid)
                .when()
                .post("https://api.spotify.com/v1/users/{user_id}/playlists"); */
		
		// to get total no of playlists and it's id
		 Response list = RestAssured.given()
	                .header("Authorization", Token)
	                .pathParam("user_id", userid)
	                .when()
	                .get("https://api.spotify.com/v1/users/{user_id}/playlists");
	        int count = list.path("total");
	        System.out.println("total no.of playlists=" + count);
	        plid1 = list.path("items[0].id");
	        plid2 = list.path("items[1].id");
	        plid3 = list.path("items[2].id");
	        System.out.println("1st Playlist id is = " + plid1);
	        System.out.println("2nd Playlist id is = " + plid2);
	        System.out.println("3rd Playlist id is = " + plid3);

	        // to get all the tracks in a play list
	        Response items = RestAssured.given()
	                .header("Authorization", Token)
	                .pathParam("playlist_id", plid2)
	                .when()
	                .get("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");
	        int all = items.path("total");
	        tracks = new String[all];
	        for (int i = 0; i < tracks.length; i++) {
	            tracks[i] = items.path("items[" + i + "].track.uri"); 
	        }
	        for (String track : tracks) {
	            System.out.println("Track uri=" + track);
	        }
	        
	        
	        Response remove = RestAssured.given()
	                .header("Authorization", Token)
	                .pathParam("playlist_id", plid2)
	                .body("{\"uris\": [\"" + tracks[1] + "\"]}")
	                .when()
	                .delete("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");
	        remove.prettyPrint();

	        
		}
	
}
