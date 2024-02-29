package org.example.custom;

public class RequestInfo {
    public int roomCount; // количество комнат
    public String district; // район
    public BuildingTypeEnum.BuildingType buildingType; // вид дома
    public RequestInfo(int rooms, String dist, String type) {
        roomCount = rooms;
        district = dist;
        buildingType = BuildingTypeEnum.BuildingType.fromString(type);
    }

    public RequestInfo() {
        roomCount = -1;
        district = "";
        buildingType = null;
    }

    public String toString() {
        return "Заявка: " + buildingType + " дом в р-не " + district + ", квартира на " + roomCount + " комнаты";
    }

}
