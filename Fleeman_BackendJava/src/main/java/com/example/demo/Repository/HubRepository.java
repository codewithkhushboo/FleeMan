package com.example.demo.Repository;

import com.example.demo.Entity.HubMaster;
import com.example.demo.Entity.HubMaster;
import com.example.demo.Repository.projection.HubInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HubRepository extends JpaRepository<HubMaster, Integer> {


    @Query(value = "SELECT h.hub_id as hubId, h.hub_name as hubName, h.hub_address_and_details as hubAddress, " +
            "h.city_id as cityId, h.state_id as stateId, " +
            "c.city_name as cityName, s.state_name as stateName " +
            "FROM hub_master h " +
            "JOIN city_master c ON h.city_id = c.city_id " +
            "JOIN state_master s ON h.state_id = s.state_id " +
            "WHERE c.city_name = :cityName AND s.state_name = :stateName",nativeQuery = true)


    List<HubInfoProjection> getHubListCityIdAndStateId(@Param("cityName") String  cityName, @Param("stateName") String stateName);

}

