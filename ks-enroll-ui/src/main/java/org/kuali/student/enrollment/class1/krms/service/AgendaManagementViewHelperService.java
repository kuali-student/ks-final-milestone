package org.kuali.student.enrollment.class1.krms.service;

import org.kuali.student.enrollment.class1.krms.form.AgendaManagementForm;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.List;

public interface AgendaManagementViewHelperService {
    /**
     *
     * @param termCode Each institution uses a code to represent a term.  At UW, the code appears to be three letters
     *                 followed by a 4-digit year, e.g., FAL2011, WIN2011, etc.
     * @return List of terms which match the term code (should be a list of one TermInfo)
     * @throws Exception
     */
    public List<TermInfo> findTermByTermCode(String termCode) throws Exception;

    public void loadAgendasByTermAndCourseCode(String termId, String courseCode, AgendaManagementForm form) throws Exception;

}
