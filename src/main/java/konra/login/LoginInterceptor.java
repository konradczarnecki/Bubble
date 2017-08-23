package konra.login;

import konra.common.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        User usr = (User) request.getSession().getAttribute("user");
        String token = request.getHeader("token") != null ? request.getHeader("token") : request.getParameter("token");
        return verifyToken(token, usr);
    }

    public static String makeToken(User user){
        String toHash = user.getUsername() + 47 + user.getPassword();
        return String.valueOf(Math.abs(toHash.hashCode()));
    }

    public static boolean verifyToken(String token, User user){
        String toHash = (user.getUsername() + 47 + user.getPassword());
        String hash = String.valueOf(Math.abs(toHash.hashCode()));
        return token.equals(hash);
    }
}
