import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Yourorders } from './yourorders';

describe('Yourorders', () => {
  let component: Yourorders;
  let fixture: ComponentFixture<Yourorders>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Yourorders]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Yourorders);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
