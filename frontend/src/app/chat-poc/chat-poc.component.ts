import { Component, OnDestroy, OnInit } from '@angular/core';
import { ChatService, Message } from './chat.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat-poc', // The selector to include this component in other parts of the app
  standalone: true, // Indicates that this is a standalone component
  imports: [CommonModule, FormsModule, HttpClientModule], // Required Angular modules
  templateUrl: './chat-poc.component.html', // Template file for the component
  styleUrls: ['./chat-poc.component.scss'], // Styles specific to this component
})
export class ChatPocComponent implements OnInit, OnDestroy {
  messages: Message[] = []; // Stores all chat messages to display
  input: string = ''; // Stores the current message being typed
  username: string | null = null; // Stores the username after validation
  usernameInput: string = ''; // Temporary input for username before validation

  constructor(private chatService: ChatService) {} // Inject the chat service to handle communication with the backend

  /**
   * Lifecycle hook called when the component is initialized.
   * Subscribes to the chat service to retrieve all messages.
   */
  ngOnInit(): void {
    this.chatService.getMessages().subscribe({
      next: (data) => {
        this.messages = data; // Populate the messages array with data from the server
      },
      error: (err) => {
        console.error('Error while fetching messages:', err); // Log any error that occurs during the API call
      },
    });
  }

  /**
   * Validates and sets the username provided by the user.
   * Also passes the username to the chat service for further communication.
   */
  submitUsername(): void {
    if (this.usernameInput.trim()) {
      this.username = this.usernameInput.trim(); // Trim and set the username
      this.chatService.setUsername(this.username); // Pass the username to the service
    } else {
      console.error('Username cannot be empty.'); // Log an error if the username is empty
    }
  }

  /**
   * Sends a message to the chat service and updates the local chat list.
   * Validates that a username is set and the input message is not empty.
   */
  sendMessage(): void {
    if (!this.username) {
      console.error('Username is not set.'); // Ensure the username is defined
      return;
    }

    if (this.input.trim()) {
      // Add the new message to the local chat messages
      this.messages.push({
        id: 0, // Temporary ID for the new message (actual ID will be set by the backend)
        sender: this.username, // Sender is the current username
        content: this.input, // The message content
        timestamp: new Date().toISOString(), // Add a timestamp
      });
      // Send the message to the backend using the chat service
      this.chatService.sendMessage(this.input);
      this.input = ''; // Reset the input field after sending the message
    }
  }

  /**
   * Lifecycle hook called when the component is destroyed.
   * Closes the WebSocket connection to the chat server.
   */
  ngOnDestroy(): void {
    this.chatService.closeConnection(); // Ensure the connection is properly closed
  }
}
