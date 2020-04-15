package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;

import java.sql.*;

@Component("crudImpl")
public class CrudOperationUserImpl implements CrudOperations<User> {

    @Autowired
    User user;

    private Connection connection;
    public CrudOperationUserImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(User user) {
        System.out.println("Добавляю пользователя..");
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement( "INSERT INTO users(id, login, password, firstname, lastname)" + "VALUES(?,?,?,?,?)");
            ps.setLong(1,user.getId());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.execute();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        user.setPassword("987");
        String SQLUpdate = "UPDATE users SET password = " + "'" + user.getPassword() + "'";
        try {
            connection.createStatement().executeUpdate(SQLUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Обновление...");
    }

    @Override
    public User read(long id) {
        User user = new User();
        String sql = "SELECT * FROM users WHERE id = " + id;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(!resultSet.next()){
                return null;
            }
            user.setId(resultSet.getLong(1));
            user.setLogin(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setFirstName(resultSet.getString(4));
            user.setLastName(resultSet.getString(5));
            resultSet.close();
            System.out.println(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void delete(User user) {
        try {
            connection.createStatement().execute("DELETE FROM users where id = " + user.getId());
            System.out.println("Запись удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
