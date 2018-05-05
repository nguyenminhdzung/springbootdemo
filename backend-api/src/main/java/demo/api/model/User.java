package demo.api.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import com.google.common.base.Joiner;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name="tbl_user")
public class User implements Serializable {

	private static final long serialVersionUID = 7895861764856491046L;

	public static final int GENDER_MALE = 1;
	public static final int GENDER_FEMALE = 2;
	
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
    
    @Column
    private String email;
    
    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private String address3;
    
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("ID=").append(this.id == null ? "" : this.id).append("|");
        sb.append("Username=").append(this.getUsername()).append("|");
        sb.append("Firstname=").append(this.firstname == null ? "" : this.firstname).append("|");
        sb.append("Lastname=").append(this.lastname == null ? "" : this.lastname).append("|");
        sb.append("Email=").append(this.email == null ? "" : this.email).append("|");
        sb.append("Address1=").append(this.address1 == null ? "" : this.address1).append("|");
        sb.append("Address2=").append(this.address2 == null ? "" : this.address2).append("|");
        sb.append("Address3=").append(this.address3 == null ? "" : this.address3).append("|");        
                
        String roles = this.roles == null ? ""
        						          : Joiner.on(", ").join(this.roles);
        sb.append("Roles=").append(roles);


        return sb.toString();
    }
}
