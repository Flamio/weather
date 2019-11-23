package App;

import App.dao.WeatherRepository;
import App.dto.*;
import App.model.EarthQuake;
import App.model.Precipitation;
import App.model.Weather;
import App.model.Wind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherService implements IWeatherService {

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private WeatherRepository repository;

    public void setRepository(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public WeatherDto get(long id)
    {
        Weather weatherDao = repository.findById(id).orElse(null);

        if (weatherDao == null)
            return null;

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setDate(weatherDao.getDate());
        weatherDto.setEvents(new ArrayList<>());

        EarthQuake earthQuake = weatherDao.getEarthQuake();
        Wind wind = weatherDao.getWind();

        if (earthQuake != null)
            weatherDto.getEvents().add(new EarthQuakeDto("earthQuake", earthQuake.getMagnitudeScaleValue()));

        if (wind != null)
            weatherDto.getEvents().add(new WindDto("wind", wind.getSpeed()));

        return weatherDto;
    }

    @Transactional
    @Override
    public void add(WeatherDto weather) {

        Weather weatherDao = new Weather();

        weatherDao.setDate(weather.getDate());

        if (weather.getEvents() != null)
            initDaoEvents(weather, weatherDao);

        repository.save(weatherDao);
    }

    private void initDaoEvents(WeatherDto weather, Weather weatherDao) {
        EarthQuakeDto earthQuake = (EarthQuakeDto) weather.getEvents().stream().filter(event -> event.getClass().isAssignableFrom(EarthQuakeDto.class)).findFirst().orElse(null);
        if (earthQuake != null) {
            EarthQuake earthQuakeDao = new EarthQuake(earthQuake);
            earthQuakeDao.setWeather(weatherDao);
            weatherDao.setEarthQuake(earthQuakeDao);
        }

        WindDto wind = (WindDto) weather.getEvents().stream().filter(event -> event.getClass().isAssignableFrom(WindDto.class)).findFirst().orElse(null);
        if (wind != null) {
            Wind windDao = new Wind(wind);
            windDao.setWeather(weatherDao);
            weatherDao.setWind(windDao);
        }


        List<Event> precipitations = weather.getEvents().stream().filter(event -> event.getClass().isAssignableFrom(PrecipitationDto.class)).collect(Collectors.toList());

        List<Precipitation> precipitationDao = precipitations.stream().map(p -> {
            Precipitation precipitation = new Precipitation((PrecipitationDto) p);
            precipitation.setWeather(weatherDao);
            return precipitation;
        }).collect(Collectors.toList());
        weatherDao.setPrecipitations(precipitationDao);
    }

   /* @Override
    public Weather getByDate(String dateString) throws ParseException {
        Optional<Weather> weatherByDate = repository.findByDate(dateFormat.parse(dateString));
        return weatherByDate.isPresent() ? weatherByDate.get() : null;
    }*/
}
