package com.rubick.bechakini;

/**
 * Created by Mehedee Zaman on 1/29/2015.
 */
public class SearchResult {

    public String name;
    public String areaName;
    public String photoUrl;
    public String price;

    public SearchResult(String name, String price, String photoUrl, String areaName) {
        this.name = name;
        this.price = price;
        this.photoUrl = photoUrl;
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return name + " " + areaName ;
    }
}
