package com.rubick.bechakini;

import java.util.ArrayList;

/**
 * Created by Mehedee Zaman on 1/28/2015.
 */
public class Category {

    public String name;
    public ArrayList<SubCategory> subCategories;

    public Category(String name) {
        this.name = name;
        subCategories = new ArrayList<SubCategory>();
    }

    public void addSubCategory(SubCategory sub){
        this.subCategories.add(sub);
    }
}
