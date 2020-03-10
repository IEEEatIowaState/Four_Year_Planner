import java.util.*;
import java.net.*;
import java.io.*;
/**
 * Write a description of class parserController here.
 *
 * @author Matthew Phipps
 * @version (a version number or a date)
 */
public class parserController
{
    public static void main()throws java.net.MalformedURLException,
    java.io.IOException{
        Course course = new Course(
        "CprE 281",
        "Fun class",
        null,
        3,
        "f,s,sum",
        null,
        null);
        postCourse(course);
    }
    public static void postCourse(Course course)throws java.net.MalformedURLException,
    java.io.IOException{
        URL url = new URL("http://localhost:3000/class/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", course.name);
        parameters.put("description", course.description);
        parameters.put("crosslists", course.crossList);
        //TODO (figure out how to post non strings
        //parameters.put("credits", course.credits);
        parameters.put("SemOffered", course.offered);
        //TODO rest of parameters that arnt strings.
        String temp = ParameterStringBuilder.getParamsString(parameters);
        System.out.println(temp);
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(temp);
        out.flush();
        out.close();
        int status = con.getResponseCode();
    }
    
    public static void getTest()throws java.net.MalformedURLException,
    java.io.IOException{
        
        URL url = new URL("http://localhost:3000/class/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", "CprE 281");
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        String temp = ParameterStringBuilder.getParamsString(parameters);
        out.writeBytes(temp);
        System.out.println(temp);
        out.flush();
        out.close();
        int status = con.getResponseCode();
    }
    
}
