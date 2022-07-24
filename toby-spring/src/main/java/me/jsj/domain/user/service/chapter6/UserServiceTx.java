package me.jsj.domain.user.service.chapter6;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jsj.domain.user.UserV2;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Getter
@RequiredArgsConstructor
public class UserServiceTx implements UserServiceCh6V1 {

    private final UserServiceCh6V1 userService;

    private final PlatformTransactionManager transactionManager;

    @Override
    public void add(UserV2 user) {
        userService.add(user);
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            userService.upgradeLevels();

            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
