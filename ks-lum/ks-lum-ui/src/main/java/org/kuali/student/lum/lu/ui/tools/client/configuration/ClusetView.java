package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.configurable.mvc.binding.HasDataValueBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
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
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.lum.common.client.lo.LUConstants;
import org.kuali.student.lum.lu.dto.CluSetInfo;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ClusetView extends VerticalSectionView {

    private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);
    private SearchRpcServiceAsync searchRpcServiceAsync = GWT.create(SearchRpcService.class);
    private DataModelDefinition modelDefinition;
    private String selectedCluSetId;
    private List<KSSelectedListPanelPair> selectedListPanelPairs = new ArrayList<KSSelectedListPanelPair>();
    private List<KSItemLabelPanelPair> itemLabelPanelPairs = new ArrayList<KSItemLabelPanelPair>();
    private CluSetsManagementViews viewEnum;
    private final List<HandlerRegistration> showClusetDetailsHandlerRegs = new ArrayList<HandlerRegistration>(); 
    private final List<HandlerRegistration> showCluRangeDetailsHandlerRegs = new ArrayList<HandlerRegistration>(); 
    private BlockingTask retrievingTask = new BlockingTask("Retrieving ...");
    private SimplePanel cluSetDisplay = new SimplePanel();

    public enum CluSetsManagementViews {
        MAIN,
        CREATE,
        EDIT,
        VIEW
    }
    
    public ClusetView(CluSetsManagementViews clusetViewEnum, String name, String modelId, final Callback<Boolean> onReady) {
        this(clusetViewEnum, name, modelId, true, onReady);
    }

    public ClusetView(final CluSetsManagementViews clusetViewEnum, String name, String modelId, boolean showTitle, final Callback<Boolean> onReady) {
        super(clusetViewEnum, name, modelId, showTitle);
        viewEnum = clusetViewEnum;
        cluSetManagementRpcServiceAsync.getMetadata("", null, new KSAsyncCallback<Metadata>(){
            @Override
            public void handleFailure(Throwable caught) {
            }
            @Override
            public void onSuccess(Metadata result) {
                DataModelDefinition def = new DataModelDefinition(result);
                setDef(def);
                setupView(clusetViewEnum);
                if (onReady != null) {
                    onReady.exec(new Boolean(true));
                }
            }
        });
    }
    
    private void setDef(DataModelDefinition def) {
        this.modelDefinition = def;
    }
    
    @Override
    public void beforeShow(Callback<Boolean> onReadyCallback) {
        super.beforeShow(onReadyCallback);
        if (viewEnum == CluSetsManagementViews.VIEW) {
            if (this.selectedCluSetId != null) {
                {
                    KSBlockingProgressIndicator.addTask(retrievingTask);
                    cluSetManagementRpcServiceAsync.getCluSetInformation(selectedCluSetId, 
                            new AsyncCallback<CluSetInformation>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    KSBlockingProgressIndicator.removeTask(retrievingTask);
                                    Window.alert("failed to get Cluset information");
                                }
                                @Override
                                public void onSuccess(CluSetInformation result) {
                                    CluSetDetailsWidget clusetDetailsWidget = 
                                        new CluSetDetailsWidget(result, cluSetManagementRpcServiceAsync);
                                    cluSetDisplay.clear();
                                    cluSetDisplay.setWidget(clusetDetailsWidget);
                                    KSBlockingProgressIndicator.removeTask(retrievingTask);
                                }
                    });
                }
            }
        }
    }
    
    @Override
    public void updateView(DataModel m) {
        super.updateView(m);
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

    private void setupView(CluSetsManagementViews clusetViewEnum) {
        if (clusetViewEnum == CluSetsManagementViews.CREATE ||
                clusetViewEnum == CluSetsManagementViews.EDIT) {
            setupCreateEditClusetView();
        } else if (clusetViewEnum == CluSetsManagementViews.MAIN) {
            setupMainView();
        } else if (clusetViewEnum == CluSetsManagementViews.VIEW) {
            setupViewClusetView();
        }
    }
    
    private void setupViewClusetView() {
        Anchor editCluSet = new Anchor("Edit Course Set");
        editCluSet.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Controller parentController = getController();
                parentController.showView(CluSetsManagementViews.EDIT);
            }
        });
        this.addWidget(editCluSet);
        Section generalClusInfoSection = new VerticalSection();
        KSLabel cluSetTitle = new KSLabel();
        cluSetTitle.getElement().getStyle().setProperty("fontWeight", "bold");
        cluSetTitle.getElement().getStyle().setProperty("fontSize", "16px");
        cluSetTitle.getElement().getStyle().setProperty("borderBotton", "1px solid #D8D8D8");
        addField(generalClusInfoSection, 
                ToolsConstants.CLU_SET_NAME_FIELD, 
                null,
                cluSetTitle,
                null);
        addField(generalClusInfoSection, 
                ToolsConstants.CLU_SET_DESCRIPTION_FIELD, 
                generateMessageInfo(ToolsConstants.DESCRIPTION),
                new KSLabel(),
                null);
        addField(generalClusInfoSection, 
                ToolsConstants.CLU_SET_EXP_DATE_FIELD, 
                generateMessageInfo(ToolsConstants.EXPIRATION_DATE),
                new KSLabel(),
                null);
        this.addSection(generalClusInfoSection);
        this.addWidget(cluSetDisplay);
    }
    
    private void setupMainView() {
        Anchor createCluSet = new Anchor("Create Course Set");
        createCluSet.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getController().showView(CluSetsManagementViews.CREATE);
            }
        });
        Style createClusetStyle = createCluSet.getElement().getStyle();
        createClusetStyle.setPaddingTop(40, Style.Unit.PX);
        createClusetStyle.setProperty("fontSize", "16px");
        createClusetStyle.setProperty("fontWeight", "bold");
        this.addWidget(createCluSet);
        this.addWidget(new KSLabel("Build a new Course set from courses, Course Sets, " +
        		"or specific criteria."));

        Picker cluSetPicker = configureSearch(ToolsConstants.SEARCH_CLU_SET);
        cluSetPicker.addBasicSelectionCompletedCallback(new Callback<SelectedResults>() {
            @Override
            public void exec(SelectedResults result) {
                if (result != null && result.getReturnKey() != null && result.getReturnKey().trim().length() > 0) {
                    selectedCluSetId = result.getReturnKey();
                    Controller parentController = getController();
                    parentController.showView(CluSetsManagementViews.VIEW);
                }
            }
        });
        SectionTitle modifyCluSetTitle = SectionTitle.generateH3Title("View or Modify Course Sets");
        modifyCluSetTitle.getElement().getStyle().setPaddingTop(40, Style.Unit.PX);
        this.addWidget(modifyCluSetTitle);
        this.addWidget(cluSetPicker);
    }
    
    public String getSelectedCluSetId() {
        return selectedCluSetId;
    }

    public void setSelectedCluSetId(String selectedCluSetId) {
        this.selectedCluSetId = selectedCluSetId;
    }

    private void setupCreateEditClusetView() {
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
        final FieldDescriptor cluRangeFieldEditDescriptor = addField(
                cluRangeSection, 
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
                final LayoutController parent = LayoutController.findParentLayout(cluSetRangePicker);
                if(parent != null){
//                  ((ModelWidgetBinding)cluRangeFieldDescriptor.getModelWidgetBinding()).setWidgetValue(widget, model, path)
//                  CluSetHelper cluSetHelper = CluSetHelper.wrap(model.getRoot());
//                  cluSetHelper.setCluRangeParams(value)
                    final SearchRequest searchRequest = cluSetRangePicker.getSearchWindow()
                    .getSearchRequest();
                    String selectedSearchKey = searchRequest.getSearchKey();
                    Data searchRequestData = CluSetRangeModelUtil.INSTANCE.
                    toData(searchRequest, null);
                    DataValue dataValue = new DataValue(searchRequestData);
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
        
        VerticalSection defineCluSet = initSection(getH3Title(ToolsConstants.NEW_CLU_SET_INFO), true);
        addField(defineCluSet, ToolsConstants.CLU_SET_TYPE_FIELD, null, null, null);
        addField(defineCluSet, ToolsConstants.CLU_SET_ORGANIZATION_FIELD, generateMessageInfo(ToolsConstants.ORGANIZATION), null, null);
        addField(defineCluSet, ToolsConstants.CLU_SET_NAME_FIELD, generateMessageInfo(ToolsConstants.TITLE), null, null);
        addField(defineCluSet, ToolsConstants.CLU_SET_DESCRIPTION_FIELD, generateMessageInfo(ToolsConstants.DESCRIPTION), new KSTextArea(), null);
        addField(defineCluSet, ToolsConstants.CLU_SET_EFF_DATE_FIELD, generateMessageInfo(ToolsConstants.EFFECTIVE_DATE), new KSDatePicker(), null);
        addField(defineCluSet, ToolsConstants.CLU_SET_EXP_DATE_FIELD, generateMessageInfo(ToolsConstants.EXPIRATION_DATE), new KSDatePicker(), null);
        
        VerticalSection choosingSection = new VerticalSection();
        choosingSection.addWidget(chooser);
        choosingSection.addSection(clusetDetails);
        choosingSection.addStyleName("KS-CluSetManagement-chooser");
        this.addSection(choosingSection);
        this.addWidget(selectedValuesPanel);
        KSLabel spacer = new KSLabel();
        spacer.setHeight("20px");
        this.addWidget(spacer);
        this.addSection(defineCluSet);
    }
    
    /**
     * finds the list of clusetIds in a cluset
     */
    private void searchClusetIds(SearchRequest searchRequest, final Callback<List<String>> callback) {
        searchRpcServiceAsync.search(searchRequest, new KSAsyncCallback<SearchResult>(){
            @Override
            public void onSuccess(SearchResult result) {
                if (callback != null) {
                    List<String> clusetIds = new ArrayList<String>();
                    List<SearchResultRow> rows = result.getRows();
                    int rowNum = (rows == null)? 0 : rows.size();
                    for(SearchResultRow row : rows) {
                        List<SearchResultCell> cells = row.getCells();
                        String clusetId = null;
                        for(SearchResultCell cell : cells) {
                            if(cell.getKey().equals("cluset.resultColumn.cluSetId")) {
                                clusetId = cell.getValue();
                            }
                        }
                        clusetIds.add(clusetId);
                    }
                    callback.exec(clusetIds);
                }
            }
        });
    }
    
    private void search(SearchRequest searchRequest, final Callback<List<CluInformation>> callback) {
        searchRpcServiceAsync.search(searchRequest, new KSAsyncCallback<SearchResult>(){
            @Override
            public void onSuccess(SearchResult result) {
                if (callback != null) {
                    List<CluInformation> cluInformations = new ArrayList<CluInformation>();
                    List<SearchResultRow> rows = result.getRows();
                    int rowNum = (rows == null)? 0 : rows.size();
                    for(SearchResultRow row : rows) {
                        List<SearchResultCell> cells = row.getCells();
                        CluInformation cluInformation = new CluInformation();
                        for(SearchResultCell cell : cells) {
                            if(cell.getKey().equals("lu.resultColumn.cluId")) {
                                cluInformation.setId(cell.getValue());
                            }
                            if (cell.getKey().equals("lu.resultColumn.cluOfficialIdentifier.cluCode")) {
                                cluInformation.setCode(cell.getValue());
                            }
                        }
                        cluInformations.add(cluInformation);
                    }
                    callback.exec(cluInformations);
                }
            }
        });
    }
    
    private VerticalFlowPanel prepareValuesPanel(Widget widget, String title) {
        final VerticalFlowPanel valuesPanel = new VerticalFlowPanel();
        valuesPanel.add(new KSLabel(title));
        valuesPanel.add(widget);
        valuesPanel.setVisible(false);
        return valuesPanel;
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
    
    private static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection(title);
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return section;
    }
    
    private String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel("clusetmanagement", "clusetmanagement", "draft", labelKey);
    }
    
    private SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
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
    
    private Picker configureSearch(String fieldKey) {
        QueryPath path = QueryPath.concat(null, fieldKey);
        Metadata metaData = modelDefinition.getMetadata(path);
        Picker picker = new Picker(metaData.getInitialLookup(), metaData.getAdditionalLookups());
        return picker;
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
    
    class ViewClusetData {
        private List<CluInformation> clus;
        private Map<String, List<CluInformation>> clusetsClus;
        private Map<QueryParamInfo, List<CluInformation>> clusetRanges;
        public List<CluInformation> getClus() {
            return clus;
        }
        public void setClus(List<CluInformation> clus) {
            this.clus = clus;
        }
        public Map<String, List<CluInformation>> getClusetsClus() {
            return clusetsClus;
        }
        public void setClusetsClus(Map<String, List<CluInformation>> clusetsClus) {
            this.clusetsClus = clusetsClus;
        }
        public Map<QueryParamInfo, List<CluInformation>> getClusetRanges() {
            return clusetRanges;
        }
        public void setClusetRanges(Map<QueryParamInfo, List<CluInformation>> clusetRanges) {
            this.clusetRanges = clusetRanges;
        }
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
            DataModel middleManModel = new DataModel();
            if (model != null && model.getRoot() != null) {
                middleManModel = new DataModel(model.getDefinition(), model.getRoot().copy());
            }
            binding.setModelValue(widget, middleManModel, path);
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
