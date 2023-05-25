import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGroupSidebarComponent } from './view-group-sidebar.component';

describe('ViewGroupSidebarComponent', () => {
  let component: ViewGroupSidebarComponent;
  let fixture: ComponentFixture<ViewGroupSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewGroupSidebarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewGroupSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
