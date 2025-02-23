//package com.API_SupportCustommer.API.WebSocket.Config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(new ChatWebSocketHandler(), "/ws/chat") // Defina o caminho do endpoint WebSocket
//                .setAllowedOrigins("*") // URL do frontend
//                .addInterceptors(new HttpSessionHandshakeInterceptor());
//    }
//}
//
//
