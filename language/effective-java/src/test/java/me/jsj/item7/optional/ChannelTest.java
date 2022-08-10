package me.jsj.item7.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ChannelTest {

    @Test
    void npe() {
        Channel channel = new Channel();
        Optional<Membership> optionalMembership = channel.defaultMembership();
        optionalMembership.ifPresent(m -> System.out.println(m.hello()));
    }
}