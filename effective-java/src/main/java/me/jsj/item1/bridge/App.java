package me.jsj.item1.bridge;

public abstract class App implements Champion {

    public static void main(String[] args) {
        Champion kda아리 = new 아리(new KDA());
        kda아리.skillQ();

        Champion poolparty아리 = new 아리(new PoolParty());
        poolparty아리.skillR();
    }
}
