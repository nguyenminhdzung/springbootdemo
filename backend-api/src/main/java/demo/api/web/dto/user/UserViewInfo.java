package demo.api.web.dto.user;

import java.util.ArrayList;
import java.util.List;

import demo.api.model.Role;
import demo.api.model.User;


public class UserViewInfo
{
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String address1;
    private String address2;
    private String address3;
    
	private List<String> roles;
	
	public UserViewInfo() {}
    
    public UserViewInfo(User user) {
    	this.id = user.getId();    	
    	this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.address1 = user.getAddress1();
        this.address2 = user.getAddress2();
        this.address3 = user.getAddress3();
        
        this.roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
	        roles.add(role.getName());
	    }
    }
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}