package com.cloud.oauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis存储授权码
 *
 * @author com.hlxd
 */
@Service
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices
{

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 存储code到redis，并设置过期时间，10分钟<br>
     * value为OAuth2Authentication序列化后的字节<br>
     * 因为OAuth2Authentication没有无参构造函数<br>
     * redisTemplate.opsForValue().set(key, value, timeout, unit);
     * 这种方式直接存储的话，redisTemplate.opsForValue().get(key)的时候有些问题，
     * 所以这里采用最底层的方式存储，get的时候也用最底层的方式获取
     */
    @Override
    protected void store(String code, OAuth2Authentication authentication) {

        RedisCallback<Long> action = connection -> {
            connection.set(codeKey(code).getBytes(), SerializationUtils.serialize(authentication), Expiration.from(10, TimeUnit.MINUTES), SetOption.UPSERT);
            return 1L;
        };

        redisTemplate.execute(action);
    }

    @Override
    protected OAuth2Authentication remove(final String code) {
        RedisCallback<OAuth2Authentication> action = connection -> {
            byte[] keyByte = codeKey(code).getBytes();
            byte[] valueByte = connection.get(keyByte);

            if (valueByte != null) {
                connection.del(keyByte);
                return SerializationUtils.deserialize(valueByte);
            }

            return null;
        };


        return redisTemplate.execute(action);
    }

    /**
     * 拼装redis中key的前缀
     *
     * @param code
     * @return
     */
    private String codeKey(String code) {
        return "oauth2:codes:" + code;
    }
}
