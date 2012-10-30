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

public class KitchenSinkMockDisplayScheduleData implements Serializable {

    private String courseId;
    private String title;
    private String credits;
    private List<KitchenSinkMockActivityData> activities = new ArrayList<KitchenSinkMockActivityData>();

    public KitchenSinkMockDisplayScheduleData() {
    }

    public KitchenSinkMockDisplayScheduleData(String courseId, String title, String credits) {
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

    public List<KitchenSinkMockActivityData> getActivities() {
        return activities;
    }

    public void setActivities(List<KitchenSinkMockActivityData> activities) {
        this.activities = activities;
    }

    public static List<KitchenSinkMockDisplayScheduleData> mockTestData() {
        List<KitchenSinkMockDisplayScheduleData> testData = new ArrayList<KitchenSinkMockDisplayScheduleData>();

        KitchenSinkMockDisplayScheduleData md1 = new KitchenSinkMockDisplayScheduleData("CHEM142", "GENERAL CHEMISTRY", "5");
        md1.getActivities().add(new KitchenSinkMockActivityData("A", "LEC", "M W F", "11:30a-12:20P"));
        testData.add(md1);
        KitchenSinkMockDisplayScheduleData md2 = new KitchenSinkMockDisplayScheduleData("CHEM152", "GENERAL CHEMISTRY", "5");
        md2.getActivities().add(new KitchenSinkMockActivityData("AA", "LAB", "W", "2:30a-5:20P"));
        md2.getActivities().add(new KitchenSinkMockActivityData("AB", "LAB", "W", "1:30a-2:20P"));
        md2.getActivities().add(new KitchenSinkMockActivityData("AC", "LAB", "W", "1:30a-2:20P"));
        testData.add(md2);
        KitchenSinkMockDisplayScheduleData md3 = new KitchenSinkMockDisplayScheduleData("CHEM162", "GENERAL CHEMISTRY", "1-5");
        md3.getActivities().add(new KitchenSinkMockActivityData("A", "LEC", "M W F", "11:30a-12:20P"));
        testData.add(md3);

        return testData;
    }
}
