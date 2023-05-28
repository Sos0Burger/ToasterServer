package com.messenger.Messenger.config;

import com.messenger.Messenger.context.AppContextProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SpringConfiguration {
    @Bean
    public static AppContextProvider contextProvider() {
        return new AppContextProvider();
    }
}
