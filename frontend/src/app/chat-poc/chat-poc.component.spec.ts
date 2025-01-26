import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatPocComponent } from './chat-poc.component';

describe('ChatPocComponent', () => {
  let component: ChatPocComponent;
  let fixture: ComponentFixture<ChatPocComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChatPocComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ChatPocComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
