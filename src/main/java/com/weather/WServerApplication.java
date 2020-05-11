package com.weather;

import au.com.bytecode.opencsv.CSVReader;
import com.weather.model.Weather;
import com.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@EnableJpaAuditing
public class WServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WServerApplication.class, args);
    }

    @Autowired
    private WeatherRepository weatherRepository;

    @EventListener(ApplicationReadyEvent.class)
    private void testJpaMethods() throws ParseException, IOException {

        String curDate = "";
        int count = 0;
        float temp = 0f;
        float press = 0f;
        int wet = 0;

        weatherRepository.deleteAll();

        CSVReader reader = new CSVReader(new FileReader("weather.csv"), ';', '"', 1);
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                String nextDate = nextLine[0].substring(0, 10);
                if (!curDate.equals(nextDate)) {
                    if (!curDate.equals("") && count > 0) {
                        weatherRepository.save(new Weather(curDate, temp / count, press / count, wet / count));
                    }
                    curDate = nextDate;
                    count = 0;
                    temp = 0f;
                    press = 0f;
                    wet = 0;
                }
                temp += Float.parseFloat(nextLine[1].replace(",", "."));
                press += Float.parseFloat(nextLine[2].replace(",", "."));
                wet += Integer.parseInt(nextLine[3]);
                count++;
            }
        }

        if (!curDate.equals("") && count > 0) {
            weatherRepository.save(new Weather(curDate, temp / count, press / count, wet / count));
        }

    }

}
