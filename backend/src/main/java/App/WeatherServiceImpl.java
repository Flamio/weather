package App;

import App.dao.EventRepository;
import App.dao.WeatherRepository;
import App.dto.WeatherDto;
import App.model.Event;
import App.model.EventValue;
import App.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private WeatherRepository repository;

    @Autowired
    private EventRepository eventRepository;

    public void setRepository(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public WeatherDto get(long id) {
        Weather weatherDao = repository.findById(id).orElse(null);

        if (weatherDao == null)
            return null;

        return weatherDaoToDto(weatherDao);
    }

    private WeatherDto weatherDaoToDto(Weather weatherDao) {

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setDate(weatherDao.getDate());
        weatherDto.setId(weatherDao.getId());
        weatherDto.setEvents(new ArrayList<>());

        Map<Event, List<EventValue>> valuesByEvent = new HashMap<>();

        weatherDao.getEventValues().forEach(
                eventValue -> {
                    Event event = eventValue.getParameter().getEvent();
                    valuesByEvent.computeIfAbsent(event, k -> new ArrayList<>());
                    valuesByEvent.get(event).add(eventValue);
                    valuesByEvent.put(event, valuesByEvent.get(event));
                }
        );

        valuesByEvent.forEach((event, eventValues) ->
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("type", event.getName());
            eventValues.forEach(eventValue -> {
                map.put(eventValue.getParameter().getName(), eventValue.getValue());
            });
            weatherDto.getEvents().add(map);
        });

        return weatherDto;
    }

    @Transactional
    @Override
    public long add(WeatherDto weather) throws IllegalArgumentException {
        Weather existingWeather = repository.findByDate(weather.getDate()).orElse(null);
        if (existingWeather != null)
            throw new IllegalArgumentException("duplicate date");

        Weather weatherDao = weatherDtoToDao(weather);
        Weather saved = repository.save(weatherDao);
        return saved.getId();
    }

    private Weather weatherDtoToDao(WeatherDto weather) {
        Weather weatherDao = new Weather();
        weatherDao.setEventValues(new ArrayList<>());
        weatherDao.setDate(weather.getDate());

        weather.getEvents().stream().forEach(e -> {
            String type = e.get("type");
            Event event = eventRepository.findByName(type).orElse(null);
            if (event == null)
                throw new IllegalArgumentException("event type not found");

            addNames(e, type);

            event.getParameters().stream().forEach(p ->
            {
                String value = e.get(p.getName());
                if (value == null)
                    return;

                EventValue eventValue = new EventValue();
                eventValue.setParameter(p);
                eventValue.setWeather(weatherDao);
                eventValue.setValue(value);
                weatherDao.getEventValues().add(eventValue);
            });
        });

        return weatherDao;
    }

    private void addNames(Map<String, String> e, String type) {
        if (type.equals("wind")) {
            String speed = e.get("speed");
            if (speed == null) throw new IllegalArgumentException("wind speed not found");
            e.put("name", getWindName(Float.parseFloat(speed)));
        }

        if (type.equals("precipitation")) {
            String temperature = e.get("temperature");
            if (temperature == null) throw new IllegalArgumentException("temperature not found");
            e.put("name", Float.parseFloat(temperature) > 0 ? "rain" : "snow");
        }
    }

    private String getWindName(float speed) {
        if (speed < 2f)
            return "calm";
        else if (speed >= 2f && speed < 47f)
            return "moderate";
        else if (speed >= 47 && speed < 64f)
            return "storm";

        return "hurricane";
    }

    @Override
    public WeatherDto getByDate(String dateString) throws ParseException {
        Weather weatherByDate = repository.findByDate(dateFormat.parse(dateString)).orElse(null);
        if (weatherByDate == null)
            return null;

        return weatherDaoToDto(weatherByDate);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        Weather existingWeather = repository.findById(id).orElse(null);
        if (existingWeather == null)
            return false;
        repository.delete(existingWeather);
        return true;
    }
}
