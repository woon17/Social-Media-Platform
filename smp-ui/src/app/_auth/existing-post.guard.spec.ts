import { TestBed } from '@angular/core/testing';

import { ExistingPostGuard } from './existing-post.guard';

describe('ExistingPostGuard', () => {
  let guard: ExistingPostGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ExistingPostGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
