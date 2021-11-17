package ru.akshipulya.springcourse.dao;

import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.processor.SpringErrorClassTagProcessor;
import ru.akshipulya.springcourse.models.Person;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {

    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/spring_lessons_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "RootAdmin";

    private static Connection connection;

    /**
     * инициализация соединения с БД в статическом блоке инициализации
     */
    static {
        try {
            Class.forName("org.postgresql.Driver"); //подгрузка класса драйвер при помощи рефлексии
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(SQL); //помещаем полученные по запросу данные в ResultSet

            // обходим полученный Result Set и передаем его в ArrayList people
            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    public Person show(int id) {
        Person person = null;

        try {
            PreparedStatement pstm =
                    connection.prepareStatement("SELECT * FROM person WHERE id=?");
            pstm.setInt(1, id);

            ResultSet resultSet = pstm.executeQuery();

            resultSet.next();
            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }

    public void save(Person person) {
        try {
            PreparedStatement pstm =
                    connection.prepareStatement("INSERT INTO person VALUES(1, ?, ?, ?)");

            pstm.setString(1, person.getName());
            pstm.setInt(2, person.getAge());
            pstm.setString(3, person.getEmail());

            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement pstm =
                    connection.prepareStatement("UPDATE person SET name=?, age=?, email=? WHERE id=?");

            pstm.setString(1, updatedPerson.getName());
            pstm.setInt(2, updatedPerson.getAge());
            pstm.setString(3, updatedPerson.getEmail());
            pstm.setInt(4, id);

            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement pstm =
                    connection.prepareStatement("DELETE FROM person WHERE id=?");
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
