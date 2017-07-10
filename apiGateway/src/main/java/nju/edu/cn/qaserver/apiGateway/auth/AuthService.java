package nju.edu.cn.qaserver.apiGateway.auth;

/**
 * Created by nju.edu.cn.qaserver on 2017/6/27.
 */
public interface AuthService {

    Boolean register(String username, String password);

    String login(String username, String password);

    String refresh(String oldToken);
}
