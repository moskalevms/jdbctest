package ru.sberbank;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;
import service.impl.CrudOperationUserImpl;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        User user = context.getBean("user", User.class);
        user = new User(3,"guest","qwerty", "Vasya", "Sidorov");
        CrudOperations crud = context.getBean("crudImpl", CrudOperations.class);
        crud.create(user);
        crud.update(user);
        crud.read(1);
        crud.delete(user);
        System.out.println(user);
    }




}
