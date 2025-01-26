import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Message {
  id: number;
  sender: string;
  content: string;
  timestamp: string;
}

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private socket: WebSocket;

  constructor(private http: HttpClient) {
    this.socket = new WebSocket('ws://localhost:8080/chat');

    this.socket.onmessage = (event) => {
      console.log('Message reçu du serveur :', event.data);
    };
  }

  getMessages(): Observable<Message[]> {
    return this.http.get<Message[]>('/api/messages');
  }

  sendMessage(message: string): void {
    if (this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(message);
    } else {
      console.error('WebSocket non connecté');
    }
  }

  closeConnection(): void {
    this.socket.close();
  }
}
