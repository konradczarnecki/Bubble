package konra.game;


import konra.common.Balance;
import konra.common.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameServiceTest {

    private static String USERNAME = "username";
    private static int INITIAL_BALANCE = 1000;

    GameService service;
    User user;

    @Before
    public void prepare(){

        service = new GameService();

        user = new User();
        user.setUsername(USERNAME);
        Balance b =new Balance();
        b.setValue(INITIAL_BALANCE);
        user.setBalance(b);
    }
    @Test
    public void testInitAndBubbleUpdate() throws InterruptedException {

        service.init();

        assertNotNull(service.getBubble());
        assertNotNull(service.getBets());

        int progressBefore = service.getBubble().getProgress();
        Thread.sleep(GameService.BUBBLE_DELAY + 50);
        int progressAfter = service.getBubble().getProgress();
        assertNotEquals(progressBefore, progressAfter);
    }

    @Test
    public void testNewBubbleCreation() throws InterruptedException {

        service.init();

        service.getBubble().setMax(2);
        int oldId = service.getBubble().getId();
        Thread.sleep(GameService.BUBBLE_DELAY + 2000);
        int newId = service.getBubble().getId();
        assertNotEquals(oldId, newId);
    }

    @Test
    public void testMakeBetSuccess(){

        service.init();

        int betAmount = 100;
        int bubbleProgressBeforeBet = service.getBubble().getProgress();

        boolean betMade = service.makeBet(user, betAmount);
        assertTrue(betMade);

        Bet bet = service.getBets().get(0);
        assertNotNull(bet);
        assertEquals(betAmount, bet.getAmount());
        assertEquals(USERNAME, bet.getUser().getUsername());
        assertTrue(bubbleProgressBeforeBet <= bet.getBase());
        assertEquals(INITIAL_BALANCE - betAmount, user.getBalance().getValue());
    }

    @Test
    public void testMakeBetFail(){

        service.init();

        assertFalse(service.makeBet(null, 100));
        assertFalse(service.makeBet(user, 3000));

        service.getRedeemed().add(user);
        assertFalse(service.makeBet(user, 100));
    }
}
