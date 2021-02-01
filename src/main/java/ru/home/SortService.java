package ru.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class SortService extends AbstractAnagramService<String> {
    private static final Logger logger = LoggerFactory.getLogger(SortService.class);

    @Override
    public boolean doCheck(String text1, String text2) throws IllegalArgumentException {
        if(text1.isEmpty() || text2.isEmpty())
            throw new IllegalArgumentException("empty string");
        if(text1.equals(text2))
            return true;
        if(text1.length() != text2.length())
            return false;

        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        String text1n = new String(chars1);
        String text2n = new String(chars2);
        return checkEquals(text1n, text2n);
    }

    @Override
    protected boolean checkEquals(String obj1, String obj2) throws IllegalArgumentException {
        return obj1.equals(obj2);
    }
}
