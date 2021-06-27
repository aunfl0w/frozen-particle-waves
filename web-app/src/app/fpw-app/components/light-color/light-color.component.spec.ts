import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LightColorComponent } from './light-color.component';

describe('LightColorComponent', () => {
  let component: LightColorComponent;
  let fixture: ComponentFixture<LightColorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LightColorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LightColorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
