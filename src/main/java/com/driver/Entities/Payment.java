package com.driver.Entities;

import com.driver.model.PaymentMode;

import javax.persistence.*;

@Entity
@Table(name="payment")

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean paymentCompleted;

    @Enumerated(value=EnumType.STRING)
    private PaymentMode PaymentMode;

    @OneToOne(mappedBy = "payment",cascade = CascadeType.ALL)
    @JoinColumn
    private Reservation reservation;

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public PaymentMode getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.PaymentMode = paymentMode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
