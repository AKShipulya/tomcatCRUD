package ru.akshipulya.springcourse.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.akshipulya.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * класс, который переводит строки из записей в BD в поля объекта Person
 * можно использовать RowMapper от Spring --> new BeanPropertyRowMapper<>(Person.class) в DAO в качестве аргумента
 * dbcTemplate.query (см. PersonDao)
 */
public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {

        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));

        return person;
    }
}
