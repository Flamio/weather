package App.controller;

import App.model.Weather;
import App.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
