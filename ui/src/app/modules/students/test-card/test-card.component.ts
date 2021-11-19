import { animate, style, transition, trigger } from '@angular/animations';
import { Component, ElementRef, HostBinding, Input, OnChanges } from '@angular/core';

@Component({
  selector: 'app-test-card',
  templateUrl: './test-card.component.html',
  styleUrls: ['./test-card.component.scss'],
  animations: [
    trigger('grow', [
      transition('void <=> *', []),
      transition(
        '* <=> *',
        [
          style({ height: '{{startHeight}}px' }),
          animate('.5s ease'),
        ],
        { params: { startHeight: 0 } }
      ),
    ]),
  ],
})
export class TestCardComponent implements OnChanges {

  @Input() trigger: boolean;
  startHeight: number;

  constructor(private element: ElementRef) { }

  @HostBinding('@grow') get grow() {
    return { value: this.trigger, params: { startHeight: this.startHeight } };
  }

  setStartHeight() {
    this.startHeight = this.element.nativeElement.clientHeight;
  }

  ngOnChanges() {
    this.setStartHeight();
  }

}
