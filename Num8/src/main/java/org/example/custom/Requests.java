package org.example.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Requests {

    List<RequestInfo> requests = new ArrayList<>();


    public int size() {
        return requests.size();
    }


    public boolean add(RequestInfo a) {
        return requests.add(a);
    }

    public boolean remove(RequestInfo a) {
        return requests.remove(a);
    }

    public RequestInfo get(int index) {
        return requests.get(index);
    }

    public List<RequestInfo> search(String districtToSearch) {
        List<RequestInfo> list = new ArrayList<>();
        for (RequestInfo info : requests) {
            if (info.district.equals(districtToSearch)) {
                list.add(info);
            }
        }
        return list;
    }

    public HashMap<String, List<Double>> createStatistic(HashMap<String, List<ApartmentInfo>> apartmentsHash) {
        HashMap<String, List<Double>> res = new HashMap<>();
        HashMap<String, List<RequestInfo>> hashMap = groupByDistrict();
        for (String key : hashMap.keySet()) {
            List<RequestInfo> localRequests = hashMap.get(key);
            List<ApartmentInfo> checkedApartments = new ArrayList<>();
            if (apartmentsHash.containsKey(key)) {
                List<ApartmentInfo> localApartments = apartmentsHash.get(key);
                for (RequestInfo requestInfo : localRequests) {
                    for (ApartmentInfo apartmentInfo : localApartments) {
                        if (!checkedApartments.contains(apartmentInfo) && apartmentInfo.buildingType == requestInfo.buildingType &&
                            apartmentInfo.roomCount == requestInfo.roomCount && apartmentInfo.address.district.equals(requestInfo.district)) {
                            checkedApartments.add(apartmentInfo);
                            break;
                        }
                    }
                }
            }
            List<Double> stat = new ArrayList<>();
            double count = localRequests.size(), percent = 0;
            if (count > 0) percent = checkedApartments.size() / count * 100;
            stat.add(count); // количество заявок на покупку по району
            stat.add(percent); // потенциальный процент покрытия заявок по району
            res.put(key, stat);
        }
        return res;
    }

    public HashMap<String, List<RequestInfo>> groupByDistrict() {
        HashMap<String, List<RequestInfo>> hashMap = new HashMap();
        for (RequestInfo info : requests) {
            String key = info.district;
            if(hashMap.containsKey(key)){
                List<RequestInfo> list = hashMap.get(key);
                list.add(info);
            }else{
                List<RequestInfo> list = new ArrayList();
                list.add(info);
                hashMap.put(key, list);
            }
        }
        return hashMap;
    }
}
