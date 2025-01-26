package com.chat.chat.controller;

import com.chat.chat.model.Message;
import com.chat.chat.repository.MessageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/api/messages")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
