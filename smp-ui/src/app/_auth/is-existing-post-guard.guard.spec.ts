import { TestBed } from '@angular/core/testing';

import { IsExistingPostGuardGuard } from './is-existing-post-guard.guard';

describe('IsExistingPostGuardGuard', () => {
  let guard: IsExistingPostGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(IsExistingPostGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
