package com.emanuelvictor.api.nonfunctional.authengine.application.messaging;

import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.services.RevokeTokenDomainService;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.springframework.stereotype.Component;

@Component
public class RevokeTokenDomainServiceImpl implements RevokeTokenDomainService {

    private final RedisClient redisClient;

    public RevokeTokenDomainServiceImpl(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public void revokeToken(String token) {
        final StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();
        final RedisPubSubAsyncCommands<String, String> async = connection.async();
        async.publish("revoke-token-redis-channel", token);
    }
}
