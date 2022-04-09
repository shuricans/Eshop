import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderInfoPageComponent } from './order-info-page.component';

describe('OrderInfoPageComponent', () => {
  let component: OrderInfoPageComponent;
  let fixture: ComponentFixture<OrderInfoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderInfoPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderInfoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
