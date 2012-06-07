package org.kuali.student.enrollment.academicrecord.infc.attic;

import org.kuali.student.enrollment.academicrecord.infc.StudentCourseRecord;
import org.kuali.student.enrollment.academicrecord.infc.StudentTestScoreRecord;
import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StudentTransferCreditRecord extends IdNamelessEntity {

    /**
     * Awarded course identifier
     *
     * @name Awarded Course Id
     * @readOnly
     * @required
     */
    public String getAwardedCourseId();

    /**
     * Awarded course title
     * 
     * @name Awarded Course Title
     * @readOnly
     * @required
     */
    public String getAwardedCourseTitle();

    /**
     * Awarded course code
     * 
     * @name Awarded Course Code
     * @readOnly
     * @required
     */
    public String getAwardedCourseCode();

    /**
     * Awarded credits
     * 
     * @name Awarded Credits
     * @readOnly
     * @required
     */
    public Float getAwardedCredits();

    /**
     * Date awarded
     * 
     * @name Date Awarded
     * @readOnly
     * @required
     */
    public Date getDateAwarded();

    /**
     * Identifier for the source of the transfer credit
     * 
     * @name Source Id
     * @readOnly
     * @required
     */
    public String getSourceId();

    /**
     *  Information about the transfer work
     *  
     *  @name Source Work
     *  @readOnly
     *  @required
     */
    public List<? extends StudentCourseRecord> getSourceWork();

    /**
     * Subcomponents of the transfer work
     * 
     * @name Sub Components
     * @readOnly
     * @required
     */
    public List<? extends StudentTestScoreRecord> getSubComponents();
}
