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
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingRolloverManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.form.DeleteTargetTermForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseofferingset.service.impl.DeleteTargetTermRolloverRunner;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingViewHelperServiceImpl extends ViewHelperServiceImpl implements CourseOfferingViewHelperService {
    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;
    private CourseOfferingSetService socService = null;
    private CourseService courseService = null;

    private static final Logger LOG = Logger.getLogger(CourseOfferingViewHelperServiceImpl.class);

    @Override
    public List<TermInfo> findTermByTermCode(String termCode) throws Exception {
        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        AcademicCalendarService acalService = _getAcalService();
        List<TermInfo> terms = acalService.searchForTerms(criteria, new ContextInfo());
        return terms;
    }

    private int _mainSocCount(List<String> socIds) {
        int mainSocCount = 0;
        try {
            for (String socId: socIds) {
                SocInfo socInfo = socService.getSoc(socId, new ContextInfo());
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
                SocInfo socInfo = socService.getSoc(socId, new ContextInfo());
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
        CourseOfferingSetService socService = _getSocService();
        try {
            List<String> socIds = socService.getSocIdsByTerm(termId, new ContextInfo());
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
    public SocInfo getMainSoc(String termId) {
        CourseOfferingSetService socService = _getSocService();
        try {
            List<String> socIds = socService.getSocIdsByTerm(termId, new ContextInfo());
            return _getUniqueMainSoc(socIds);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SocRolloverResultInfo performReverseRollover(String sourceTermId, String targetTermId, CourseOfferingRolloverManagementForm form) {
        CourseOfferingSetService socService = _getSocService();
        try {
            List<String> socIds = socService.getSocIdsByTerm(sourceTermId, new ContextInfo());
            if (socIds == null || socIds.isEmpty()) {
                form.setStatusField("No SOCS in source term");
                return null;
            } else if (socIds.size() > 1) {
                form.setStatusField("Too many SOCS in source term: " + socIds.size());
                return null;
            } else {
                String sourceSocId = socIds.get(0);
                List<String> resultIds = socService.getSocRolloverResultIdsBySourceSoc(sourceSocId, new ContextInfo());
                if (resultIds == null || resultIds.isEmpty()) {
                    form.setStatusField("No rollover results for source term");
                    return null;
                } else if (resultIds.size() > 1) {
                    form.setStatusField("Too many rollover results for source term: " + resultIds.size());
                    return null;
                } else {
                    String socResultId = resultIds.get(0);
                    List<String> options = new ArrayList<String>();
                    SocRolloverResultInfo info = socService.reverseRollover(socResultId, options, new ContextInfo());
                    return info;
                }
            }
        } catch (Exception e) {
            return null;       
        }
    }
    
    @Override
    public boolean performRollover(String sourceTermId, String targetTermId, CourseOfferingRolloverManagementForm form) {
        CourseOfferingSetService socService = _getSocService();
        try {
            ContextInfo context = ContextUtils.getContextInfo();
            List<String> socIds = socService.getSocIdsByTerm(sourceTermId, context);
            SocInfo socInfo = _getUniqueMainSoc(socIds);
            if (socInfo == null) {
                GlobalVariables.getMessageMap().putError("sourceTermCode", "error.rollover.sourceTerm.noSoc");
            } else {
                String sourceSocId = socInfo.getId();
                List<String> options = new ArrayList<String>();
                // Rollover now runs asynchronously. KSENROLL-1545
                // options.add(CourseOfferingSetServiceConstants.RUN_SYNCHRONOUSLY_OPTION_KEY);
                options.add(CourseOfferingSetServiceConstants.LOG_SUCCESSES_OPTION_KEY);
                socService.rolloverSoc(sourceSocId, targetTermId, options, context);
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
        runner.setSocService(_getSocService());
        runner.setCoService(_getCourseOfferingService());
        runner.setTermId(targetTermId);
        runner.run();
    }

    private AcademicCalendarService _getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                                                                                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    // This method make service call to fetch soc rollover result infos for target term
    @Override
    public List<SocRolloverResultInfo> findRolloverByTerm(String termId) throws Exception{
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("targetTermId", termId));
        QueryByCriteria criteria = qbcBuilder.build();
        List<SocRolloverResultInfo> socRolloverResultInfos = _getSocService().searchForSocRolloverResults(criteria, ContextUtils.createDefaultContextInfo());
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
            TermInfo termInfo = _getAcalService().getTerm(termId, new ContextInfo());
            return termInfo.getDescr().getPlain();
        } catch (Exception e) {
            return "NO TERM DATA";
        }
    }

    private CourseOfferingService _getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                         CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    private CourseOfferingSetService _getSocService() {
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                          CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    private CourseService _getCourseService() {
        if (courseService == null) {
            Object o = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "course",
                    "CourseService"));
            courseService = (CourseService) o;
        }
        return courseService;
    }
}
