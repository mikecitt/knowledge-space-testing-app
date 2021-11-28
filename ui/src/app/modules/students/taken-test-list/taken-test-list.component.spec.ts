import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TakenTestListComponent } from './taken-test-list.component';

describe('TakenTestListComponent', () => {
  let component: TakenTestListComponent;
  let fixture: ComponentFixture<TakenTestListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TakenTestListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TakenTestListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
