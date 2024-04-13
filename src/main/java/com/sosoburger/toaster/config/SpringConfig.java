package com.sosoburger.toaster.config;

import com.sosoburger.toaster.context.AppContextProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SpringConfig {
    @Bean
    public static AppContextProvider contextProvider() {
        return new AppContextProvider();
    }
}
