import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkingTestComponent } from './working-test.component';

describe('WorkingTestComponent', () => {
  let component: WorkingTestComponent;
  let fixture: ComponentFixture<WorkingTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WorkingTestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkingTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
