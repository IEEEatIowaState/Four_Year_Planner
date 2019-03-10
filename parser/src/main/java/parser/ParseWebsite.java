package parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ParseWebsite {
    private static Document doc;
    private static boolean debug = false;

    public static void main(String args[]) {
        String url = "http://catalog.iastate.edu/azcourses/cpr_e/";
        Course courseList[] = parseToCourseArray(url);
        System.out.println("things");
        System.out.println("course_size " + courseList.length);

        for(Course c : courseList) {
            //System.out.println("-----");
            //System.out.println(c.toString());
        }

    }

    /**
     * takes an url and chuck all the classes into a course array
     *
     * Created by Tom Sun  - Nov.5,18
     *
     * @param url  The url of the website to be parsed
     * @return Course[] an array of course from the site
     */
    public static Course[] parseToCourseArray(String url) {
        Elements content = getElementsfromURL(url); //get an arrayList elements

        Course  courses[] = new Course[content.size()];			// create a new empty array

        for (int i = 0; i < content.size(); i++){ //parse the whole thing to string cuz i dont know how html works
            courses[i] = ElementToCourse(content.get(i).toString()); //add parsed element to array
        }

        return courses;
    }

    public static Elements getElementsfromURL(String url) {
        try {
            doc = Jsoup.connect(url).get();  //retrieve html file from url
            String title = doc.title(); //retrieve html tite
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements content = doc.getElementsByClass("courseblock"); //extract all course sub blocks

        return content;

    }

    /**
     * Takes an element from String and parse it into a course object
     *
     * Created by Tom Sun - Nov.5, 18
     *
     * @param a single element
     * @return  Course the course with all of the content from e
     */
    public static Course ElementToCourse(String str) {


        str = str.substring(str.indexOf("><strong>")+9); //gets rid of the garbage stuff up front
        String department = "";
        while(!Character.isDigit(str.charAt(0))) { //this loop takes out the department
            char c = str.charAt(0);
            if (Character.isUpperCase(c))
                department += c+"";
            str = str.substring(1);
        }// end for


        int coursenum = Integer.parseInt(str.substring(0, 3)); //this takes the course number out
        str = str.substring(5); //cut the course num part out

        String courseTitle = str.substring(0, str.indexOf("</strong><span>"));	//this takes the course title out
        str = str.substring(str.indexOf("credits noindent")+20);

        String crossList = "No Cross List";

        if (str.indexOf("Cross-listed")>= 0) { //check if there is a cross list
            crossList = str.substring(0, str.indexOf(").")); //this takes the cross list out
            str = str.substring(str.indexOf(").") + 1);
        } //end if

        int creditIndex = str.indexOf("Cr. ");
        String creditNum = "NA";
        if (creditIndex > 0) {
            creditNum = str.substring(creditIndex+4, creditIndex+5); //takes out the credit number, if any
        }//end if

        boolean offered[] = new boolean[] {false, false, false};
        String semesterOffered = "";
        if (str.indexOf("S.") > 0) {
            semesterOffered += "S,";
            offered[0] = true;
        }
        if (str.indexOf("F.") > 0) {
            semesterOffered += "F,";
            offered[1] = true;
        }
        if (str.indexOf("Repeatable.")>0) {
            semesterOffered += "Repeatable";
            offered[2] = true;
        }
        if (semesterOffered.length() <= 1) semesterOffered = "N/A";
        else semesterOffered = semesterOffered.substring(0, semesterOffered.length()-1); //takes out when classes are offered

        //TODO add function to parse prereqs
        str = str.substring(str.indexOf("prereq"));
        String prereqs = "";
        if(str.indexOf("<em>") > 0) {
            prereqs = str.substring(str.indexOf("<em>"), str.indexOf("</em>"));
        }
        System.out.println("\n------------");
        System.out.println("Department: " + department); //
        System.out.println("Course number: " + coursenum);//
        getPrereqs(str);

        str = str.substring(str.indexOf("<br>") + 4);
        String description  = str.substring(0, str.indexOf("</p>"));

        Course course = new Course(department, coursenum);//create a new course since there is enough info to create one
        course.setCourseName(courseTitle);
        int numcredit = (creditNum.indexOf("R") < 0 && creditNum.indexOf("NA") < 0)? Integer.parseInt(creditNum):0;
        course.setCredit(numcredit);
        course.setCrossList(crossList);

        //TODO need to add Summer as well
        course.setSemesterOffered(offered);

        course.setDescription(description);
        //String description = str.substring(0,1 );

        if (debug) {
            System.out.println("Department: " + department); //
            System.out.println("Course number: " + coursenum);//
            System.out.println("Course title: " + courseTitle);//
            System.out.println(crossList);//
            System.out.println("Credit: " + creditNum);//
            System.out.println("Semesters offered: " + semesterOffered);//
            System.out.println(prereqs);
            System.out.println("Corse Description: " + description);//

            System.out.println("\n\n" + str  + "\n\n");
        }
        return course;
    }

    public static ArrayList<String> getPrereqs(String str) {
        // each list is a prereq string
        // i.e. "COM S 207 or COM S 227 or E E 285"
        //      "credit or enrollment in E E 230"
        ArrayList<String> prereqs = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();

        if(str.indexOf("<em>Prereq") > 0) {
            // Get the substring that is the list of classes. Offest the index
            // by 8 to remove the "Prereq: " part.
            str = str.substring(str.indexOf("Prereq")+8, str.indexOf("</em>"));
            String strs[] = str.split("</a>,");
            //TODO finish getting the prereq strings

            for(String req : strs) {
                req = req.trim();
                System.out.println(req);
            }

        } else {
            prereqs.add("Nothing");
        }
        return prereqs;
    }
}
/*
</p><p class="prereq"><em>Prereq: Member of Cpr E Learning Community</em>
</p><p class="prereq"><em>
        Prereq: <a href="http://catalog.iastate.edu/search/?P=E%20E%20201" title="E E 201" class="bubblelink code"
            onclick="return showCourse(this, &#39;E E 201&#39;);">E&nbsp;E&nbsp;201</a>
        , credit or enrollment in <a href="http://catalog.iastate.edu/search/?P=E%20E%20230" title="E E 230" class="bubblelink code"
            onclick="return showCourse(this, &#39;E E 230&#39;);">E&nbsp;E&nbsp;230</a>
        , <a href="http://catalog.iastate.edu/search/?P=CPR%20E%20281" title="CPR E 281" class="bubblelink code"
            onclick="return showCourse(this, &#39;CPR E 281&#39;);">CPR&nbsp;E&nbsp;281</a></em>.
</p><p class="prereq"><em>
        Prereq: <a href="http://catalog.iastate.edu/search/?P=CPR%20E%20281" title="CPR E 281" class="bubblelink code"
            onclick="return showCourse(this, &#39;CPR E 281&#39;);">CPR&nbsp;E&nbsp;281</a>
        , <a href="http://catalog.iastate.edu/search/?P=COM%20S%20207" title="COM S 207" class="bubblelink code"
            onclick="return showCourse(this, &#39;COM S 207&#39;);">COM&nbsp;S&nbsp;207</a>
        or <a href="http://catalog.iastate.edu/search/?P=COM%20S%20227" title="COM S 227" class="bubblelink code"
            onclick="return showCourse(this, &#39;COM S 227&#39;);">COM&nbsp;S&nbsp;227</a>
        or <a href="http://catalog.iastate.edu/search/?P=E%20E%20285" title="E E 285" class="bubblelink code"
            onclick="return showCourse(this, &#39;E E 285&#39;);">E&nbsp;E&nbsp;285</a></em>
*/
/*
   Engineering Internship</strong><span></span></a></div><div class="courseblockdesc accordion-content"><p class="credits noindent">
   (Cross-listed with E E).  Cr. R.
   Repeatable.
   </p><p class="prereq"><br>One semester and one summer maximum per academic year professional work period.
   Offered on a satisfactory-fail basis only.</p></div>
   </div>
   */
