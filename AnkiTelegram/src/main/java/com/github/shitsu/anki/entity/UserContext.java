package com.github.shitsu.anki.entity;

import lombok.Data;

@Data
public class UserContext {
    private String tempFront;
    private String tempBack;
    private String tempDeckName;
    private Long currentDeckId;
}
