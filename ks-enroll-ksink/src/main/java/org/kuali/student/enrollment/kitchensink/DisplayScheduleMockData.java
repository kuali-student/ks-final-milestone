package org.kuali.student.enrollment.kitchensink;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 8/30/12
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DisplayScheduleMockData implements Serializable {

    private String courseId;
    private String title;
    private String credits;
    private List<ActivityMockData> activities = new ArrayList<ActivityMockData>();

    public DisplayScheduleMockData() {
    }

    public DisplayScheduleMockData(String courseId, String title, String credits) {
        this.courseId = courseId;
        this.title = title;
        this.credits = credits;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseid) {
        this.courseId = courseid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public List<ActivityMockData> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityMockData> activities) {
        this.activities = activities;
    }

    public static List<DisplayScheduleMockData> mockTestData() {
        List<DisplayScheduleMockData> testData = new ArrayList<DisplayScheduleMockData>();

        DisplayScheduleMockData md1 = new DisplayScheduleMockData("CHEM142", "GENERAL CHEMISTRY", "5");
        md1.getActivities().add(new ActivityMockData("A", "LEC", "M W F", "11:30a-12:20P"));
        testData.add(md1);
        DisplayScheduleMockData md2 = new DisplayScheduleMockData("CHEM152", "GENERAL CHEMISTRY", "5");
        md2.getActivities().add(new ActivityMockData("AA", "LAB", "W", "2:30a-5:20P"));
        md2.getActivities().add(new ActivityMockData("AB", "LAB", "W", "1:30a-2:20P"));
        md2.getActivities().add(new ActivityMockData("AC", "LAB", "W", "1:30a-2:20P"));
        testData.add(md2);
        DisplayScheduleMockData md3 = new DisplayScheduleMockData("CHEM162", "GENERAL CHEMISTRY", "1-5");
        md3.getActivities().add(new ActivityMockData("A", "LEC", "M W F", "11:30a-12:20P"));
        testData.add(md3);

        return testData;
    }
}
