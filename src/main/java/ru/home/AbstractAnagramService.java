package ru.home;

public abstract class AbstractAnagramService<T> implements AnagramService {
    abstract protected boolean checkEquals(T obj1, T obj2) throws IllegalArgumentException;
}
