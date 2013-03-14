package org.kuali.student.myplan.course.dataobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jasonosgood
 * Date: 12/5/12
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingInstitution implements Comparable<CourseOfferingInstitution> {
    private String code;
    private String name;

    private List<CourseOfferingTerm> courseOfferingTermList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseOfferingTerm> getCourseOfferingTermList() {
        if (courseOfferingTermList == null) {
            courseOfferingTermList = new ArrayList<CourseOfferingTerm>();
        }
        return courseOfferingTermList;
    }

    public void setCourseOfferingTermList(List<CourseOfferingTerm> courseOfferingTermList) {
        this.courseOfferingTermList = courseOfferingTermList;
    }

    @Override
    public int compareTo(CourseOfferingInstitution that) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if (this == that) return EQUAL;
        // I decided in this context, null > ""
        if (this.code == null) return AFTER;
        if (that.code == null) return BEFORE;
        return this.code.compareTo(that.code);
    }

}
