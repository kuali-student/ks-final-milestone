package org.kuali.student.enrollment.academicrecord.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StudentProgramRecord extends IdEntity {

    /**
     * Program Id
     *
     * @name Program Id
     * @readOnly
     */
    public String getProgramId();


    /**
     * Program title
     *
     * @name Program Title
     * @readOnly
     */
    public String getProgramTitle();


    /**
     * Program type
     *
     * @name Program Type Key
     * @readOnly
     */
    public String getProgramTypeKey();

    /**
     * Program code
     *
     * @name Program Code
     * @readOnly
     */
    public String getProgramCode();

    /**
     * Program admittance date
     *
     * @name Admitted Date
     * @readOnly
     * @required
     */
    public String getAdmittedDate();

    /**
     * Credits earned in the program
     *
     * @name Credits Earned
     * @required
     */
    public String getCreditsEarned();

    /**
     * Student's class standing. It's an enumeration of levels
     * Example: freshman, sophomore, junior, senior.
     *
     * @name Class Standing
     */
    public String getClassStanding();


    /**
     * Sub programs the student is enrolled in. For example, specialization
     * within the program
     *
     * @name Child Programs
     */
    public List<? extends StudentProgramRecord> getChildPrograms();

    /**
     * Student's status in the program
     *
     * @name Status Key
     */
    public String getStatusKey();

}
