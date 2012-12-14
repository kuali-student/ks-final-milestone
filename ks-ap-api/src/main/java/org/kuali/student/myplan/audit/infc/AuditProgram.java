package org.kuali.student.myplan.audit.infc;

import org.kuali.student.r2.common.infc.TypeStateEntity;

import javax.activation.DataHandler;
import java.util.Date;


/**
 * Programs for an audit
 *
 * @Author hemanthg
 */
public interface AuditProgram {
    /**
     *
     * Audit program Id
     */
    public String getProgramId();

    /**
     *
     * Audit program Title
     */
    public String getProgramTitle();


}
