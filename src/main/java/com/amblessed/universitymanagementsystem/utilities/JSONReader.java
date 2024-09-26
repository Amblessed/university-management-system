package com.amblessed.universitymanagementsystem.utilities;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class JSONReader {

    public static String readJSONFile(String departmentName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        final String[] facultyName = {null};
        JSONObject object = (JSONObject) parser.parse(new FileReader("src/main/resources/departments.json"));
        Set<?> keySet = object.keySet();

        for (Object key: keySet){
            JSONArray departmentArray = (JSONArray) object.get(key);
            if(departmentArray.contains(departmentName)){
                facultyName[0] = key.toString();
                break;
            }
        }
        return facultyName[0];
    }

    public static void main(String[] args) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader("src/main/resources/departments.json"));
        Set<?> keySet = object.keySet();


        System.out.println(keySet);
        for (Object key: keySet){
            JSONArray departmentArray = (JSONArray) object.get(key);
            for (Object department: departmentArray){
                System.out.println(department);
            }
        }

        System.out.println("******************");

        InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("departments.json");
            //pass InputStream to JSON-Library, e.g. using Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
            String jsonString = mapper.writeValueAsString(jsonNode);
            System.out.println(jsonNode);
            System.out.println(jsonNode.size());
            jsonNode.fields().forEachRemaining(key -> {
                System.out.println(key.getKey());
                JsonNode node = key.getValue();
                for (JsonNode dept: node){
                    System.out.println(dept.asText());
                }
                System.out.println("   ");
            });
        }
}

