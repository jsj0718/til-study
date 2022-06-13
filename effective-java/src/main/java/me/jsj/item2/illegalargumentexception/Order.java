package me.jsj.item2.illegalargumentexception;

import java.time.LocalDate;

public class Order {

    public void updateDeliveryDate(LocalDate deliveryDate) throws NullPointerException, IllegalArgumentException {
        if (deliveryDate == null) {
            throw new NullPointerException("deliveryDate can't be null");
        }

        if (deliveryDate.isBefore(LocalDate.now())) {
            //과거 배송은 안되므로 예외 발생
            throw new IllegalArgumentException("deliveryDate can't be earlier than " + LocalDate.now());
        }
    }
}
