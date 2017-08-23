package konra.game.farm;

import com.google.gson.Gson;
import konra.common.GenericResponse;
import konra.login.LoginService;
import konra.common.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
public class FarmController {

    private BubbleFarm game;
    private LoginService service;
    private Gson gson;
    private Logger log;
    private Environment env;

    @Autowired
    public FarmController(BubbleFarm game, Environment env, LoginService service){
        this.game = game;
        this.env = env;
        this.service = service;
        gson = new Gson();
        log = Logger.getLogger(FarmController.class);
    }

    @RequestMapping(value = "/farmstate", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getState(HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        List<FarmBubble> bubbles = game.getBubbles();
        int balance = user.getBalance().getValue();

        FarmResponse response = new FarmResponse();
        response.setStatus("success");
        response.setBubbles(bubbles);
        response.setBalance(balance);

        String json = gson.toJson(response);
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/bet", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> makeBet(HttpServletRequest request,
                                          @RequestParam(name = "bubble_id") String bubbleId,
                                          @RequestParam(name = "amount") int amount){

        User user = (User) request.getSession().getAttribute("user");

        boolean betPlaced = game.makeBet(user, amount, bubbleId);

        GenericResponse response = new GenericResponse();
        String status = betPlaced ? "success" : "failed";
        response.setStatus(status);

        String json = gson.toJson(response);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(value = "/redeem", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> redeem(HttpServletRequest request,
                                          @RequestParam(name = "bubble_id") String bubbleId){

        User user = (User) request.getSession().getAttribute("user");

        boolean result = game.redeem(user, bubbleId);

        GenericResponse response = new GenericResponse();
        if(result) response.setStatus("success");
        else response.setStatus("failed");

        String json = gson.toJson(response);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

}
