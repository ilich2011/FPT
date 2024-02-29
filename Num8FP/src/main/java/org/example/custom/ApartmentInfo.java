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

    public ApartmentInfo(double area, int rooms, String district, String street, int buildingNum, int floorNum, BuildingTypeEnum.BuildingType type, int floorsCount, double price) {
        this.area = area;
        roomCount = rooms;
        address = new Address(district, street, buildingNum);
        this.floorNum = floorNum;
        buildingType = type;
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

    public String toString(int roomCount, double area, double price, int floorNum, BuildingTypeEnum.BuildingType buildingType, int floorsCount, Address address) {
        return roomCount + "-комнатная квартира площадью " + area + "м^2 и стоимостью " + price + " руб. на " + floorNum + " этаже,\n\t" +
                buildingType + " " + floorsCount + "-этажный дом по адресу: " + address;
    }

    public String toString() {
        return roomCount + "-комнатная квартира площадью " + area + "м^2 и стоимостью " + price + " руб. на " + floorNum + " этаже,\n\t" +
                buildingType + " " + floorsCount + "-этажный дом по адресу: " + address;
    }

    public ApartmentInfo copy(ApartmentInfo info) {
        return new ApartmentInfo(info.area, info.roomCount, info.address.district, info.address.street, info.address.number, info.floorNum, info.buildingType, info.floorsCount, info.price);
    }

    public ApartmentInfo setBuildingType(ApartmentInfo info, BuildingTypeEnum.BuildingType type) {
        ApartmentInfo copy = info.copy(info);
        copy.buildingType = type;
        return copy;
    }

    public ApartmentInfo setArea(ApartmentInfo info, double area) {
        ApartmentInfo copy = info.copy(info);
        copy.area = area;
        return copy;
    }

    public ApartmentInfo setPrice(ApartmentInfo info, double price) {
        ApartmentInfo copy = info.copy(info);
        copy.price = price;
        return copy;
    }

    public ApartmentInfo setFloorNum(ApartmentInfo info, int floorNum) {
        ApartmentInfo copy = info.copy(info);
        copy.floorNum = floorNum;
        return copy;
    }

    public ApartmentInfo setFloorsCount(ApartmentInfo info, int count) {
        ApartmentInfo copy = info.copy(info);
        copy.floorsCount = count;
        return copy;
    }

    public ApartmentInfo setAddress(ApartmentInfo info, Address address) {
        ApartmentInfo copy = info.copy(info);
        copy.address = address;
        return copy;
    }

    public ApartmentInfo setRoomCount(ApartmentInfo info, int i) {
        ApartmentInfo copy = info.copy(info);
        copy.roomCount = i;
        return copy;
    }
}
