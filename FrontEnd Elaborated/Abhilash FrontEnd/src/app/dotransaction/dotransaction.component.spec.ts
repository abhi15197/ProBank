import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DotransactionComponent } from './dotransaction.component';

describe('DotransactionComponent', () => {
  let component: DotransactionComponent;
  let fixture: ComponentFixture<DotransactionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DotransactionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DotransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
