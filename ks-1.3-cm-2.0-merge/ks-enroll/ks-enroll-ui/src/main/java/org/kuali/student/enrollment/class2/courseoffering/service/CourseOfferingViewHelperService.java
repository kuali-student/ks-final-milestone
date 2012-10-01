package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingRolloverManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.form.DeleteTargetTermForm;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;

import java.util.Date;
import java.util.List;

/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 5/7/12
 */
public interface CourseOfferingViewHelperService extends ViewHelperService {
    /**
     *
     * @param termCode Each institution uses a code to represent a term.  At UW, the code appears to be three letters
     *                 followed by a 4-digit year, e.g., FAL2011, WIN2011, etc.
     * @return List of terms which match the term code (should be a list of one TermInfo)
     * @throws Exception
     */
    public List<TermInfo> findTermByTermCode(String termCode) throws Exception;

    public SocInfo createSocCoFoAoForTerm(String termId, CourseOfferingRolloverManagementForm form);

    public boolean cleanSourceTerm(String targetTermId, CourseOfferingRolloverManagementForm form);
    
    public boolean performRollover(String sourceTermId, String targetTermId, CourseOfferingRolloverManagementForm form);

    public SocRolloverResultInfo performReverseRollover(String sourceTermId, String targetTermId, CourseOfferingRolloverManagementForm form);

    public void deleteTargetTerm(String targetTermId, DeleteTargetTermForm form);

    public List<SocRolloverResultInfo> findRolloverByTerm(String termId) throws Exception;
    
    public boolean termHasSoc(String termId, CourseOfferingRolloverManagementForm form);
    
    public String formatDate(Date date);

    public SocInfo getMainSoc(String termId);

    // Used in rollover details screen
    public String formatDateAndTime(Date date);

    // User friendly term string
    public String getTermDesc(String termId);
}
