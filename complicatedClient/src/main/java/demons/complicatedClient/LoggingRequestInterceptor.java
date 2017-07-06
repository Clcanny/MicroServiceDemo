package demons.complicatedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Created by demons on 2017/7/6.
 */
@Component
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Value("${spring.application.name}")
    private String name;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        ClientHttpResponse response = execution.execute(request, body);

        log(request, body, response);

        return response;
    }

    private void log(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
        //do logging
//        System.out.println(clientNameProperty.getName());
        System.out.println(name);
        System.out.println(request);
        System.out.println(request.getURI());
    }
}
