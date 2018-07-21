package pl.bykowski.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bykowski.domains.Person;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

    Optional<Person> findPersonByNameAndSurname(String name, String surname);
}
