package konra.login;

import konra.common.Response;
import konra.common.User;

/**
 * Created by konra on 22.07.2017.
 */
public class LoginResponse extends Response {

    private String token;
    private User user;

    public LoginResponse() {
    }

    public LoginResponse(String status){
        super(status);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
