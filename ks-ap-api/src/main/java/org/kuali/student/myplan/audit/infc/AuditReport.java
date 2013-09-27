package org.kuali.student.myplan.audit.infc;

import org.kuali.student.r2.common.infc.TypeStateEntity;

import javax.activation.DataHandler;
import java.util.Date;


/**
 * Detailed report for an audit
 *
 * @Author Kamal
 */
public interface AuditReport extends TypeStateEntity {

    /**
     * Id of the Audit request
     * @name Audit Id
     */
    public String getAuditId();

    public String getReportType();

        /**
         * Content type of the audit report data
         * @name Report Content Type
         */
    public String getReportContentTypeKey();

    /**
     * Audit report data
     * @name Report
     */
    public DataHandler getReport();

    public String getRequirementsSatisfied();

    public String getStudentId();

    public String getProgramId();

    public Date getRunDate();
}
