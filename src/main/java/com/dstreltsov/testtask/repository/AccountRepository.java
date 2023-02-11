package com.dstreltsov.testtask.repository;

import com.dstreltsov.testtask.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Account repository.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByLogin(String login);

    Optional<Account> findByEmail(String email);
}
