package me.jsj.item8.cleaner_as_a_safetynet;

import java.lang.ref.Cleaner;

public class Room implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();

    //청소가 필요한 자원으로 Room에 의존해서는 안 된다.
    private static class State implements Runnable {
        int numJunkPiles;

        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        //close() 또는 cleaner 호출
        @Override
        public void run() {
            System.out.println("Cleaning room");
            numJunkPiles = 0;
        }
    }

    //방의 상태. cleanable과 공유
    private final State state;

    //cleanable 객체. 수거 대상이 되면 방 청소
    private final Cleaner.Cleanable cleanable;


    public Room(int numJunkPiles) {
        this.state = new State(numJunkPiles);
        this.cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }
}
