package App;


import App.dao.WeatherRepository;
import App.dto.EarthQuakeDto;
import App.dto.PrecipitationDto;
import App.dto.WeatherDto;
import App.dto.WindDto;
import App.model.EarthQuake;
import App.model.Precipitation;
import App.model.Weather;
import App.model.Wind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    private WeatherServiceImpl service;

    @Mock
    private WeatherRepository repository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Captor
    private ArgumentCaptor<Weather> weatherDaoCaptor;

    @Test
    public void getWeatherByDateTest() throws ParseException {

        //arrange
        service = new WeatherServiceImpl();
        service.setRepository(repository);

        Weather weather = new Weather();

        Precipitation precipitation = new Precipitation();
        precipitation.setWeather(weather);
        precipitation.setNumberOfDailyAllowances(4);
        precipitation.setIntensity(2.4f);
        precipitation.setTemperature(30f);

        Precipitation precipitation2 = new Precipitation();
        precipitation2.setWeather(weather);
        precipitation2.setNumberOfDailyAllowances(5);
        precipitation2.setIntensity(2.5f);
        precipitation2.setTemperature(-50f);

        EarthQuake earthQuake = new EarthQuake();
        earthQuake.setWeather(weather);
        earthQuake.setMagnitudeScaleValue(8.5f);

        Wind wind = new Wind();
        wind.setWeather(weather);
        wind.setSpeed(55.4f);

        weather.setDate(dateFormat.parse("01.01.2000"));
        weather.setPrecipitations(Arrays.asList(precipitation, precipitation2));
        weather.setEarthQuake(earthQuake);
        weather.setWind(wind);

        given(repository.findByDate(ArgumentMatchers.any())).willReturn(Optional.of(weather));

        //act
        WeatherDto weatherDto = service.getByDate("01.01.2000");

        //assert
        assertThat(weatherDto.getDate()).isEqualTo(dateFormat.parse("01.01.2000"));
        assertThat(weatherDto.getEvents().size()).isEqualTo(4);
        assertThat(weatherDto.getEvents().stream().filter(event -> event.getClass().isAssignableFrom(WindDto.class))
                .anyMatch(event -> {
                    WindDto windDto = (WindDto) event;
                    return windDto.getSpeed() == 55.4f;
                })).isTrue();
        assertThat(weatherDto.getEvents().stream().filter(event -> event.getClass().isAssignableFrom(EarthQuakeDto.class))
                .anyMatch(event -> {
                    EarthQuakeDto earthQuakeDto = (EarthQuakeDto) event;
                    return earthQuakeDto.getMagnitudeScaleValue() == 8.5f;
                })).isTrue();
        assertThat(weatherDto.getEvents().stream().filter(event -> event.getClass().isAssignableFrom(PrecipitationDto.class))
                .anyMatch(event -> {
                    PrecipitationDto precipitationDto = (PrecipitationDto) event;
                    return precipitationDto.getIntensity() == 2.5f &&
                            precipitationDto.getNumberOfDailyAllowances() == 5 &&
                            precipitationDto.getTemperature() == -50.0f &&
                            precipitationDto.getName().equals("snow");
                })).isTrue();
        assertThat(weatherDto.getEvents().stream().filter(event -> event.getClass().isAssignableFrom(PrecipitationDto.class))
                .anyMatch(event -> {
                    PrecipitationDto precipitationDto = (PrecipitationDto) event;
                    return precipitationDto.getIntensity() == 2.4f &&
                            precipitationDto.getNumberOfDailyAllowances() == 4 &&
                            precipitationDto.getTemperature() == 30f &&
                            precipitationDto.getName().equals("rain");
                })).isTrue();
    }

    @Test
    public void weatherSaveTest() throws ParseException {

        //assert
        service = new WeatherServiceImpl();
        service.setRepository(repository);

        given(repository.save(ArgumentMatchers.any())).willReturn(new Weather());

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setDate(dateFormat.parse("11.11.2011"));
        weatherDto.setEvents(Arrays.asList(
                new WindDto("wind", 1.5f),
                new PrecipitationDto("precipitation", 10, 5, 20, "rain")));

        //act
        service.add(weatherDto);

        //assert
        verify(repository, times(1)).save(weatherDaoCaptor.capture());

        Weather weatherDaoCaptorValue = weatherDaoCaptor.getValue();
        assertThat(weatherDaoCaptorValue.getDate()).isEqualTo(dateFormat.parse("11.11.2011"));
        assertThat(weatherDaoCaptorValue.getWind().getSpeed()).isEqualTo(1.5f);
        assertThat(weatherDaoCaptorValue.getPrecipitations().get(0).getTemperature()).isEqualTo(20.0f);
        assertThat(weatherDaoCaptorValue.getPrecipitations().get(0).getIntensity()).isEqualTo(5);
        assertThat(weatherDaoCaptorValue.getPrecipitations().get(0).getNumberOfDailyAllowances()).isEqualTo(10);
    }
}