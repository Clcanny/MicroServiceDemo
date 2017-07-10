package demons.nameServer;

//import demons.eureka.server.plugin.EnableEurekaServer;
//import demons.eureka.server.plugin.EnableEurekaServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NameServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NameServerApplication.class, args);
    }
}
