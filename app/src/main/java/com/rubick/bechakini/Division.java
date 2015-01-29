package com.rubick.bechakini;

import java.util.ArrayList;

/**
 * Created by Mehedee Zaman on 1/28/2015.
 */
public class Division {

    public String name;
    public int id;
    public ArrayList<District> districts;

    public Division(String name, int id) {
        this.name = name;
        this.id = id;
        this.districts = new ArrayList<District>();
    }

    public void addDistrict(District d){
        this.districts.add(d);
    }
}
