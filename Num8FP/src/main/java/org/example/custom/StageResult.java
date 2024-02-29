package org.example.custom;

public class StageResult {

    public int stage = 0;
    public ApartmentInfo newOrSelectedApartment = null;
    public RequestInfo newOrSelectedRequest = null;
    public String districtToSearch = "";
    public Requests requests = null;
    public Apartments apartments = null;


    public StageResult copy(StageResult res){
        StageResult resCopy = new StageResult();
        resCopy.stage = res.stage;
        resCopy.newOrSelectedApartment = res.newOrSelectedApartment;
        resCopy.newOrSelectedRequest = res.newOrSelectedRequest;
        resCopy.districtToSearch = res.districtToSearch;
        resCopy.requests = res.requests;
        resCopy.apartments = res.apartments;
        return resCopy;
    }

    public StageResult setNewOrSelectedRequestDistrict(StageResult res, String dist) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedRequest = resCopy.newOrSelectedRequest.setDistrict(resCopy.newOrSelectedRequest, dist);
        return resCopy;
    }

    public StageResult setNewOrSelectedRequestBuildingType(StageResult res, BuildingTypeEnum.BuildingType type) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedRequest = resCopy.newOrSelectedRequest.setBuildingType(resCopy.newOrSelectedRequest, type);
        return resCopy;
    }

    public StageResult setNewOrSelectedApartmentBuildingType(StageResult res, BuildingTypeEnum.BuildingType type) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedApartment = resCopy.newOrSelectedApartment.setBuildingType(resCopy.newOrSelectedApartment, type);
        return resCopy;
    }

    public StageResult setNewOrSelectedApartmentFloorsCount(StageResult res, int count) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedApartment = resCopy.newOrSelectedApartment.setFloorsCount(resCopy.newOrSelectedApartment, count);
        return resCopy;
    }

    public StageResult setNewOrSelectedRequestRoomCount(StageResult res, int count) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedRequest = resCopy.newOrSelectedRequest.setRoomCount(resCopy.newOrSelectedRequest, count);
        return resCopy;
    }

    public StageResult addToRequests(StageResult res, Requests requests, RequestInfo newOrSelectedRequest) {
        StageResult resCopy = res.copy(res);
        resCopy.requests = requests.add(newOrSelectedRequest, requests);
        return resCopy;
    }

    public StageResult removeFromRequests(StageResult res, Requests requests, RequestInfo newOrSelectedRequest) {
        StageResult resCopy = res.copy(res);
        resCopy.requests = requests.remove(newOrSelectedRequest, requests);
        return resCopy;
    }

    public StageResult removeFromApartments(StageResult res, Apartments apartments, ApartmentInfo newOrSelectedApartment) {
        StageResult resCopy = res.copy(res);
        resCopy.apartments = apartments.remove(newOrSelectedApartment, apartments);
        return resCopy;
    }

    public StageResult setStage(StageResult res, int stage) {
        StageResult resCopy = res.copy(res);
        resCopy.stage = stage;
        return resCopy;
    }

    public StageResult setDistrictToSearch(StageResult res, String districtToSearch) {
        StageResult resCopy = res.copy(res);
        resCopy.districtToSearch = districtToSearch;
        return resCopy;
    }

    public StageResult setNewOrSelectedApartment(StageResult res, ApartmentInfo newOrSelectedApartment) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedApartment = newOrSelectedApartment;
        return resCopy;
    }

    public StageResult setNewOrSelectedRequest(StageResult res, RequestInfo newOrSelectedRequest) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedRequest = newOrSelectedRequest;
        return resCopy;
    }

    public StageResult setNewOrSelectedApartmentAddress(StageResult res, Address address) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedApartment = resCopy.newOrSelectedApartment.setAddress(resCopy.newOrSelectedApartment, address);
        return resCopy;
    }

    public StageResult setNewOrSelectedApartmentFloorNum(StageResult res, int i) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedApartment = resCopy.newOrSelectedApartment.setFloorNum(resCopy.newOrSelectedApartment, i);
        return resCopy;
    }

    public StageResult setNewOrSelectedApartmentRoomCount(StageResult res, int i) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedApartment = resCopy.newOrSelectedApartment.setRoomCount(resCopy.newOrSelectedApartment, i);
        return resCopy;
    }

    public StageResult setNewOrSelectedApartmentArea(StageResult res, double i) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedApartment = resCopy.newOrSelectedApartment.setArea(resCopy.newOrSelectedApartment, i);
        return resCopy;
    }

    public StageResult setNewOrSelectedApartmentPrice(StageResult res, double i) {
        StageResult resCopy = res.copy(res);
        resCopy.newOrSelectedApartment = resCopy.newOrSelectedApartment.setPrice(resCopy.newOrSelectedApartment, i);
        return resCopy;
    }
}

