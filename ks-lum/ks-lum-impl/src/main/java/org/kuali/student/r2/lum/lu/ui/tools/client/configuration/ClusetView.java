package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.Date;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.common.client.widgets.CluSetDetailsWidget;
import org.kuali.student.lum.common.client.widgets.CluSetEditorWidget;
import org.kuali.student.lum.common.client.widgets.CluSetManagementRpcService;
import org.kuali.student.lum.common.client.widgets.CluSetManagementRpcServiceAsync;
import org.kuali.student.lum.common.client.widgets.CluSetRetriever;
import org.kuali.student.lum.common.client.widgets.CluSetRetrieverImpl;
import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ClusetView extends VerticalSectionView {

    protected CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);
    protected CluSetRetriever cluSetRetriever = new CluSetRetrieverImpl();
    protected DataModelDefinition modelDefinition;
    protected DataModelDefinition searchDefinition;
    protected String selectedCluSetId;
    protected CluSetsManagementViews viewEnum;
    protected SimplePanel cluSetDisplay = new SimplePanel();
    protected CluSetEditorWidget cluSetEditor;
    protected String cluSetType;

    protected KSLabel titleLabel = new KSLabel();

    public void afterModelIsLoaded(DataModel cluSetModel) {
        titleLabel.setText(cluSetModel.<String>get("name"));
    }

    public enum CluSetsManagementViews {
        MAIN,
        CREATE,
        EDIT,
        VIEW
    }

    public ClusetView() {
        super();
    }

    public ClusetView(Enum<?> viewEnum, String name, String modelId,
            boolean showTitle) {
        super(viewEnum, name, modelId, showTitle);
    }

    public ClusetView(Enum<?> viewEnum, String name, String modelId,
            Widget titleWidget) {
        super(viewEnum, name, modelId, titleWidget);
    }

    public ClusetView(Enum<?> viewEnum, String name, String modelId) {
        super(viewEnum, name, modelId);
    }

    public void init(CluSetsManagementViews clusetViewEnum, String name, String modelId, final Callback<Boolean> onReady) {
        init(clusetViewEnum, name, modelId, false, onReady);
    }

    public void init(final CluSetsManagementViews clusetViewEnum, String name,
            String clusetMgtModel, boolean showTitle, final Callback<Boolean> onReady) {
        super.init(clusetViewEnum, name, modelId, showTitle);
        cluSetType = "kuali.cluSet.type.CreditCourse";
        if (clusetViewEnum == CluSetsManagementViews.CREATE ||
                clusetViewEnum == CluSetsManagementViews.EDIT) {
            cluSetEditor = new CluSetEditorWidget(
                    new CluSetRetrieverImpl(),
                    clusetViewEnum, name, modelId, true, null,
                    cluSetType, false);
        }
        viewEnum = clusetViewEnum;
        cluSetManagementRpcServiceAsync.getMetadata("courseSet", null, new KSAsyncCallback<Metadata>() {
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
        setStyles();
    }

    private void setStyles() {
        titleLabel.addStyleName("cluSetTitle");
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
                    new CluSetDetailsWidget(selectedCluSetId, cluSetRetriever);
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
                (CluSetsManagementController) getController();
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
        addWidget(titleLabel);
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
//        addField(generalClusInfoSection, 
//                ToolsConstants.CLU_SET_DESCRIPTION_FIELD, 
//                generateMessageInfo(ToolsConstants.DESCRIPTION),
//                new KSLabel(),
//                null);
//        addField(generalClusInfoSection, 
//                ToolsConstants.CLU_SET_EXP_DATE_FIELD, 
//                generateMessageInfo(ToolsConstants.EXPIRATION_DATE),
//                new KSLabel(),
//                null);
        this.addSection(setupGeneralClusInfoSection());
//        this.addSection(generalClusInfoSection);
        this.addWidget(new KSLabel("Items in this Course Set"));
        this.addWidget(cluSetDisplay);
        this.setStyleName("standard-content-padding");
    }

    private SummaryTableSection setupGeneralClusInfoSection() {
        SummaryTableSection result = new SummaryTableSection(getController());
        result.setEditable(false);

        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
//        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));
//        block.setTitle(getLabel(LUConstants.INFORMATION_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(ToolsConstants.CLU_SET_DESCRIPTION_FIELD, generateMessageInfo(ToolsConstants.DESCRIPTION)));
        SummaryTableFieldRow expDateRow = getFieldRow(ToolsConstants.CLU_SET_EXP_DATE_FIELD,
                generateMessageInfo(ToolsConstants.EXPIRATION_DATE), null, null, null,
                new ModelWidgetBindingSupport<HasText>() {
                    public String dateToString(Date date) {
                        String result = null;
                        DateTimeFormat format = DateTimeFormat.getFormat("MM/dd/yyyy");
                        result = format.format(date);

                        return result;
                    }

                    @Override
                    public void setModelValue(HasText widget, DataModel model, String path) {
                        // not implementing here since this value should not be edited through this widget
                    }

                    @Override
                    public void setWidgetValue(HasText widget, DataModel model, String path) {
                        try {
                            QueryPath qPath = QueryPath.parse(path);

                            Object value = null;
                            if (model != null) {
                                value = model.get(qPath);
                            }

                            if (value != null && widget != null) {
                                if (value instanceof Date) {
                                    widget.setText(dateToString((Date) value));
                                } else {
                                    widget.setText(value.toString());
                                }
                            } else if (value == null && widget != null) {
                                widget.setText("");
                            }
                        } catch (Exception e) {
                            GWT.log("Error setting widget value for: " + path, e);
                        }
                    }

                }
                , false);
        block.addSummaryTableFieldRow(expDateRow);

        result.addSummaryTableFieldBlock(block);
        return result;
    }

    protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey) {
        return getFieldRow(fieldKey, messageKey, null, null, null, null, false);
    }

    protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey, boolean optional) {
        return getFieldRow(fieldKey, messageKey, null, null, null, null, optional);
    }

    protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey, Widget widget, Widget widget2, String parentPath, ModelWidgetBinding<?> binding, boolean optional) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptorReadOnly fd = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        if (binding != null) {
            fd.setWidgetBinding(binding);
        }
        fd.setOptional(optional);

        FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget2 != null) {
            fd2.setFieldWidget(widget2);
        }
        if (binding != null) {
            fd2.setWidgetBinding(binding);
        }
        fd2.setOptional(optional);

        SummaryTableFieldRow fieldRow = new SummaryTableFieldRow(fd, fd2);

        return fieldRow;
    }

    private void setupMainView() {
        this.addStyleName("blockLayout");
        KSDocumentHeader header = new KSDocumentHeader();
        header.setTitle("Course Set Management");
        this.addWidget(header);

        Anchor createCluSet = new Anchor("<h2 class=\"contentBlock-title\">Create Course Set</h2>", true);
        createCluSet.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getController().showView(CluSetsManagementViews.CREATE);
            }
        });
        this.addWidget(createCluSet);
        this.addWidget(new KSLabel("Build a new Course set from courses, Course Sets, " +
                "or specific criteria."));

        Picker cluSetPicker = configureSearch(ToolsConstants.SEARCH_COURSE_SET);
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
        SectionTitle modifyCluSetTitle = SectionTitle.generateH2Title("View or Modify Course Sets");
        modifyCluSetTitle.addStyleName("contentBlock-title");
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

    protected void setupCreateEditClusetView() {
        String contextName = (cluSetType != null && cluSetType.equals("kuali.cluSet.type.Program")) ?
                "Program" : "Course";
        VerticalSection defineCluSet = initSection(getH3Title(ToolsConstants.DEFINE_CLUSET + contextName), true);
        
        addFields(defineCluSet,contextName);

        KSLabel spacer = new KSLabel();
        spacer.setHeight("20px");
        this.addSection(cluSetEditor);
        this.addWidget(spacer);
        this.addSection(defineCluSet);
        this.setStyleName("standard-content-padding");
    }
    
    protected void addFields(VerticalSection defineCluSet, String contextName) {
        addField(defineCluSet, ToolsConstants.CLU_SET_ORGANIZATION_FIELD, generateMessageInfo(ToolsConstants.ORGANIZATION), null, null);
        addField(defineCluSet, ToolsConstants.CLU_SET_NAME_FIELD, generateMessageInfo(ToolsConstants.TITLE + contextName), null, null);
        addField(defineCluSet, ToolsConstants.CLU_SET_DESCRIPTION_FIELD, generateMessageInfo(ToolsConstants.DESCRIPTION), new KSTextArea(), null);
        addField(defineCluSet, ToolsConstants.CLU_SET_EFF_DATE_FIELD, generateMessageInfo(ToolsConstants.EFFECTIVE_DATE), new KSDatePicker(), null);
        addField(defineCluSet, ToolsConstants.CLU_SET_EXP_DATE_FIELD, generateMessageInfo(ToolsConstants.EXPIRATION_DATE), new KSDatePicker(), null);
    }

    protected static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection(title);
        section.addStyleName(LUUIConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUUIConstants.STYLE_SECTION_DIVIDER);
        return section;
    }

    private String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel("clusetmanagement", "clusetmanagement", "draft", labelKey);
    }

    protected SectionTitle getH3Title(String labelKey) {
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
        } else {
            fd = new FieldDescriptor(path.toString(), messageKey, meta);
        }
        return fd;
    }

    protected FieldDescriptor addField(Section section,
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
