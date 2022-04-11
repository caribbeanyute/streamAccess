package me.cleon.streamaccess;

import me.cleon.streamaccess.config.Constants;
import me.cleon.streamaccess.utils.CustomBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;

@SpringBootApplication
public class StreamAccessApplication {
    public static void main(String[] args) {


        //SpringApplication.run(StreamAccessApplication.class, args);
        SpringApplication app = new SpringApplication(StreamAccessApplication.class);
        app.setBanner(new CustomBanner());
        app.run(args);


    }
    @Bean
    @Primary
    Clock clock() {
        return Clock.system(Constants.ZONE_ID);
    }


}
