package com.dstreltsov.testtask.controller;

import com.dstreltsov.testtask.dto.AccountDTO;
import com.dstreltsov.testtask.model.Account;
import com.dstreltsov.testtask.service.AccountService;
import com.dstreltsov.testtask.util.AccountValidator;
import com.dstreltsov.testtask.util.ErrorHandler;
import com.dstreltsov.testtask.util.HTTPMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller that handles all request regarding Account entity.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final ModelMapper      modelMapper;
    private final AccountValidator accountValidator;
    private final AccountService   accountService;
    private final ErrorHandler     errorHandler;

    @Autowired
    public AccountController(ModelMapper modelMapper, AccountValidator accountValidator, AccountService accountService, ErrorHandler errorHandler) {
        this.modelMapper = modelMapper;
        this.accountValidator = accountValidator;
        this.accountService = accountService;
        this.errorHandler = errorHandler;
    }

    @PostMapping("/create")
    public AccountDTO createAccount(@RequestBody @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        Account accountToCreate = convertToAccount(accountDTO);

        accountValidator.validate(accountToCreate, bindingResult);
        if (bindingResult.hasErrors()) {
            errorHandler.returnErrorsToClient(bindingResult);
        }

        Account created = accountService.createAccount(accountToCreate);

        return convertToAccountDTO(created);
    }

    @PostMapping("/edit")
    public AccountDTO editAccount(@RequestBody @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        Account accountToEdit = convertToAccount(accountDTO);
        if (accountToEdit.getBirthYear() == null) {
            bindingResult.rejectValue("birthYear", "Birth year cannot be empty.");
        }

        Optional<Account> optional = accountService.findByLogin(accountToEdit.getLogin());
        if (optional.isPresent()) {
            Account existingAccount = optional.get();
            if (!Objects.equals(accountToEdit.getEmail(), existingAccount.getEmail())) {
                bindingResult.rejectValue("email", "You cannot change email.");
            }
            accountToEdit.setId(existingAccount.getId());
        } else {
            bindingResult.rejectValue("login", "Unable to find account by specified login.");
        }

        if (bindingResult.hasErrors()) {
            errorHandler.returnErrorsToClient(bindingResult);
        }

        Account updated = accountService.editAccount(accountToEdit);

        return convertToAccountDTO(updated);
    }

    @PostMapping("/delete")
    public HTTPMessage deleteAccount(@RequestBody AccountDTO accountDTO) {
        Optional<Account> account = accountService.findByLogin(accountDTO.getLogin());
        if (!account.isPresent()) {
            throw new RuntimeException("Account with this login not found.");
        }
        accountService.deleteAccount(account.get());

        return new HTTPMessage("Successfully deleted.");
    }

    @GetMapping("/view-account")
    public AccountDTO viewAccount(@RequestParam String login) {
        Optional<Account> account = accountService.findByLogin(login);

        return account.map(this::convertToAccountDTO).orElseThrow(() -> new RuntimeException("Account with this login not found."));
    }

    private Account convertToAccount(AccountDTO accountDTO) {
        return modelMapper.map(accountDTO, Account.class);
    }

    private AccountDTO convertToAccountDTO(Account account) {
        return modelMapper.map(account, AccountDTO.class);
    }
}
