package com.github.shitsu.AnkiTelegram.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("/application.properties")
public class AnkiBotConfig {

    @Value("${telegram.bot.username}")
    String botName;

    @Value("${telegram.bot.token}")
    String token;

}
