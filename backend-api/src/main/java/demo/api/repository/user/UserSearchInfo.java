package demo.api.repository.user;

import java.io.Serializable;

public class UserSearchInfo implements Serializable{

	private static final long serialVersionUID = 6172702563886642779L;
	
	private String keyword;
	
	public UserSearchInfo(){
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}