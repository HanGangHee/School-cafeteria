package com.example.administrator.schoolcafeteria;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-05-30.
 */

public class NewMenu implements Serializable{
    private int price;
    private String name;
    private String Native; // 원산지
    private int Vote;
    private String[] side_dish;
    private int kcal;
    private String ct_name;
    private String picture;
    int menu_num;
    int image;

    public NewMenu(int menu_num,String name,int price,String ct_name,String Native,int Kcal,int Vote, int image){
        this.menu_num = menu_num;
        this.name=name;
        this.price = price;
        this.ct_name = ct_name;
        this.kcal = Kcal;
        this.Vote = Vote;
        this.Native =Native;
        this.image=image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNative() {
        return Native;
    }

    public void setNative(String aNative) {
        Native = aNative;
    }

    public String[] getSide_dish() {
        return side_dish;
    }

    public void setSide_dish(String[] side_dish) {
        this.side_dish = side_dish;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public String getCt_name() {
        return ct_name;
    }

    public void setCt_name(String ct_name) {
        this.ct_name = ct_name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public int getMenu_num() {
        return menu_num;
    }



    public void setMenu_num(int menu_num) {
        this.menu_num = menu_num;
    }


    public void setImage(int image){
        this.image = image;
    }
    public int getImage(){
        return image;
    }

    public void setVote(int Vote){
        this.Vote = Vote;
    }
    public int getVote(){
        return Vote;
    }


    public void changeData(String name, int price, String Native, int Kcal) {
        this.name = name;
        this.price = price;
        this.kcal = Kcal;
        this.Native = Native;
    }

}
