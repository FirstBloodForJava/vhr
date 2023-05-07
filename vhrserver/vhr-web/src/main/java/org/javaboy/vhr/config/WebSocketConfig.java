package org.javaboy.vhr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // /ws/ep是注册的STOMP节点
        // setAllowedOrigins("*")方法允许所有来源的主机都可以连接,withSockJS()方法表示启用SockJS支持
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //使用enableSimpleBroker方法启用简单的基于内存的消息代理，可以使用/queue前缀过滤目的地为queue的消息。
        // 在客户端发送消息时，必须先订阅对应的目的地，才能接收到该目的地的消息
        registry.enableSimpleBroker("/queue");
    }
}
