package me.jsj.item7.optional;

import java.util.Optional;

public class Channel {
    private int numOfSubscribers;

    public Optional<Membership> defaultMembership() {
        if (this.numOfSubscribers < 2000) {
            return Optional.empty();
        } else {
            return Optional.of(new Membership());
        }
    }
}
