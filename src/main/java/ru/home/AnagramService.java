package ru.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class AnagramService {
    private static final Logger logger = LoggerFactory.getLogger(AnagramService.class);

    public static boolean checkAnagramSort(String text1, String text2) throws IllegalArgumentException {
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
        return text1n.equals(text2n);
    }

    public static boolean checkAnagramBites(String text1, String text2) throws IllegalArgumentException {
        if(text1.isEmpty() || text2.isEmpty())
            throw new IllegalArgumentException("empty string");
        if(text1.equals(text2))
            return true;
        if(text1.length() != text2.length())
            return false;

        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();

        char max1 = 0;
        for(char c : chars1) {
            if(c > max1) max1 = c;
        }
        char max2 = 0;
        for(char c : chars2) {
            if(c > max2) max2 = c;
        }
        if (max1 != max2)
            return false;

        BitSet s1 = new BitSet(max1 + 1);
        StringBuilder sb1 = new StringBuilder();
        for(char c : chars1) {
            if (s1.get(c))
                sb1.append(c);
            else
                s1.set(c);
        }

        BitSet s2 = new BitSet(max2 + 1);
        StringBuilder sb2 = new StringBuilder();
        for(char c : chars2) {
            if (s2.get(c))
                sb2.append(c);
            else
                s2.set(c);
        }

        String doubles1 = sb1.toString();
        String doubles2 = sb2.toString();
        if(!doubles1.isEmpty() || !doubles2.isEmpty()) {
            if (doubles1.length() != doubles2.length())
                return false;
            else
                if (!checkAnagramBites(doubles1, doubles2))
                    return false;
        }

        return checkEquals(s1, s2);
    }

    private static boolean checkEquals(BitSet s1, BitSet s2) throws IllegalArgumentException {
        if (s1.isEmpty() || s2.isEmpty())
            throw new IllegalArgumentException("empty set");

        if (s1.length() != s2.length())
            return false;

        s1.xor(s2);
        return s1.isEmpty();
    }

    public static boolean checkAnagramPow(String text1, String text2) throws IllegalArgumentException {
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

        return s1 == s2;
    }

    public static boolean checkAnagramMap(String text1, String text2) throws IllegalArgumentException {
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

    private static boolean checkEquals(Map<Character, Integer> m1, Map<Character, Integer> m2) throws IllegalArgumentException {
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

    public static boolean checkAnagramList(String text1, String text2) throws IllegalArgumentException {
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

    public static boolean checkAnagramBitesAsync(String text1, String text2) throws IllegalArgumentException {
        if (text1.isEmpty() || text2.isEmpty())
            throw new IllegalArgumentException("empty string");
        if (text1.equals(text2))
            return true;
        if (text1.length() != text2.length())
            return false;

        return ForkJoinPool.commonPool().invoke(new AsyncAnargamTask(text1, text2));
    }
}
