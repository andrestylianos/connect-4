package connect4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void upsertValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> Optional<T> getValue(String key, Class<T> type) {
        return Optional.ofNullable(type.cast(redisTemplate.opsForValue().get(key)));
    }

    public void removeValue(String key) {
        redisTemplate.delete(key);
    }
}
