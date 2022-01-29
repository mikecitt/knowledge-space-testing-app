import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KnowledgeSpaceFormComponent } from './knowledge-space-form.component';

describe('KnowledgeSpaceFormComponent', () => {
  let component: KnowledgeSpaceFormComponent;
  let fixture: ComponentFixture<KnowledgeSpaceFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KnowledgeSpaceFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(KnowledgeSpaceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
