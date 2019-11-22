package App;

import App.model.Weather;

public interface IWeatherService {
    Weather get(long id);

    void add(Weather weather);
}
