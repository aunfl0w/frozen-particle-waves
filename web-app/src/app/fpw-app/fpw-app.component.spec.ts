import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpwAppComponent } from './fpw-app.component';

describe('FpwAppComponent', () => {
  let component: FpwAppComponent;
  let fixture: ComponentFixture<FpwAppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpwAppComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpwAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
