package org.kuali.student.common.ui.client.widgets.rules;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class ReqCompEditWidget extends FlowPanel {

    private FlowPanel ruleTypePanel = new FlowPanel();
    private KSDropDown reqCompTypesList = new KSDropDown();
    private FlowPanel ruleFieldsPanel = new FlowPanel();
    private KSButton confirmButton = new KSButton("", KSButtonAbstract.ButtonStyle.FORM_SMALL);
    private final String NO_SELECTION_TEXT = "Select rule type";

    //widget's data
    private List<ReqComponentTypeInfo> reqCompTypeInfoList;     //list of all Requirement Component Types based on selected Statement Type
    private ListItems listItemReqCompTypes;                 	//list of all Requirement Component Types
    private ReqComponentInfo editedReqComp;						//Req. Component that user is editing or adding
    private ReqComponentTypeInfo selectedReqType;
    private ReqComponentTypeInfo originalReqType;
    private boolean addingNewReqComp;                           //adding (true) or editing (false) req. component
    private Callback reqCompConfirmCallback;

    //TODO use app context for text

    public ReqCompEditWidget() {
        super();

        //wait until req. comp. types are loaded and user actually selects a type from drop down
        reqCompTypesList.setEnabled(false);
        confirmButton.setEnabled(false);
        
        setupReqCompTypesList();
        setupHandlers();
        
        displayReqCompListPanel();
        add(ruleFieldsPanel);
        ruleFieldsPanel.addStyleName("KS-Program-Rule-FieldsList");
        displayConfirmButton();

        setupNewReqComp();
    }

    private void setupHandlers() {

        /*  //TODO do we have a cancel button?
        cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            }
        });   */

        confirmButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {

                //1. check that all fields have values

                //2. update req. component being edited
                //editedReqComp.setReqCompFields(editedFields);
                //editedReqComp.setType(selectedReqType.getId());

                confirmButton.setEnabled(false);
                //callback needs to update NL for given req. component and the rule
                reqCompConfirmCallback.exec(editedReqComp);
            }
        });

        reqCompTypesList.addSelectionChangeHandler(new SelectionChangeHandler() {
			@Override
            public void onSelectionChange(SelectionChangeEvent event) {

			    if (!event.isUserInitiated()) {
			        return;
			    }

                 confirmButton.setEnabled(true);

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
        editedReqComp.setId("");  
        editedReqComp.setReqCompFields(null);
        editedReqComp.setRequiredComponentType(reqCompTypeInfo);
        if (reqCompTypeInfo != null) editedReqComp.setType(reqCompTypeInfo.getId());
    }

    //call when user wants to edit an existing req. component
    public void setupExistingReqComp(ReqComponentInfo existingReqComp) {
        if (!confirmButton.isEnabled()) {
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
            confirmButton.setText("Add Rule");
            confirmButton.setEnabled(false);
        } else {
            confirmButton.setText("Update Rule");
            confirmButton.setEnabled(false);
            if (selectedReqType != null) {
                confirmButton.setEnabled(true);
            }
        }
    }

    private void displayReqComponentDetails() {

        //if no req. comp. type is selected then don't display anything
        if (selectedReqType == null) {
            return;
        }

        //TODO
        
    }

    private void displayConfirmButton() {
        confirmButton.addStyleName("KS-Program-Rule-ReqComp-btn");
        confirmButton.setText("Add Rule");        
        add(confirmButton);
    }

    private void displayReqCompListPanel() {

        ruleTypePanel.setStyleName("KS-Program-Rule-ReqCompList-box");

        SectionTitle subHeader = SectionTitle.generateH5Title("Select rule type");
        subHeader.setStyleName("KS-Program-Rule-ReqComp-header");
        ruleTypePanel.add(subHeader);

        KSLabel instructions = new KSLabel("Use the list below to select the type of rule you would like to add to this requirement");
        ruleTypePanel.add(instructions);
        
        reqCompTypesList.addStyleName("KS-Program-Rule-ReqCompList");
        ruleTypePanel.add(reqCompTypesList);
        add(ruleTypePanel);
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

    public void setReqCompConfirmButtonClickCallback(Callback<ReqComponentInfo> callback) {
        reqCompConfirmCallback = callback;
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
        confirmButton.setEnabled(true);
    }
}


