package exercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.CityNotFoundException;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    // BEGIN
    @GetMapping(path = "/cities/{id}")
    Map getWeather(@PathVariable long id) throws JsonProcessingException {
        City currentCity = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException("NF city with id "
                + id));
        return weatherService.getWeather(currentCity);
    }

    @GetMapping(path = "/search")
    List<Map> searchWeather(@RequestParam(value = "name", required = false) String startsWith) {
        if (startsWith == null) {
            List<City> cities = (List<City>) cityRepository.findAllByOrderByName();
            return weatherService.getWeatherForList(cities);
        }
        List<City> cities = (List<City>) cityRepository.findByNameStartsWithIgnoreCase(startsWith);
        return weatherService.getWeatherForList(cities);
    }
    // END
}

