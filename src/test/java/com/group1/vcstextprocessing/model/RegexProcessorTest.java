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

    @Test
    void testMatchInCollection() {
        List<DataItem> data = List.of(
                new DataItem(1, "test"),
                new DataItem(2, "test fail"),
                new DataItem(3, "test")
        );
        List<DataItem> results = regexProcessor.matchInCollection(data, "test");
        assertEquals(2, results.size(), "Should exactly match 'test' in two items.");
    }

    @Test
    void testMatchWithPositions() {
        List<DataItem> data = List.of(new DataItem(1, "test"));
        List<RegexProcessor.MatchResult> results = regexProcessor.matchWithPositions(data, "test");
        assertEquals(1, results.size(), "Should find one exact match.");
        RegexProcessor.MatchResult matchResult = results.get(0);
        assertEquals("test", matchResult.line());
        assertEquals(1, matchResult.positions().size());
        assertArrayEquals(new int[]{0, 4}, matchResult.positions().get(0), "Position of 'test' should cover the whole string.");
    }

    @Test
    void testSearchAndReplaceInCollection() {
        List<DataItem> data = List.of(
                new DataItem(1, "test data"),
                new DataItem(2, "check test")
        );
        List<DataItem> results = regexProcessor.searchAndReplaceInCollection(data, "test", "exam");
        assertEquals(2, results.size(), "Should replace 'test' with 'exam' in all applicable items.");
        assertEquals("exam data", results.get(0).getData());
        assertEquals("check exam", results.get(1).getData());
    }
}