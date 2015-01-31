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
    public static final String ADLISTFRAGMENT_ARG_CATEGORY_ID = "catid";
    public static final String ADLISTFRAGMENT_ARG_SUBCATEGORY_ID = "subcatid";

    public static ArrayList<Category> adCategories;
    public static ArrayList<Area> areas;

    public static String[] areaStringArray;
    public static String[] categoryStringArray;


    public static ArrayList<SearchResult> demoresults;

    public static int favCount;


    public static void init(){


        favCount = 0;

        adCategories = new ArrayList<Category>();







        areas = new ArrayList<Area>();




        String car_url = "http://www.mocky.io/v2/54cb577a96d6b22d07431f1d";
        String motorcycle_url = "http://www.mocky.io/v2/54cb58b896d6b25107431f1e";
        String bicycle_url = "http://www.mocky.io/v2/54cb58fa96d6b25107431f1f";
        String mobile_url= "http://www.mocky.io/v2/54cb593996d6b26907431f20";
        String laptop_url= "http://www.mocky.io/v2/54cb598196d6b27107431f21";
        String television_url= "http://www.mocky.io/v2/54cb59f796d6b27d07431f22";
        String novel_url= "http://www.mocky.io/v2/54cb5a6296d6b28207431f23";
        String academic_url= "http://www.mocky.io/v2/54cb5aef96d6b29b07431f24";
        String comic_url= "http://www.mocky.io/v2/54cb5b4e96d6b2a907431f25";
        String cricket_url= "http://www.mocky.io/v2/54cb5b8d96d6b2ac07431f26";
        String football_url= "http://www.mocky.io/v2/54cb5bc596d6b2b707431f28";
        String table_tennis_url= "http://www.mocky.io/v2/54cb5c5096d6b2c807431f2a";
        String bedroom_url= "http://www.mocky.io/v2/54cb5cae96d6b2d007431f2b";
        String dinning_url= "http://www.mocky.io/v2/54cb5cd996d6b2d007431f2c";
        String kitchen_url= "http://www.mocky.io/v2/54cb5d1496d6b2dd07431f2d";
        String house_rent_url= "http://www.mocky.io/v2/54cb5d4196d6b2e207431f2e";
        String house_for_sale_url= "http://www.mocky.io/v2/54cb5e4a96d6b20908431f32";
        String office_rent_url= "http://www.mocky.io/v2/54cb5d7196d6b2dc07431f2f";




        SubCategory car = new SubCategory("Car",car_url,new String[]{"title", "description", "price", "seller_name", "age", "mileage", "image_url"});
        SubCategory motorCycle = new SubCategory("Motor Cycle",motorcycle_url,new String[]{"title", "description", "price", "seller_name", "age", "mileage", "image_url"});
        SubCategory biCycle = new SubCategory("Bi Cycle",bicycle_url,new String[]{"title", "description", "price", "seller_name", "age", "extragears", "image_url"});

        Category vehicle = new Category("Vehicle");

        vehicle.addSubCategory(car);
        vehicle.addSubCategory(motorCycle);
        vehicle.addSubCategory(biCycle);

        adCategories.add(vehicle);




        SubCategory mobile = new SubCategory("Mobile",mobile_url,new String[]{"title", "description", "price", "seller_name", "age", "warranty", "image_url"});
        SubCategory laptop = new SubCategory("Laptop",laptop_url,new String[]{"title", "description", "price", "seller_name", "age", "warranty", "image_url"});
        SubCategory television = new SubCategory("Television",television_url,new String[]{"title", "description", "price", "seller_name", "age", "type", "image_url"});

        Category elec = new Category("Electronics");

        elec.addSubCategory(mobile);
        elec.addSubCategory(laptop);
        elec.addSubCategory(television);

        adCategories.add(elec);


        SubCategory novel = new SubCategory("Novel",novel_url,new String[]{"title", "description", "price", "seller_name", "writer", "publisher", "image_url"});
        SubCategory academic = new SubCategory("Academic",academic_url,new String[]{"title", "description", "price", "seller_name", "writer", "edition", "image_url"});
        SubCategory comic = new SubCategory("Comic",comic_url,new String[]{"title", "description", "price", "seller_name", "writer", "publisher", "image_url"});

        Category books = new Category("Books");

        books.addSubCategory(novel);
        books.addSubCategory(academic);
        books.addSubCategory(comic);

        adCategories.add(books);



        SubCategory cricket = new SubCategory("Cricket",cricket_url,new String[]{"title", "description", "price", "seller_name", "age", "manufacturer", "image_url"});
        SubCategory football = new SubCategory("Football",football_url,new String[]{"title", "description", "price", "seller_name", "age", "manufacturer", "image_url"});
        SubCategory tableTennis = new SubCategory("Table Tennis",table_tennis_url,new String[]{"title", "description", "price", "seller_name", "age", "manufacturer", "image_url"});

        Category sports = new Category("Sports");

        sports.addSubCategory(cricket);
        sports.addSubCategory(football);
        sports.addSubCategory(tableTennis);

        adCategories.add(sports);



        SubCategory bedroomFurniture = new SubCategory("Bedroom",bedroom_url,new String[]{"title", "description", "price", "seller_name", "age", "type", "image_url"});
        SubCategory dinningFurniture = new SubCategory("Dinning",dinning_url,new String[]{"title", "description", "price", "seller_name", "age", "type", "image_url"});
        SubCategory kitchenFurniture = new SubCategory("Kitchen",kitchen_url,new String[]{"title", "description", "price", "seller_name", "age", "type", "image_url"});

        Category furniture = new Category("Furniture");

        furniture.addSubCategory(bedroomFurniture);
        furniture.addSubCategory(dinningFurniture);
        furniture.addSubCategory(kitchenFurniture);

        adCategories.add(furniture);

        SubCategory houseRent = new SubCategory("House Rent",house_rent_url,new String[]{"title", "description", "rent", "owner", "builder", "location", "image_url"});
        SubCategory officeRent = new SubCategory("Office Rent",office_rent_url,new String[]{"title", "description", "rent", "owner", "builder", "location", "image_url"});
        SubCategory houseForSale = new SubCategory("House For Sale",house_for_sale_url,new String[]{"title", "description", "price", "owner", "builder", "location", "image_url"});

        Category housing = new Category("Housing");

        housing.addSubCategory(houseRent);
        housing.addSubCategory(officeRent);
        housing.addSubCategory(houseForSale);

        adCategories.add(housing);





        areas.add(new Area("North South University",23.81641125132503,90.42620105370487));
        areas.add(new Area("North Badda",23.783033176052193,90.42306300582892));
        areas.add(new Area("Middle Badda",23.779380927727992,90.4247152465821));
        areas.add(new Area("Merul Badda",23.77706385678881,90.42302009048468));
        areas.add(new Area("DIT Project",23.775271622243793,90.42739391821895));
        areas.add(new Area("Shahzadpur",23.79196700694272,90.42553063812262));
        areas.add(new Area("Kuril",23.8227836232271,90.42327396166988));
        areas.add(new Area("Nurerchala",23.79233474373153,90.43818114953616));
        areas.add(new Area("Aftabnagar",23.770426594596124,90.42849179687506));
        areas.add(new Area("Kalabagan" ,23.750369979995888,90.38286924857174));
        areas.add(new Area("Dhanmondi Lake" ,23.74652040512678,90.37874937552486));
        areas.add(new Area("Pilkhana" ,23.735599563347453,90.37595987814937));
        areas.add(new Area("Kathalbagan" ,23.747345323607174,90.3877615978149));
        areas.add(new Area("Hatirpool" ,23.743692073527203,90.38836241263424));
        areas.add(new Area("New Market" ,23.73438171490524,90.38325548666988));
        areas.add(new Area("Sheikh Kamal Road" ,23.751744800587556,90.37454367178951));
        areas.add(new Area("BGB" ,23.73469599946604,90.37593842047725));
        areas.add(new Area("Shahajahanpur" ,23.74654535993719,90.42372819366462));
        areas.add(new Area("Shantibagh" ,23.74652571895979,90.41763421478278));
        areas.add(new Area("Shahidbagh" ,23.74357953899736,90.41896459045417));
        areas.add(new Area("Malibagh" ,23.748057706201823,90.41415807189948));
        areas.add(new Area("Rajarbagh" ,23.742204632208292,90.41523095550544));
        areas.add(new Area("Shantinagar" ,23.739179786191652,90.41420098724372));
        areas.add(new Area("Bangabandhu National Stadium" ,23.728474409964118,90.41312810363776));
        areas.add(new Area("Notre Dame College" ,23.730556625421993,90.42033788146979));
        areas.add(new Area("Kamalapur" ,23.734681671018667,90.42565938415534));
        areas.add(new Area("Nayapaltan" ,23.73548702182699,90.4137289184571));
        areas.add(new Area("Purana Paltan" ,23.73336559922463,90.41063901367194));
        areas.add(new Area("Sector 1",23.859647801322655,90.39999600830085));
        areas.add(new Area("Sector 3",23.866398239900892,90.39712068023688));
        areas.add(new Area("Sector 4",23.863415531354924,90.40424462738044));
        areas.add(new Area("Sector 5",23.86573106106144,90.39042588653571));
        areas.add(new Area("Sector 6",23.872284963060153,90.40411588134772));
        areas.add(new Area("Sector 7",23.872363450895108,90.39694901885993));
        areas.add(new Area("Sector 8",23.878328387047265,90.4042017120362));
        areas.add(new Area("Sector 9",23.87828914494357,90.39712068023688));
        areas.add(new Area("Sector 10",23.884999371562753,90.38948174896247));
        areas.add(new Area("Sector 11",23.878367629137312,90.38982507171637));
        areas.add(new Area("Sector 12",23.872402694795184,90.38046952667243));
        areas.add(new Area("Sector 13",23.87236345089464,90.38789388122565));
        areas.add(new Area("Sector 14",23.86867447125747,90.38725015106208));
        areas.add(new Area("Bakshi Bazar" ,23.720714917946754,90.39239999237067));
        areas.add(new Area("Dhakeswari" ,23.723298192519024,90.3901898521424));
        areas.add(new Area("Dhaka Board",23.722306142558157,90.39206739845282));
        areas.add(new Area("Eden College",23.72819939788564,90.3875076431275));
        areas.add(new Area("Home Economics College" ,23.731195033872684,90.3863167423249));
        areas.add(new Area("Azimpur Graveyard" ,23.72973160063184,90.38214322509772));
        areas.add(new Area("Hussaini Dalan" ,23.722571344774316,90.39746400299079));
        areas.add(new Area("Bangshal" ,23.717149325634118,90.4060041564942));
        areas.add(new Area("SSMC" ,23.710951052990975,90.4010152477265));
        areas.add(new Area("Lalbagh Fort" ,23.71900580322248,90.38854834022528));
        areas.add(new Area("Babu Bazar" ,23.714673981079386,90.39488908233649));
        areas.add(new Area("Moghbazar",23.752584819592798,90.408128466034));
        areas.add(new Area("Paribagh",23.745474922423618,90.39304372253424));
        areas.add(new Area("Shiddheswari",23.746044514124737,90.41010257186896));
        areas.add(new Area("Madhubagh",23.760165663457524,90.41164752426154));
        areas.add(new Area("Nayatola",23.757023398328528,90.40928718032843));
        areas.add(new Area("Shahbagh",23.740721680766967,90.39420243682868));
        areas.add(new Area("Kakrail",23.737303953550136,90.40426608505256));
        areas.add(new Area("Shegunbagicha",23.732511127592026,90.40654059829718));
        areas.add(new Area("BUET",23.727069877926002,90.39212104263312));
        areas.add(new Area("Dhaka University Campus",23.732481662954015,90.39477106513984));
        areas.add(new Area("Bangla Bazar",23.723887525564436,90.40590759696967));
        areas.add(new Area("Ullan",23.767422042090143,90.41894313278205));
        areas.add(new Area("Rampura",23.762198275809,90.42053100051886));
        areas.add(new Area("Taltola",23.75430331383102,90.42192574920661));
        areas.add(new Area("Tilpapara",23.748175550638678,90.42844888153083));
        areas.add(new Area("Basabo",23.745386537287793,90.43655988159186));
        areas.add(new Area("Goran",23.75292852025965,90.43398496093756));
        areas.add(new Area("Madartek",23.745347254984722,90.44029351654059));
        areas.add(new Area("Nandi Para",23.75611016330644,90.44688102188117));
        areas.add(new Area("Tromohoni",23.762276830242072,90.4627167839051));
        areas.add(new Area("Banani",23.78440765154377,90.4080211776734));
        areas.add(new Area("Mohakhali",23.778615120718108,90.40538188400275));
        areas.add(new Area("Niketon",23.775002021669135,90.41169043960578));
        areas.add(new Area("Karail",23.786233717927868,90.41121837081916));
        areas.add(new Area("Baridhara",23.8007627942842,90.42010184707648));
        areas.add(new Area("Kalachandpur",23.810735868329594,90.41658278884894));
        areas.add(new Area("Embassy",23.79650240915437,90.42263385238654));
        areas.add(new Area("Tejkunipara",23.7621589985811,90.39224978866584));
        areas.add(new Area("Ajantapara",23.776455127925065,90.39551135482795));
        areas.add(new Area("West Nakhalpara",23.771467371131656,90.39495345535285));
        areas.add(new Area("East Nakhalpara",23.771192449841323,90.39731379928595));
        areas.add(new Area("Farngate",23.757092136199137,90.38709994735724));
        areas.add(new Area("Kawran Bazar",23.752496439285522,90.39418097915656));
        areas.add(new Area("Ser-e-bangla Nagar",23.77319543450944,90.37731524887091));
        areas.add(new Area("Jatiyo Sangsad Bhaban",23.762316107440657,90.37710067214972));
        areas.add(new Area("Begun Bari",23.76451561154839,90.40370818557746));
        areas.add(new Area("Kunipara",23.764319228766574,90.40729161682135));
        areas.add(new Area("Raja Bazar",23.75414619530773,90.38544770660407));
        areas.add(new Area("National Zoo",23.81395535861867,90.34227487030036));
        areas.add(new Area("Nobaberbag",23.808890514746093,90.34171697082526));
        areas.add(new Area("Harirampur",23.793105780839745,90.34133073272712));
        areas.add(new Area("Anandanagar",23.78430947521516,90.35004254760749));
        areas.add(new Area("Kallyanpur",23.783170624269196,90.35931226196296));
        areas.add(new Area("Paikpara",23.785409045972866,90.36476251068122));
        areas.add(new Area("Sewrapara",23.792084811619787,90.37351724090583));
        areas.add(new Area("Kazipara",23.79899583124227,90.36931153717047));
        areas.add(new Area("Monipur",23.80095912203803,90.36656495513922));
        areas.add(new Area("Ahmed Nagar",23.795618901669716,90.36167260589606));
        areas.add(new Area("Mirpur - I",23.79660057629223,90.35347577514655));
        areas.add(new Area("Rayerbazar",23.744483041290675,90.36175843658454));
        areas.add(new Area("Shankar",23.749746799470955,90.35991307678229));
        areas.add(new Area("Shyamoli",23.7712709988459,90.3640758651734));
        areas.add(new Area("Adabar",23.77720131131713,90.3571235794068));
        areas.add(new Area("Geneva Camp",23.769582184907573,90.36532041015631));
        areas.add(new Area("Zafrabad",23.75261427970087,90.36128636779792));
        areas.add(new Area("Ramchandrapur",23.759370284783596,90.34060117187506));
        areas.add(new Area("Lalmatia",23.756385116471638,90.36918279113776));
        areas.add(new Area("Balughat",23.830326408997333,90.3895246643067));
        areas.add(new Area("Barontek",23.83103302654087,90.38699265899665));
        areas.add(new Area("Manikdi",23.82698955192753,90.39179917755133));
        areas.add(new Area("CMH",23.816153989008832,90.39772149505622));
        areas.add(new Area("Damalkot",23.80099838753158,90.39012547912604));
        areas.add(new Area("Bhasantek",23.809047567117673,90.38703557434089));
        areas.add(new Area("Baigertek",23.83707837484111,90.3836881774903));
        areas.add(new Area("Airport",23.843201946837418,90.4056608337403));
        areas.add(new Area("Zia colony",23.819451864800985,90.40561791839606));
        areas.add(new Area("Nikunja - I",23.825419236553877,90.41883584442145));
        areas.add(new Area("Nikunja - II",23.83283880944561,90.4181062835694));


        areaStringArray = new String[areas.size()];

        for(int i = 0; i<areas.size(); ++i){
            areaStringArray[i] = areas.get(i).name;
        }

        categoryStringArray = new String[adCategories.size()];

        for(int i = 0; i<adCategories.size(); ++i){
            categoryStringArray[i] = adCategories.get(i).name;
        }






    }




}
