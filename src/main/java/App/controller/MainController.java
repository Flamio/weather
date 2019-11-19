package App.controller;

import App.dao.Event;
import App.dao.EventParameter;
import App.dao.Weather;
import App.dao.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class MainController {

    @Autowired
    private WeatherRepository weatherRepository;

    @RequestMapping("/main")
    public  String Main()
    {
        EventParameter p = new EventParameter();
        p.setName("test_parameter");
        p.setValue(10);

        Event event = new Event();
        event.setName("test_event");

        event.setParameters(new HashSet<>(Collections.singletonList(p)));

        Weather w = new Weather();
        w.setEvents(new ArrayList<>(Collections.singletonList(event)));

        weatherRepository.save(w);

        return "hello_world";
    }
}
