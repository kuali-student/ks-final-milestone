package org.kuali.student.myplan.audit.service.model;

import java.util.ArrayList;
import java.util.List;

public class Subrequirement {

    // complete, inprogress
    public String status = "complete";
    public boolean optional = false;
    public String or = " ";

    public String caption = null;
    public Count count;
    public GPA gpa;
    public Credits credits;
    public String nolist;

    public ArrayList<CourseTaken> courseTakenList = new ArrayList<CourseTaken>();
    public ArrayList<CourseAcceptable> courseAcceptableList = new ArrayList<CourseAcceptable>();


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isComplete() {
        return "C".equals( status );
    }

    public String getCaption() {
        return caption;
    }

    public boolean hasCaption() {
        return caption != null;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean hasCount() {
        return count != null;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public boolean hasGPA() {
        return gpa != null;
    }

    public GPA getGpa() {
        return gpa;
    }

    public boolean hasCredits() {
        if( credits == null ) return false;
        if( "0".equals( credits.getInprogress()) && "0".equals( credits.getNeeds() ) ) return false;
        return true;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public void setGPA(GPA gpa) {
        this.gpa = gpa;
    }


    public boolean hasCourseTakenList() {
        return courseTakenList.size() > 0;
    }

    public List<CourseTaken> getCourseTakenList() {
        return courseTakenList;
    }

    public void addCourseTaken(CourseTaken course) {
        courseTakenList.add(course);
    }

    public void setNolist( String nolist ) {
        this.nolist = nolist;
    }


    public boolean showTitle() {
        return " ".equals( nolist ) || "T".equals( nolist ) || "C".equals( nolist ) || "M".equals( nolist );
    }

    public boolean showAcceptable() {
        if( "T".equals( nolist )) return false;
        return ( "V".equals(nolist) && isComplete()) || ("W".equals(nolist) && !isComplete()) || "M".equals(nolist);
    }

    public String getAcceptableText() {
        StringBuilder sb = new StringBuilder();
        String dept = null;
        boolean comma = false;
        for (CourseAcceptable a : getCourseAcceptableList() ) {
            if (!a.getDept().equals(dept)) {
                if( comma ) {
                    sb.append(",");
                }
                else
                {
                    comma = true;
                }
                sb.append(" ");
                sb.append(a.getDept());
                sb.append(" ");
                dept = a.getDept();
            } else {
                sb.append(", ");
            }
            sb.append(a.getNumber());
        }
        return sb.toString();
    }

    public boolean showTaken() {
        if ("T".equals(nolist)) return false;
        return " ".equals(nolist) || "C".equals(nolist) || "M".equals(nolist);
    }

    public void addCourseAcceptable(CourseAcceptable courseAcceptable) {
        courseAcceptableList.add(courseAcceptable);
    }

    public List<CourseAcceptable> getCourseAcceptableList() {
        return courseAcceptableList;
    }

    public boolean hasCourseAcceptableList() {
        return courseAcceptableList.size() > 0;
    }


}
