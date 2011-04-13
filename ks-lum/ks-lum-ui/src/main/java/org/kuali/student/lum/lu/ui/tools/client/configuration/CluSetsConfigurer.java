/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.assembly.data.Data.DataValue;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.DisplayMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.SwapSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.common.client.widgets.CluSetRangeDataHelper;
import org.kuali.student.lum.common.client.widgets.CluSetRangeModelUtil;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class CluSetsConfigurer {

    private boolean WITH_DIVIDER = true;

    public static final String CREATE_CLUSET_MGT_MODEL = "createCluSetManagementModel";
    public static final String EDIT_CLUSET_MGT_MODEL = "editCluSetManagementModel";
    public static final String VIEW_CLUSET_MGT_MODEL = "viewCluSetManagerModel";
    public static final String EDIT_SEARCH_CLUSET_MGT_MODEL = "editSearchCluSetManagementModel";
    public static final String VIEW_SEARCH_CLUSET_MGT_MODEL = "viewSearchCluSetManagementModel";
    private String editSearchCluSetId = null;
    private String viewSearchCluSetId = null;
    private Map<String, CluSetEditOptionList> cluSetEditOptionsMap =
        new HashMap<String, CluSetEditOptionList>();


    private DataModelDefinition modelDefinition;

    public enum CluSetSections{
        CREATE_CLU_SET, EDIT_CLU_SET, VIEW_CLU_SET
    }

    public void setModelDefinition(DataModelDefinition modelDefinition){
    	this.modelDefinition = modelDefinition;
    }

//    public void configureCluSetManager(ConfigurableLayout layout) {
//        SectionView createCluSetView = createCluSetSection();
//        SectionView editCluSetView = editCluSetSection();
//        CluSetsModelDispatcher createCluSetModelDispatcher = new CluSetsModelDispatcher();
//        CluSetsModelDispatcher editCluSetModelDispatcher = new CluSetsModelDispatcher();
//
//        createCluSetModelDispatcher.setModelId(CluSetsConfigurer.CREATE_CLUSET_MGT_MODEL);
//        createCluSetView.setLayoutController(createCluSetModelDispatcher);
//        createCluSetView.setController(createCluSetModelDispatcher);
//
//        editCluSetModelDispatcher.setModelId(CluSetsConfigurer.EDIT_CLUSET_MGT_MODEL);
//        editCluSetView.setLayoutController(editCluSetModelDispatcher);
//        editCluSetView.setController(editCluSetModelDispatcher);
////        addStartSection(layout);
//        layout.addSection(new String[] {"Manage CLU Sets", getLabel(ToolsConstants.NEW_CLU_SET_LABEL_KEY)}, createCluSetView);
//        layout.addSection(new String[] {"Manage CLU Sets", getLabel(ToolsConstants.NEW_CLU_SET_LABEL_KEY)}, editCluSetView);
//        layout.addSection(new String[] {"View CLU Sets"}, viewCluSetSection());
//    }

    private void addClusetDetailsSections(SectionView parentView, final String modelId) {
        VerticalSection defineCluSet = initSection(getH3Title(ToolsConstants.NEW_CLU_SET_INFO), WITH_DIVIDER);
        CluSetEditOptionList cluSetEditOptions = new CluSetEditOptionList();
        cluSetEditOptionsMap.put(modelId, cluSetEditOptions);
        SwapSection clusetDetails = new SwapSection(
                cluSetEditOptions,
                new ConfirmationDialog("Delete Clu Set Details",
                        "You are about to delete clu set details.  Continue?")
                );
        
        ModelIdPlaceHolder modelIdObj = new ModelIdPlaceHolder(modelId);

        // ****** Add Approved Clus *******
        Section approvedClusSection = new VerticalSection();
        addField(approvedClusSection, ToolsConstants.CLU_SET_APPROVED_CLUS_FIELD, generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_APPROVED_COURSE)).setModelId(modelId);
        clusetDetails.addSection(approvedClusSection, ToolsConstants.CLU_SET_SWAP_APPROVED_CLUS);
        // END OF items related to Add Approved Clus

        // ****** Add Proposed Clus *******
        Section proposedClusSection = new VerticalSection();
        addField(proposedClusSection, ToolsConstants.CLU_SET_PROPOSED_CLUS_FIELD, generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_PROPOSED_COURSE)).setModelId(modelId);
        clusetDetails.addSection(proposedClusSection, ToolsConstants.CLU_SET_SWAP_PROPOSED_CLUS);
        // END OF items related to Add Approved Clus

        // ****** Add Clu Range *******
        //TODO add cluset and clurange here
        Section cluRangeSection = new VerticalSection();
        final Picker cluSetRangePicker = configureSearch(ToolsConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD);
        final FieldDescriptor cluRangeFieldEditDescriptor = addField(cluRangeSection, ToolsConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD, generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_RANGE), cluSetRangePicker);
        final CluSetRangeDataHelper clusetRangeModelHelper = new CluSetRangeDataHelper();
        final KSItemLabel clusetRangeLabel = new KSItemLabel(true, clusetRangeModelHelper);
        clusetRangeLabel.getElement().getStyle().setProperty("border", "solid 1px #cdcdcd");
        final FieldDescriptor cluRangeFieldDescriptor = addField(cluRangeSection, ToolsConstants.CLU_SET_CLU_SET_RANGE_FIELD, null, clusetRangeLabel);
        cluSetRangePicker.getSearchWindow().addActionCompleteCallback(new Callback<Boolean>() {
        
            @Override
            public void exec(Boolean result) {
                cluSetRangePicker.getSearchWindow().hide();
                final LayoutController parent = LayoutController.findParentLayout(cluSetRangePicker);
                if(parent != null){
                    parent.requestModel(modelId, new ModelRequestCallback<DataModel>() {
                        @Override
                        public void onModelReady(DataModel model) {
//                            ((ModelWidgetBinding)cluRangeFieldDescriptor.getModelWidgetBinding()).setWidgetValue(widget, model, path)
//                            CluSetHelper cluSetHelper = CluSetHelper.wrap(model.getRoot());
//                            cluSetHelper.setCluRangeParams(value)
                            SearchRequest searchRequest = cluSetRangePicker.getSearchWindow()
                                .getSearchRequest();
                            String selectedLookupName = cluSetRangePicker.getSearchWindow()
                                .getSelectedLookupName();
                            Data searchRequestData = CluSetRangeModelUtil.INSTANCE.
                                toData(searchRequest, null);
                            DataValue dataValue = new DataValue(searchRequestData);
                            LookupMetadata lookupMetadata = null;
                            
                            // look for the lookupMetaData corresponding to the searchRequest
                            List<LookupMetadata> lookupMDs = new ArrayList<LookupMetadata>();
                            lookupMDs.add(cluSetRangePicker.getInitLookupMetadata());
                            lookupMetadata = findLookupMetadata(selectedLookupName, 
                                    lookupMDs);
                            if (lookupMetadata == null || 
                                    !nullSafeEquals(lookupMetadata.getName(), 
                                            selectedLookupName)) {
                                lookupMetadata = findLookupMetadata(selectedLookupName, 
                                        cluSetRangePicker.getAdditionalLookupMetadata());
                            }
                            
                            clusetRangeModelHelper.setLookupMetadata(lookupMetadata);
                            clusetRangeLabel.setValue(dataValue);
                        }

                        @Override
                        public void onRequestFail(Throwable cause) {
                            GWT.log("Unable to retrieve model" + cluRangeFieldDescriptor.getFieldKey(), null);
                        }
                        
                    });
                }
            }
        });
        clusetDetails.addSection(cluRangeSection, ToolsConstants.CLU_SET_SWAP_CLU_SET_RANGE);
        // END OF items related to Add Clu Range
        // ****** Add cluSets *******
        Section cluSetSection = new VerticalSection();
        addField(cluSetSection, ToolsConstants.CLU_SET_CLU_SETS_FIELD, generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_CLUSET)).setModelId(modelId);
        clusetDetails.addSection(cluSetSection, ToolsConstants.CLU_SET_SWAP_CLU_SETS);
        // END OF items related to Add CluSets
        
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_TYPE_FIELD);
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_ORGANIZATION_FIELD, generateMessageInfo(ToolsConstants.ORGANIZATION));
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_NAME_FIELD, generateMessageInfo(ToolsConstants.TITLE));
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_DESCRIPTION_FIELD, generateMessageInfo(ToolsConstants.DESCRIPTION), new KSTextArea());
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_EFF_DATE_FIELD, generateMessageInfo(ToolsConstants.EFFECTIVE_DATE), new KSDatePicker());
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_EXP_DATE_FIELD, generateMessageInfo(ToolsConstants.EXPIRATION_DATE), new KSDatePicker());
        
        parentView.addWidget(cluSetEditOptions);
        parentView.addSection(clusetDetails);
        parentView.addSection(defineCluSet);
    }
    
    private static boolean nullSafeEquals(Object obj1, Object obj2) {
        return (obj1 == null && obj2 == null ||
                obj1 != null && obj2 != null && obj1.equals(obj2));
    }
    
    private static LookupMetadata findLookupMetadata(String selectedLookupName, 
            List<LookupMetadata> lookupMetadatas) {
        LookupMetadata result = null;
        if (lookupMetadatas != null) {
            for (LookupMetadata lookupMetadata : lookupMetadatas) {
                if (nullSafeEquals(lookupMetadata.getName(), selectedLookupName)) {
                    result = lookupMetadata;
                }
            }
        }
        return result;
    }

    private SectionView createCluSetSection() {
        // TODO change this to custom layoutcontroller here to support multiple model ids?
        VerticalSectionView section = initNestedSectionView(CluSetSections.CREATE_CLU_SET, ToolsConstants.NEW_CLU_SET, CREATE_CLUSET_MGT_MODEL);
        addClusetDetailsSections(section, CluSetsConfigurer.CREATE_CLUSET_MGT_MODEL);
        return section;
	}

    private boolean hasCluSetRange(Data cluSetRangeData) {
        boolean hasCluSetRange = false;
        MembershipQueryInfo membershipQueryInfo = 
            CluSetRangeModelUtil.INSTANCE.toMembershipQueryInfo(cluSetRangeData);
        if (membershipQueryInfo != null) {
            hasCluSetRange = true;
        }
        return hasCluSetRange;
    }
    
    private boolean hasClus(Data clusData) {
        boolean clusExist = false;
        if (clusData != null) {
            for (Data.Property p : clusData) {
                if(!"_runtimeData".equals(p.getKey())){
                    String cluId = p.getValue();
                    if (cluId != null && !cluId.trim().isEmpty()) {
                        clusExist = true;
                        break;
                    }
                }
            }
        }
        return clusExist;
    }
    
    private boolean hasCluSets(Data clusetData) {
        boolean cluSetsExist = false;
        if (clusetData != null) {
            for (Data.Property p : clusetData) {
                if(!"_runtimeData".equals(p.getKey())){
                    String cluSetId = p.getValue();
                    if (cluSetId != null && !cluSetId.trim().isEmpty()) {
                        cluSetsExist = true;
                        break;
                    }
                }
            }
        }
        return cluSetsExist;
    }
    
    private boolean hasData(String queryPath, Data queryData) {
        boolean hasData = false;
        if (queryPath != null && (queryPath.equals(ToolsConstants.CLU_SET_APPROVED_CLUS_FIELD) ||
                queryPath.equals(ToolsConstants.CLU_SET_PROPOSED_CLUS_FIELD))) {
            hasData = hasClus(queryData);
        } else if (queryPath != null && queryPath.equals(ToolsConstants.CLU_SET_CLU_SETS_FIELD)) {
            hasData = hasCluSets(queryData);
        } else if (queryPath != null && queryPath.equals(ToolsConstants.CLU_SET_CLU_SET_RANGE_FIELD)) {
            hasData = hasCluSetRange(queryData);
        } else {
            throw new IllegalArgumentException("Unexpected queryPath: " + queryPath);
        }
        return hasData;
    }
    
    private void selectCluSetOptionList(DataModel model, CluSetEditOptionList cluSetEditOptions) {
        selectCluSetOption(model, cluSetEditOptions, ToolsConstants.CLU_SET_APPROVED_CLUS_FIELD,
                ToolsConstants.CLU_SET_SWAP_APPROVED_CLUS);
        selectCluSetOption(model, cluSetEditOptions, ToolsConstants.CLU_SET_PROPOSED_CLUS_FIELD, 
                ToolsConstants.CLU_SET_SWAP_PROPOSED_CLUS);
        selectCluSetOption(model, cluSetEditOptions, ToolsConstants.CLU_SET_CLU_SET_RANGE_FIELD, 
                ToolsConstants.CLU_SET_SWAP_CLU_SET_RANGE);
        selectCluSetOption(model, cluSetEditOptions, ToolsConstants.CLU_SET_CLU_SETS_FIELD,
                ToolsConstants.CLU_SET_SWAP_CLU_SETS);
    }
    
    private void selectCluSetOption(DataModel model, CluSetEditOptionList cluSetEditOptions,
            String queryPath, String selectionId) {
        final QueryPath qPath = QueryPath.parse(queryPath);
        Data queryData = model.get(qPath);
        if (hasData(queryPath, queryData)) {
            cluSetEditOptions.selectItem(selectionId);
        } else {
            cluSetEditOptions.deSelectItem(selectionId);
        }
    }
    
    private SectionView editCluSetSection() {
        // TODO change this to custom layoutcontroller here to support multiple model ids?
        ModelIdPlaceHolder modelIdObj = new ModelIdPlaceHolder(CluSetsConfigurer.EDIT_CLUSET_MGT_MODEL);
        final VerticalSectionView section = initNestedSectionView(CluSetSections.EDIT_CLU_SET, ToolsConstants.EDIT_CLU_SET, EDIT_CLUSET_MGT_MODEL);
        VerticalSection oversight = initSection(getH3Title(""), WITH_DIVIDER);
        Picker cluSetPicker = configureSearch(ToolsConstants.SEARCH_CLU_SET);
        cluSetPicker.addBasicSelectionCompletedCallback(new Callback<SelectedResults>() {
            @Override
            public void exec(SelectedResults result) {
                if (result != null && result.getReturnKey() != null && result.getReturnKey().trim().length() > 0) {
                    editSearchCluSetId = result.getReturnKey();
                    final CluSetEditOptionList cluSetEditOptions = 
                        cluSetEditOptionsMap.get(CluSetsConfigurer.EDIT_CLUSET_MGT_MODEL);
                    // TODO retrieve cluset by id and
                    final LayoutController parent = LayoutController.findParentLayout(section);
                    if(parent != null){
                        parent.requestModel(CluSetsConfigurer.EDIT_SEARCH_CLUSET_MGT_MODEL, new ModelRequestCallback<DataModel>() {
                            @Override
                            public void onModelReady(DataModel model) {
                                selectCluSetOptionList(model, cluSetEditOptions);
                                section.updateWidgetData(model);
                            }

                            @Override
                            public void onRequestFail(Throwable cause) {
                                GWT.log("Unable to retrieve model" + EDIT_SEARCH_CLUSET_MGT_MODEL.toString(), null);
                            }

                        });
                    }
                }
            }
        });
        //addField(oversight, COURSE + "/" + ACADEMIC_SUBJECT_ORGS);
        addField(modelIdObj, oversight, ToolsConstants.SEARCH_CLU_SET, generateMessageInfo("Search Clu Set"), cluSetPicker);
        
        section.addSection(oversight);
        addClusetDetailsSections(section, CluSetsConfigurer.EDIT_CLUSET_MGT_MODEL);
        return section;
	}

    private SectionView viewCluSetSection() {
        final VerticalSectionView sectionView = initVerticalSectionView(CluSetSections.VIEW_CLU_SET, ToolsConstants.VIEW_CLU_SET, VIEW_CLUSET_MGT_MODEL);


        Picker cluSetPicker = configureSearch(ToolsConstants.SEARCH_CLU_SET);
        cluSetPicker.addBasicSelectionCompletedCallback(new Callback<SelectedResults>() {
            @Override
            public void exec(SelectedResults result) {
                if (result != null && result.getReturnKey() != null && result.getReturnKey().trim().length() > 0) {
                    viewSearchCluSetId = result.getReturnKey();
                    // TODO retrieve cluset by id and
                    final LayoutController parent = LayoutController.findParentLayout(sectionView);
                    if(parent != null){
                        parent.requestModel(CluSetsConfigurer.VIEW_SEARCH_CLUSET_MGT_MODEL, new ModelRequestCallback<DataModel>() {
                            @Override
                            public void onModelReady(DataModel model) {
                                sectionView.updateWidgetData(model);
                                
                                //FIXME do something else here, rework logic
                                //rootSection.redraw();
                            }

                            @Override
                            public void onRequestFail(Throwable cause) {
                                GWT.log("Unable to retrieve model" + VIEW_SEARCH_CLUSET_MGT_MODEL.toString(), null);
                            }

                        });
                    }
                }
            }
        });
        addField(sectionView, ToolsConstants.SEARCH_CLU_SET, generateMessageInfo(""), cluSetPicker);

        VerticalSection nameSection = initSection(null, !WITH_DIVIDER);
        nameSection.addStyleName(LUUIConstants.STYLE_BOTTOM_DIVIDER);
        addField(nameSection, ToolsConstants.CLU_SET_NAME_FIELD, generateMessageInfo(ToolsConstants.CLU_SET_NAME), new KSLabel());
        sectionView.addSection(nameSection);

        VerticalSection descriptionSection = initSection(null, !WITH_DIVIDER);
        descriptionSection.addStyleName(LUUIConstants.STYLE_BOTTOM_DIVIDER);
        addField(descriptionSection, ToolsConstants.CLU_SET_DESCRIPTION_FIELD, generateMessageInfo("Description"), new KSLabel());
        sectionView.addSection(descriptionSection);
        
        VerticalSection expirationDateSection = initSection(null, !WITH_DIVIDER);
        expirationDateSection.addStyleName(LUUIConstants.STYLE_BOTTOM_DIVIDER);
        addField(expirationDateSection, ToolsConstants.CLU_SET_EXP_DATE_FIELD, generateMessageInfo(ToolsConstants.EFFECTIVE_DATE), new KSLabel());
        sectionView.addSection(expirationDateSection);
        
        VerticalSection clusSection = initSection(null, !WITH_DIVIDER);
        clusSection.addStyleName(LUUIConstants.STYLE_BOTTOM_DIVIDER);
        addField(clusSection, ToolsConstants.CLU_SET_ALL_CLUS_FIELD, 
                generateMessageInfo("Individual Courses"),
                new TranslatedStringList(ToolsConstants.CLU_SET_ALL_CLUS_FIELD));
        sectionView.addSection(clusSection);
        
        VerticalSection cluSetsSection = initSection(null, !WITH_DIVIDER);
        cluSetsSection.addStyleName(LUUIConstants.STYLE_BOTTOM_DIVIDER);
        addField(cluSetsSection, ToolsConstants.CLU_SET_CLU_SETS_FIELD, 
                generateMessageInfo("CLU Sets"),
                new TranslatedStringList(ToolsConstants.CLU_SET_CLU_SETS_FIELD));
        sectionView.addSection(cluSetsSection);
        
        VerticalSection cluRangeDetailsSection = initSection(null, !WITH_DIVIDER);
        cluRangeDetailsSection.addStyleName(LUUIConstants.STYLE_BOTTOM_DIVIDER);
        addField(cluRangeDetailsSection, ToolsConstants.CLU_SET_CLUSET_RANGE_VIEW_DETAILS_FIELD, 
                generateMessageInfo("Course Range"),
                new TranslatedStringList(ToolsConstants.CLU_SET_CLUSET_RANGE_VIEW_DETAILS_FIELD));
        sectionView.addSection(cluRangeDetailsSection);

        return sectionView;
    }

    private class TranslatedStringList extends DisplayMultiplicityComposite {
        private final String parentPath;
        public TranslatedStringList(String parentPath){
            this.parentPath = parentPath;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, CreditCourseConstants._RUNTIME_DATA, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, "id-translation", null, new KSLabel(), path);

            return ns;
        }
    }

    private VerticalSectionView initVerticalSectionView(Enum<?> viewEnum, String labelKey, String modelId) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), modelId);
        section.addStyleName(LUUIConstants.STYLE_SECTION);
        //section.setSectionTitle(getH1Title(labelKey));

        return section;
    }

    private VerticalSectionView initNestedSectionView (Enum<?> viewEnum, String labelKey, String modelId) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), modelId);
        section.addStyleName(LUUIConstants.STYLE_SECTION);
        //section.setSectionTitle(getH1Title(labelKey));

        return section;
    }

    private static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection(title);
        section.addStyleName(LUUIConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUUIConstants.STYLE_SECTION_DIVIDER);
        return section;
    }

    private String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel("clusetmanagement", "clusetmanagement", "draft", labelKey);
    }

    private SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    }

    private Picker configureSearch(String fieldKey) {
        QueryPath path = QueryPath.concat(null, fieldKey);
        Metadata metaData = modelDefinition.getMetadata(path);
        Picker picker = new Picker(metaData.getInitialLookup(), metaData.getAdditionalLookups());
        return picker;
    }

