package ru.home;

import java.util.HashMap;
import java.util.Map;

public class MapService extends AbstractAnagramService<Map<Character, Integer>> {
    @Override
    protected boolean checkEquals(Map<Character, Integer> m1, Map<Character, Integer> m2) throws IllegalArgumentException {
        if (m1.isEmpty() || m2.isEmpty())
            throw new IllegalArgumentException("empty set");

        if (m1.size() != m2.size())
            return false;
        for (char v : m1.keySet()) {
            if (!m2.containsKey(v))
                return false;
            if (m1.get(v) != m2.get(v))
                return false;
        }
        return true;
    }

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

        Map<Character, Integer> m1 = new HashMap<>();
        for(char c : chars1) {
            m1.computeIfPresent(c, (k, v) -> ++v);
            m1.computeIfAbsent(c, k -> 0);
        }

        Map<Character, Integer> m2 = new HashMap<>();
        for(char c : chars2) {
            m2.computeIfPresent(c, (k, v) -> ++v);
            m2.computeIfAbsent(c, k -> 0);
        }

        return checkEquals(m1, m2) && checkEquals(m2, m1);
    }
}
