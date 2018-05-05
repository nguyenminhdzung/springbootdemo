package demo.api.web.dto.role;

import java.io.Serializable;

import demo.api.model.Role;

public class RoleInfo implements Serializable
{
	private static final long serialVersionUID = 1385364582319999302L;
	
	private String id;
    private String name;
    
    public RoleInfo() {}
    
    public RoleInfo(Role role) {    
    	this.id = role.getId();
    	this.name = role.getName();
    }

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}