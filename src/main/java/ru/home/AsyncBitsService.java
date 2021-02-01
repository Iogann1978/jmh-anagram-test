package ru.home;

import java.util.concurrent.ForkJoinPool;

public class AsyncBitsService extends AbstractAnagramService<String> {
    @Override
    protected boolean checkEquals(String text1, String text2) throws IllegalArgumentException {
        return ForkJoinPool.commonPool().invoke(new AsyncBitsTask(new String[]{text1, text2}));
    }

    @Override
    public boolean doCheck(String text1, String text2) throws IllegalArgumentException {
        if (text1.isEmpty() || text2.isEmpty())
            throw new IllegalArgumentException("empty string");
        if (text1.equals(text2))
            return true;
        if (text1.length() != text2.length())
            return false;

        return checkEquals(text1, text2);
    }
}
