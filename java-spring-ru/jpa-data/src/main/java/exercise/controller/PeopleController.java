package exercise.controller;

import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import exercise.model.Person;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    @GetMapping()
    public List<Person> showAll() {
        return personRepository.findAll();
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Person create(@RequestBody Person newperson) {
        System.out.println("±±±±±§§§§§§ Now Contains entrys: " + personRepository.findAll().size());
        System.out.println("±±±±±§§§§§§ NewPers1 (id, first, last): " + newperson.getId() + newperson.getFirstName());
        personRepository.save(newperson);
        System.out.println("±±±±±§§§§§§ NewPers2 (id, first, last): " + newperson.getId() + newperson.getFirstName());
        return newperson; //w id?
    }


    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        System.out.println("±±±±±§§§§§§ Now Contains entrys(1): " + personRepository.findAll().size());
        Person toDelete = personRepository.findById(id).get();
        System.out.println("±±±±±§§§§§§ toDel (id, first, last): " + toDelete.getId() + toDelete.getFirstName());
        personRepository.delete(toDelete);
        System.out.println("±±±±±§§§§§§ Now Contains entrys(2): " + personRepository.findAll().size());
    }
    // END
}
