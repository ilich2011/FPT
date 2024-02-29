package org.example.custom;

public class ApartmentInfo {

    public double area; // метраж
    public int roomCount; // количество комнат
    public Address address; // адрес
    public int floorNum; // этаж
    public BuildingTypeEnum.BuildingType buildingType; // вид дома
    public int floorsCount; // количество этажей
    public double price; // стоимость

    public ApartmentInfo(double area, int rooms, String district, String street, int buildingNum, int floorNum, String type, int floorsCount, double price) {
        this.area = area;
        roomCount = rooms;
        address = new Address(district, street, buildingNum);
        this.floorNum = floorNum;
        buildingType = BuildingTypeEnum.BuildingType.fromString(type);
        this.floorsCount = floorsCount;
        this.price = price;
    }

    public ApartmentInfo() {
        area = -1;
        roomCount = -1;
        address = null;
        floorNum = 0;
        buildingType = null;
        floorsCount = -1;
        price = -1;
    }

    public String toString() {
        return roomCount + "-комнатная квартира площадью " + area + "м^2 и стоимостью " + price + " руб. на " + floorNum + " этаже,\n\t" +
                buildingType + " " + floorsCount + "-этажный дом по адресу: " + address;
    }

}
