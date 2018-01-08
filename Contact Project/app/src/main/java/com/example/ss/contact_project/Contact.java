package com.example.ss.contact_project;

/**
 * Created by Ezz on 8/12/2015.
 */
public class Contact {
    private int id;
    private String name ;
    private String phone ;
    private String email;
    private double lng;
    private double lat;








    public Contact ()
    {

    }
    public Contact (String name , String phone , String email)
    {
        this.name=name;
        this.phone=phone;
        this.email=email;

    }

    public Contact(String name, String phone, String email, double lng, double lat) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public Contact(int id, String name, String phone, String email, double lng, double lat) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.lng = lng;
        this.lat = lat;
    }

    public Contact (int id , String name , String phone , String email)
    {
        this.id = id ;
        this.name = name;
        this.phone = phone;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
