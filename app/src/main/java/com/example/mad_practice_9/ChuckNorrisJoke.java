package com.example.mad_practice_9;

public class ChuckNorrisJoke {
    String id = "";
    String categories = "";
    String iconUrl = "";
    String createdAt = "";
    String updatedAt = "";
    String url = "";
    String value = "";

    @Override
    public String toString() {
        String formatString = "Внимание ШУТКА!\n%s\n\nid = %s\ncategories = %s\nicon url %s" +
                "\nurl = %s\ncreated at %s\nupdated at %s";
        return String.format(formatString, value, id, categories, iconUrl, url, createdAt, updatedAt);
    }
}
