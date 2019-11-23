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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WeatherServiceImpl implements WeatherService {

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

        return weatherDaoToDto(weatherDao);
    }

    private WeatherDto weatherDaoToDto(Weather weatherDao) {

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setEvents(new ArrayList<>());
        weatherDto.setDate(weatherDao.getDate());
        weatherDto.setId(weatherDao.getId());

        EarthQuake earthQuake = weatherDao.getEarthQuake();
        Wind wind = weatherDao.getWind();
        List<Precipitation> precipitations = weatherDao.getPrecipitations();

        if (earthQuake != null)
            weatherDto.getEvents().add(new EarthQuakeDto("earthQuake", earthQuake.getMagnitudeScaleValue()));

        if (wind != null)
            weatherDto.getEvents().add(new WindDto("wind", wind.getSpeed()));

        precipitations.stream().forEach(p -> {
            weatherDto.getEvents().add(
                    new PrecipitationDto("precipitation", p.getNumberOfDailyAllowances(), p.getIntensity(), p.getTemperature(), p.getTemperature() <= 0 ? "snow" : "rain"));
        });

        return weatherDto;
    }

    @Transactional
    @Override
    public long add(WeatherDto weather) {
        Weather weatherDao = weatherDtoToDao(weather);
        Weather saved = repository.save(weatherDao);
        return saved.getId();
    }

    private Weather weatherDtoToDao(WeatherDto weather) {
        Weather weatherDao = new Weather();
        weatherDao.setDate(weather.getDate());

        if (weather.getEvents() == null)
            return  weatherDao;

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

        return weatherDao;
    }

    @Override
    public WeatherDto getByDate(String dateString) throws ParseException {
        Weather weatherByDate = repository.findByDate(dateFormat.parse(dateString)).orElse(null);
        if (weatherByDate == null)
            return null;

        return weatherDaoToDto(weatherByDate);
    }
}
