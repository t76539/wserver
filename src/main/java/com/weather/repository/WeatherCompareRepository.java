package com.weather.repository;

import com.weather.model.WeatherCompare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface WeatherCompareRepository extends CrudRepository<WeatherCompare, Long> {

    @Query(value = "SELECT w.id, w.date_metering, w.temp, w.press, w.wet, coalesce(p.temp,0) tempo, coalesce(p.press, 0) presso, coalesce(p.wet, 0) weto\n" +
            " FROM Weather as w\n" +
            " LEFT JOIN Weather as p ON\n" +
            " p.date_metering = w.date_metering - interval '1 year'\n" +
            " WHERE w.date_metering BETWEEN ?1 AND ?2\n" +
            " ORDER BY w.date_metering",
            nativeQuery = true)
    public List<WeatherCompare> findWeatherCompare(Date start, Date stop);

}