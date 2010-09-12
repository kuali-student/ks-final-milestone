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

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.naturallanguage.util.ReqComponentFieldTypes;
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

public class RuleComponentEditorView extends ViewComposite {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);

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
    private KSButton btnCancelView = new KSButton("Cancel");
    private KSButton addReqComp = new KSButton("Add Rule");
    private KSButton updateReqComp = new KSButton("Update Rule");
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

    public RuleComponentEditorView(Controller controller) {
        super(controller, "Clause Editor View");
        super.initWidget(mainPanel);
        setupHandlers();
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        setupReqCompTypesList();

        getController().requestModel(RuleInfo.class, new ModelRequestCallback<CollectionModel<RuleInfo>>() {
            public void onModelReady(CollectionModel<RuleInfo> theModel) {
                model = theModel;
            }

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

        //1. show view HEADING
        SimplePanel headingPanel = new SimplePanel();
        KSLabel heading = new KSLabel((addingNewReqComp ? "Add " : "Edit ") + getRuleTypeName() + " Rule");
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
        if (addingNewReqComp) {
            tempPanelButtons.add(addReqComp);
            addReqComp.setStyleName("KS-Rules-Tight-Button");
            addReqComp.setEnabled(false);
        } else {
            tempPanelButtons.add(updateReqComp);
            updateReqComp.setStyleName("KS-Rules-Tight-Button");
            updateReqComp.setEnabled(true);
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
                	addReqComp.setEnabled(true);
                	updateReqComp.setEnabled(true);
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
        KSLabel reqCompTypeName = new KSLabel(selectedReqType.getDescr() + ":");
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

        btnCancelView.addClickHandler(new ClickHandler() {
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

        addReqComp.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {

            	//1. check that all fields have values
                if (retrieveReqCompFields() == false) {
                    return;
                }

            	//2. update req. component being edited
                editedReqComp.setReqCompFields(editedFields);
                editedReqComp.setType(selectedReqType.getId());

                //3. create new req. component and possibly new statement if none exists yet
                editedStatementVO.addReqComponentVO(editedReqCompVO);
                updateNLAndExit();
            }
        });

        updateReqComp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

            	//1. check that all fields have values
                if (retrieveReqCompFields() == false) {
                    return;
                }

            	//2. update req. component being edited (type was updated already)
                editedReqComp.setReqCompFields(editedFields);

                //3. update rule
                updateNLAndExit();
            }
        });

        compReqTypesList.addSelectionChangeHandler(new SelectionChangeHandler() {
			@Override
            public void onSelectionChange(SelectionChangeEvent event) {
                 addReqComp.setEnabled(true);
                 updateReqComp.setEnabled(true);
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

    private String getFieldName(ReqCompFieldInfo fieldInfo) {

        if (fieldInfo.getId().equals(ReqComponentFieldTypes.CLU_KEY.getKey())) {
            return "Course";
        } else if (fieldInfo.getId().equals(ReqComponentFieldTypes.CLUSET_KEY.getKey())) {
            return "Courses";
        } else if (fieldInfo.getId().equals(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getKey())) {
            return "count";
        } else if (fieldInfo.getId().equals(ReqComponentFieldTypes.GPA_KEY.getKey())) {
            return "GPA";
        } else if (fieldInfo.getId().equals(ReqComponentFieldTypes.TOTAL_CREDIT_KEY.getKey())) {
            return "Total Credits";
        }

        return "";
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

            if ((tag.equals(ReqComponentFieldTypes.REQUIRED_COUNT_KEY.getKey())) || 
            		(tag.equals(ReqComponentFieldTypes.GPA_KEY.getKey())) || (tag.equals(ReqComponentFieldTypes.TOTAL_CREDIT_KEY.getKey()))) {
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

				//need a better way to determine what pickers to use for given req. component type field
                final ReqCompPicker valueWidget;
                if ((editedReqComp.getRequiredComponentType().getId().equals("kuali.reqCompType.programList.enroll.oneof")) ||
                        (editedReqComp.getRequiredComponentType().getId().equals("kuali.reqCompType.programList.enroll.none"))) {
                    valueWidget = configureProgramCluSetSearch(fieldLabel);
                } else {
                    valueWidget = configureCourseCluSetSearch(fieldLabel);
                }
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
}
