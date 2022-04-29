import { TestBed } from '@angular/core/testing';

import { IsMatchedPostUserGuard } from './is-matched-post-user.guard';

describe('IsMatchedPostUserGuard', () => {
  let guard: IsMatchedPostUserGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(IsMatchedPostUserGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
