package com.sep3.javaapplicationserver.service;

import com.sep3.javaapplicationserver.model.Account;
import com.sep3.javaapplicationserver.repository.AccountRepository;
import com.sep3.javaapplicationserver.service.account.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

//@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AccountServiceImplTest {
    @Autowired
    private AccountRepository repository;

    private AccountServiceImpl service;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUp() {
        service = new AccountServiceImpl(repository);
        Account account = new Account();
        account.setUsername("Pupendo");
        account.setPassword("password");
        repository.save(account);
        ArrayList<Account> accounts = (ArrayList<Account>) repository.findAll();
        for (Account a: accounts) {
            System.out.println("SETUP");
            displayAccount(a);
        }
    }

    @Test
    void save() {
        Account account = new Account();
        account.setUsername("Joshua");
        account.setPassword("password");
        service.save(account);


        ArrayList<Account> accounts = (ArrayList<Account>) repository.findAll();
        for (Account a: accounts) {
            System.out.println("ADDED ACCOUNT");
            displayAccount(a);
        }

        Optional<Account> account1 = repository.findAccountByUsername("Joshua");
        Assertions.assertEquals(account.getUsername(), account1.get().getUsername());
    }

    @Test
    void update() {
        Optional<Account> account = repository.findAccountByUsername("Pupendo");
        Account newAccountDetails = new Account();
        newAccountDetails.setUsername("lucas");
        newAccountDetails.setPassword("lucaspassword");
        service.update(newAccountDetails, account.get());
        ArrayList<Account> accounts = (ArrayList<Account>) repository.findAll();
        for (Account a: accounts) {
            System.out.println("EDITED ACCOUNT");
            displayAccount(a);
        }
    }

    private void displayAccount(Account account){
        System.out.println("User ID:"+account.getId());
        System.out.println("Username:"+account.getUsername());
        System.out.println("Password:"+account.getPassword());
    }
}