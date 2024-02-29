package org.example.custom;

public class Address {
    public String district; // район
    public String street; // улица
    public int number; // дом
    public Address(String dist, String str, int num) {
        district = dist;
        street = str;
        number = num;
    }

    public Address() {
        district = "";
        street = "";
        number = -1;
    }

    public String toString(String district, String street, int number) {
        return "р-он " + district + ", ул. " + street + ", д. " + number;
    }

    public String toString() {
        return "р-он " + district + ", ул. " + street + ", д. " + number;
    }
}
