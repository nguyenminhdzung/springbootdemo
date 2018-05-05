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
import demo.api.web.dto.profile.ChangePasswordInput;
import demo.api.web.dto.profile.ProfileChangeInfo;
import demo.api.web.dto.role.RoleInfo;
import demo.api.web.dto.user.UserInputInfo;
import demo.api.web.dto.user.UserViewInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProfileEndpointTest {

	@Autowired
	private TestRestTemplate template;
	
	private String adminAccessToken;
	private String testUsername = "testUsername";
	private String testPassword = "testPassword";
	private ObjectMapper objectMapper = new ObjectMapper();

	@Before
	public void setup() throws Exception {
		this.adminAccessToken = TestUtils.obtainAdminOAuthAccessToken();
	}
	
	@After
	public void cleanup() throws Exception {
		
		deleteTestingUserData();
	}
	
	@Test
	public void testGetProfile() throws Exception {
		UserInputInfo inputInfo = new UserInputInfo();
		inputInfo.setUsername(testUsername);
		inputInfo.setPassword(testPassword);
		inputInfo.setFirstname("test firstname");
		inputInfo.setLastname("test lastname");
		inputInfo.setEmail("test@demo.com");
		inputInfo.setAddress1("test address 1");
		inputInfo.setAddress2("test address 2");
		inputInfo.setAddress3("test address 3");
		inputInfo.setRoles(Lists.newArrayList(Role.ROLE_USER));
		
		HttpEntity inputEntity = createEntity(objectMapper.writeValueAsString(inputInfo));
		
		ResponseEntity<APIOutputInfo<UserViewInfo>> response = template.exchange("/v0/user/create", HttpMethod.POST, inputEntity, new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		UserViewInfo savedUser = response.getBody().getData();
		
		String accessToken = TestUtils.obtainOAuthAccessToken(testUsername, testPassword);
		
		HttpEntity entity = new HttpEntity(createBearerHeader(accessToken));
		response = template.exchange("/v0/profile", HttpMethod.GET, entity, new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		
		UserViewInfo profile = response.getBody().getData();
		Assert.assertTrue(savedUser.getId().equals(profile.getId()));
	}
	
	@Test
	public void testUpdateProfile() throws Exception {
		UserInputInfo inputInfo = new UserInputInfo();
		inputInfo.setUsername(testUsername);
		inputInfo.setPassword(testPassword);
		inputInfo.setFirstname("test firstname");
		inputInfo.setLastname("test lastname");
		inputInfo.setEmail("test@demo.com");
		inputInfo.setAddress1("test address 1");
		inputInfo.setAddress2("test address 2");
		inputInfo.setAddress3("test address 3");
		inputInfo.setRoles(Lists.newArrayList(Role.ROLE_USER));
		
		HttpEntity inputEntity = createEntity(objectMapper.writeValueAsString(inputInfo));
		
		ResponseEntity<APIOutputInfo<UserViewInfo>> response = template.exchange("/v0/user/create", HttpMethod.POST, inputEntity, new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		
		UserViewInfo savedUser = response.getBody().getData();
		
		ProfileChangeInfo changeInfo = new ProfileChangeInfo();
		changeInfo.setFirstname("updated firstname");
		changeInfo.setLastname("updated lastname");
		changeInfo.setEmail("updated@demo.com");
		changeInfo.setAddress1("updated address 1");
		changeInfo.setAddress2("updated address 2");
		changeInfo.setAddress3("updated address 3");
		
		String accessToken = TestUtils.obtainOAuthAccessToken(testUsername, testPassword);
		
		HttpEntity updateEntity = new HttpEntity(objectMapper.writeValueAsString(changeInfo), createBearerHeader(accessToken));
		response = template.exchange("/v0/profile", HttpMethod.POST, updateEntity, new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		
		savedUser = response.getBody().getData();
		Assert.assertTrue(savedUser.getFirstname().contains("updated") && savedUser.getLastname().contains("updated")
						&& savedUser.getEmail().contains("updated") && savedUser.getAddress1().contains("updated")
						&& savedUser.getAddress2().contains("updated") && savedUser.getAddress3().contains("updated")
						&& savedUser.getRoles().contains(Role.ROLE_USER));
	}
	
	@Test
	public void testChangePassword() throws Exception {
		UserInputInfo inputInfo = new UserInputInfo();
		inputInfo.setUsername(testUsername);
		inputInfo.setPassword(testPassword);
		inputInfo.setFirstname("test firstname");
		inputInfo.setLastname("test lastname");
		inputInfo.setEmail("test@demo.com");
		inputInfo.setAddress1("test address 1");
		inputInfo.setAddress2("test address 2");
		inputInfo.setAddress3("test address 3");
		inputInfo.setRoles(Lists.newArrayList(Role.ROLE_USER));
		
		HttpEntity inputEntity = createEntity(objectMapper.writeValueAsString(inputInfo));
		
		ResponseEntity<APIOutputInfo<UserViewInfo>> response = template.exchange("/v0/user/create", HttpMethod.POST, inputEntity, new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		
		UserViewInfo savedUser = response.getBody().getData();
		
		ChangePasswordInput changeInfo = new ChangePasswordInput();
		changeInfo.setPassword(testPassword);
		changeInfo.setNewPassword(testPassword + "1");
		
		String accessToken = TestUtils.obtainOAuthAccessToken(testUsername, testPassword);
		
		HttpEntity updateEntity = new HttpEntity(objectMapper.writeValueAsString(changeInfo), createBearerHeader(accessToken));
		response = template.exchange("/v0/profile/changePassword", HttpMethod.POST, updateEntity, new ParameterizedTypeReference<APIOutputInfo<UserViewInfo>>() {});
		
		Assert.assertTrue(response.getBody().isSuccess());
	}
	
	private void deleteTestingUserData() throws Exception {
		
		ResponseEntity<APIOutputInfo<PageInfo>> response = template.exchange("/v0/user/list?keyword=" + testUsername, HttpMethod.GET, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<PageInfo>>() {});
		
		if (response.getBody().getData().getTotalElements() > 0) {	
			LinkedHashMap userInfo = (LinkedHashMap) response.getBody().getData().getData().get(0);
			ResponseEntity<APIOutputInfo<Void>> deleteResponse = template.exchange("/v0/user/delete/" + userInfo.get("id"), HttpMethod.DELETE, new HttpEntity(createBearerHeader()), new ParameterizedTypeReference<APIOutputInfo<Void>>() {});
			
			Assert.assertTrue(deleteResponse.getBody().isSuccess());
		}
	}
	
	private HttpHeaders createBearerHeader() {
		return createBearerHeader(adminAccessToken);
	}
	
	private HttpHeaders createBearerHeader(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	private HttpEntity createEntity(Object body) {
		return new HttpEntity(body, createBearerHeader());
	}
}
