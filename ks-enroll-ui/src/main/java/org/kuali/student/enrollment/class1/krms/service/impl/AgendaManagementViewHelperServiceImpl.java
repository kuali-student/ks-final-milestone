package org.kuali.student.enrollment.class1.krms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.NaturalLanguageTemplateBoService;
import org.kuali.rice.krms.impl.repository.NaturalLanguageUsageBoService;
import org.kuali.rice.krms.impl.repository.TypeTypeRelationBoService;
import org.kuali.student.enrollment.class1.krms.form.AgendaManagementForm;
import org.kuali.student.enrollment.class1.krms.keyvalues.RequisiteAgendaTypeKeyValues;
import org.kuali.student.enrollment.class1.krms.keyvalues.RuleTypeKeyValues;
import org.kuali.student.enrollment.class1.krms.service.AgendaManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.krms.naturallanguage.util.KsKrmsRepositoryServiceLocator;
import org.kuali.student.krms.util.KSKRMSConstants;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AgendaManagementViewHelperServiceImpl extends KSViewHelperServiceImpl implements AgendaManagementViewHelperService {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AgendaManagementViewHelperServiceImpl.class);

    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;
    private SearchService searchService = null;

    private CourseService courseService;

    @Override
    public void performInitialization(View view, Object model) {
        AgendaManagementForm form = (AgendaManagementForm) model;

        String agendaId = getAgendaCourseTypeId();
        List<KeyValue> agendaTypeIds = setAgendaTypes(form, agendaId);
        setRuleTypes(form, agendaTypeIds);

        super.performInitialization(view, model);

    }

    private String getAgendaCourseTypeId() {
        KrmsTypeDefinition krmsTypeDefinition = getKrmsTypeRepositoryService().getTypeByName(KSKRMSConstants.KS_SYS_NAMESPACE, KSKRMSConstants.COURSE_AGENDA_TYPE_ID);
        String agendaId = krmsTypeDefinition.getId();
        return agendaId;
    }

    private List<KeyValue> setAgendaTypes(AgendaManagementForm form, String agendaId) {
//        agendaId = form.getAgenda().getId();
        RequisiteAgendaTypeKeyValues requisiteAgendaTypeKeyValues = new RequisiteAgendaTypeKeyValues();
        List<KeyValue> agendaTypes = requisiteAgendaTypeKeyValues.getKeyValues(agendaId);
        for(KeyValue agendaType : agendaTypes) {
            if(agendaType.getValue().equals(KSKRMSConstants.CONTEXT_ENROLLMENT_ELIGIBILITY)) {
                form.setEnrollmentEligibility(agendaType.getValue());
            } else if(agendaType.getValue().equals(KSKRMSConstants.CONTEXT_CREDIT_CONTRAINTS)) {
                form.setCreditConstraints(agendaType.getValue());
            }
        }
        return agendaTypes;
    }

    private void setRuleTypes(AgendaManagementForm form, List<KeyValue> agendaTypeIds) {
        RuleTypeKeyValues ruleTypeKeyValues = new RuleTypeKeyValues();
        Map<String, Map<String, String>> keyValues = ruleTypeKeyValues.getKeyValues(agendaTypeIds);
        Map<String, String> ruleTypes = keyValues.get("ruleTypes");
        Map<String, String> ruleInstructions = keyValues.get("ruleInstructions");
        List<String> ruleTypeList = new ArrayList<String>(ruleTypes.values());

        for(String ruleType: ruleTypeList) {
            if(ruleType.equals(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY)) {
                form.setStudentEligAndPrereq(ruleType);
                form.setStudentEligAndPrereqInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_CORE_REQUISITE)) {
                form.setCorequisites(ruleType);
                form.setCorequisitesInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_RECOMMENDED_PREPARATION)) {
                form.setRecommendedPreparation(ruleType);
                form.setRecommendedPreparationInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_ANTI_REQUISITE)) {
                form.setAntirequisite(ruleType);
                form.setAntirequisiteInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_COURSE_RESTRICTS)) {
                form.setRestrictCredits(ruleType);
                form.setRestrictCreditsInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_REPEATED_CREDITS)) {
                form.setRepeatableCredit(ruleType);
                form.setRepeatableCreditInstruction(ruleInstructions.get(ruleType));
            }
        }
    }

    public List<TermInfo> findTermByTermCode(String termCode) throws Exception {
        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        // TODO: How does one get rid of hard-coding "atpCode"?
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        AcademicCalendarService acalService = _getAcalService();
        return acalService.searchForTerms(criteria, createContextInfo());
    }

    public void loadAgendasByTermAndCourseCode(String termId, String courseCode, AgendaManagementForm form) throws Exception {

        ContextInfo contextInfo = createContextInfo();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        SearchResultInfo searchResult = getSearchService().search(searchRequest, contextInfo);

        List<CourseOfferingListSectionWrapper>  coListWrapperList = new ArrayList<CourseOfferingListSectionWrapper>();
        for (SearchResultRowInfo row : searchResult.getRows()) {
            CourseOfferingListSectionWrapper coListWrapper = new CourseOfferingListSectionWrapper();

            for(SearchResultCellInfo cellInfo : row.getCells()){

                String value = StringUtils.EMPTY;
                if(cellInfo.getValue() != null)  {
                    value = new String(cellInfo.getValue());
                }

                if(CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCode(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.DESC.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingDesc(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.STATE.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingStateKey(value);
                    coListWrapper.setCourseOfferingStateDisplay(getStateInfo(value).getName());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCreditOptionKey(value);
                    CourseOfferingTransformer courseOfferingTransformer = new CourseOfferingTransformer();
                    coListWrapper.setCourseOfferingCreditOptionDisplay(courseOfferingTransformer.getCreditCount(value, "", null, null, contextInfo));
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingId(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.SUBJECT_AREA.equals(cellInfo.getKey())){
                    coListWrapper.setSubjectArea(value);
                }

            }
            coListWrapperList.add(coListWrapper);
        }

        form.setCourseOfferingResultList(Collections.unmodifiableList(coListWrapperList));

    }

    private AcademicCalendarService _getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    public CourseOfferingService getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    public CourseService getCourseService() {
        if (courseService == null){
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    protected SearchService getSearchService() {
        if(searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    private NaturalLanguageUsageBoService getNaturalLanguageUsageBoService() {
        return KsKrmsRepositoryServiceLocator.getNaturalLanguageUsageBoService();
    }

    private NaturalLanguageTemplateBoService getNaturalLanguageTemplateBoService() {
        return KsKrmsRepositoryServiceLocator.getNaturalLanguageTemplateBoService();
    }

    private KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

}
