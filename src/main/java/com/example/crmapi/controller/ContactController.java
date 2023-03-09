package com.example.crmapi.controller;

import com.example.crmapi.constants.ApiResponse;
import com.example.crmapi.constants.CustomMessages;
import com.example.crmapi.exceptions.CustomExceptionHandler;
import com.example.crmapi.exceptions.RecordNotFoundException;
import com.example.crmapi.model.Account;
import com.example.crmapi.model.Contact;
import com.example.crmapi.model.auth.User;
import com.example.crmapi.service.AppService;
import com.example.crmapi.service.auth.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ContactController {

    @Autowired
    private AppService service;
    @Autowired
    private OauthService oauthService;

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @PostMapping(value = "/contact/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addContact(@Valid @RequestBody Contact contact) {
        Optional<Account> account = service.getAccountRepository().findById(contact.getAccountName().getId());
        Optional<User> userById = oauthService.getUserById(contact.getContactOwner().getId());

        if (account.isPresent()){
            contact.setAccountName(account.get());
        }
        if (userById.isPresent()) {
            contact.setContactOwner(userById.get());
        }


        contact.setCreatedBy(new Date());
        Contact newAccount = service.getContactRepository().save(contact);

        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, newAccount));

    }

    @GetMapping("/contact/getContacts")
    public List<Contact> getAllContacts() {
        return service.getContactRepository().findAll();
    }

    @GetMapping("/contact/getContactById/{id}")
    public ResponseEntity getContactById(@PathVariable("id") Long id) {
        return service.getContactRepository().findById(id).map(record -> {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, record));
        }).orElseThrow(() -> new RecordNotFoundException("Record Not Found for: " + id));
    }

    @DeleteMapping("/contact/deleteContactById/{id}")
    public ResponseEntity deleteContactById(@PathVariable("id") Long id) {
        return service.getContactRepository().findById(id).map(record -> {
            service.getContactRepository().deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Deleted, CustomMessages.DeletedMessage));
        }).orElseThrow(() -> new RecordNotFoundException("Record Not Found for: " + id));
    }
}
