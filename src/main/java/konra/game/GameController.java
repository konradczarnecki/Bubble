package konra.game;

import konra.common.GenericResponse;
import konra.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GameController {

    private GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @ResponseBody
    @RequestMapping(value = "/gamestate", produces = "application/json")
    public StateResponse state(HttpServletRequest request){

        User usr = (User) request.getSession().getAttribute("user");
        StateResponse rsp = new StateResponse();

        if(usr != null){
            rsp.setStatus("success");
            rsp.setBets(service.getBets());
            rsp.setBubble(service.getBubble());
            rsp.setBalance(usr.getBalance());

        } else rsp.setStatus("failed");

        return rsp;
    }

    @ResponseBody
    @RequestMapping(value = "/bet", produces = "application/json")
    public GenericResponse makeBet(@RequestParam(value = "amount") int amount, HttpServletRequest request){

        User usr = (User) request.getSession().getAttribute("user");
        boolean betSuccessful = service.makeBet(usr, amount);

        return betSuccessful ? new GenericResponse("success") : new GenericResponse("failed");
    }
}
