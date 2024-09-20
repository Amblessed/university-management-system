package com.amblessed.universitymanagementsystem.utilities;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
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

}
