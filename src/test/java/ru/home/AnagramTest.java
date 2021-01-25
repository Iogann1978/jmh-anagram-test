package ru.home;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(JUnitPlatform.class)
public class AnagramTest {
    private static final Logger logger = LoggerFactory.getLogger(AnagramTest.class);
    private List<Character> chars = new ArrayList<>();

    @BeforeEach
    public void init() {
        chars.clear();
        for (char c = 65; c <= 122; c++) {
            if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                chars.add(c);
            }
        }
    }

    @Test
    public void testCharsSort() {
        Assert.assertNotNull("chars is null", chars);
        Assert.assertFalse("chars is empty", chars.isEmpty());
        Map<String, Set<String>> multiMap = new HashMap<>();
        for (char c1 : chars) {
            for (char c2 : chars) {
                if (c1 != c2) {
                    String text1 = new String(new char[]{c1});
                    String text2 = new String(new char[]{c2});
                    if (AnagramService.checkAnagramSort(text1, text2))
                        multiMap.computeIfAbsent(text1, key -> new HashSet<>()).add(text2);
                }
            }
        }
        multiMap.entrySet().stream().filter(e -> e.getValue().size() > 2)
                .forEach(e -> logger.debug("Collisions for {}: {}", e.getKey(), e.getValue())
        );
        Assert.assertTrue(AnagramService.checkAnagramSort("BRITNEYSPeARs", "PRESBYTeRIANs"));
        Assert.assertTrue(AnagramService.checkAnagramSort("ERICcLAPTON", "NARCOLEPTIc"));
        Assert.assertTrue(AnagramService.checkAnagramSort("BRITNEYSPEARS", "PRESBYTERIANS"));
        Assert.assertTrue(AnagramService.checkAnagramSort("ERICCLAPTON", "NARCOLEPTIC"));
        Assert.assertFalse(AnagramService.checkAnagramSort("ERICCLAPTON", "ERICcLAPTON"));
        Assert.assertFalse(AnagramService.checkAnagramSort("BRITNEYSPEARS", "BRITNEYSPeARs"));
    }

    @Test
    public void testCharsBits() {
        Assert.assertNotNull("chars is null", chars);
        Assert.assertFalse("chars is empty", chars.isEmpty());
        Map<String, Set<String>> multiMap = new HashMap<>();
        for (char c1 : chars) {
            for (char c2 : chars) {
                if (c1 != c2) {
                    String text1 = new String(new char[]{c1});
                    String text2 = new String(new char[]{c2});
                    if (AnagramService.checkAnagramBites(text1, text2))
                        multiMap.computeIfAbsent(text1, key -> new HashSet<>()).add(text2);
                }
            }
        }
        multiMap.entrySet().stream().filter(e -> e.getValue().size() > 2)
                .forEach(e -> logger.debug("Collisions for {}: {}", e.getKey(), e.getValue())
                );
        Assert.assertTrue(AnagramService.checkAnagramBites("BRITNEYSPeARs", "PRESBYTeRIANs"));
        Assert.assertTrue(AnagramService.checkAnagramBites("ERICcLAPTON", "NARCOLEPTIc"));
        Assert.assertTrue(AnagramService.checkAnagramBites("BRITNEYSPEARS", "PRESBYTERIANS"));
        Assert.assertTrue(AnagramService.checkAnagramBites("ERICCLAPTON", "NARCOLEPTIC"));
        Assert.assertFalse(AnagramService.checkAnagramBites("ERICCLAPTON", "ERICcLAPTON"));
        Assert.assertFalse(AnagramService.checkAnagramBites("BRITNEYSPEARS", "BRITNEYSPeARs"));
        Assert.assertFalse(AnagramService.checkAnagramBites("DEEPENED", "DEPENDED"));
    }

    @Test
    public void testCharsBitsAsync() {
        Assert.assertNotNull("chars is null", chars);
        Assert.assertFalse("chars is empty", chars.isEmpty());
        Map<String, Set<String>> multiMap = new HashMap<>();
        for (char c1 : chars) {
            for (char c2 : chars) {
                if (c1 != c2) {
                    String text1 = new String(new char[]{c1});
                    String text2 = new String(new char[]{c2});
                    if (AnagramService.checkAnagramBitesAsync(text1, text2))
                        multiMap.computeIfAbsent(text1, key -> new HashSet<>()).add(text2);
                }
            }
        }
        multiMap.entrySet().stream().filter(e -> e.getValue().size() > 2)
                .forEach(e -> logger.debug("Collisions for {}: {}", e.getKey(), e.getValue())
                );
        Assert.assertTrue(AnagramService.checkAnagramBitesAsync("BRITNEYSPeARs", "PRESBYTeRIANs"));
        Assert.assertTrue(AnagramService.checkAnagramBitesAsync("ERICcLAPTON", "NARCOLEPTIc"));
        Assert.assertTrue(AnagramService.checkAnagramBitesAsync("BRITNEYSPEARS", "PRESBYTERIANS"));
        Assert.assertTrue(AnagramService.checkAnagramBitesAsync("ERICCLAPTON", "NARCOLEPTIC"));
        Assert.assertFalse(AnagramService.checkAnagramBitesAsync("ERICCLAPTON", "ERICcLAPTON"));
        Assert.assertFalse(AnagramService.checkAnagramBitesAsync("BRITNEYSPEARS", "BRITNEYSPeARs"));
        Assert.assertFalse(AnagramService.checkAnagramBitesAsync("DEEPENED", "DEPENDED"));
    }

    @Test
    public void testCharsMap() {
        Assert.assertNotNull("chars is null", chars);
        Assert.assertFalse("chars is empty", chars.isEmpty());
        Map<String, Set<String>> multiMap = new HashMap<>();
        for (char c1 : chars) {
            for (char c2 : chars) {
                if (c1 != c2) {
                    String text1 = new String(new char[]{c1});
                    String text2 = new String(new char[]{c2});
                    if (AnagramService.checkAnagramMap(text1, text2))
                        multiMap.computeIfAbsent(text1, key -> new HashSet<>()).add(text2);
                }
            }
        }
        multiMap.entrySet().stream().filter(e -> e.getValue().size() > 2)
                .forEach(e -> logger.debug("Collisions for {}: {}", e.getKey(), e.getValue())
                );
        Assert.assertTrue(AnagramService.checkAnagramMap("BRITNEYSPeARs", "PRESBYTeRIANs"));
        Assert.assertTrue(AnagramService.checkAnagramMap("ERICcLAPTON", "NARCOLEPTIc"));
        Assert.assertTrue(AnagramService.checkAnagramMap("BRITNEYSPEARS", "PRESBYTERIANS"));
        Assert.assertTrue(AnagramService.checkAnagramMap("ERICCLAPTON", "NARCOLEPTIC"));
        Assert.assertFalse(AnagramService.checkAnagramMap("ERICCLAPTON", "ERICcLAPTON"));
        Assert.assertFalse(AnagramService.checkAnagramMap("BRITNEYSPEARS", "BRITNEYSPeARs"));
        Assert.assertFalse(AnagramService.checkAnagramMap("SINKING", "KISSING"));
    }

    @Test
    public void testCharsPow() {
        Assert.assertNotNull("chars is null", chars);
        Assert.assertFalse("chars is empty", chars.isEmpty());
        Map<String, Set<String>> multiMap = new HashMap<>();
        for (char c1 : chars) {
            for (char c2 : chars) {
                if (c1 != c2) {
                    String text1 = new String(new char[]{c1});
                    String text2 = new String(new char[]{c2});
                    if (AnagramService.checkAnagramPow(text1, text2))
                        multiMap.computeIfAbsent(text1, key -> new HashSet<>()).add(text2);
                }
            }
        }
        multiMap.entrySet().stream().filter(e -> e.getValue().size() > 2)
                .forEach(e -> logger.debug("Collisions for {}: {}", e.getKey(), e.getValue())
                );
        Assert.assertTrue(AnagramService.checkAnagramPow("BRITNEYSPeARs", "PRESBYTeRIANs"));
        Assert.assertTrue(AnagramService.checkAnagramPow("ERICcLAPTON", "NARCOLEPTIc"));
        Assert.assertTrue(AnagramService.checkAnagramPow("BRITNEYSPEARS", "PRESBYTERIANS"));
        Assert.assertTrue(AnagramService.checkAnagramPow("ERICCLAPTON", "NARCOLEPTIC"));
        Assert.assertFalse(AnagramService.checkAnagramPow("ERICCLAPTON", "ERICcLAPTON"));
        Assert.assertFalse(AnagramService.checkAnagramPow("BRITNEYSPEARS", "BRITNEYSPeARs"));
    }

    @Test
    public void testCharsList() {
        Assert.assertNotNull("chars is null", chars);
        Assert.assertFalse("chars is empty", chars.isEmpty());
        Map<String, Set<String>> multiMap = new HashMap<>();
        for (char c1 : chars) {
            for (char c2 : chars) {
                if (c1 != c2) {
                    String text1 = new String(new char[]{c1});
                    String text2 = new String(new char[]{c2});
                    if (AnagramService.checkAnagramList(text1, text2))
                        multiMap.computeIfAbsent(text1, key -> new HashSet<>()).add(text2);
                }
            }
        }
        multiMap.entrySet().stream().filter(e -> e.getValue().size() > 2)
                .forEach(e -> logger.debug("Collisions for {}: {}", e.getKey(), e.getValue())
                );
        Assert.assertTrue(AnagramService.checkAnagramList("BRITNEYSPeARs", "PRESBYTeRIANs"));
        Assert.assertTrue(AnagramService.checkAnagramList("ERICcLAPTON", "NARCOLEPTIc"));
        Assert.assertTrue(AnagramService.checkAnagramList("BRITNEYSPEARS", "PRESBYTERIANS"));
        Assert.assertTrue(AnagramService.checkAnagramList("ERICCLAPTON", "NARCOLEPTIC"));
        Assert.assertFalse(AnagramService.checkAnagramList("ERICCLAPTON", "ERICcLAPTON"));
        Assert.assertFalse(AnagramService.checkAnagramList("BRITNEYSPEARS", "BRITNEYSPeARs"));
    }

    //@Test
    public void testLOTR() {
        Set<String> words = new HashSet<>();
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

        Map<String, String> anagrams[] = new Map[5];
        anagrams[0] = new HashMap<>();
        anagrams[1] = new HashMap<>();
        anagrams[2] = new HashMap<>();
        anagrams[3] = new HashMap<>();
        anagrams[4] = new HashMap<>();
        for (String W1 : words) {
            for (String W2 : words) {
                if (W1 != W2) {
                    boolean sortFlag = AnagramService.checkAnagramSort(W1, W2);
                    if (sortFlag)
                        anagrams[0].putIfAbsent(W1, W2);
                    if (AnagramService.checkAnagramBites(W1, W2)) {
                        anagrams[1].putIfAbsent(W1, W2);
                        if (!sortFlag)
                            logger.debug("Error for bites anagram: {}, {}", W1, W2);
                    }
                    if (AnagramService.checkAnagramList(W1, W2)) {
                        anagrams[2].putIfAbsent(W1, W2);
                        if (!sortFlag)
                            logger.debug("Error for list anagram: {}, {}", W1, W2);
                    }
                    if (AnagramService.checkAnagramMap(W1, W2)) {
                        anagrams[3].putIfAbsent(W1, W2);
                        if (!sortFlag)
                            logger.debug("Error for map anagram: {}, {}", W1, W2);
                    }
                    if (AnagramService.checkAnagramPow(W1, W2)) {
                        anagrams[4].putIfAbsent(W1, W2);
                        if (!sortFlag)
                            logger.debug("Error for pow anagram: {}, {}", W1, W2);
                    }
                }
            }
        }
        anagrams[0].entrySet().stream().forEach(e ->
            logger.debug("Sort anagrams for {}: {}", e.getKey(), e.getValue())
        );
        logger.info("count sort anagrams: {}", anagrams[0].size());
        logger.info("count bites anagrams: {}", anagrams[1].size());
        logger.info("count list anagrams: {}", anagrams[2].size());
        logger.info("count map anagrams: {}", anagrams[3].size());
        logger.info("count pow anagrams: {}", anagrams[4].size());
        logger.debug("{} anagrams in {} words", anagrams[0].size(), words.size());

        Assert.assertNotNull("Sort anagrams is null", anagrams[0]);
        Assert.assertNotNull("Bites anagrams is null", anagrams[1]);
        Assert.assertNotNull("List anagrams is null", anagrams[2]);
        Assert.assertNotNull("Map anagrams is null", anagrams[3]);
        Assert.assertNotNull("Pow anagrams is null", anagrams[4]);
        Assert.assertTrue("Size sort not equals size bites", anagrams[0].size() == anagrams[1].size());
        Assert.assertTrue("Size sort not equals size list", anagrams[0].size() == anagrams[2].size());
        Assert.assertTrue("Size sort not equals size map", anagrams[0].size() == anagrams[3].size());
        Assert.assertTrue("Size sort not equals size bites", anagrams[0].size() == anagrams[4].size());
    }
}
