package com.driver.services.impl;

import com.driver.Entities.Payment;
import com.driver.Entities.Reservation;
import com.driver.Entities.Spot;
import com.driver.cannotMakeReservation;
import com.driver.insufficientAmount;
import com.driver.invalidPaymentMode;
import com.driver.model.PaymentMode;
import com.driver.model.SpotType;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Optional<Reservation>reservationOptional=reservationRepository2.findById(reservationId);
        if(reservationOptional.isEmpty())
            throw new cannotMakeReservation("Cannot make reservation");
        Reservation reservation=reservationOptional.get();
        Spot spot=reservation.getSpot();
        int amount=(reservation.getNumberOfHours())*(spot.getPricePerHour());
        if(amountSent<amount)
            throw new insufficientAmount("Insufficient Amount");
        try{
            PaymentMode isDefined= PaymentMode.valueOf(mode);
        }catch(Exception e){
            throw new invalidPaymentMode("Payment mode not detected");
        }
        Payment payment= new Payment();
        payment.setPaymentMode(PaymentMode.valueOf(mode));
        payment.setPaymentCompleted(true);

        reservation.setPayment(payment);
        payment.setReservation(reservation);

        payment=paymentRepository2.save(payment);

        return payment;

    }
}
