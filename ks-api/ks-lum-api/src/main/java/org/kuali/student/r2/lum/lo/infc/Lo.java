package org.kuali.student.r2.lum.lo.infc;

import java.util.Date;


import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Detailed information about a learning object
 * 
 * @author Kuali Student Team
 */
public interface Lo extends IdEntity {

    /**
     * Unique identifier for a learning objective repository. This value is
     * immutable once set during creation.
     * 
     * @name LO Repository Key
     * @Required
     */
    public String getLoRepositoryKey();

    /**
     * Date and time that this learning objective became effective. This is a
     * similar concept to the effective date on enumerated values. When an
     * expiration date has been specified, this field must be less than or equal
     * to the expiration date.
     * 
     * @name Effective Date
     * @Required
     */
    public Date getEffectiveDate();

    /**
     * Date and time that this learning objective expires. This is a similar
     * concept to the expiration date on enumerated values. If specified, this
     * should be greater than or equal to the effective date. If this field is
     * not specified, then no expiration date has been currently defined and
     * should automatically be considered greater than the effective date.
     * 
     * @name Expiration Date
     * @required
     */
    public Date getExpirationDate();

}
