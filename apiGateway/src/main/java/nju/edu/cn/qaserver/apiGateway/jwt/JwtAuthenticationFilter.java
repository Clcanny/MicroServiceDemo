package nju.edu.cn.qaserver.apiGateway.jwt;

import com.netflix.zuul.http.HttpServletRequestWrapper;
import nju.edu.cn.qaserver.apiGateway.auth.KeyUserInfo;
import nju.edu.cn.qaserver.apiGateway.auth.UserRepository;
import nju.edu.cn.qaserver.apiGateway.configuration.ZuulFilterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

// What this filter does is to intercept all requests to validate the presence of
// the JWT–that is, the ones that are not issued to / nor /users.
// This validation is done with the help of the TokenAuthenticationService class.

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    class AddParamsToHeader extends HttpServletRequestWrapper {

        public AddParamsToHeader(HttpServletRequest request) {
            super(request);
        }

        private Map<String, String> headerMap = new HashMap<String, String>();

        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = super.getHeader(name);
            if (headerMap.containsKey(name)) {
                headerValue = headerMap.get(name);
            }
            return headerValue;
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> values = Collections.list(super.getHeaders(name));
            if (headerMap.containsKey(name)) {
                values.add(headerMap.get(name));
            }
            return Collections.enumeration(values);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            for (String name : headerMap.keySet()) {
                names.add(name);
            }
            return Collections.enumeration(names);
        }
    }

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String username = jwtTokenUtil.getUsername(request);
        System.out.println("checking authentication " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtil.validate(request)) {
                Authentication authentication = jwtTokenUtil.
                        getAuthentication(request);
                System.out.println("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);

                KeyUserInfo keyUserInfo = userRepository.findByEmail(username).get(0);
                System.out.println(keyUserInfo.getId());
                ZuulFilterConfig.setUid(keyUserInfo.getId());
            }
        }
        chain.doFilter(request, response);
    }
}
