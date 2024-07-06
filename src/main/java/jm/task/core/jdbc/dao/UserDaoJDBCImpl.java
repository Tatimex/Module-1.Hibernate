package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import java.sql.*;
import jm.task.core.jdbc.util.Util;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Users(
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(64),
                    lastname VARCHAR(64),
                    age SMALLINT
                    );
                    """);
            System.out.println("The table is created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS Users");
                System.out.println("Таблица удалена");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void saveUser(String name, String lastName, byte age) {
            String sql = "INSERT INTO Users(name, lastname, age) VALUES(?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                System.out.println("User with name " + name + " is added to the database");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void removeUserById(long id) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE id = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
                System.out.println("The user is deleted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public List<User> getAllUsers() {
            List<User> allUser = new ArrayList<>();
            String sql = "SELECT id, name, lastName, age FROM Users";

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    allUser.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return allUser;
        }


        public void cleanUsersTable() {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM Users");
                System.out.println("The table is cleaned");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("The table could not be cleaned");
            }
    }
}
