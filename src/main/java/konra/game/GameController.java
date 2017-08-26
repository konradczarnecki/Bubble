package konra.game;

import konra.common.Response;
import konra.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.crypto.Cipher;

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

        User user = (User) request.getSession().getAttribute("user");
        StateResponse rsp = new StateResponse();

        if(user != null){
            rsp.setStatus("success");
            rsp.setBets(service.getBets());
            rsp.setBubble(service.getBubble());
            rsp.setBalance(user.getBalance());

        } else rsp.setStatus("failed");

        return rsp;
    }

    @ResponseBody
    @RequestMapping(value = "/bet", produces = "application/json", method = RequestMethod.POST)
    public Response<Integer> makeBet(@RequestParam(value = "amount") int amount, HttpServletRequest request){

        User usr = (User) request.getSession().getAttribute("user");
        boolean betSuccessful = service.makeBet(usr, amount);

        Response<Integer> rsp = new Response<>();

        if(betSuccessful){
            rsp.setStatus("success");
            rsp.setItem(usr.getBalance().getValue());

        } else rsp.setStatus("failed");

        return rsp;
    }

    @ResponseBody
    @RequestMapping(value = "/redeem", produces = "application/json", method = RequestMethod.POST)
    public Response<Integer> redeem(@RequestParam(value = "stamp") long timestamp,
                                    @RequestParam(value = "tkn") String hash,
                                    @RequestParam(value = "balance") int balance,
                                    @RequestParam(value = "bubble") int bubId,
                                    HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        int prize = service.redeem(user, bubId);

        Response<Integer> rsp = new Response<>();

        if(prize != -1){
            rsp.setStatus("success");
            rsp.setItem(user.getBalance().getValue());

        } else rsp.setStatus("failed");

        return rsp;
    }
}
