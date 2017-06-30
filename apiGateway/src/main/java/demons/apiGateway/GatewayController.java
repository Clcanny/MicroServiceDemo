package demons.apiGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GatewayController {

    @Autowired
    private ApiGatewayProperties apiGatewayProperties;

    @Autowired
    private RestTemplate restTemplate;

    public String getServiceUrl(String requestURI, HttpServletRequest httpServletRequest)
            throws NoSuchApiException {
        System.out.print(apiGatewayProperties.getEndpoints().size());
        System.out.print(apiGatewayProperties.getEndpoints().get(0));
        ApiGatewayProperties.Endpoint endpoint =
                apiGatewayProperties.getEndpoints().stream()
                        .filter(e ->
                                requestURI.matches(e.getPath()) && e.getMethod() == RequestMethod.valueOf(httpServletRequest.getMethod())
                        )
                        .findFirst().orElseThrow(() -> new NoSuchApiException(httpServletRequest));

        return endpoint.getLocation() + requestURI;
    }

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
    @ResponseBody
    public ResponseEntity<Object> onlyDispatchNoBodyParamsRequest(
            HttpServletRequest request,
            HttpEntity<Object> entity) throws Exception {
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

        return restTemplate.exchange(
                getServiceUrl(request.getRequestURI(), request),
                httpMethod,
                entity,
                Object.class
        );
    }
}
