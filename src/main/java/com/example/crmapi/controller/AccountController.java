package com.example.crmapi.controller;

import com.example.crmapi.constants.ApiResponse;
import com.example.crmapi.constants.CustomMessages;
import com.example.crmapi.exceptions.RecordNotFoundException;
import com.example.crmapi.model.Account;
import com.example.crmapi.model.BillingAddress;
import com.example.crmapi.model.auth.User;
import com.example.crmapi.service.AppService;
import com.example.crmapi.service.auth.OauthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AccountController {

    @Autowired
    private AppService service;

    @Autowired
    private OauthService oauthService;

    @ApiOperation("To add an Account")
    @PostMapping(value = "/account/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addAccount(@Valid @RequestBody Account account) {
        Optional<User> userById = oauthService.getUserById(account.getAccountOwner().getId());

        if (userById.isPresent()) {
            account.setAccountOwner(userById.get());
        }
        account.setCreatedBy(new Date());
        account.setModifiedBy(new Date());
        Account newAccount = service.getAccountRepository().save(account);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, newAccount));
    }

    @ApiOperation("To Get Get All Account")
    @GetMapping("/account/getAccounts")
    public List<Account> getAllAccount() {
        return service.getAccountRepository().findAll();
    }

    @ApiOperation("To Get an Account by ID")
    @GetMapping("/account/getAccountById/{id}")
    public ResponseEntity getAccountById(@PathVariable("id") Long id) {
        return service.getAccountRepository().findById(id).map(record -> {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, record));
        }).orElseThrow(() -> new RecordNotFoundException("Record Not Found for: " + id));
    }

    @ApiOperation("To delete an Account")
    @DeleteMapping("/account/deleteAccountById/{id}")
    public ResponseEntity deleteAccountById(@PathVariable("id") Long id) {
        return service.getAccountRepository().findById(id).map(record -> {
            service.getAccountRepository().deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Deleted, CustomMessages.DeletedMessage));
        }).orElseThrow(() -> new RecordNotFoundException("Record Not Found for: " + id));
    }


}
