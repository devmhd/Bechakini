package com.rubick.bechakini;

/**
 * Created by Mehedee Zaman on 1/29/2015.
 */
public class SearchResult {

    public String name;
    public String areaName;
    public String districtName;
    public String photoUrl;
    public int price;

    public SearchResult(String name, int price, String photoUrl, String districtName, String areaName) {
        this.name = name;
        this.price = price;
        this.photoUrl = photoUrl;
        this.districtName = districtName;
        this.areaName = areaName;
    }
}
