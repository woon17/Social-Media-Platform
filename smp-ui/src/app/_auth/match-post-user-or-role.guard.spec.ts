import { TestBed } from '@angular/core/testing';

import { MatchPostUserOrRoleGuard } from './match-post-user-or-role.guard';

describe('MatchPostUserOrRoleGuard', () => {
  let guard: MatchPostUserOrRoleGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(MatchPostUserOrRoleGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
