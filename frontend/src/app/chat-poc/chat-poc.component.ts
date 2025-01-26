import { Component, OnDestroy, OnInit } from '@angular/core';
import { ChatService, Message } from './chat.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat-poc',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './chat-poc.component.html',
  styleUrls: ['./chat-poc.component.scss'],
})
export class ChatPocComponent implements OnInit, OnDestroy {
  messages: Message[] = [];
  input: string = '';

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.chatService.getMessages().subscribe({
      next: (data) => {
        this.messages = data;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des messages :', err);
      },
    });
  }

  sendMessage(): void {
    if (this.input.trim()) {
      this.messages.push({
        id: 0,
        sender: 'user',
        content: this.input,
        timestamp: new Date().toISOString(),
      });
      this.chatService.sendMessage(this.input);
      this.input = '';
    }
  }

  ngOnDestroy(): void {
    this.chatService.closeConnection();
  }
}
