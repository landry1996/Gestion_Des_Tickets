package com.landry.gestion_des_tickets.users.dao;

import com.landry.gestion_des_tickets.users.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """

            select t from Token t inner join Usr u\s
            on t.usr.id = u.id\s
            where u.id = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(Integer id);
    Optional<Token> findByToken(String token);
}
