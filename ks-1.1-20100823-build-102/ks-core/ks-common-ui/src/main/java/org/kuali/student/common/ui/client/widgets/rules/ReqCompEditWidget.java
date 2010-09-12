package org.kuali.student.common.ui.client.widgets.rules;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class ReqCompEditWidget extends FlowPanel {

    KSDropDown reqCompTypesList = new KSDropDown();
    KSButton confirmButton = new KSButton("Add a Rule", KSButtonAbstract.ButtonStyle.FORM_SMALL);

    //widget's data
    private List<ReqComponentTypeInfo> reqCompTypeInfoList;     	//list of all Requirement Component Types based on selected Statement Type
    private ListItems listItemReqCompTypes;                 	//list of all Requirement Component Types
    private ReqComponentInfo editedReqComp;						//Req. Component that user is editing or adding
    private ReqComponentTypeInfo selectedReqType;
    private ReqComponentTypeInfo originalReqType;
    private boolean addingNewReqComp;                           //adding (true) or editing (false) req. component

    public ReqCompEditWidget() {
        super();
        this.setStyleName("KS-Program-Rule-ReqComp-box");
        setupReqCompTypesList();
        setupHandlers();
        setupNewReqComp();
        confirmButton.setEnabled(false);   //wait until req. comp. types are loaded
    }

    private void setupHandlers() {

        /*
        cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //if rule has not been created, cancel should return user back to initial rules summary screen
                setupNewReqComp(); - my new method

                //TODO need the code below?
                if (editedStatementVO.getReqComponentVOs().size() == 0) {
                    ((CourseReqManager)getController()).removeRule(model.getValue());
                    getController().showView(PrereqViews.RULES_LIST, Controller.NO_OP_CALLBACK);
                } else {
                    if (!addingNewReqComp) {
                        //revert changes to the existing Req. Component
                        editedReqComp.setRequiredComponentType(originalReqType);
                        editedReqComp.setType(originalReqType.getId());
                    }

                    //updateNLAndExit();
                    getController().showView(PrereqViews.MANAGE_RULES, Controller.NO_OP_CALLBACK);
                }
            }
        });   */

        confirmButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {

                /*
            	if(getRuleTypeName().equals("Prerequisite") && (selectedReqType != null) && isReqCompFieldType(ReqComponentFieldTypeKeys.CLUSET_KEY.getKey())){
            		saveCluSet();
            	} else {
	            	//1. check that all fields have values
	                if (retrieveReqCompFields() == false) {
	                    return;
	                }

	            	//2. update req. component being edited
	                editedReqComp.setReqCompFields(editedFields);
	                editedReqComp.setType(selectedReqType.getId());

	                //3. create new req. component and possibly new statement if none exists yet
	                if (addingNewReqComp) {
	                    editedStatementVO.addReqComponentVO(editedReqCompVO);
	                }

	                confirmButton.setEnabled(false);
	                updateNLAndExit();
	            } */
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
                     setupNewEditedReqComp(selectedReqType);
                 } else {
                	 editedReqComp.setRequiredComponentType(selectedReqType);
                     editedReqComp.setType(selectedReqType.getId());
                 }
                 displayReqComponentDetails();
         }});

    }

    //call if user wants to add a new req. component
    public void setupNewReqComp() {
        addingNewReqComp = true;
        originalReqType = null;
        setupNewEditedReqComp(null);
        selectedReqType = null;
        draw();
    }

    //call when user wants to edit an existing req. component
    public void setupReqComp(ReqComponentInfo existingReqComp) {
        if (confirmButton.isEnabled() == false) {
            return;
        }

        addingNewReqComp = false;
        editedReqComp = existingReqComp;
        originalReqType = editedReqComp.getRequiredComponentType();
        for (int i = 0; i < reqCompTypeInfoList.size(); i++) {
            if (editedReqComp.getType().equals(reqCompTypeInfoList.get(i).getId())) {
                selectedReqType = reqCompTypeInfoList.get(i);
                break;
            }
        }
    }

    private void draw() {

        //1. show a list of available requirement component types
        displayReqCompList();

        //2. display selected req. comp. details
        displayReqComponentDetails();

        //3. add 'Add Rule' button
        confirmButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {

            }
        });
        confirmButton.addStyleName("KS-Program-Rule-ReqComp-btn");
        add(confirmButton);
        
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

    private void displayReqCompList() {

        SectionTitle subHeader = SectionTitle.generateH5Title("Select rule type");
        subHeader.setStyleName("KS-Program-Rule-ReqComp-header");
        add(subHeader);

        KSLabel instructions = new KSLabel("Use the list below to select the type of rule you would like to add to this requirement");
        add(instructions);

        if (selectedReqType != null) {
        	int i = 0;
            for (ReqComponentTypeInfo comp : reqCompTypeInfoList) {
                if (comp.getId().equals(selectedReqType.getId())) {
                    reqCompTypesList.selectItem(Integer.toString(i));
                    break;
                }
                i++;
            }
        }
    }

    private void displayReqComponentDetails() {
        //TODO
    }

    public void setReqCompList(List<ReqComponentTypeInfo> reqComponentTypeInfoList) {

        if (reqComponentTypeInfoList == null || reqComponentTypeInfoList.size() == 0) {
            GWT.log("Missing Requirement Component Types", null);
            Window.alert("Missing Requirement Component Types");
            return;
        }

        //store all requirement components locally
        reqCompTypeInfoList = reqComponentTypeInfoList; /*new ArrayList<ReqComponentTypeInfo>();
        for (ReqComponentTypeInfo reqCompInfo : reqComponentTypeInfoList) {
            reqCompTypeList.add(reqCompInfo);
        } */


        reqCompTypesList.setListItems(listItemReqCompTypes);
        if (reqCompTypesList.getSelectedItem() != null) {
            reqCompTypesList.deSelectItem(reqCompTypesList.getSelectedItem());
        }

        confirmButton.setEnabled(true);
    }

    /* create a new Req. Component Type based on user selection or empty at first */
    private void setupNewEditedReqComp(ReqComponentTypeInfo reqCompTypeInfo) {
        /* TODO
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("");
        desc.setFormatted("");
        editedReqComp = new ReqComponentInfo();
        editedReqComp.setDesc(desc);      						 //will be set after user is finished with all changes
        editedReqComp.setId(Integer.toString(tempCounterID++));  //TODO
        editedReqComp.setReqCompFields(null);
        editedReqComp.setRequiredComponentType(reqCompTypeInfo);
        if (reqCompTypeInfo != null) editedReqComp.setType(reqCompTypeInfo.getId());
        editedReqCompVO = new ReqComponentVO(editedReqComp); */
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
}
