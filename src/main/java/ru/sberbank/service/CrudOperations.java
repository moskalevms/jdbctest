package ru.sberbank.service;

import ru.sberbank.entity.User;

public interface CrudOperations {
    void create(User user);
    void update(User user);
    User read(long id);
    void delete(User user);
}
