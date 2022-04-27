package com.dxc.smp.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dxc.smp.entity.User;

// used for createdBy and modifiedBy post entity audit fields
@Component
public class AuditorAwareImpl implements AuditorAware<String> { // return userName
	
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		return Optional.of(authentication.getName());
	}
}
