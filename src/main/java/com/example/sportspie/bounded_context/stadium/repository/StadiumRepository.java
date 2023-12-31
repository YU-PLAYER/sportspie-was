package com.example.sportspie.bounded_context.stadium.repository;

import com.example.sportspie.bounded_context.stadium.dto.INearbyStadium;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StadiumRepository extends JpaRepository<Stadium, Integer> {
    @Query(value = """
SELECT s.id AS id, 
s.name AS name,
s.city AS city,
s.district AS district,
s.village AS village,
s.latitude AS latitude,
s.longitude AS longitude,
( 6371 * acos( cos( radians(:latitude) ) * cos( radians( s.latitude ) ) * cos( radians( s.longitude ) - radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( s.latitude ) ) ) ) AS distance
FROM stadium AS s HAVING distance < 10 ORDER BY distance
""", nativeQuery = true)
    List<INearbyStadium> findNearbyStadium(@Param(value="latitude") Double latitude, @Param(value="longitude") Double longitude);

    @Modifying(clearAutomatically = true)
    @Query(value = """
UPDATE stadium s
SET s.weather_type = :weatherType
where s.id = :id
""", nativeQuery = true)
    void updateWeatherType(@Param(value = "id") Long id, @Param(value = "weatherType") int weatherType);
}
