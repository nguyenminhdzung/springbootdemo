package demo.api.endpoint;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import demo.api.model.Role;
import demo.api.util.PageInfo;
import demo.api.web.APIOutputInfo;
import demo.api.web.dto.role.RoleInfo;
import demo.api.web.dto.user.UserInputInfo;
import demo.api.web.dto.user.UserViewInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserEndpointTest {

	@Autowired
	private TestRestTemplate template;
	
	private String accessToken;
	private String testUsername = "testUsername";
	private ObjectMapper objectMapper = new ObjectMapper();

	@Before
	public void setup() throws Exception {
		this.accessToken = TestUtils.obtainAdminOAuthAccessToken();
	}
	
	@After
	public void cleanup() throws Exception {
		testDelete();
	}

	@Test
	public void testList() throws Exception {
		ResponseEntity<APIOutputInfo<PageInfo>> response = template.exchange("/v0/user/list?keyword=admin", HttpMethod.GET, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<PageInfo>>() {});
		
		Assert.assertTrue(response.getBody().isSuccess());
		Assert.assertTrue(response.getBody().getData().getTotalElements() > 0);
	}

	@Test
	public void testGetById() throws Exception {
		ResponseEntity<APIOutputInfo<PageInfo>> response = template.exchange("/v0/user/list?keyword=admin", HttpMethod.GET, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<PageInfo>>() {});
		
		LinkedHashMap userInfo = (LinkedHashMap) response.getBody().getData().getData().get(0);
		ResponseEntity<APIOutputInfo<UserViewInfo>> getOneResponse = template.exchange("/v0/user/get/" + userInfo.get("id"), HttpMethod.GET, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		
		Assert.assertTrue(getOneResponse.getBody().isSuccess());
		UserViewInfo readUserInfo = getOneResponse.getBody().getData();
		Assert.assertTrue(userInfo.get("id").equals(readUserInfo.getId()) 
						  && userInfo.get("username").equals(readUserInfo.getUsername()));
	}
	
	@Test
	public void testCreate() throws Exception {
		UserInputInfo inputInfo = new UserInputInfo();
		inputInfo.setUsername(testUsername);
		inputInfo.setFirstname("test firstname");
		inputInfo.setLastname("test lastname");
		inputInfo.setPassword("test password");
		inputInfo.setEmail("test@demo.com");
		inputInfo.setAddress1("test address 1");
		inputInfo.setAddress2("test address 2");
		inputInfo.setAddress3("test address 3");
		inputInfo.setRoles(Lists.newArrayList(Role.ROLE_USER));
		
		HttpEntity inputEntity = createEntity(objectMapper.writeValueAsString(inputInfo));
		
		ResponseEntity<APIOutputInfo<UserViewInfo>> response = template.exchange("/v0/user/create", HttpMethod.POST, inputEntity, new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		
		Assert.assertTrue(response.getBody().isSuccess());
		UserViewInfo savedUser = response.getBody().getData();
		
		Assert.assertTrue(StringUtils.isNotBlank(savedUser.getId()) && savedUser.getUsername().equals(testUsername));
	}
	
	@Test
	public void testDelete() throws Exception {
		
		ResponseEntity<APIOutputInfo<PageInfo>> response = template.exchange("/v0/user/list?keyword=" + testUsername, HttpMethod.GET, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<PageInfo>>() {});
		
		if (response.getBody().getData().getTotalElements() > 0) {	
			LinkedHashMap userInfo = (LinkedHashMap) response.getBody().getData().getData().get(0);
			ResponseEntity<APIOutputInfo<Void>> deleteResponse = template.exchange("/v0/user/delete/" + userInfo.get("id"), HttpMethod.DELETE, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<Void>>() {});
			
			Assert.assertTrue(deleteResponse.getBody().isSuccess());
		}
	}
	
	@Test
	public void testUpdate() throws Exception {
		UserInputInfo inputInfo = new UserInputInfo();
		inputInfo.setUsername(testUsername);
		inputInfo.setFirstname("test firstname");
		inputInfo.setLastname("test lastname");
		inputInfo.setPassword("test password");
		inputInfo.setEmail("test@demo.com");
		inputInfo.setAddress1("test address 1");
		inputInfo.setAddress2("test address 2");
		inputInfo.setAddress3("test address 3");
		inputInfo.setRoles(Lists.newArrayList(Role.ROLE_USER));
		
		HttpEntity inputEntity = createEntity(objectMapper.writeValueAsString(inputInfo));
		
		ResponseEntity<APIOutputInfo<UserViewInfo>> response = template.exchange("/v0/user/create", HttpMethod.POST, inputEntity, new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		
		UserViewInfo savedUser = response.getBody().getData();
		
		inputInfo.setId(savedUser.getId());
		inputInfo.setFirstname("updated firstname");
		inputInfo.setLastname("updated lastname");
		inputInfo.setEmail("updated@demo.com");
		inputInfo.setAddress1("updated address 1");
		inputInfo.setAddress2("updated address 2");
		inputInfo.setAddress3("updated address 3");
		inputInfo.setRoles(Lists.newArrayList(Role.ROLE_ADMIN));
		
		inputEntity = createEntity(objectMapper.writeValueAsString(inputInfo));
		response = template.exchange("/v0/user/update", HttpMethod.POST, inputEntity, new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		
		savedUser = response.getBody().getData();
		Assert.assertTrue(savedUser.getFirstname().contains("updated") && savedUser.getLastname().contains("updated")
						&& savedUser.getEmail().contains("updated") && savedUser.getAddress1().contains("updated")
						&& savedUser.getAddress2().contains("updated") && savedUser.getAddress3().contains("updated")
						&& savedUser.getRoles().contains(Role.ROLE_ADMIN));
		
	}
	
	private HttpHeaders createBearerHeader() {
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	private HttpEntity createEntity(Object body) {
		return new HttpEntity(body, createBearerHeader());
	}
}
