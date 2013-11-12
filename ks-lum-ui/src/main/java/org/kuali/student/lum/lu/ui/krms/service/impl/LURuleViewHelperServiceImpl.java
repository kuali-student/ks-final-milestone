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
package org.kuali.student.lum.lu.ui.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.service.impl.RuleViewHelperServiceImpl;
import org.kuali.rice.krms.tree.RuleEditTreeBuilder;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.core.krms.dto.KSPropositionEditor;
import org.kuali.student.core.krms.tree.KSRuleEditTreeBuilder;
import org.kuali.student.core.krms.tree.KSRulePreviewTreeBuilder;
import org.kuali.student.core.krms.tree.KSRuleViewTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.dto.KrmsSuggestDisplay;
import org.kuali.student.lum.lu.ui.krms.tree.LURulePreviewTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.tree.LURuleViewTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetInformation;
import org.kuali.student.lum.lu.ui.krms.util.CluInformationHelper;
import org.kuali.student.lum.lu.ui.krms.util.CluSearchUtil;
import org.kuali.student.lum.lu.ui.krms.util.LUKRMSConstants;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Helpers Service with enrolment specific functions for the Rule Pages.
 *
 * @author Kuali Student Team
 */
public class LURuleViewHelperServiceImpl extends RuleViewHelperServiceImpl {

    private CluService cluService;
    private ContextInfo contextInfo;
    private OrganizationService organizationService;

    private CluInformationHelper cluInfoHelper;

    private KSRulePreviewTreeBuilder previewTreeBuilder;
    private KSRuleViewTreeBuilder viewTreeBuilder;
    private KSRuleEditTreeBuilder editTreeBuilder;

    @Override
    public Class<? extends PropositionEditor> getPropositionEditorClass() {
        return KSPropositionEditor.class;
    }

    @Override
    public void applyAuthorizationAndPresentationLogic(View view, Component component, ViewModel model) {
        super.applyAuthorizationAndPresentationLogic(view, component, model);

        if(component instanceof Group) {
            Group group = (Group) component;

            if(group.isReadOnly()) {
                processGroupItems(group);
            }
        }
    }

    protected void processGroupItems(Group group) {
        List<Field> fields = ComponentUtils.getComponentsOfType(group.getItems(), Field.class);
        for(Field field : fields) {
            field.setReadOnly(true);
        }

        List<Action> actions = ComponentUtils.getComponentsOfTypeDeep(group.getItems(), Action.class);
        for(Action action : actions) {
            action.setRender(false);
        }
    }

