package parser;
import java.util.ArrayList;

public class Requirements {

    private ArrayList<String> classes; // classes that are interchangable
    private boolean unique;
    private boolean isCoreq;

    public Requirements() {
        classes = new ArrayList<String>();
        unique = false;
        isCoreq = false;
    }
    public String toString() {
        String s = "";
        for(String c : classes) {
            s += c + ", ";
        }
        if(isCoreq) {
            s += "coreq";
        }
        return s;
    }
    public void setCoreq(boolean coreq) {
        isCoreq = coreq;
    }
    public void setUnique(boolean unique) {
        this.unique = unique;
    }
    public void add(String cl) {
        classes.add(cl);
    }
    public ArrayList<String> getClasses() {
        return classes;
    }
    public boolean isUnique() {
        return unique;
    }
    public boolean isCoreq() {
        return isCoreq;
    }
}
