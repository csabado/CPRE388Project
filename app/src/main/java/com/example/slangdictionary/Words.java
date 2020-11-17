package com.example.slangdictionary;

public class Words {

    private String Word;
    private String Definition;
    private String Example;
    private String Audio;
    private String Image;

    public Words() {
    }

    public Words(String word, String definition, String example, String audio, String image) {
        Word = word;
        Definition = definition;
        Example = example;
        Audio = audio;
        Image = image;
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

    public String getImage(){
        return Image;
    }
}
