package connect4.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"connect4.conf", "connect4.controllers", "connect4.services"})
public class ConnectFourConfiguration {

    public static void main(String[] args){
        SpringApplication.run(ConnectFourConfiguration.class, args);
    }

}
