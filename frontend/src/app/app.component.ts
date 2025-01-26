import { Component } from '@angular/core';
import { ChatPocComponent } from './chat-poc/chat-poc.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ChatPocComponent],
  template: ` <app-chat-poc></app-chat-poc> `,
  styles: [],
})
export class AppComponent {}
