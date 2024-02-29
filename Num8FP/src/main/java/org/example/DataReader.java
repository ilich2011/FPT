package org.example;

import org.example.custom.ApartmentInfo;
import org.example.custom.Apartments;
import org.example.custom.RequestInfo;
import org.example.custom.Requests;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {

    public Apartments readFromApartmentsFile() throws IOException, ParseException {
        FileReader fReader = new FileReader("src/main/java/org/example/json/apartments.json");
        JSONArray jArray = (JSONArray) new JSONParser().parse(fReader);
        Apartments apartments = new Apartments();
        for (Object obj : jArray) {
            JSONObject apartmentData = (JSONObject) obj;
            JSONObject addressData = (JSONObject) apartmentData.get("address");
            ApartmentInfo apartmentInfo = new ApartmentInfo(
                    (double) apartmentData.get("area"),
                    (int) (long) apartmentData.get("rooms"),
                    (String) addressData.get("district"),
                    (String) addressData.get("street"),
                    (int) (long) addressData.get("building_number"),
                    (int) (long) apartmentData.get("floor"),
                    (String) apartmentData.get("building_type"),
                    (int) (long) apartmentData.get("floors"),
                    (double) apartmentData.get("price")
            );
            apartments.apartments.add(apartmentInfo);
        }
        return apartments;
    }

    public Requests readFromRequestsFile() throws IOException, ParseException {
        FileReader fReader = new FileReader("src/main/java/org/example/json/requests.json");
        JSONArray jArray = (JSONArray) new JSONParser().parse(fReader);
        Requests requests = new Requests();
        for (Object obj : jArray) {
            JSONObject requestData = (JSONObject) obj;
            RequestInfo request = new RequestInfo(
                    (int) (long) requestData.get("rooms"),
                    (String) requestData.get("district"),
                    (String) requestData.get("building_type")
            );
            requests.requests.add(request);
        }
        return requests;
    }

}
