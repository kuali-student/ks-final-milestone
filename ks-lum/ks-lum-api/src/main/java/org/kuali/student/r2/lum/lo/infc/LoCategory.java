package org.kuali.student.r2.lum.lo.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;

/**
 *  Detailed information about a learning objective category.
 * 
 * @author Kuali Student Team
 */
public interface LoCategory extends IdEntity {

    /**
     * Unique identifier for a learning objective repository. Once set in
     * creation, this is immutable.
     * 
     * @name LO Repository
     */
    public String getLoRepositoryKey();

    /**
     * Date and time that this learning objective category became effective.
     * This is a similar concept to the effective date on enumerated values.
     * When an expiration date has been specified, this field must be less than
     * or equal to the expiration date.
     * 
     * @name Effectvie Date
     */
    public Date getEffectiveDate();

    /**
     * Date and time that this learning objective category expires. This is a
     * similar concept to the expiration date on enumerated values. If
     * specified, this should be greater than or equal to the effective date. If
     * this field is not specified, then no expiration date has been currently
     * defined and should automatically be considered greater than the effective
     * date.
     * 
     * @name Expiration Date
     */
    public Date getExpirationDate();

}
