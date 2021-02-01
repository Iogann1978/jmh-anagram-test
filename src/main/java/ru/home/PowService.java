package ru.home;

import java.util.HashMap;
import java.util.Map;

public class PowService extends AbstractAnagramService<Double> {
    @Override
    protected boolean checkEquals(Double obj1, Double obj2) {
        return obj1.equals(obj2);
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

        Map<String, Integer> degree = new HashMap<>();
        Map<Character, Character> counter = new HashMap<>();

        double s1 = 0.0d;
        for(char c : chars1) {
            counter.computeIfPresent(c, (k, v) -> ++v);
            char key = counter.computeIfAbsent(c, k -> '0');
            int d = degree.computeIfAbsent(new String(new char[]{c, key}), k -> degree.size());
            s1 += Math.pow(2.0d, d);
        }

        counter.clear();
        double s2 = 0.0d;
        for(char c : chars2) {
            counter.computeIfPresent(c, (k, v) -> ++v);
            char key = counter.computeIfAbsent(c, k -> '0');
            String dk = new String(new char[]{c, key});
            if (!degree.containsKey(dk))
                return false;
            else {
                int d = degree.get(dk);
                s2 += Math.pow(2.0d, d);
            }
        }

        return checkEquals(s1, s2);
    }
}
