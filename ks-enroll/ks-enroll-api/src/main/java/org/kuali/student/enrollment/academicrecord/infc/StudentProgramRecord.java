package org.kuali.student.enrollment.academicrecord.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StudentProgramRecord extends IdEntity {

    /**
     *  Program code
     *  @name Program Code
     *  @readOnly
     */
    public String getProgramCode();

    /**
     *  Student's major
     *  
     *  @name Major
     */
    public String getMajor();

    /**
     *  Student's minor
     *
     *  @name Minor
     */
    public String getMinor();

    /**
     *  Program admittance date
     *  @name Admitted Date
     *  @readOnly
     *  @required
     */
    public String getAdmittedDate();

    /**
     * Credits earned in the program
     * @name Credits Earned
     * @required
     */
    public String getCreditsEarned();

    /**
     * Sub programs the student is enrolled in 
     * @name Child Programs
     */
    public List<? extends StudentProgramRecord> getChildPrograms();

    /**
     * Indication of how much of the program is completed
     * @name Completion Status Type Key
     * TODO: Is this same as 'standing in the program' -- MyPlan needs one
     */
    public String getCompletionStatusTypeKey();
        
}
