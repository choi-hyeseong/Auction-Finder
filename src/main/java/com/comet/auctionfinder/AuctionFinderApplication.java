package com.comet.auctionfinder;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuctionFinderApplication {

    public static void main(String[] args) {
        WebDriverManager.chromedriver()
                .setup(); //크롬드라이버 셋업
        SpringApplication.run(AuctionFinderApplication.class, args);
    }

}
