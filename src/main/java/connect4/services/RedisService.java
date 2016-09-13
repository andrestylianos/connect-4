package connect4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void upsertValue(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> T getValue(String key, Class<T> type) {
        return (T) redisTemplate.opsForValue().get(key);
    }
}
