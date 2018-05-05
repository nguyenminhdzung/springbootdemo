package demo.api.web.dto.user;

import demo.api.model.User;

public class UserInputInfo extends UserViewInfo {

    public UserInputInfo() { }

    public UserInputInfo(User user) {
    	super(user);
        this.password = user.getPassword();
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
