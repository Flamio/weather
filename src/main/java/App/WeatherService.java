package App;

import App.dao.WeatherRepository;
import App.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherService implements IWeatherService {

    @Autowired
    private WeatherRepository repository;

    public void setRepository(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public Weather get(long id)
    {
        Optional<Weather> weatherOptional = repository.findById(id);
        return weatherOptional.isPresent() ? weatherOptional.get() : null;
    }

    @Override
    public void add(Weather weather) {
        repository.save(weather);
    }
}
