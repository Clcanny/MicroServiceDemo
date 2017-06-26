package demons.msdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by demons on 2017/6/26.
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
public class ReturnParamServer {

    public static void main(String[] args) {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.setProperty("spring.config.name", "returnparam-server");
        SpringApplication.run(ReturnParamServer.class, args);
    }
}
