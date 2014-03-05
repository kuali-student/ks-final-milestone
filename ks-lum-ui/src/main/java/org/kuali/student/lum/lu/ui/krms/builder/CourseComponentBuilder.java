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
package org.kuali.student.lum.lu.ui.krms.builder;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.util.CluSearchUtil;
import org.kuali.student.lum.lu.ui.krms.util.LUKRMSConstants;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class CourseComponentBuilder extends CluComponentBuilder {

    private final static Logger LOG = Logger.getLogger(CourseComponentBuilder.class);

    private CourseService courseService;
    private AcademicCalendarService acalService = null;
    private SearchService searchService = null;

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(LUPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String courseId = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY);
        String termCode1 = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERMCODE_KEY);
        String termCode2 = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERMCODE2_KEY);

        if (courseId != null) {
            try {
                VersionDisplayInfo versionInfo = this.getCluService().getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, courseId, null);
                CourseInfo courseInfo = this.getCourseService().getCourse(versionInfo.getId(), ContextUtils.getContextInfo());
                propositionEditor.setCourseInfo(courseInfo);
            } catch (DoesNotExistException e) {
                throw new RuntimeException("Clu does not exist");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        if (termCode1 != null) {
            propositionEditor.setTermCode(termCode1);
            propositionEditor.setTermInfo(this.getTermForTermCode(propositionEditor.getTermCode(), null));
        }
        if (termCode2 != null) {
            propositionEditor.setTermCode2(termCode2);
            propositionEditor.setTermInfo2(this.getTermForTermCode(propositionEditor.getTermCode2(), null));
        }
    }

    @Override
    public Map<String, String> buildTermParameters(LUPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getCourseInfo() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, propositionEditor.getCourseInfo().getVersion().getVersionIndId());
        }

        if (propositionEditor.getTermInfo() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, propositionEditor.getTermInfo().getId());
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERMCODE_KEY, propositionEditor.getTermCode());
        }

        if (propositionEditor.getTermInfo2() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM2_KEY, propositionEditor.getTermInfo2().getId());
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERMCODE2_KEY, propositionEditor.getTermCode2());
        }

        return termParameters;
    }

    @Override
    public void onSubmit(LUPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void validate(LUPropositionEditor propositionEditor) {

        CourseInfo course = propositionEditor.getCourseInfo();
        if((course.getCode()==null)||(course.getCode().isEmpty())){
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "courseInfo.code");
            GlobalVariables.getMessageMap().putErrorForSectionId(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_APPROVED_COURSE_REQUIRED);
        } else {
            // convert term-code to UPPERCASE
            course.setCode(course.getCode().toUpperCase());
        }

        CluInformation searchClu = this.getCluInfoHelper().getCluInfoForCodeAndType(course.getCode(), CluSearchUtil.getCluTypesForCourse());
        if(searchClu==null){
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "courseInfo.code");
            GlobalVariables.getMessageMap().putErrorForSectionId(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_APPROVED_COURSE_CODE_INVALID);
        } else {
            course.setId(searchClu.getCluId());
            VersionInfo version = new VersionInfo();
            version.setVersionIndId(searchClu.getVerIndependentId());
            course.setVersion(version);
        }

        if (propositionEditor.getTermCode() != null) {
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "termCode");
            propositionEditor.setTermInfo(this.getTermForTermCode(propositionEditor.getTermCode(), propName));
        }

        if (propositionEditor.getTermCode2() != null) {
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "termCode2");
            propositionEditor.setTermInfo2(this.getTermForTermCode(propositionEditor.getTermCode2(), propName));
        }
    }

    /**
     * Method used to
     * 1) search to get TermInfo based on termCode. Only accept one valid TermInfo. If find more than one TermInfo or
     * don't find any termInfo, log and report an error message.
     *
     * @param termCode
     * @return termInfo
     */
    public TermInfo getTermForTermCode(String termCode, String propName) {

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", termCode));

        try {
            int firstTerm = 0;
            List<TermInfo> terms = getAcalService().searchForTerms(qbcBuilder.build(), ContextUtils.getContextInfo());
            if (terms.isEmpty()) {
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_NO_TERM_IS_FOUND, termCode);
            } else if (terms.size() > 1) {
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, termCode);
            } else {
                return terms.get(firstTerm);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "course", "CourseService"));
        }
        return courseService;
    }

    private AcademicCalendarService getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    public SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

}