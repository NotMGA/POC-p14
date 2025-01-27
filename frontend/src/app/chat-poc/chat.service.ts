import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Interface defining the structure of a message object
export interface Message {
  id: number; // Unique identifier for the message
  sender: string; // The sender of the message (username)
  content: string; // The content of the message
  timestamp: string; // The timestamp of when the message was sent
}

@Injectable({
  providedIn: 'root', // Marks this service as a singleton available throughout the application
})
export class ChatService {
  private socket: WebSocket; // WebSocket instance for real-time communication
  private username: string = ''; // Property to store the username of the user

  constructor(private http: HttpClient) {
    // Initialize the WebSocket connection
    this.socket = new WebSocket('ws://localhost:8080/chat');

    // Event listener to handle incoming WebSocket messages
    this.socket.onmessage = (event) => {
      console.log('Message received from server:', event.data);
    };
  }

  /**
   * Fetches all messages from the backend REST API.
   *
   * @returns an Observable of an array of messages
   */
  getMessages(): Observable<Message[]> {
    return this.http.get<Message[]>('/api/messages');
  }

  /**
   * Sends a message through the WebSocket connection.
   *
   * @param message the message content to be sent
   */
  sendMessage(message: string): void {
    if (!this.username) {
      console.error('Username is not defined'); // Ensure the username is set before sending
      return;
    }

    // Construct the payload as a JSON object
    const payload = JSON.stringify({
      sender: this.username, // Include the username
      content: message, // Include the message content
    });

    // Check if the WebSocket connection is open before sending
    if (this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(payload); // Send the message payload
    } else {
      console.error('WebSocket is not connected'); // Log an error if the WebSocket is not ready
    }
  }

  /**
   * Sets the username for the current user.
   *
   * @param name the username to be set
   */
  setUsername(name: string): void {
    this.username = name;
  }

  /**
   * Closes the WebSocket connection when the service is destroyed.
   */
  closeConnection(): void {
    this.socket.close(); // Properly close the WebSocket connection
  }
}
