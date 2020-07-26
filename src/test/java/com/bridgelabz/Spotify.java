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
    	
    	Token = "Bearer BQASWuR4ObuhCtAsVCiXt6CwzNIxMMVTVSH6s2BtoGlOIfunY0jtr84DT3UJ4Wjaq8iK21kh59pv2JEC0C1Ac_3HVlB1fD4iVXyM80FYMKLnmLCk3cuecydF82K4t3IGW_x_t9Lcq-qmvXKr5RGmwpua5_KyRJ4fQn6fE6weG0VfYcJfSskMh3LLi8HklUPn3fboN5Mn9oL7HFvnS23PKnXXEeAmDkB00js91ybVpW4XqwO-awBRBBUtGrSNCFIR9Cudht_RFSspyEd27VdOnVaXlslU51O4";
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
	        
		}
	
}
