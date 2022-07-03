package me.jsj.item1.enumeration;

public enum OrderStatus {
    PREPARING(0), SHIPPED(1), DELIVERING(2), DELIVERED(3);

    private final int status;

    OrderStatus(int status) {
        this.status = status;
    }
}
