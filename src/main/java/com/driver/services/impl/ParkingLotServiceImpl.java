package com.driver.services.impl;

import com.driver.Entities.ParkingLot;
import com.driver.Entities.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot= new ParkingLot(name,address);
        parkingLot=parkingLotRepository1.save(parkingLot);

        return parkingLot;

    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot=new Spot();
        spot.setPricePerHour(pricePerHour);
        ParkingLot parkingLot= parkingLotRepository1.findById(parkingLotId).get();
        if(numberOfWheels<=2)
            spot.setSpotType(SpotType.TWO_WHEELER);
        else if(numberOfWheels<=4)
            spot.setSpotType(SpotType.FOUR_WHEELER);
        else
            spot.setSpotType(SpotType.OTHERS);

        spot.setOccupied(false);

        spot.setParkingLot(parkingLot);
        parkingLot.getSpotList().add(spot);

        parkingLotRepository1.save(parkingLot);

        return spot;

    }

    @Override
    public void deleteSpot(int spotId) {

        spotRepository1.deleteById(spotId);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
      Spot spot=spotRepository1.findById(spotId).get();
      ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
      spot.setParkingLot(parkingLot);
      spot.setPricePerHour(pricePerHour);

      parkingLot.getSpotList().add(spot);

      parkingLotRepository1.save(parkingLot);

      return spot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        List<Spot>spotList=parkingLot.getSpotList();

        for(Spot spot:spotList)
            spotRepository1.delete(spot);

        parkingLotRepository1.delete(parkingLot);

    }
}
