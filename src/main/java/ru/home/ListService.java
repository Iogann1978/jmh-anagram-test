package ru.home;

import java.util.LinkedList;
import java.util.List;

public class ListService implements AnagramService {
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

        List<Character> list = new LinkedList<>();
        for(char c : chars1) {
            list.add(c);
        }

        for(char c : chars2) {
            if (list.contains(c))
                list.remove((Character)c);
            else
                return false;
        }

        return list.isEmpty();
    }
}
