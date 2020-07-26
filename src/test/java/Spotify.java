import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Spotify {
	
	String userid="";
    String Token ="";
    
    @BeforeMethod
    public void setup() {
    Token = "Bearer BQBCOAWOKP8w4Z2onWrX6QX7SLdDFFTvms5Aqvev44gWLtRb02IZdhUc-0rTnkVPqISPlYQHj4a7lEIYHnFYj9CJ7rejg17M7YrwOgOtxoOU7reGW2BbjbxHmCNWfFqgY4N8q6fTGL8HVhPta9AmZKwTbUs1ldYexTIEGMfSz_LgnnsgxyTHAHbIpp_35U0pRiCaoLcCFdKJPyoHPAweXnXq7xQqmT_z1an-qWSY4atO_ysyQ2Hk4ug2XtiPv96QwF1T2JrtIk06OdX9aTDAXlffxjnH9WJZ";      
   }
	
	@Test
  public void togetuserid () {
	
		Response response =  RestAssured.given()			
				.header("Authorization", Token)
                .when()
				.get("https://api.spotify.com/v1/me");
		 userid = response.path("id");
	        System.out.println("userId=" + userid);
	
		}
	
}
