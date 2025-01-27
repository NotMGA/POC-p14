package com.chat.chat.config;

import com.chat.chat.handler.ChatHandler;
import com.chat.chat.repository.MessageRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class to set up WebSocket support in the application.
 */
@Configuration
@EnableWebSocket // Enables WebSocket functionality in the Spring application
public class WebSocketConfig implements WebSocketConfigurer {

    // Injecting the MessageRepository to pass it to the WebSocket handler
    private final MessageRepository messageRepository;

    /**
     * Constructor for WebSocketConfig, initializing the message repository.
     *
     * @param messageRepository the repository for message persistence
     */
    public WebSocketConfig(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Registers WebSocket handlers and maps them to specific endpoints.
     *
     * @param registry the WebSocketHandlerRegistry to register handlers
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Registering the ChatHandler to handle WebSocket requests at the "/chat"
        // endpoint
        registry.addHandler(new ChatHandler(messageRepository), "/chat")
                .setAllowedOrigins("*"); // Allows connections from any origin
    }
}
