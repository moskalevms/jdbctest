package service;

import ru.sberbank.entity.User;

import java.sql.*;

public class JdbcService {

    public static void main(String[] args) throws SQLException {
         Connection connection;
         User user = new User(1,"admin", "admin", "Ivan", "Ivanov" );

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=TRUE;Mode=Oracle", "sa", "");

        createTable(connection, user);
        readTable(connection,user);
        updateTable(connection,user);
        //deleteTable(connection);

    }

    static void createTable(Connection connection, User user){
        try {
            //connection = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=TRUE;Mode=Oracle", "sa", "");
            System.out.println("Создаю таблицу");
            connection.createStatement().execute("create table if not exists users(id serial primary key auto_increment not null, login varchar(255), password varchar(255), firstName varchar(255), lastName varchar(255)) ");
            System.out.println("Добавляю пользователя..");
            PreparedStatement ps = connection.prepareStatement( "INSERT INTO users(id, login, password, firstname, lastname)" + "VALUES(?,?,?,?,?)");
            ps.setLong(1,user.getId());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to connect");
            }
        }


        static void readTable(Connection connection, User user) throws SQLException {
            String sql = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setFirstName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
            }
            resultSet.close();
            System.out.println(user);
        }


    static void updateTable(Connection connection, User user) throws SQLException {
        String SQLUpdate = "UPDATE users SET firstName = 'Petr'";
        connection.createStatement().executeUpdate(SQLUpdate);
        System.out.println("Обновление...");
    }

    static void deleteTable(Connection connection) throws SQLException {
        connection.createStatement().execute("DROP TABLE users");
    }
}
