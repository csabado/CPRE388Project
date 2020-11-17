package com.example.slangdictionary;

public class Words {

    private String Word;
    private String Definition;
    private String Example;

    public Words() {
    }

    public Words(String word, String definition, String example) {
        Word = word;
        Definition = definition;
        Example = example;
    }

    public String getWord() {
        return Word;
    }

    public String getDefinition() {
        return Definition;
    }

    public String getExample() {
        return Example;
    }
}
