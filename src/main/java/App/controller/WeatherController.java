package App.controller;

import App.model.EarthQuake;
import App.model.Precipitation;
import App.model.Weather;
import App.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("api/")
public class WeatherController {

    @Autowired
    private IWeatherService service;

    @RequestMapping(value = "/weather/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Weather> get(@PathVariable("id") long id)
    {
        Weather weather = service.get(id);
        return weather == null ?  ResponseEntity.notFound().build() : ResponseEntity.ok().body(weather);
    }

    @RequestMapping(value = "/weather/c", method = RequestMethod.GET)
    public ResponseEntity add()
    {
        Precipitation precipitation = new Precipitation();
        precipitation.setTemperature(-30);
        precipitation.setIntensity(4);
        precipitation.setNumberOfDailyAllowances(5);
        precipitation.setName("rain");

        Precipitation precipitation2 = new Precipitation();
        precipitation2.setTemperature(-30);
        precipitation2.setIntensity(4);
        precipitation2.setNumberOfDailyAllowances(5);
        precipitation2.setName("snow");

        Weather w = new Weather();
        w.setDate(new Date());
        w.setPrecipitations(Arrays.asList(precipitation, precipitation2));

        EarthQuake earthQuake = new EarthQuake();
        w.setEarthQuake(earthQuake);

        precipitation.setWeather(w);
        earthQuake.setWeather(w);
        precipitation.setWeather(w);
        precipitation2.setWeather(w);

        service.add(w);

        return  ResponseEntity.ok().build();
    }

}
