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
@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST)
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

    @CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> login(@RequestBody String body, HttpServletResponse rsp, HttpServletRequest request){

        User logged = gson.fromJson(body, User.class);
        User user  = service.getUser(logged.getUsername());

        LoginResponse loginResponse = new LoginResponse();

        if(user != null && user.getPassword().equals(logged.getPassword())){
            loginResponse.setStatus("success");
            loginResponse.setToken("dupa");

            request.getSession(true).setAttribute("user", user);
        } else {
            loginResponse.setStatus("failed");
        }

        rsp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        rsp.setHeader("Access-Control-Allow-Methods", "POST");
        rsp.setHeader("Access-Control-Allow-Headers", "Authorization");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
        headers.add("Access-Control-Allow-Methods", "POST");
        headers.add("Access-Control-Allow-Headers", "Authorization");

        ResponseEntity response = new ResponseEntity<>(gson.toJson(loginResponse),headers, HttpStatus.OK);

        return response;
    }
}
