package demo.api.endpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

import demo.api.web.APIOutputInfo;
import demo.api.web.dto.role.RoleInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoleEndpointTest {

	@Autowired
	private TestRestTemplate template;
	
	private String accessToken;

	@Before
	public void setup() throws Exception {
		this.accessToken = TestUtils.obtainAdminOAuthAccessToken();
	}

	@Test
	public void testListAll() throws Exception {
		ResponseEntity<APIOutputInfo<List<RoleInfo>>> response = template.exchange("/v0/role/all", HttpMethod.GET, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<List<RoleInfo>>>() {});
		
		Assert.assertTrue(response.getBody().isSuccess());
		Assert.assertTrue(response.getBody().getData().size() > 0);
	}

	@Test
	public void testGetById() throws Exception {
		ResponseEntity<APIOutputInfo<List<RoleInfo>>> response = template.exchange("/v0/role/all", HttpMethod.GET, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<List<RoleInfo>>>() {});
		RoleInfo roleInfo = response.getBody().getData().get(0);
		ResponseEntity<APIOutputInfo<RoleInfo>> getOneResponse = template.exchange("/v0/role/get/" + roleInfo.getId(), HttpMethod.GET, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<RoleInfo>>() {});
		
		Assert.assertTrue(getOneResponse.getBody().isSuccess());
		RoleInfo readRoleInfo = getOneResponse.getBody().getData();
		Assert.assertTrue(roleInfo.getId().equals(readRoleInfo.getId()) 
						  && roleInfo.getName().equals(readRoleInfo.getName()));
	}

	@Test
	public void testGetByIdShoudNotFound() throws Exception {
		ResponseEntity<String> getOneResponse = template.exchange("/v0/role/get/" + UUID.randomUUID().toString(), HttpMethod.GET, new HttpEntity(createBearerHeader()), String.class);
		
		Assert.assertTrue(getOneResponse.getStatusCode() == HttpStatus.NOT_FOUND);
	}
	
	private HttpHeaders createBearerHeader() {
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
