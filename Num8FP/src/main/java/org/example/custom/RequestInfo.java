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

    public RequestInfo(int rooms, String dist, BuildingTypeEnum.BuildingType type) {
        roomCount = rooms;
        district = dist;
        buildingType = type;
    }

    public RequestInfo() {
        roomCount = -1;
        district = "";
        buildingType = null;
    }

    public RequestInfo setDistrict(RequestInfo info, String district) {
        RequestInfo copy = info.copy(info);
        copy.district = district;
        return copy;
    }

    public RequestInfo setBuildingType(RequestInfo info, BuildingTypeEnum.BuildingType type) {
        RequestInfo copy = info.copy(info);
        copy.buildingType = type;
        return copy;
    }

    public RequestInfo setRoomCount(RequestInfo info, int count) {
        RequestInfo copy = info.copy(info);
        copy.roomCount = count;
        return copy;
    }

    public String toString(int roomCount, String district, BuildingTypeEnum.BuildingType buildingType) {
        return "Заявка: " + buildingType + " дом в р-не " + district + ", квартира на " + roomCount + " комнаты";
    }

    public String toString() {
        return "Заявка: " + buildingType + " дом в р-не " + district + ", квартира на " + roomCount + " комнаты";
    }

    public RequestInfo copy(RequestInfo info) {
        return new RequestInfo(info.roomCount, info.district, info.buildingType);
    }

}
