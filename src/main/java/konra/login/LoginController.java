package konra.login;

import konra.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final LoginService service;

    @Autowired
    public LoginController(LoginService service) {
        this.service = service;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public LoginResponse register(@ModelAttribute User user){

        service.newUser(user);
        return new LoginResponse("success");
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public LoginResponse login(@ModelAttribute User user, HttpServletRequest request){

        User authenticated = service.authenticateUser(user);

        LoginResponse rsp = new LoginResponse();
        if(authenticated != null){
            rsp.setStatus("success");
            rsp.setToken(LoginInterceptor.makeToken(user));
            rsp.setUser(authenticated);

            request.getSession(true).setAttribute("user", user);
        } else {
            rsp.setStatus("failed");
        }

        return rsp;
    }

    @RequestMapping(value = {"/login_page", "/"})
    public void logPage(HttpServletResponse response){

        response.setStatus(301);
        response.setHeader("Location", "http://localhost:8090/view/login.html");
    }
}
