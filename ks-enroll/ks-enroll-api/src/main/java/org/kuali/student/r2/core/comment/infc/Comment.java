package org.kuali.student.r2.core.comment.infc;

import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;

/**
 * Comments associated with other objects in the system
 * User: komandur
 */
public interface Comment extends IdEntity {

    public RichText getCommentText();

    public String getReferenceTypeKey();

    public String getReferenceId();

    public Date getEffectiveDate();

    public Date getExpirationDate();

}
