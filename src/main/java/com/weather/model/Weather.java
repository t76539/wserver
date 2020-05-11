package com.weather.model;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@Entity
@Table(name = "weather")
public class Weather {

    @Id
    @GeneratedValue(generator = "weather_id_seq")
    @SequenceGenerator(
            name = "weather_id_seq",
            sequenceName = "weather_id_seq",
            initialValue = 1000
    )
    private Long id;
    private Date date_metering;
    private float temp;
    private float press;
    private int wet;

    public Weather() {
    }

    public Weather(String date_metering, float temp, float press, int wet) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        this.date_metering = new Date(sdf.parse(date_metering).getTime());
        this.temp = temp;
        this.press = press;
        this.wet = wet;
    }
}
