package com.rubick.bechakini;

import java.util.ArrayList;

/**
 * Created by Mehedee Zaman on 1/28/2015.
 */
public final class HardConstants {

    public static ArrayList<Division> divisions;
    public static ArrayList<Category> adCategories;

    public static void init(){


        adCategories = new ArrayList<Category>();

        adCategories.add(new Category("Electronics",1));
        adCategories.add(new Category("Home Appliances",1));
        adCategories.add(new Category("Vehicles",1));
        adCategories.add(new Category("Personal",1));
        adCategories.add(new Category("Sport",1));
        adCategories.add(new Category("Education",1));
        adCategories.add(new Category("Agriculture",1));

        District dhaka = new District("Dhaka", 1);
        dhaka.addArea(new Area("Basabo", 1));
        dhaka.addArea(new Area("Khilgaon", 2));
        dhaka.addArea(new Area("Motijheel", 3));
        dhaka.addArea(new Area("Malibagh", 4));
        dhaka.addArea(new Area("Kamalapur", 5));
        dhaka.addArea(new Area("Shahbagh", 6));
        dhaka.addArea(new Area("BUET Campus", 7));
        dhaka.addArea(new Area("DU Campus", 8));

        District gazipur = new District("Gazipur",2);

        gazipur.addArea(new Area("abc",1));
        gazipur.addArea(new Area("ere",2));
        gazipur.addArea(new Area("acsdcbc",3));
        gazipur.addArea(new Area("absc",4));
        gazipur.addArea(new Area("reter",5));

        Division dhakaDivision = new Division("Dhaka",1);

        dhakaDivision.addDistrict(dhaka);
        dhakaDivision.addDistrict(gazipur);


        divisions.add(dhakaDivision);




    }

}
