package com.chat.chat.controller;

import com.chat.chat.model.Message;
import com.chat.chat.repository.MessageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller to handle HTTP requests for messages.
 * Provides endpoints to retrieve and create messages.
 */
@RestController
public class MessageController {

    // Dependency injection of the MessageRepository to interact with the database
    private final MessageRepository messageRepository;

    /**
     * Constructor for MessageController, initializing the message repository.
     *
     * @param messageRepository the repository for message persistence
     */
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * GET endpoint to retrieve all messages from the database.
     *
     * @return a list of all messages stored in the database
     */
    @GetMapping("/api/messages")
    public List<Message> getAllMessages() {
        // Fetch and return all messages from the repository
        return messageRepository.findAll();
    }

    /**
     * POST endpoint to create and save a new message in the database.
     *
     * @param message the message object received in the request body
     * @return the saved message with an auto-generated ID and timestamp
     */
    @PostMapping("/api/messages")
    public Message createMessage(@RequestBody Message message) {
        // Set the current timestamp for the message before saving
        message.setTimestamp(LocalDateTime.now());
        // Save the message to the database and return it
        return messageRepository.save(message);
    }
}
