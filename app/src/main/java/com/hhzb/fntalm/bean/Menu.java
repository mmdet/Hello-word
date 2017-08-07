package com.hhzb.fntalm.bean;

/**
 * Created by c on 2016-11-29.
 */
public class Menu extends BaseModel{
    private int ID;
    private String name;
    private int bg;
    private int img;

    public Menu(int ID, String name, int bg, int img) {
        this.ID = ID;
        this.name = name;
        this.bg = bg;
        this.img = img;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }
}
