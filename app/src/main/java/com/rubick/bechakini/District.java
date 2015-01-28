package com.rubick.bechakini;

import java.util.ArrayList;

/**
 * Created by Mehedee Zaman on 1/28/2015.
 */
public class District {

    public String name;
    public int id;
    public ArrayList<Area> areas;

    public District(String name, int id) {
        this.name = name;
        this.id = id;
        areas = new ArrayList<Area>();
    }

    public void addArea(Area a){
        this.areas.add(a);
    }


}
