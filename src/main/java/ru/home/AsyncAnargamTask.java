package ru.home;

import java.util.BitSet;
import java.util.concurrent.RecursiveTask;

public class AsyncAnargamTask extends RecursiveTask<Boolean> {

    private String text1, text2;

    public AsyncAnargamTask(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    @Override
    protected Boolean compute() {
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
            else {
                AsyncAnargamTask subTask = new AsyncAnargamTask(doubles1, doubles2);
                subTask.fork();
                if (!subTask.join())
                    return false;
            }
        }

        return checkEquals(s1, s2);
    }

    private boolean checkEquals(BitSet s1, BitSet s2) {
        if (s1.length() != s2.length())
            return false;

        s1.xor(s2);
        return s1.isEmpty();
    }
}
