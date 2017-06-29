package demons.apiGateway;

import demons.apiGateway.utils.ContentRequestTransformer;
import demons.apiGateway.utils.HeadersRequestTransformer;
import demons.apiGateway.utils.URLRequestTransformer;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
public class GatewayController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiGatewayProperties apiGatewayProperties;

    @Autowired
    private RestTemplate restTemplate;

    private HttpClient httpClient;

    public static String getBody(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    @PostConstruct
    public void init() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    @RequestMapping(value = "/api/**", method = {
            RequestMethod.GET,
            RequestMethod.POST,
            RequestMethod.DELETE})
    @ResponseBody
    public ResponseEntity<String> proxyRequest(HttpServletRequest request) throws Exception {
        HttpUriRequest proxiedRequest = createHttpUriRequest(request);
        logger.info("request: {}", proxiedRequest);
        HttpResponse proxiedResponse = httpClient.execute(proxiedRequest);
        logger.info("Response {}", proxiedResponse.getStatusLine().getStatusCode());
        return new ResponseEntity<>(
                read(proxiedResponse.getEntity().getContent()),
                makeResponseHeaders(proxiedResponse),
                HttpStatus.valueOf(proxiedResponse.getStatusLine().getStatusCode())
        );
    }

//    @RequestMapping(value = "/**", method = { RequestMethod.GET, RequestMethod.POST })
//    @ResponseBody
//    public ResponseEntity<String> hello(HttpServletRequest request) throws Exception {
//        HttpUriRequest proxiedRequest = createHttpUriRequest(request);
//        HttpResponse proxiedResponse = httpClient.execute(proxiedRequest);
//        return new ResponseEntity<>(
//                read(proxiedResponse.getEntity().getContent()),
//                makeResponseHeaders(proxiedResponse),
//                HttpStatus.valueOf(proxiedResponse.getStatusLine().getStatusCode())
//        );
//    }

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
    @ResponseBody
    public ResponseEntity<Object> hello(HttpServletRequest request, HttpEntity<String> entity) throws Exception {
        URLRequestTransformer urlRequestTransformer = new URLRequestTransformer(apiGatewayProperties);
        System.out.print(urlRequestTransformer.getServiceUrl(request.getRequestURI(), request));
        return restTemplate.exchange(
                urlRequestTransformer.getServiceUrl(request.getRequestURI(), request),
                HttpMethod.GET,
                entity,
                Object.class
        );
    }

    private HttpHeaders makeResponseHeaders(HttpResponse response) {
        HttpHeaders result = new HttpHeaders();
        Header h = response.getFirstHeader("Content-Type");
        result.set(h.getName(), h.getValue());
        return result;
    }

    private HttpUriRequest createHttpUriRequest(HttpServletRequest request) throws Exception {
        URLRequestTransformer urlRequestTransformer = new URLRequestTransformer(apiGatewayProperties);
        ContentRequestTransformer contentRequestTransformer = new ContentRequestTransformer();
        HeadersRequestTransformer headersRequestTransformer = new HeadersRequestTransformer();
        headersRequestTransformer.setPredecessor(contentRequestTransformer);
        contentRequestTransformer.setPredecessor(urlRequestTransformer);

        return headersRequestTransformer.transform(request).build();
    }

    private String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
