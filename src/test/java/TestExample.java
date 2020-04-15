import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Server;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;
import service.impl.CrudOperationUserImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestExample {

    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE;Mode=Oracle";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private Connection connection;
    private CrudOperations<User> crudOperations;
    private Server server;

    @Before()
    public void init() throws SQLException {
        DeleteDbFiles.execute("~", "test", true);
        server = Server.createWebServer();
        server.start();
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        crudOperations = new CrudOperationUserImpl(conn);
        Statement statement = conn.createStatement();
        statement.execute("create table if not exists users(id serial primary key auto_increment not null, login varchar(255), password varchar(255), firstName varchar(255), lastName varchar(255))");
    }

    @Test
    public void testCreate(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", user, user2 );
    }

    @Test
    public void testUpdate(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        user.setPassword("987");
        crudOperations.update(user);
        User user2 =  crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", user, user2 );
    }

    @Test
    public void testDelete(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        crudOperations.delete(user);
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", null, user2 );
    }

    @Test
    public void testRead(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        crudOperations.read(1);
        User user2 = crudOperations.read(1);
        Assert.assertEquals("Все хорошо", user, user2 );
    }

    @After
    public void stop() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = conn.createStatement();
        statement.execute("DROP table users");
        server.stop();
    }
}
