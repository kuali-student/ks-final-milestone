/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.builder;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.lu.ui.krms.builder.CourseComponentBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class EnrolCourseComponentBuilder extends CourseComponentBuilder {

    private final static Logger LOG = Logger.getLogger(EnrolCourseComponentBuilder.class);
    private transient AtpService atpService;
    private transient CourseOfferingService courseOfferingService;
    private AcademicCalendarService acalService = null;

    @Override
    public Map<String, String> buildTermParameters(LUPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getCourseInfo() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, propositionEditor.getCourseInfo().getVersion().getVersionIndId());
        }

        if (propositionEditor.getTermInfo() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, propositionEditor.getTermInfo().getId());
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERMCODE_KEY, propositionEditor.getTermCode());
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "termCode");
            loadCourseOfferingsByTermAndCourseCode(propositionEditor, propName);
        }

        if (propositionEditor.getTermInfo2() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM2_KEY, propositionEditor.getTermInfo2().getId());
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERMCODE2_KEY, propositionEditor.getTermCode2());
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "termCode2");
            loadCourseOfferingsByTermAndCourseCode(propositionEditor,propName );
        }

        return termParameters;
    }

    /**
     * Method used to
     * find THE course based on termId and courseCode. If find more than one CO or don't find
     * any CO, log and report an error message.
     *
     * @param propositionEditor
     * @param propName
     */
    public void loadCourseOfferingsByTermAndCourseCode(LUPropositionEditor propositionEditor, String propName ) {

        boolean foundPriorTerm = true;
        boolean foundAsOfTerm = true;
        boolean foundBetweenTerm = true;
        List<CourseOfferingInfo> courseOfferings;
        try {
            courseOfferings = this.getCourseOfferingService().getCourseOfferingsByCourse(propositionEditor.getCourseInfo().getId(), ContextUtils.createDefaultContextInfo());

            for (CourseOfferingInfo courseOffering : courseOfferings) {

                TermInfo term;
                term = this.getAcalService().getTerm(courseOffering.getTermId(), ContextUtils.createDefaultContextInfo());

                if (propositionEditor.getType().equals(KSKRMSServiceConstants.PROPOSITION_TYPE_SUCCESS_COMPL_PRIOR_TO_TERM)) {
                    foundPriorTerm = false;
                    if ((term.getEndDate().before(propositionEditor.getTermInfo().getEndDate()))) {
                        foundPriorTerm = true;
                        break;
                    }
                } else if (propositionEditor.getType().equals(KSKRMSServiceConstants.PROPOSITION_TYPE_SUCCESS_COMPL_COURSE_AS_OF_TERM)) {
                    foundAsOfTerm = false;
                    if ((term.getStartDate().after(propositionEditor.getTermInfo().getStartDate()))) {

                        foundAsOfTerm = true;
                        break;
                    }
                } else if (propositionEditor.getType().equals(KSKRMSServiceConstants.PROPOSITION_TYPE_SUCCESS_COMPL_COURSE_BETWEEN_TERMS)) {
                    foundBetweenTerm = false;
                    if ((term.getStartDate().before(propositionEditor.getTermInfo().getStartDate())) || (term.getStartDate().after(propositionEditor.getTermInfo2().getEndDate()))) {
                        foundBetweenTerm = true;
                        break;
                    }
                }

            }

        } catch (DoesNotExistException e) {
            throw new RuntimeException("CO does not exist");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!foundPriorTerm) {
            GlobalVariables.getMessageMap().putError(propName, KRMSConstants.KSKRMS_MSG_ERROR_NO_PRIOR_COURSEOFFERING_TERM_FOUND, "Course", propositionEditor.getCourseInfo().getCode(), propositionEditor.getTermInfo().getName());
        }
        if (!foundAsOfTerm) {
            GlobalVariables.getMessageMap().putError(propName, KRMSConstants.KSKRMS_MSG_ERROR_NO_ASOF_COURSEOFFERING_TERM_FOUND, "Course", propositionEditor.getCourseInfo().getCode(), propositionEditor.getTermInfo().getName());
        }
        if (!foundBetweenTerm) {
            GlobalVariables.getMessageMap().putError(propName, KRMSConstants.KSKRMS_MSG_ERROR_NO_BETWEEN_COURSEOFFERING_TERM_FOUND, "Course", propositionEditor.getCourseInfo().getCode(), propositionEditor.getTermInfo().getName(), propositionEditor.getTermInfo2().getName());
        }
    }

    private AtpService getAtpService() {
        if (atpService == null) {
            atpService = CourseOfferingResourceLoader.loadAtpService();
        }
        return atpService;
    }

    private CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    private AcademicCalendarService getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

}
