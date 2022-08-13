package com.dudu.dictation;

public class Dictation {
    private String name;
    private int imageId;
    public Dictation(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
