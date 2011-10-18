package org.kuali.student.r2.core.comment.infc;


import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.Date;

/**
 * Information about a Tag
 * User: komandur
 */
public interface Tag extends IdNamelessEntity {

    public String getNamespace();

    public String getPredicate();

    public String getValue();

    public String getReferenceTypeKey();

    public String getReferenceId();

    public Date getEffectiveDate();

    public Date getExpirationDate();
}