    @Override
    public PropositionEditor copyProposition(PropositionEditor oldProposition) {
        try {
            LUPropositionEditor newProposition = (LUPropositionEditor) this.copyPropositionEditor(oldProposition);

            //Set the cluset to null to force the builder to create a new cluset.
            nullifyCluSetInfo(newProposition);

            //Use a deepcopy to create new references to inner objects such as string.
            return (PropositionEditor) ObjectUtils.deepCopy(newProposition);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setTypeForCompoundOpCode(PropositionEditor proposition, String compoundOpCode) {
        super.setTypeForCompoundOpCode(proposition, compoundOpCode);
        if (LogicalOperator.AND.getCode().equalsIgnoreCase(compoundOpCode)) {
            proposition.setType(KSKRMSServiceConstants.PROPOSITION_TYPE_COMPOUND_AND);
        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(compoundOpCode)) {
            proposition.setType(KSKRMSServiceConstants.PROPOSITION_TYPE_COMPOUND_OR);
        }
        try {
            KrmsTypeDefinition type = KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService().getTypeByName(StudentIdentityConstants.KS_NAMESPACE_CD, proposition.getType());
            proposition.setTypeId(type.getId());
        } catch (Exception e) {
            //ignore if service not available.
        }

    }

    /**
     * Method to recursively set proposition's cluSetInfo to null.
     *
     * @param propositionEditor
     */
    protected void nullifyCluSetInfo(LUPropositionEditor propositionEditor) {

        //Set cluSetInfo recursively to null to force builder to create new cluset.
        if(propositionEditor.getCluSet()!=null){
            propositionEditor.getCluSet().setCluSetInfo(null);
        } else if(propositionEditor.getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
            for(int i = 0; i < propositionEditor.getCompoundEditors().size(); i++) {
                LUPropositionEditor prop = (LUPropositionEditor) propositionEditor.getCompoundEditors().get(i);
                if(prop.getCluSet() != null) {
                    prop.getCluSet().setCluSetInfo(null);
                } else if(prop.getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
                    nullifyCluSetInfo(prop);
                }
            }
        }
    }

    protected void checkNaturalLanguageForTree(RuleEditor ruleEditor) {
        if ((ruleEditor !=null) && (ruleEditor.getProposition()!=null)){
            PropositionEditor originalRoot = ruleEditor.getPropositionEditor();
            if (!originalRoot.getNaturalLanguage().containsKey(this.getEditTreeBuilder().getNaturalLanguageUsageKey())) {
                this.getNaturalLanguageHelper().setNaturalLanguageTreeForUsage(originalRoot, this.getEditTreeBuilder().getNaturalLanguageUsageKey(), StudentIdentityConstants.KS_NAMESPACE_CD);
            }
        }
    }

    /**
     * Compares CO and CLU with each other for the display of a info message.
     *
     * @param original
     * @param compare
     * @return
     */
    @Override
    public Boolean compareProposition(PropositionEditor original, PropositionEditor compare) {
        //If proposition contains cluset or programCluset, skip super compare method else use super compare method for all simple propositions
        if(((LUPropositionEditor) original).getCluSet() == null && ((LUPropositionEditor) original).getProgCluSet() == null) {
            if(!super.compareProposition(original, compare)) {
                return false;
            }
        } //If proposition contains cluset or programCluset, compare typeId's
        else if(!original.getTypeId().equals(compare.getTypeId())) {
            return false;
        } //If proposition contains cluset or programCluset and is not a compound proposition, compare multicourse proposition properties
        else if(!original.getPropositionTypeCode().equals("C")) {
            LUPropositionEditor enrolOriginal = (LUPropositionEditor) original;

            //Populate compare proposition cluSetInformation for comparison
            if(enrolOriginal.getCluSet() != null) {
                if(enrolOriginal.getCluSet().getParent() == null) {
                    TermEditor term = new TermEditor(PropositionTreeUtil.getTermParameter(compare.getParameters()).getTermValue());
                    for(TermParameterEditor termParameterEditor : term.getEditorParameters()) {
                        if(termParameterEditor.getName().equals(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLUSET_KEY)) {
                            enrolOriginal.getCluSet().setParent(this.getCluInfoHelper().getCluSetInformation(termParameterEditor.getValue()));
                            break;
                        }
                    }
                }
                //If compare and original propositions are not null compare CluSetInformation
                if(enrolOriginal.getCluSet() != null && enrolOriginal.getCluSet().getParent() != null) {
                    //Compare propositions CluSetInformation clu's
                    if(!enrolOriginal.getCluSet().getCluDelimitedString().equals(enrolOriginal.getCluSet().getParent().getCluDelimitedString())) {
                        return false;
                    }
                    //Compare propositions CluSetInformation cluSets
                    if(!enrolOriginal.getCluSet().getCluSetDelimitedString().equals(enrolOriginal.getCluSet().getParent().getCluSetDelimitedString())) {
                        return false;
                    }
                }
            }

            //Populate compare proposition Program CluSetInformation for comparison
            if(enrolOriginal.getProgCluSet() != null) {
                if(enrolOriginal.getProgCluSet().getParent() == null) {
                    TermEditor term = new TermEditor(PropositionTreeUtil.getTermParameter(compare.getParameters()).getTermValue());
                    for(TermParameterEditor termParameterEditor : term.getEditorParameters()) {
                        if(termParameterEditor.getName().equals(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLUSET_KEY)) {
                            enrolOriginal.getProgCluSet().setParent(this.getCluInfoHelper().getCluSetInformation(termParameterEditor.getValue()));
                            break;
                        }
                    }
                }
                //If compare and original propositions are not null compare ProgramCluSetInformation
                if(enrolOriginal.getProgCluSet() != null && enrolOriginal.getProgCluSet().getParent() != null) {
                    //Compare propositions ProgramCluSetInformation clu's
                    if(!enrolOriginal.getProgCluSet().getCluDelimitedString().equals(enrolOriginal.getProgCluSet().getParent().getCluDelimitedString())) {
                        return false;
                    }
                    //Compare propositions ProgramCluSetInformation cluSets
                    if(!enrolOriginal.getProgCluSet().getCluSetDelimitedString().equals(enrolOriginal.getProgCluSet().getParent().getCluSetDelimitedString())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model,
                                               Object addLine) {
        if(LUKRMSConstants.KSKRMS_PROPERTY_NAME_CLUS.equals(collectionGroup.getPropertyName())){
            //Check if this is a valid clu.
            CluInformation clu = (CluInformation) addLine;
            if((clu.getCode()==null)||(clu.getCode().isEmpty())){
                collectionGroup.initializeNewCollectionLine(view, model, collectionGroup, true);
                GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroup.getId(), LUKRMSConstants.KSKRMS_MSG_ERROR_APPROVED_COURSE_REQUIRED);
                return false;
            } else {
                // convert term-code to UPPERCASE
                clu.setCode(clu.getCode().toUpperCase());
            }

            CluInformation searchClu = this.getCluInfoHelper().getCluInfoForCodeAndType(clu.getCode(), CluSearchUtil.getCluTypesForCourse());
            if(searchClu==null){
                collectionGroup.initializeNewCollectionLine(view, model, collectionGroup, true);
                GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroup.getId(), LUKRMSConstants.KSKRMS_MSG_ERROR_APPROVED_COURSE_CODE_INVALID);
                return false;
            } else {
                clu.setCluId(searchClu.getCluId());
                clu.setVerIndependentId(searchClu.getVerIndependentId());
                clu.setShortName(searchClu.getShortName());
                clu.setDescription(searchClu.getDescription());
            }

            //Check if this clu is not already in the collection
            RuleEditor ruleEditor = this.getRuleEditor(model);
            LUPropositionEditor propEditor = (LUPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);
            for(CluInformation cluInformation : propEditor.getCluSet().getClus()){
                if(cluInformation.getCluId().equals(clu.getCluId())){
                    collectionGroup.initializeNewCollectionLine(view, model, collectionGroup, true);
                    return false;
                }
            }
        } else if (LUKRMSConstants.KSKRMS_PROPERTY_NAME_CLUSETS.equals(collectionGroup.getPropertyName())){
            //Check if this is a valid clu.
            CluSetInformation cluSet = (CluSetInformation) addLine;
            if((cluSet.getCluSetInfo().getId() == null)||(cluSet.getCluSetInfo().getId().isEmpty())){
                GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroup.getId(), LUKRMSConstants.KSKRMS_MSG_ERROR_COURSESETS_REQUIRED);
                return false;
            }

            CluSetInfo searchCluSet = this.getCluInfoHelper().getCluSetForId(cluSet.getCluSetInfo().getId());
            if(searchCluSet==null){
                collectionGroup.initializeNewCollectionLine(view, model, collectionGroup, true);
                GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroup.getId(), LUKRMSConstants.KSKRMS_MSG_ERROR_APPROVED_COURSE_CODE_INVALID);
                return false;
            } else {
                cluSet.setCluSetInfo(searchCluSet);
            }

            //Check if this clu is not already in the collection
            RuleEditor ruleEditor = this.getRuleEditor(model);
            LUPropositionEditor propEditor = (LUPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);
            for(CluSetInformation cluSetInfo : propEditor.getCluSet().getCluSets()){
                if(cluSetInfo.getCluSetInfo().getId().equals(cluSet.getCluSetInfo().getId())){
                    return false;
                }
            }
        }
        else if(LUKRMSConstants.KSKRMS_PROPERTY_NAME_PROG_CLUS.equals(collectionGroup.getPropertyName())){
            //Check if this is a valid clu.
            CluInformation clu = (CluInformation) addLine;
            if((clu.getCode()==null)||(clu.getCode().isEmpty())){
                collectionGroup.initializeNewCollectionLine(view, model, collectionGroup, true);
                GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroup.getId(), LUKRMSConstants.KSKRMS_MSG_ERROR_APPROVED_PROGRAM_REQUIRED);
                return false;
            } else {
                // convert term-code to UPPERCASE
                clu.setCode(clu.getCode().toUpperCase());
            }

            CluInformation searchClu = this.getCluInfoHelper().getCluInfoForCodeAndType(clu.getCode(), CluSearchUtil.getCluTypesForProgram());
            if(searchClu==null){
                collectionGroup.initializeNewCollectionLine(view, model, collectionGroup, true);
                GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroup.getId(), LUKRMSConstants.KSKRMS_MSG_ERROR_APPROVED_PROGRAM_CODE_INVALID);
                return false;
            } else {
                clu.setCluId(searchClu.getCluId());
                clu.setVerIndependentId(searchClu.getVerIndependentId());
                clu.setShortName(searchClu.getShortName());
                clu.setDescription(searchClu.getDescription());
            }

            //Check if this clu is not already in the collection
            RuleEditor ruleEditor = this.getRuleEditor(model);
            LUPropositionEditor propEditor = (LUPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);
            for(CluInformation cluInformation : propEditor.getProgCluSet().getClus()){
                if(cluInformation.getCluId().equals(clu.getCluId())){
                    collectionGroup.initializeNewCollectionLine(view, model, collectionGroup, true);
                    return false;
                }
            }
        }

        return true;
    }

    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine,
                                       boolean isValidLine) {

        if(LUKRMSConstants.KSKRMS_PROPERTY_NAME_CLUS.equals(collectionGroup.getPropertyName())){
            //Sort the clus.
            RuleEditor ruleEditor = this.getRuleEditor(model);
            LUPropositionEditor propEditor = (LUPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);

            CluInformation clu = (CluInformation) addLine;
            if(clu.getCluId() != null){
            clu.setCredits(this.getCluInfoHelper().getCreditInfo(clu.getCluId()));
            Collections.sort(propEditor.getCluSet().getClus());

            }
        } else if (LUKRMSConstants.KSKRMS_PROPERTY_NAME_CLUSETS.equals(collectionGroup.getPropertyName())){
            //Set the clus on the wrapper object.
            CluSetInformation cluSet = (CluSetInformation) addLine;
            if(cluSet.getCluSetInfo().getId() != null) {
                completeCluSetInformation(cluSet);

                //Sort the clus.
                RuleEditor ruleEditor = this.getRuleEditor(model);
                    LUPropositionEditor propEditor = (LUPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);
                Collections.sort(propEditor.getCluSet().getCluSets(), new Comparator<CluSetInformation>(){

                @Override
                public int compare(CluSetInformation o1, CluSetInformation o2) {
                        return o1.getCluSetInfo().getName().compareTo(o2.getCluSetInfo().getName());
                    }
                });
            }
        }
        else if(LUKRMSConstants.KSKRMS_PROPERTY_NAME_PROG_CLUS.equals(collectionGroup.getPropertyName())){
            //Sort the clus.
            RuleEditor ruleEditor = this.getRuleEditor(model);
            LUPropositionEditor propEditor = (LUPropositionEditor)PropositionTreeUtil.getProposition(ruleEditor);

            CluInformation clu = (CluInformation) addLine;
            if(clu.getCluId() != null){
                clu.setCredits(this.getCluInfoHelper().getCreditInfo(clu.getCluId()));
                Collections.sort(propEditor.getProgCluSet().getClus());
            }
        }
    }

    private void completeCluSetInformation(CluSetInformation cluSet) {
        try {
            for(String subCluSetId : cluSet.getCluSetInfo().getCluSetIds()){
                cluSet.getCluSets().add(this.getCluInfoHelper().getCluSetInformation(subCluSetId));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cluSet.setClus(this.getCluInfoHelper().getCluInfos(cluSet.getCluSetInfo().getCluIds()));
    }

    public List<CluInformation> getCoursesInRange(MembershipQueryInfo membershipQuery) {
        return this.getCluInfoHelper().getCluInfosWithDetailForQuery(membershipQuery);
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
        cluSetTypeParam.getValues().add(CluServiceConstants.CLUSET_TYPE_TEST);
        queryParamValueList.add(cluSetTypeParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("cluset.search.generic");
        searchRequest.setParams(queryParamValueList);

        try {
            SearchResultInfo clus = getCluService().search(searchRequest, getContextInfo());
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
            throw new RuntimeException(e);
        }
        return displays;
    }

    protected CluInformationHelper getCluInfoHelper() {
        if (cluInfoHelper == null) {
            cluInfoHelper = new CluInformationHelper();
            cluInfoHelper.setCluService(this.getCluService());
            LRCService lrcService = GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
            cluInfoHelper.setLrcService(lrcService);
        }
        return cluInfoHelper;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    protected ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = ContextUtils.createDefaultContextInfo();
        }
        return contextInfo;
    }

    protected OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }

    @Override
    public RulePreviewTreeBuilder getPreviewTreeBuilder() {
        if (previewTreeBuilder == null) {
            previewTreeBuilder = new LURulePreviewTreeBuilder();
            previewTreeBuilder.setNlHelper(this.getNaturalLanguageHelper());
        }
        return previewTreeBuilder;
    }

    @Override
    protected RuleViewTreeBuilder getViewTreeBuilder() {
        if (viewTreeBuilder == null) {
            viewTreeBuilder = new LURuleViewTreeBuilder();
            viewTreeBuilder.setNlHelper(this.getNaturalLanguageHelper());
        }
        return viewTreeBuilder;
    }

    @Override
    protected RuleEditTreeBuilder getEditTreeBuilder() {
        if (editTreeBuilder == null) {
            editTreeBuilder = new KSRuleEditTreeBuilder();
            editTreeBuilder.setNlHelper(this.getNaturalLanguageHelper());
        }
        return editTreeBuilder;
    }

}
