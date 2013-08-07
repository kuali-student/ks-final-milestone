package org.kuali.student.enrollment.academicrecord.infc.attic;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface ExternalCredentialRecord extends IdEntity {

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
     * @required
     */
    public Date getDateAwarded();

    /**
     * Awarding institution
     *
     * @name Awarding Institution
     */
    public String getAwardingInstitution();
}
