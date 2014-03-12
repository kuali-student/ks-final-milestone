package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.framework.context.EnrollmentStatusHelper;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.r2.core.acal.infc.Term;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnrollmentStatusHelperMockTest implements EnrollmentStatusHelper {
    /**
     * This was changed form taking an ActivityOfferingItem to simply returning a string array.
     * Prevents a dependency on ks-ap-ui and the use of pass by ref.
     *
     * @param year
     * @param quarter
     * @param curric
     * @param num
     * @param sectionID
     * @return
     * @throws Exception
     */
    @Override
    public String[] populateEnrollmentFields(Term term, ActivityOffering offering) throws Exception {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Finds and returns the max. enrollment value for a class section of a course.
     *
     * @param year
     * @param quarter
     * @param curric
     * @param num
     * @param sectionID
     * @return
     * @throws Exception
     */
    @Override
    public String populateMaxEnrollmentField(Term term, ActivityOffering offering) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Finds and returns the current enrollment value for a class section of a course.
     *
     * @param year
     * @param quarter
     * @param curric
     * @param num
     * @param sectionID
     * @return
     * @throws Exception
     */
    @Override
    public String populateCurrentEnrollmentField(Term term, ActivityOffering offering) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Finds and returns the estimated enrollment value for a class section of a course.
     *
     * @param year
     * @param quarter
     * @param curric
     * @param num
     * @param sectionID
     * @return
     * @throws Exception
     */
    @Override
    public String populateEstimatedEnrollmentField(Term term, ActivityOffering offering) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param courseCode
     * @return
     */
    @Override
    public CourseCode getCourseDivisionAndNumber(String courseCode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param subjectArea
     * @param number
     * @return
     */
    @Override
    public String getCourseId(String subjectArea, String number) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
