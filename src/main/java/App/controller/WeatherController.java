package App.controller;

import App.WeatherService;
import App.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;

@RestController
@RequestMapping("api")
public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<WeatherDto> get(@PathVariable("id") long id) {
        WeatherDto weather = service.get(id);
        return weather == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(weather);
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public ResponseEntity<WeatherDto> getByDate(@RequestParam String date)
    {
        WeatherDto weather = null;
        try {
            weather = service.getByDate(date);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        return weather != null ? ResponseEntity.ok(weather) : ResponseEntity.notFound().build();
    }


    @PostMapping(value = "/register")
    public ResponseEntity add(@RequestBody WeatherDto weather, UriComponentsBuilder builder) {
        try {
            long newId = service.add(weather);

            UriComponents uriComponents =
                    builder.path("/api/{id}").buildAndExpand(newId);
            return ResponseEntity.created(uriComponents.toUri()).build();
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") long id)
    {
        boolean deleted = service.delete(id);
        return  deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
