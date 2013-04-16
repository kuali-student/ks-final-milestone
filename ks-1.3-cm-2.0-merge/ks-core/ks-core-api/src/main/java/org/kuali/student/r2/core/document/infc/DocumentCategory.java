package org.kuali.student.r2.core.document.infc;

import org.kuali.student.r2.common.infc.KeyEntity;

import java.util.Date;

/**
 *
 * Detailed information about a document category.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public interface DocumentCategory extends KeyEntity {

    /**
     * Date and time that this document category became effective. This is
     * a similar concept to the effective date on enumerated values. When an
     * expiration date has been specified,this field must be less than or
     * equal to the expiration date.
     *
     * @name Effective Date
     *
     */
    public Date getEffectiveDate();

    /**
     * Date and time that this document category expires. This is a similar
     * concept to the expiration date on enumerated values. If specified, this
     * should be greater than or equal to the effective date. If this field is
     * not specified, then no expiration date has been currently defined and
     * should automatically be considered greater than the effective date.
     * 
     * @name Expiration Date
     *
     */
    public Date getExpirationDate();

}
