import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KnowledgeSpaceComponent } from './knowledge-space.component';

describe('KnowledgeSpaceComponent', () => {
  let component: KnowledgeSpaceComponent;
  let fixture: ComponentFixture<KnowledgeSpaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KnowledgeSpaceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(KnowledgeSpaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
