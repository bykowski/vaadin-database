package pl.bykowski.manager;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bykowski.domains.Person;
import pl.bykowski.repo.PersonRepo;

import java.util.Optional;

@Service
public class PersonManager {

    private PersonRepo personRepo;

    @Autowired
    public PersonManager(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public Long addPerson(String name, String surname) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(surname)) {
            Optional<Person> personByNameAndSurname = personRepo.findPersonByNameAndSurname(name, surname);
            if (!personByNameAndSurname.isPresent()) {
                Person person = new Person();
                person.setName(name);
                person.setSurname(surname);
                Person savePerson = personRepo.save(person);
                return savePerson.getId();
            }
        }
        return -1L;
    }

    public Person getPersonById(Long id) {
        return personRepo.findById(id).get();
    }

    public boolean update(Long id, String name, String surname, String age) {
        Integer integerAge = tryParse(age);
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(surname) && integerAge != null) {
            Person person = personRepo.findById(id).get();
            person.setName(name);
            person.setSurname(surname);
            person.setAge(integerAge);
            personRepo.save(person);
            return true;
        }
        return false;
    }

    private Integer tryParse(String age) {
        try {
            return Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}


