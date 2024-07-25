package com.group1.vcstextprocessing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexProcessor {
    // This method is for searching part of the data that matches the regex pattern
    public List<String> searchInCollection(List<DataItem> data, String regexPattern) {
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexPattern);

        for (DataItem item : data) {
            Matcher matcher = pattern.matcher(item.getData());
            while (matcher.find()) {
                matches.add(matcher.group());
            }
        }
        return matches;
    }

    // for coloring purpose we use this instead of searchInCollection
    public List<MatchResult> searchWithPositions(List<DataItem> data, String regexPattern) {
        List<MatchResult> matchResults = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexPattern);

        for (DataItem item : data) {
            Matcher matcher = pattern.matcher(item.getData());
            List<int[]> positions = new ArrayList<>();

            while (matcher.find()) {
                positions.add(new int[]{matcher.start(), matcher.end()});
            }

            if (!positions.isEmpty()) {
                matchResults.add(new MatchResult(item.getData(), positions));
            }
        }
        return matchResults;
    }
    // using Java's record class
    public record MatchResult(String line, List<int[]> positions) {
    }

    // This method is for matching the entire string with the regex pattern
    public List<DataItem> matchInCollection(List<DataItem> data, String regexPattern) {
        List<DataItem> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexPattern);

        for (DataItem item : data) {
            Matcher matcher = pattern.matcher(item.getData());
            if (matcher.matches()) {
                matches.add(item);
            }
        }
        return matches;
    }


    // for coloring purpose we use this instead of matchInCollection
    public List<MatchResult> matchWithPositions(List<DataItem> data, String regexPattern) {
        List<MatchResult> matchResults = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexPattern);

        for (DataItem item : data) {
            Matcher matcher = pattern.matcher(item.getData());
            List<int[]> positions = new ArrayList<>();

            if (matcher.matches()) {
                positions.add(new int[]{0, item.getData().length()});
                matchResults.add(new MatchResult(item.getData(), positions));
            }
        }
        return matchResults;
    }

    // This method is for search and replace in the collection data
    public List<DataItem> searchAndReplaceInCollection(List<DataItem> data, String regexPattern, String replacement) {
        List<DataItem> modifiedData = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexPattern);

        for (DataItem item : data) {
            Matcher matcher = pattern.matcher(item.getData());
            modifiedData.add(new DataItem(item.getId(), matcher.replaceAll(replacement)));
        }
        return modifiedData;
    }
}
