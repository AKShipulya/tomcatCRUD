package ru.akshipulya.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.akshipulya.springcourse.models.Person;

import java.util.List;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }

    /**
     * для примера, вместо new PersonMapper() буду использовать new BeanPropertyRowMapper
     * который полностью выполняет аналогичную функцию, как наш кастомный маппер
     */
    public Person show(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null); // если будет найден объект с данным id -он будет возвращен,
        // в противном случае будет возвращать null
        // правильнее возвращать кастомный объект с ошибкой "not found"
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES(1, ?, ?, ?)",
                person.getName(),
                person.getAge(),
                person.getEmail()
        );
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(),
                updatedPerson.getAge(),
                updatedPerson.getEmail(),
                id
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
