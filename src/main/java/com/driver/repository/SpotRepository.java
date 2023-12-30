package com.driver.repository;

import com.driver.Entities.ParkingLot;
import com.driver.Entities.Spot;
import com.driver.model.SpotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer>{
    @Query(value="select * from spot where parking_lot_id=?1 and  occupied=?2 and spot_type=?3 ORDER BY price_per_hour;",nativeQuery = true)
    List<Spot> findspotsbyparking(Integer id, boolean occupied,String name);

}