//    public class CourseList extends UpdatableMultiplicityComposite {
//        private final String parentPath;
//        public CourseList(String parentPath){
//            super(StyleType.TOP_LEVEL_GROUP);
//            this.parentPath = parentPath;
//            setAddItemLabel("Add Course");
////            setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
//        }
//
//        public Widget createItem() {
//            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
//            GroupSection item = new GroupSection();
//            addField(item, "id", "" , path);
//            addField(item, "name", "", path);
//            return item;
//        }
//    }

    public static class Picker extends KSPicker {

        private String name;
        private LookupMetadata initLookupMetadata;
        private List<LookupMetadata> additionalLookupMetadata;

        public Picker(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata) {
            super(inLookupMetadata, additionalLookupMetadata);
            this.initLookupMetadata = inLookupMetadata;
            this.additionalLookupMetadata = additionalLookupMetadata;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LookupMetadata getInitLookupMetadata() {
            return initLookupMetadata;
        }

        public void setInitLookupMetadata(LookupMetadata initLookupMetadata) {
            this.initLookupMetadata = initLookupMetadata;
        }

        public List<LookupMetadata> getAdditionalLookupMetadata() {
            return additionalLookupMetadata;
        }

        public void setAdditionalLookupMetadata(List<LookupMetadata> additionalLookupMetadata) {
            this.additionalLookupMetadata = additionalLookupMetadata;
        }
        
    }
    private FieldDescriptor addField(ModelIdPlaceHolder modelIdObj, Section section, String fieldKey) {
    	return addField(modelIdObj, section, fieldKey, null, null, null);
    }
    private FieldDescriptor addField(ModelIdPlaceHolder modelIdObj, Section section, String fieldKey, MessageKeyInfo messageKey) {
    	return addField(modelIdObj, section, fieldKey, messageKey, null, null);
    }
    private FieldDescriptor addField(ModelIdPlaceHolder modelIdObj, Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
    	return addField(modelIdObj, section, fieldKey, messageKey, widget, null);
    }

    // TODO - when DOL is pushed farther down into LOBuilder,
    // revert these 5 methods to returning void again.
    // TODO - when DOL is pushed farther down into LOBuilder,
    // revert these 5 methods to returning void again.
    protected FieldDescriptor addField(Section section, String fieldKey) {
    	return addField(null, section, fieldKey, null, null, null);
    }    
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
    	return addField(null, section, fieldKey, messageKey, null, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
    	return addField(null, section, fieldKey, messageKey, widget, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addField(null, section, fieldKey, messageKey, null, parentPath);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        return addField(null, section, fieldKey, messageKey, widget, parentPath);
    }
    private FieldDescriptor addField(ModelIdPlaceHolder modelId, Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
    	Metadata meta = modelDefinition.getMetadata(path);
    	FieldDescriptor fd;
    	if (widget != null) {
    		fd = new FieldDescriptor(path.toString(), messageKey, meta, widget);
    	}
    	else{
    		fd = new FieldDescriptor(path.toString(), messageKey, meta);
    	}
    	if (modelId != null) {
    		fd.setModelId(modelId.getModelId());
    	}
    	section.addField(fd);
    	return fd;
    }

    public String getEditSearchCluSetId() {
        return editSearchCluSetId;
    }

    public void setEditSearchCluSetId(String searchCluSetId) {
        this.editSearchCluSetId = searchCluSetId;
    }

    public String getViewSearchCluSetId() {
        return viewSearchCluSetId;
    }

    public void setViewSearchCluSetId(String viewSearchCluSetId) {
        this.viewSearchCluSetId = viewSearchCluSetId;
    }
    
    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo("clusetmanagement", "clusetmanagement", "draft", labelKey);
    }
    
    class ModelIdPlaceHolder {
    	private String modelId;
    	
    	public ModelIdPlaceHolder(String modelId) {
    		setModelId(modelId);
    	}
    	
		public String getModelId() {
			return modelId;
		}

		public void setModelId(String modelId) {
			this.modelId = modelId;
		}
    }

    public class CluSetEditOptionList extends KSCheckBoxList{
        public CluSetEditOptionList(){
            SimpleListItems editOptions = new SimpleListItems();

            editOptions.addItem(ToolsConstants.CLU_SET_SWAP_APPROVED_CLUS, "Approved Courses");
            editOptions.addItem(ToolsConstants.CLU_SET_SWAP_PROPOSED_CLUS, "Proposed Courses");
            editOptions.addItem(ToolsConstants.CLU_SET_SWAP_CLU_SETS, "CLU Sets");
            editOptions.addItem(ToolsConstants.CLU_SET_SWAP_CLU_SET_RANGE, "Course Ranges (Course numbers, common learning objectives, etc)");

            super.setListItems(editOptions);
        }
    }

}
