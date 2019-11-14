import java.util.*;

/**
 * A class for holding and manipulating data for a specific course
 *
 * @author Matthew Phipps
 */
public class Course
{
    public String name;
    public String description;
    public String crossList;
    public double credits;
    public String offered;
    public ArrayList<ArrayList<String>>  preReqs;
    public ArrayList<ArrayList<String>>  coReqs;
    
    /**
     * Constructor for objects of class course
     */
    public Course(String name,
    String description,
    String crossList,
    double credits,
    String offered,
    ArrayList<ArrayList<String>>  preReqs,
    ArrayList<ArrayList<String>>  coReqs)
    {
       this.name        = name;
       this.description = description;
       this.crossList   = crossList;
       this.credits     = credits;
       this.offered     = offered;
       this.preReqs     = preReqs;
       this.coReqs      = coReqs;
    }
}
