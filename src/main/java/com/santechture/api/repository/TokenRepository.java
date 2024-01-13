package com.santechture.api.repository;

import com.santechture.api.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
            select t from Token t inner join Admin a on t.admin.id = a.id where a.id = :adminId and (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllValidTokensByAdmin(Integer adminId);

    Optional<Token> findByToken(String token);

}
