package com.weather.controller;

import com.weather.model.Weather;
import com.weather.model.WeatherCompare;
import com.weather.repository.WeatherCompareRepository;
import com.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class WeatherController {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private WeatherRepository weatherRepository;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/weatherall")
    public List<Weather> getWeather() {
        return weatherRepository.findAll();
    }

    // http://localhost:8080/weather?start=1.10.2019&stop=1.12.2019
    @GetMapping("/weather")
    public List<Weather> getWeather2(@RequestParam String start, @RequestParam String stop) throws ParseException {
        Date from = new Date(sdf.parse(start).getTime());
        Date to = new Date(sdf.parse(stop).getTime());

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Weather> criteriaQuery = builder.createQuery(Weather.class);
        Root<Weather> root = criteriaQuery.from(Weather.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.between(root.get("date_metering"), from, to));
        criteriaQuery.orderBy(builder.asc(root.get("date_metering")));
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Autowired
    private WeatherCompareRepository repo;

    @GetMapping("/weather/compare")
    public List<WeatherCompare> getWeatherCompare(@RequestParam String start, @RequestParam String stop) throws ParseException {
        return repo.findWeatherCompare(new Date(sdf.parse(start).getTime()), new Date(sdf.parse(stop).getTime()));
    }
}
