package ru.home;

import com.sun.tools.javac.util.List;
import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 3)
@Measurement(iterations = 7)
public class MyBenchmark {
    private Set<String> words = new HashSet<>();

    @Setup
    public void init() {
        words.add("debitcard");
        words.add("badcredit");
        words.add("astronomer");
        words.add("moonstarer");
    }

    //@Setup
    public void initLOTR() {
        InputStream is = this.getClass().getResourceAsStream("/book.txt");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                    sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern p = Pattern.compile("\\d+");
        for (String s : sb.toString().split("\\W+")) {
            if (!s.isEmpty()) {
                String S = s.toUpperCase();
                Matcher m = p.matcher(S);
                if (!m.find())
                    words.add(S);
            }
        }
    }

    @Benchmark
    public void testMap() {
        for (String w1 : words) {
            for (String w2 : words) {
                if (!w1.equals(w2))
                    AnagramService.checkAnagramMap(w1, w2);
            }
        }
    }

    @Benchmark
    public void testBites() {
        for (String w1 : words) {
            for (String w2 : words) {
                if (!w1.equals(w2))
                    AnagramService.checkAnagramBites(w1, w2);
            }
        }
    }

    @Benchmark
    public void testSort() {
        for (String w1 : words) {
            for (String w2 : words) {
                if (!w1.equals(w2))
                    AnagramService.checkAnagramSort(w1, w2);
            }
        }
    }

    @Benchmark
    public void testPow() {
        for (String w1 : words) {
            for (String w2 : words) {
                if (!w1.equals(w2))
                    AnagramService.checkAnagramPow(w1, w2);
            }
        }
    }

    @Benchmark
    public void testList() {
        for (String w1 : words) {
            for (String w2 : words) {
                if (!w1.equals(w2))
                    AnagramService.checkAnagramList(w1, w2);
            }
        }
    }
}
