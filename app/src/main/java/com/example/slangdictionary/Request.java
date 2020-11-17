package com.example.slangdictionary;

public class Request {
    private String Word;
    private String Definition;

    public Request() {
    }

    public Request(String word, String definition) {
        Word = word;
        Definition = definition;
    }
    public String getWord() {
        return Word;
    }

    public String getDefinition() {
        return Definition;
    }
    public void setWord(String word) {
        Word = word;
    }

    public void setDefinition(String definition) {
        Definition = definition;
    }
}
