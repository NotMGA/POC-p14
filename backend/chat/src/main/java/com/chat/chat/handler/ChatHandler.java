package com.chat.chat.handler;

import com.chat.chat.model.Message;
import com.chat.chat.repository.MessageRepository;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final MessageRepository messageRepository;

    public ChatHandler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New session connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Message received: " + payload);

        // save message on the bdd
        Message newMessage = new Message("user", payload, LocalDateTime.now());
        messageRepository.save(newMessage);

        // send message to all ppl
        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage("Server: " + payload));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
            throws Exception {
        sessions.remove(session);
    }
}