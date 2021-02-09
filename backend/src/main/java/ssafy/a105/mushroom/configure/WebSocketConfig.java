package ssafy.a105.mushroom.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  // 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/socket/send");
  }

  @Override
  // connection을 맺을때 CORS 허용합니다.
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/").setAllowedOriginPatterns("*").withSockJS();
  }
}
