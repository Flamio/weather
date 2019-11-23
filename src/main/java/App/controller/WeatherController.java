package App.controller;

import App.IWeatherService;
import App.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/weather")
public class WeatherController {

    @Autowired
    private IWeatherService service;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<WeatherDto> get(@PathVariable("id") long id) {
        WeatherDto weather = service.get(id);
        return weather == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(weather);
    }

   /* @GetMapping(value = "/find")
    @ResponseBody
    public ResponseEntity<Weather> getByDate(@RequestParam String date)
    {
        Weather weather;
        try {
            weather = service.getByDate(date);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        return weather == null ?  ResponseEntity.notFound().build() : ResponseEntity.ok().body(weather);
    }*/


    @PostMapping(value = "/register")
    public ResponseEntity add(@RequestBody WeatherDto weather) {
        service.add(weather);
        return ResponseEntity.ok().build();
    }

}
