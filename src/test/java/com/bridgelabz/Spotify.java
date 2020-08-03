package com.bridgelabz;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class Spotify {

	String userid;
	String Token;
	String plid1,plid2,plid3;
	public String[] tracks;

	@BeforeMethod
	public void setup() {
		Token = "Bearer BQDOjorbDCSghOexEkFflWtUztiwUkdWhPY19867OFML_cbJxFs_tma9ZsKfvaNX2uVcZq1rmvjgiivre2rZJd_dI3oL9j4S7COZ-rDqDM5mPz_NmdQ3pndjTOZLTIJMzeuatA7vPvgm5H1xE_rOAMw-GvJYl5_Ci0W_PNhVzlB-erNe5IpoqcFI7XbbQqvoiFL7oiHoAITRVb_bT3TZwC3x-g-6CUTeYrCvOYxo4SHlQ6FpGtaNtaqSIqzoghziTJNWG923czFmPIi_Ka298qtVUTbManev";
		}
	//to get user id and user profile
	@Test
	public void togetTheUserProfileAndPlaylistDetails () {
		String url = "https://api.spotify.com/v1/me";
		Response response =  given()
				.header("Authorization", Token)
				.when()
				.get(url);
		userid = response.path("id");
		System.out.println("userId=" + userid);
		System.out.println(response.getBody().asString());
	
	//to create a new playlist 
/*		String url1 = "https://api.spotify.com/v1/users/{user_id}/playlists";
		Response addplaylist =  given()
				.accept("application/json")
				.contentType("application/json")               
				.header("Authorization", Token)
				.body("{\"name\": \" Hollywood  \",\"description\": \"English songs\",\"public\": false}")
				.pathParam("user_id",userid)
				.when()
				.post(url1); 
	 */

	// to get total no of play lists and it's id
		String url2 = "https://api.spotify.com/v1/users/{user_id}/playlists";
		Response list = given()
				.header("Authorization", Token)
				.pathParam("user_id", userid)
				.when()
				.get(url2);
		int count = list.path("total");
		System.out.println("total no.of playlists=" + count);
		plid1 = list.path("items[0].id");
		plid2 = list.path("items[1].id");
		plid3 = list.path("items[2].id");
		String desc = list.path("items[0].description");       
		System.out.println("1st playlist description=" +desc);
		System.out.println("1st Playlist id is = " + plid1);
		System.out.println("2nd Playlist id is = " + plid2);
		System.out.println("3rd Playlist id is = " + plid3);

	// to get all the tracks in a play list
		
		String url3 = "https://api.spotify.com/v1/playlists/{playlist_id}/tracks";
		Response items = given()
				.header("Authorization", Token)
				.pathParam("playlist_id", plid2)
				.when()
				.get(url3);
		int all = items.path("total");
		System.out.println("total no of tracks=" +all);
		tracks = new String[all];
		for (int i = 0; i < tracks.length; i++) {
			tracks[i] = items.path("items[" + i + "].track.uri"); 
		}
		for (int j = 0; j < all; j++) {
			System.out.println("track uris" + j + tracks[j]);
	}
	

/*	// delete a track from a play list
		String url4 = "https://api.spotify.com/v1/playlists/{playlist_id}/tracks";
		Response remove = given()
				.header("Authorization", Token)
				.pathParam("playlist_id", plid2)
				.body("{\"uris\": [\"" + tracks[1] + "\"]}")
				.when()
				.delete(url4);
		remove.prettyPrint(); 
	 
	 
	//add a track in play list 
		String url5 = "https://api.spotify.com/v1/playlists/{playlist_id}/tracks";
		Response add = given()
				.header("Authorization", Token)
				.pathParam("playlist_id", plid1)
				.body("{\"uris\": [\"" + tracks[1] + "\"]}")
				.when()
				.post(url5);
		add.prettyPrint();
*/	

	//Change Play list Details
		Response change = given()
				.accept("application/json")
				.contentType("application/json")
				.header("Authorization", Token)
				.pathParam("playlist_id", plid1)
				.body("{\"name\": \" International \",\"description\": \"New playlist description\",\"public\": true}")
				.when()
				.put("https://api.spotify.com/v1/playlists/{playlist_id}");
		response.then().assertThat().statusCode(200); 	        	             

		Response replace = given()
				.accept("application/json")
				.contentType("application/json")
				.header("Authorization", Token)
				.pathParam("playlist_id", plid2)
				.body("{\"range_start\": \" 0 \",\"range_length\": \"1\",\"insert_before\": 2}")
				.when()
				.put("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");
		response.then().assertThat().statusCode(200); 	        	             

		
	} 

}
