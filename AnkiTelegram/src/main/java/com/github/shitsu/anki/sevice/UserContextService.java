package com.github.shitsu.anki.sevice;

import com.github.shitsu.anki.entity.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserContextService {

    private final RedisTemplate<String, UserContext> redisTemplate;

    private static final Duration TTL = Duration.ofMinutes(30);


    private String buildKey(Long chatId) {
        return "user_ctx:" + chatId;
    }
    public void saveContext(Long chatId, UserContext context) {
        redisTemplate.opsForValue().set(buildKey(chatId), context, TTL);
    }

    public UserContext getContext(Long chatId) {
        return redisTemplate.opsForValue().get(buildKey(chatId));
    }

    public void clearContext(Long chatId) {
        redisTemplate.delete(buildKey(chatId));
    }

}
