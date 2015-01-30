package com.rubick.bechakini;

import java.util.ArrayList;

/**
 * Created by Mehedee Zaman on 1/28/2015.
 */
public final class HardConstants {


    public static final int MIN_PHONE_LENGTH = 7;
    public static final int MAX_PHONE_LENGTH = 14;


    public static final String ADLISTFRAGMENT_ARG_REQUESTURL = "requestUrl";
    public static final String ADLISTFRAGMENT_ARG_MAXLOADCOUNT = "maxLoadCount";

    public static ArrayList<Category> adCategories;
    public static ArrayList<Area> areas;


    public static ArrayList<SearchResult> demoresults;

    public static void init(){


        adCategories = new ArrayList<Category>();

        Category electronics = new Category("Electronics");

        electronics.addSubCategory(new SubCategory("Mobile", "mocky.io", new String[]{"field1", "field2", "Producer","Brand","Operating System"}));
        electronics.addSubCategory(new SubCategory("Mobile", "mocky.io", new String[]{"field1", "field2", "Producer","Brand","Operating System"}));


        adCategories.add(electronics);








        demoresults = new ArrayList<SearchResult>();

        demoresults.add(new SearchResult("Macbook Air", 40000, "", "Dhaka", "Motijheel"));
        demoresults.add(new SearchResult("Mac Air", 40000, "", "Dhaka", "Motijheel"));
        demoresults.add(new SearchResult("Lenovo", 40000, "", "Dhaka", "Motijheel"));
        demoresults.add(new SearchResult("Macbook Air", 40000, "", "Dhaka", "Motijheel"));
        demoresults.add(new SearchResult("Macbook Air", 40000, "", "Dhaka", "Motijheel"));
        demoresults.add(new SearchResult("Macbook Air", 40000, "", "Dhaka", "Motijheel"));
        demoresults.add(new SearchResult("Mac Air", 40000, "", "Dhaka", "Motijheel"));
        demoresults.add(new SearchResult("Lenovo", 40000, "", "Dhaka", "Motijheel"));



        areas = new





    }




}
