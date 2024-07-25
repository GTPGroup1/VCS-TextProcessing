package com.group1.vcstextprocessing.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegexProcessorTest {

    RegexProcessor regexProcessor = new RegexProcessor();

    @Test
    void testSearchInCollection() {
        List<DataItem> data = List.of(
                new DataItem(1, "test data"),
                new DataItem(2, "another test"),
                new DataItem(3, "no match here")
        );
        List<String> results = regexProcessor.searchInCollection(data, "test");
        assertEquals(2, results.size(), "Should find 'test' in two items.");
        assertTrue(results.contains("test"));
    }

    @Test
    void testSearchWithPositions() {
        List<DataItem> data = List.of(new DataItem(1, "testing regex fun"));
        List<RegexProcessor.MatchResult> results = regexProcessor.searchWithPositions(data, "test");
        assertEquals(1, results.size(), "Should find one match.");
        RegexProcessor.MatchResult matchResult = results.get(0);
        assertEquals("testing regex fun", matchResult.line());
        assertEquals(1, matchResult.positions().size());
        assertArrayEquals(new int[]{0, 4}, matchResult.positions().get(0), "Position of 'test' should be correct.");
    }
}