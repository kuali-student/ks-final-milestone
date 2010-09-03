package org.kuali.student.common.ui.client.widgets.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class ReqCompEditWidget extends FlowPanel {

    //widgets
    private FlowPanel reqCompTypePanel = new FlowPanel();
    private KSDropDown reqCompTypesList = new KSDropDown();
    private FlowPanel reqCompWidgetsPanel = new FlowPanel();
    private List<Object> reqCompWidgets = new ArrayList<Object>();
    private ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.AddCancelEnum.ADD, ButtonEnumerations.AddCancelEnum.CANCEL);
    private final String NO_SELECTION_TEXT = "Select rule type";

    //data
    private List<ReqComponentTypeInfo> reqCompTypeInfoList;     //list of all Requirement Component Types based on selected Statement Type
    private ListItems listItemReqCompTypes;                 	//list of all Requirement Component Types
    private Map<String, String> compositionTemplates = new HashMap<String, String>();    
    private ReqComponentInfo editedReqComp;						//Req. Component that user is editing or adding
    private ReqComponentTypeInfo selectedReqType;
    private ReqComponentTypeInfo originalReqType;
    private boolean addingNewReqComp;                           //adding (true) or editing (false) req. component
    private Callback reqCompConfirmCallback;
    private Callback compositionTemplateCallback;
    private static int tempCounterID = 99999;

    //TODO use app context for text

    public ReqCompEditWidget() {
        super();

        //wait until req. comp. types are loaded and user actually selects a type from drop down
        reqCompTypesList.setEnabled(false);
        setEnableAddRuleButtons(false);

        setupReqCompTypesList();
        setupHandlers();
        
        displayReqCompListPanel();
        add(reqCompWidgetsPanel);
        reqCompWidgetsPanel.addStyleName("KS-Program-Rule-FieldsList");
        displayConfirmButton();

        //setupNewReqComp();
    }

    private void setupHandlers() {

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
                @Override
               public void exec(ButtonEnumerations.ButtonEnum result) {
                    if (result == ButtonEnumerations.AddCancelEnum.ADD) {
                        //1. check that all fields have values

                        //2. update req. component being edited
                        //editedReqComp.setReqCompFields(editedFields);
                        //editedReqComp.setType(selectedReqType.getId());

                        setEnableAddRuleButtons(false);
                        if (reqCompTypesList.getSelectedItem() != null) {
                            reqCompTypesList.deSelectItem(reqCompTypesList.getSelectedItem());
                        }
                        
                        //callback needs to update NL for given req. component and the rule
                        reqCompConfirmCallback.exec(editedReqComp);
                    } else {
                        setupNewReqComp();
                    }
               }                                             
        });

        reqCompTypesList.addSelectionChangeHandler(new SelectionChangeHandler() {
			@Override
            public void onSelectionChange(SelectionChangeEvent event) {

			    if (!event.isUserInitiated()) {
			        return;
			    }

                 setEnableAddRuleButtons(true);

                 List<String> ids = ((KSSelectItemWidgetAbstract)event.getWidget()).getSelectedItems();
                 selectedReqType = reqCompTypeInfoList.get(Integer.valueOf(ids.get(0)));

                 if (addingNewReqComp) {
                     createReqComp(selectedReqType);
                 } else {
                	 editedReqComp.setRequiredComponentType(selectedReqType);
                     editedReqComp.setType(selectedReqType.getId());
                 }

                 displayReqComponentDetails();
         }});

    }

    public void setupNewReqComp() {
        addingNewReqComp = true;
        selectedReqType = null;        
        originalReqType = null;
        createReqComp(null);
        redraw();        
    }

    /* create a new Req. Component Type based on user selection or empty at first */
    private void createReqComp(ReqComponentTypeInfo reqCompTypeInfo) {        
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("");
        desc.setFormatted("");
        editedReqComp = new ReqComponentInfo();
        editedReqComp.setDesc(desc);
        editedReqComp.setId(Integer.toString(tempCounterID++));  
        editedReqComp.setReqCompFields(null);
        editedReqComp.setRequiredComponentType(reqCompTypeInfo);
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
        originalReqType = editedReqComp.getRequiredComponentType();

        selectedReqType = null;
        for (ReqComponentTypeInfo aReqCompTypeInfoList : reqCompTypeInfoList) {
            if (editedReqComp.getType().equals(aReqCompTypeInfoList.getId())) {
                selectedReqType = aReqCompTypeInfoList;
                break;
            }
        }
        if (selectedReqType == null) {
            GWT.log("Unknown Requirement Component Type found: " + existingReqComp.getType(), null);
            Window.alert("Unknown Requirement Component Type found: " + existingReqComp.getType());
        }

        redraw();        
    }

    private void redraw() {

        selectReqCompTypeInList();        

        displayReqComponentDetails();

        if (addingNewReqComp) {
            actionCancelButtons.getButton(ButtonEnumerations.AddCancelEnum.ADD).setText("Add Rule");
            setEnableAddRuleButtons(false);
        } else {
            actionCancelButtons.getButton(ButtonEnumerations.AddCancelEnum.ADD).setText("Update Rule"); 
            setEnableAddRuleButtons(false);
            if (selectedReqType != null) {
                setEnableAddRuleButtons(true);
            }
        }
    }

    public void displayReqComponentDetails() {

        //if no req. comp. type is selected then don't display anything
        if (selectedReqType == null) {
            return;
        }

        //get composition template either from local cache or from service through user of this widget
        String compositionTemplate = compositionTemplates.get(selectedReqType.getId());
        if (compositionTemplate == null) {
            compositionTemplateCallback.exec(editedReqComp);
            return;
        }

        displayReqComponentFields(compositionTemplate);
    }

    public void displayReqComponentFields(String compositionTemplate) {
        
        compositionTemplates.put(selectedReqType.getId(), compositionTemplate);
        //setEnableAddRuleButtons(true);
        
        List<ReqCompFieldInfo> reqCompFields = (editedReqComp == null ? null : editedReqComp.getReqCompFields());
        reqCompWidgetsPanel.clear();
        reqCompWidgets = new ArrayList();

        //display one widget for each template token
        final String[] tokens = compositionTemplate.split("[<>]");
        for (String token : tokens) {

            token = token.trim();
            if (token.isEmpty()) {
                continue;
            }

            //find tokens identifying a field
            final Map<String, String> fieldProperties = new HashMap<String, String>();
            final String[] fieldTokens = token.split(";");
            if (fieldTokens != null) {
                for (String fieldToken : fieldTokens) {
                    if (fieldToken == null) continue;
                    String[] keyValuePair = fieldToken.split("=");
                    if (keyValuePair != null && keyValuePair.length == 2) {
                        fieldProperties.put(keyValuePair[0], keyValuePair[1]);
                    }
                }
            }

            String fieldType = fieldProperties.get("reqCompFieldType");
            String fieldLabel = fieldProperties.get("reqCompFieldLabel");
            String fieldValue = getFieldValue(reqCompFields, fieldType);

            //TODO replace with metadata and check on req. comp. field type
            if (editedReqComp.getRequiredComponentType().getId().equals("kuali.reqComponent.type.course.permission.org.required")) {
                final KSTextBox valueWidget = new KSTextBox();
                valueWidget.setName(fieldType);
                valueWidget.setText(fieldValue);
                reqCompWidgetsPanel.add(new KSLabel(fieldLabel + ":"));
                reqCompWidgetsPanel.add(valueWidget);
                reqCompWidgets.add(valueWidget);
                continue;
            }

        }

        //TODO save history
    }

    private String getFieldValue(List<ReqCompFieldInfo> fields, String key) {

        if (fields == null) {
            return null;
        }

        for (ReqCompFieldInfo fieldInfo : fields) {
            if (fieldInfo.getId().equals(key)) {
                return (fieldInfo.getValue() == null ? "" : fieldInfo.getValue());
            }
        }
        
        return "";
    }

    private void displayConfirmButton() {
        actionCancelButtons.addStyleName("KS-Program-Rule-ReqComp-btn");
        actionCancelButtons.getButton(ButtonEnumerations.AddCancelEnum.ADD).setText("Add Rule");        
        add(actionCancelButtons);
    }

    private void displayReqCompListPanel() {

        reqCompTypePanel.setStyleName("KS-Program-Rule-ReqCompList-box");

        SectionTitle subHeader = SectionTitle.generateH5Title("Select rule type");
        subHeader.setStyleName("KS-Program-Rule-ReqComp-header");
        reqCompTypePanel.add(subHeader);

        KSLabel instructions = new KSLabel("Use the list below to select the type of rule you would like to add to this requirement");
        reqCompTypePanel.add(instructions);
        
        reqCompTypesList.addStyleName("KS-Program-Rule-ReqCompList");
        reqCompTypePanel.add(reqCompTypesList);
        add(reqCompTypePanel);
    }

    private void selectReqCompTypeInList() {
        if (selectedReqType == null) {
            if (reqCompTypesList.getSelectedItem() != null) {
                reqCompTypesList.deSelectItem(reqCompTypesList.getSelectedItem());
            }
            return;
        }

        int i = 0;
        for (ReqComponentTypeInfo comp : reqCompTypeInfoList) {
            if (comp.getId().equals(selectedReqType.getId())) {
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
            reqCompWidgetsPanel.clear();
            reqCompWidgets.clear();
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

    public void setReqCompConfirmButtonClickCallback(Callback<ReqComponentInfo> callback) {
        reqCompConfirmCallback = callback;
    }

    public void setRetrieveCompositionTemplateCallback(Callback<ReqComponentInfo> callback) {
        compositionTemplateCallback = callback;
    }
}


