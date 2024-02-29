package org.example;

import org.example.custom.*;

import java.util.*;

public class Main {

    Apartments apartments = new Apartments();
    Requests requests = new Requests();
    int stage = 0;
    ApartmentInfo newOrSelectedApartment = null;
    RequestInfo newOrSelectedRequest = null;
    String districtToSearch = "";
    double startRange = -1, endRange = -1;

    public static void main(String[] args) {
        new Main().Menu();
    }

    public void Menu() {
        DataReader reader = new DataReader();
        try {
            apartments = reader.readFromApartmentsFile();
            requests = reader.readFromRequestsFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        while (true) {
            mainMenu();
        }
    }

    public void mainMenu() {
        Scanner in = new Scanner(System.in);
        if (stage == 0) {
            System.out.println("1. Заявки по району");
            System.out.println("2. Группировка квартир по району");
            System.out.println("3. Поиск квартир по диапазону стоимости");
            System.out.println("4. Статистика квартир по каждому району");
            System.out.println("5. Выход");
            System.out.println();
            System.out.print("Введите номер опции: ");
            int i = in.nextInt();
            if (i == 5) {
                System.exit(0);
            } else if (i >= 1 && i <= 4) {
                stage = i;
            }
        } else if (stage == 1) {
            System.out.print("Введите название района: ");
            districtToSearch = in.nextLine();
            if (!districtToSearch.isEmpty()) {
                stage = 5;
            } else {
                stage = 0;
            }
        } else if (stage == 2) {
            int counter = 1;
            System.out.println("Список квартир:");
            List<ApartmentInfo> list = apartments.groupAndSort();
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
            int i = in.nextInt();
            if (i >= 1 && i <= list.size()) {
                newOrSelectedApartment = list.get(i - 1);
                stage = 9;
            } else if (i == counter) {
                stage = 8;
            } else  {
                stage = 0;
            }
        } else if (stage == 3) {
            if (startRange < 0) {
                System.out.println("Поиск квартир по диапазону стоимости \"? руб. - ? руб.\":");
                System.out.print("Левая граница: ");
                double i = in.nextDouble();
                if (i < 0) {
                    stage = 0;
                } else {
                    startRange = i;
                }
            } else if (endRange < startRange) {
                System.out.println("Поиск квартир по диапазону стоимости \"" + startRange + " руб. - ? руб.\":");
                System.out.print("Правая граница: ");
                double i = in.nextDouble();
                if (i < 0 || i < startRange) {
                    startRange = -1;
                    stage = 0;
                } else {
                    endRange = i;
                }
            } else {
                System.out.println("Поиск квартир по диапазону стоимости \"" + startRange + " руб. - " + endRange + " руб.\":");
                List<ApartmentInfo> list = apartments.getInRange(startRange, endRange);
                if (list.isEmpty()) {
                    System.out.println("Квартир не найдено");
                } else {
                    for (int counter = 1; counter <= list.size(); counter++) {
                        System.out.println(counter + ") " + list.get(counter -1));
                    }
                }
                System.out.println("1. Назад");
                in.nextInt();
                startRange = endRange = -1;
                stage = 0;
            }
        } else if (stage == 4) {
            System.out.println("Статистика по районам:");
            HashMap<String, List<ApartmentInfo>> apartmentsDistrict = apartments.groupByDistrict();
            HashMap<String, List<Double>> apartmentsStat = apartments.createStatistic(apartmentsDistrict);
            HashMap<String, List<Double>> requestsStat = requests.createStatistic(apartmentsDistrict);
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
            in.nextInt();
            stage = 0;
        } else if (stage == 5) {
            int counter = 1;
            System.out.println("Список заявок по району \"" + districtToSearch + "\":");
            List<RequestInfo> reqList = requests.search(districtToSearch);
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
            int i = in.nextInt();
            if (i >= 1 && i <= reqList.size()) {
                stage = 7;
                newOrSelectedRequest = reqList.get(i-1);
            } else if (i == counter) {
                stage = 6;
            } else {
                stage = 0;
            }
        } else if (stage == 6) {
            boolean back = false;
            System.out.println("Создание заявки для района \"" + districtToSearch + "\":");
            if (newOrSelectedRequest == null) {
                newOrSelectedRequest = new RequestInfo();
            }
            newOrSelectedRequest.district = districtToSearch;
            if (newOrSelectedRequest.buildingType == null) {
                System.out.println("1. Кирпичный");
                System.out.println("2. Панельный");
                System.out.print("Выберите номер типа дома: ");
                int i = in.nextInt();
                if (i == 1) {
                    newOrSelectedRequest.buildingType = BuildingTypeEnum.BuildingType.BRICK;
                } else if (i == 2) {
                    newOrSelectedRequest.buildingType = BuildingTypeEnum.BuildingType.PANEL;
                } else {
                    back = true;
                }
            } else {
                System.out.print("Введите желаемое число комнат: ");
                int i = in.nextInt();
                if (i > 0) {
                    newOrSelectedRequest.roomCount = i;
                    requests.add(newOrSelectedRequest);
                }
                back = true;
            }
            if (back) {
                newOrSelectedRequest = null;
                stage = 5;
            }
        } else if (stage == 7) {
            System.out.println(newOrSelectedRequest);
            System.out.println("1. Список подходящих квартир");
            System.out.println("2. Удалить");
            System.out.println("3. Назад");
            System.out.print("Введите номер опции: ");
            int i = in.nextInt();
            if (i == 1) {
                stage = 10;
            } else {
                if (i == 2) {
                    requests.remove(newOrSelectedRequest);
                }
                newOrSelectedRequest = null;
                stage = 5;
            }
        } else if (stage == 8) {
            boolean back = false;
            System.out.println("Добавление квартиры:");
            if (newOrSelectedApartment == null) {
                newOrSelectedApartment = new ApartmentInfo();
            }
            if (newOrSelectedApartment.address == null) {
                newOrSelectedApartment.address = new Address();
            }
            if (newOrSelectedApartment.address.district.isEmpty()) {
                System.out.print("Введите название района: ");
                String s = in.nextLine();
                if (s.isEmpty()) {
                    back = true;
                } else {
                    newOrSelectedApartment.address.district = s;
                }
            } else if (newOrSelectedApartment.address.street.isEmpty()) {
                System.out.print("Введите название улицы: ");
                String s = in.nextLine();
                if (s.isEmpty()) {
                    back = true;
                } else {
                    newOrSelectedApartment.address.street = s;
                }
            } else if (newOrSelectedApartment.address.number < 1) {
                System.out.print("Введите номер дома: ");
                int i = in.nextInt();
                if (i < 1) {
                    back = true;
                } else {
                    newOrSelectedApartment.address.number = i;
                }
            } else if (newOrSelectedApartment.buildingType == null) {
                System.out.println("1. Кирпичный");
                System.out.println("2. Панельный");
                System.out.print("Выберите номер типа дома: ");
                int i = in.nextInt();
                if (i == 1) {
                    newOrSelectedApartment.buildingType = BuildingTypeEnum.BuildingType.BRICK;
                } else if (i == 2) {
                    newOrSelectedApartment.buildingType = BuildingTypeEnum.BuildingType.PANEL;
                } else {
                    back = true;
                }
            } else if (newOrSelectedApartment.floorsCount < 0) {
                System.out.print("Введите число этажей в доме: ");
                int i = in.nextInt();
                if (i > 0) {
                    newOrSelectedApartment.floorsCount = i;
                } else {
                    back = true;
                }
            } else if (newOrSelectedApartment.floorNum == 0) {
                System.out.print("Введите номер этажа, на котором находится квартира: ");
                int i = in.nextInt();
                if (i != 0) {
                    newOrSelectedApartment.floorNum = i;
                } else {
                    back = true;
                }
            } else if (newOrSelectedApartment.roomCount < 0) {
                System.out.print("Введите число комнат в квартире: ");
                int i = in.nextInt();
                if (i > 0) {
                    newOrSelectedApartment.roomCount = i;
                } else {
                    back = true;
                }
            } else if (newOrSelectedApartment.area < 0) {
                System.out.print("Введите метраж квартиры: ");
                double i = in.nextDouble();
                if (i > 0) {
                    newOrSelectedApartment.area = i;
                } else {
                    back = true;
                }
            } else {
                System.out.print("Введите стоимость квартиры: ");
                double i = in.nextDouble();
                if (i >= 0) {
                    newOrSelectedApartment.price = i;
                    apartments.add(newOrSelectedApartment);
                }
                back = true;
            }
            if (back) {
                newOrSelectedApartment = null;
                stage = 2;
            }
        }   else if (stage == 9) {
            System.out.println(newOrSelectedApartment);
            System.out.println("1. Удалить");
            System.out.println("2. Назад");
            int i = in.nextInt();
            if (i == 1) {
                apartments.remove(newOrSelectedApartment);
            }
            newOrSelectedApartment = null;
            stage = 2;
        }   else if (stage == 10) {
            System.out.println("Список подходящих и «почти подходящих» квартир:");
            int counter = 1;
            List<ApartmentInfo> searchRes = apartments.search(newOrSelectedRequest.buildingType, newOrSelectedRequest.district, newOrSelectedRequest.roomCount, 1);
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
            int i = in.nextInt();
            if (i >= 1 && i <= searchRes.size()) {
                requests.remove(newOrSelectedRequest);
                apartments.remove(searchRes.get(i - 1));
                newOrSelectedRequest = null;
                stage = 5;
            } else {
                stage = 7;
            }
        }
    }

}