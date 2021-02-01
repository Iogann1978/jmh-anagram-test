package ru.home;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class AbstractAnagramTest {
    private static final Logger logger = LoggerFactory.getLogger(AbstractAnagramTest.class);

    protected void doTest(List<Character> chars, AnagramService service, Map<String, Set<String>> multiMap) {
        Assert.assertNotNull("chars is null", chars);
        Assert.assertFalse("chars is empty", chars.isEmpty());
        for (char c1 : chars) {
            for (char c2 : chars) {
                if (c1 != c2) {
                    String text1 = new String(new char[]{c1});
                    String text2 = new String(new char[]{c2});
                    if (service.doCheck(text1, text2))
                        multiMap.computeIfAbsent(text1, key -> new HashSet<>()).add(text2);
                }
            }
        }
        multiMap.entrySet().stream().filter(e -> e.getValue().size() > 2)
                .forEach(e -> logger.debug("Collisions for {}: {}", e.getKey(), e.getValue())
                );
        Assert.assertTrue(service.doCheck("BRITNEYSPeARs", "PRESBYTeRIANs"));
        Assert.assertTrue(service.doCheck("ERICcLAPTON", "NARCOLEPTIc"));
        Assert.assertTrue(service.doCheck("BRITNEYSPEARS", "PRESBYTERIANS"));
        Assert.assertTrue(service.doCheck("ERICCLAPTON", "NARCOLEPTIC"));
        Assert.assertFalse(service.doCheck("ERICCLAPTON", "ERICcLAPTON"));
        Assert.assertFalse(service.doCheck("BRITNEYSPEARS", "BRITNEYSPeARs"));
    }
}
