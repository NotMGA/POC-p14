package com.chat.chat.handler;

import com.chat.chat.model.Message;
import com.chat.chat.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    /**
     * Constructor for ChatHandler.
     *
     * @param messageRepository the repository for persisting messages
     */
    public ChatHandler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
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
        String payload = message.getPayload(); // Get the raw message payload
        System.out.println("Message received: " + payload);

        // Convert the raw JSON payload to a Message object
        ObjectMapper objectMapper = new ObjectMapper();
        Message newMessage;
        try {
            newMessage = objectMapper.readValue(payload, Message.class); // Deserialize JSON to Message
        } catch (Exception e) {
            System.err.println("Error parsing message JSON: " + e.getMessage());
            return; // Exit the method if JSON parsing fails
        }

        // Set the timestamp for the message
        newMessage.setTimestamp(LocalDateTime.now());

        // Save the message to the database
        messageRepository.save(newMessage);

        // Broadcast the message to all connected clients
        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage(newMessage.getSender() + ": " + newMessage.getContent()));
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
