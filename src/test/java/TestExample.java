import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Server;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.SpringConfig;
import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;
import ru.sberbank.service.JdbcConnection;
import service.impl.CrudOperationUserImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestExample {

    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    JdbcConnection connnection = context.getBean("jdbcConn", JdbcConnection.class);
    CrudOperationUserImpl crudOperations = context.getBean("crudImpl", CrudOperationUserImpl.class);

    @Before()
    public void init() throws SQLException {
        connnection.getConnection();
        crudOperations.createTable();
    }

    @Test
    public void testCreate(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", user, user2 );
    }

    @Test
    public void testRead(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        crudOperations.read(user.getId());
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", user, user2 );
    }

    @Test
    public void testUpdate(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        user.setPassword("987");
        crudOperations.update(user);
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", user, user2 );
    }

    @Test
    public void testDelete(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
      //  crudOperations.create(user);
        crudOperations.delete(user.getId());
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", null, user2 );
    }


    @After
    public void stop() throws SQLException {
       Statement statement = connnection.getConnection().createStatement();
       statement.execute("DROP TABLE users");
    }

}
