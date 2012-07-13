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

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingRolloverManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.form.DeleteTargetTermForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseofferingset.service.impl.DeleteTargetTermRolloverRunner;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.lum.course.service.CourseService;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
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

    private CourseOfferingInfo _createCourseOffering(String termId) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextUtils.getContextInfo();

        CourseOfferingService coService = _getCourseOfferingService();
        CourseOfferingTransformer coTrans = new CourseOfferingTransformer();
        CourseService courseService = _getCourseService();
        CourseInfo courseInfo = null;
        try {
            courseInfo = courseService.getCourse("b509ad01-6ef3-44a4-8857-f5df8631f79e"); // Now CHEM 241
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        CourseOfferingInfo coInfo = new CourseOfferingInfo();
        List<String> copyOptions = new ArrayList<String>();
        copyOptions.add(CourseOfferingSetServiceConstants.NOT_GRADING_CREDIT_OPTION_KEY);
        // At this point
        coTrans.copyFromCanonical(courseInfo, coInfo, copyOptions, contextInfo);
        coInfo.setCourseOfferingTitle("Intro to Finite Math");
        coInfo.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        coInfo.setStateKey(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        coInfo.setMinimumEnrollment(5);
        coInfo.setMaximumEnrollment(40);
//        // info.setCourseId("REFERENCECOURSEMATH140");
//        coInfo.setCourseId("5aa58103-1644-40d8-8d9c-09f64e437b93"); // In the new DB
        coInfo.setCourseOfferingCode("CHEM241");
        coInfo.setCourseNumberSuffix("241");
        coInfo.setIsEvaluated(Boolean.TRUE);
        coInfo.setFinalExamType(FinalExam.STANDARD.toString());
        coInfo.setTermId(termId);
        coInfo.setIsFeeAtActivityOffering(Boolean.FALSE);
//        coInfo.setSubjectArea("MATH");
        coInfo.setInstructors(new ArrayList<OfferingInstructorInfo>());
        try {
            String courseId = coInfo.getCourseId();
            String infoTermId = coInfo.getTermId();
            String typeKey = coInfo.getTypeKey();
            List<String> options = new ArrayList<String>();

            CourseOfferingInfo result = coService.createCourseOffering(courseId, infoTermId, typeKey, coInfo,
                                                                       options, contextInfo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private FormatOfferingInfo _createFormatOffering(CourseOfferingInfo coInfo) {
        FormatOfferingInfo foInfo = new FormatOfferingInfo();
        foInfo.setName("DEVTEST_format");
        foInfo.setCourseOfferingId(coInfo.getId());
        foInfo.setFormatId("10f433ba-50e4-4037-a727-4ea7747c3e6b"); // Format for CHEM241
        foInfo.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        foInfo.setStateKey(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
        try {
            FormatOfferingInfo result =
                    coService.createFormatOffering(coInfo.getId(), foInfo.getFormatId(), foInfo.getTypeKey(), foInfo, new ContextInfo());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ActivityOfferingInfo _createActivityOffering(FormatOfferingInfo foInfo, CourseOfferingInfo coInfo) {
        ActivityOfferingInfo aoInfo = new ActivityOfferingInfo();
        aoInfo.setActivityId("f0072e90-3aed-4d9b-8a5a-e7efe317a686"); // Lecture for CHEM241
        aoInfo.setName("DEVTEST_activity");
        aoInfo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        aoInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
        aoInfo.setActivityCode("A");
        aoInfo.setCourseOfferingCode(coInfo.getCourseOfferingCode());
        aoInfo.setCourseOfferingTitle(coInfo.getCourseOfferingTitle());
        aoInfo.setFormatOfferingId(foInfo.getId());
        aoInfo.setMinimumEnrollment(5);
        aoInfo.setMaximumEnrollment(40);
        try {
            ActivityOfferingInfo result =
                    coService.createActivityOffering(foInfo.getId(), aoInfo.getActivityId(), aoInfo.getTypeKey(), aoInfo, new ContextInfo());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<CourseOfferingInfo> _getCourseOfferingsByTerm(String termId) {
        CourseOfferingSetService socService = _getSocService();
        try {
            List<String> socIds = socService.getSocIdsByTerm(termId, new ContextInfo());
            SocInfo soc = null;
            if (socIds != null && socIds.size() > 0) {
                soc = socService.getSoc(socIds.get(0), new ContextInfo());
            }
            List<String> coIds = socService.getCourseOfferingIdsBySoc(soc.getId(), new ContextInfo());
            List<CourseOfferingInfo> coInfos = new ArrayList<CourseOfferingInfo>();
            for (String id: coIds) {
                CourseOfferingInfo coInfo = coService.getCourseOffering(id, new ContextInfo());
                coInfos.add(coInfo);
            }
            return coInfos;
        } catch (Exception e) {
            return null;
        }
    }

    private void _deleteCourseOfferingById(String coId) {
        CourseOfferingService coService = _getCourseOfferingService();
        try {
            coService.deleteCourseOffering(coId, new ContextInfo());
        } catch (Exception e) {
        }
    }

    private void _verify(CourseOfferingInfo coInfo, FormatOfferingInfo foInfo, ActivityOfferingInfo aoInfo) {
        CourseOfferingService coService = _getCourseOfferingService();
        CourseOfferingInfo coFetched;
        FormatOfferingInfo foFetched;
        ActivityOfferingInfo aoFetched;
        try {
            coFetched = coService.getCourseOffering(coInfo.getId(), new ContextInfo());
            foFetched = coService.getFormatOffering(foInfo.getId(), new ContextInfo());
            aoFetched = coService.getActivityOffering(aoInfo.getId(), new ContextInfo());
            System.err.println("Success 1");
        } catch (Exception e) {
            System.err.println("Error");
        }
        System.err.println("Success");
    }
    @Override
    public SocInfo createSocCoFoAoForTerm(String termId, CourseOfferingRolloverManagementForm form) {
        CourseOfferingInfo coOffering;

        try{
            coOffering = _createCourseOffering(termId);
        }catch(Exception e){
            throw new RuntimeException("Failed to create Course Offering from Course",e);
        }

        if (coOffering == null) {
            form.setStatusField("createSocCoFoAoForTerm: Course offering not created");
        }
        FormatOfferingInfo foOffering = _createFormatOffering(coOffering);
        ActivityOfferingInfo aoOffering = _createActivityOffering(foOffering, coOffering);
        _verify(coOffering, foOffering, aoOffering);
        CourseOfferingSetService socService = _getSocService();
        // Create the SOC
        SocInfo socInfo = new SocInfo();
        socInfo.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        socInfo.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        socInfo.setTermId(termId);
        try {
            String socTermId = socInfo.getTermId();
            String typeKey = socInfo.getTypeKey();
            ContextInfo contextInfo = new ContextInfo();
            SocInfo result = socService.createSoc(socTermId, typeKey, socInfo, contextInfo);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    private String _deleteSocAndCourseOfferingsByTerm(String termId, String termCode) {
        CourseOfferingSetService socService = _getSocService();
        CourseOfferingService coService = _getCourseOfferingService();
        String mesg = "";
        try {
            List<String> socIds = socService.getSocIdsByTerm(termId, new ContextInfo());
            if (socIds == null || socIds.isEmpty()) {
                mesg = "NO_SOC";
            } 
            // String socId = socIds.get(0);
            // TODO: Ideally, use socService.getCourseOfferingsBySoc()
            List<String> coIds = coService.getCourseOfferingIdsByTerm(termId, Boolean.FALSE, new ContextInfo());
            if (coIds.size() > 3) {
                return "MANYCOS";
            } else {
                // TODO: Currently, deleteCourseOfferingsByTerm is not implemented in SOC service
                for (String coId: coIds) {
                    coService.deleteCourseOffering(coId, new ContextInfo());
                }
                if (!mesg.equals("NO_SOC")) {
                    String socId = socIds.get(0);
                    socService.deleteSoc(socId, new ContextInfo());
                }
            }
            return mesg;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
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
        CourseOfferingService coService = _getCourseOfferingService();
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
            ContextInfo context = ContextInfo.createDefaultContextInfo();
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
                SocInfo result = socService.rolloverSoc(sourceSocId, targetTermId, options, context);
                return true;
            }
        } catch (Exception e) {
            System.err.println("--------- rollover exception in performRollover [START]");
            e.printStackTrace();
            System.err.println("--------- rollover exception in performRollover [END]");
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

    @Override
    public boolean cleanSourceTerm(String sourceTermId, CourseOfferingRolloverManagementForm form) {
     // Remove SOCS, SOCResults, and course offerings
        CourseOfferingSetService socService = _getSocService();
        CourseOfferingService coService = _getCourseOfferingService();

        try {
            // Delete course offerings since they will be regenerated
            List<String> coIds = coService.getCourseOfferingIdsByTerm(sourceTermId, Boolean.TRUE, new ContextInfo());
            if (coIds != null) {
                if (coIds.size() > 3) {
                    // Probably deleting wrong term
                    form.setStatusField("Too many course offerings to delete: " + coIds.size());
                    return false;
                } else {
                    // Delete course offerings
                    for (String coId : coIds) {
                        List<FormatOfferingInfo> foInfos =
                                coService.getFormatOfferingsByCourseOffering(coId, new ContextInfo());
                        for (FormatOfferingInfo foInfo: foInfos) {
                            String foId = foInfo.getId();
                            // Delete activity offerings
                            List<ActivityOfferingInfo> aoInfos =
                                    coService.getActivityOfferingsByFormatOffering(foId, new ContextInfo());
                            for (ActivityOfferingInfo aoInfo: aoInfos) {
                                coService.deleteActivityOffering(aoInfo.getId(), new ContextInfo());
                            }
                            // Delete format offerings first
                            coService.deleteFormatOffering(foInfo.getId(), new ContextInfo());
                        }
                        coService.deleteCourseOffering(coId, new ContextInfo());
                    }
                }
            }
            // Then, SocRolloverItems
            List<String> socIds = socService.getSocIdsByTerm(sourceTermId, new ContextInfo());
            if (socIds != null) {
                ContextInfo contextInfo = new ContextInfo();
                for (String sourceSocId: socIds) {
                    SocInfo socInfo = socService.getSoc(sourceSocId, new ContextInfo());
                    if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                        // Only deal with main SOCs for now
                        List<String> resultIds = socService.getSocRolloverResultIdsBySourceSoc(sourceSocId, contextInfo);
                        if (resultIds != null) {
                            for (String resultId: resultIds) {
                                List<SocRolloverResultItemInfo> items = socService.getSocRolloverResultItemsByResultId(resultId, contextInfo);
                                // Items deleted here
                                for (SocRolloverResultItemInfo item: items) {
                                    socService.deleteSocRolloverResultItem(item.getId(), contextInfo);
                                }
                                // Results deleted here
                                socService.deleteSocRolloverResult(resultId, contextInfo);
                            }
                        }
                        // Finally, delete the SOC
                        socService.deleteSoc(sourceSocId, contextInfo);
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            form.setStatusField("Exception in cleanTargetTerm");
            return false;
        }
    }

    private List<SocInfo> _getSocsByTerm(String termId) {
        CourseOfferingSetService socService = _getSocService();
        try {
            List<String> socIds = socService.getSocIdsByTerm(termId, new ContextInfo());
            List<SocInfo> socInfos = new ArrayList<SocInfo>();
            for (String id: socIds) {
                SocInfo info = socService.getSoc(id, new ContextInfo());
                socInfos.add(info);
            }
            return socInfos;
        } catch (Exception e) {
            return null;
        }
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
        List<SocRolloverResultInfo> socRolloverResultInfos = new ArrayList<SocRolloverResultInfo>();
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("targetTermId", termId));
        ContextInfo contextInfo = new ContextInfo();
        QueryByCriteria criteria = qbcBuilder.build();
        socRolloverResultInfos = _getSocService().searchForSocRolloverResults(criteria, contextInfo);
        return socRolloverResultInfos;
    }

    @Override
    public String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMMMM d, yyyy");
        String startDateStr = format.format(date);
        return startDateStr;
    }

    @Override
    public String formatDateAndTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("MMMMM d, yyyy, h:mm a");
        String startDateStr = format.format(date);
        return startDateStr;
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
