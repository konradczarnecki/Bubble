package konra.login;

import konra.common.GenericResponse;

/**
 * Created by konra on 22.07.2017.
 */
public class LoginResponse extends GenericResponse {

    private String token;

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
}
