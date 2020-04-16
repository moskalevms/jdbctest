package ru.sberbank;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;
import ru.sberbank.service.JdbcConnection;
import service.impl.CrudOperationUserImpl;

//Для отладки
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        JdbcConnection conn = context.getBean("jdbcConn", JdbcConnection.class);
        CrudOperations crud = context.getBean("crudImpl", CrudOperationUserImpl.class);
        User user = new User(4, "gegsd", "3423", "werewrt", "ergag");
        crud.createTable();
        crud.create(user);
        crud.delete(user.getId());
    }
}
