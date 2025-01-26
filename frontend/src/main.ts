import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http'; // Ajoute cette ligne
import { ChatPocComponent } from './app/chat-poc/chat-poc.component';

bootstrapApplication(ChatPocComponent, {
  providers: [provideHttpClient()],
}).catch((err) => console.error(err));
