package com.driver.services.impl;

import com.driver.Entities.ParkingLot;
import com.driver.Entities.Reservation;
import com.driver.Entities.Spot;
import com.driver.Entities.User;
import com.driver.cannotMakeReservation;
import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        Optional<User>userOptional=userRepository3.findById(userId);
        if(userOptional.isEmpty())
            throw new cannotMakeReservation("Cannot make reservation");
        Optional<ParkingLot>optionalParkingLot=parkingLotRepository3.findById(parkingLotId);
        if(optionalParkingLot.isEmpty())
            throw new cannotMakeReservation("Cannot make reservation");
        User user=userOptional.get();
        ParkingLot parkingLot=optionalParkingLot.get();
        SpotType spotType=null;
        if(numberOfWheels<=2)
            spotType=SpotType.TWO_WHEELER;
        else if(numberOfWheels<=4)
            spotType=SpotType.FOUR_WHEELER;
        else
            spotType=SpotType.OTHERS;
                String name=spotType.toString();
        List<Spot> spotList=spotRepository3.findspotsbyparking(parkingLotId,false,name);
        if(spotList.isEmpty())
            throw new cannotMakeReservation("Cannot make reservation");

          Spot spot=spotList.get(0);

          spot.setOccupied(true);
          Reservation reservation= new Reservation();
          reservation.setNumberOfHours(timeInHours);
          reservation.setSpot(spot);
          reservation.setUser(user);

          spot.getReservationList().add(reservation);
          user.getReservationList().add(reservation);

           reservation=reservationRepository3.save(reservation);


          return reservation;

    }
}
