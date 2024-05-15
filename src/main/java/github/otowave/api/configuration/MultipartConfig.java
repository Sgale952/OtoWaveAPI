package github.otowave.api.configuration;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultipartConfig {
    //private static final String TEMP_DIR = "/home/otowave/data/temp/";
    private static final String TEMP_DIR = "D:\\Archive\\";

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(TEMP_DIR);
        return factory.createMultipartConfig();
    }
}
