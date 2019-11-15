package parser;

import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;

public class JsonParser {

    public static void main(String args[]) {
        // Gson gson = new Gson();

        // Course sample = new Course("Cpre", 123, "Test Course", 2, new boolean[] {true, true, true}, "This is a sample course");
        // ArrayList<Course> list = new ArrayList<>();
        // list.add(sample);
        // list.add(sample);
        // list.add(sample);
        // String jsonInString = gson.toJson(list);
        // System.out.println(jsonInString);
    }


    public static String CourseToJson(ArrayList<Course> course) {
        Gson gson = new Gson();
        return gson.toJson(course);
    }
}
