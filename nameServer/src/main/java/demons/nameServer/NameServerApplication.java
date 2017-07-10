package demons.nameServer;

import demons.eureka.server.plugin.EnableEurekaServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaServer
//@ComponentScan(basePackageClasses = demons.eureka.server.plugin.EurekaController.class)
//@ComponentScan(basePackageClasses = demons.eureka.server.plugin.EurekaServerAutoConfiguration.class)
public class NameServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NameServerApplication.class, args);
    }
}
