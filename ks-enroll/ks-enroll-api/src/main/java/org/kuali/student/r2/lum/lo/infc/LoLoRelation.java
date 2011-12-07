package org.kuali.student.r2.lum.lo.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;

public interface LoLoRelation extends IdEntity {

    // TODO JAVADOCS

    public String getLoId();
    public String getRelatedLoId();
    public Date getEffectiveDate();
    public Date getExpirationDate();

}
