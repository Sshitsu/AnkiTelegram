package com.github.shitsu.anki.models;

import com.github.shitsu.anki.entity.UserEntity;
import lombok.Data;

@Data
public class User {

    private String userName;

    public User(){};

    public static User toModel(UserEntity userEntity) {
        User user = new User();
        user.setUserName(userEntity.getUsername());
        return user;
    }

}
