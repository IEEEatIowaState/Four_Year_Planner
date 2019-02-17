package parser;

import java.io.FileWriter;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import com.google.gson.Gson;

public class JsonParser {

	public static void main(String args[]) {
		Gson gson = new Gson();
	
	Course sample = new Course("Cpre", 123, "Test Course", 2, new boolean[] {true, true, true}, "This is a sample course");
	Course CprE281 = new Course("CprE", 281, "New Course", 4, new boolean[]{true, true, true}, "Minimum sophomore classification" );
	ArrayList<Course> list = new ArrayList<>();
	list.add(sample);
	list.add(sample);
	list.add(sample);
	String jsonInString = gson.toJson(list);
	System.out.println(jsonInString);
	}
	*
	
	public String CourseToJson(ArrayList<Course> course) {
		Gson gson = new Gson();
		return gson.toJson(course);
	}
}
