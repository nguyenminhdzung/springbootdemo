package demo.api.endpoint;

import java.util.HashMap;

import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
	public static String obtainAdminOAuthAccessToken() {
		return obtainOAuthAccessToken("admin", "admin");
	}
	
	public static String obtainOAuthAccessToken(String username, String password) {
		try {
			ResponseEntity<String> response = new TestRestTemplate("test-client", "test-client").postForEntity("http://localhost:8585/oauth/token?grant_type=password&username=" + username + "&password=" + password, null, String.class);
			HashMap tokenMap = new ObjectMapper().readValue(response.getBody(), HashMap.class);
	        return (String) tokenMap.get("access_token");
		} catch (Exception e) {
			return "";
		}
	}
}
