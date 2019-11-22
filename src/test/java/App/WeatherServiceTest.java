package App;


import App.dao.WeatherRepository;
import App.model.Weather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    private WeatherService service;

    @Mock
    private WeatherRepository repository;

    @Test
    public void getWeatherTest() throws ParseException {

        //assert
        service = new WeatherService();
        service.setRepository(repository);



        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Weather weather = new Weather();
        weather.setDate(dateFormat.parse("01.01.2000"));
        //weather.setEvents(Arrays.asList(event));

        given(repository.findById(Matchers.any())).willReturn(Optional.of(weather));

        //act
        Weather weatherAnswer = service.get(10);

        //assert
        assertThat(weatherAnswer).isEqualTo(weather);
    }

    @Test
    public void weatherSave() throws ParseException {

        //assert
       /* service = new WeatherService();
        service.setRepository(repository);

        EventParameter eventParameter = new EventParameter();
        eventParameter.setName("parameter_1");
        eventParameter.setValue(2);

        EventParameter eventParameter2 = new EventParameter();
        eventParameter2.setName("parameter_2");
        eventParameter2.setValue(3);

        Event event = new Event();
        event.setName("event_1");
        event.setParameters(Arrays.asList(eventParameter,eventParameter2));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Weather weather = new Weather();
        weather.setTemperature(30);
        weather.setDate(dateFormat.parse("01.01.2000"));
        weather.setEvents(Arrays.asList(event));

        //act
        service.add(weather);

        //assert
        verify(repository, times(1)).save(weather);*/
    }

}