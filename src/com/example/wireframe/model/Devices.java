package com.example.wireframe.model;

/**
 * Created by likaiwen on 15/5/20.
 */
public class Devices {
    private int id;
    private String name;
    private int image;

    public Devices() {

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
