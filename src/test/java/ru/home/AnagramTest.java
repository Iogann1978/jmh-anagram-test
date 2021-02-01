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
public class AnagramTest extends AbstractAnagramTest {
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
    public void testSort() {
        AnagramService service = new SortService();
        Map<String, Set<String>> multiMap = new HashMap<>();
        doTest(chars, service, multiMap);
    }

    @Test
    public void testBits() {
        AnagramService service = new BitsService();
        Map<String, Set<String>> multiMap = new HashMap<>();
        doTest(chars, service, multiMap);
    }

    @Test
    public void testBitsAsync() {
        AnagramService service = new AsyncBitsService();
        Map<String, Set<String>> multiMap = new HashMap<>();
        doTest(chars, service, multiMap);
    }

    @Test
    public void testMap() {
        AnagramService service = new MapService();
        Map<String, Set<String>> multiMap = new HashMap<>();
        doTest(chars, service, multiMap);
    }

    @Test
    public void testPow() {
        AnagramService service = new PowService();
        Map<String, Set<String>> multiMap = new HashMap<>();
        doTest(chars, service, multiMap);
    }

    @Test
    public void testList() {
        AnagramService service = new ListService();
        Map<String, Set<String>> multiMap = new HashMap<>();
        doTest(chars, service, multiMap);
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

        AnagramService sortService = new SortService();
        AnagramService bitsService = new BitsService();
        AnagramService mapService = new MapService();
        AnagramService listService = new ListService();
        AnagramService powService = new PowService();
        AnagramService asyncBitsService = new AsyncBitsService();

        Map<String, String> anagrams[] = new Map[6];
        anagrams[0] = new HashMap<>();
        anagrams[1] = new HashMap<>();
        anagrams[2] = new HashMap<>();
        anagrams[3] = new HashMap<>();
        anagrams[4] = new HashMap<>();
        anagrams[5] = new HashMap<>();
        for (String W1 : words) {
            for (String W2 : words) {
                if (W1 != W2) {
                    boolean sortFlag = sortService.doCheck(W1, W2);
                    if (sortFlag)
                        anagrams[0].putIfAbsent(W1, W2);
                    if (bitsService.doCheck(W1, W2)) {
                        anagrams[1].putIfAbsent(W1, W2);
                        if (!sortFlag)
                            logger.debug("Error for bites anagram: {}, {}", W1, W2);
                    }
                    if (listService.doCheck(W1, W2)) {
                        anagrams[2].putIfAbsent(W1, W2);
                        if (!sortFlag)
                            logger.debug("Error for list anagram: {}, {}", W1, W2);
                    }
                    if (mapService.doCheck(W1, W2)) {
                        anagrams[3].putIfAbsent(W1, W2);
                        if (!sortFlag)
                            logger.debug("Error for map anagram: {}, {}", W1, W2);
                    }
                    if (powService.doCheck(W1, W2)) {
                        anagrams[4].putIfAbsent(W1, W2);
                        if (!sortFlag)
                            logger.debug("Error for pow anagram: {}, {}", W1, W2);
                    }
                    if (asyncBitsService.doCheck(W1, W2)) {
                        anagrams[5].putIfAbsent(W1, W2);
                        if (!sortFlag)
                            logger.debug("Error for asyncBits anagram: {}, {}", W1, W2);
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
        logger.info("count asyncBits anagrams: {}", anagrams[5].size());
        logger.debug("{} anagrams in {} words", anagrams[0].size(), words.size());

        Assert.assertNotNull("Sort anagrams is null", anagrams[0]);
        Assert.assertNotNull("Bites anagrams is null", anagrams[1]);
        Assert.assertNotNull("List anagrams is null", anagrams[2]);
        Assert.assertNotNull("Map anagrams is null", anagrams[3]);
        Assert.assertNotNull("Pow anagrams is null", anagrams[4]);
        Assert.assertNotNull("AsyncBits anagrams is null", anagrams[5]);
        Assert.assertTrue("Size sort not equals size bites", anagrams[0].size() == anagrams[1].size());
        Assert.assertTrue("Size sort not equals size list", anagrams[0].size() == anagrams[2].size());
        Assert.assertTrue("Size sort not equals size map", anagrams[0].size() == anagrams[3].size());
        Assert.assertTrue("Size sort not equals size pow", anagrams[0].size() == anagrams[4].size());
        Assert.assertTrue("Size sort not equals size asyncBits", anagrams[0].size() == anagrams[5].size());
    }
}
