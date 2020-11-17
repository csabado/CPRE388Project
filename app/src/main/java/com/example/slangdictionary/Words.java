package com.example.slangdictionary;

public class Words {

    private String Word;
    private String Definition;
    private String Example;
    private String Audio;

    public Words() {
    }

    public Words(String word, String definition, String example, String audio) {
        Word = word;
        Definition = definition;
        Example = example;
        Audio = audio;
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

    public String getAudio(){
        return Audio;
    }
}
