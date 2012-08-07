package org.kuali.student.r2.lum.lo.infc;

import java.util.Date;
import org.kuali.student.r2.common.infc.KeyEntity;

public interface LoRepository extends KeyEntity {

    /**
     * The id of the root learning objective.
     *
     * @name Root LO Id
     */
    public String getRootLoId();

    /**
     * The effective date of this repository
     *
     * @name Effective Date
     */
    public Date getEffectiveDate();

    /**
     * The expiration date of this repository
     *
     * @name Expiration Date
     */
    public Date getExpirationDate();
}
