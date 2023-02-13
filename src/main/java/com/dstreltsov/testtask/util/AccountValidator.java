package com.dstreltsov.testtask.util;

import com.dstreltsov.testtask.model.Account;
import com.dstreltsov.testtask.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Account validator implementation.
 *
 * @author dstreltsov
 * @since 13.02.2023
 */
@Component
public class AccountValidator implements Validator {

    private final AccountService accountService;

    @Autowired
    public AccountValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    /**
     * Checks if an account already exists in database.
     *
     * @param target the object that is to be validated
     * @param errors contextual state about the validation process
     */
    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;

        accountService.findByLogin(account.getLogin()).ifPresent(val -> errors.rejectValue("login", "An account with specified login already exists."));
        accountService.findByEmail(account.getEmail()).ifPresent(val -> errors.rejectValue("email", "An account with specified email already exists."));
    }
}
