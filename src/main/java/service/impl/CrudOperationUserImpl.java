package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;
import ru.sberbank.service.JdbcConnection;

import java.sql.*;

@Component("crudImpl")
public class CrudOperationUserImpl implements CrudOperations<Long, User> {

    @Autowired
    private JdbcConnection conn;

    @Override
    public boolean createTable() {
        System.out.println("Создаю таблицу..");
        PreparedStatement ps;
        try {
            ps = conn.getConnection().prepareStatement("create table if not exists users(id serial primary key auto_increment not null, login varchar(255), password varchar(255), firstName varchar(255), lastName varchar(255))");
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void create(User user) {
        System.out.println(" добавляю пользователя..");
        PreparedStatement ps;
        try {
            ps = conn.getConnection().prepareStatement( "INSERT INTO users(id, login, password, firstname, lastname)" + "VALUES(?,?,?,?,?)");
            ps.setLong(1,user.getId());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.execute();
            ps.close();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        user.setPassword("987");
        String SQLUpdate = "UPDATE users SET password = " + "'" + user.getPassword() + "'";
        try {
            conn.getConnection().createStatement().executeUpdate(SQLUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Обновление...");
    }

    @Override
    public User read(Long id) {
        User user = new User();
        String sql = "SELECT * FROM users WHERE id = " + id;
        Statement statement = null;
        try {
            statement = conn.getConnection().createStatement();
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

    //TODO Удалять по id
    @Override
    public void delete(Long id) {
        try {

            String sql = "DELETE FROM users WHERE id = ?;";
            PreparedStatement ps = conn.getConnection().prepareStatement(sql);
            ps.setLong(1,id);
            ps.close();
            //conn.getConnection().createStatement().execute("DELETE FROM users where id = " + user.getId());
            System.out.println("Запись удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
