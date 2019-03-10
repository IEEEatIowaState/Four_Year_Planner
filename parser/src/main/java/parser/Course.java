package parser;

public class Course {

    //http://catalog.iastate.edu/azcourses/cpr_e/

    private String Department;
    private int CourseNum;
    private String CourseName;
    private int Credit;
    private boolean[] SemesterOffered = new boolean[] {false, false, false};  //fall, spring, repeatable
    private String CrossList;
    private String[] prereq;
    private String Description;

    public Course(String department, int courseNum) {
        this(department, courseNum, null, 0, null, null);
    }

    public Course(String department, int courseNum, String courseName, int credit, boolean[] semesterOffered,
            String description) {
        super();
        Department = department;
        CourseNum = courseNum;
        CourseName = courseName;
        Credit = credit;
        SemesterOffered = semesterOffered;
        Description = description;
        CrossList = "";
        prereq = new String[1];
        prereq[0] = "";
    }


    public void setDepartment(String department) {
        Department = department;
    }

    public void setCourseNum(int courseNum) {
        CourseNum = courseNum;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public void setCredit(int credit) {
        Credit = credit;
    }

    public void setSemesterOffered(boolean[] semesterOffered) {
        SemesterOffered = semesterOffered;
    }

    public void setCrossList(String crossList) {
        CrossList = crossList;
    }

    public void setPrereq(String[] prereq) {
        this.prereq = prereq;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDepartment() {
        return Department;
    }
    public int getCourseNum() {
        return CourseNum;
    }
    public String getCourseName() {
        return CourseName;
    }
    public int getCredit() {
        return Credit;
    }
    public boolean[] getSemesterOffered() {
        return SemesterOffered;
    }
    public String getCrossList() {
        return CrossList;
    }
    public String[] getPrereq() {
        return prereq;
    }
    public String getDescription() {
        return Description;
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

        sb.append(CourseName + "\n");
        sb.append("Credits: ");
        sb.append(Credit);
        sb.append("\n");

        sb.append("Offered: ");
        if(SemesterOffered[0]) {
            sb.append("f,");
        }
        if(SemesterOffered[1]) {
            sb.append("s,");
        }
        if(SemesterOffered[2]) {
            sb.append("r,");
        }
        sb.append("\n");
        sb.append("Crosslist: " + CrossList + "\n");

        sb.append("prereqs: ");
        for (String c : prereq) {
            sb.append(c + ", ");
        }
        sb.append("\n");
        sb.append(Description);
        return sb.toString();
    }



}
