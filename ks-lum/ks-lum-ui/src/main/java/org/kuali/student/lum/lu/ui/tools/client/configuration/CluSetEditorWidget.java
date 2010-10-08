package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
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
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcService;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcServiceAsync;
import org.kuali.student.lum.lu.ui.tools.client.widgets.CluSetRangeDataHelper;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluSetRangeModelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class CluSetEditorWidget extends VerticalSectionView {

//    private VerticalSection mainSection = new VerticalSection();
    private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);
    private List<KSSelectedListPanelPair> selectedListPanelPairs = new ArrayList<KSSelectedListPanelPair>();
    private DataModelDefinition modelDefinition;
    private final List<HandlerRegistration> showClusetDetailsHandlerRegs = new ArrayList<HandlerRegistration>(); 
    private final List<HandlerRegistration> showCluRangeDetailsHandlerRegs = new ArrayList<HandlerRegistration>(); 
    private List<KSItemLabelPanelPair> itemLabelPanelPairs = new ArrayList<KSItemLabelPanelPair>();

    public CluSetEditorWidget(Enum<?> viewEnum,
            String name, String modelId, boolean showTitle,
            final Callback<Boolean> onReady) {
        super(viewEnum, name, modelId, showTitle);
        cluSetManagementRpcServiceAsync.getMetadata("", null, new KSAsyncCallback<Metadata>(){
            @Override
            public void handleFailure(Throwable caught) {
            }
            @Override
            public void onSuccess(Metadata result) {
                DataModelDefinition def = new DataModelDefinition(result);
                setDef(def);
                setupEditor();
                if (onReady != null) {
                    onReady.exec(new Boolean(true));
                }
            }
        });
    }
    
    private void setDef(DataModelDefinition def) {
        this.modelDefinition = def;
    }
    
    public void setupEditor() {
        CluSetEditOptionDropdown chooser = new CluSetEditOptionDropdown();
        SwitchSection clusetDetails = new SwitchSection(
                chooser,
                null);
        clusetDetails.enableConfirmation(false);
        // ****** Add Approved Clus *******
        Section approvedClusSection = new VerticalSection();
        FieldDescriptor approvedClusFd = addField(approvedClusSection, 
                ToolsConstants.CLU_SET_APPROVED_CLUS_FIELD, 
                generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_APPROVED_COURSE),
                null,
                null);
        final KSSelectedList approvedClusSelection = (KSSelectedList) approvedClusFd.getFieldWidget();
        KSListPanel approvedClusListPanel = approvedClusSelection.separateValuesPanel();
        clusetDetails.addSection(approvedClusSection, ToolsConstants.CLU_SET_SWAP_APPROVED_CLUS);
        // END OF items related to Add Approved Clus

        // ****** Add Proposed Clus *******
        Section proposedClusSection = new VerticalSection();
        FieldDescriptor proposedClusFd = addField(proposedClusSection, 
                ToolsConstants.CLU_SET_PROPOSED_CLUS_FIELD, 
                generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_PROPOSED_COURSE),
                null,
                null);
        final KSSelectedList proposedClusSelection = (KSSelectedList) proposedClusFd.getFieldWidget();
        KSListPanel proposedClusListPanel = proposedClusSelection.separateValuesPanel();
        clusetDetails.addSection(proposedClusSection, ToolsConstants.CLU_SET_SWAP_PROPOSED_CLUS);
        // END OF items related to Add Approved Clus

        // ****** Add Clu Range *******
        Section cluRangeSection = new VerticalSection();
        final Picker cluSetRangePicker = configureSearch(ToolsConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD);
        addField(cluRangeSection, 
                ToolsConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD, 
                generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_RANGE),
                cluSetRangePicker,
                null);
        final CluSetRangeDataHelper clusetRangeModelHelper = new CluSetRangeDataHelper();
        final KSItemLabel clusetRangeLabel = new KSItemLabel(true, true, clusetRangeModelHelper);
        clusetRangeLabel.getElement().getStyle().setProperty("border", "solid 1px #cdcdcd");
        final FieldDescriptor cluRangeFieldDescriptor = addField(
                cluRangeSection, 
                ToolsConstants.CLU_SET_CLU_SET_RANGE_FIELD, 
                null, 
                clusetRangeLabel,
                null);
        cluRangeFieldDescriptor.setWidgetBinding(new CluSetRangeBinding(clusetRangeModelHelper));
        cluSetRangePicker.getSearchWindow().addActionCompleteCallback(new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {
                cluSetRangePicker.getSearchWindow().hide();
//              ((ModelWidgetBinding)cluRangeFieldDescriptor.getModelWidgetBinding()).setWidgetValue(widget, model, path)
//              CluSetHelper cluSetHelper = CluSetHelper.wrap(model.getRoot());
//              cluSetHelper.setCluRangeParams(value)
                final SearchRequest searchRequest = cluSetRangePicker.getSearchWindow()
                .getSearchRequest();
                String selectedSearchKey = searchRequest.getSearchKey();
                Data searchRequestData = CluSetRangeModelUtil.INSTANCE.
                toData(searchRequest, null);
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
                        clusetRangeLabel,
                        lookupMetadata,
                        searchRequestData,
                        searchRequest);
                clusetRangeLabel.setHighlighted(true);
                new Timer() {
                    @Override
                    public void run() {
                        clusetRangeLabel.setHighlighted(false);
                    }
                }.schedule(5000);
            }
        });
        clusetDetails.addSection(cluRangeSection, ToolsConstants.CLU_SET_SWAP_CLU_SET_RANGE);
        // END OF items related to Add Clu Range

        // ****** Add cluSets *******
        Section cluSetSection = new VerticalSection();
        FieldDescriptor cluSetsTempFd = getFieldDescriptor( 
                ToolsConstants.CLU_SET_CLU_SETS_FIELD, 
                generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_CLUSET),
                null,
                null);
        KSSelectedList tempCluSetsSelection = (KSSelectedList) cluSetsTempFd.getFieldWidget();
        WidgetConfigInfo config = tempCluSetsSelection.getConfig();
        final KSSelectedList cluSetsSelection = new KSSelectedList(config, true);
        KSListPanel cluSetsListPanel = cluSetsSelection.separateValuesPanel();
        final FieldDescriptor cluSetsFd = addField(
                cluSetSection, 
                ToolsConstants.CLU_SET_CLU_SETS_FIELD, 
                generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_CLUSET),
                cluSetsSelection, 
                null);
        cluSetsFd.setWidgetBinding(new CluSetBinding());
        cluSetsSelection.addSelectionChangeHandler(new SelectionChangeHandler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                if (showClusetDetailsHandlerRegs != null) {
                    for (HandlerRegistration showDetailsHandlerReg : showClusetDetailsHandlerRegs) {
                        showDetailsHandlerReg.removeHandler();
                    }
                    showClusetDetailsHandlerRegs.clear();
                }
                List<KSItemLabel> selectedCluSets = cluSetsSelection.getSelectedItems();
                for (final KSItemLabel selectedCluSet : selectedCluSets) {
                    showClusetDetailsHandlerRegs.add(selectedCluSet.addShowDetailsHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
                            SearchParam cluSetIdParam = new SearchParam();
                            cluSetIdParam.setKey("cluset.queryParam.optionalId");
                            cluSetIdParam.setValue(selectedCluSet.getKey());
                            queryParamValues.add(cluSetIdParam);
                            SearchRequest clusInCluSetSearch = new SearchRequest();
                            clusInCluSetSearch.setSearchKey("lu.search.clusInCluset");
                            clusInCluSetSearch.setSortColumn("lu.resultColumn.cluOfficialIdentifier.longName");
                            clusInCluSetSearch.setParams(queryParamValues);
                            Metadata metaDataClusInCluSet =
                                modelDefinition.getMetadata(QueryPath.parse("search/findClusInCluset"));
                            SearchResultsLightBox srLightBox = new SearchResultsLightBox("View Course Set",
                                    clusInCluSetSearch, metaDataClusInCluSet.getAdditionalLookups().get(0));
                            srLightBox.show();
                        }
                    }));
                }
            }
        });
        
        clusetDetails.addSection(cluSetSection, ToolsConstants.CLU_SET_SWAP_CLU_SETS);
        // END OF items related to Add CluSets

        // display item type title if the list selected items is populated
        // hide if the list becomes empty (thus the close listeners for each item
        VerticalFlowPanel selectedValuesPanel = new VerticalFlowPanel();
        selectedValuesPanel.getElement().getStyle().setPaddingTop(20, Style.Unit.PX);
        VerticalFlowPanel approvedClusPanel = prepareValuesPanel(approvedClusListPanel, "APPROVED COURSES");
        approvedClusPanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
        VerticalFlowPanel proposedClusPanel = prepareValuesPanel(proposedClusListPanel, "PROPOSED COURSES");
        proposedClusPanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
        VerticalFlowPanel rangePanel = prepareValuesPanel(clusetRangeLabel, "COURSES RANGE");
        rangePanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
        VerticalFlowPanel cluSetsPanel = prepareValuesPanel(cluSetsListPanel, "COURSES SETS");
        cluSetsPanel.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);

        selectedValuesPanel.add(approvedClusPanel);
        selectedValuesPanel.add(proposedClusPanel);
        selectedValuesPanel.add(rangePanel);
        selectedValuesPanel.add(cluSetsPanel);

        addVisibilityHandlers(approvedClusSelection, approvedClusPanel);
        addVisibilityHandlers(proposedClusSelection, proposedClusPanel);
        addVisibilityHandlers(clusetRangeLabel, rangePanel);
        addVisibilityHandlers(cluSetsSelection, cluSetsPanel);
        
        selectedListPanelPairs.add(new KSSelectedListPanelPair(approvedClusSelection, approvedClusPanel));
        selectedListPanelPairs.add(new KSSelectedListPanelPair(proposedClusSelection, proposedClusPanel));
        selectedListPanelPairs.add(new KSSelectedListPanelPair(cluSetsSelection, cluSetsPanel));
        itemLabelPanelPairs.add(new KSItemLabelPanelPair(clusetRangeLabel, rangePanel));
        
        VerticalSection choosingSection = new VerticalSection();
        choosingSection.addWidget(chooser);
        choosingSection.addSection(clusetDetails);
        choosingSection.addStyleName("KS-CluSetManagement-chooser");
        this.addSection(choosingSection);
        this.addWidget(selectedValuesPanel);
    }
    
    private void addClusetItemViewHandler(CluSetRangeDataHelper clusetRangeModelHelper,
            KSItemLabel clusetRangeLabel,
            final LookupMetadata lookupMetadata,
            Data searchRequestData,
            final SearchRequest searchRequest) {
        clusetRangeModelHelper.setLookupMetadata(lookupMetadata);
        clusetRangeLabel.setValue(new DataValue(searchRequestData));
        showCluRangeDetailsHandlerRegs.add(clusetRangeLabel.addShowDetailsHandler(new ClickHandler() {
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
    
    public class CluSetEditOptionDropdown extends KSDropDown {
        public CluSetEditOptionDropdown(){
            SimpleListItems editOptions = new SimpleListItems();

            editOptions.addItem(ToolsConstants.CLU_SET_SWAP_APPROVED_CLUS, "Approved Courses");
            editOptions.addItem(ToolsConstants.CLU_SET_SWAP_PROPOSED_CLUS, "Proposed Courses");
            editOptions.addItem(ToolsConstants.CLU_SET_SWAP_CLU_SETS, "CLU Sets");
            editOptions.addItem(ToolsConstants.CLU_SET_SWAP_CLU_SET_RANGE, "Course Ranges (Course numbers, common learning objectives, etc)");

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
            if (showClusetDetailsHandlerRegs != null) {
                for (HandlerRegistration showDetailsHandlerReg : showClusetDetailsHandlerRegs) {
                    showDetailsHandlerReg.removeHandler();
                }
                showClusetDetailsHandlerRegs.clear();
            }
            List<KSItemLabel> selectedCluSets = ((KSSelectedList)widget).getSelectedItems();
            for (final KSItemLabel selectedCluSet : selectedCluSets) {
                showClusetDetailsHandlerRegs.add(selectedCluSet.addShowDetailsHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
                        SearchParam cluSetIdParam = new SearchParam();
                        cluSetIdParam.setKey("cluset.queryParam.optionalId");
                        cluSetIdParam.setValue(selectedCluSet.getKey());
                        queryParamValues.add(cluSetIdParam);
                        SearchRequest clusInCluSetSearch = new SearchRequest();
                        clusInCluSetSearch.setSearchKey("lu.search.clusInCluset");
                        clusInCluSetSearch.setSortColumn("lu.resultColumn.cluOfficialIdentifier.longName");
                        clusInCluSetSearch.setParams(queryParamValues);
                        Metadata metaDataClusInCluSet =
                            modelDefinition.getMetadata(QueryPath.parse("search/findClusInCluset"));
                        SearchResultsLightBox srLightBox = new SearchResultsLightBox("View Course Set",
                                clusInCluSetSearch, metaDataClusInCluSet.getAdditionalLookups().get(0));
                        srLightBox.show();
                    }
                }));
            }
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
                    QueryPath.parse(ToolsConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD));
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
                
                if (showCluRangeDetailsHandlerRegs != null) {
                    for (HandlerRegistration showCluRangeDetailsHandlerReg : showCluRangeDetailsHandlerRegs) {
                        showCluRangeDetailsHandlerReg.removeHandler();
                    }
                    showCluRangeDetailsHandlerRegs.clear();
                }
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
