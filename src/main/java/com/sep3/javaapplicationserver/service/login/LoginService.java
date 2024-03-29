package com.sep3.javaapplicationserver.service.login;

import com.sep3.javaapplicationserver.model.Account;

public interface LoginService {

    Account login(String username, String password);

    Account findByUsernameOrFail(String username);
}
