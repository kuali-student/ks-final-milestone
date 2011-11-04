package org.kuali.student.r2.lum.lo.infc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public interface Lo {


    /**
     * Unique identifier for a learning objective repository. This value is immutable once set during creation.
     */
    public String getLoRepositoryKey() ;
    /**
     * Date and time that this learning objective became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() ;


}
