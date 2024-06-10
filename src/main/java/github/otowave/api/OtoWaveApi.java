package github.otowave.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "github.otowave.api")
@EnableR2dbcRepositories(basePackages = "github.otowave.api")
public class OtoWaveApi {
    public static void main(String[] args) {
        SpringApplication.run(OtoWaveApi.class, args);
    }
}