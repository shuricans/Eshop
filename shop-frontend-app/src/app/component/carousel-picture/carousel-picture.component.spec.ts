import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarouselPictureComponent } from './carousel-picture.component';

describe('CarouselPictureComponent', () => {
  let component: CarouselPictureComponent;
  let fixture: ComponentFixture<CarouselPictureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarouselPictureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarouselPictureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
