package com.example.tdm_android.models;

import java.util.Arrays;

public class Character {

    private String name;
    private String city;
    private String gender;
    private String culture;
    private String born;
    private String died;
    private String[] titles;
    private String[] aliases;
    private String father;
    private String mother;
    private String spouse;
    private String[] allegiances;
    private String[] books;
    private String[] povBooks;
    private String[] tvSeries;
    private String[] playedBy;

    public Character() {}

    public Character(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Character(
            String name, String city, String gender, String culture, String born, String died,
            String[] titles, String[] aliases, String father, String mother, String spouse, String[] allegiances,
            String[] books, String[] povBooks, String[] tvSeries, String[] playedBy
    ) {
        this.name = name;
        this.city = city;
        this.gender = gender;
        this.culture = culture;
        this.born = born;
        this.died = died;
        this.titles = titles;
        this.aliases = aliases;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
        this.allegiances = allegiances;
        this.books = books;
        this.povBooks = povBooks;
        this.tvSeries = tvSeries;
        this.playedBy = playedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getDied() {
        return died;
    }

    public void setDied(String died) {
        this.died = died;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String[] getAllegiances() {
        return allegiances;
    }

    public void setAllegiances(String[] allegiances) {
        this.allegiances = allegiances;
    }

    public String[] getBooks() {
        return books;
    }

    public void setBooks(String[] books) {
        this.books = books;
    }

    public String[] getPovBooks() {
        return povBooks;
    }

    public void setPovBooks(String[] povBooks) {
        this.povBooks = povBooks;
    }

    public String[] getTvSeries() {
        return tvSeries;
    }

    public void setTvSeries(String[] tvSeries) {
        this.tvSeries = tvSeries;
    }

    public String[] getPlayedBy() {
        return playedBy;
    }

    public void setPlayedBy(String[] playedBy) {
        this.playedBy = playedBy;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", gender='" + gender + '\'' +
                ", culture='" + culture + '\'' +
                ", born='" + born + '\'' +
                ", died='" + died + '\'' +
                ", titles=" + Arrays.toString(titles) +
                ", aliases=" + Arrays.toString(aliases) +
                ", father='" + father + '\'' +
                ", mother='" + mother + '\'' +
                ", spouse='" + spouse + '\'' +
                ", allegiances=" + Arrays.toString(allegiances) +
                ", books=" + Arrays.toString(books) +
                ", povBooks=" + Arrays.toString(povBooks) +
                ", tvSeries=" + Arrays.toString(tvSeries) +
                ", playedBy=" + Arrays.toString(playedBy) +
                '}';
    }
}