package com.example.slangdictionary;

public class Comment {

    private String Word;
    private String Comment;

    public Comment() {
    }

    public Comment(String word, String comment) {
        Word = word;
        Comment = comment;
    }

    public String getWord() {
        return Word;
    }

    public String getComment() {
        return Comment;
    }

    public void setWord(String word) {
        Word = word;
    }

    public void setComment(String comment) {
        Comment = comment;
    }


}
