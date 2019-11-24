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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
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

    @Captor ArgumentCaptor<Long> longCaptor;

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
        wind.setName("storm");

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
                    return windDto.getSpeed() == 55.4f && windDto.getName().equals("storm");
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

        //arrange
        service = new WeatherServiceImpl();
        service.setRepository(repository);

        given(repository.save(ArgumentMatchers.any())).willReturn(new Weather());

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setDate(dateFormat.parse("11.11.2011"));
        weatherDto.setEvents(Arrays.asList(
                new WindDto("wind", 1.5f, "calm"),
                new PrecipitationDto("precipitation", 10, 5, 20, "rain")));

        //act
        service.add(weatherDto);

        //assert
        verify(repository, times(1)).save(weatherDaoCaptor.capture());

        Weather weatherDaoCaptorValue = weatherDaoCaptor.getValue();
        assertThat(weatherDaoCaptorValue.getDate()).isEqualTo(dateFormat.parse("11.11.2011"));
        assertThat(weatherDaoCaptorValue.getWind().getSpeed()).isEqualTo(1.5f);
        assertThat(weatherDaoCaptorValue.getWind().getName()).isEqualTo("calm");
        assertThat(weatherDaoCaptorValue.getPrecipitations().get(0).getTemperature()).isEqualTo(20.0f);
        assertThat(weatherDaoCaptorValue.getPrecipitations().get(0).getIntensity()).isEqualTo(5);
        assertThat(weatherDaoCaptorValue.getPrecipitations().get(0).getNumberOfDailyAllowances()).isEqualTo(10);
    }

    @Test
    public void windSaveTest() throws ParseException {
        //arrange
        service = new WeatherServiceImpl();
        service.setRepository(repository);

        given(repository.save(ArgumentMatchers.any())).willReturn(new Weather());

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setDate(dateFormat.parse("11.11.2011"));
        weatherDto.setEvents(Arrays.asList(
                new WindDto("wind", 1.5f, "")));

        WeatherDto weatherDto2 = new WeatherDto();
        weatherDto2.setDate(dateFormat.parse("12.11.2011"));
        weatherDto2.setEvents(Arrays.asList(
                new WindDto("wind", 10f, "")));

        WeatherDto weatherDto3 = new WeatherDto();
        weatherDto3.setDate(dateFormat.parse("13.11.2011"));
        weatherDto3.setEvents(Arrays.asList(
                new WindDto("wind", 47f, "")));

        WeatherDto weatherDto4 = new WeatherDto();
        weatherDto4.setDate(dateFormat.parse("14.11.2011"));
        weatherDto4.setEvents(Arrays.asList(
                new WindDto("wind", 65f, "")));

        //act
        service.add(weatherDto);
        service.add(weatherDto2);
        service.add(weatherDto3);
        service.add(weatherDto4);

        //assert
        verify(repository, times(4)).save(weatherDaoCaptor.capture());

        List<Weather> weatherDaoCaptorValues = weatherDaoCaptor.getAllValues();
        assertThat(weatherDaoCaptorValues.get(0).getWind().getName()).isEqualTo("calm");
        assertThat(weatherDaoCaptorValues.get(1).getWind().getName()).isEqualTo("moderate");
        assertThat(weatherDaoCaptorValues.get(2).getWind().getName()).isEqualTo("storm");
        assertThat(weatherDaoCaptorValues.get(3).getWind().getName()).isEqualTo("hurricane");
    }

    @Test
    public void saveWithoutEventsTest() throws ParseException {
        //arrange
        service = new WeatherServiceImpl();
        service.setRepository(repository);

        given(repository.save(ArgumentMatchers.any())).willReturn(new Weather());

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setDate(dateFormat.parse("11.11.2011"));

        //act
        service.add(weatherDto);

        //assert
        verify(repository, times(1)).save(weatherDaoCaptor.capture());
        Weather weatherDaoCaptorValue = weatherDaoCaptor.getValue();
        assertThat(weatherDaoCaptorValue.getPrecipitations()).isNull();
        assertThat(weatherDaoCaptorValue.getWind()).isNull();
        assertThat(weatherDaoCaptorValue.getEarthQuake()).isNull();
    }

    @Test
    public void duplicateDateAddTest() throws ParseException {
        //arrange
        service = new WeatherServiceImpl();
        service.setRepository(repository);

        given(repository.save(ArgumentMatchers.any())).willReturn(new Weather());

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setDate(dateFormat.parse("11.11.2011"));
        WeatherDto weatherDto2 = new WeatherDto();
        weatherDto2.setDate(dateFormat.parse("11.11.2011"));

        Weather existingWeather = new Weather();
        existingWeather.setDate(weatherDto2.getDate());

        given(repository.findByDate(ArgumentMatchers.any())).willReturn(Optional.empty()).willReturn(Optional.of(existingWeather));

        //act
        IllegalArgumentException exception = null;
        service.add(weatherDto);
        try {
            service.add(weatherDto2);
        }
        catch (IllegalArgumentException e)
        {
            exception = e;
        }

        //arrange
        verify(repository, times(1)).save(weatherDaoCaptor.capture());
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("duplicate date");
    }

    @Test
    public void deleteWeatherTest()
    {
        //arrange
        service = new WeatherServiceImpl();
        service.setRepository(repository);

        Weather weather = new Weather();
        weather.setId(10);
        given(repository.findById(ArgumentMatchers.any())).willReturn(Optional.of(weather));

        //act
        boolean deleted = service.delete(10L);

        //assert
        verify(repository).delete(weather);
        assertThat(deleted).isTrue();
    }

    @Test
    public void deleteNotExistingWeatherTest()
    {
        //arrange
        service = new WeatherServiceImpl();
        service.setRepository(repository);
        given(repository.findById(ArgumentMatchers.any())).willReturn(Optional.empty());

        //act
        boolean deleted = service.delete(10L);

        //assert
        verify(repository, times(0)).delete(weatherDaoCaptor.capture());
        assertThat(deleted).isFalse();
    }
}