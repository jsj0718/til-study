package me.jsj.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserV2 {
    private String id;
    private String name;
    private String password;
    private String email;
    private Level level;
    private int login;
    private int recommend;

    public void upgradeLevel() {
        Level nextLevel = level.nextLevel();
        if (nextLevel == null) {
            throw new IllegalStateException(level + "은 업그레이드가 불가능 합니다.");
        } else {
            this.level = nextLevel;
        }
    }
}

