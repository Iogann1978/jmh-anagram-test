package ru.home;

import java.util.BitSet;

public class BitsService extends AbstractAnagramService<BitSet> {
    @Override
    protected boolean checkEquals(BitSet s1, BitSet s2) throws IllegalArgumentException {
        if (s1.isEmpty() || s2.isEmpty())
            throw new IllegalArgumentException("empty set");

        if (s1.length() != s2.length())
            return false;

        s1.xor(s2);
        return s1.isEmpty();
    }

    @Override
    public boolean doCheck(String text1, String text2) {
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

        if (!checkEquals(s1, s2))
            return false;

        if(sb1.length() != 0 && sb1.length() == sb2.length()) {
            String doubles1 = sb1.toString();
            String doubles2 = sb2.toString();
            if (!doCheck(doubles1, doubles2))
                return false;
        }

        return true;
    }
}
