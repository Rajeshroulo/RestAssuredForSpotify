package com.bridgelabz;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Spotify {
	
	String userid="";
    String Token ="";
    
    @BeforeMethod
    public void setup() {
    	
    	Token = "Bearer BQA3JmrAk4l1yKQvfBeFeTJK7WESC2bYFTT-vboJccqMZWQKld9EcUCUdGvnJx9IlIfwnM1VdI71YTG2rmxAXsEKxPSTal97rpAD3gPmUEXGREztX5boVtIgd8Z7XKh0xgDQQ9wWprqByODNNNP76kIsqR8iq-aoTMBgK4aAyN7Sxgj4Oz_-8jVPYoYK3okYr8SwkbKzMJCqI3F5P7zhVn99SCquQn2pfh0xFWpdVZFSKWFhJCr07CX0hWUaC31MItLmyB-jT1P-7_FPVfCnsboLfcAV85PB ";
    	}
	
	@Test
  public void totesttheApisofspotifyAndcheckresponse () {
		Response response =  RestAssured.given()
				.header("Authorization", Token)
                .when()
				.get("https://api.spotify.com/v1/me");
		 userid = response.path("id");
	        System.out.println("userId=" + userid);
	        System.out.println(response.getBody().asString());
	
		Response playlist =  RestAssured.given()
				.accept("application/json")
                .contentType("application/json")               
				.header("Authorization", Token)
				.body("{\"name\": \" Hollywood  \",\"description\": \"English songs\",\"public\": false}")
				.pathParam("user_id",userid)
                .when()
                .post("https://api.spotify.com/v1/users/{user_id}/playlists");                	
	        
		}
	
}
