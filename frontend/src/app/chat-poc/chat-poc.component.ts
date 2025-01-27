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
    // Listen for messages coming from WebSocket
    this.chatService.onMessageReceived = (message: Message) => {
      this.messages.push(message); // Add the new message to the list
    };

    // Fetch existing messages from the API
    this.chatService.getMessages().subscribe({
      next: (data) => {
        this.messages = data;
      },
      error: (err) => {
        console.error('Error fetching messages:', err);
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
      console.error('Username is not set.');
      return;
    }

    if (this.input.trim()) {
      // Send the message to the server through WebSocket
      this.chatService.sendMessage(this.input);

      // Clear the input field after sending the message
      this.input = '';
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
