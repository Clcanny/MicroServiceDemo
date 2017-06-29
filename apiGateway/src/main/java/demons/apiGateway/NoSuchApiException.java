package demons.apiGateway;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by demons on 2017/6/29.
 */
public class NoSuchApiException extends Exception {

    public NoSuchApiException(HttpServletRequest httpServletRequest) {
        super(httpServletRequest.toString());
    }
}
