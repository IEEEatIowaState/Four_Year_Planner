package parser;
import java.util.ArrayList;

public class Course {

    //http://catalog.iastate.edu/azcourses/cpr_e/

    private String Department;
    private int CourseNum;
    private String name;
    private String description;
    private String crossList;
    private double credits;
    private String offered;
    private ArrayList<ArrayList<String> > prereqs;
    private ArrayList<ArrayList<String> > coreqs;

    public Course(String department, int courseNum) {
        this(department, courseNum, null, 0, null, null);
    }

    public Course(String department, int courseNum, String courseName, double credit, String offered,
            String description) {
        super();
        Department = department;
        CourseNum = courseNum;
        name = courseName;
        credits = credit;
        this.offered = offered;
        this.description = description;
        crossList = "";
        prereqs = new ArrayList<ArrayList<String> >();
        coreqs = new ArrayList<ArrayList<String> >();

    }


    public void setDepartment(String department) {
        Department = department;
    }

    public void setCourseNum(int courseNum) {
        CourseNum = courseNum;
    }

    public void setName(String courseName) {
        name = courseName;
    }

    public void setCredit(double credit) {
        credits = credit;
    }

    public void setOffered(String offered) {
        this.offered = offered;
    }

    public void setCrossList(String crossList) {
        this.crossList = crossList;
    }

    public void setPrereqs(ArrayList<ArrayList<String> > prereqs) {
        this.prereqs = prereqs;
    }

    public void setCoreqs(ArrayList<ArrayList<String> > coreqs) {
        this.coreqs = coreqs;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return Department;
    }
    public int getCourseNum() {
        return CourseNum;
    }
    public String getName() {
        return name;
    }
    public double getCredit() {
        return credits;
    }
    public String getOffered() {
        return offered;
    }
    public String getCrossList() {
        return crossList;
    }
    public ArrayList<ArrayList<String> > getPrereqs() {
        return prereqs;
    }
    public ArrayList<ArrayList<String> > getCoreqs() {
        return coreqs;
    }
    public String getDescription() {
        return description;
    }

    /**
     * Returns a shortened course name, for easy viewing
     *
     * @return a shortened course name, in all caps
     */
    public String getShortName() {
        //TODO
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Department + " ");
        sb.append(CourseNum);
        sb.append("\n");

        sb.append(name + "\n");
        sb.append("Credits: ");
        sb.append(credits);
        sb.append("\n");

        sb.append("Offered: ");
        sb.append(offered);

        sb.append("\n");
        sb.append("Crosslist: " + crossList + "\n");

        sb.append("prereqs: ");
        for (ArrayList<String> pre : prereqs) {
            sb.append("\n");
            for(String c : pre) {
                sb.append(c + ", ");
            }
        }
        sb.append("\n");
        sb.append(description);
        return sb.toString();
    }



}
