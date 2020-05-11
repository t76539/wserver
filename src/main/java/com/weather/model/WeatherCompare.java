package com.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@Entity
public class WeatherCompare {
    @Id
    private Long id;
    private Date date_metering;
    private float temp;
    private float press;
    private int wet;
    private float tempo;
    private float presso;
    private int weto;

    public WeatherCompare() {
    }
}
