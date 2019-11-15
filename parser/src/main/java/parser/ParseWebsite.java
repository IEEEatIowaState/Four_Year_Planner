package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.Arrays;

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

        String semesterOffered = "";
        if (str.indexOf("S.") > 0) {
            semesterOffered += "S,";
        }
        if (str.indexOf("F.") > 0) {
            semesterOffered += "F,";
        }
        if (str.indexOf("SS.") > 0) {
            semesterOffered += "SS,";
        }
        if (str.indexOf("Repeatable.")>0) {
            semesterOffered += "Repeatable";
        }
        if (semesterOffered.length() <= 1) semesterOffered = "N/A";
        else semesterOffered = semesterOffered.substring(0, semesterOffered.length()-1); //takes out when classes are offered

        //TODO add function to parse prereqs
        str = str.substring(str.indexOf("prereq"));
        String prereqs_str = "";
        if(str.indexOf("<em>") > 0) {
            prereqs_str = str.substring(str.indexOf("<em>"), str.indexOf("</em>"));
        }
        System.out.println("\n------------");
        System.out.println("Department: " + department); //
        System.out.println("Course number: " + coursenum);//
        ArrayList<Requirements> reqs = getPrereqs(str);

        str = str.substring(str.indexOf("<br>") + 4);
        String description  = str.substring(0, str.indexOf("</p>"));

        Course course = new Course(department, coursenum);//create a new course since there is enough info to create one
        course.setName(courseTitle);
        int numcredit = (creditNum.indexOf("R") < 0 && creditNum.indexOf("NA") < 0)? Integer.parseInt(creditNum):0;
        course.setCredit(numcredit);
        course.setCrossList(crossList);

        //TODO need to add Summer as well
        course.setOffered(semesterOffered);

        course.setDescription(description);
        //String description = str.substring(0,1 );

        // Parse out the prereqs
        ArrayList<ArrayList<String> > prereqs;
        ArrayList<ArrayList<String> > coreqs;
        // TODO add this to the course

        if (debug) {
            System.out.println("Department: " + department); //
            System.out.println("Course number: " + coursenum);//
            System.out.println("Course title: " + courseTitle);//
            System.out.println(crossList);//
            System.out.println("Credit: " + creditNum);//
            System.out.println("Semesters offered: " + semesterOffered);//
            System.out.println(prereqs_str);
            System.out.println("Corse Description: " + description);//

            System.out.println("\n\n" + str  + "\n\n");
        }
        return course;
    }

    public static ArrayList<Requirements> getPrereqs(String str) {
        // each list is a prereq string
        // i.e. "COM S 207 or COM S 227 or E E 285"
        //      "credit or enrollment in E E 230"
        StringBuilder sb = new StringBuilder();
        ArrayList<Requirements> reqs = new ArrayList<Requirements>();

        if(str.indexOf("<em>Prereq") > 0) {
            // Get the substring that is the list of classes. Offest the index
            // by 8 to remove the "Prereq: " part.
            str = str.substring(str.indexOf("Prereq")+8, str.indexOf("</em>"));

            // Grab everything that is between the classes (figure 2)
            String relations[] = str.split("<a.+?a>");

            // Grab everything that is a class
            ArrayList<String> classes = new ArrayList<String>();
            Pattern pat = Pattern.compile("<a.+?a>");
            Matcher match = pat.matcher(str);
            while(match.find()) {
                classes.add(extractClassName(match.group()));
            }
            //ArrayList<Requirements> reqs =  = buildReqList(classes, new ArrayList<String>(Arrays.asList(relations)));
            ArrayList<String> rels = replaceCommas(new ArrayList<String>(Arrays.asList(relations)));
            //System.out.println(rels);
            ArrayList<String> tokens = tokenizer(classes, rels);
            // cpre 538 has issues
            System.out.println(tokens);

            reqs = buildReqList(tokens);

            // check for parenthesis sectioning things off
            for(Requirements req : reqs) {
                System.out.println(req.toString());
            }

        } else {
            System.out.println("Nothing");
            //prereqs.add([]);
        }
        return reqs;
    }

    public static ArrayList<String> tokenizer(ArrayList<String> classes, ArrayList<String> relations) {
        ArrayList<String> tokens = new ArrayList<String>();
        int i;
        for(i = 0; i < relations.size(); i++) {
            String input = relations.get(i).trim().toLowerCase();
            if(!input.equals("")) {
                tokens.addAll(splitRelation(input));

            }
            if(i < classes.size()) {
                tokens.add(classes.get(i).toLowerCase());
            }
        }
        if(i < classes.size())
            tokens.add(classes.get(i));

        return tokens;
    }

    public static ArrayList<String> splitRelation(String rel) {
        ArrayList<String> reqs = new ArrayList<String>();
        String texts[] = rel.split(";");
        for(int i = 0; i < texts.length; i++) {
            String text = texts[i].trim();
            if(!text.equals("")) {
                String orSplits = trimCommas(text).trim();
                // If we split out the commans we need to add ; into the list to replace them
                if(!text.equals(orSplits)) {
                    reqs.add(";");
                }
                reqs.addAll(splitForORs(orSplits));
                if(!text.equals(orSplits)) {
                    reqs.add(";");
                }
            }
            if(i+1 < texts.length)
                reqs.add(";");
        }
        if(texts.length == 0) {
            reqs.add(";");
        }
        return reqs;
    }

    public static String trimCommas(String str) {
        if(str.length() < 2)
            return str;
        if(str.substring(0, 2).equals(", ")) {
            str = str.substring(2);
        }
        if(str.substring(str.length()-1).equals(",")) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }

    public static ArrayList<String> splitForORs(String str) {
        ArrayList<String> list = new ArrayList<String>();
        String[] tokenList = {"or", ", credit or enrollment in", "credit or enrollment in", "or instructor approval", "or equivalent"};
        for(String valid : tokenList) {
            String trimmed = str.trim();
            if(trimmed.equals(valid)) {
                list.add(str);
                return list;
            }
        }
        str = " " + str;
        String texts[] = str.split(" or ");
        for(String text : texts) {
            if(text.equals("")) {
                list.add("or");
            } else {
                list.add(text.trim());
                list.add("or");
            }
        }
        list.remove(list.size()-1);
        return list;
    }

    public static ArrayList<String> replaceCommas(ArrayList<String> tokens) {
        boolean orList = false;
        for(int i=tokens.size()-1; i >= 0; i--) {
            String val = tokens.get(i).trim();
            if(val.length() > 4 &&  val.substring(0, 3).equals("and")) {
                orList = false;
                tokens.set(i, ";");
            } else if(val.equals(", and") || val.equals("and either")) {
                orList = false;
                tokens.set(i, ";");
            } else if(val.equals(", or")) {
                orList = true;
                tokens.set(i, "or");
            } else if(val.equals(",")) {
                if(!orList) {
                    tokens.set(i, ";");
                } else {
                    tokens.set(i, "or");
                }
            } else {
                orList = false;
            }
        }
        return tokens;
    }

    public static ArrayList<Requirements> buildReqList(ArrayList<String> tokens) {
        ArrayList<Requirements> reqs = new ArrayList<Requirements>();
        Requirements reqList = new Requirements();

        if(tokens.size() < 1) {
            reqList.add(tokens.get(0));
            reqs.add(reqList);
            return reqs;
        }
        // TODO: cpre 491, 544, and 554
        for(String tk : tokens) {
            // If class, just add to req list
            if(tk.matches(".+\\d.+")) {
                reqList.add(tk);
            } else {
                if(tk.equals("and") || tk.equals(";") || tk.equals(".") || tk.matches("[!@#$%^&*(),.?\":{}|<>]")) {
                    reqs.add(reqList);
                    reqList = new Requirements();
                } else if(tk.equals("credit or enrollment in")) {
                    reqs.add(reqList);
                    reqList = new Requirements();
                    reqList.setCoreq(true);
                } else if(tk.equals("or instructor approval")) {
                    reqList.add(tk);
                } else if(tk.equals("or equivalent")) {
                    reqList.add(tk);
                } else if(tk.equals("or")) {
                    continue;
                } else {
                    reqList.add(tk);
                }
            }
        }

        if(reqList.getClasses().size() > 0) {
            reqs.add(reqList);
        }

        return reqs;
    }

    public static boolean validateRequirements(ArrayList<Requirements> reqs) {
        // TODO we should validate whether or not it's an actual class that we've seen before
        // if not mark it as a special requirement.
        return true;
    }
    public static String extractClassName(String classString) {
        // Grab everything that is a class
        Pattern pat = Pattern.compile("this, '(.+)'");
        Matcher match = pat.matcher(classString);
        match.find();
        return match.group(1);
    }

    // return: A pair object with the index range of the parentheses. The key
    // is the index of left parentheses "(" and the value is the ending parentheses
    // ")". If it doesn't find one or both the value will be -1. If given nested
    // parentheses we will just return the outer most set.
    // The pair object is represented by an arraylist.
    public static ArrayList<Integer> findParentheses(String relations[]) {
        int leftP = -1;
        int rightP = -1;
        int level = 0;

        for(int i = 0; i < relations.length; i++) {
            // check for left ( first
            if(relations[i].contains("(")) {
                // If we are at level 0 then we are at the first parentheses so
                // assign leftP to it. Then increment level.
                if(level++ == 0)
                    leftP = i;
            }
            if(relations[i].contains(")")) {
                // Decrement the level first, if it is 0 then we reached the
                // corrisponding end ).
                if(--level == 0)
                    rightP = i;
            }
        }

        ArrayList<Integer> pair = new ArrayList<Integer>();
        pair.add(leftP);
        pair.add(rightP);
        // Pair doc: https://docs.oracle.com/javase/8/javafx/api/javafx/util/Pair.html
        return pair;
    }

    // // checks if there is an or list
    // public static boolean checkForOrList(String str) {
    //     Pattern p = Pattern.compile("a>, or");
    //     Matcher m = p.matcher(str);
    //     return m.find();
    // }

    public static String getClassString(String html) {
        // determine if this is a typical class prereq,
        return "string";
    }
}

// <a href="/search/?P=CPR%20E%20281" title="CPR&nbsp;E&nbsp;281" class="bubblelink code" onclick="return showCourse(this, 'CPR E 281');">
//      CPR&nbsp;E&nbsp;281</
// <a href="/search/?P=COM%20S%20207" title="COM&nbsp;S&nbsp;207" class="bubblelink code" onclick="return showCourse(this, 'COM S 207');">
//      COM&nbsp;S&nbsp;207</a> or <a href="/search/?P=COM%20S%20227" title="COM&nbsp;S&nbsp;227" class="bubblelink code" onclick="return showCourse(this, 'COM S 227');">COM&nbsp;S&nbsp;227</a> or <a href="/search/?P=E%20E%20285" title="E&nbsp;E&nbsp;285" class="bubblelink code" onclick="return showCourse(this, 'E E 285');">E&nbsp;E&nbsp;285</a>

/*
 * Classes to look out for:
 * POL S 305, 306, 310
 * Prereq: Sophomore classification or six credits in political science
 * Prereq: 6 credits in political science or sophomore classification
 * CPR E 567
 */

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
