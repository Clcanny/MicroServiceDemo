package demons.apiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

//    @HystrixCommand(
//            groupKey = "StoreSubmission",
//            commandKey = "StoreSubmission",
//            threadPoolKey = "StoreSubmission",
//            commandProperties = {
//                    @HystrixProperty(name = "hystrix.command.default.execution.timeout.enabled", value = "false")
//            }
//    )
//    public String storeSubmission(ReturnType returnType, InputStream is, String id) {
//    }
}
