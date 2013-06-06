package org.kuali.student.enrollment.class1.krms.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.impl.util.KRMSPropertyConstants;
import org.kuali.rice.krms.service.impl.RuleViewHelperServiceImpl;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class1.krms.dto.CluInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.KrmsSuggestDisplay;
import org.kuali.student.enrollment.class1.krms.util.CourseInfoHelper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/04
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnrolRuleViewHelperServiceImpl extends RuleViewHelperServiceImpl {

    private CluService cluService;
    private CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    private OrganizationService organizationService;

    private RulePreviewTreeBuilder previewTreeBuilder;
    private RuleViewTreeBuilder viewTreeBuilder;

    @Override
    public Class<? extends PropositionEditor> getPropositionEditorClass() {
        return EnrolPropositionEditor.class;
    }

    @Override
    public PropositionEditor copyProposition(PropositionEditor oldProposition) {
        try {
            EnrolPropositionEditor newProposition = (EnrolPropositionEditor) this.copyPropositionEditor(oldProposition);

            //Set the cluset to null to force the builder to create a new cluset.
            if(newProposition.getCluSet()!=null){
                newProposition.getCluSet().setCluSetInfo(null);
            }

            //Use a deepcopy to create new references to inner objects such as string.
            return (PropositionEditor) ObjectUtils.deepCopy(newProposition);
        } catch (Exception e) {
            return null;
        }
    }

    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model,
                                               Object addLine) {
        if("proposition.cluSet.clus".equals(collectionGroup.getPropertyName())){
            //Check if this is a valid clu.
            CluInformation clu = (CluInformation) addLine;
            if((clu.getCluId() == null)||(clu.getCluId().isEmpty())){
                collectionGroup.initializeNewCollectionLine(view, model, collectionGroup, true);
                return false;
            }

            //Check if this clu is not already in the collection
            RuleEditor ruleEditor = this.getRuleEditor(model);
            EnrolPropositionEditor propEditor = (EnrolPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);
            for(CluInformation cluInformation : propEditor.getCluSet().getClus()){
                if(cluInformation.getCluId().equals(clu.getCluId())){
                    collectionGroup.initializeNewCollectionLine(view, model, collectionGroup, true);
                    return false;
                }
            }
        } else if ("proposition.cluSet.cluSets".equals(collectionGroup.getPropertyName())){
            //Check if this is a valid clu.
            CluSetInfo cluSet = (CluSetInfo) addLine;
            if((cluSet.getId() == null)||(cluSet.getId().isEmpty())){
                return false;
            }

            //Check if this clu is not already in the collection
            RuleEditor ruleEditor = this.getRuleEditor(model);
            EnrolPropositionEditor propEditor = (EnrolPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);
            for(CluSetInfo cluSetInfo : propEditor.getCluSet().getCluSets()){
                if(cluSetInfo.getId().equals(cluSet.getId())){
                    return false;
                }
            }
        }

        return true;
    }

    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine,
                                       boolean isValidLine) {

        if("proposition.cluSet.clus".equals(collectionGroup.getPropertyName())){
            //Sort the clus.
            RuleEditor ruleEditor = this.getRuleEditor(model);
            EnrolPropositionEditor propEditor = (EnrolPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);

            CourseInfoHelper courseInfoHelper = new CourseInfoHelper();
            CluInformation clu = (CluInformation) addLine;
            clu.setCredits(courseInfoHelper.getCreditInfo(clu.getCluId()));
            Collections.sort(propEditor.getCluSet().getClus());
        } else if ("proposition.cluSet.cluSets".equals(collectionGroup.getPropertyName())){
            //Sort the clus.
            RuleEditor ruleEditor = this.getRuleEditor(model);
            EnrolPropositionEditor propEditor = (EnrolPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);

            Collections.sort(propEditor.getCluSet().getCluSets(), new Comparator<CluSetInfo>(){

                @Override
                public int compare(CluSetInfo o1, CluSetInfo o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    public List<CluInformation> getCoursesInRange(MembershipQueryInfo membershipQuery) {
        List<CluInformation> clusInRange = new ArrayList<CluInformation>();
        if (membershipQuery != null) {
            SearchRequestInfo searchRequest = new SearchRequestInfo();
            searchRequest.setSearchKey(membershipQuery.getSearchTypeKey());
            searchRequest.setParams(membershipQuery.getQueryParamValues());
            SearchResultInfo searchResult = null;
            try {
                searchResult = this.getCluService().search(searchRequest, ContextUtils.getContextInfo());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            List<SearchResultRowInfo> rows = searchResult.getRows();
            for (SearchResultRowInfo row : rows) {
                List<SearchResultCellInfo> cells = row.getCells();
                CluInformation cluInformation = new CluInformation();
                for (SearchResultCellInfo cell : cells) {
                    if (cell.getKey().equals("lu.resultColumn.cluId")) {
                        cluInformation.setCluId(cell.getValue());
                    }
                    if (cell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                       cluInformation.setCode(cell.getValue());
                    }
                    if (cell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                        cluInformation.setTitle(cell.getValue());
                    }
                    if (cell.getKey().equals("lu.resultColumn.luOptionalDescr")) {
                        cluInformation.setDescription(cell.getValue());
                    }
                    if (cell.getKey().equals("lu.resultColumn.luOptionalState")) {
                        cluInformation.setState(cell.getValue());
                    }
                    if (cell.getKey().equals("lu.resultColumn.luOptionalVersionIndId")) {
                        cluInformation.setVerIndependentId(cell.getValue());
                    }
                }
                clusInRange.add(cluInformation);
            }
        }
        return clusInRange;
    }

    public List<KrmsSuggestDisplay> getCourseNamesForSuggest(String moduleName) {

        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo stateKeyParam = new SearchParamInfo();
        stateKeyParam.setKey("lu.queryParam.luOptionalState");

        List<String> stateValues = new ArrayList<String>();
        stateValues.add("Active");
        stateValues.add("Approved");
        stateKeyParam.setValues(stateValues);
        queryParamValueList.add(stateKeyParam);
        SearchParamInfo cluCodeParam = new SearchParamInfo();
        cluCodeParam.setKey("lu.queryParam.luOptionalCode");
        cluCodeParam.getValues().add(moduleName);
        queryParamValueList.add(cluCodeParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.current.quick");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = null;

        try {
            clus = getCluService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("lu.resultColumn.luOptionalCode".equals(cell.getKey())) {
                        display.setDisplayName(cell.getValue());
                    }
                }
                displays.add(display);
            }
        } catch (Exception e) {
            //do nothing
        }

        return displays;
    }

    public List<KrmsSuggestDisplay> getOrgDepartmentForSuggest(String orgName) {

        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo orgNameParam = new SearchParamInfo();
        orgNameParam.setKey("org.queryParam.orgOptionalLongName");
        orgNameParam.getValues().add(orgName);
        queryParamValueList.add(orgNameParam);
        SearchParamInfo orgTypeParam = new SearchParamInfo();
        orgTypeParam.setKey("org.queryParam.orgOptionalType");
        List<String> orgTypeValues = new ArrayList<String>();
        orgTypeValues.add("kuali.org.Department");
        orgTypeParam.setValues(orgTypeValues);
        queryParamValueList.add(orgTypeParam);
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("org.search.generic");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo orgs = null;

        try {
            orgs = getOrganizationService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : orgs.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("org.resultColumn.orgId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("org.resultColumn.orgOptionalLongName".equals(cell.getKey())) {
                        display.setDisplayName(cell.getValue());
                    }
                }
                displays.add(display);
            }
        } catch (Exception e) {
            //do nothing
        }

        return displays;
    }

    public List<KrmsSuggestDisplay> getTestNamesForSuggest(String testName) {

        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo testNameParam = new SearchParamInfo();
        testNameParam.setKey("cluset.queryParam.optionalName");
        testNameParam.getValues().add(testName);
        queryParamValueList.add(testNameParam);
        SearchParamInfo reusableParam = new SearchParamInfo();
        reusableParam.setKey("cluset.queryParam.optionalReusable");
        reusableParam.getValues().add(Boolean.TRUE.toString());
        queryParamValueList.add(reusableParam);
        SearchParamInfo cluSetTypeParam = new SearchParamInfo();
        cluSetTypeParam.setKey("cluset.queryParam.optionalType");
        cluSetTypeParam.getValues().add("kuali.cluSet.type.Test");
        queryParamValueList.add(cluSetTypeParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("cluset.search.generic");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = null;

        try {
            clus = getCluService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("cluset.resultColumn.cluSetId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("cluset.resultColumn.name".equals(cell.getKey())) {
                        display.setDisplayName(cell.getValue());
                    }
                }
                displays.add(display);
            }
        } catch (Exception e) {
            //do nothing
        }
        return displays;
    }

    public List<KrmsSuggestDisplay> getCourseSetForSuggest(String cluSetName) {
        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo cluSetParam = new SearchParamInfo();
        cluSetParam.setKey("cluset.queryParam.optionalName");
        cluSetParam.getValues().add(cluSetName);
        queryParamValueList.add(cluSetParam);
        SearchParamInfo reusableCluSet = new SearchParamInfo();
        reusableCluSet.setKey("cluset.queryParam.optionalReusable");
        reusableCluSet.getValues().add(Boolean.TRUE.toString());
        queryParamValueList.add(reusableCluSet);
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("cluset.search.generic");
        searchRequest.setParams(queryParamValueList);
        SearchParamInfo cluSetTypeParam = new SearchParamInfo();
        cluSetTypeParam.setKey("cluset.queryParam.optionalType");
        cluSetTypeParam.getValues().add("kuali.cluSet.type.CreditCourse");
        queryParamValueList.add(cluSetTypeParam);
        SearchResultInfo clus = null;
        try {
            clus = getCluService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("cluset.resultColumn.cluSetId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("cluset.resultColumn.name".equals(cell.getKey())) {
                        display.setDisplayName(cell.getValue());
                    }
                }
                displays.add(display);
            }
        } catch (Exception e) {
            //do nothing
        }
        return displays;
    }
    public List<KrmsSuggestDisplay> getProgramForSuggest(String programCode) {
        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        SearchParamInfo qpv1 = new SearchParamInfo();

        qpv1.setKey("lu.queryParam.luOptionalType");
        qpv1.getValues().add("kuali.lu.type.Program");
        searchParams.add(qpv1);
        SearchParamInfo qpv2 = new SearchParamInfo();
        qpv2.setKey("lu.queryParam.luOptionalCode");
        qpv2.getValues().add(programCode);
        searchParams.add(qpv2);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.generic");

        try {
            SearchResultInfo searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            if (searchResult.getRows().size() > 0) {
                for(SearchResultRowInfo srrow : searchResult.getRows()){
                    KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                    List<SearchResultCellInfo> srCells = srrow.getCells();
                    if(srCells != null && srCells.size() > 0){
                        for(SearchResultCellInfo srcell : srCells){
                            if (srcell.getKey().equals("lu.resultColumn.cluId")) {
                                display.setId(srcell.getValue());

                            }
                            else if(srcell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                                display.setDisplayName(srcell.getValue());
                            }

                        }
                    }
                    displays.add(display);
                }

            }

            return displays;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = CourseOfferingResourceLoader.loadCluService();
        }
        return cluService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    protected ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = ContextUtils.createDefaultContextInfo();
        }
        return contextInfo;
    }

    protected OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }

}
