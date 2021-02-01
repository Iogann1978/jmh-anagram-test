package ru.home;

public interface AnagramService {
    boolean doCheck(String text1, String text2) throws IllegalArgumentException;
}
