package org.kuali.student.enrollment.academicrecord.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StudentCredentialRecord extends IdEntity {
    /**
     * Id of the program that was enrolled in by the student.
     *
     * @name Program Id
     * @readOnly
     * @required
     */
    public String getProgramId();

    /**
     * Title of the program that was in effect at the time the student took the
     * course
     *
     * @name Program Title
     * @readOnly
     * @required
     */
    public String getProgramTitle();

    /**
     * Program Code
     * @name Program Code
     * @readOnly
     * @required
     */
    public String getProgramCode();

    /**
     * Date the student was admitted 
     *
     * @name Date Admitted
     * @readOnly
     * @required
     */
    public Date getDateAdmitted();

    /**
     * Date the student was awarded
     * 
     * @name Date Awarded
     * @readOnly
     */
    public Date getDateAwarded();

    /**
     * Awarding institution
     * 
     * @name Awarding Institution
     */
    public String getAwardingInstitution();
}
