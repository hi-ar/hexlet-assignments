package exercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    JdbcTemplate jdbc;

    @PostMapping(path = "")
    public void createPerson(@RequestBody Map<String, Object> person) {
        String query = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        jdbc.update(query, person.get("first_name"), person.get("last_name"));
    }

    // BEGIN Создайте обработчик, который который обрабатывает GET запросы по пути people/
    // и возвращает список всех персон в виде JSON
    @GetMapping(path = "", produces = "application/json")
    public List<Map<String, Object>> showPersons() throws JsonProcessingException {
        String query = "SELECT * FROM person ORDER BY id";
        List<Map<String, Object>> persons = jdbc.queryForList(query);
        return jdbc.queryForList(query);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValueAsString(persons.toString());
//        return objectMapper.toString();
    }
    //Создайте обработчик, который обрабатывает GET запросы по пути people/{id}. Обработчик должен
    // возвращать конкретную персону в виде JSON
    @GetMapping(path = "/{id}", produces = "application/json")
    public Map <String, Object> showPerson(@PathVariable int id) throws JsonProcessingException {
        String query = "SELECT * FROM person WHERE id = " + id;
        Map <String, Object> person = jdbc.queryForList(query).get(0);
        return person;
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValueAsString(person.toString());
//        return objectMapper.toString();
    }
    // END
}
