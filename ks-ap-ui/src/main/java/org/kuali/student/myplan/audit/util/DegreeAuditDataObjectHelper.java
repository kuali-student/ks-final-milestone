package org.kuali.student.myplan.audit.util;

import org.kuali.student.myplan.audit.dataobject.DegreeAuditItem;
import org.kuali.student.myplan.audit.dto.AuditReportInfo;

public class DegreeAuditDataObjectHelper {
    /**
     *  Creates a DegreeAuditItem given an AuditReportInfo.
     */
    public static DegreeAuditItem makeDegreeAuditDataObject(AuditReportInfo auditReportInfo) {
        DegreeAuditItem degreeAuditItem = new DegreeAuditItem();
        degreeAuditItem.setReport(auditReportInfo);
        //  TODO: Find out the program title using the program id.
        degreeAuditItem.setProgramTitle(auditReportInfo.getProgramId());
        degreeAuditItem.setProgramType("Program Type");
        return degreeAuditItem;
    }
}
