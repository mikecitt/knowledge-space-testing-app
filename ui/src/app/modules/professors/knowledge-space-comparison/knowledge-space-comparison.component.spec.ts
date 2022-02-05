import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KnowledgeSpaceComparisonComponent } from './knowledge-space-comparison.component';

describe('KnowledgeSpaceComparisonComponent', () => {
  let component: KnowledgeSpaceComparisonComponent;
  let fixture: ComponentFixture<KnowledgeSpaceComparisonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KnowledgeSpaceComparisonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(KnowledgeSpaceComparisonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
