package me.jsj.item1.advantage1;

import me.jsj.item1.enumeration.OrderStatus;

public class Order {
    private boolean prime;

    private boolean urgent;

    private Product product;

    private OrderStatus orderStatus;

/*
    public Order(boolean prime, Product product) {
        this.prime = prime;
        this.product = product;
    }

    public Order(Product product, boolean urgent) {
        this.product = product;
        this.urgent = urgent;
    }
*/

    public static Order primeOrder(Product product) {
        Order order = new Order();
        order.prime = true;
        order.product = product;
        return order;
    }

    public static Order urgentOrder(Product product) {
        Order order = new Order();
        order.urgent = true;
        order.product = product;
        return order;
    }
}
