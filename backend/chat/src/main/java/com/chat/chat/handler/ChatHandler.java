package com.chat.chat.handler;

import com.chat.chat.model.Message;
import com.chat.chat.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A WebSocket handler for managing chat functionality.
 * Handles incoming WebSocket messages, saves them to the database,
 * and broadcasts messages to connected clients.
 */
public class ChatHandler extends TextWebSocketHandler {

    // List to store all active WebSocket sessions (connected clients)
    private final List<WebSocketSession> sessions = new ArrayList<>();

    // Repository for saving messages to the database
    private final MessageRepository messageRepository;

    // ObjectMapper for JSON serialization/deserialization
    private final ObjectMapper objectMapper;

    /**
     * Constructor for ChatHandler.
     *
     * @param messageRepository the repository for persisting messages
     */
    public ChatHandler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule for LocalDateTime support
    }

    /**
     * Called when a new WebSocket connection is established.
     *
     * @param session the WebSocket session for the connected client
     * @throws Exception if an error occurs during connection setup
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session); // Add the session to the list of active sessions
        System.out.println("New session connected: " + session.getId());
    }

    /**
     * Called when a WebSocket message is received from a client.
     *
     * @param session the WebSocket session from which the message was received
     * @param message the message received from the client
     * @throws Exception if an error occurs while processing the message
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); // Get the raw message
        System.out.println("Message received: " + payload);

        // Convert the JSON payload into a Message object
        Message newMessage;
        try {
            newMessage = objectMapper.readValue(payload, Message.class); // Deserialize JSON to Message
        } catch (Exception e) {
            System.err.println("Error parsing JSON message: " + e.getMessage());
            return;
        }

        // Add a timestamp to the message
        newMessage.setTimestamp(LocalDateTime.now());

        // Save the message in the database
        messageRepository.save(newMessage);

        // Broadcast the message to all connected clients
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) { // Ensure the session is still open
                try {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(newMessage))); // Serialize and send
                                                                                                 // the message
                } catch (Exception e) {
                    System.err.println("Error broadcasting message: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Called when a WebSocket connection is closed.
     *
     * @param session the WebSocket session that was closed
     * @param status  the close status
     * @throws Exception if an error occurs during disconnection
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
            throws Exception {
        sessions.remove(session); // Remove the session from the list of active sessions
    }
}
