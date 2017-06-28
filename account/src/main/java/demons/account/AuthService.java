package demons.account;

/**
 * Created by demons on 2017/6/27.
 */
public interface AuthService {

    Boolean register(String username, String password);

    String login(String username, String password);

    String refresh(String oldToken);
}
