package org.kuali.student.common.ui.client.widgets.rules;

import java.util.*;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldTypeInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReqCompEditWidget extends FlowPanel {

    //widgets
    private FlowPanel reqCompTypePanel = new FlowPanel();
    private FlowPanel ReqCompEditPanel = new FlowPanel();
    private KSDropDown reqCompTypesList = new KSDropDown();
    private VerticalSectionView reqCompFieldsPanel;
    private ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.AddCancelEnum.ADD, ButtonEnumerations.AddCancelEnum.CANCEL);
    private final String NO_SELECTION_TEXT = "Select rule type";

    //data
    private List<ReqComponentTypeInfo> reqCompTypeInfoList;     //list of all Requirement Component Types based on selected Statement Type
    private ListItems listItemReqCompTypes;                 	//list of all Requirement Component Types wrapped in a list for drop down
    private Map<String, String> compositionTemplates = new HashMap<String, String>();
    private ReqComponentInfo editedReqComp;						//Req. Component that user is editing or adding
    private ReqComponentTypeInfo selectedReqCompType;           //the type of Req. Component that the user is editing or adding
    private List<String> selectedReqCompFieldTypes;             //types of all fields for given selected req. component type
    private boolean addingNewReqComp;                           //adding (true) or editing (false) req. component
    private DataModel ruleFieldsData;
    private BasicLayout reqCompFieldsLayout;
    private Map<String, Widget> customWidgets = new HashMap<String, Widget>();
    private List<Widget> customWidgetsNew;

    //other
    private Callback reqCompConfirmCallback;
    private Callback newReqCompSelectedCallback;
    private Callback fieldsMetadataTemplateCallback;
    private Callback compositionTemplateCallback;
    private Callback displayCustomWidgetCallback;
    private String newReqCompId;
    private static int tempReqCompInfoID = 999999;
    private static final String REQ_COMP_MODEL_ID = "reqCompModelId";

    private enum ReqCompEditView {VIEW}

    //TODO use app context for text

    public ReqCompEditWidget(String newReqCompId) {
        super();

        ruleFieldsData = new DataModel();
        ruleFieldsData.setRoot(new Data());
        this.newReqCompId = newReqCompId;
   //     this.relatedProgramReqInfoId = relatedProgramReqInfoId;

        //wait until req. comp. types are loaded and user actually selects a type from drop down
        reqCompTypesList.setEnabled(false);
        setEnableAddRuleButtons(false);

        setupReqCompTypesList();
        setupHandlers();

        displayReqCompListPanel();
        add(ReqCompEditPanel);
        displayConfirmButton();
    }

    private void setupHandlers() {

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
                @Override
               public void exec(ButtonEnumerations.ButtonEnum result) {

                    setEnableAddRuleButtons(false);

                    if (result == ButtonEnumerations.AddCancelEnum.ADD) {

                        //true if we have no fields for this req. component type
                        if (((selectedReqCompFieldTypes == null) || selectedReqCompFieldTypes.size() == 0) && (customWidgets.size() == 0)) {
                            finalizeRuleUpdate();
                            return;
                        }

                        //validate and retrieve fields
                        validateAndRetrieveFields();

                    } else { //user canceled add/edit rule action
                        setupNewReqComp();
                        reqCompConfirmCallback.exec(null);
                    }
               }
        });

        reqCompTypesList.addSelectionChangeHandler(new SelectionChangeHandler() {
			@Override
            public void onSelectionChange(SelectionChangeEvent event) {

			    if (!event.isUserInitiated()) {
			        return;
			    }

                //disable Step 2 section and enable 'Add Rule' and 'cancel' buttons
                 newReqCompSelectedCallback.exec(null);
                 setEnableAddRuleButtons(true);

                 List<String> ids = ((KSSelectItemWidgetAbstract)event.getWidget()).getSelectedItems();
                 selectedReqCompType = reqCompTypeInfoList.get(Integer.valueOf(ids.get(0)));

                 if (addingNewReqComp) {
                     createReqComp(selectedReqCompType);
                 } else {
                	 //editedReqComp.setRequiredComponentType(selectedReqCompType);
                     editedReqComp.setType(selectedReqCompType.getId());
                 }

                 displayFieldsSection();
         }});

    }

    private void validateAndRetrieveFields() {

        final List<ReqCompFieldInfo> editedFields = new ArrayList<ReqCompFieldInfo>();
        reqCompFieldsLayout.updateModel();

        //1. validate and retrieve non-custom fields
        if (ruleFieldsData.getRoot().size() > 0) {
            ruleFieldsData.validate(new Callback<List<ValidationResultInfo>>() {
                @Override
                public void exec(List<ValidationResultInfo> validationResults) {

                    //do not proceed if the user input is not valid
                    if (!reqCompFieldsLayout.isValid(validationResults, true, true)) {
                        setEnableAddRuleButtons(true);
                        return;
                    }

                    //2. retrieve entered values and set the rule info
                    for (ReqCompFieldTypeInfo fieldTypeInfo : selectedReqCompType.getReqCompFieldTypeInfos()) {

                        //we handle custom widgets elsewhere in order to deal with callbacks
                        if (customWidgets.containsKey(fieldTypeInfo.getId())) {
                            continue;
                        }

                        ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
                        fieldInfo.setId(null);
                        fieldInfo.setType(fieldTypeInfo.getId());
                        String fieldValue = ruleFieldsData.getRoot().get(fieldTypeInfo.getId()).toString();
                        fieldInfo.setValue((fieldValue == null ? "" : fieldValue.toString()));
                        editedFields.add(fieldInfo);
                    }

                    //2. retrieve non-custom fields
                    retrieveValuesFromCustomWidgets(editedFields);
                }
            });
        } else {
            //2. retrieve non-custom fields
            retrieveValuesFromCustomWidgets(editedFields);
        }
    }

    private void retrieveValuesFromCustomWidgets(final List<ReqCompFieldInfo> editedFields) {

        if (customWidgets.size() == 0) {
            editedReqComp.setReqCompFields(editedFields);
            finalizeRuleUpdate();
            return;
        }

        final Iterator iter = customWidgets.entrySet().iterator();
        while (iter.hasNext()) {
            final String fieldType = (String)((Map.Entry)iter.next()).getKey();
            ((AccessWidgetValue)customWidgets.get(fieldType)).getValue(new Callback<String>() {
                @Override
                public void exec(String widgetValue) {
                    //clu set validation failed...
                    if (widgetValue == null) {
                        setEnableAddRuleButtons(true);
                        return;
                    }
                    ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
                    fieldInfo.setId(null);
                    fieldInfo.setType(fieldType);
                    fieldInfo.setValue(widgetValue);
                    editedFields.add(fieldInfo);

                    //TODO not sure if this will work all the time due to parallel nature of this code but running out of time
                    iter.remove();
                    if (customWidgets.size() == 0) {
                        //3. update req. component being edited
                        editedReqComp.setReqCompFields(editedFields);
                        finalizeRuleUpdate();
                    }
                }
            });
        }
    }

    private void finalizeRuleUpdate() {
        editedReqComp.setType(selectedReqCompType.getId());

        //callback needs to update NL for given req. component and the rule
        reqCompConfirmCallback.exec(editedReqComp);
    }

    public void setupNewReqComp() {
        addingNewReqComp = true;
        selectedReqCompType = null;
        createReqComp(null);
        ReqCompEditPanel.clear();
        redraw();
    }

    /* create a new Req. Component Type based on user selection or empty at first */
    private void createReqComp(ReqComponentTypeInfo reqCompTypeInfo) {
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("");
        desc.setFormatted("");
        editedReqComp = new ReqComponentInfoUi();
        editedReqComp.setDesc(desc);
        editedReqComp.setId(newReqCompId + Integer.toString(tempReqCompInfoID++));
        editedReqComp.setReqCompFields(null);
        //editedReqComp.setRequiredComponentType(reqCompTypeInfo);
        if (reqCompTypeInfo != null) {
            editedReqComp.setType(reqCompTypeInfo.getId());
        }
    }

    //call when user wants to edit an existing req. component
    public void setupExistingReqComp(ReqComponentInfo existingReqComp) {
        if (!actionCancelButtons.getButton(ButtonEnumerations.AddCancelEnum.ADD).isEnabled()) {
            return;
        }

        addingNewReqComp = false;
        editedReqComp = existingReqComp;
        //originalReqType = editedReqComp.getRequiredComponentType();

        selectedReqCompType = null;
        for (ReqComponentTypeInfo aReqCompTypeInfoList : reqCompTypeInfoList) {
            if (editedReqComp.getType().equals(aReqCompTypeInfoList.getId())) {
                selectedReqCompType = aReqCompTypeInfoList;
                break;
            }
        }
        if (selectedReqCompType == null) {
            GWT.log("Unknown Requirement Component Type found: " + existingReqComp.getType(), null);
            Window.alert("Unknown Requirement Component Type found: " + existingReqComp.getType());
        }

        redraw();
    }

    private void redraw() {

        selectReqCompTypeInList();

        displayFieldsSection();

        if (addingNewReqComp) {
            actionCancelButtons.getButton(ButtonEnumerations.AddCancelEnum.ADD).setText("Add Rule");
            setEnableAddRuleButtons(false);
        } else {
            actionCancelButtons.getButton(ButtonEnumerations.AddCancelEnum.ADD).setText("Update Rule");
            setEnableAddRuleButtons(false);
            if (selectedReqCompType != null) {
                setEnableAddRuleButtons(true);
            }
        }
    }

    public void displayFieldsSection() {

        //if no req. comp. type is selected then don't display anything
        if (selectedReqCompType == null) {
            return;
        }

        //no display if the req. comp. type has no fields
        if ((selectedReqCompType.getReqCompFieldTypeInfos() == null) || selectedReqCompType.getReqCompFieldTypeInfos().isEmpty()) {
            ReqCompEditPanel.clear();
            return;
        }

        //get composition template either from local cache or from service through user of this widget
        String compositionTemplate = compositionTemplates.get(selectedReqCompType.getId());
        if (compositionTemplate == null) {
            compositionTemplateCallback.exec(editedReqComp);
            return;
        }

        displayFieldsStart(compositionTemplate);
    }

    public void displayFieldsStart(String compositionTemplate) {

        compositionTemplates.put(selectedReqCompType.getId(), compositionTemplate);
        setEnableAddRuleButtons(true);

        selectedReqCompFieldTypes = new ArrayList<String>();
        for (ReqCompFieldTypeInfo fieldTypeInfo : selectedReqCompType.getReqCompFieldTypeInfos()) {
            selectedReqCompFieldTypes.add(fieldTypeInfo.getId());
        }

        fieldsMetadataTemplateCallback.exec(selectedReqCompFieldTypes);
    }

    public void displayFieldsEnd(List<Metadata> fieldsMetadataList) {

        customWidgets = new HashMap<String, Widget>();
        List<ReqCompFieldInfo> reqCompFields = (editedReqComp == null ? null : editedReqComp.getReqCompFields());
        reqCompFieldsPanel = new VerticalSectionView(ReqCompEditView.VIEW, "", REQ_COMP_MODEL_ID, false);
        reqCompFieldsPanel.addStyleName("KS-Rule-FieldsList");
        Map<String, FieldDescriptor> fields = new HashMap<String, FieldDescriptor>();

        int ix = 0;
        Metadata fieldGradeTypeMetadata = null;
        Metadata fieldGradeMetadata = null;
        ReqCompEditPanel.clear();
        Map<String, Metadata> fieldDefinitionMetadata = new HashMap<String,Metadata>();
        for (Metadata oneFieldMetadata : fieldsMetadataList) {

            Metadata fieldMetadata = oneFieldMetadata.getProperties().get("value");
            String fieldType = selectedReqCompFieldTypes.get(ix++);

            //add clusets separately
            if (RulesUtil.isCluSetWidget(fieldType) || RulesUtil.isCluWidget(fieldType)) {
                displayCustomWidgetCallback.exec(fieldType);
                continue;
            }

            String fieldLabel = getFieldLabel(fieldType);
            FieldDescriptor fd;
            if (RulesUtil.isGradeWidget(fieldType)) {

                if (fieldType.toLowerCase().equals("kuali.reqComponent.field.type.gradeType.id".toLowerCase())) {
                    fieldGradeTypeMetadata = fieldMetadata;
                }
                if (fieldType.toLowerCase().equals("kuali.reqComponent.field.type.grade.id".toLowerCase())) {
                    fieldGradeMetadata = fieldMetadata;
                }

                if ((fieldGradeTypeMetadata != null) && (fieldGradeMetadata != null)) {
                    Widget gradeWidget = customWidgetsNew.get(0);
                    List<Metadata> fieldsMetadata = new ArrayList<Metadata>();
                    fieldsMetadata.add(fieldGradeTypeMetadata);
                    fieldsMetadata.add(fieldGradeMetadata);
                    ((AccessWidgetValue)gradeWidget).initWidget(fieldsMetadata);
                    fieldType = "kuali.reqComponent.field.type.grade.id";
                    fieldLabel = getFieldLabel(fieldType);
                    fieldMetadata = fieldGradeMetadata;
                    fd = new FieldDescriptor(fieldType, new MessageKeyInfo(fieldLabel), fieldMetadata, gradeWidget);
                } else {
                    continue;
                }
            } else {
                 fd = new FieldDescriptor(fieldType, new MessageKeyInfo(fieldLabel), fieldMetadata);
            }

            //reqCompFieldsPanel.addField(fd);
            fields.put(fieldType, fd);

            //add field to the data model metadata
            fieldDefinitionMetadata.put(fieldType, fieldMetadata);
        }

        //now we add fields to the panel in proper order based on composition template
        for (String type : getFieldSequence()) {
            if (RulesUtil.isCluSetWidget(type) || RulesUtil.isCluWidget(type) ||
                type.toLowerCase().equals("kuali.reqComponent.field.type.gradeType.id".toLowerCase())) {
                continue;
            }
            reqCompFieldsPanel.addField(fields.get(type));
        }

        //setup data model
        Metadata modelDefinitionMetadata = new Metadata();
        modelDefinitionMetadata.setCanView(true);
        modelDefinitionMetadata.setDataType(Data.DataType.DATA);
        modelDefinitionMetadata.setProperties(fieldDefinitionMetadata);
        ruleFieldsData = new DataModel();
        ruleFieldsData.setRoot(new Data());
        ruleFieldsData.setDefinition(new DataModelDefinition(modelDefinitionMetadata));

        if (selectedReqCompFieldTypes.contains("kuali.reqComponent.field.type.grade.id")) {
            selectedReqCompFieldTypes.remove("kuali.reqComponent.field.type.gradeType.id");
        }

        //initialize fields with values if user is editing an existing rule
        if (!addingNewReqComp) {
            for (String fieldType : selectedReqCompFieldTypes) {
                String fieldValue = getFieldValue(reqCompFields, fieldType);
                if (fieldValue != null) {
                    if (RulesUtil.isCluSetWidget(fieldType) || RulesUtil.isCluWidget(fieldType)) {
                        ((AccessWidgetValue)customWidgets.get(fieldType)).setValue(fieldValue);
                    } else {
                        ruleFieldsData.set(QueryPath.parse(fieldType), fieldValue);
                    }
                }
            }
        }

        //setup controller
        reqCompFieldsLayout = new BasicLayout(null);
        reqCompFieldsLayout.addView(reqCompFieldsPanel);
        reqCompFieldsLayout.setDefaultModelId(REQ_COMP_MODEL_ID);
        reqCompFieldsLayout.registerModel(REQ_COMP_MODEL_ID, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                callback.onModelReady(ruleFieldsData);
            }
        });

        //show fields
        ReqCompEditPanel.add(reqCompFieldsLayout);
        reqCompFieldsLayout.showView(ReqCompEditView.VIEW);

        //TODO save history
    }

    //TODO we should mix the custom widgets with regular widgets
    public void displayCustomWidget(String fieldType, Widget customWidget) {
        KSLabel label = new KSLabel(getFieldLabel(fieldType));
        label.addStyleName("KS-Rule-ReqComp-Custom-Widget-label");
        ReqCompEditPanel.add(label);
        customWidgets.put(fieldType, customWidget);
        ReqCompEditPanel.add(customWidget);
    }

    private String getFieldLabel(String fieldType) {
        String compositionTemplate = compositionTemplates.get(selectedReqCompType.getId());
        String label = compositionTemplate.substring(compositionTemplate.indexOf(fieldType) + fieldType.length());
        int ix = label.indexOf("reqCompFieldLabel") + "reqCompFieldLabel".length();
        int ix2 = label.indexOf(">", ix);
        return label.substring(ix, ix2).replace("=", "").trim();
    }

    private List<String> getFieldSequence() {
        List<String> fieldTypes = new ArrayList<String>();
        String compositionTemplate = compositionTemplates.get(selectedReqCompType.getId());

        int stIx = 0;
        while (compositionTemplate.indexOf("reqCompFieldType", stIx) > 0) {
            stIx = compositionTemplate.indexOf("reqCompFieldType") + "reqCompFieldType".length();
            compositionTemplate = compositionTemplate.substring(stIx);
            stIx = 0;
            int ix = compositionTemplate.indexOf(";");
            String type = compositionTemplate.substring(0, ix).replace("=", "").replace(" ", "");
            fieldTypes.add(type);
        }

        return fieldTypes;
    }

    private String getFieldValue(List<ReqCompFieldInfo> fields, String key) {

        if (fields == null) {
            return null;
        }

        for (ReqCompFieldInfo fieldInfo : fields) {
            if (fieldInfo.getType().equals(key)) {
                return (fieldInfo.getValue() == null ? "" : fieldInfo.getValue());
            }
        }

        return "";
    }

    private void displayConfirmButton() {
        actionCancelButtons.addStyleName("KS-Rule-ReqComp-btn");
        actionCancelButtons.getButton(ButtonEnumerations.AddCancelEnum.ADD).setText("Add Rule");
        add(actionCancelButtons);
    }

    private void displayReqCompListPanel() {

        reqCompTypePanel.setStyleName("KS-Rule-ReqCompList-box");

        SectionTitle subHeader = SectionTitle.generateH5Title("Select rule type");
        subHeader.setStyleName("KS-Rule-ReqComp-header");
        reqCompTypePanel.add(subHeader);

        KSLabel instructions = new KSLabel("Use the list below to select the type of rule you would like to add to this requirement");
        reqCompTypePanel.add(instructions);

        reqCompTypesList.addStyleName("KS-Rule-ReqCompList");
        reqCompTypePanel.add(reqCompTypesList);
        add(reqCompTypePanel);
    }

    private void selectReqCompTypeInList() {
        if (selectedReqCompType == null) {
            if (reqCompTypesList.getSelectedItem() != null) {
                reqCompTypesList.deSelectItem(reqCompTypesList.getSelectedItem());
            }
            return;
        }

        int i = 0;
        for (ReqComponentTypeInfo comp : reqCompTypeInfoList) {
            if (comp.getId().equals(selectedReqCompType.getId())) {
                reqCompTypesList.selectItem(Integer.toString(i));
                break;
            }
            i++;
        }
    }

    private void setupReqCompTypesList() {
	    listItemReqCompTypes = new ListItems() {
	        @Override
	        public List<String> getAttrKeys() {
	            List<String> attributes = new ArrayList<String>();
	            attributes.add("Key");
	            return attributes;
	        }

	        @Override
	        public String getItemAttribute(String id, String attrkey) {
	            String value = null;
	            Integer index;
	            try{
	                index = Integer.valueOf(id);
	                value = reqCompTypeInfoList.get(index).getDescr();
	            } catch (Exception e) {
	            }

	            return value;
	        }

	        @Override
	        public int getItemCount() {
	            return reqCompTypeInfoList.size();
	        }

	        @Override
	        public List<String> getItemIds() {
	            List<String> ids = new ArrayList<String>();
	            for(int i = 0; i < reqCompTypeInfoList.size(); i++){
	                ids.add(String.valueOf(i));
	            }
	            return ids;
	        }

	        @Override
	        public String getItemText(String id) {
	            return getItemAttribute(id, "?");
	        }
	    };
    }

    private void setEnableAddRuleButtons(boolean enable) {
        actionCancelButtons.getButton(ButtonEnumerations.AddCancelEnum.ADD).setEnabled(enable);
        actionCancelButtons.getButton(ButtonEnumerations.AddCancelEnum.CANCEL).setEnabled(enable);

        if (!enable) {
            reqCompFieldsPanel = null;
        }
    }

    //called by view that manages this widget, passing list of req. component types
    public void setReqCompList(List<ReqComponentTypeInfo> reqComponentTypeInfoList) {

        if (reqComponentTypeInfoList == null || reqComponentTypeInfoList.size() == 0) {
            GWT.log("Missing Requirement Component Types", null);
            Window.alert("Missing Requirement Component Types");
            return;
        }

        //store all requirement components locally
        reqCompTypeInfoList = reqComponentTypeInfoList;

        reqCompTypesList.setListItems(listItemReqCompTypes);
        if (reqCompTypesList.getSelectedItem() != null) {
            reqCompTypesList.deSelectItem(reqCompTypesList.getSelectedItem());
        }

        reqCompTypesList.setEnabled(true);
        setEnableAddRuleButtons(false);
    }

    public void setCustomWidgets(List<Widget> customWidgets) {
        customWidgetsNew = customWidgets;
    }

    public void setReqCompConfirmButtonClickCallback(Callback<ReqComponentInfoUi> actionButtonClickedReqCompCallback) {
        reqCompConfirmCallback = actionButtonClickedReqCompCallback;
    }

    public void setRetrieveCompositionTemplateCallback(Callback<ReqComponentInfo> callback) {
        compositionTemplateCallback = callback;
    }

    public void setRetrieveFieldsMetadataCallback(Callback<List<String>> callback) {
        fieldsMetadataTemplateCallback = callback;
    }

    public void setNewReqCompSelectedCallbackCallback(Callback<ReqComponentInfo> callback) {
        newReqCompSelectedCallback = callback;
    }

    public void setRetrieveCustomWidgetCallback(Callback<String> callback) {
        displayCustomWidgetCallback = callback;
    }
}


