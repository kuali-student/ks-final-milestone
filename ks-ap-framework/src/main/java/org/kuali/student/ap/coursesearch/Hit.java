package org.kuali.student.ap.coursesearch;

import java.util.Comparator;

public class Hit implements Comparable<Hit>{
    public String courseID;
    public int count = 0;

    public Hit(String courseID) {
        this.courseID = courseID;
        count = 1;
    }

    @Override
    public boolean equals(Object other) {
        return courseID.equals(((Hit) other).courseID);
    }

    @Override
    public int hashCode() {
        return courseID.hashCode();
    }

    @Override
    public int compareTo(Hit y) {
        if (this == null)
            return -1;
        if (y == null)
            return 1;
        return y.count - this.count;
    }
}