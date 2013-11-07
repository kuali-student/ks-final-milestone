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
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingRolloverManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.form.DeleteTargetTermForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseofferingset.service.impl.DeleteTargetTermRolloverRunner;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class provides helper logic for Course Offering related ui
 *
 * @author Kuali Student Team
 */
public class CourseOfferingViewHelperServiceImpl extends ViewHelperServiceImpl implements CourseOfferingViewHelperService {

    private static final Logger LOG = Logger.getLogger(CourseOfferingViewHelperServiceImpl.class);

    @Override
    public List<TermInfo> findTermByTermCode(String termCode) throws Exception {
        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        List<TermInfo> terms = CourseOfferingManagementUtil.getAcademicCalendarService().searchForTerms(criteria, new ContextInfo());

        return terms;
    }

    private int _mainSocCount(List<String> socIds) {
        int mainSocCount = 0;
        try {
            for (String socId: socIds) {
                SocInfo socInfo = CourseOfferingManagementUtil.getSocService().getSoc(socId, new ContextInfo());
                if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                    mainSocCount++;
                }
            }
        } catch (Exception e) {
            return -1;
        }
        return mainSocCount;
    }

    private SocInfo _getUniqueMainSoc(List<String> socIds) {
        SocInfo mainSoc = null;
        int mainSocCount = 0;
        try {
            for (String socId: socIds) {
                SocInfo socInfo = CourseOfferingManagementUtil.getSocService().getSoc(socId, new ContextInfo());
                if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                    mainSocCount++;
                    if (mainSocCount > 1) {
                        mainSoc = null;
                    } else if (mainSocCount == 1) {
                        mainSoc = socInfo;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return mainSoc;
    }

    @Override
    public boolean termHasSoc(String termId, CourseOfferingRolloverManagementForm form) {
        try {
            List<String> socIds = CourseOfferingManagementUtil.getSocService().getSocIdsByTerm(termId, new ContextInfo());
            if (socIds == null || socIds.isEmpty()) {
                if (form != null) {
                    form.setStatusField("No SOCS in source term");
                }
                return false;
            } else {
                int mainSocCount = _mainSocCount(socIds);
                if (mainSocCount != 1) {
                    if (form != null) {
                        form.setStatusField("Wrong number of SOCS in source term: " + socIds.size());
                    }
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean termHasExamPeriod(String termId) {
        try {
            List<ExamPeriodInfo> epInfos = CourseOfferingManagementUtil.getAcademicCalendarService().getExamPeriodsForTerm(termId, new ContextInfo());
            if (epInfos == null || epInfos.isEmpty()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SocInfo getMainSoc(String termId) {
        try {
            List<String> socIds = CourseOfferingManagementUtil.getSocService().getSocIdsByTerm(termId, new ContextInfo());
            return _getUniqueMainSoc(socIds);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SocRolloverResultInfo performReverseRollover(String sourceTermId, String targetTermId, CourseOfferingRolloverManagementForm form) {
        try {
            List<String> socIds = CourseOfferingManagementUtil.getSocService().getSocIdsByTerm(sourceTermId, new ContextInfo());
            if (socIds == null || socIds.isEmpty()) {
                form.setStatusField("No SOCS in source term");
                return null;
            } else if (socIds.size() > 1) {
                form.setStatusField("Too many SOCS in source term: " + socIds.size());
                return null;
            } else {
                int firstValue = 0;
                String sourceSocId = socIds.get(firstValue);
                List<String> resultIds = CourseOfferingManagementUtil.getSocService().getSocRolloverResultIdsBySourceSoc(sourceSocId, new ContextInfo());
                if (resultIds == null || resultIds.isEmpty()) {
                    form.setStatusField("No rollover results for source term");
                    return null;
                } else if (resultIds.size() > 1) {
                    form.setStatusField("Too many rollover results for source term: " + resultIds.size());
                    return null;
                } else {
                    String socResultId = resultIds.get(firstValue);
                    List<String> options = new ArrayList<String>();
                    SocRolloverResultInfo info = CourseOfferingManagementUtil.getSocService().reverseRollover(socResultId, options, new ContextInfo());
                    return info;
                }
            }
        } catch (Exception e) {
            return null;       
        }
    }
    
    @Override
    public boolean performRollover(String sourceTermId, String targetTermId, CourseOfferingRolloverManagementForm form) {
        try {
            ContextInfo context = ContextUtils.getContextInfo();
            List<String> socIds = CourseOfferingManagementUtil.getSocService().getSocIdsByTerm(sourceTermId, context);
            SocInfo socInfo = _getUniqueMainSoc(socIds);
            if (socInfo == null) {
                GlobalVariables.getMessageMap().putError("sourceTermCode", "error.rollover.sourceTerm.noSoc");
            } else {
                String sourceSocId = socInfo.getId();
                List<String> options = CourseOfferingManagementUtil.getDefaultOptionKeysService().getDefaultOptionKeysForRolloverSoc();
                CourseOfferingManagementUtil.getSocService().rolloverSoc(sourceSocId, targetTermId, options, context);
                return true;
            }
        } catch (Exception e) {
            LOG.error("--------- rollover exception in performRollover. ", e);
            form.setStatusField("performRollover: Exception thrown: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void deleteTargetTerm(String targetTermId, DeleteTargetTermForm form) {
        // Remove SOCS, SOCResults, and course offerings
        DeleteTargetTermRolloverRunner runner = new DeleteTargetTermRolloverRunner();
        runner.setSocService(CourseOfferingManagementUtil.getSocService());
        runner.setCoService(CourseOfferingManagementUtil.getCourseOfferingService());
        runner.setTermId(targetTermId);
        runner.run();
    }

    // This method make service call to fetch soc rollover result infos for target term
    @Override
    public List<SocRolloverResultInfo> findRolloverByTerm(String termId) throws Exception{
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("targetTermId", termId));
        QueryByCriteria criteria = qbcBuilder.build();
        List<SocRolloverResultInfo> socRolloverResultInfos = CourseOfferingManagementUtil.getSocService().searchForSocRolloverResults(criteria, ContextUtils.createDefaultContextInfo());
        return socRolloverResultInfos;
    }

    @Override
    public String formatDate(Date date) {
        return DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_FORMATTER.format(date);
    }

    @Override
    public String formatDateAndTime(Date date) {
        if (date == null) {
            return "";
        }

        return DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMATTER.format(date);
    }

    @Override
    public String getTermDesc(String termId) {
        try {
            TermInfo termInfo = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm(termId, new ContextInfo());
            return termInfo.getDescr().getPlain();
        } catch (Exception e) {
            return "NO TERM DATA";
        }
    }
}