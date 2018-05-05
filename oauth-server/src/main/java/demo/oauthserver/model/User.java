package demo.oauthserver.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.base.Joiner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity(name="tbl_user")
public class User implements Serializable {
    
	private static final long serialVersionUID = -8023188009052888630L;

	public static final String USER_NAME = "username";
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;
    
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String firstname;
    
    @Column(nullable = false)
    private String lastname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<Role>();

    public User() {
    }

    public User(String username, String passwordHash) {
        this.username = username;
        this.password = passwordHash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

	public Collection<GrantedAuthority> getAuthorities() {
		if (roles == null) {
            return Collections.emptyList();
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles) {
        	authorities.add(new SimpleGrantedAuthority(role.getName()));
        	
        }
        return authorities;
    }
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof User) 
			return ((User) other).getId() == this.id;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return id == null ? "".hashCode() : id.hashCode();
	}

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("ID=").append(this.id == null ? "" : this.id).append("|");
        sb.append("Username=").append(this.username == null ? "" : this.username).append("|");
        sb.append("Firstname=").append(this.firstname == null ? "" : this.firstname).append("|");
        sb.append("Lastname=").append(this.lastname == null ? "" : this.lastname).append("|");
        
        
        String roles = Joiner.on(", ").join(this.getAuthorities()).replace("[", "")
                .replace("]", "");
        sb.append("Roles=").append(roles);


        return sb.toString();
    }
}
