package org.kuali.student.ap.framework.context;

public interface EnrollmentStatusHelper {

    /**
     * This was changed form taking an ActivityOfferingItem to simply returning a string array.
     * Prevents a dependency on ks-ap-ui and the use of pass by ref.
     * @param year
     * @param quarter
     * @param curric
     * @param num
     * @param sectionID
     * @return
     * @throws Exception
     */
    public String[] populateEnrollmentFields(String year, String quarter, String curric, String num, String sectionID) throws Exception;

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
    public String populateMaxEnrollmentField(String year, String quarter, String curric, String num, String sectionID) throws Exception;

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
    public String populateCurrentEnrollmentField(String year, String quarter, String curric, String num, String sectionID) throws Exception;

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
    public String populateEstimatedEnrollmentField(String year, String quarter, String curric, String num, String sectionID) throws Exception;

    /**
     * @param courseCode
     * @return
     */
    public CourseCode getCourseDivisionAndNumber(String courseCode);

    /**
     *
     * @param subjectArea
     * @param number
     * @return
     */
    public String getCourseId(String subjectArea, String number);

    public static class CourseCode {
        private final String subject;
        private final String number;
        private final String section;

        public CourseCode(String subject, String number, String section) {
            this.subject = subject;
            this.number = number;
            this.section = section;

        }

        public String getSubject() {
            return subject;
        }

        public String getNumber() {
            return number;
        }

        public String getSection() {
            return section;
        }

    }
}
