package me.jsj.jojoldu.domain.pay;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    private boolean soldOutStatus;

    public Product(Long amount, boolean soldOutStatus) {
        this.amount = amount;
        this.soldOutStatus = soldOutStatus;
    }

    public void soldOut() {
        soldOutStatus = true;
    }
}
