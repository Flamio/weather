package App;

import App.dto.WeatherDto;

public interface IWeatherService {
    WeatherDto get(long id);

    void add(WeatherDto weather);

  //  Weather getByDate(String date) throws ParseException;
}
