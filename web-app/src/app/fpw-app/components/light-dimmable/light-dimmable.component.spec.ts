import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LightDimmableComponent } from './light-dimmable.component';

describe('LightDimmableComponent', () => {
  let component: LightDimmableComponent;
  let fixture: ComponentFixture<LightDimmableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LightDimmableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LightDimmableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
