package konra.game;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GameController {

    private BubbleGame service;
    private Gson gson;

    @Autowired
    public GameController(BubbleGame service) {
        this.service = service;
        this.gson = new Gson();
    }

    @RequestMapping(value = "/state", produces = "application/json")
    public ResponseEntity<String> state(){

        MainBubble bubble = service.getBubble();

        return new ResponseEntity<>(gson.toJson(bubble), HttpStatus.OK);
    }


}
