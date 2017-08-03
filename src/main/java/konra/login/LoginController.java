package konra.login;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by konra on 22.07.2017.
 */
@Controller
public class LoginController {

    private final Gson gson;
    private final LoginService service;

    @Autowired
    public LoginController(LoginService service) {
        this.service = service;
        gson = new Gson();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> register(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "password") String password,
                                   @RequestParam(value = "email") String email){


        service.newUser(username, password, email);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setStatus("success");

        return new ResponseEntity<>(gson.toJson(loginResponse), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> login(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "password") String password,
                                        HttpServletRequest request){

        User user  = service.getUser(username);

        LoginResponse loginResponse = new LoginResponse();

        if(user != null && user.getPassword().equals(password)){
            loginResponse.setStatus("success");
            loginResponse.setToken("dupa");

            request.getSession(true).setAttribute("user", user);
        } else {
            loginResponse.setStatus("failed");
        }

        ResponseEntity response = new ResponseEntity<>(gson.toJson(loginResponse), HttpStatus.OK);

        return response;
    }

    @RequestMapping(value = "/login_page")
    public String logPage(){

        return "view/login.html";
    }
}
