package com.github.shitsu.AnkiTelegram.models;

import com.github.shitsu.AnkiTelegram.entity.UserEntity;
import com.github.shitsu.AnkiTelegram.bot.State;
import lombok.Data;

@Data
public class User {

    private String firstName;

    private String lastName;

    private String username;

    private State state;

    public User(){};

    public static User toModel(UserEntity userEntity) {
        User user = new User();
        user.setUsername(userEntity.getUsername());
        return user;
    }

}
