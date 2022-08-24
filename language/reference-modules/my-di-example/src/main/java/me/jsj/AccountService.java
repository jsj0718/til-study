package me.jsj;

import me.jsj.thejava.codemanipulation.reflection.mydi.Inject;

public class AccountService {

    @Inject
    AccountRepository accountRepository;

    public void join() {
        System.out.println("AccountService.join()");
        accountRepository.save();
    }
}
