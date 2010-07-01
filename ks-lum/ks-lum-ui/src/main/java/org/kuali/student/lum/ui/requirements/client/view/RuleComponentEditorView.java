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

package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.SwapSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldTypeInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.naturallanguage.util.ReqComponentFieldTypes;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluSetHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetsConfigurer;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ToolsConstants;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetsConfigurer.CluSetEditOptionList;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetsConfigurer.Picker;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcService;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcServiceAsync;
import org.kuali.student.lum.lu.ui.tools.client.widgets.CluSetRangeDataHelper;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluSetRangeModelUtil;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RuleComponentEditorView extends ViewComposite {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);
    
    public enum ReqCompFieldDefinitions { TODO }

    //view's widgets
    private static final int NOF_BASIC_RULE_TYPES = 3;
    public static final String TEMLATE_LANGUAGE = "en";
    private static final String EXAMPLE_TEMLATE = "KUALI.EXAMPLE";
    private static final String COMPOSITION_TEMLATE = "KUALI.COMPOSITION";
    private static final String SIMPLE_RULE_RB_GROUP = "RuleTypesGroup";
    private static final String RULE_TYPES_OTHER = "Other:";
    private List<KSRadioButton> rbRuleType = new ArrayList<KSRadioButton>();

    private VerticalPanel mainPanel = new VerticalPanel();
    private VerticalPanel addEditRuleView = new VerticalPanel();
    private HorizontalPanel ruleDetailsPanel = new HorizontalPanel();
    private ConfirmCancelGroup confirmCancelButtons = new ConfirmCancelGroup();
    private KSButton confirmButton;
    private KSButton cancelButton;
    private KSDropDown compReqTypesList = new KSDropDown();
    private SimplePanel reqCompDesc = new SimplePanel();
    private KSLabel exampleText = new KSLabel();

    //rules data
    private StatementVO editedStatementVO;						//rule being edited by the user
    private ReqComponentVO editedReqCompVO;   					//Req. Component of the rule being edited by the user (plus extra UI data)
    private ReqComponentInfo editedReqComp;						//Req. Component that user is editing or adding
    private ReqComponentTypeInfo selectedReqType;
    private ReqComponentTypeInfo originalReqType;
    private CollectionModel<RuleInfo> model;

    //view's data
    private boolean addingNewReqComp;                           //adding (true) or editing (false) req. component
    private List<ReqCompFieldInfo> editedFields;
    private List<ReqComponentTypeInfo> reqCompTypeList;     	//list of all Requirement Component Types based on selected Statement Type
    private ListItems listItemReqCompTypes;                 	//list of advanced Requirement Component Types
    private List<ReqComponentTypeInfo> advReqCompTypeList;     	//list of advanced Requirement Component Types
    private List<Object> reqCompWidgets = new ArrayList<Object>();
    private static int tempCounterID = 2000;
    private List<ReqCompPicker> valueWidgets = new ArrayList<ReqCompPicker>();
    private List<Metadata> fieldsWithLookup = new ArrayList<Metadata>();  //contains definition of lookups

    //cluset data
    private DataModel clusetModel = new DataModel();
    private FieldDescriptor fdApprovedClus = null;
    private FieldDescriptor fdProposedClus = null;
    private FieldDescriptor fdClusets = null;
    private SwapSection clusetDetails = null;
    
    public RuleComponentEditorView(Controller controller) {
        super(controller, "Clause Editor View");
        super.initWidget(mainPanel);
        confirmButton = confirmCancelButtons.getButton(ConfirmCancelEnum.CONFIRM);
        cancelButton = confirmCancelButtons.getButton(ConfirmCancelEnum.CANCEL);        
        setupHandlers();
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        setupReqCompTypesList();
        confirmButton.setEnabled(true);

        getController().requestModel(RuleInfo.class, new ModelRequestCallback<CollectionModel<RuleInfo>>() {
            public void onModelReady(CollectionModel<RuleInfo> theModel) {
                model = theModel;
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });

        getController().requestModel(CourseConfigurer.CLU_PROPOSAL_MODEL, new ModelRequestCallback<DataModel>(){
	           @Override
	            public void onModelReady(DataModel model) {
	        	    clusetModel = model;
            }       			
	           
	            @Override
	            public void onRequestFail(Throwable cause) {
	                throw new RuntimeException("Unable to connect to model", cause);
	            }       	
       });
        
        requirementsRpcServiceAsync.getReqComponentTypesForLuStatementType(getSelectedStatementType(), new AsyncCallback<List<ReqComponentTypeInfo>>() {
            public void onFailure(Throwable cause) {
            	GWT.log("Failed to get req. component types for statement of type:" + getSelectedStatementType(), cause);
            	Window.alert("Failed to get req. component types for statement of type:" + getSelectedStatementType());
            }

            public void onSuccess(final List<ReqComponentTypeInfo> reqComponentTypeInfoList) {
            	reqCompTypeList = new ArrayList<ReqComponentTypeInfo>();
                for (ReqComponentTypeInfo reqCompInfo : reqComponentTypeInfoList) {
                	reqCompTypeList.add(reqCompInfo);
                }

                advReqCompTypeList = new ArrayList<ReqComponentTypeInfo>();
                if (reqCompTypeList.size() > NOF_BASIC_RULE_TYPES) {
                    for(int i = NOF_BASIC_RULE_TYPES; i < reqCompTypeList.size(); i++){
                        advReqCompTypeList.add(reqCompTypeList.get(i));
                    }
                }

                compReqTypesList.setListItems(listItemReqCompTypes);
                if (compReqTypesList.getSelectedItem() != null) {
                    compReqTypesList.deSelectItem(compReqTypesList.getSelectedItem());
                }

                //true if we are editing an existing rule
                 if (editedReqCompVO != null) {
                    addingNewReqComp = false;
                    editedReqComp = editedReqCompVO.getReqComponentInfo();
                    originalReqType = editedReqComp.getRequiredComponentType();
                    for (int i = 0; i < reqCompTypeList.size(); i++) {
                        if (editedReqComp.getType().equals(reqCompTypeList.get(i).getId())) {
                            selectedReqType = reqCompTypeList.get(i);
                            break;
                        }
                    }
                } else {
                    //create a basic structure for a new rule
                	addingNewReqComp = true;
                	originalReqType = null;
                    setupNewEditedReqComp(null);
                    selectedReqType = null;
                }

                redraw();
                onReadyCallback.exec(true);
            }
        });
    }

    public void redraw() {

        VerticalPanel bodyPanel = new VerticalPanel();
        
        //1. show editor HEADING
        KSLabel heading = new KSLabel((addingNewReqComp ? "Add " : "Edit ") + getRuleTypeName() + " Rule");
        heading.setStyleName("KS-ReqMgr-Heading");
        bodyPanel.add(heading);

        //2. show RULE TYPES
        KSLabel reqTypeLabel = new KSLabel("Step 1: Select rule type");
        reqTypeLabel.setStyleName("KS-RuleEditor-SubHeading");
        bodyPanel.add(reqTypeLabel);

        addEditRuleView.clear();
        addEditRuleView.setStyleName("KS-Rules-FullWidth");

        //show list of rule types
        displayReqComponentTypes(addEditRuleView);

        SimplePanel verticalSpacer2 = new SimplePanel();
        verticalSpacer2.setHeight("30px");
        addEditRuleView.add(verticalSpacer2);

        //3. show SELECTED RULE TYPE DETAILS
        displayReqComponentDetails();
        addEditRuleView.add(ruleDetailsPanel);

        //buttons at the bottom
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
        addEditRuleView.add(confirmCancelButtons);
        bodyPanel.add(addEditRuleView);

        mainPanel.clear();
        mainPanel.setStyleName("Content-Margin");
        mainPanel.add(bodyPanel);

        //updateExampleContext(); TODO - download all necessary contexts?
    }

    //show basic and advanced requirement types
    private void displayReqComponentTypes(Panel container) {

        //TODO list basic and advanced types based on some configuration data

        //show radio button for each basic Requirement Component Type
        VerticalPanel rbPanel = new VerticalPanel();
        int nofBasicRuleTypes = (reqCompTypeList.size() > NOF_BASIC_RULE_TYPES ? NOF_BASIC_RULE_TYPES : reqCompTypeList.size());
        for (int i = 0; i < nofBasicRuleTypes; i++) {
            final KSRadioButton newButton = new KSRadioButton(SIMPLE_RULE_RB_GROUP, reqCompTypeList.get(i).getDescr());
            rbRuleType.add(newButton);
            newButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    confirmButton.setEnabled(true);
                    resetClusetModel();
                	if (compReqTypesList.getSelectedItem() != null) {
                        compReqTypesList.deSelectItem(compReqTypesList.getSelectedItem());
                    }
                    for (int i = 0; i < NOF_BASIC_RULE_TYPES; i++) {
                        if (newButton.getText().trim().equals(reqCompTypeList.get(i).getDescr().trim())) {
                            selectedReqType = reqCompTypeList.get(i);
                            if (addingNewReqComp) {
                                setupNewEditedReqComp(selectedReqType);
                            } else {
                                editedReqComp.setRequiredComponentType(selectedReqType);
                                editedReqComp.setType(selectedReqType.getId());
                            }
                            displayReqComponentDetails();
                            return;
                        }
                    }
                }
            });
            if ((selectedReqType != null) && reqCompTypeList.get(i).getId().equals(selectedReqType.getId())) {
                newButton.setValue(true);
            }
            rbPanel.add(newButton);
        }

        //now add a drop down for advanced Requirement Component Types
        HorizontalPanel hodler = new HorizontalPanel();
        hodler.setSpacing(5);
        KSRadioButton newButton = new KSRadioButton(SIMPLE_RULE_RB_GROUP, RULE_TYPES_OTHER);
        rbRuleType.add(newButton);
        hodler.add(newButton);
        compReqTypesList.setStyleName("KS-Radio-Dropdown");

        if (selectedReqType != null) {
        	int i = 0;
            for (ReqComponentTypeInfo comp : advReqCompTypeList) {
                if (comp.getId().equals(selectedReqType.getId())) {
                    compReqTypesList.selectItem(Integer.toString(i)); 
                    newButton.setValue(true);
                    break;
                }
                i++;
            }
        }
        hodler.add(compReqTypesList);

        //don't create advanced req. component types list if we don't have any
        if (advReqCompTypeList.size() > 0) {
            rbPanel.add(hodler);
        }

        container.add(rbPanel);
    }

    //TODO generic function that will retrieve all data required to display details of this req. componenet type...
    private void displayReqComponentDetails() {
        //true if no Requirement Component Type selected
        ruleDetailsPanel.clear();
        if (selectedReqType == null) {
            return;
        }
        displayReqComponentDetailsCont();
    }

    private void displayReqComponentDetailsCont() {
        //show heading
        VerticalPanel reqCompDetailsExampleContainerPanel = new VerticalPanel();
        KSLabel reqCompTypeName = new KSLabel("Step 2: Enter rule details"); // + selectedReqType.getDescr());
        reqCompTypeName.setStyleName("KS-ReqMgr-SubHeading");
        reqCompDetailsExampleContainerPanel.add(reqCompTypeName);

        //show details
        HorizontalPanel reqCompDetailsExamplePanel = new HorizontalPanel();
        final SimplePanel reqCompDetailsPanel = new SimplePanel();
        reqCompDetailsPanel.setStyleName("KS-Rules-ReqCompEdit-Width");

        requirementsRpcServiceAsync.getNaturalLanguageForReqComponentInfo(editedReqComp, COMPOSITION_TEMLATE, TEMLATE_LANGUAGE,
                new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                GWT.log("getNaturalLanguageForReqComponentInfo failed",caught);
            }

            public void onSuccess(final String reqCompNaturalLanguage) {
           //     editedReqCompVO.setTypeDesc(reqCompNaturalLanguage);
           //     editedReqCompVO.setCheckBoxOn(true);
           //     editedStatementVO.clearSelections();
                ((CourseReqManager)getController()).saveEditHistory(editedStatementVO);
                 displayReqComponentText(reqCompNaturalLanguage, reqCompDesc, (editedReqComp == null ? null : editedReqComp.getReqCompFields()));
            	 reqCompDetailsPanel.add(reqCompDesc);           
            }
        });

        reqCompDetailsExamplePanel.add(reqCompDetailsPanel);

        //show example
        final VerticalPanel examplePanel = new VerticalPanel();
        examplePanel.setSpacing(0);
        KSLabel exampleText1 = new KSLabel("Example:");
        exampleText1.setStyleName("KS-RuleEditor-ExampleText1");
        examplePanel.add(exampleText1);
        requirementsRpcServiceAsync.getNaturalLanguageForReqComponentInfo(editedReqComp, EXAMPLE_TEMLATE, TEMLATE_LANGUAGE,
                new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                GWT.log("getNaturalLanguageForReqComponentInfo failed", caught);
            }

            public void onSuccess(final String reqCompNaturalLanguage) {
                exampleText.setText(reqCompNaturalLanguage);
                exampleText.setStyleName("KS-RuleEditor-ExampleText2");
                examplePanel.add(exampleText);
            }
        });
        reqCompDetailsExamplePanel.add(examplePanel);

        reqCompDetailsExampleContainerPanel.add(reqCompDetailsExamplePanel);

        SimplePanel verticalSpacer2 = new SimplePanel();
        verticalSpacer2.setHeight("30px");
        reqCompDetailsExampleContainerPanel.add(verticalSpacer2);

        /* we might want to show catalog template if the rule is updated but not when it is being edited
        String catalogTemplate = getTemplate(CATALOG_TEMLATE);
        reqCompDetailsExampleContainerPanel.add(new KSLabel(catalogTemplate)); */

        ruleDetailsPanel.add(reqCompDetailsExampleContainerPanel);
    }

    private void setupHandlers() {

        cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //if rule has not been created, cancel should return user back to initial rules summary screen
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
        });

        confirmButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
            	
            	if(getRuleTypeName().equals("Prerequisite") && (selectedReqType != null) && isReqCompFieldType(ReqComponentFieldTypes.CLUSET_KEY.getKey())){
            		saveCluSet();
            	}else{
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
	            }
            }
        });

        compReqTypesList.addSelectionChangeHandler(new SelectionChangeHandler() {
			@Override
            public void onSelectionChange(SelectionChangeEvent event) {
			    
			    if (!event.isUserInitiated()) {
			        return;
			    }
			    
                 confirmButton.setEnabled(true);
                 for (KSRadioButton button : rbRuleType) {
                     button.setValue(button.getText().equals(RULE_TYPES_OTHER) ? true : false);
                 }

                 List<String> ids = ((KSSelectItemWidgetAbstract)event.getWidget()).getSelectedItems();
                 selectedReqType = advReqCompTypeList.get(Integer.valueOf(ids.get(0)));
                 if (addingNewReqComp) {
                     setupNewEditedReqComp(selectedReqType);
                 } else {
                	 editedReqComp.setRequiredComponentType(selectedReqType);
                     editedReqComp.setType(selectedReqType.getId());
                 }
                 displayReqComponentDetails();
         }});

    }

    /* check that all fields have values and confirm basic format of each value
     * TODO: this needs to be more generic based on validation of each field from dictionary
     */
    private boolean retrieveReqCompFields() {
        StringBuffer enteredCluCodes = new StringBuffer();
    	editedFields = new ArrayList<ReqCompFieldInfo>();
    	
    	for (Object reqCompWidget : reqCompWidgets) {
        	String name = "";
        	String value = "";
        	String fieldLabel = ""; //FIXME: we need to have universal basic widget that contains its label
        	Boolean valid = false;
        	if (reqCompWidget.getClass().getName().contains("KSTextBox")) {
        		name = ((KSTextBox)reqCompWidget).getName();
        		value = ((KSTextBox)reqCompWidget).getText();
        		valid = !value.trim().isEmpty();  //FIXME: missing proper validation
        	} else if (reqCompWidget.getClass().getName().contains("ReqCompPicker")) {
        		name = ((ReqCompPicker)reqCompWidget).getName();
        		value = ((ReqCompPicker)reqCompWidget).getValue().get();
        		valid = ((ReqCompPicker)reqCompWidget).getValidEntry();
        		fieldLabel = ((ReqCompPicker)reqCompWidget).getFieldName();
        	}

            ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
            fieldInfo.setId(name);
            fieldInfo.setValue(value);

            if (!valid) {
                Window.alert("Please enter valid value in the field: " + (fieldLabel.isEmpty() ? getFieldName(fieldInfo) : fieldLabel));
            	editedFields.clear();
                return false;
            }

            if (fieldInfo.getId().equals(ReqComponentFieldTypes.CLU_KEY.getKey())) {
            	enteredCluCodes.append((enteredCluCodes.length() > 0 ? ", " : "") + fieldInfo.getValue());
            } else {
            	editedFields.add(fieldInfo);
            }
        }

    	if (enteredCluCodes.length() > 0) {
            ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
            fieldInfo.setId(ReqComponentFieldTypes.CLU_KEY.getKey());
            fieldInfo.setValue(enteredCluCodes.toString());
            editedFields.add(fieldInfo);
        }

        return true;
    }

    private boolean isReqCompFieldType(String fieldType) {
        List<ReqCompFieldTypeInfo> fieldTypes = editedReqComp.getRequiredComponentType().getReqCompFieldTypeInfos();
        return fieldTypes.contains(fieldType);
    }
    
    private String getFieldName(ReqCompFieldInfo fieldInfo) {

        if (fieldInfo.getId().equals(ReqComponentFieldTypes.CLU_KEY.getKey())) {
            return "Course";
        } else if (fieldInfo.getId().equals(ReqComponentFieldTypes.CLUSET_KEY.getKey())) {
            return "Courses";
        } else if (fieldInfo.getId().equals(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getKey())) {
            return "count";
        } else if (fieldInfo.getId().equals(ReqComponentFieldTypes.GPA_KEY.getKey())) {
            return "GPA";
        }

        return "";
    }
    
    private void saveCluSet(){
        try {
        	
        	//1. check other fields have values
        	  if (retrieveReqCompFields() == false) {
                  return;
              }

        	//2. create a new cluset and get it's Id
        	if(clusetDetails != null)
        		clusetDetails.updateModel(clusetModel);
        	
        	Data input = clusetModel.getRoot();
        	CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(input);
        	 if (root.getCourse() == null) {
        		 Window.alert("Please fill out Course Information.");
				 return;
        	 }
        	 
        	CreditCourseHelper course =CreditCourseHelper.wrap(root.getCourse().getData());  
			
			if(course.getCourseTitle() == null || course.getCourseTitle().trim().isEmpty()){
				 Window.alert("Course Title can't be empty.");
				 return;
			}
			
			final CluSetHelper cluSet = CluSetHelper.wrap((Data)clusetModel.get("cluset"));
			cluSet.setName(course.getCourseTitle());
			cluSet.setDescription(course.getId());
			cluSet.setEffectiveDate(course.getEffectiveDate());
			cluSet.setExpirationDate(course.getExpirationDate());
			cluSet.setReusable(new Boolean(false));
			cluSet.setReferenceable(new Boolean(false));

			cluSetManagementRpcServiceAsync.saveData(clusetModel.getRoot(), new AsyncCallback<DataSaveResult>() {
                @Override
                public void onFailure(Throwable caught) {
	                Window.alert(caught.getMessage());
	                GWT.log("Create Cluset failed",caught);
                }

                @Override
                public void onSuccess(DataSaveResult result) {
                	String clusetId = CluSetHelper.wrap((Data)result.getValue().get("cluset")).getId();

                	if (clusetId != null && !clusetId.trim().isEmpty()){
	                	 ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
	                     fieldInfo.setId(ReqComponentFieldTypes.CLUSET_KEY.getKey());
	                     fieldInfo.setValue(CluSetHelper.wrap((Data)result.getValue().get("cluset")).getId());
	                     editedFields.add(fieldInfo);
	                     
	                    editedReqComp.setReqCompFields(editedFields);
	 	                editedReqComp.setType(selectedReqType.getId());

	 	                if (addingNewReqComp) {
	 	                    editedStatementVO.addReqComponentVO(editedReqCompVO);
	 	                }
	 	                
	 	                confirmButton.setEnabled(false);
	 	                updateNLAndExit();
                	}
                	else{
                        Window.alert("ClusetId is invalid. ");
                    	editedFields.clear();               		
                	}
                }
            });
        } catch (Exception e) {
        	Window.alert(e.getMessage());
        }       
    }
    
    public AsyncCallback<DataSaveResult> getAsyncCallback() {
        
        final Callback<Throwable> saveFailedCallback = new Callback<Throwable>() {

			@Override
			public void exec(Throwable caught) {
				 GWT.log("Save Failed.", caught);
			}      	
        };
     
    	AsyncCallback<DataSaveResult> saveCluSetCallback = new AsyncCallback<DataSaveResult>() {
    		@Override
    		public void onFailure(Throwable caught) {
    			saveFailedCallback.exec(caught);    
    		}
    		
    		@Override
    		public void onSuccess(DataSaveResult result) {
    			final CluSetHelper cluSet = CluSetHelper.wrap((Data)clusetModel.get("cluset"));
    			cluSet.setId(CluSetHelper.wrap(result.getValue()).getId());
    		}
    	};
    	
    	return saveCluSetCallback;
    }
    
	private static LookupMetadata findLookupMetadata(String lookupId,
			List<LookupMetadata> lookupMetadatas) {
		LookupMetadata result = null;
		if (lookupMetadatas != null) {
			for (LookupMetadata lookupMetadata : lookupMetadatas) {
				if (nullSafeEquals(lookupMetadata.getSearchTypeId(), lookupId)) {
					result = lookupMetadata;
				}
			}
		}
		return result;
	}
	
	private static boolean nullSafeEquals(Object obj1, Object obj2) {
		return (obj1 == null && obj2 == null || obj1 != null && obj2 != null
				&& obj1.equals(obj2));
	}
	
	private Picker configureSearch(String fieldKey) {	    
		QueryPath path = QueryPath.concat(null, fieldKey);
		Metadata meta = clusetModel.getDefinition().getMetadata(path);
	        
		Picker picker = new Picker(meta.getInitialLookup(), meta.getAdditionalLookups());
		return picker;
	}
	
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
    	return addField(null, section, fieldKey, messageKey, null, null);
    }

	protected FieldDescriptor addField(Section section, String fieldKey,
			MessageKeyInfo messageKey, Widget widget) {
		return addField(null, section, fieldKey, messageKey, widget, null);
	}
	
    private FieldDescriptor addField(ModelIdPlaceHolder modelId, Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = clusetModel.getDefinition().getMetadata(path);

    	FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);
    	if (widget != null) {
    		fd.setFieldWidget(widget);
    	}
    	if (modelId != null) {
    		fd.setModelId(modelId.getModelId());
    	}
    	section.addField(fd);
    	return fd;
    }
    
    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo("clusetmanagement", "clusetmanagement", "draft", labelKey);
    }
    
    private static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection(title);
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return section;
    }
 
    private void resetClusetModel(){
    	if(clusetModel.get("cluset") != null){
	    	final CluSetHelper cluSet = CluSetHelper.wrap((Data)clusetModel.get("cluset"));
	    	
			cluSet.setApprovedClus(null);
	    	
			cluSet.setProposedClus(null);
	    	
			cluSet.setCluSets(null);
			cluSet.setCluRangeParams(null);
			cluSet.setCluRangeViewDetails(null);
    	}
    }
    
    private SwapSection displayCluSetsSection( final String modelId, CluSetEditOptionList cluSetEditOptions){
         SwapSection clusetDetails = new SwapSection(
                 cluSetEditOptions,
                 new ConfirmationDialog("Delete Clu Set Details",
                         "You are about to delete clu set details.  Continue?")
                 );
        
         // ****** Add Approved Clus *******
         Section approvedClusSection = new VerticalSection();
         fdApprovedClus= addField(approvedClusSection, ToolsConstants.CLU_SET_APPROVED_CLUS_FIELD, generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_APPROVED_COURSE));
         fdApprovedClus.setModelId(modelId);
         clusetDetails.addSection(approvedClusSection, ToolsConstants.CLU_SET_SWAP_APPROVED_CLUS);
         // END OF items related to Add Approved Clus
         
         // ****** Add Proposed Clus *******
         Section proposedClusSection = new VerticalSection();
         fdProposedClus = addField(proposedClusSection, ToolsConstants.CLU_SET_PROPOSED_CLUS_FIELD, generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_PROPOSED_COURSE));
         fdProposedClus.setModelId(modelId);
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
//                             ((ModelWidgetBinding)cluRangeFieldDescriptor.getModelWidgetBinding()).setWidgetValue(widget, model, path)
//                             CluSetHelper cluSetHelper = CluSetHelper.wrap(model.getRoot());
//                             cluSetHelper.setCluRangeParams(value)
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
                             lookupMetadata = findLookupMetadata(searchRequest.getSearchKey(), 
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
         fdClusets = addField(cluSetSection, ToolsConstants.CLU_SET_CLU_SETS_FIELD, generateMessageInfo(ToolsConstants.NEW_CLU_SET_CONTENT_CLUSET));
         fdClusets.setModelId(modelId);
         clusetDetails.addSection(cluSetSection, ToolsConstants.CLU_SET_SWAP_CLU_SETS);
         // END OF items related to Add CluSets
         
         //clusetSection.addWidget(cluSetEditOptions);
         //clusetSection.addSection(clusetDetails);
         //parentWidget.setWidget(clusetSection);      
         
        // return clusetSection;
         return clusetDetails;
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
    
    private void displayReqComponentText(String reqInfoDesc, SimplePanel parentWidget, final List<ReqCompFieldInfo> fields) {

        // resets the list of reqCompWidgets to make sure it is created fresh.
        reqCompWidgets.clear();
        parentWidget.clear();
        VerticalPanel innerReqComponentTextPanel = new VerticalPanel();
        innerReqComponentTextPanel.setStyleName("KS-Rules-FullWidth");
        final String[] tokens = reqInfoDesc.split("[<>]");
        boolean isValueWidget = true;
        Map<String, Integer> tagCounts = new HashMap<String, Integer>();

        valueWidgets.clear();
        for (int i = 0; i < tokens.length; i++) {

            isValueWidget = !isValueWidget;
            //this token is a text only
            if (isValueWidget == false) {
                // skip. just show the fields
                continue;
            }

            //TODO use ENUMs and Switch()
            final String[] fieldTokens = tokens[i].split(";");
            final Map<String, String> fieldProperties = new HashMap<String, String>();
            if (fieldTokens != null) {
                for (String fieldToken : fieldTokens) {
                    if (fieldToken == null) continue;
                    String[] keyValuePair = fieldToken.split("=");
                    if (keyValuePair != null && keyValuePair.length == 2) {
                        fieldProperties.put(keyValuePair[0], keyValuePair[1]);
                    }
                }
            }

            String tag = fieldProperties.get("reqCompFieldType");
            String fieldLabel = fieldProperties.get("reqCompFieldLabel");
            int tagCount = 0;
            if (!tagCounts.containsKey(tag)) {
                tagCount = 0;
            } else {
                tagCount = tagCounts.get(tag).intValue();
                tagCount++;
            }
            tagCounts.put(tag, tagCount);

            if (tag.equals(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getKey()) || 
            		tag.equals(ReqComponentFieldTypes.GPA_KEY.getKey())) {
                final KSTextBox valueWidget = new KSTextBox();
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tag);
                valueWidget.setText(getSpecificFieldValue(fields, tag));
                valueWidget.setWidth("50px");
                valueWidget.setStyleName("KS-Textbox-Fix");
                SimplePanel tempPanel = new SimplePanel();
                tempPanel.addStyleName("KS-Rules-FlexPanelFix");
                tempPanel.add(valueWidget);
                if (i > 1) {
                    SimplePanel verticalSpacer = new SimplePanel();
                    verticalSpacer.setHeight("30px");
                    innerReqComponentTextPanel.add(verticalSpacer);
                }
                if (fieldLabel != null && fieldLabel.trim().length() > 0 && tokens.length > 2) {
                    innerReqComponentTextPanel.add(new KSLabel(fieldLabel + ":"));
                }
                innerReqComponentTextPanel.add(tempPanel);
                continue;
            }

            if (tag.equals(ReqComponentFieldTypes.CLU_KEY.getKey())) {
            	final ReqCompPicker valueWidget = configureCourseSearch(fieldLabel);
                valueWidgets.add(valueWidget);
                String cluIdsInClause = getSpecificFieldValue(fields, tag);

                String[] cluIds = (cluIdsInClause == null)? null : cluIdsInClause.split("(, *)");
                //retrieve clu code to display for user
                if ((cluIds != null) && (tagCount < cluIds.length) && (cluIds[tagCount].length() > 0)) {
                	valueWidget.setValue(cluIds[tagCount]);
                }
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tag);
                valueWidget.setWidth("100px");
                valueWidget.setStyleName("KS-Textbox-Fix");
                VerticalPanel tempPanel = new VerticalPanel();
                tempPanel.addStyleName("KS-Rules-FlexPanelFix");
                tempPanel.add(valueWidget);

                if (i > 1) {
                    SimplePanel verticalSpacer = new SimplePanel();
                    verticalSpacer.setHeight("30px");
                    innerReqComponentTextPanel.add(verticalSpacer);
                }
                if (fieldLabel != null && fieldLabel.trim().length() > 0 && tokens.length > 2) {
                    innerReqComponentTextPanel.add(new KSLabel(fieldLabel + ":"));
                }
                innerReqComponentTextPanel.add(tempPanel);

                continue;
            }

            if (tag.equals(ReqComponentFieldTypes.CLUSET_KEY.getKey())) {

                CluSetsConfigurer clusetConfig = new CluSetsConfigurer();
                CluSetEditOptionList cluSetEditOptions = clusetConfig.new CluSetEditOptionList();
                innerReqComponentTextPanel.add(cluSetEditOptions);
                
                //         	       final SwapSection clusetDetails = displayCluSetsSection(CourseConfigurer.CLU_PROPOSAL_MODEL, cluSetEditOptions);   
                clusetDetails = displayCluSetsSection(CourseConfigurer.CLU_PROPOSAL_MODEL, cluSetEditOptions);
                   
                selectCluSetOptionList(clusetModel, cluSetEditOptions);
                clusetDetails.updateWidgetData(clusetModel);
                   
                if (i > 1) {
                    SimplePanel verticalSpacer = new SimplePanel();
                    verticalSpacer.setHeight("30px");
                    innerReqComponentTextPanel.add(verticalSpacer);
                }
   
                innerReqComponentTextPanel.add(clusetDetails);
                continue;                
         	}
            	
            //need a better way to determine what pickers to use for given req. component type field
            if ((editedReqComp.getRequiredComponentType().getId().equals("kuali.reqCompType.programList.enroll.oneof")) ||
                        (editedReqComp.getRequiredComponentType().getId().equals("kuali.reqCompType.programList.enroll.none")))
            {
                final ReqCompPicker valueWidget;
                valueWidget = configureProgramCluSetSearch(fieldLabel);
                 
                valueWidgets.add(valueWidget);
                String cluSetIdsInClause = getSpecificFieldValue(fields, tag);

                String[] cluIds = (cluSetIdsInClause == null)? null : cluSetIdsInClause.split("(, *)");
                //retrieve clu code to display for user
                if ((cluIds != null) && (tagCount < cluIds.length) && (cluIds[tagCount].length() > 0)) {
                    valueWidget.setValue(cluIds[tagCount]);
                }
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tag);
                valueWidget.setWidth("100px");
                valueWidget.setStyleName("KS-Textbox-Fix");
                VerticalPanel tempPanel = new VerticalPanel();
                tempPanel.addStyleName("KS-Rules-FlexPanelFix");
                tempPanel.add(valueWidget);

                if (i > 1) {
                    SimplePanel verticalSpacer = new SimplePanel();
                    verticalSpacer.setHeight("30px");
                    innerReqComponentTextPanel.add(verticalSpacer);
                }
                if (fieldLabel != null && fieldLabel.trim().length() > 0 && tokens.length > 2) {
                    innerReqComponentTextPanel.add(new KSLabel(fieldLabel + ":"));
                }
                innerReqComponentTextPanel.add(tempPanel);
  
                continue;
            }
            
        }
        parentWidget.setWidget(innerReqComponentTextPanel);
    }

    private ReqCompFieldInfo getReqCompFieldInfo(List<ReqCompFieldInfo> fields, String key) {
        ReqCompFieldInfo result = null;
        if (fields == null) {
            return null;
        }
        for (ReqCompFieldInfo fieldInfo : fields) {
            if (fieldInfo.getId().equals(key)) {
                result = fieldInfo;
            }
        }
        return result;
    }

    private String getSpecificFieldValue(List<ReqCompFieldInfo> fields, String key) {
        ReqCompFieldInfo reqCompFieldInfo = getReqCompFieldInfo(fields, key);
        String fieldValue = null;

        //if we are showing new req. comp. type then show empty fields
        if (reqCompFieldInfo == null) {
            return "";
        }

        fieldValue = reqCompFieldInfo.getValue();
        fieldValue = (fieldValue == null)? "" : fieldValue;
        return fieldValue;
    }

    /* create a new Req. Component Type based on user selection or empty at first */
    private void setupNewEditedReqComp(ReqComponentTypeInfo reqCompTypeInfo) {
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("");
        desc.setFormatted("");
        editedReqComp = new ReqComponentInfo();
        editedReqComp.setDesc(desc);      						 //will be set after user is finished with all changes
        editedReqComp.setId(Integer.toString(tempCounterID++));  //TODO
        editedReqComp.setReqCompFields(null);
        editedReqComp.setRequiredComponentType(reqCompTypeInfo);
        if (reqCompTypeInfo != null) editedReqComp.setType(reqCompTypeInfo.getId());
        editedReqCompVO = new ReqComponentVO(editedReqComp);
    }

    private void updateNLAndExit() {
    	if (editedReqComp.getType() != null && !editedReqComp.getType().isEmpty()) {
	    	requirementsRpcServiceAsync.getNaturalLanguageForReqComponentInfo(editedReqComp, "KUALI.CATALOG", TEMLATE_LANGUAGE, new AsyncCallback<String>() {
	            public void onFailure(Throwable caught) {
	                Window.alert(caught.getMessage());
	                GWT.log("getNaturalLanguageForReqComponentInfo failed",caught);
	            }

	            public void onSuccess(final String reqCompNaturalLanguage) {
	                editedReqCompVO.setTypeDesc(reqCompNaturalLanguage);
	                editedReqCompVO.setCheckBoxOn(true);
	                editedStatementVO.clearSelections();
	                model.getValue().setStatementVO(editedStatementVO);
	                ((CourseReqManager)getController()).saveEditHistory(editedStatementVO);
	                getController().showView(PrereqViews.MANAGE_RULES, Controller.NO_OP_CALLBACK);
	            }
	        });
    	}
    }

    private String getRuleTypeName() {
    	String luStatementTypeKey = getSelectedStatementType();
        if (luStatementTypeKey.contains("enroll")) return "Enrollment Restriction";
        if (luStatementTypeKey.contains("prereq")) return "Prerequisite";
        if (luStatementTypeKey.contains("coreq")) return "Corequisite";
        if (luStatementTypeKey.contains("antireq")) return "Antirequisite";
        return "";
    }

    private String getSelectedStatementType() {
        return ((CourseReqManager) getController()).getSelectedLuStatementType();
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
	                value = advReqCompTypeList.get(index).getDescr();
	            } catch (Exception e) {
	            }

	            return value;
	        }

	        @Override
	        public int getItemCount() {
	            return advReqCompTypeList.size();
	        }

	        @Override
	        public List<String> getItemIds() {
	            List<String> ids = new ArrayList<String>();
	            for(int i = 0; i < advReqCompTypeList.size(); i++){
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

    public static class ReqCompPicker extends KSPicker {

        private String name;
        private Boolean validEntry = true;     //only when user enters existing clu, cluset etc. this is true
        private String fieldName;

        public ReqCompPicker(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata, String fieldName) {
            super(inLookupMetadata, additionalLookupMetadata);

            this.fieldName = fieldName;

            this.addBasicSelectionTextChangeCallback(new Callback<String>() {
                @Override
                public void exec(String result) {
                    //validEntry = false;
                }
            });

            this.addBasicSelectionCompletedCallback(new Callback<SelectedResults>() {
                @Override
                public void exec(SelectedResults result) {
                    validEntry = (result.getReturnKey().isEmpty() ? false : true);
                }
            });
        }

        public String getName() {
            return name;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getValidEntry() {
            return validEntry;
        }
    }

    private ReqCompPicker configureCourseSearch(String tag) {
    	for (Metadata fieldMetadata : fieldsWithLookup) {
    		if (fieldMetadata.getName().equals("findCourse")) {
    			return new ReqCompPicker(fieldMetadata.getInitialLookup(), fieldMetadata.getAdditionalLookups(), tag);
    		}
    	}
    	return null;
    }

    private ReqCompPicker configureProgramCluSetSearch(String tag) {
        for (Metadata fieldMetadata : fieldsWithLookup) {
            if (fieldMetadata.getName().equals("findProgram")) {
                return new ReqCompPicker(fieldMetadata.getInitialLookup(), fieldMetadata.getAdditionalLookups(), tag);
            }
        }
        return null;
    }

    private ReqCompPicker configureCourseCluSetSearch(String tag) {
        for (Metadata fieldMetadata : fieldsWithLookup) {
            if (fieldMetadata.getName().equals("findCluSet")) {
                return new ReqCompPicker(fieldMetadata.getInitialLookup(), fieldMetadata.getAdditionalLookups(), tag);
            }
        }
        return null;
    }

	public void setEditedStatementVO(StatementVO editedStatementVO) {
		this.editedStatementVO = editedStatementVO;
	}

	public void setEditedReqCompVO(ReqComponentVO editedReqCompVO) {
		this.editedReqCompVO = editedReqCompVO;
	}

	public void setFieldsWithLookup(List<Metadata> fieldsWithLookup) {
		this.fieldsWithLookup = fieldsWithLookup;
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
}
