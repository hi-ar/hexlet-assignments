package exercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import exercise.CityNotFoundException;
import exercise.repository.CityRepository;
import exercise.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class WeatherService {

    @Autowired
    CityRepository cityRepository;

    // Клиент
    HttpClient client;

    // При создании класса сервиса клиент передаётся снаружи
    // В теории это позволит заменить клиент без изменения самого сервиса
    WeatherService(HttpClient client) {
        this.client = client;
    }

    private Logger log = LoggerFactory.getLogger(WeatherService.class);

    // BEGIN
    public Map getWeather(City city) throws JsonProcessingException {
        log.info("GW 1/3 incoming city " + city.getName());
        String resp = client.get("http://weather/api/v2/cities/" + city.getName());
        log.info("GW 2/3 clint got string response from weather service: " + resp);
        // city not found
        Map <String, String> result = new ObjectMapper().readValue(resp, Map.class);
        log.info("GW 3/3 response mapped to Map");
        return result;
    }

    public List<Map> getWeatherForList(List<City> cities) {
        log.info("GWFL got list of cities:" + cities.stream().map(City::getName).toList());
        if (cities.isEmpty()) {
            log.error("GWFL got empty list of cities");
            return new ArrayList<>();
        }
        List<Map> result = cities.stream()
                .map(city -> {
                    try {
                        return getWeather(city);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                })
                .toList();
        return result;
    }
    // END
}
