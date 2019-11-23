package App;

import App.dto.WeatherDto;

import java.text.ParseException;

public interface WeatherService {
    WeatherDto get(long id);
    long add(WeatherDto weather);
    WeatherDto getByDate(String date) throws ParseException, ParseException;
}
