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
	Course CprE281 = new Course("EcpE", 281, "New Course", 4, new boolean[]{true, true, true}, "Minimum sophomore classification" );
	Course CprE288 = new Course("EcpE", 288, "New Course", 4, new boolean[]{true,true,true}, "");
	Course CprE308 = new Course("EcpE", 308, "New Course", 4, new boolean[]{true,true,true}, "");
	Course CprE381 = new Course("EcpE", 381, "New Course", 4, new boolean[]{true,true,true}, "");
	Course ComS309 = new Course("ComS", 309, "New Course", 3, new boolean[]{true,true,true}, "");
	Course EE201 = new Course("EcpE", 201, "New Course", 4, new boolean[]{true,true,true}, "Death of the soul and body");
	Course EE230 = new Course("Ecpe", 230, "New Course", 4, new boolean[]{true,true,true}, "");
	Course ComS227 = new Course("ComS", 227, "New Course", 4, new boolean[]{true,true,true}, "");
    Course ComS228 = new Course("ComS", 228, "New Course", 3, new boolean[]{true,true,true},"");
    Course Math265 = new Course("Math", 265, "New Course", 4, new boolean[]{true,true,true},"");
    Course Math267 = new Course("Math", 267, "New Course", 4, new boolean[]{true,true,true},"");
    Course Phys222 = new Course("Phys", 222, "New Course", 5, new boolean[]{true,true,true},"");
    Course Engl314 = new Course("Engl", 314, "New Course", 3, new boolean[]{true,true,true},"");
    Course CprE491 = new Course("EcpE", 491, "New Course", 3, new boolean[]{true,true,true},"CprE core program completion");
    Course Stat330 = new Course("Stat", 330, "New Course", 3, new boolean[]{true,true,true},"");
    Course Math165 = new Course("Math", 165, "New Course", 4, new boolean[]{true,true,true},"");
    Course Math166 = new Course("Math", 166, "New Course", 4, new boolean[]{true,true,true},"");
    Course CprE185 = new Course("CprE", 185, "New Course", 3, new boolean[]{true,true,true},"");
    Course Chem167 = new Course("Chem", 167, "New Course", 4, new boolean[]{true,true,true},"");
    Course Phys221 = new Course("Phys", 221, "New Course", 5, new boolean[]{true,true,true},"");
    Course Engr101 = new Course("Engr", 101, "New Course", 1, new boolean[]{true,true,true},"");
    Course Lib160 = new Course("Lib", 160, "New Course", 1, new boolean[]{true,true,true},"");
    Course Eng150 = new Course("Engl", 150, "New Course", 3, new boolean[]{true,true,true},"");
    Course Eng250 = new Course("Engl", 250, "New Course", 3, new boolean[]{true,true,true},"");




    public ArrayList<Course> list = new ArrayList<>();
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
