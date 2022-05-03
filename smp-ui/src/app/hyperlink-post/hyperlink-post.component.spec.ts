import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HyperlinkPostComponent } from './hyperlink-post.component';

describe('HyperlinkPostComponent', () => {
  let component: HyperlinkPostComponent;
  let fixture: ComponentFixture<HyperlinkPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HyperlinkPostComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HyperlinkPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
