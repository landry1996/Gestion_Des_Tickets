package com.landry.gestion_des_tickets.users.auditing;

import com.landry.gestion_des_tickets.users.models.Usr;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken)
        {
            return Optional.empty();
        }

        Usr userPrincipal = (Usr) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
}
