package demons.account;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by demons on 2017/6/30.
 */
public class NoSuchApiException extends Exception {

    public NoSuchApiException(HttpServletRequest request) {
        super(request.toString());
    }
}
