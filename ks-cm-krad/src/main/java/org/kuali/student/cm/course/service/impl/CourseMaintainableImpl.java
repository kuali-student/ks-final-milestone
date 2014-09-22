/**
 * Copyright 2005-2013 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.cm.course.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.util.LifecycleElement;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.AgendaTypeInfo;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.ActivityInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseCreateUnitsContentOwner;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.FormatInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoCategoryInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayWrapperModel;
import org.kuali.student.cm.course.form.wrapper.OrganizationInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.OutcomeReviewSection;
import org.kuali.student.cm.course.form.wrapper.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.ReviewProposalDisplay;
import org.kuali.student.cm.course.form.wrapper.SubjectCodeWrapper;
import org.kuali.student.cm.course.service.CourseCopyHelper;
import org.kuali.student.cm.course.service.CourseMaintainable;
import org.kuali.student.cm.course.service.util.CourseCodeSearchUtil;
import org.kuali.student.cm.course.service.util.LoCategorySearchUtil;
import org.kuali.student.cm.course.service.util.OrganizationSearchUtil;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.service.ProposalMaintainable;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.lum.lu.ui.course.keyvalues.OrgsBySubjectCodeValuesFinder;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.lum.lu.ui.krms.util.CluInformationHelper;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.r1.core.personsearch.service.impl.QuickViewByGivenName;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.AttributeHelper;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.EnumerationManagementServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.CourseVariationInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.kuali.student.cm.common.util.CMUtils.getCluService;
import static org.kuali.student.cm.course.util.CourseProposalUtil.isCurrentVersionOfCourse;

/**
 * Base view helper service for both create and edit course info presentations.
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class CourseMaintainableImpl extends CommonCourseMaintainableImpl implements CourseMaintainable, RuleViewHelperService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseMaintainableImpl.class);

    protected transient static final String CREDIT_COURSE_CLU_TYPE_KEY = "kuali.lu.typeKey.CreditCourse";

    private static final long serialVersionUID = 1338662637708570500L;

    private transient OrganizationService organizationService;

    private transient CluService cluService;

    private transient LearningObjectiveService learningObjectiveService;

    private transient TypeService typeService;

    private transient LRCService lrcService;

    private transient EnumerationManagementService enumerationManagementService;

    private PersonService personService;

    private CluInformationHelper cluInfoHelper;

    private CourseCopyHelper courseCopyHelper;

    /**
     * Method called when queryMethodToCall is executed for Administering Organizations in order to suggest back to the user an Administering Organization
     *
     * @param organizationName
     * @see org.kuali.student.cm.course.service.CourseMaintainable#getOrganizationsForSuggest(String)
     */
    public List<OrganizationInfoWrapper> getOrganizationsForSuggest(final String organizationName) {
        return OrganizationSearchUtil.searchForOrganizations(organizationName, getOrganizationService());
    }

    protected SearchResultInfo getInstructorsSearchResult(List<SearchParamInfo> queryParamValueList) throws Exception {

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(QuickViewByGivenName.SEARCH_TYPE);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn(QuickViewByGivenName.DISPLAY_NAME_RESULT);

        SearchResultInfo searchResult = null;
        searchResult = getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

        return searchResult;
    }

    private List<CluInstructorInfoWrapper> getInstructorsFromSearchResult(SearchResultInfo searchResult) {

        List<CluInstructorInfoWrapper> cluInstructorInfoDisplays = new ArrayList<CluInstructorInfoWrapper>();
        for (SearchResultRowInfo result : searchResult.getRows()) {
            List<SearchResultCellInfo> cells = result.getCells();
            CluInstructorInfoWrapper cluInstructorInfoDisplay = new CluInstructorInfoWrapper();
            for (SearchResultCellInfo cell : cells) {
                if (QuickViewByGivenName.GIVEN_NAME_RESULT.equals(cell.getKey())) {
                    cluInstructorInfoDisplay.setGivenName(cell.getValue());
                } else if (QuickViewByGivenName.PERSON_ID_RESULT.equals(cell.getKey())) {
                    cluInstructorInfoDisplay.setPersonId(cell.getValue());
                } else if (QuickViewByGivenName.ENTITY_ID_RESULT.equals(cell.getKey())) {
                    cluInstructorInfoDisplay.setId(cell.getValue());
                } else if (QuickViewByGivenName.PRINCIPAL_NAME_RESULT.equals(cell.getKey())) {
                    cluInstructorInfoDisplay.setPrincipalName(cell.getValue());
                } else if (QuickViewByGivenName.DISPLAY_NAME_RESULT.equals(cell.getKey())) {
                    cluInstructorInfoDisplay.setDisplayName(cell.getValue());
                }
            }
            cluInstructorInfoDisplays.add(cluInstructorInfoDisplay);
        }
        return cluInstructorInfoDisplays;
    }

    public List<CluInstructorInfoWrapper> getInstructorsById(String id) {
        List<CluInstructorInfoWrapper> cluInstructorInfoDisplays = new ArrayList<CluInstructorInfoWrapper>();
        SearchResultInfo searchResult = null;
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo displayNameParam = new SearchParamInfo();

        try {
            displayNameParam.setKey(QuickViewByGivenName.ID_PARAM);
            displayNameParam.getValues().add(id);
            queryParamValueList.add(displayNameParam);

            searchResult = getInstructorsSearchResult(queryParamValueList);
            cluInstructorInfoDisplays = getInstructorsFromSearchResult(searchResult);
        } catch (Exception e) {
            throw new RuntimeException("No Instructor found for given ID :" + id, e);
        }
        return cluInstructorInfoDisplays;
    }


    /**
     * @see org.kuali.student.cm.course.service.CourseMaintainable#getInstructorsForSuggest(String)
     */
    public List<CluInstructorInfoWrapper> getInstructorsForSuggest(
            String instructorName) {
        List<CluInstructorInfoWrapper> cluInstructorInfoDisplays = new ArrayList<CluInstructorInfoWrapper>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo displayNameParam = new SearchParamInfo();
        SearchResultInfo searchResult = null;
        try {
            displayNameParam.setKey(QuickViewByGivenName.NAME_PARAM);
            displayNameParam.getValues().add(instructorName.toUpperCase());
            queryParamValueList.add(displayNameParam);
            searchResult = getInstructorsSearchResult(queryParamValueList);
            cluInstructorInfoDisplays = getInstructorsFromSearchResult(searchResult);
        } catch (Exception e) {
            LOG.error("An error occurred in the getInstructorsForSuggest method", e);
        }
        return cluInstructorInfoDisplays;
    }


    public LoDisplayWrapperModel getLoDisplayWrapperModel() {
        if (loDisplayWrapperModel == null) {
            loDisplayWrapperModel = new LoDisplayWrapperModel();
        }
        return loDisplayWrapperModel;
    }

    public void setLoDisplayWrapperModel(LoDisplayWrapperModel loDisplayWrapperModel) {
        this.loDisplayWrapperModel = loDisplayWrapperModel;
    }

    private LoDisplayWrapperModel loDisplayWrapperModel;

    /**
     * @see org.kuali.student.cm.course.service.CourseMaintainable#getSubjectCodesForSuggest(String)
     */
    public List<SubjectCodeWrapper> getSubjectCodesForSuggest(String subjectCode) {
        List<SubjectCodeWrapper> retrievedCodes = new ArrayList<SubjectCodeWrapper>();

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();

        SearchParamInfo codeParam = new SearchParamInfo();
        codeParam.setKey(CourseServiceConstants.SUBJECTCODE_CODE_PARAM);
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(subjectCode);
        codeParam.setValues(codeValues);

        queryParamValueList.add(codeParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(CourseServiceConstants.SUBJECTCODE_GENERIC_SEARCH);
        searchRequest.setParams(queryParamValueList);

        SearchResultInfo searchResult = null;
        try {
            searchResult = getSubjectCodeService().search(searchRequest, ContextUtils.createDefaultContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                String id = "";
                String code = "";
                for (SearchResultCellInfo cell : cells) {
                    if (CourseServiceConstants.SUBJECTCODE_ID_RESULT.equals(cell.getKey())) {
                        id = cell.getValue();
                    } else if (CourseServiceConstants.SUBJECTCODE_CODE_RESULT.equals(cell.getKey())) {
                        code = cell.getValue();
                    }
                }
                retrievedCodes.add(new SubjectCodeWrapper(id, code));
            }
        } catch (Exception e) {
            LOG.error("An error occurred retrieving the SubjectCodeDisplay", e);
        }

        return retrievedCodes;
    }

    @Override
    public List<CourseJointInfoWrapper> searchForJointOfferingCourses(String courseNumber) {
        return CourseCodeSearchUtil.searchForCourseJointInfos(courseNumber, getCluService());
    }

    @Override
    public List<LoCategoryInfoWrapper> searchForLoCategories(String categoryName) {
        return LoCategorySearchUtil.searchForLoCategories(categoryName, getLearningObjectiveService());
    }


    @SuppressWarnings("deprecation")
    @Override
    protected boolean performAddLineValidation(ViewModel viewModel, Object newLine, String collectionId,
                                               String collectionPath) {
        boolean returnValue = super.performAddLineValidation(viewModel, newLine, collectionId, collectionPath);
        // if return value is false then something failed in the parent class so just return false
        if (!returnValue) {
            return false;
        }

        if (newLine instanceof CourseCreateUnitsContentOwner) {
            MaintenanceDocumentForm modelForm = (MaintenanceDocumentForm) viewModel;
            CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) modelForm.getDocument().getNewMaintainableObject().getDataObject();

            for (CourseCreateUnitsContentOwner unitsContentOwner : courseInfoWrapper.getUnitsContentOwner()) {
                if (StringUtils.isBlank(unitsContentOwner.getOrgId())) {
                    return false;
                }
            }
        } else if (newLine instanceof LoCategoryInfo) {
            LoCategoryInfo loCategoryInfo = (LoCategoryInfo) newLine;

            boolean isCategoryAlreadyExist = false;
            if (StringUtils.isNotBlank(loCategoryInfo.getName())) {
                String[] categoryItems = loCategoryInfo.getName().split("-");
                //Check if Category is an existing one or a new.
                if (categoryItems != null) {
                    String categoryType = null;
                    String categoryName = categoryItems[0].trim();
                    if (categoryItems.length == 2) {
                        categoryType = categoryItems[1].trim();
                    }
                    if (StringUtils.isNotBlank(categoryName)) {
                        List<LoCategoryInfoWrapper> loCategoryInfoWrapper = searchForLoCategories(categoryName);
                        if (loCategoryInfoWrapper != null && !loCategoryInfoWrapper.isEmpty()) {
                            //Check against the each existing category and its type
                            for (LoCategoryInfoWrapper loCategoryInfoWrap : loCategoryInfoWrapper) {
                                if (loCategoryInfoWrap.getName().equals(categoryName) && loCategoryInfoWrap.getTypeName().equals(categoryType)) {
                                    //get the complete category record
                                    LoCategoryInfo origLoCat = (LoCategoryInfo) loCategoryInfoWrap;
                                    BeanUtils.copyProperties(origLoCat, loCategoryInfo);
                                    isCategoryAlreadyExist = true;
                                    break;
                                }
                            }
                        }

                        if (!isCategoryAlreadyExist) {
                            //if category doesn't exist then create newly.
                            loCategoryInfo.setStateKey(CurriculumManagementConstants.STATE_KEY_ACTIVE);
                            loCategoryInfo.setLoRepositoryKey(CurriculumManagementConstants.KUALI_LO_REPOSITORY_KEY_SINGLE_USE);
                            try {
                                LoCategoryInfo savedLoCat = getLearningObjectiveService().createLoCategory(loCategoryInfo.getTypeKey(), loCategoryInfo,
                                        ContextUtils.createDefaultContextInfo());
                                BeanUtils.copyProperties(savedLoCat, loCategoryInfo);
                            } catch (DataValidationErrorException e) {
                                LOG.error("An error occurred while trying to create a duplicate Learning Objective Category", e);
                            } catch (Exception e) {
                                LOG.error("An error occurred while trying to create a new Learning Objective Category", e);
                            }
                        }

                        try {
                            //Get the type info
                            TypeInfo typeInfo = getLearningObjectiveService().getLoCategoryType(loCategoryInfo.getTypeKey(), ContextUtils.createDefaultContextInfo());
                            loCategoryInfo.setName((new StringBuilder().append(loCategoryInfo.getName()).append(" - ").append(typeInfo.getName()).toString()));
                        } catch (Exception e) {
                            LOG.error("An error occurred while retrieving the LoCategoryType", e);
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void processMultipleValueLookupResults(ViewModel model, String collectionId, String collectionPath,
                                                  String multiValueReturnFields, String lookupResultValues) {

        super.processMultipleValueLookupResults(model, collectionId, collectionPath, multiValueReturnFields, lookupResultValues);

        /**
         * If it;s LO lookup, rearrange all the line actions for LOs (indent,move etc)
         */
        if (StringUtils.equals(collectionId, "LearningObjective-CollectionSection")) {
            setDataObject(((MaintenanceDocumentForm) model).getDocument().getNewMaintainableObject().getDataObject());
            setLOActions();
        }
    }

    @Override
    public List<AgendaEditor> getAgendasForRef(String discriminatorType, String refObjectId) {
        // Initialize new array lists.
        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();
        List<AgendaEditor> sortedAgendas = new ArrayList<AgendaEditor>();

        if (refObjectId != null) {
            // Get the list of existing agendas
            List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(discriminatorType, refObjectId);
            for (ReferenceObjectBinding referenceObjectBinding : refObjectsBindings) {
                agendas.add(this.getAgendaEditor(referenceObjectBinding.getKrmsObjectId()));
            }
        }

        // Lookup existing agenda by type
        for (AgendaTypeInfo agendaTypeInfo : this.getTypeRelationships()) {
            AgendaEditor agenda = null;
            for (AgendaEditor existingAgenda : agendas) {
                if (existingAgenda.getTypeId().equals(agendaTypeInfo.getId())) {
                    agenda = existingAgenda;
                    break;
                }
            }
            if (agenda == null) {
                agenda = new AgendaEditor();
                agenda.setTypeId(agendaTypeInfo.getId());
            }

            agenda.setAgendaTypeInfo(agendaTypeInfo);
            agenda.setRuleEditors(this.getRulesForAgendas(agenda));
            sortedAgendas.add(agenda);
        }

        return sortedAgendas;
    }

    protected LearningObjectiveService getLearningObjectiveService() {
        if (learningObjectiveService == null) {
            learningObjectiveService = GlobalResourceLoader.getService(new QName(
                    LearningObjectiveServiceConstants.NAMESPACE, LearningObjectiveService.class.getSimpleName()));
        }
        return learningObjectiveService;
    }

    protected OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader
                    .getService(new QName("http://student.kuali.org/wsdl/organization", "OrganizationService"));
        }
        return organizationService;
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        if (courseInfoWrapper.isProposalDataUsed()) {
            super.processAfterNew(document, requestParameters);
        }

        if (!courseInfoWrapper.isDisableCourseDefaulting()) {
            courseInfoWrapper.getCourseInfo().setStateKey(DtoConstants.STATE_DRAFT);
            courseInfoWrapper.getCourseInfo().setEffectiveDate(new java.util.Date());
            courseInfoWrapper.getCourseInfo().setTypeKey(CREDIT_COURSE_CLU_TYPE_KEY);
        }

        // We can actually get this from the workflow document initiator id. It doesn't need to be stored in the form.
        courseInfoWrapper.setUserId(ContextUtils.createDefaultContextInfo().getPrincipalId());

        // Initialize Course Requisites
        courseInfoWrapper.setNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);
        courseInfoWrapper.setRefDiscriminatorType(CourseServiceConstants.REF_OBJECT_URI_COURSE);
        courseInfoWrapper.setRefObjectId(courseInfoWrapper.getCourseInfo().getId());
        courseInfoWrapper.setAgendas(getAgendasForRef(courseInfoWrapper.getRefDiscriminatorType(), courseInfoWrapper.getRefObjectId()));

        courseInfoWrapper.setLastUpdated(CurriculumManagementConstants.CM_DATE_FORMATTER.format(new DateTime()));

        // Initialize Curriculum Oversight if it hasn't already been.
        if (courseInfoWrapper.getCourseInfo().getUnitsContentOwner() == null) {
            courseInfoWrapper.getCourseInfo().setUnitsContentOwner(new ArrayList<String>());
        }

        CourseCreateUnitsContentOwner newCourseCreateUnitsContentOwner = new CourseCreateUnitsContentOwner();
        newCourseCreateUnitsContentOwner.getRenderHelper().setNewRow(true);
        courseInfoWrapper.getUnitsContentOwner().add(newCourseCreateUnitsContentOwner);

        // Initialize Crosslistings if it hasn't already been.
        if (courseInfoWrapper.getCourseInfo().getCrossListings().isEmpty()) {
            List<CourseCrossListingInfo> crossListingInfoList = courseInfoWrapper.getCourseInfo().getCrossListings();
            crossListingInfoList.add(new CourseCrossListingInfo());
            courseInfoWrapper.getCourseInfo().setCrossListings(crossListingInfoList);
        }
        // Initialize Joint Offerings if it hasn't already been.
        if (courseInfoWrapper.getCourseJointWrappers() == null || courseInfoWrapper.getCourseJointWrappers().isEmpty()) {
            List<CourseJointInfoWrapper> courseJointInfoList = courseInfoWrapper.getCourseJointWrappers();
            courseJointInfoList.add(new CourseJointInfoWrapper());
            courseInfoWrapper.setCourseJointWrappers(courseJointInfoList);
        }
        // Initialize Variations if it hasn't already been.
        if (courseInfoWrapper.getCourseInfo().getVariations().isEmpty()) {
            List<CourseVariationInfo> courseVariationInfoList = courseInfoWrapper.getCourseInfo().getVariations();
            courseVariationInfoList.add(new CourseVariationInfo());
            courseInfoWrapper.getCourseInfo().setVariations(courseVariationInfoList);
        }
        //Initialize formats/activities
        initializeFormat(courseInfoWrapper);

        //Initialize outcomes
        initializeOutcome(courseInfoWrapper);


        // Administering Organizations
        if (courseInfoWrapper.getAdministeringOrganizations().isEmpty()) {
            courseInfoWrapper.getAdministeringOrganizations().add(new OrganizationInfoWrapper());
        }

        // Initialize Instructors
        if (courseInfoWrapper.getInstructorWrappers().isEmpty()) {
            courseInfoWrapper.getInstructorWrappers().add(new CluInstructorInfoWrapper());
        }

    }

    /**
     * This method adds an empty Format if the format list is empty. This is needed to display a
     * blank row at format section at the ui.
     *
     * @param dataObject
     */
    protected void initializeFormat(CourseInfoWrapper dataObject) {
        if (dataObject.getFormats().isEmpty()) {
            FormatInfo format = new FormatInfo();
            ActivityInfo activity = new ActivityInfo();
            format.getActivities().add(activity);
            dataObject.getFormats().add(format);
        }
    }

    protected void initializeOutcome(CourseInfoWrapper dataObject) {
        if (dataObject.getCreditOptionWrappers().isEmpty()) {
            ResultValuesGroupInfoWrapper resultValuesGroupInfoWrapper = new ResultValuesGroupInfoWrapper();
            dataObject.getCreditOptionWrappers().add(resultValuesGroupInfoWrapper);
        }
    }


    @Override
    public void processCollectionAddBlankLine(ViewModel model, String collectionId, String collectionPath) {

        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
        MaintenanceDocument document = maintenanceForm.getDocument();

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) document.getNewMaintainableObject().getDataObject();

        if (StringUtils.endsWith(collectionPath, "unitsContentOwner")) {

            //Before adding a new row, just make sure all the existing rows are not editable.
            for (CourseCreateUnitsContentOwner existing : courseInfoWrapper.getUnitsContentOwner()) {
                existing.getRenderHelper().setNewRow(false);
                if (!existing.isUserEntered()) {
                    populateOrgName(courseInfoWrapper.getCourseInfo().getSubjectArea(), existing);
                }
            }

            OrgsBySubjectCodeValuesFinder optionsFinder = new OrgsBySubjectCodeValuesFinder();
            List<KeyValue> availableOptions = optionsFinder.getAvailableOrgs(courseInfoWrapper);

            if (!availableOptions.isEmpty()) {
                CourseCreateUnitsContentOwner newCourseCreateUnitsContentOwner = new CourseCreateUnitsContentOwner();
                newCourseCreateUnitsContentOwner.getRenderHelper().setNewRow(true);

                courseInfoWrapper.getUnitsContentOwner().add(newCourseCreateUnitsContentOwner);
            }

            return;
        } else if (StringUtils.endsWith(collectionPath, "formats")) {
            FormatInfo format = new FormatInfo();
            ActivityInfo activity = new ActivityInfo();
            format.getActivities().add(activity);
            courseInfoWrapper.getFormats().add(format);
            return;
        }

        super.processCollectionAddBlankLine(model, collectionId, collectionPath);
    }

    /**
     * Updates the ReviewProposalDisplay object for the Course Proposal and refreshes remote data elements based on passed in @shouldRepopulateRemoteData param
     *
     * @param shouldRepopulateRemoteData identifies whether remote data elements like Collaborators should be refreshed
     */
    @Override
    protected void updateReview(ProposalElementsWrapper proposalElementsWrapper, boolean shouldRepopulateRemoteData) {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) proposalElementsWrapper;
        CourseInfo savedCourseInfo = courseInfoWrapper.getCourseInfo();

        // Update course section
        ReviewProposalDisplay reviewData = courseInfoWrapper.getReviewProposalDisplay();
        if (reviewData == null) {
            reviewData = new ReviewProposalDisplay();
            courseInfoWrapper.setReviewProposalDisplay(reviewData);
        }
        if (StringUtils.isBlank(courseInfoWrapper.getPreviousSubjectCode()) && StringUtils.isNotBlank(savedCourseInfo.getSubjectArea())) {
            courseInfoWrapper.setPreviousSubjectCode(savedCourseInfo.getSubjectArea());
        }

        reviewData.getCourseSection().setCourseTitle(savedCourseInfo.getCourseTitle());
        reviewData.getCourseSection().setTranscriptTitle(savedCourseInfo.getTranscriptTitle());
        reviewData.getCourseSection().setSubjectArea(savedCourseInfo.getSubjectArea());
        reviewData.getCourseSection().setCourseNumberSuffix(savedCourseInfo.getCourseNumberSuffix());

        if (StringUtils.isNotBlank(courseInfoWrapper.getProposalInfo().getId())){
            Date updateTime = courseInfoWrapper.getProposalInfo().getMeta().getUpdateTime();
            if (updateTime != null){
                courseInfoWrapper.setLastUpdated(CurriculumManagementConstants.CM_DATE_FORMATTER.format(updateTime));
            }
        }

        if (savedCourseInfo.getDescr() != null) {
            reviewData.getCourseSection().setDescription(savedCourseInfo.getDescr().getPlain());
        }

        reviewData.getCourseSection().getInstructors().clear();
        for (CluInstructorInfoWrapper insturctorWrappers : courseInfoWrapper.getInstructorWrappers()) {
            reviewData.getCourseSection().getInstructors().add(insturctorWrappers.getDisplayName());
        }

        reviewData.getCourseSection().getCrossListings().clear();
        if (!savedCourseInfo.getCrossListings().isEmpty()) {
            for (CourseCrossListingInfo crossListingInfo : savedCourseInfo.getCrossListings()) {
                reviewData.getCourseSection().getCrossListings().add(crossListingInfo.getCode());
            }
        }

        reviewData.getCourseSection().getJointlyOfferedCourses().clear();
        if (!savedCourseInfo.getJoints().isEmpty()) {
            for (CourseJointInfo jointInfo : savedCourseInfo.getJoints()) {
                reviewData.getCourseSection().getJointlyOfferedCourses().add(jointInfo.getSubjectArea() + jointInfo.getCourseNumberSuffix());
            }
        }

        reviewData.getCourseSection().getVariations().clear();
        if (!savedCourseInfo.getVariations().isEmpty()) {
            for (CourseVariationInfo variationInfo : savedCourseInfo.getVariations()) {
                if (variationInfo.getVariationCode() == null && variationInfo.getVariationTitle() == null) {
                    reviewData.getCourseSection().getVariations().add("");
                } else if (variationInfo.getVariationCode() == null) {
                    reviewData.getCourseSection().getVariations().add("" + ": " + variationInfo.getVariationTitle());
                } else {
                    reviewData.getCourseSection().getVariations().add(variationInfo.getVariationCode() + ": " + variationInfo.getVariationTitle());
                }
            }
        }

        // Update governance section
        reviewData.getGovernanceSection().getCampusLocations().clear();
        reviewData.getGovernanceSection().getCampusLocations().addAll(updateCampusLocations(savedCourseInfo.getCampusLocations()));
        reviewData.getGovernanceSection().setCurriculumOversight(
                buildCurriculumOversightList(savedCourseInfo.getSubjectArea(),savedCourseInfo.getUnitsContentOwner()));

        reviewData.getGovernanceSection().getAdministeringOrganization().clear();
        for (OrganizationInfoWrapper organizationInfoWrapper : courseInfoWrapper.getAdministeringOrganizations()) {
            if (organizationInfoWrapper.isUserEntered()) {
                reviewData.getGovernanceSection().getAdministeringOrganization().add(organizationInfoWrapper.getOrganizationName());
            }
        }

        // update course logistics section
        reviewData.getCourseLogisticsSection().getTerms().clear();
        try {
            for (String termType : savedCourseInfo.getTermsOffered()) {
                TypeInfo term = getTypeService().getType(termType, ContextUtils.createDefaultContextInfo());
                reviewData.getCourseLogisticsSection().getTerms().add(term.getName());
            }
        } catch (Exception e) {
            throw new RiceIllegalStateException(e);
        }

        if (savedCourseInfo.getDuration() != null && StringUtils.isNotBlank(savedCourseInfo.getDuration().getAtpDurationTypeKey())) {
            try {
                TypeInfo type = getTypeService().getType(savedCourseInfo.getDuration().getAtpDurationTypeKey(), ContextUtils.createDefaultContextInfo());
                reviewData.getCourseLogisticsSection().setAtpDurationType(type.getName());
            } catch (Exception e) {
                throw new RiceIllegalStateException(e);
            }
        }

        if (savedCourseInfo.getDuration() != null) {
            reviewData.getCourseLogisticsSection().setTimeQuantity(savedCourseInfo.getDuration().getTimeQuantity());
        }

        reviewData.getCourseLogisticsSection().setAudit(BooleanUtils.toStringYesNo(courseInfoWrapper.isAudit()));
        reviewData.getCourseLogisticsSection().setPassFail(BooleanUtils.toStringYesNo(courseInfoWrapper.isPassFail()));
        reviewData.getCourseLogisticsSection().setGradingOptions(buildGradingOptionsList());
        reviewData.getCourseLogisticsSection().setFinalExamStatus(getFinalExamString());
        reviewData.getCourseLogisticsSection().setFinalExamStatusRationale(courseInfoWrapper.getFinalExamRationale());

        reviewData.getCourseLogisticsSection().getOutcomes().clear();

        for (ResultValuesGroupInfoWrapper rvg : courseInfoWrapper.getCreditOptionWrappers()) {
            if (StringUtils.isNotBlank(rvg.getTypeKey())) {
                String creditOptionType = "";
                String creditOptionValue = rvg.getUiHelper().getResultValue();
                if (StringUtils.equals(rvg.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    creditOptionType = "Fixed";
                    if (StringUtils.contains(rvg.getResultValueRange().getMinValue(), "degree.")) {
                        creditOptionValue = StringUtils.substringAfterLast(rvg.getUiHelper().getResultValue(), "degree.");
                    } else {
                        creditOptionValue = rvg.getUiHelper().getResultValue();
                    }
                } else if (StringUtils.equals(rvg.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                    creditOptionType = "Multiple";
                } else if (StringUtils.equals(rvg.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                    creditOptionType = "Range";
                }
                reviewData.getCourseLogisticsSection().getOutcomes().add(new OutcomeReviewSection(creditOptionType, creditOptionValue));
            }
        }

        List<FormatInfoWrapper> formatInfoWrappers = new ArrayList<FormatInfoWrapper>();
        for (FormatInfo formatInfo : savedCourseInfo.getFormats()) {

            List<ActivityInfoWrapper> activityInfoWrapperList = new ArrayList<ActivityInfoWrapper>();
            for (ActivityInfo activityInfo : formatInfo.getActivities()) {

                Integer anticipatedClassSize = activityInfo.getDefaultEnrollmentEstimate();
                String activityType = activityInfo.getTypeKey();

                String contactHours = "";
                String durationCount = "";

                if (activityInfo.getDuration() != null) {
                    String durationType = null;
                    if (StringUtils.isNotBlank(activityInfo.getDuration().getAtpDurationTypeKey())) {
                        try {
                            TypeInfo duration = getTypeService().getType(activityInfo.getDuration().getAtpDurationTypeKey(), createContextInfo());
                            durationType = duration.getName();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (activityInfo.getDuration().getTimeQuantity() != null) {
                        durationCount = activityInfo.getDuration().getTimeQuantity().toString();
                    }
                    if (StringUtils.isNotBlank(durationType)) {
                        durationCount = durationCount + " " + durationType + CurriculumManagementConstants.COLLECTION_ITEM_PLURAL_END;
                    }
                }

                if (activityInfo.getContactHours() != null) {

                    String contactType = activityInfo.getContactHours().getUnitTypeKey();
                    contactType = StringUtils.substringAfterLast(contactType, ".");

                    if (activityInfo.getContactHours().getUnitQuantity() != null) {
                        contactHours = activityInfo.getContactHours().getUnitQuantity();
                    }
                    if (StringUtils.isNotBlank(contactType)) {
                        contactHours = contactHours + " per " + StringUtils.lowerCase(contactType);
                    }
                }

                ActivityInfoWrapper activityInfoWrapper = new ActivityInfoWrapper(anticipatedClassSize, activityType, durationCount, contactHours);
                activityInfoWrapperList.add(activityInfoWrapper);
            }
            FormatInfoWrapper formatInfoWrapper = new FormatInfoWrapper(activityInfoWrapperList);
            formatInfoWrappers.add(formatInfoWrapper);
        }
        if (!formatInfoWrappers.isEmpty()) {
            reviewData.getCourseLogisticsSection().setFormatInfoWrappers(formatInfoWrappers);
        }

        /**
         * Active Dates section
         */
        reviewData.getActiveDatesSection().setStartTerm(getTermDesc(courseInfoWrapper.getCourseInfo().getStartTerm()));
        reviewData.getActiveDatesSection().setEndTerm(getTermDesc(courseInfoWrapper.getCourseInfo().getEndTerm()));
        reviewData.getActiveDatesSection().setPilotCourse(BooleanUtils.toStringYesNo(courseInfoWrapper.getCourseInfo().isPilotCourse()));

        /**
         * Financials section
         */
//        reviewData.getFinancialsSection().setFee();
//        reviewData.getFinancialsSection().setExpendingOrganization();
//        reviewData.getFinancialsSection().setRevenueSource();
        if (savedCourseInfo.getFeeJustification() != null) {
            reviewData.getFinancialsSection().setJustificationOfFees(savedCourseInfo.getFeeJustification().getPlain());
        }

        /**
         * Populate 'Proposal' specific model
         */
        if (courseInfoWrapper.isProposalDataUsed()) {
            updateSupportingDocumentsForReviewPages(courseInfoWrapper);
            updateProposalDataForReviewPages(reviewData, shouldRepopulateRemoteData);
        }
    }

    /**
     * This method populates the proposal related model which can be displayed at review proposal page
     * @param reviewData
     * @param shouldRepopulateRemoteData
     */
    protected void updateProposalDataForReviewPages(ReviewProposalDisplay reviewData,boolean shouldRepopulateRemoteData){
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();
        ProposalInfo proposalInfo = courseInfoWrapper.getProposalInfo();

        reviewData.getCourseSection().setProposalName(proposalInfo.getName());

        if (proposalInfo.getRationale() != null) {
            reviewData.getCourseSection().setRationale(proposalInfo.getRationale().getPlain());
        }

        // update the remote elements if needed but let the method decide which to repopulate
        updateRemoteDataElementsForReviewPages(courseInfoWrapper, shouldRepopulateRemoteData);

        // update supporting Documents Section on review page
        copySupportingDocumentsToReviewPages(courseInfoWrapper);
    }

    protected List<String> updateCampusLocations(List<String> campusLocations) {

        final List<KeyValue> keyValues = new ArrayList<KeyValue>();
        try {
            final List<EnumeratedValueInfo> enumerationInfos =
                    getEnumerationManagementService().getEnumeratedValues
                            (CluServiceConstants.CAMPUS_LOCATION_ENUM_KEY, null, null, null, ContextUtils.createDefaultContextInfo());

            Collections.sort(enumerationInfos, new Comparator<EnumeratedValueInfo>() {
                @Override
                public int compare(EnumeratedValueInfo o1, EnumeratedValueInfo o2) {
                    int result = o1.getSortKey().compareToIgnoreCase(o2.getSortKey());
                    return result;
                }
            });

            for (final EnumeratedValueInfo enumerationInfo : enumerationInfos) {
                keyValues.add(new ConcreteKeyValue(enumerationInfo.getCode(), enumerationInfo.getValue()));
            }

        } catch (DoesNotExistException e) {
            throw new RuntimeException("No subject areas found! There should be some in the database", e);
        } catch (Exception e) {
            throw new RuntimeException("Error looking up Campus Locations", e);
        }

        List<String> newCampusLocationsName = new ArrayList<String>();
        for (KeyValue kval : keyValues) {
            if (campusLocations.contains(kval.getKey())) {
                newCampusLocationsName.add(kval.getValue());
            }
        }

        return newCampusLocationsName;
    }

    @Override
    public void saveDataObject() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        if (courseInfoWrapper.getCourseInfo().getDescr() != null && courseInfoWrapper.getCourseInfo().getDescr().getPlain() != null) {
            String courseDescription = courseInfoWrapper.getCourseInfo().getDescr().getPlain().replace("\r\n", "\n"); // replacing carriage return and new line with new line char.
            courseInfoWrapper.getCourseInfo().getDescr().setPlain(courseDescription);
        }

        courseInfoWrapper.getCourseInfo().getInstructors().clear();

        if (courseInfoWrapper.getInstructorWrappers() != null) {
            for (final CluInstructorInfoWrapper instructorDisplay : courseInfoWrapper.getInstructorWrappers()) {
                courseInfoWrapper.getCourseInfo().getInstructors().add(instructorDisplay);
            }
        }

        for (int count = 0; count < (courseInfoWrapper.getCourseInfo().getCrossListings().size()); count++) {
            courseInfoWrapper.getCourseInfo().getCrossListings().get(count).setTypeKey(CourseAssemblerConstants.COURSE_CROSSLISTING_IDENT_TYPE);
        }

        /*
         * Learning Objectives
         */
        courseInfoWrapper.getCourseInfo().getCourseSpecificLOs().clear();
        if (courseInfoWrapper.getLoDisplayWrapperModel() != null && courseInfoWrapper.getLoDisplayWrapperModel().getLoWrappers() != null) {
            List<LoDisplayInfoWrapper> loWrappers = courseInfoWrapper.getLoDisplayWrapperModel().getLoWrappers();
            List<LoDisplayInfo> courseLos = courseInfoWrapper.getCourseInfo().getCourseSpecificLOs();
            courseLos.clear();

            //  LOs are assigned a sequence via this variable which reflects and preserves the order in which they appeared in the form.
            int sequence = 0;

            for (int i = 0; i < loWrappers.size(); i++) {
                LoDisplayInfoWrapper currentLo = loWrappers.get(i);
                if (!currentLo.isUserEntered() && (currentLo.getLoCategoryInfoList().isEmpty())) {
                    continue;
                }

                //  (Re)Set the sequence in the LoInfo dynamic attributes.
                new AttributeHelper(currentLo.getLoInfo().getAttributes())
                        .put(CurriculumManagementConstants.LoProperties.SEQUENCE, String.valueOf(sequence++));

                boolean rootLevel = true;
                int parentIndex = i - 1;

                while (parentIndex >= 0) {
                    LoDisplayInfoWrapper potentialParent = loWrappers.get(parentIndex);
                    boolean parentMatch = currentLo.getIndentLevel() > potentialParent.getIndentLevel();
                    if (parentMatch) {
                        //currentLo.setParentLoRelationid(potentialParent.getLoInfo().getId());
                        //currentLo.setParentRelType(CourseAssemblerConstants.COURSE_LO_RELATION_INCLUDES);
                        potentialParent.getLoDisplayInfoList().add(currentLo);
                        rootLevel = false;
                        break;
                    } else {
                        parentIndex--;
                    }
                }

                if (rootLevel) {
                    courseLos.add(currentLo);
                }
            }
        }

        // Set derived course fields before saving/updating
        courseInfoWrapper.setCourseInfo(calculateCourseDerivedFields(courseInfoWrapper.getCourseInfo()));

        courseInfoWrapper.getCourseInfo().getUnitsContentOwner().clear();
        for (CourseCreateUnitsContentOwner wrapper : courseInfoWrapper.getUnitsContentOwner()) {
            courseInfoWrapper.getCourseInfo().getUnitsContentOwner().add(wrapper.getOrgId());
            wrapper.getRenderHelper().setNewRow(false);
            if (!wrapper.isUserEntered()) {
                populateOrgName(courseInfoWrapper.getCourseInfo().getSubjectArea(), wrapper);
            }
        }

        courseInfoWrapper.getCourseInfo().getCreditOptions().clear();

        //Credit Options
        if (courseInfoWrapper.isAudit()) {
            ResultValuesGroupInfo resultValuesGroupInfo = new ResultValuesGroupInfo();
            resultValuesGroupInfo.setName("Audit");
            resultValuesGroupInfo.setTypeKey(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
            //This should be based on the course state. But for now, it's draft
            resultValuesGroupInfo.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_DRAFT);
            courseInfoWrapper.getCourseInfo().getCreditOptions().add(resultValuesGroupInfo);
        }

        populateAuditOnDTO();

        populatePassFailOnDTO();

        populateFinalExamOnDTO();

        populateOutComesOnDTO();

        populateFormatOnDTO();

        populateJointCourseOnDTO();

        courseInfoWrapper.getCourseInfo().setStartTerm(courseInfoWrapper.getCourseInfo().getStartTerm());
        courseInfoWrapper.getCourseInfo().setEndTerm(courseInfoWrapper.getCourseInfo().getEndTerm());
        courseInfoWrapper.getCourseInfo().setPilotCourse(courseInfoWrapper.getCourseInfo().isPilotCourse());

        try {
            LOG.info("Saving Proposal for course {}", courseInfoWrapper.getCourseInfo().getId());
            updateAndSaveCourseInfo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        courseInfoWrapper.setNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);
        courseInfoWrapper.setRefDiscriminatorType(CourseServiceConstants.REF_OBJECT_URI_COURSE);
        courseInfoWrapper.setRefObjectId(courseInfoWrapper.getCourseInfo().getId());

        super.saveDataObject();

        // now that we've saved the proposal in the super call... we can try to update the updateTime
        if (StringUtils.isNotBlank(courseInfoWrapper.getProposalInfo().getId())){
            Date updateTime = courseInfoWrapper.getProposalInfo().getMeta().getUpdateTime();
            if (updateTime != null){
                courseInfoWrapper.setLastUpdated(CurriculumManagementConstants.CM_DATE_FORMATTER.format(updateTime));
            }
        }else{
            courseInfoWrapper.setLastUpdated(CurriculumManagementConstants.CM_DATE_FORMATTER.format(new DateTime()));
        }
    }

    /**
     * This method creates <class>CourseJointInfoWrapper</class> instances from <class>CourseJointInfo</class> instance
     */
    protected void populateJointCourseOnDTO() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();
        courseInfoWrapper.getCourseInfo().getJoints().clear();

        for (final CourseJointInfoWrapper jointInfoDisplay : courseInfoWrapper.getCourseJointWrappers()) {

            if (jointInfoDisplay.isUserEntered()) {
                courseInfoWrapper.getCourseInfo().getJoints().add(jointInfoDisplay);
            }

        }

    }

    protected void populateOutComesOnDTO() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        for (ResultValuesGroupInfoWrapper rvgWrapper : courseInfoWrapper.getCreditOptionWrappers()) {

            if (!rvgWrapper.isUserEntered()) {
                continue;
            }

            ResultValuesGroupInfo rvg = rvgWrapper.getResultValuesGroupInfo();

            if (rvg == null) {
                rvg = new ResultValuesGroupInfo();
                rvgWrapper.setResultValuesGroupInfo(rvg);
            }

            rvg.setTypeKey(rvgWrapper.getTypeKey());
            rvg.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_DRAFT);
            courseInfoWrapper.getCourseInfo().getCreditOptions().add(rvg);

            if (StringUtils.equals(rvgWrapper.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                ResultValueRangeInfo range = new ResultValueRangeInfo();
                range.setMinValue(rvgWrapper.getUiHelper().getResultValue());
                rvg.setResultValueRange(range);
                rvg.setTypeKey(rvgWrapper.getTypeKey());
            } else if (StringUtils.equals(rvgWrapper.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                String[] resultValues = StringUtils.split(rvgWrapper.getUiHelper().getResultValue(), ",");
                rvg.getResultValueKeys().clear();
                for (String result : resultValues) {
                    StringBuilder builder = new StringBuilder(LrcServiceConstants.RESULT_VALUE_KEY_CREDIT_DEGREE_PREFIX);
                    float floatValue = Float.valueOf(result);
                    builder.append(floatValue);
                    rvg.getResultValueKeys().add(builder.toString());
                    rvg.setTypeKey(rvgWrapper.getTypeKey());
                }
            } else if (StringUtils.equals(rvgWrapper.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                ResultValueRangeInfo range = new ResultValueRangeInfo();
                range.setMinValue(StringUtils.substringBefore(rvgWrapper.getUiHelper().getResultValue(), "-"));
                range.setMaxValue(StringUtils.substringAfter(rvgWrapper.getUiHelper().getResultValue(), "-"));
                rvg.setResultValueRange(range);
                rvg.setTypeKey(rvgWrapper.getTypeKey());
            }
        }

        initializeOutcome(courseInfoWrapper);

    }

    /**
     * Populates format/activity to course dto to save
     */
    protected void populateFormatOnDTO() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();
        courseInfoWrapper.getCourseInfo().getFormats().clear();
        List<ActivityInfo> activities;
        for (FormatInfo format : courseInfoWrapper.getFormats()) {
            activities = new ArrayList<ActivityInfo>();
            if (!isEmptyFormat(format)) {

                if (StringUtils.isBlank(format.getId())) { // If it's new
                    format.setState(DtoConstants.STATE_DRAFT);
                    if (StringUtils.isBlank(format.getTypeKey())) {
                        format.setTypeKey(CluServiceConstants.COURSE_FORMAT_TYPE_KEY);
                    }
                }
                for (ActivityInfo activity : format.getActivities()) {
                    if (StringUtils.isBlank(activity.getId())) { // If it's new
                        activity.setState(DtoConstants.STATE_DRAFT);
                    }
                    // blank activities are removed from the list.
                    if (activity.getId() == null && (activity.getTypeKey() == null)) {
                        continue;
                    }
                    // only non blank activities are added to the list
                    activities.add(activity);
                }
                format.getActivities().clear();
                format.setActivities(activities);
                courseInfoWrapper.getCourseInfo().getFormats().add(format);

            }
        }
    }

    /**
     * This method checks whether a format is empty or not by checking all the activities type.
     *
     * @param format
     * @return
     */
    protected boolean isEmptyFormat(FormatInfo format) {

        for (ActivityInfo activity : format.getActivities()) {
            if (StringUtils.isNotBlank(activity.getTypeKey())) {
                return false;
            }
        }

        return true;
    }

    protected void populateOutComesOnWrapper() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();
        courseInfoWrapper.getCreditOptionWrappers().clear();

        for (ResultValuesGroupInfo rvg : courseInfoWrapper.getCourseInfo().getCreditOptions()) {

            ResultValuesGroupInfoWrapper rvgWrapper = new ResultValuesGroupInfoWrapper();
            BeanUtils.copyProperties(rvg, rvgWrapper);
            rvgWrapper.setResultValuesGroupInfo(rvg);

            if (StringUtils.equals(rvg.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {

                StringBuilder resultValue = new StringBuilder("");
                List<String> resultValueList = new ArrayList<String>();

                for (String rvKey : rvg.getResultValueKeys()) {
                    String value = StringUtils.strip(rvKey, LrcServiceConstants.RESULT_VALUE_KEY_CREDIT_DEGREE_PREFIX);
                    resultValueList.add(StringUtils.strip(value, ".0")); // This can be only be integer at ui.
                }

                // Sort the values to be displayed at ui
                Collections.sort(resultValueList);
                rvgWrapper.getUiHelper().setResultValue(StringUtils.join(resultValueList, ","));

            } else if (StringUtils.equals(rvg.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {

                String minValue = StringUtils.strip(rvg.getResultValueRange().getMinValue(), ".0"); // This can be only be integer at ui.
                String maxValue = StringUtils.strip(rvg.getResultValueRange().getMaxValue(), ".0"); // This can be only be integer at ui.

                rvgWrapper.getUiHelper().setResultValue(minValue + "-" + maxValue);
            } else if (StringUtils.equals(rvg.getTypeKey(), LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                rvgWrapper.getUiHelper().setResultValue(StringUtils.strip(rvg.getResultValueRange().getMinValue(), ".0"));
            }
            courseInfoWrapper.getCreditOptionWrappers().add(rvgWrapper);
        }

        Collections.sort(courseInfoWrapper.getCreditOptionWrappers(),
                new Comparator<ResultValuesGroupInfoWrapper>() {
                    public int compare(ResultValuesGroupInfoWrapper a, ResultValuesGroupInfoWrapper b) {
                        if (a.getTypeKey() == null) {
                            return 1;
                        } else if (b.getTypeKey() == null) {
                            return -1;
                        }
                        return a.getTypeKey().compareToIgnoreCase(b.getTypeKey());
                    }
                }
        );

        initializeOutcome(courseInfoWrapper);

    }

    protected void populateFormatOnWrapper() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();
        courseInfoWrapper.getFormats().clear();
        courseInfoWrapper.getFormats().addAll(courseInfoWrapper.getCourseInfo().getFormats());

        initializeFormat(courseInfoWrapper);
    }

    protected void populatePassFailOnDTO() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        AttributeInfo passFailAttr = null;
        for (AttributeInfo attr : courseInfoWrapper.getCourseInfo().getAttributes()) {
            if (StringUtils.equals(attr.getKey(), CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_PASSFAIL)) {
                passFailAttr = attr;
                break;
            }
        }

        if (passFailAttr == null) {
            passFailAttr = new AttributeInfo();
            passFailAttr.setKey(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_PASSFAIL);
            courseInfoWrapper.getCourseInfo().getAttributes().add(passFailAttr);
        }

        passFailAttr.setValue(BooleanUtils.toStringTrueFalse(courseInfoWrapper.isPassFail()));
    }

    protected void populatePassFailOnWrapper() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        for (AttributeInfo attr : courseInfoWrapper.getCourseInfo().getAttributes()) {
            if (StringUtils.equals(attr.getKey(), CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_PASSFAIL)) {
                courseInfoWrapper.setPassFail(BooleanUtils.toBoolean(attr.getValue()));
                break;
            }
        }

    }

    protected void populateAuditOnDTO() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        AttributeInfo auditAttr = null;
        for (AttributeInfo attr : courseInfoWrapper.getCourseInfo().getAttributes()) {
            if (StringUtils.equals(attr.getKey(), CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT)) {
                auditAttr = attr;
                break;
            }
        }

        if (auditAttr == null) {
            auditAttr = new AttributeInfo();
            auditAttr.setKey(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT);
            courseInfoWrapper.getCourseInfo().getAttributes().add(auditAttr);
        }

        auditAttr.setValue(BooleanUtils.toStringTrueFalse(courseInfoWrapper.isAudit()));
    }

    protected void populateAuditOnWrapper() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        for (AttributeInfo attr : courseInfoWrapper.getCourseInfo().getAttributes()) {
            if (StringUtils.equals(attr.getKey(), CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT)) {
                courseInfoWrapper.setAudit(BooleanUtils.toBoolean(attr.getValue()));
                break;
            }
        }

    }

    protected void populateFinalExamOnDTO() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        AttributeInfo finalExamStatusDetail = null;
        AttributeInfo finalExamRationalDetail = null;
        for (AttributeInfo attr : courseInfoWrapper.getCourseInfo().getAttributes()) {
            if (StringUtils.equals(attr.getKey(), CourseServiceConstants.FINAL_EXAM)) {
                finalExamStatusDetail = attr;
            } else if (StringUtils.equals(attr.getKey(), CourseServiceConstants.FINAL_EXAM_RATIONALE)) {
                finalExamRationalDetail = attr;
            }
        }

        if (finalExamStatusDetail == null) {
            finalExamStatusDetail = new AttributeInfo();
            finalExamStatusDetail.setKey(CourseServiceConstants.FINAL_EXAM);
            courseInfoWrapper.getCourseInfo().getAttributes().add(finalExamStatusDetail);
        }

        if (StringUtils.isNotBlank(courseInfoWrapper.getFinalExamStatus()) &&
                !StringUtils.equals(courseInfoWrapper.getFinalExamStatus(), CourseServiceConstants.STD_EXAM_FINAL_ENUM_KEY) &&
                finalExamRationalDetail == null) {
            finalExamRationalDetail = new AttributeInfo();
            finalExamRationalDetail.setKey(CourseServiceConstants.FINAL_EXAM_RATIONALE);
            courseInfoWrapper.getCourseInfo().getAttributes().add(finalExamRationalDetail);
        }

        if (finalExamRationalDetail != null) {
            finalExamRationalDetail.setValue(courseInfoWrapper.getFinalExamRationale());
        }

        finalExamStatusDetail.setValue(courseInfoWrapper.getFinalExamStatus());
    }


    protected void populateFinalExamOnWrapper() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        for (AttributeInfo attr : courseInfoWrapper.getCourseInfo().getAttributes()) {
            if (StringUtils.equals(attr.getKey(), CourseServiceConstants.FINAL_EXAM)) {
                courseInfoWrapper.setFinalExamStatus(attr.getValue());
            } else if (StringUtils.equals(attr.getKey(), CourseServiceConstants.FINAL_EXAM_RATIONALE)) {
                courseInfoWrapper.setFinalExamRationale(attr.getValue());
            }
        }

    }

    /**
     * @return List list of grading options.
     */
    protected List<String> buildGradingOptionsList() {
        List<String> gradingOptions = new ArrayList<String>();

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        if (!courseInfoWrapper.getCourseInfo().getGradingOptions().isEmpty()) {
            try {
                List<ResultValuesGroupInfo> rvgs = getLRCService().getResultValuesGroupsByKeys(courseInfoWrapper.getCourseInfo().getGradingOptions(), createContextInfo());
                for (ResultValuesGroupInfo rvg : rvgs) {
                    gradingOptions.add(rvg.getName());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return gradingOptions;
    }

    protected String getFinalExamString() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        if (StringUtils.isNotBlank(courseInfoWrapper.getFinalExamStatus())) {
            List<EnumeratedValueInfo> enumerationInfos = null;
            try {
                enumerationInfos = getEnumerationManagementService().getEnumeratedValues(
                        CluServiceConstants.FINAL_EXAM_STATUS_ENUM_KEY, null, null, null, ContextUtils.createDefaultContextInfo());

                for (EnumeratedValueInfo enumerationInfo : enumerationInfos) {
                    if (StringUtils.equals(courseInfoWrapper.getFinalExamStatus(), enumerationInfo.getCode())) {
                        return enumerationInfo.getValue();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return "";
    }

    /**
     * Converts the display name of the instructor into the plain user name (for use in a search query)
     *
     * @param displayName The display name of the instructor.
     * @return The user name of the instructor.
     */
    protected String getInstructorSearchString(String displayName) {
        if (displayName == null) {
            return StringUtils.EMPTY;
        }
        String searchString = "";
        if (displayName.contains("(") && displayName.contains(")")) {
            searchString = displayName.substring(displayName.lastIndexOf('(') + 1, displayName.lastIndexOf(')'));
        }
        return searchString;
    }

    /**
     * Copied this method from CourseDataService.
     * This calculates and sets fields on course object that are derived from other course object fields.
     */
    protected CourseInfo calculateCourseDerivedFields(CourseInfo courseInfo) {
        // Course code is not populated in UI, need to derive them from the subject area and suffix fields
        if (StringUtils.isNotBlank(courseInfo.getCourseNumberSuffix()) && StringUtils.isNotBlank(courseInfo.getSubjectArea())) {
            courseInfo.setCode(calculateCourseCode(courseInfo.getSubjectArea(), courseInfo.getCourseNumberSuffix()));
        }

        // Derive course code for crosslistings
        for (CourseCrossListingInfo crossListing : courseInfo.getCrossListings()) {
            if (StringUtils.isNotBlank(crossListing.getCourseNumberSuffix()) && StringUtils.isNotBlank(crossListing.getSubjectArea())) {
                crossListing.setCode(calculateCourseCode(crossListing.getSubjectArea(), crossListing.getCourseNumberSuffix()));
            }
        }

        return courseInfo;
    }

    /**
     * Copied this method from CourseDataService
     * This method calculates code for course and cross listed course.
     *
     * @param subjectArea
     * @param suffixNumber
     * @return
     */
    protected String calculateCourseCode(final String subjectArea, final String suffixNumber) {
        return subjectArea + suffixNumber;
    }

    /**
     * Loads the data object for the maintenance view.
     */
    public void retrieveDataObject() {
        super.retrieveDataObject();

        CourseInfoWrapper dataObject = (CourseInfoWrapper) getDataObject();
        try {
            populateCourseAndReviewData(getProposalInfo().getProposalReference().get(0), dataObject);
        } catch (Exception e) {
            throw new RuntimeException("Caught Exception while populating Course data", e);
        }

        finalizeUiHelper();

        //  If this is the draft course on a modify with new version then save the start term for the current version of the course.
        //  This is used to constrain the list of start terms on the UI.
        if (dataObject.getUiHelper().isModifyWithNewVersionProposal()
                && dataObject.getCourseInfo().getStateKey().equals(DtoConstants.STATE_DRAFT)) {
            String vIId = dataObject.getCourseInfo().getVersion().getVersionIndId();
            CourseInfo currentVersion = null;
            try {
                currentVersion = CourseProposalUtil.getCurrentVersionOfCourse(vIId, ContextUtils.createDefaultContextInfo());
                dataObject.setCurrentCourseStartTermId(currentVersion.getStartTerm());
            } catch (Exception e) {
                LOG.error("Could not get current course for version.", e);
            }
        }
    }

    /**
     * Performs the final initialization of the UI Helper class.
     */
    protected void finalizeUiHelper() {
        CourseInfoWrapper dataObject = (CourseInfoWrapper) getDataObject();

        String docId = dataObject.getProposalInfo().getWorkflowId();
        String docTypeName = findDocumentTypeName(docId);

        //  Set the flag for a modify a course with a new version.
        //  Kinda feels we should just be setting the doc type and put the logic in the UI Helper.
        if (ArrayUtils.contains(CurriculumManagementConstants.DocumentTypeNames.COURSE_MODIFY_DOC_TYPE_NAMES, docTypeName)) {
            dataObject.getUiHelper().setModifyWithNewVersionProposal(true);
        }
    }

    /**
     * Populates the wrapper objects used on the create course proposal and course view pages.
     *
     * @param proposalElementsWrapper The wrapper to populate.
     */
    @Override
    public void populateWrapperData(ProposalElementsWrapper proposalElementsWrapper) throws Exception {
        CourseInfoWrapper courseWrapper = (CourseInfoWrapper) proposalElementsWrapper;
        CourseInfo course = courseWrapper.getCourseInfo();
        /*
         * Instructors
         */
        courseWrapper.getInstructorWrappers().clear();
        for (CluInstructorInfo instructorInfo : course.getInstructors()) {
            List<CluInstructorInfoWrapper> cluInstructorInfoWrapperList = getInstructorsById(instructorInfo.getPersonId());
            CluInstructorInfoWrapper cluInstructorInfoWrapper = KSCollectionUtils.getRequiredZeroElement(cluInstructorInfoWrapperList);
            cluInstructorInfoWrapper.setId(instructorInfo.getId());
            courseWrapper.getInstructorWrappers().add(cluInstructorInfoWrapper);
        }
        //  Add an empty line
        if (courseWrapper.getInstructorWrappers().isEmpty()) {
            courseWrapper.getInstructorWrappers().add(new CluInstructorInfoWrapper());
        }

        /*
         * Administering Organizations
         */
        courseWrapper.getAdministeringOrganizations().clear();
        for (String unitDeployment : course.getUnitsDeployment()) {
            OrgInfo org = getOrganizationService().getOrg(unitDeployment, createContextInfo());
            OrganizationInfoWrapper organizationInfoWrapper = new OrganizationInfoWrapper(org);
            courseWrapper.getAdministeringOrganizations().add(organizationInfoWrapper);
        }
        //  Add an empty line
        if (courseWrapper.getAdministeringOrganizations().isEmpty()) {
            courseWrapper.getAdministeringOrganizations().add(new OrganizationInfoWrapper());
        }

        populateCurrentCourseEndTermShortName();
        populateAuditOnWrapper();
        populateFinalExamOnWrapper();
        populatePassFailOnWrapper();
        populateOutComesOnWrapper();
        populateFormatOnWrapper();
        populateJointCourseOnWrapper();
        populateLearningObjectives();
        populateRequisities(courseWrapper,course.getId());
        super.populateWrapperData(courseWrapper);
    }

    /*
     * Calculate the end term for the current course and store the display text if this is a modify proposal and
     * the courseInfo state is draft.
     */
    protected void populateCurrentCourseEndTermShortName() {
        CourseInfoWrapper dataObject = (CourseInfoWrapper) getDataObject();

        boolean isModify = false;

        String docId = dataObject.getProposalInfo().getWorkflowId();
        //  If we don't know the docId then don't load the data.
        if (StringUtils.isNotBlank(docId)) {
            String docTypeName = findDocumentTypeName(docId);
            if (ArrayUtils.contains(CurriculumManagementConstants.DocumentTypeNames.COURSE_MODIFY_DOC_TYPE_NAMES, docTypeName)) {
                isModify = true;
            }
        }

        if (dataObject.getCourseInfo().getStateKey().equals(DtoConstants.STATE_DRAFT) && isModify) {
            String startTerm = dataObject.getCourseInfo().getStartTerm();
            if (startTerm != null) {
                String endTermShortName = CourseProposalUtil.getPreviousTerm(startTerm, ContextUtils.createDefaultContextInfo()).getShortName();
                dataObject.setCurrentCourseEndTermShortName(endTermShortName);
            }
        }
    }

    /**
     * This method builds a course copy and returns CourseInfoWrapper for the new course.
     *
     * @param sourceCourseId
     * @return
     * @throws Exception
     */
    public CourseInfoWrapper copyCourse(String sourceCourseId) throws Exception {

        CourseInfo sourceCourse = getCourseService().getCourse(sourceCourseId, createContextInfo());
        CourseInfo targetCourse = new CourseInfo();

        getCourseCopyHelper().copyCourse(sourceCourse, targetCourse);

        /**
         * Populate the source course wrapper first sothat we can create target from here.
         */
        CourseInfoWrapper targetCourseWrapper = new CourseInfoWrapper();
        targetCourseWrapper.setCourseInfo(targetCourse);
        setDataObject(targetCourseWrapper); // Most of the populate methods and requisities are using this dataobject

        targetCourseWrapper.setPreviousSubjectCode(sourceCourse.getSubjectArea());

        /**
         * As we cleaned up target course which doesnt have course Id, we need to populate requisities seperately
         */
        populateWrapperData(targetCourseWrapper);

        /**
         * Populate all the source requisities into the target
         */
        populateRequisities(targetCourseWrapper,sourceCourse.getId());

        /**
         * Needs final cleanup with requisitie Ids and other
         */
        getCourseCopyHelper().cleanUpCourseWrapperOnCopy(targetCourseWrapper);

        initializeOutcome(targetCourseWrapper);

        return targetCourseWrapper;
    }

    public ProposalElementsWrapper copyWrapperObjectsToProposal(ProposalInfo sourceProposal) throws Exception {
        String courseId = sourceProposal.getProposalReference().get(0);
        return copyCourse(courseId);
    }

    /**
     * This method loads course information and populate to <class>CourseInfoWrapper</class> and also to
     * <class>ReviewProposalDisplay</class> for display purpose at 'review proposal' and 'view course'.
     * <p/>
     * Note: This method is used by non-maintenance views so you can't put anything maintenance specific here or in any
     * methods this method calls.
     *
     * @param courseId The id of the course to load.
     * @param courseWrapper The data object to populate.
     * @throws Exception
     */
    public void populateCourseAndReviewData(String courseId, CourseInfoWrapper courseWrapper) throws Exception {
         populateCourseAndReviewData(courseId, courseWrapper, false);
    }

    /**
     * Same as above, but adds the option to load version data.
     *
     * @param courseId The id of the course to load.
     * @param courseWrapper The data object to populate.
     * @param loadVersionData A flag to indicate whether version info should be looked up and populated.
     */
    public void populateCourseAndReviewData(String courseId, CourseInfoWrapper courseWrapper, boolean loadVersionData) throws Exception {

        CourseInfo course = getCourseService().getCourse(courseId, createContextInfo());

        courseWrapper.setCourseInfo(course);

        populateWrapperData(courseWrapper);

        updateReview(courseWrapper, false);
        if (loadVersionData) {
            loadVersionData(courseWrapper);
        }
    }

    /**
     * Populates .
     *
     * @param courseWrapper
     */
    protected void loadVersionData(CourseInfoWrapper courseWrapper) throws Exception {
        String currentCourseSuffix = "";

        if (isCurrentVersionOfCourse(courseWrapper.getCourseInfo(), this.createContextInfo())) {
            courseWrapper.setCurrentVersion(true);
            currentCourseSuffix = "(current version)";
        } else {
            courseWrapper.setCurrentVersion(false);
        }

        courseWrapper.setVersionText(String.format("Version %s %s",
                courseWrapper.getCourseInfo().getVersion().getSequenceNumber(), currentCourseSuffix));
    }

    public void populateRequisities(CourseInfoWrapper courseInfoWrapper, String courseId) {

        if (StringUtils.isNotBlank(courseId)) {

            courseInfoWrapper.setRefObjectId(courseId);

            courseInfoWrapper.setAgendas(this.getAgendasForRef(CourseServiceConstants.REF_OBJECT_URI_COURSE, courseId, null));
        }
    }

    /**
     * This method creates <class>LoDisplayWrapperModel</class> instances from <class>LoDisplayInfoWrapper</class> instances
     */
    protected void populateLearningObjectives() {
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();
        courseInfoWrapper.getLoDisplayWrapperModel().clearLoWrappers();
        List<LoDisplayInfoWrapper> newDisplayWrappers = new ArrayList<LoDisplayInfoWrapper>();
        int indent = 0;
        for (LoDisplayInfo loDisplayInfo : courseInfoWrapper.getCourseInfo().getCourseSpecificLOs()) {
            LoDisplayInfoWrapper displayInfoWrapper = new LoDisplayInfoWrapper(loDisplayInfo);
            populateLOCategoryName(displayInfoWrapper);
            newDisplayWrappers.add(displayInfoWrapper);
            indentLoOnLoad(newDisplayWrappers, displayInfoWrapper, indent);
        }

        //  Sort the wrappers by their sequence.
        Collections.sort(newDisplayWrappers, new Comparator<LoDisplayInfoWrapper>() {
            @Override
            public int compare(LoDisplayInfoWrapper o1, LoDisplayInfoWrapper o2) {
                int result = 0;
                if (o1.getSequence() < o2.getSequence()) {
                    result = -1;
                } else if (o1.getSequence() > o2.getSequence()) {
                    result = 1;
                }
                return result;
            }
        });

        courseInfoWrapper.getLoDisplayWrapperModel().getLoWrappers().addAll(newDisplayWrappers);

        /**
         * Once we added all the child to a single list for display reasons, clear out all the children from each LOs to avoid
         * duplicates on save. Save will attach the children back to LOs based on the user indentation
         */
        for (LoDisplayInfo loDisplayInfo : courseInfoWrapper.getLoDisplayWrapperModel().getLoWrappers()) {
            loDisplayInfo.getLoDisplayInfoList().clear();
        }

        setLOActions();
    }

    protected void indentLoOnLoad(List<LoDisplayInfoWrapper> newDisplayWrappers, LoDisplayInfoWrapper loDisplayInfoWrapper, int currentIndent) {
        if (loDisplayInfoWrapper.getLoDisplayInfoList().isEmpty()) {
            return;
        }
        int nextLevel = currentIndent + 1;
        for (LoDisplayInfo loDisplayInfo : loDisplayInfoWrapper.getLoDisplayInfoList()) {
            LoDisplayInfoWrapper displayInfoWrapper = new LoDisplayInfoWrapper(loDisplayInfo);
            populateLOCategoryName(displayInfoWrapper);
            newDisplayWrappers.add(displayInfoWrapper);
            displayInfoWrapper.setIndentLevel(nextLevel);
            indentLoOnLoad(newDisplayWrappers, displayInfoWrapper, nextLevel);
        }
    }

    protected void populateLOCategoryName(LoDisplayInfoWrapper displayInfoWrapper) {
        for (LoCategoryInfo loCategoryInfo : displayInfoWrapper.getLoCategoryInfoList()) {
            try {
                TypeInfo typeInfo = getLearningObjectiveService().getLoCategoryType(loCategoryInfo.getTypeKey(), ContextUtils.createDefaultContextInfo());
                loCategoryInfo.setName((new StringBuilder().append(loCategoryInfo.getName()).append(" - ").append(typeInfo.getName()).toString()));
            } catch (Exception e) {
                LOG.error("An error occurred while retrieving the LoCategoryType", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method enables/disables the indent, outdent, move up and move down actions for each LO.
     */
    public void setLOActions() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        for (LoDisplayInfoWrapper loWrapper : courseInfoWrapper.getLoDisplayWrapperModel().getLoWrappers()) {
            loWrapper.setIndentable(courseInfoWrapper.getLoDisplayWrapperModel().isIndentable(loWrapper));
            loWrapper.setOutdentable(courseInfoWrapper.getLoDisplayWrapperModel().isOutdentable(loWrapper));
            loWrapper.setMoveDownable(courseInfoWrapper.getLoDisplayWrapperModel().isMoveDownable(loWrapper));
            loWrapper.setMoveUpable(courseInfoWrapper.getLoDisplayWrapperModel().isMoveUpable(loWrapper));
        }

    }

    /**
     * This method creates <class>CourseJointInfoWrapper</class> instances from <class>CourseJointInfo</class> instance
     */
    protected void populateJointCourseOnWrapper() {

        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();
        courseInfoWrapper.getCourseJointWrappers().clear();

        for (final CourseJointInfo jointInfo : courseInfoWrapper.getCourseInfo().getJoints()) {
            CourseJointInfoWrapper jointInfoWrapper = new CourseJointInfoWrapper();
            BeanUtils.copyProperties(jointInfo, jointInfoWrapper);
            jointInfoWrapper.setCourseCode(jointInfo.getSubjectArea() + jointInfo.getCourseNumberSuffix());
            courseInfoWrapper.getCourseJointWrappers().add(jointInfoWrapper);
        }
        if (courseInfoWrapper.getCourseJointWrappers().isEmpty()) {
            List<CourseJointInfoWrapper> courseJointInfoList = courseInfoWrapper.getCourseJointWrappers();
            courseJointInfoList.add(new CourseJointInfoWrapper());
            courseInfoWrapper.setCourseJointWrappers(courseJointInfoList);
        }

    }

    protected void updateAndSaveCourseInfo() throws Exception {
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper) getDataObject();

        final CourseInfo course = courseInfoWrapper.getCourseInfo();
        for (final CourseVariationInfo variation : course.getVariations()) {
            variation.setTypeKey(ProgramConstants.VARIATION_TYPE_KEY);
        }
        if (StringUtils.isBlank(course.getId())) {
            courseInfoWrapper.setCourseInfo(getCourseService().createCourse(course, ContextUtils.createDefaultContextInfo()));
        } else {
            courseInfoWrapper.setCourseInfo(getCourseService().updateCourse(course.getId(), course, ContextUtils.createDefaultContextInfo()));
        }
    }

    protected String getProposalTypeKey() {
        return ProposalServiceConstants.PROPOSAL_TYPE_COURSE_CREATE_KEY;
    }

    public String getProposalReferenceType() {
        return ProposalServiceConstants.PROPOSAL_DOC_RELATION_TYPE_CLU_KEY;
    }

    protected String getProposalReference() {
        CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper)getDataObject();
        return courseInfoWrapper.getCourseInfo().getId();
    }

    /**
     * The finalizeMethodToCall for the Review Proposal link. Populates the given action link with the URL for the
     * document.
     */
    protected void buildProposalActionLink(Action actionLink, MaintenanceDocumentForm form, String methodToCall, String pageId) {
        String docId = form.getDocument().getDocumentNumber();

        String href = StringUtils.EMPTY;
        if (form.getDocument() != null && form.getDocument().getNewMaintainableObject() != null && form.getDocument().getNewMaintainableObject().getDataObject() != null) {
            String proposalTypeKey = ((ProposalMaintainable) form.getDocument().getNewMaintainableObject()).getProposalInfo().getTypeKey();
            if (StringUtils.isNotBlank(proposalTypeKey)) {
                href = CourseProposalUtil.buildCourseProposalUrl(methodToCall, pageId, docId, proposalTypeKey);
            }
        }

        if (StringUtils.isBlank(href)) {
            actionLink.setRender(false);
            return;
        }

        actionLink.setActionScript("window.open('" + href + "', '_self');");
    }

    @Override
    public String getViewTypeNameForProposal() {
        return KSKRMSServiceConstants.AGENDA_TYPE_COURSE;
    }

    /**
     * Return the clu id from the canonical course that is linked to the given course offering id.
     *
     * @param refObjectId - the course offering id.
     * @return
     * @throws Exception
     */
    @Override
    public List<ReferenceObjectBinding> getParentRefObjectsForProposal(String refObjectId) {
        if (StringUtils.isBlank(refObjectId)) {
            return Collections.EMPTY_LIST;
        }
        return this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(CourseServiceConstants.REF_OBJECT_URI_COURSE, refObjectId);
    }

    protected PersonService getPersonService() {
        if (personService == null) {
            personService = GlobalResourceLoader.getService(new QName(KimConstants.Namespaces.KIM_NAMESPACE_2_0, KimConstants.KIM_PERSON_SERVICE));
        }
        return personService;
    }

    protected TypeService getTypeService() {
        if (typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return typeService;
    }

    private LRCService getLRCService() {
        if (lrcService == null) {
            QName qname = new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART);
            lrcService = (LRCService) GlobalResourceLoader.getService(qname);
        }
        return lrcService;
    }

    protected EnumerationManagementService getEnumerationManagementService() {
        if (enumerationManagementService == null) {
            enumerationManagementService = (EnumerationManagementService) GlobalResourceLoader.getService(new QName(
                    EnumerationManagementServiceConstants.NAMESPACE, EnumerationManagementServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.enumerationManagementService;
    }

    public List<CluInformation> getCoursesInRange(MembershipQueryInfo membershipQuery) {
        return this.getCluInfoHelper().getCluInfosWithDetailForQuery(membershipQuery);
    }

    protected CluInformationHelper getCluInfoHelper() {
        if (cluInfoHelper == null) {
            cluInfoHelper = new CluInformationHelper();
            cluInfoHelper.setCluService(getCluService());
            LRCService lrcService = GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
            cluInfoHelper.setLrcService(lrcService);
        }
        return cluInfoHelper;
    }

    /**
     * As we're using this maintainable in course maintenace document (CourseMaintenanceView.xml) as well as
     * in regular view 'view course' (ViewCourseView.xml), we dont want any of the maintenance document specific
     * logics here to avoid form type casting issues. Also, we dont really need any of the logic here as our functionalities
     * are different than the out of the box logic.
     *
     * @param element
     * @param model
     */
    @Override
    public void performCustomApplyModel(LifecycleElement element, Object model) {

    }

    /**
     * @see #performCustomApplyModel(org.kuali.rice.krad.uif.util.LifecycleElement, Object)
     * @param element
     * @param model
     * @param parent
     */
    @Override
    public void performCustomFinalize(LifecycleElement element, Object model, LifecycleElement parent) {

    }

    /**
     * @see #performCustomApplyModel(org.kuali.rice.krad.uif.util.LifecycleElement, Object)
     * @param model
     */
    @Override
    public void performCustomViewFinalize(Object model) {

    }

    public CourseCopyHelper getCourseCopyHelper() {
        if (courseCopyHelper == null){
            courseCopyHelper = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseCopyHelper", CourseCopyHelper.class.getSimpleName()));
        }
        return courseCopyHelper;
    }

    public void setCourseCopyHelper(CourseCopyHelper courseCopyHelper) {
        this.courseCopyHelper = courseCopyHelper;
    }

}
