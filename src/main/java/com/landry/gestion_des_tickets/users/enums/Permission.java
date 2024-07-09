package com.landry.gestion_des_tickets.users.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMINISTRATOR_READ("administrator: read"),
    ADMINISTRATOR_CREATE("administrator: create"),
    ADMINISTRATOR_UPDATE("administrator: update"),
    ADMINISTRATOR_DELETE("administrator: delete"),
    USER_READ("user: read");



    @Getter
    private final String permission;
}
