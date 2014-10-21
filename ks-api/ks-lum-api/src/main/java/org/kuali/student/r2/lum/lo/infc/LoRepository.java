package org.kuali.student.r2.lum.lo.infc;

import org.kuali.student.r2.common.infc.KeyEntity;

import java.util.Date;

public interface LoRepository extends KeyEntity {

    // TODO JAVADOCS

    public String getRootLoId();
    public Date getEffectiveDate();
    public Date getExpirationDate();

}
