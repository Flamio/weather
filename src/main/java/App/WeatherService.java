package App;

import App.dto.WeatherDto;

import java.text.ParseException;

public interface WeatherService {
    WeatherDto get(long id);
    long add(WeatherDto weather) throws IllegalArgumentException;
    WeatherDto getByDate(String date) throws ParseException, ParseException;

    boolean delete(long l);
}
