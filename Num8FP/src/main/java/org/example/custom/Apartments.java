package org.example.custom;

import java.util.*;

public class Apartments {

    public List<ApartmentInfo> apartments = new ArrayList<>();


    public Apartments add(ApartmentInfo a, Apartments apart) {
        Apartments apartCopy = new Apartments();
        apartCopy.apartments = apart.apartments;
        Collections.copy(apartCopy.apartments, apart.apartments);
        apartCopy.apartments.add(a);
        return apartCopy;
    }

    public Apartments remove(ApartmentInfo a, Apartments apart) {
        Apartments apartCopy = new Apartments();
        apartCopy.apartments = apart.apartments;
        Collections.copy(apartCopy.apartments, apart.apartments);
        apartCopy.apartments.remove(a);
        return apartCopy;
    }

    public List<ApartmentInfo> search(BuildingTypeEnum.BuildingType buildingType, String district, int roomCount, int roomRange, Apartments apart) {
        List<ApartmentInfo> list = new ArrayList<>();
        for (ApartmentInfo info : apart.apartments) {
            if (info.buildingType == buildingType && info.address.district.equals(district) && Math.abs(info.roomCount - roomCount) <= roomRange) {
                list.add(info);
            }
        }
        return list;
    }

    public HashMap<String, List<ApartmentInfo>> groupByDistrict(Apartments apart) {
        HashMap<String, List<ApartmentInfo>> hashMap = new HashMap();
        for (ApartmentInfo info : apart.apartments) {
            String key = info.address.district;
            if(hashMap.containsKey(key)){
                List<ApartmentInfo> list = hashMap.get(key);
                list.add(info);
            }else{
                List<ApartmentInfo> list = new ArrayList();
                list.add(info);
                hashMap.put(key, list);
            }
        }
        return hashMap;
    }

    public HashMap<String, List<Double>> createStatistic(HashMap<String, List<ApartmentInfo>> hashMap) {
        HashMap<String, List<Double>> res = new HashMap<>();
        for (String key : hashMap.keySet()) {
            List<ApartmentInfo> list = hashMap.get(key);
            double sumArea = 0, sumPrice = 0;
            for (ApartmentInfo info : list) {
                sumArea += info.area;
                sumPrice += info.price;
            }
            List<Double> stat = new ArrayList<>();
            double count = list.size();
            stat.add(count); // количество предложений продажи по району
            stat.add(sumArea / count); // средний метраж по району
            stat.add(sumPrice / count); // средняя стоимость по району
            res.put(key, stat);
        }
        return res;
    }

    public List<ApartmentInfo> groupAndSort(Apartments apart) {
        List<ApartmentInfo> res = new ArrayList<>();
        HashMap<String, List<ApartmentInfo>> hashMap = groupByDistrict(apart);
        for (String key : hashMap.keySet()) {
            List<ApartmentInfo> list = hashMap.get(key);
            Collections.sort(list, Comparator.comparingDouble(o -> o.area));
            res.addAll(list);
        }
        return res;
    }

    public List<ApartmentInfo> getInRange(double startRange, double endRange, Apartments apart) {
        List<ApartmentInfo> res = new ArrayList<>();
        for (ApartmentInfo info : apart.apartments) {
            if(info.price >= startRange && info.price <= endRange) {
                res.add(info);
            }
        }
        return res;
    }
}
