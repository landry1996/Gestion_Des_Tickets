package com.landry.gestion_des_tickets.users.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.landry.gestion_des_tickets.users.enums.Permission.*;

@RequiredArgsConstructor
public enum Roles {

    Usr(Collections.emptySet()),
    ADMIN(Set.of(
            ADMINISTRATOR_CREATE,
            ADMINISTRATOR_READ,
            ADMINISTRATOR_UPDATE,
            ADMINISTRATOR_DELETE

    )),
    USER(Set.of(
            USER_READ
    ));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
