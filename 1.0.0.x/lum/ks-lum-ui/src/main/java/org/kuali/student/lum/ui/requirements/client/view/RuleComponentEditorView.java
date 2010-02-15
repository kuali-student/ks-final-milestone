/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.selectors.KSSearchComponent;
import org.kuali.student.common.ui.client.widgets.selectors.SearchComponentConfiguration;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeNLTemplateInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleComponentEditorView extends ViewComposite {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    //private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);

    public enum reqCompFieldDefinitions { TODO }

    //view's widgets
    private static final int NOF_BASIC_RULE_TYPES = 3;
    private static final String TEMLATE_LANGUAGE = "en";
    private static final String CATALOG_TEMLATE = "KUALI.CATALOG";
    private static final String EXAMPLE_TEMLATE = "KUALI.EXAMPLE";
    private static final String COMPOSITION_TEMLATE = "KUALI.COMPOSITION";
    private static final String SIMPLE_RULE_RB_GROUP = "RuleTypesGroup";
    private static final String RULE_TYPES_OTHER = "Other:";
    private List<KSRadioButton> rbRuleType = new ArrayList<KSRadioButton>();
    private FocusHandler ruleTypeSelectionHandler = null;

    private VerticalPanel mainPanel = new VerticalPanel();
    private VerticalPanel addEditRuleView = new VerticalPanel();
    private HorizontalPanel ruleDetailsPanel = new HorizontalPanel();
    private KSButton btnCancelView = new KSButton("Cancel");
    private KSButton addReqComp = new KSButton("Add Rule");
    private KSButton updateReqComp = new KSButton("Update Rule");
    private KSDropDown compReqTypesList = new KSDropDown();
    private SimplePanel reqCompDesc = new SimplePanel();
    private KSLabel exampleText = new KSLabel();

    //view's data
    private boolean addNewReqComp;
    private CollectionModel<RuleInfo> modelRuleInfo;
    private ReqComponentTypeInfo selectedReqType;
    private ReqComponentInfo editedReqComp;
    private List<ReqCompFieldInfo> editedFields;
    private String origReqCompType;
    private ReqComponentVO editedReqCompVO;
    private List<ReqComponentTypeInfo> reqCompTypeList;     //list of all Requirement Component Types
    private ListItems listItemReqCompTypes;                 //list of advanced Requirement Component Types
    private List<ReqComponentTypeInfo> advReqCompTypeList;     //list of advanced Requirement Component Types
    private List<Object> reqCompWidgets = new ArrayList<Object>();
    private Map<String, String> cluSetsData = new HashMap<String, String>();
    private static int tempCounterID = 2000;
    private List<TmpCoursePicker> valueWidgets = new ArrayList<TmpCoursePicker>();    

    public RuleComponentEditorView(Controller controller) {
        super(controller, "Clause Editor View");
        super.initWidget(mainPanel);
        setupHandlers();
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        setReqComponentListAndReqComp();
        // TODO should probably pass the callback into the method above and invoke it when the work is actually done
        onReadyCallback.exec(true);
    }

    public void redraw() {

        //1. show view HEADING
        SimplePanel headingPanel = new SimplePanel();
        KSLabel heading = new KSLabel((addNewReqComp ? "Add " : "Edit ") + getRuleTypeName() + " Rule");
        heading.setStyleName("KS-Rules-FullWidth");
        heading.setStyleName("KS-ReqMgr-Heading");
        headingPanel.add(heading);

        //2. show RULE TYPES
        HorizontalPanel ruleTypesPanel = new HorizontalPanel();

        //show requirement component label
        SimplePanel labelPanel = new SimplePanel();
        KSLabel reqTypeLabel = new KSLabel("Rule");
        reqTypeLabel.setStyleName("KS-RuleEditor-SubHeading");
        labelPanel.add(reqTypeLabel);
        ruleTypesPanel.add(labelPanel);

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
        SimplePanel horizSpacer = new SimplePanel();
        horizSpacer.setWidth("30px");
        HorizontalPanel tempPanelButtons = new HorizontalPanel();
        tempPanelButtons.setSpacing(0);
        tempPanelButtons.setStyleName("KS-ReqCompEditor-BottomButtons");
        btnCancelView.setStyleName("KS-Rules-Tight-Button");
        if (addNewReqComp) {
            tempPanelButtons.add(addReqComp);
            addReqComp.setStyleName("KS-Rules-Tight-Button");
            if (selectedReqType == null) {
                addReqComp.setEnabled(false);
            }
        } else {
            tempPanelButtons.add(updateReqComp);
            updateReqComp.setStyleName("KS-Rules-Tight-Button");
            if (selectedReqType == null) {
                updateReqComp.setEnabled(false);
            }
        }
        tempPanelButtons.add(horizSpacer);
        tempPanelButtons.add(btnCancelView);
        addEditRuleView.add(tempPanelButtons);
        
        HorizontalPanel bodyPanel = new HorizontalPanel(); 
        bodyPanel.add(ruleTypesPanel);
        bodyPanel.add(addEditRuleView);

        mainPanel.clear();
        mainPanel.setStyleName("Content-Margin");
        mainPanel.add(headingPanel);
        mainPanel.add(bodyPanel);

        //updateExampleContext(); TODO - download all necessary contexts?
    }

    //show basic and advanced requirement types
    private void displayReqComponentTypes(Panel container) {

        //TODO list of basic and advanced types based on some configuration data

        //show radio button for each basic Requirement Component Type
        VerticalPanel rbPanel = new VerticalPanel();
        int nofBasicRuleTypes = (reqCompTypeList.size() > NOF_BASIC_RULE_TYPES ? NOF_BASIC_RULE_TYPES : reqCompTypeList.size());
        for (int i = 0; i < nofBasicRuleTypes; i++) {
            KSRadioButton newButton = new KSRadioButton(SIMPLE_RULE_RB_GROUP, reqCompTypeList.get(i).getDesc());
            rbRuleType.add(newButton);
            newButton.addFocusHandler(ruleTypeSelectionHandler);
            if ((selectedReqType != null) && reqCompTypeList.get(i).getId().equals(selectedReqType.getId())) {
                newButton.setValue(true);
            }
            rbPanel.add(newButton);
        }

        //now add a drop down for advanced Requirement Component Types
        HorizontalPanel hodler = new HorizontalPanel();
        KSRadioButton newButton = new KSRadioButton(SIMPLE_RULE_RB_GROUP, RULE_TYPES_OTHER);
        rbRuleType.add(newButton);
        hodler.add(newButton);
        compReqTypesList.setStyleName("KS-Radio-Dropdown");

        if (selectedReqType != null) {            
        	int i = 0;
            for (ReqComponentTypeInfo comp : advReqCompTypeList) {
                if (comp.getId().equals(selectedReqType.getId())) {
                    compReqTypesList.selectItem(Integer.toString(i));    //comp.getId());
                    newButton.setValue(true);
                    break;
                }     
                i++;
            }
        }
        hodler.add(compReqTypesList);

        //don't advanced req. component types list if we don't have any
        if (advReqCompTypeList.size() > 0) {
            rbPanel.add(hodler);
        }

        container.add(rbPanel);
    }


    private void displayReqComponentDetails() {

        //TODO generic function that will retrieve all data required to display details of this req. componenet type...

        //true if no Requirement Component Type selected
        ruleDetailsPanel.clear();
        if (selectedReqType == null) {
            return;
        }
        displayReqComponentDetailsCont();
    }

    private String getTemplate(String nlUsageTypeKey) {
    	return getTemplate(nlUsageTypeKey, TEMLATE_LANGUAGE);
    }
    
    private String getTemplate(String nlUsageTypeKey, String language) {
    	for(ReqComponentTypeNLTemplateInfo template : editedReqComp.getRequiredComponentType().getNlUsageTemplates()) {
    		if(nlUsageTypeKey.equals(template.getNlUsageTypeKey()) && language.equals(template.getLanguage())) {
    			return template.getTemplate();
    		}
    	}
    	return null;
    }
    
    private void displayReqComponentDetailsCont() {
        //show heading
        VerticalPanel reqCompDetailsExampleContainerPanel = new VerticalPanel();
        KSLabel reqCompTypeName = new KSLabel(selectedReqType.getDesc() + ":");
        reqCompTypeName.setStyleName("KS-ReqMgr-SubHeading");
        reqCompDetailsExampleContainerPanel.add(reqCompTypeName);

        //show details
        HorizontalPanel reqCompDetailsExamplePanel = new HorizontalPanel();
        SimplePanel reqCompDetailsPanel = new SimplePanel();
        reqCompDetailsPanel.setStyleName("KS-Rules-ReqCompEdit-Width");

        String compositionTemplate = getTemplate(COMPOSITION_TEMLATE);
        displayReqComponentText(compositionTemplate, reqCompDesc, (editedReqComp == null ? null : editedReqComp.getReqCompFields()));
        reqCompDetailsPanel.add(reqCompDesc);

        reqCompDetailsExamplePanel.add(reqCompDetailsPanel);

        //show example
        VerticalPanel examplePanel = new VerticalPanel();
        examplePanel.setSpacing(0);
        KSLabel exampleText1 = new KSLabel("Example:");
        exampleText1.setStyleName("KS-RuleEditor-ExampleText1");
        examplePanel.add(exampleText1);
        String exampleTemplate = getTemplate(EXAMPLE_TEMLATE);
        exampleText.setText(exampleTemplate);
        exampleText.setStyleName("KS-RuleEditor-ExampleText2");
        examplePanel.add(exampleText);
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

        btnCancelView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (origReqCompType != null) {
                    editedReqComp.setType(origReqCompType); //revert possible changes to type
                }
                getController().showView(PrereqViews.MANAGE_RULES, Controller.NO_OP_CALLBACK);
            }
        });

        addReqComp.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {

            	//1. check that all fields have values
                if (retrieveReqCompFields() == false) {
                    return;
                }

            	//2. check that entered values are valid
                requirementsRpcServiceAsync.verifyFieldsAndSetIds(editedFields, new AsyncCallback<List<ReqCompFieldInfo>>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        caught.printStackTrace();
                    }

                    public void onSuccess(final List<ReqCompFieldInfo> editedFields) { 
                    	//3. update req. component being edited
                        editedReqComp.setReqCompFields(editedFields);                                                                
                        editedReqComp.setType(selectedReqType.getId());
                        editedReqCompVO.setCheckBoxOn(true);
                        
                        //4. create new req. component and possibly new statement if none exists yet
                        RuleInfo reqInfo = RulesUtilities.getReqInfoModelObject(modelRuleInfo);
                        StatementVO statementVO = reqInfo.getStatementVO();
                        
                        // Setup first statementVO if user just created the first req. component for this rule
                        if (statementVO == null) {
                            LuStatementInfo newLuStatementInfo = new LuStatementInfo();
                            statementVO = new StatementVO();
                            newLuStatementInfo.setOperator(StatementOperatorTypeKey.AND);
                            newLuStatementInfo.setType(reqInfo.getLuStatementTypeKey());
                            statementVO.setLuStatementInfo(newLuStatementInfo);
                            reqInfo.setStatementVO(statementVO);
                        }
                        statementVO.addReqComponentVO(editedReqCompVO);
                        statementVO.clearSelections();
                        
                        updateNLAndExit();                    	                    	
                    }
                });	    	            	                    	                    
            }
        });

        updateReqComp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

            	//1. check that all fields have values
                if (retrieveReqCompFields() == false) {
                    return;
                }

            	//2. check that entered values are valid
                requirementsRpcServiceAsync.verifyFieldsAndSetIds(editedFields, new AsyncCallback<List<ReqCompFieldInfo>>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        caught.printStackTrace();
                    }

                    public void onSuccess(final List<ReqCompFieldInfo> editedFields) { 
                    	//3. update req. component being edited
                        editedReqComp.setReqCompFields(editedFields);
                        
                        //2. update rule model
                        if (modelRuleInfo != null) {
                            RuleInfo prereqInfo = RulesUtilities.getReqInfoModelObject(modelRuleInfo);
                            StatementVO statementVO = prereqInfo.getStatementVO();
                            prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                            statementVO.clearSelections();
                            editedReqCompVO.setCheckBoxOn(true);
                        }
                        updateNLAndExit();                  	                    	
                    }
                });	                                                
            }
        });

        ruleTypeSelectionHandler = new FocusHandler() {
            public void onFocus(FocusEvent event) {

                addReqComp.setEnabled(true);
                updateReqComp.setEnabled(true);

                KSRadioButton btn = ((KSRadioButton) event.getSource());

                //de-select a list of advanced requirement comp. types
                if (compReqTypesList.getSelectedItem() != null) {
                    compReqTypesList.deSelectItem(compReqTypesList.getSelectedItem());
                }

                for (int i = 0; i < NOF_BASIC_RULE_TYPES; i++) {
                    if (btn.getText().trim().equals(reqCompTypeList.get(i).getDesc().trim())) {
                        selectedReqType = reqCompTypeList.get(i);
                        if (addNewReqComp) {
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
        };

        compReqTypesList.addSelectionChangeHandler(new SelectionChangeHandler() {
             public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                 addReqComp.setEnabled(true);
                 updateReqComp.setEnabled(true);
                 for (KSRadioButton button : rbRuleType) {
                     button.setValue(button.getText().equals(RULE_TYPES_OTHER) ? true : false);
                 }

                 List<String> ids = w.getSelectedItems();
                 selectedReqType = advReqCompTypeList.get(Integer.valueOf(ids.get(0)));
                 if (addNewReqComp) {
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
        	if (reqCompWidget.getClass().getName().contains("KSTextBox")) {
        		name = ((KSTextBox)reqCompWidget).getName();
        		value = ((KSTextBox)reqCompWidget).getText();
        	} else if (reqCompWidget.getClass().getName().contains("TmpCoursePicker")) {
        		name = ((TmpCoursePicker)reqCompWidget).getName();
        		value = ((TmpCoursePicker)reqCompWidget).getSelectedValue();            		
        	}
        	
            ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
            fieldInfo.setId(name);
            fieldInfo.setValue(value);
            if (checkField(fieldInfo) == false) {
            	editedFields.clear();
                return false;
            }

            if (fieldInfo.getId().equals("reqCompFieldType.clu")) {
            	enteredCluCodes.append((enteredCluCodes.length() > 0 ? ", " : "") + fieldInfo.getValue());
            } else {
            	editedFields.add(fieldInfo);
            }
        }
        
    	if (enteredCluCodes.length() > 0) {
            ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
            fieldInfo.setId("reqCompFieldType.clu");
            fieldInfo.setValue(enteredCluCodes.toString());
            editedFields.add(fieldInfo);
        }

        return true;    	
    }

    private boolean checkField(ReqCompFieldInfo fieldInfo) {

        String fieldValue = fieldInfo.getValue();

        if (fieldValue.trim().isEmpty()) {
            Window.alert("Please enter all fields");
            return false;
        }

        if (fieldInfo.getId().equals("reqCompFieldType.clu")) {
            if (fieldValue.contains(",")) {
                Window.alert("Please enter only one course");
            }
            return true;
        }

        if (fieldInfo.getId().equals("reqCompFieldType.cluSet")) {
            String cluSetId = cluSetsData.get(fieldValue);
            if (cluSetId != null) {
                fieldInfo.setValue(cluSetId);
                return true;
            }
            if (fieldValue.contains(",")) {
                Window.alert("Please enter only one course set");
            } else {
                Window.alert("Cannot find course set '" + fieldValue + "'");
            }
            return false;
        }
        return true;
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
            final Map<String, String> fieldProperties =
                new HashMap<String, String>();
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
            tagCounts.put(tag, new Integer(tagCount));

            if ((tag.equals("reqCompFieldType.requiredCount")) || (tag.equals("reqCompFieldType.gpa")) || (tag.equals("reqCompFieldType.totalCredits"))) {
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

            if (tag.equals("reqCompFieldType.clu")) {
            	final TmpCoursePicker valueWidget = configureCourseSearch();
                valueWidgets.add(valueWidget);
                String cluIdsInClause = getSpecificFieldValue(fields, tag);
                
                String[] cluIds = (cluIdsInClause == null)? null : cluIdsInClause.split("(, *)");                
                //retrieve clu code to display for user
                if ((cluIds != null) && (tagCount < cluIds.length) && (cluIds[tagCount].length() > 0)) {
                	retrieveCluCode(tagCount, cluIds[tagCount]);
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

            if (tag.equals("reqCompFieldType.cluSet")) {
                final KSTextBox valueWidget = new KSTextBox();
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tag);
                valueWidget.setText(getSpecificFieldValue(fields, tag));
                valueWidget.setWidth("250px");
                valueWidget.setStyleName("KS-Textbox-Fix");
                valueWidget.addStyleName("KS-Rules-FlexPanelFix");
                VerticalPanel tempPanel = new VerticalPanel();
                tempPanel.setStyleName("KS-Rules-FlexPanelFix");
                tempPanel.add(valueWidget);
                final SearchDialog searchDialog = new SearchDialog(getController(), cluSetsData);
                searchDialog.addCourseAddHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        String origFieldValue = valueWidget.getText();
                        int fieldValueCount = 0;
                        origFieldValue = (origFieldValue == null)? "" : origFieldValue;
                        StringBuilder newFieldValue = new StringBuilder("");
                        SortedSet<String> newValues = new TreeSet<String>();
                        newValues.addAll(Arrays.asList(origFieldValue.split(", +")));
                        newValues.addAll(searchDialog.getSelections());
                        for (String newValue : newValues) {
                            if (fieldValueCount > 0 && newFieldValue.toString().trim().length() > 0) {
                                newFieldValue.append(", ");
                            }
                            newFieldValue.append(newValue);
                            fieldValueCount++;
                        }
                        valueWidget.setText(newFieldValue.toString());
                    }
                });
                tempPanel.add(searchDialog);
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

	private void retrieveCluCode(final int id, final String cluId) {	
        requirementsRpcServiceAsync.retrieveCluCode(cluId, new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                caught.printStackTrace();
            }

            public void onSuccess(final String cluCode) {
            	if (cluCode != null) {
            		valueWidgets.get(id).setSuggestBox(cluId, cluCode);
            	}
            }
        });		  
	}
    
    public static class TmpCoursePicker extends KSSearchComponent {

    	private String name;
    	private String text;

		public TmpCoursePicker(SearchComponentConfiguration searchConfig,
				SearchSuggestOracle orgSearchOracle) {
			super(searchConfig, orgSearchOracle);
			// TODO Auto-generated constructor stub
		}
    	
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}    	
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
        String result = null;

        //if we are showing new req. comp. type then show empty fields
        if (reqCompFieldInfo == null) {
            return "";
        }

        result = reqCompFieldInfo.getValue();
        result = (result == null)? "" : result;
        return result;
    }

    private void setupNewEditedReqComp(ReqComponentTypeInfo reqCompTypeInfo) {
        editedReqComp = new ReqComponentInfo();
        editedReqComp.setDesc("");      //will be set after user is finished with all changes
        editedReqComp.setId(Integer.toString(tempCounterID++));  //TODO
        editedReqComp.setReqCompFields(null); //fieldList);                       
        editedReqComp.setRequiredComponentType(reqCompTypeInfo);
        if (reqCompTypeInfo != null) editedReqComp.setType(reqCompTypeInfo.getId());
        editedReqCompVO = new ReqComponentVO(editedReqComp);
    }

    private void setReqComponentListAndReqComp() {

        getController().requestModel(ReqComponentTypeInfo.class, new ModelRequestCallback<CollectionModel<ReqComponentTypeInfo>>() {
            public void onModelReady(CollectionModel<ReqComponentTypeInfo> theModel) {
                reqCompTypeList = new ArrayList<ReqComponentTypeInfo>();
                reqCompTypeList.addAll(theModel.getValues());

                advReqCompTypeList = new ArrayList<ReqComponentTypeInfo>();

                if (reqCompTypeList.size() > NOF_BASIC_RULE_TYPES) {
                    for(int i = NOF_BASIC_RULE_TYPES; i < reqCompTypeList.size(); i++){
                        advReqCompTypeList.add(reqCompTypeList.get(i));
                    }
                }

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
                            value = advReqCompTypeList.get(index).getDesc();
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

                compReqTypesList.setListItems(listItemReqCompTypes);

                setupReqComponent();
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });
    }

    private void setupReqComponent() {

        if (compReqTypesList.getSelectedItem() != null) {
            compReqTypesList.deSelectItem(compReqTypesList.getSelectedItem());
        }

        getController().requestModel(RuleInfo.class, new ModelRequestCallback<CollectionModel<RuleInfo>>() {
            public void onModelReady(CollectionModel<RuleInfo> theModel) {
                modelRuleInfo = theModel;

                getController().requestModel(ReqComponentVO.class, new ModelRequestCallback<CollectionModel<ReqComponentVO>>() {
                    public void onModelReady(CollectionModel<ReqComponentVO> theModel) {

                        if (theModel != null) {
                            List<ReqComponentVO> selectedReqComp = new ArrayList<ReqComponentVO>();
                            selectedReqComp.addAll(theModel.getValues());

                            //true if we are editing existing rule
                            origReqCompType = null;
                            if (selectedReqComp.size() > 0) {
                                addNewReqComp = false;
                                editedReqCompVO = theModel.get(selectedReqComp.get(0).getId());
                                editedReqComp = editedReqCompVO.getReqComponentInfo();
                                origReqCompType = editedReqComp.getType();
                                for (int i = 0; i < reqCompTypeList.size(); i++) {
                                    if (editedReqComp.getType().equals(reqCompTypeList.get(i).getId())) {
                                        selectedReqType = reqCompTypeList.get(i);
                                        break;
                                    }
                                }

                            } else {
                                //create a basic structure for a new rule
                                addNewReqComp = true;
                                setupNewEditedReqComp(null);
                                selectedReqType = null;
                            }
                        }
                        redraw();
                    }

                    public void onRequestFail(Throwable cause) {
                        throw new RuntimeException("Unable to connect to model", cause);
                    }
                });

            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });
    }

    private void updateNLAndExit() {    	            	
        requirementsRpcServiceAsync.getNaturalLanguageForReqComponentInfo(editedReqComp, "KUALI.CATALOG", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                caught.printStackTrace();
            }

            public void onSuccess(final String reqCompNaturalLanguage) {
                editedReqCompVO.setTypeDesc(reqCompNaturalLanguage);
                RuleInfo prereqInfo = RulesUtilities.getReqInfoModelObject(modelRuleInfo);
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                getController().showView(PrereqViews.MANAGE_RULES, Controller.NO_OP_CALLBACK);
            }
        });            	
    }

    public void setCluSetsData(Map<String, String> cluSetsData) {
        this.cluSetsData = cluSetsData;
    }
    
    private String getRuleTypeName() {
    	String luStatementTypeKey = RulesUtilities.getReqInfoModelObject(modelRuleInfo).getLuStatementTypeKey();
        if (luStatementTypeKey.contains("enroll")) return "Enrollment Restriction";
        if (luStatementTypeKey.contains("prereq")) return "Prerequisite";
        if (luStatementTypeKey.contains("coreq")) return "Corequisite";
        if (luStatementTypeKey.contains("antireq")) return "Antirequisite";
        return "";
    }  
    
    private static TmpCoursePicker configureCourseSearch() {
 	   
    	LuRpcServiceAsync luRpcServiceAsync = GWT.create(LuRpcService.class);
    	
    	List<String> basicCriteria = new ArrayList<String>() {
  		   {
  		      add("lu.queryParam.luOptionalLongName");
  		   }
  		};
  	
  		List<String> advancedCriteria = new ArrayList<String>() {
   		   {
   		      add("lu.queryParam.luOptionalLongName");
  		      add("lu.queryParam.luOptionalCode");
  		      add("lu.queryParam.luOptionalLevel");  		      
   		   }
   		};       			
   		
        //set context criteria - we want to show only courses
   		List<QueryParamValue> contextCriteria = new ArrayList<QueryParamValue>();
		QueryParamValue orgOptionalTypeParam = new QueryParamValue();
		orgOptionalTypeParam.setKey("lu.queryParam.luOptionalType");
		orgOptionalTypeParam.setValue("luType.shell.course");   
		contextCriteria.add(orgOptionalTypeParam); 
    	
    	SearchComponentConfiguration searchConfig = new SearchComponentConfiguration(contextCriteria, basicCriteria, advancedCriteria);
    	
    	searchConfig.setSearchDialogTitle("Find Course");
    	searchConfig.setSearchService(luRpcServiceAsync);
    	searchConfig.setSearchTypeKey("lu.search.generic");
    	searchConfig.setResultIdKey("lu.resultColumn.cluId");  //lu.resultColumn.luOptionalCode  lu.resultColumn.cluId
    	searchConfig.setRetrievedColumnKey("lu.resultColumn.luOptionalCode");
    	
    	//TODO: following code should be in KSSearchComponent with config parameters set within SearchComponentConfiguration class
    	final SearchSuggestOracle cluSearchOracle = new SearchSuggestOracle(searchConfig.getSearchService(),
    	        "lu.search.generic", 
    	        "lu.queryParam.luOptionalCode", //field user is entering and we search on... add '%' the parameter
    	        "lu.queryParam.luOptionalId", 		//if one wants to search by ID rather than by name
    	        "lu.resultColumn.cluId", 		
    	        "lu.resultColumn.luOptionalCode");    	  				
    	
    	return new TmpCoursePicker(searchConfig, cluSearchOracle);
    }     
}
