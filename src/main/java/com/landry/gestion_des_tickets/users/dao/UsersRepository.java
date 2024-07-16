package com.landry.gestion_des_tickets.users.dao;

import com.landry.gestion_des_tickets.users.models.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Usr, Long> {

    Optional<Usr> findUsrByEmail(String email);
    Optional<Usr> findUsrByUsername(String username);
}
