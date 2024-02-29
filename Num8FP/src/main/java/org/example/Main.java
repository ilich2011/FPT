package org.example;

import org.example.custom.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        new Main().mainMenu();
    }

    private StageResult defaultMenu() {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        System.out.println("1. Заявки по району");
        System.out.println("2. Группировка квартир по району");
        System.out.println("3. Поиск квартир по диапазону стоимости");
        System.out.println("4. Статистика квартир по каждому району");
        System.out.println("5. Выход");
        System.out.println();
        System.out.print("Введите номер опции: ");
        int i = -1;
        try {
            i = in.nextInt();
        } catch (Exception e) {
        }
        if (i == 5) {
            System.exit(0);
        } else if (i >= 1 && i <= 4) {
            res = res.setStage(res, i);
        } else {
            res = res.setStage(res, 0);
        }
        return res;
    }

    private StageResult requestsByDistinct() {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        System.out.print("Введите название района: ");
        res = res.setDistrictToSearch(res, in.nextLine());
        if (!res.districtToSearch.isEmpty()) {
            res = res.setStage(res, 5);
        } else {
            res = res.setStage(res, 0);
        }
        System.out.println(res.districtToSearch);
        return res;
    }

    private StageResult apartmentsByDistinct(Apartments apartments) {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        int counter = 1;
        System.out.println("Список квартир:");
        List<ApartmentInfo> list = apartments.groupAndSort(apartments);
        if (list.isEmpty()) {
            System.out.println("Квартир не найдено");
        } else {
            for (; counter <= list.size(); counter++) {
                System.out.println(counter + ". " + list.get(counter - 1));
            }
        }
        System.out.println(counter + ". Добавить квартиру");
        System.out.println((counter + 1) + ". Назад");
        System.out.print("Введите номер опции: ");
        int i = -1;
        try {
            i = in.nextInt();
        } catch (Exception e) {
        }
        if (i >= 1 && i <= list.size()) {
            res = res.setStage(res, 9);
            res = res.setNewOrSelectedApartment(res, list.get(i - 1));
        } else if (i == counter) {
            res = res.setStage(res, 8);
        } else {
            res = res.setStage(res, 0);
        }
        return res;
    }

    private StageResult apartmentsByCost(Apartments apartments) {
        double startRange = -1, endRange = -1;
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        while (true) {
            if (startRange < 0) {
                System.out.println("Поиск квартир по диапазону стоимости \"? руб. - ? руб.\":");
                System.out.print("Левая граница: ");
                double i = -1;
                try {
                    i = in.nextDouble();
                } catch (Exception e) {
                }
                if (i < 0) {
                    res.setStage(res, 0);
                    break;
                } else {
                    startRange = i;
                }
            } else if (endRange < startRange) {
                System.out.println("Поиск квартир по диапазону стоимости \"" + startRange + " руб. - ? руб.\":");
                System.out.print("Правая граница: ");
                double i = -1;
                try {
                    i = in.nextDouble();
                } catch (Exception e) {
                }
                if (i < 0 || i < startRange) {
                    res.setStage(res, 0);
                    break;
                } else {
                    endRange = i;
                }
            } else {
                System.out.println("Поиск квартир по диапазону стоимости \"" + startRange + " руб. - " + endRange + " руб.\":");
                List<ApartmentInfo> list = apartments.getInRange(startRange, endRange, apartments);
                if (list.isEmpty()) {
                    System.out.println("Квартир не найдено");
                } else {
                    for (int counter = 1; counter <= list.size(); counter++) {
                        System.out.println(counter + ") " + list.get(counter - 1));
                    }
                }
                System.out.println("1. Назад");
                try {
                    in.nextInt();
                } catch (Exception e) {
                }
                res.setStage(res, 0);
                break;
            }
        }
        return res;
    }

    private StageResult statByDistinct(Apartments apartments, Requests requests) {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        System.out.println("Статистика по районам:");
        HashMap<String, List<ApartmentInfo>> apartmentsDistrict = apartments.groupByDistrict(apartments);
        HashMap<String, List<Double>> apartmentsStat = apartments.createStatistic(apartmentsDistrict);
        HashMap<String, List<Double>> requestsStat = requests.createStatistic(apartmentsDistrict, requests);
        int i = 1;
        for (String key : apartmentsStat.keySet()) {
            List<Double> stat = apartmentsStat.get(key);
            if (requestsStat.containsKey(key)) {
                stat.addAll(requestsStat.get(key));
            } else {
                stat.add(0.0);
                stat.add(0.0);
            }
            System.out.println(i + ") " + key + " район: ");
            System.out.println("\tКоличество предложений продажи: " + stat.get(0));
            System.out.println("\tСредний метраж: " + stat.get(1));
            System.out.println("\tСредняя стоимость: " + stat.get(2));
            System.out.println("\tКоличество заявок на покупку: " + stat.get(3));
            System.out.println("\tПотенциальный процент покрытия заявок: " + stat.get(4) + "%");
            i++;
        }
        if (apartmentsStat.isEmpty()) {
            System.out.println("База данных пуста");
        }
        System.out.println("1. Назад");
        try {
            in.nextInt();
        } catch (Exception e) {
        }
        res.setStage(res, 0);
        return res;
    }

    private StageResult showRequestsByDistinct(Requests requests, String districtToSearch) {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        int counter = 1;
        System.out.println("Список заявок по району \"" + districtToSearch + "\":");
        List<RequestInfo> reqList = requests.search(districtToSearch, requests);
        if (reqList.isEmpty()) {
            System.out.println("Заявок не найдено");
        } else {
            for (; counter <= reqList.size(); counter++) {
                System.out.println(counter + ". " + reqList.get(counter - 1));
            }
        }
        System.out.println(counter + ". Добавить заявку");
        System.out.println((counter + 1) + ". Назад");
        System.out.print("Введите номер опции: ");
        int i = -1;
        try {
            i = in.nextInt();
        } catch (Exception e) {
        }
        if (i >= 1 && i <= reqList.size()) {
            res = res.setStage(res, 7);
            res = res.setNewOrSelectedRequest(res, reqList.get(i - 1));
        } else if (i == counter) {
            res = res.setStage(res, 6);
        } else {
            res = res.setStage(res, 0);
        }
        return res;
    }

    private StageResult createRequestsByDistinct(Requests requests, String districtToSearch) {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        boolean back = false;
        System.out.println("Создание заявки для района \"" + districtToSearch + "\":");
        if (res.newOrSelectedRequest == null) {
            res = res.setNewOrSelectedRequest(res, new RequestInfo());
        }
        res = res.setNewOrSelectedRequestDistrict(res, districtToSearch);
        while (true){
            if (res.newOrSelectedRequest.buildingType == null) {
                System.out.println("1. Кирпичный");
                System.out.println("2. Панельный");
                System.out.print("Выберите номер типа дома: ");
                int i = -1;
                try {
                    i = in.nextInt();
                } catch (Exception e) {
                }
                if (i == 1) {
                    res = res.setNewOrSelectedRequestBuildingType(res, BuildingTypeEnum.BuildingType.BRICK);
                } else if (i == 2) {
                    res = res.setNewOrSelectedRequestBuildingType(res, BuildingTypeEnum.BuildingType.PANEL);
                } else {
                    back = true;
                    break;
                }
            } else {
                System.out.print("Введите желаемое число комнат: ");
                int i = -1;
                try {
                    i = in.nextInt();
                } catch (Exception e) {
                }
                if (i > 0) {
                    res = res.setNewOrSelectedRequestRoomCount(res, i);
                    res = res.addToRequests(res, requests, res.newOrSelectedRequest);
                    break;
                }
                back = true;
                break;
            }
        }
        if (back) {
            res = res.setNewOrSelectedRequest(res, null);
            res = res.setStage(res, 5);
        }else {
            res.setStage(res, 2);
            res.requests = requests.add(res.newOrSelectedRequest, requests);
        }

        return res;
    }

    private StageResult showRequestByDistinct(Requests requests, RequestInfo newOrSelectedRequest) {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        System.out.println(newOrSelectedRequest);
        System.out.println("1. Список подходящих квартир");
        System.out.println("2. Удалить");
        System.out.println("3. Назад");
        System.out.print("Введите номер опции: ");
        int i = -1;
        try {
            i = in.nextInt();
        } catch (Exception e) {
        }
        if (i == 1) {
            res = res.setStage(res, 10);
        } else {
            if (i == 2) {
                res = res.removeFromRequests(res, requests, newOrSelectedRequest);
            }
            res = res.setStage(res, 5);
        }
        return res;
    }

    private StageResult deleteApartment(Apartments apartments, ApartmentInfo newOrSelectedApartment) {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        System.out.println(newOrSelectedApartment);
        System.out.println("1. Удалить");
        System.out.println("2. Назад");
        int i = -1;
        try {
            i = in.nextInt();
        } catch (Exception e) {
        }
        if (i == 1) {
            res = res.removeFromApartments(res, apartments, newOrSelectedApartment);
        }
        res.setStage(res, 2);
        return res;
    }

    private StageResult suitableApartment(Apartments apartments, Requests requests, RequestInfo newOrSelectedRequest) {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        System.out.println("Список подходящих и «почти подходящих» квартир:");
        int counter = 1;
        List<ApartmentInfo> searchRes = apartments.search(newOrSelectedRequest.buildingType, newOrSelectedRequest.district, newOrSelectedRequest.roomCount, 1, apartments);
        if (searchRes.isEmpty()) {
            System.out.println("Подходящих квартир не найдено");
            System.out.println("1. Назад");
        } else {
            for (; counter <= searchRes.size(); counter++) {
                System.out.println(counter + ". " + searchRes.get(counter - 1));
            }
            System.out.println(counter + ". Назад");
        }
        System.out.print("Введите номер опции: ");
        int i = -1;
        try {
            i = in.nextInt();
        } catch (Exception e) {
        }
        if (i >= 1 && i <= searchRes.size()) {
            res = res.removeFromRequests(res, requests, newOrSelectedRequest);
            res = res.removeFromApartments(res, apartments, searchRes.get(i - 1));
            //res.newOrSelectedRequest = null;
            res.setStage(res, 5);
        } else {
            res.setStage(res, 7);
        }
        return res;
    }

    private StageResult createApartment(Apartments apartments, ApartmentInfo newOrSelectedApartment) {
        Scanner in = new Scanner(System.in);
        StageResult res = new StageResult();
        res = res.setNewOrSelectedApartment(res, newOrSelectedApartment);
        boolean back = false;
        System.out.println("Добавление квартиры:");
        if (res.newOrSelectedApartment == null) {
            res = res.setNewOrSelectedApartment(res, new ApartmentInfo());
        }
        if (res.newOrSelectedApartment.address == null) {
            res.newOrSelectedApartment.address = new Address();
            res = res.setNewOrSelectedApartmentAddress(res, new Address());
        }
        while (true) {
            if (res.newOrSelectedApartment.address.district.isEmpty()) {
                System.out.print("Введите название района: ");
                String s = in.nextLine();
                if (s.isEmpty()) {
                    back = true;
                    break;
                } else {
                    res = res.setNewOrSelectedApartmentAddress(res, new Address(s, "", -1));
                }
            } else if (res.newOrSelectedApartment.address.street.isEmpty()) {
                System.out.print("Введите название улицы: ");
                String s = in.nextLine();
                if (s.isEmpty()) {
                    back = true;
                    break;
                } else {
                    res = res.setNewOrSelectedApartmentAddress(res, new Address(res.newOrSelectedApartment.address.district, s, -1));
                }
            } else if (res.newOrSelectedApartment.address.number < 1) {
                System.out.print("Введите номер дома: ");
                int i = -1;
                try {
                    i = in.nextInt();
                } catch (Exception e) {
                }
                if (i < 1) {
                    back = true;
                    break;
                } else {
                    res.newOrSelectedApartment.address.number = i;
                    res = res.setNewOrSelectedApartmentAddress(res, new Address(res.newOrSelectedApartment.address.district, res.newOrSelectedApartment.address.street, i));
                }
            } else if (res.newOrSelectedApartment.buildingType == null) {
                System.out.println("1. Кирпичный");
                System.out.println("2. Панельный");
                System.out.print("Выберите номер типа дома: ");
                int i = -1;
                try {
                    i = in.nextInt();
                } catch (Exception e) {
                }
                if (i == 1) {
                    res = res.setNewOrSelectedApartmentBuildingType(res, BuildingTypeEnum.BuildingType.BRICK);
                } else if (i == 2) {
                    res = res.setNewOrSelectedApartmentBuildingType(res, BuildingTypeEnum.BuildingType.PANEL);
                } else {
                    back = true;
                    break;
                }
            } else if (res.newOrSelectedApartment.floorsCount < 0) {
                System.out.print("Введите число этажей в доме: ");
                int i = -1;
                try {
                    i = in.nextInt();
                } catch (Exception e) {
                }
                if (i > 0) {
                    res = res.setNewOrSelectedApartmentFloorsCount(res, i);
                } else {
                    back = true;
                    break;
                }
            } else if (res.newOrSelectedApartment.floorNum == 0) {
                System.out.print("Введите номер этажа, на котором находится квартира: ");
                int i = -1;
                try {
                    i = in.nextInt();
                } catch (Exception e) {
                }
                if (i != 0) {
                    res = res.setNewOrSelectedApartmentFloorNum(res, i);
                } else {
                    back = true;
                    break;
                }
            } else if (res.newOrSelectedApartment.roomCount < 0) {
                System.out.print("Введите число комнат в квартире: ");
                int i = -1;
                try {
                    i = in.nextInt();
                } catch (Exception e) {
                }
                if (i > 0) {
                    res = res.setNewOrSelectedApartmentRoomCount(res, i);
                } else {
                    back = true;
                    break;
                }
            } else if (res.newOrSelectedApartment.area < 0) {
                System.out.print("Введите метраж квартиры: ");
                double i = -1;
                try {
                    i = in.nextDouble();
                } catch (Exception e) {
                }
                if (i > 0) {
                    res = res.setNewOrSelectedApartmentArea(res, i);
                } else {
                    back = true;
                    break;
                }
            } else {
                System.out.print("Введите стоимость квартиры: ");
                double i = -1;
                try {
                    i = in.nextDouble();
                } catch (Exception e) {
                }
                if (i >= 0) {
                    res = res.setNewOrSelectedApartmentPrice(res, i);
                    break;
                }
                back = true;
                break;
            }
        }
        if (back) {
            res.setNewOrSelectedApartment(res, null);
            res.setStage(res, 0);
        }else{
            res.setStage(res, 2);
            res.apartments = apartments.add(res.newOrSelectedApartment, apartments);
        }
        return res;
    }

    public void mainMenu() {
        int stage = 0;
        String districtToSearch = "";
        ApartmentInfo newOrSelectedApartment = null;
        RequestInfo newOrSelectedRequest = null;
        Apartments apartments = new Apartments();
        Requests requests = new Requests();

        DataReader reader = new DataReader();
        try {
            apartments = reader.readFromApartmentsFile();
            requests = reader.readFromRequestsFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        while (true) {
            StageResult res = null;
            if (stage == 0) {
                res = defaultMenu();
            } else if (stage == 1) {
                res = requestsByDistinct();
                districtToSearch = res.districtToSearch;
            } else if (stage == 2) {
                res = apartmentsByDistinct(apartments);
                newOrSelectedApartment = res.newOrSelectedApartment;
            } else if (stage == 3) {
                res = apartmentsByCost(apartments);
            } else if (stage == 4) {
                res = statByDistinct(apartments, requests);
            } else if (stage == 5) {
                res = showRequestsByDistinct(requests, districtToSearch);
                newOrSelectedRequest = res.newOrSelectedRequest;
            } else if (stage == 6) {
                res = createRequestsByDistinct(requests, districtToSearch);
                requests = res.requests;
            } else if (stage == 7) {
                res = showRequestByDistinct(requests, newOrSelectedRequest);
            } else if (stage == 8) {
                res = createApartment(apartments, newOrSelectedApartment);
                apartments = res.apartments;
            } else if (stage == 9) {
                res = deleteApartment(apartments, newOrSelectedApartment);
                apartments = res.apartments;
                newOrSelectedApartment = null;
            } else if (stage == 10) {
                res = suitableApartment(apartments, requests, newOrSelectedRequest);
                apartments = res.apartments;
                requests = res.requests;
                newOrSelectedRequest = null;
            }
            stage = res.stage;
        }
    }

}