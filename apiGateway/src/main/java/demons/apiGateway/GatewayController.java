package demons.apiGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Enumeration;

@RestController
public class GatewayController {

    @Autowired
    private RestTemplate restTemplate;

    public String getServiceUrl(String requestURI)
            throws NoSuchApiException {
        return "http:/" + requestURI;
    }

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
    @ResponseBody
    public ResponseEntity<Object> onlyDispatchNoBodyParamsRequest(
            HttpServletRequest request) throws Exception {
        String method = request.getMethod();
        HttpMethod httpMethod;
        if (method.equals("GET")) {
            httpMethod = HttpMethod.GET;
        } else if (method.equals("POST")) {
            httpMethod = HttpMethod.POST;
        } else if (method.equals("DELETE")) {
            httpMethod = HttpMethod.DELETE;
        } else {
            throw new NoSuchApiException(request);
        }

        String requestURI = request.getRequestURI();
        URI uri;
        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
            uri = new URI(getServiceUrl(requestURI) + "?" + request.getQueryString());
        } else {
            uri = new URI(getServiceUrl(requestURI));
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        HttpHeaders headers = new HttpHeaders();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.add(headerName, headerValue);
        }
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        return restTemplate.exchange(
                uri,
                httpMethod,
                entity,
                Object.class
        );
    }
}
