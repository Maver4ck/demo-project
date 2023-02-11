package com.dstreltsov.testtask.service;

import com.dstreltsov.testtask.model.Account;
import com.dstreltsov.testtask.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Account service.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder   passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Account> findByLogin(String login) {
        return accountRepository.findByLogin(login);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Transactional
    public Account createAccount(Account account) {
        enrichAccount(account);
        return accountRepository.save(account);
    }

    @Transactional
    public Account editAccount(Account account) {
        Optional<Account> optional = accountRepository.findById(account.getId());
        if (optional.isPresent()) {
            Account accountToUpdate = optional.get();
            updateAccount(account, accountToUpdate);
            return accountRepository.save(accountToUpdate);
        }
        return null;
    }

    @Transactional
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    private void updateAccount(Account old, Account fromDB) {
        fromDB.setBirthYear(old.getBirthYear());
        fromDB.setPassword(passwordEncoder.matches(old.getPassword(), fromDB.getPassword())
                ? fromDB.getPassword()
                : passwordEncoder.encode(old.getPassword())
        );
    }

    private void enrichAccount(Account account) {
        account.setCreatedAt(new Date());
        hashPassword(account);
    }

    private void hashPassword(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
    }
}
