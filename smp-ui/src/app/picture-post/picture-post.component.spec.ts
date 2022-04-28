import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PicturePostComponent } from './picture-post.component';

describe('PicturePostComponent', () => {
  let component: PicturePostComponent;
  let fixture: ComponentFixture<PicturePostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PicturePostComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PicturePostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
