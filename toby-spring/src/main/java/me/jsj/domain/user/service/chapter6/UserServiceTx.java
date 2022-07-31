package me.jsj.domain.user.service.chapter6;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jsj.domain.user.UserV2;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserServiceTx implements UserServiceCh6V1 {

    private final UserServiceCh6V1 userService; //타깃 오브젝트

    private final PlatformTransactionManager transactionManager;

    //메소드 구현 및 위임
    @Override
    public void add(UserV2 user) {
        userService.add(user); 
    }

    @Override
    public UserV2 get(String id) {
        return userService.get(id);
    }

    @Override
    public List<UserV2> getAll() {
        return userService.getAll();
    }

    @Override
    public void deleteAll() {
        userService.deleteAll();
    }

    @Override
    public void update(UserV2 user) {
        userService.update(user);
    }

    //메소드 구현
    @Override
    public void upgradeLevels() {
        //부가기능 수행
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            //위임
            userService.upgradeLevels();

            //부가 기능 수행
            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
