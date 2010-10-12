package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.common.client.lo.LUConstants;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcService;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ClusetView extends VerticalSectionView {

    private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);
    private DataModelDefinition modelDefinition;
    private DataModelDefinition searchDefinition;
    private String selectedCluSetId;
    private CluSetsManagementViews viewEnum;
    private SimplePanel cluSetDisplay = new SimplePanel();
    private CluSetEditorWidget cluSetEditor;

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
        if (clusetViewEnum == CluSetsManagementViews.CREATE ||
                clusetViewEnum == CluSetsManagementViews.EDIT) {
            cluSetEditor = new CluSetEditorWidget(
                    clusetViewEnum, name, modelId, false, null);
        }
        viewEnum = clusetViewEnum;
        cluSetManagementRpcServiceAsync.getMetadata("cluset", null, new KSAsyncCallback<Metadata>(){
            @Override
            public void handleFailure(Throwable caught) {
                Window.alert("Failed to retrieve cluset definition");
            }
            @Override
            public void onSuccess(Metadata result) {
                DataModelDefinition def = new DataModelDefinition(result);
                setModelDefinition(def);
                cluSetManagementRpcServiceAsync.getMetadata("search", null, new AsyncCallback<Metadata>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed to retrieve searchDefinition");
                    }
                    @Override
                    public void onSuccess(Metadata result) {
                        setSearchDefinition(new DataModelDefinition(result));
                        setupView(clusetViewEnum);
                        if (onReady != null) {
                            onReady.exec(new Boolean(true));
                        }
                    }
                });
            }
        });
    }
    
    private void setModelDefinition(DataModelDefinition modelDefinition) {
        this.modelDefinition = modelDefinition;
    }
    
    private void setSearchDefinition(DataModelDefinition searchDefinition) {
        this.searchDefinition = searchDefinition;
    }
    
    @Override
    public void beforeShow(Callback<Boolean> onReadyCallback) {
        super.beforeShow(onReadyCallback);
        if (viewEnum == CluSetsManagementViews.VIEW) {
            refreshCluSetDisplay();
        }
    }
    
    private void refreshCluSetDisplay() {
        if (this.selectedCluSetId != null) {
            CluSetDetailsWidget clusetDetailsWidget = 
                new CluSetDetailsWidget(selectedCluSetId, cluSetManagementRpcServiceAsync);
            cluSetDisplay.clear();
            cluSetDisplay.setWidget(clusetDetailsWidget);
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
    
    @Override
    public void onHistoryEvent(String historyStack) {
        super.onHistoryEvent(historyStack);
        CluSetsManagementController cluSetsManagementController = 
            (CluSetsManagementController)getController();
        selectedCluSetId = HistoryManager.getTokenMap(historyStack).get("docId");
        cluSetsManagementController.getMainView().setSelectedCluSetId(selectedCluSetId);
        cluSetsManagementController.showView(CluSetsManagementViews.VIEW);
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
        VerticalSection defineCluSet = initSection(getH3Title(ToolsConstants.NEW_CLU_SET_INFO), true);
        FieldDescriptor typeField = getFieldDescriptor(ToolsConstants.CLU_SET_TYPE_FIELD, null, null, null);
        typeField.getFieldWidget().setVisible(false);
        defineCluSet.addField(typeField);
        addField(defineCluSet, ToolsConstants.CLU_SET_ORGANIZATION_FIELD, generateMessageInfo(ToolsConstants.ORGANIZATION), null, null);
        addField(defineCluSet, ToolsConstants.CLU_SET_NAME_FIELD, generateMessageInfo(ToolsConstants.TITLE), null, null);
        addField(defineCluSet, ToolsConstants.CLU_SET_DESCRIPTION_FIELD, generateMessageInfo(ToolsConstants.DESCRIPTION), new KSTextArea(), null);
        addField(defineCluSet, ToolsConstants.CLU_SET_EFF_DATE_FIELD, generateMessageInfo(ToolsConstants.EFFECTIVE_DATE), new KSDatePicker(), null);
        addField(defineCluSet, ToolsConstants.CLU_SET_EXP_DATE_FIELD, generateMessageInfo(ToolsConstants.EXPIRATION_DATE), new KSDatePicker(), null);
        
        KSLabel spacer = new KSLabel();
        spacer.setHeight("20px");
        this.addSection(cluSetEditor);
        this.addWidget(spacer);
        this.addSection(defineCluSet);
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

    private Picker configureSearch(String fieldKey) {
        QueryPath path = QueryPath.concat(null, fieldKey);
        Metadata metaData = searchDefinition.getMetadata(path);
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

    @Override
    public void setController(Controller controller) {
        super.setController(controller);
        if (cluSetEditor != null) {
            cluSetEditor.setController(controller);
        }
    }
    
}
