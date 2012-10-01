package org.kuali.student.lum.common.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.assembly.data.LookupParamMetadata;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.assembly.data.Data.DataValue;
import org.kuali.student.common.assembly.data.Data.Value;
import org.kuali.student.common.search.dto.SearchParam;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.configurable.mvc.binding.HasDataValueBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class CluSetEditorWidget extends VerticalSectionView {

    private List<KSSelectedListPanelPair> selectedListPanelPairs = new ArrayList<KSSelectedListPanelPair>();
    private DataModelDefinition modelDefinition;
//    private final List<HandlerRegistration> showClusetDetailsHandlerRegs = new ArrayList<HandlerRegistration>(); 
    private final Map<String, HandlerRegistration> showCluRangeDetailsHandlerRegs = new HashMap<String, HandlerRegistration>();
    private List<KSItemLabelPanelPair> itemLabelPanelPairs = new ArrayList<KSItemLabelPanelPair>();
    private String cluSetType;
    private String metadataId;
    private static Map<String, DataModelDefinition> modelDefinitionCache = new HashMap<String, DataModelDefinition>();
    private BlockingTask initializeTask = new BlockingTask("Initializing");
    private boolean singularCluOnly;
    private KSSelectedList approvedClusSelection;
    
    private enum CluSetManagementField {
        APPROVED, PROPOSED, CLUSETS, RANGE
    }

    public CluSetEditorWidget(CluSetRetriever cluSetRetriever, Enum<?> viewEnum,
            String name, String modelId, boolean showTitle,
            final Callback<Boolean> onReady, String cluSetType, boolean singularCluOnly) {
        super(viewEnum, name, modelId, showTitle);
        this.cluSetType = cluSetType;
        this.singularCluOnly = singularCluOnly;
        if (cluSetType != null && cluSetType.equals("kuali.cluSet.type.Program")) {
            this.metadataId = "programSet";
        } else {
            this.metadataId = "courseSet";
        }
        KSBlockingProgressIndicator.addTask(initializeTask);
        if (modelDefinitionCache.get(metadataId) == null) {
            cluSetRetriever.getMetadata(metadataId, new Callback<Metadata>(){
                @Override
                public void exec(Metadata result) {
                    DataModelDefinition def = new DataModelDefinition(result);
                    modelDefinitionCache.put(metadataId, def);
                    setDef(def);
                    setupEditor();
                    if (onReady != null) {
                        onReady.exec(new Boolean(true));
                    }
                    KSBlockingProgressIndicator.removeTask(initializeTask);                
                }
            });
        } else {
            DataModelDefinition def = modelDefinitionCache.get(metadataId);
            setDef(def);
            setupEditor();
            if (onReady != null) {
                onReady.exec(new Boolean(true));
            }
            KSBlockingProgressIndicator.removeTask(initializeTask);                
        }
    }
    
    private void setDef(DataModelDefinition def) {
        this.modelDefinition = def;
    }
    
    public void setupEditor() {
        String labelType = null;
        if (cluSetType == null || !cluSetType.equals("kuali.cluSet.type.Program")) {
            labelType = "Course";
        } else {
            labelType = "Program";
        }
        final CluSetEditOptionDropdown chooser = new CluSetEditOptionDropdown();
        SwitchSection clusetDetails = new SwitchSection(
                chooser,
                null);
        clusetDetails.enableConfirmation(false);
        // ****** Add Approved Clus *******
        Section approvedClusSection = new VerticalSection();
        FieldDescriptor approvedClusFd = addField(approvedClusSection, 
                CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD, 
                generateMessageInfo(CommonWidgetConstants.NEW_CLU_SET_CONTENT_APPROVED + labelType),
                null,
                null);
        approvedClusSelection = (KSSelectedList) approvedClusFd.getFieldWidget();
        KSListPanel approvedClusListPanel = approvedClusSelection.separateValuesPanel();
        clusetDetails.addSection(approvedClusSection, CommonWidgetConstants.CLU_SET_SWAP_APPROVED_CLUS);
        // END OF items related to Add Approved Clus

        // ****** Add Proposed Clus *******
        Section proposedClusSection = new VerticalSection();
        FieldDescriptor proposedClusFd = addField(proposedClusSection, 
                CommonWidgetConstants.CLU_SET_PROPOSED_CLUS_FIELD, 
                generateMessageInfo(CommonWidgetConstants.NEW_CLU_SET_CONTENT_PROPOSED + labelType),
                null,
                null);
        final KSSelectedList proposedClusSelection = (KSSelectedList) proposedClusFd.getFieldWidget();
        KSListPanel proposedClusListPanel = proposedClusSelection.separateValuesPanel();
        clusetDetails.addSection(proposedClusSection, CommonWidgetConstants.CLU_SET_SWAP_PROPOSED_CLUS);
        // END OF items related to Add Approved Clus

        // ****** Add Clu Range *******
        KSItemLabel clusetRangeLabel = null;
        if (displayField(CluSetManagementField.RANGE)) {
            Section cluRangeSection = new VerticalSection();
            final Picker cluSetRangePicker = configureSearch(CommonWidgetConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD);
            addField(cluRangeSection, 
                    CommonWidgetConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD, 
                    generateMessageInfo(CommonWidgetConstants.NEW_CLU_SET_CONTENT_RANGE + labelType),
                    cluSetRangePicker,
                    null);
            final CluSetRangeDataHelper clusetRangeModelHelper = new CluSetRangeDataHelper();
            clusetRangeLabel = new KSItemLabel(true, true, clusetRangeModelHelper);
            final KSItemLabel theClusetRangeLabel = clusetRangeLabel;
            clusetRangeLabel.getElement().getStyle().setProperty("border", "solid 1px #cdcdcd");
            clusetRangeLabel.getElement().getStyle().setProperty("width", "354px");
            final FieldDescriptor cluRangeFieldDescriptor = addField(
                    cluRangeSection, 
                    CommonWidgetConstants.CLU_SET_CLU_SET_RANGE_FIELD, 
                    null, 
                    clusetRangeLabel,
                    null);
            cluRangeFieldDescriptor.setWidgetBinding(new CluSetRangeBinding(clusetRangeModelHelper));
            cluSetRangePicker.getSearchWindow().addActionCompleteCallback(new Callback<Boolean>() {
                @Override
                public void exec(Boolean result) {
                    cluSetRangePicker.getSearchWindow().hide();
//                  ((ModelWidgetBinding)cluRangeFieldDescriptor.getModelWidgetBinding()).setWidgetValue(widget, model, path)
//                  CluSetHelper cluSetHelper = CluSetHelper.wrap(model.getRoot());
//                  cluSetHelper.setCluRangeParams(value)
                    final SearchRequest searchRequest = cluSetRangePicker.getSearchWindow().getSearchRequest();
                    String selectedSearchKey = searchRequest.getSearchKey();
                    Data searchRequestData = CluSetRangeModelUtil.INSTANCE.toData(searchRequest, null);
                    LookupMetadata lookupMetadata = null;

                    // look for the lookupMetaData corresponding to the searchRequest
                    List<LookupMetadata> lookupMDs = new ArrayList<LookupMetadata>();
                    lookupMDs.add(cluSetRangePicker.getInitLookupMetadata());
                    lookupMetadata = findLookupMetadataByLookupId(selectedSearchKey, 
                            lookupMDs, searchRequest.getParams());
                    if (lookupMetadata == null || 
                            !nullSafeEquals(lookupMetadata.getSearchTypeId(), 
                                    selectedSearchKey)) {
                        lookupMetadata = findLookupMetadataByLookupId(selectedSearchKey, 
                                cluSetRangePicker.getAdditionalLookupMetadata(),
                                searchRequest.getParams());
                    }

                    addClusetItemViewHandler(clusetRangeModelHelper,
                            theClusetRangeLabel,
                            lookupMetadata,
                            searchRequestData,
                            searchRequest);
                    theClusetRangeLabel.setHighlighted(true);
                    new Timer() {
                        @Override
                        public void run() {
                            theClusetRangeLabel.setHighlighted(false);
                        }
                    }.schedule(5000);
                }
            });
            clusetDetails.addSection(cluRangeSection, CommonWidgetConstants.CLU_SET_SWAP_CLU_SET_RANGE);
        }
        // END OF items related to Add Clu Range

        // ****** Add cluSets *******
        KSListPanel cluSetsListPanel = null;
        KSSelectedList cluSetsSelection = null;
        if (displayField(CluSetManagementField.CLUSETS)) {
            Section cluSetSection = new VerticalSection();
            FieldDescriptor cluSetsTempFd = getFieldDescriptor( 
                    CommonWidgetConstants.CLU_SET_CLU_SETS_FIELD, 
                    generateMessageInfo(CommonWidgetConstants.NEW_CLU_SET_CONTENT_CLUSET + labelType),
                    null,
                    null);
            KSSelectedList tempCluSetsSelection = (KSSelectedList) cluSetsTempFd.getFieldWidget();
            WidgetConfigInfo config = tempCluSetsSelection.getConfig();
            cluSetsSelection = new KSSelectedList(config, false);
            cluSetsListPanel = cluSetsSelection.separateValuesPanel();
            final FieldDescriptor cluSetsFd = addField(
                    cluSetSection, 
                    CommonWidgetConstants.CLU_SET_CLU_SETS_FIELD, 
                    generateMessageInfo(CommonWidgetConstants.NEW_CLU_SET_CONTENT_CLUSET + labelType),
                    cluSetsSelection, 
                    null);
            cluSetsFd.setWidgetBinding(new CluSetBinding());
            clusetDetails.addSection(cluSetSection, CommonWidgetConstants.CLU_SET_SWAP_CLU_SETS);
        }
        // END OF items related to Add CluSets

        // display item type title if the list selected items is populated
        // hide if the list becomes empty (thus the close listeners for each item
        String contextName = (cluSetType != null && cluSetType.equals("kuali.cluSet.type.Program"))?
                "PROGRAMS" : "COURSES";
        VerticalFlowPanel selectedValuesPanel = new VerticalFlowPanel();
        selectedValuesPanel.getElement().getStyle().setPaddingTop(20, Style.Unit.PX);
        VerticalFlowPanel approvedClusPanel = prepareValuesPanel(approvedClusListPanel, "APPROVED " + contextName);
        approvedClusPanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
        VerticalFlowPanel proposedClusPanel = prepareValuesPanel(proposedClusListPanel, "PROPOSED " + contextName);
        proposedClusPanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
        VerticalFlowPanel rangePanel = null;
        if (displayField(CluSetManagementField.RANGE)) {
            rangePanel = prepareValuesPanel(clusetRangeLabel, contextName + " RANGE");
            rangePanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
        }
        VerticalFlowPanel cluSetsPanel = null;
        if (displayField(CluSetManagementField.CLUSETS)) {
            cluSetsPanel = prepareValuesPanel(cluSetsListPanel, contextName + " SETS");
            cluSetsPanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
        }
        
        selectedValuesPanel.add(approvedClusPanel);
        selectedValuesPanel.add(proposedClusPanel);
        if (displayField(CluSetManagementField.RANGE)) {
            selectedValuesPanel.add(rangePanel);
        }
        if (displayField(CluSetManagementField.CLUSETS)) {
            selectedValuesPanel.add(cluSetsPanel);
        }

        addVisibilityHandlers(approvedClusSelection, approvedClusPanel);
        addVisibilityHandlers(proposedClusSelection, proposedClusPanel);
        if (displayField(CluSetManagementField.RANGE)) {
            addVisibilityHandlers(clusetRangeLabel, rangePanel);
        }
        if (displayField(CluSetManagementField.CLUSETS)) {
            addVisibilityHandlers(cluSetsSelection, cluSetsPanel);
        }
        
        selectedListPanelPairs.add(new KSSelectedListPanelPair(approvedClusSelection, approvedClusPanel));
        selectedListPanelPairs.add(new KSSelectedListPanelPair(proposedClusSelection, proposedClusPanel));
        if (displayField(CluSetManagementField.CLUSETS)) {
            selectedListPanelPairs.add(new KSSelectedListPanelPair(cluSetsSelection, cluSetsPanel));
        }
        if (displayField(CluSetManagementField.RANGE)) {
            itemLabelPanelPairs.add(new KSItemLabelPanelPair(clusetRangeLabel, rangePanel));
        }
        
        final VerticalSection choosingSection = new VerticalSection();
        HTML prompt;
        if(cluSetType.equals("kuali.cluSet.type.Program")){
            choosingSection.addWidget(new HTML("<b>Add a program or program set</b>"));
            prompt = new HTML("Add program or program sets. You may  <br/>"
                    + "add any combination of programs or program sets.");
        }
        else{
            choosingSection.addWidget(new HTML("<b>Add a course, course set, or course range</b>"));
            prompt = new HTML("Add courses, course sets, or course ranges to your course set. You may <br/>" +
                "add any combination of courses, dynamic course ranges, or Course sets.");
        }
        choosingSection.addWidget(chooser);
        choosingSection.addSection(clusetDetails);
        chooser.addSelectionChangeHandler(new SelectionChangeHandler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                if (chooser.getSelectedItem() != null &&
                        !chooser.getSelectedItem().trim().isEmpty()) {
                    choosingSection.setStyleName("KS-CluSetManagement-chooser");
                } else {
                    choosingSection.setStyleName("KS-CluSetManagement-chooser-unselected");
                }
            }
        });
        
        this.addWidget(prompt);
        this.addSection(choosingSection);
        this.addWidget(selectedValuesPanel);
    }
    
    private boolean displayField(CluSetManagementField field) {
        boolean result = false;
        switch(field) {
            case APPROVED:
            case PROPOSED:
                result = true;
                break;
            case RANGE:
                if (!singularCluOnly && (cluSetType == null || !cluSetType.equals("kuali.cluSet.type.Program"))) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case CLUSETS:
                if (!singularCluOnly) {
                    result = true;
                } else {
                    result = false;
                }
                break;
        }
        return result;
    }
    
    private void addClusetItemViewHandler(CluSetRangeDataHelper clusetRangeModelHelper,
            KSItemLabel clusetRangeLabel,
            final LookupMetadata lookupMetadata,
            Data searchRequestData,
            final SearchRequest searchRequest) {
        clusetRangeModelHelper.setLookupMetadata(lookupMetadata);
        clusetRangeLabel.setValue(new DataValue(searchRequestData));
        if (showCluRangeDetailsHandlerRegs != null && 
                showCluRangeDetailsHandlerRegs.get(Integer.toString(clusetRangeLabel.instanceId)) != null) {
            ((HandlerRegistration)showCluRangeDetailsHandlerRegs.get(Integer.toString(clusetRangeLabel.instanceId))).removeHandler();
        }
        showCluRangeDetailsHandlerRegs.put(Integer.toString(clusetRangeLabel.instanceId), 
                clusetRangeLabel.addShowDetailsHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SearchResultsLightBox srLightBox = new SearchResultsLightBox("View Course Range",
                        searchRequest, lookupMetadata);
                srLightBox.show();
            }
        }));
    }
    
    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo("clusetmanagement", "clusetmanagement", "draft", labelKey);
    }
    
    private FieldDescriptor getFieldDescriptor( 
            String fieldKey, 
            MessageKeyInfo messageKey, 
            Widget widget, 
            String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);
        FieldDescriptor fd;
        if (widget != null) {
            fd = new FieldDescriptor(path.toString(), messageKey, meta, widget);
        }
        else{
            fd = new FieldDescriptor(path.toString(), messageKey, meta);
        }
        return fd;
    }

    private FieldDescriptor addField(Section section, 
            String fieldKey, 
            MessageKeyInfo messageKey, 
            Widget widget, 
            String parentPath) {
        FieldDescriptor fd = getFieldDescriptor(fieldKey, messageKey, widget, parentPath);
        section.addField(fd);
        return fd;
    }

    private static boolean nullSafeEquals(Object obj1, Object obj2) {
        return (obj1 == null && obj2 == null ||
                obj1 != null && obj2 != null && obj1.equals(obj2));
    }
    
    private VerticalFlowPanel prepareValuesPanel(Widget widget, String title) {
        final VerticalFlowPanel valuesPanel = new VerticalFlowPanel();
        valuesPanel.add(new KSLabel(title));
        valuesPanel.add(widget);
        valuesPanel.setVisible(false);
        return valuesPanel;
    }
    
    @Override
    public void updateWidgetData(DataModel model) {
        super.updateWidgetData(model);
        // this is to refresh the lists and item labels.
        if (selectedListPanelPairs != null) {
            for (KSSelectedListPanelPair selectedListPanelPair : selectedListPanelPairs) {
                handleSelectedListVisibility(selectedListPanelPair.getKsSelectedList(), 
                        selectedListPanelPair.getPanel());
            }
        }
        if (itemLabelPanelPairs != null) {
            for (KSItemLabelPanelPair itemLabelPanelPair : itemLabelPanelPairs) {
                handleListItemVisibility(itemLabelPanelPair.getKsItemLabel(), 
                        itemLabelPanelPair.getPanel());
            }
        }
    }

    private void addVisibilityHandlers(final KSItemLabel listItem,
            final Panel selectionPanel) {
        listItem.addValueChangeCallback(new Callback<Value>() {
            @Override
            public void exec(Value result) {
                handleListItemVisibility(listItem, selectionPanel);
            }
        });
    }
    
    private void handleListItemVisibility(KSItemLabel listItem, Panel selectionPanel) {
        String displayText = listItem.getDisplayText();
        if (displayText == null || displayText.equals("")) {
            selectionPanel.setVisible(false);
        } else {
            selectionPanel.setVisible(true);
        }
    }
    
    private void addVisibilityHandlers(final KSSelectedList selectionList,
            final Panel selectionPanel) {
        selectionList.addSelectionChangeHandler(
                new SelectionChangeHandler() {
                    @Override
                    public void onSelectionChange(SelectionChangeEvent event) {
                        handleSelectedListVisibility(selectionList, selectionPanel);
                    }
                }
        );
    }
    
    private void handleSelectedListVisibility(final KSSelectedList selectionList, final Panel selectionPanel) {
        List<KSItemLabel> selectedItems = selectionList.getSelectedItems();
        if (selectedItems != null && !selectedItems.isEmpty()) {
            // show item type title
            selectionPanel.setVisible(true);
            for (KSItemLabel selectedItem : selectedItems) {
                selectedItem.addCloseHandler(new CloseHandler<KSItemLabel>() {
                    @Override
                    public void onClose(CloseEvent<KSItemLabel> event) {
                        List<KSItemLabel> newSelectedItems = 
                            selectionList.getSelectedItems();
                        int numNewSelectedItems = (newSelectedItems == null)? 0 :
                            newSelectedItems.size();
                        if (numNewSelectedItems == 0) {
                            //hide item type title
                            selectionPanel.setVisible(false);
                        }
                    }
                });
            }
        }
    }

    private Picker configureSearch(String fieldKey) {
        QueryPath path = QueryPath.concat(null, fieldKey);
        Metadata metaData = modelDefinition.getMetadata(path);
        Picker picker = new Picker(metaData.getInitialLookup(), metaData.getAdditionalLookups());
        return picker;
    }

    private static LookupMetadata findLookupMetadataByLookupId(String searchTypeId,
            List<LookupMetadata> lookupMetadatas,
            List<SearchParam> searchParams) {
        LookupMetadata result = null;
        if (lookupMetadatas != null) {
            for (LookupMetadata lookupMetadata : lookupMetadatas) {
                if (nullSafeEquals(lookupMetadata.getSearchTypeId(), searchTypeId)) {
                    // search Type id matches now add up all the params meta found to the result
                    List<LookupParamMetadata> paramMetas = lookupMetadata.getParams();
                    List<LookupParamMetadata> resultParamMetas = null;
                    result = (result == null)? new LookupMetadata() : result;
                    result.setDesc(lookupMetadata.getDesc());
                    result.setId(lookupMetadata.getId());
                    result.setName("");
                    resultParamMetas = result.getParams();
                    if (resultParamMetas == null) {
                        resultParamMetas = new ArrayList<LookupParamMetadata>();
                    }
                    if (paramMetas != null) {
                        for (LookupParamMetadata paramMeta : paramMetas) {
                            boolean resultParamMetaFound = false;
                            for (LookupParamMetadata resultParamMeta : resultParamMetas) {
                                if (paramMeta.getKey().equals(resultParamMeta)) {
                                    resultParamMetaFound = true;
                                    break;
                                }
                            }
                            if (!resultParamMetaFound) {
                                resultParamMetas.add(paramMeta);
                            }
                        }
                    }
                    result.setParams(resultParamMetas);
                    result.setQosMetadata(lookupMetadata.getQosMetadata());
                    result.setResultDisplayKey(lookupMetadata.getResultDisplayKey());
                    result.setResultReturnKey(lookupMetadata.getResultReturnKey());
                    result.setResults(lookupMetadata.getResults());
                    result.setResultSortKey(lookupMetadata.getResultSortKey());
                }
            }
        }
        return result;
    }
    
    @Override
    public void updateModel() {
        
        super.updateModel();
    }



    public class CluSetEditOptionDropdown extends KSDropDown {
        public CluSetEditOptionDropdown(){
            SimpleListItems editOptions = new SimpleListItems();

            if (cluSetType != null && cluSetType.equals("kuali.cluSet.type.Program")) {
                editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_APPROVED_CLUS, "Approved Programs");
                editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_PROPOSED_CLUS, "Proposed Programs");
                if (!CluSetEditorWidget.this.singularCluOnly) {
                    editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_CLU_SETS, "Program Sets");
                }
            } else {
                editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_APPROVED_CLUS, "Approved Courses");
                editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_PROPOSED_CLUS, "Proposed Courses");
                if (!CluSetEditorWidget.this.singularCluOnly) {
                    editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_CLU_SETS, "Course Sets");
                    editOptions.addItem(CommonWidgetConstants.CLU_SET_SWAP_CLU_SET_RANGE, "Course Ranges (Course numbers, common learning objectives, etc)");
                }
            }
            super.setListItems(editOptions);
        }
    }

    class KSSelectedListPanelPair {
        private KSSelectedList ksSelectedList;
        private Panel panel;
        public KSSelectedListPanelPair(KSSelectedList ksSelectedList, Panel panel) {
            setKsSelectedList(ksSelectedList);
            setPanel(panel);
        }
        public KSSelectedList getKsSelectedList() {
            return ksSelectedList;
        }
        public void setKsSelectedList(KSSelectedList ksSelectedList) {
            this.ksSelectedList = ksSelectedList;
        }
        public Panel getPanel() {
            return panel;
        }
        public void setPanel(Panel panel) {
            this.panel = panel;
        }
    }
    
    class KSItemLabelPanelPair {
        private KSItemLabel ksItemLabel;
        private Panel panel;
        public KSItemLabelPanelPair(KSItemLabel ksItemLabel, Panel panel) {
            setKsItemLabel(ksItemLabel);
            setPanel(panel);
        }
        public KSItemLabel getKsItemLabel() {
            return ksItemLabel;
        }
        public void setKsItemLabel(KSItemLabel ksItemLabel) {
            this.ksItemLabel = ksItemLabel;
        }
        public Panel getPanel() {
            return panel;
        }
        public void setPanel(Panel panel) {
            this.panel = panel;
        }
    }
    
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
    
    public class CluSetBinding extends ModelWidgetBindingSupport<HasDataValue> {
        private HasDataValueBinding binding = HasDataValueBinding.INSTANCE;

        @Override
        public void setModelValue(HasDataValue widget, DataModel model, String path) {
            binding.setModelValue(widget, model, path);
        }

        @Override
        public void setWidgetValue(HasDataValue widget, DataModel model, String path) {
            binding.setWidgetValue(widget, model, path);
        }
    }
    
    public class CluSetRangeBinding extends ModelWidgetBindingSupport<HasDataValue> {
        private HasDataValueBinding binding = HasDataValueBinding.INSTANCE;
        private CluSetRangeDataHelper clusetRangeModelHelper = null;
        
        public CluSetRangeBinding(CluSetRangeDataHelper clusetRangeModelHelper) {
            this.clusetRangeModelHelper = clusetRangeModelHelper;
        }
        
        @Override
        public void setModelValue(HasDataValue widget, DataModel model, String path) {
            binding.setModelValue(widget, model, path);
        }

        @Override
        public void setWidgetValue(final HasDataValue widget, DataModel model, String path) {
            DataModel middleManModel = new DataModel();
            if (model != null && model.getRoot() != null) {
                middleManModel = new DataModel(model.getDefinition(), model.getRoot().copy());
            }
            Metadata rangeEditMetaData = model.getMetadata(
                    QueryPath.parse(CommonWidgetConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD));
            Data membershipQueryData = (Data)model.get(path);
            MembershipQueryInfo membershipQueryInfo = (membershipQueryData == null)?
                    null : CluSetRangeModelUtil.INSTANCE.toMembershipQueryInfo(membershipQueryData);
            if (membershipQueryInfo != null) {
                String selectedSearchTypeKey = membershipQueryInfo.getSearchTypeKey();
                List<LookupMetadata> lookupMDs = new ArrayList<LookupMetadata>();
                lookupMDs.add(rangeEditMetaData.getInitialLookup());
                LookupMetadata lookupMetadata = findLookupMetadataByLookupId(selectedSearchTypeKey, 
                        lookupMDs, membershipQueryInfo.getQueryParamValueList());
                if (lookupMetadata == null || 
                        !nullSafeEquals(lookupMetadata.getName(), 
                                selectedSearchTypeKey)) {
                    lookupMetadata = findLookupMetadataByLookupId(selectedSearchTypeKey, 
                            rangeEditMetaData.getAdditionalLookups(),
                            membershipQueryInfo.getQueryParamValueList());
                }

                SearchRequest searchRequest = new SearchRequest();
                searchRequest.setSearchKey(selectedSearchTypeKey);
//              if ()
                searchRequest.setParams(membershipQueryInfo.getQueryParamValueList());
                searchRequest.setSortColumn(lookupMetadata.getResultSortKey());
                
//                if (showCluRangeDetailsHandlerRegs != null) {
//                    for (HandlerRegistration showCluRangeDetailsHandlerReg : showCluRangeDetailsHandlerRegs) {
//                        showCluRangeDetailsHandlerReg.removeHandler();
//                    }
//                    showCluRangeDetailsHandlerRegs.clear();
//                }
                addClusetItemViewHandler(clusetRangeModelHelper,
                        (KSItemLabel)widget,
                        lookupMetadata,
                        membershipQueryData,
                        searchRequest);
                ((KSItemLabel)widget).setHighlighted(true);
                new Timer() {
                    @Override
                    public void run() {
                        ((KSItemLabel)widget).setHighlighted(false);
                    }
                }.schedule(5000);
            }
            binding.setWidgetValue(widget, middleManModel, path);
        }
    }
}
