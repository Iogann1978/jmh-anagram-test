package ru.home;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class AsyncBitsTask extends RecursiveTask<Boolean> {

    private String[] texts;

    List<BitSet> listBS;

    public AsyncBitsTask(String[] texts) {
        this.texts = texts;
    }

    @Override
    protected Boolean compute() {
        listBS = new ArrayList<>();
        if (texts != null && texts.length == 2) {
            AsyncBitsTask subTask = new AsyncBitsTask(new String[]{texts[1]});
            subTask.fork();
            doTask(texts[0], listBS);
            subTask.join();
            return checkEquals(getListBS(), subTask.getListBS());
        } else {
            doTask(texts[0], listBS);
            return true;
        }
    }

    private void doTask(String text, List<BitSet> listBS) {
        char[] chars = text.toCharArray();

        char max = 0;
        for(char c : chars) {
            if(c > max) max = c;
        }

        BitSet s = new BitSet(max + 1);
        StringBuilder sb = new StringBuilder();
        for(char c : chars) {
            if (s.get(c))
                sb.append(c);
            else
                s.set(c);
        }
        listBS.add(s);

        if(sb.length() != 0) {
            doTask(sb.toString(), listBS);
        }
    }

    private boolean checkEquals(List<BitSet> lbs1, List<BitSet> lbs2) {
        if (lbs1.size() != lbs2.size())
            return false;
        for (int i = 0; i < lbs1.size(); i++) {
            if (!checkEquals(lbs1.get(i), lbs2.get(i)))
                return false;
        }
        return true;
    }

    private static boolean checkEquals(BitSet s1, BitSet s2) throws IllegalArgumentException {
        if (s1.isEmpty() || s2.isEmpty())
            throw new IllegalArgumentException("empty set");

        if (s1.length() != s2.length())
            return false;

        s1.xor(s2);
        return s1.isEmpty();
    }

    public List<BitSet> getListBS() {
        return listBS;
    }
}
