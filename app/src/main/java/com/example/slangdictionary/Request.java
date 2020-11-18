package com.example.slangdictionary;

public class Request {
    private String Word;
    private String Definition;
    private String User;

    public Request() {
    }

    public Request(String word, String definition, String user) {
        Word = word;
        Definition = definition;
        User = user;
    }

    public String getWord() {
        return Word;
    }

    public String getDefinition() {
        return Definition;
    }

    public String getUser() {
        return User;
    }

    public void setWord(String word) {
        Word = word;
    }

    public void setUser(String user) {
        User = user;
    }

    public void setDefinition(String definition) {
        Definition = definition;
    }
}
