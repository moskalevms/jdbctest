package ru.sberbank.service;

public interface CrudOperations<K, V> {
    void create(V obj);
    void update(V obj);
    V read(Long key);
    void delete(V obj);
}
