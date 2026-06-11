package com.psm.transaction_service.controller;

import com.psm.transaction_service.api.request.CreateAccountRequest;
import com.psm.transaction_service.api.response.AccountResponse;
import com.psm.transaction_service.domain.dto.AccountData;
import com.psm.transaction_service.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(
            AccountService accountService
    ) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest createAccountRequest
    ) {

        AccountData accountData = AccountData.from(createAccountRequest);
        AccountData createdAccountData =
                accountService.createAccount(accountData);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdAccountData.toResponse());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> findAccount(
            @PathVariable Long accountId
    ) {
        final AccountData accountData = accountService.findAccountById(accountId);

        return ResponseEntity.ok(
                accountData.toResponse()
        );
    }

}
