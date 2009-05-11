package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class ClauseEditorView extends ViewComposite {

    public enum reqCompFieldDefinitions { TODO }
    
    //view's widgets
    private static final String SIMPLE_RULE_RB_GROUP = "RuleTypesGroup";
    private KSRadioButton rbRuleType1 = new KSRadioButton(SIMPLE_RULE_RB_GROUP, "Student must take all of the specified courses"); 
    private KSRadioButton rbRuleType2 = new KSRadioButton(SIMPLE_RULE_RB_GROUP, "Student must take one of two specified courses"); 
    private KSRadioButton rbRuleType3 = new KSRadioButton(SIMPLE_RULE_RB_GROUP, "Student must take <n> credits from the specified courses");    
    private KSRadioButton rbRuleType4 = new KSRadioButton(SIMPLE_RULE_RB_GROUP, "Other:"); 
    private FocusHandler ruleTypeSelectionHandler = null;
    private KSTextBox choice1CourseList = new KSTextBox();
    private final SearchDialog searchPanelChoice1 = new SearchDialog(getController());
    private KSTextBox choice2CourseA = new KSTextBox();
    private KSTextBox choice2CourseB = new KSTextBox();
    private final SearchDialog searchPanelChoice2a = new SearchDialog(getController());
    private final SearchDialog searchPanelChoice2b = new SearchDialog(getController());
    private KSTextBox choice3NumCredits = new KSTextBox();
    private KSTextBox choice3CourseList = new KSTextBox();
    private final SearchDialog searchPanelChoice3 = new SearchDialog(getController());         
    
    private Panel mainPanel = new SimplePanel();
    private VerticalPanel addEditRuleView = new VerticalPanel();
    private KSButton btnCancelView = new KSButton("Cancel");
    private KSButton addReqComp = new KSButton("Add Rule");
    private KSButton submitReqComp = new KSButton("Submit Rule");
    KSDropDown compReqTypesList = new KSDropDown();
    SimplePanel reqCompDesc = new SimplePanel();  
    private KSLabel exampleText2 = new KSLabel();    
    
    //view's data
    ReqComponentTypeInfo selectedReqType;
    ReqComponentInfo editedReqComp; 
    ReqComponentVO editedReqCompVO;     
    ListItems listItemReqCompTypes;
    private List<ReqComponentTypeInfo> reqCompTypeList;
    private List<KSTextBox> reqCompWidgets = new ArrayList<KSTextBox>(); 
    private String selectedReqCompType;

    public ClauseEditorView(Controller controller) {
        super(controller, "Clause Editor View");
        super.initWidget(mainPanel);
        setupHandlers();
        setReqComponentList();
    }
    
    public void beforeShow() {
                       
        String selectedItem = compReqTypesList.getSelectedItem();
        if (selectedItem != null) {
            compReqTypesList.deSelectItem(compReqTypesList.getSelectedItem());
        }
        
        getController().requestModel(ReqComponentVO.class, new ModelRequestCallback<ReqComponentVO>() {
            public void onModelReady(Model<ReqComponentVO> theModel) {
                if (theModel != null) {
                    List<ReqComponentVO> selectedReqComp = new ArrayList<ReqComponentVO>();
                    selectedReqComp.addAll(theModel.getValues());

                    if (selectedReqComp.size() > 0) {
                        editedReqCompVO = theModel.get(selectedReqComp.get(0).getId());                       
                        editedReqComp = editedReqCompVO.getReqComponentInfo();
                    } else {
                        editedReqCompVO = null;
                        editedReqComp = null;
                    }
                    
                }
                redraw();
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });                  
    }
    
    public void redraw() { 
      
        addEditRuleView.clear();
        addEditRuleView.setStyleName("KS-Rules-FullWidth");
        
        //1. show view HEADING
        KSLabel Heading = new KSLabel((editedReqComp == null ? "Add" : "Edit") + " Prerequisite Rule");
        Heading.setStyleName("KS-Rules-FullWidth"); 
        Heading.setStyleName("KS-ReqMgr-Heading");
        addEditRuleView.add(Heading);
        
        //2. show RULE TYPES
        HorizontalPanel ruleTypesPanel = new HorizontalPanel();        
        
        //show requirement component label
        SimplePanel labelPanel = new SimplePanel();       
        labelPanel.setStyleName("KS-ReqCompEditor-EditFirstColumn");        
        KSLabel reqTypeLabel = new KSLabel("Rule");
        reqTypeLabel.setStyleName("KS-RuleEditor-SubHeading"); 
        labelPanel.add(reqTypeLabel);
        ruleTypesPanel.add(labelPanel);               

        //show list of rule types
        displayReqComponentTypes(ruleTypesPanel);
        addEditRuleView.add(ruleTypesPanel); 
                
        SimplePanel verticalSpacer2 = new SimplePanel();
        verticalSpacer2.setHeight("30px");
        addEditRuleView.add(verticalSpacer2);        
        
        //3. show SELECTED RULE TYPE DETAILS
        HorizontalPanel ruleDetailsPanel = new HorizontalPanel();  
        SimplePanel emptyPanel = new SimplePanel();       
        emptyPanel.setStyleName("KS-ReqCompEditor-EditFirstColumn"); 
        ruleDetailsPanel.add(emptyPanel);          
        displayReqComponentDetails(ruleDetailsPanel);              
        addEditRuleView.add(ruleDetailsPanel); 
                       
        //buttons at the bottom        
        HorizontalPanel tempPanelButtons = new HorizontalPanel();        
        tempPanelButtons.setStyleName("KS-ReqCompEditor-BottomButtons");        
        tempPanelButtons.add(btnCancelView);
        btnCancelView.setStyleName("KS-Rules-Standard-Button");   
        if (editedReqComp == null) {
            tempPanelButtons.add(addReqComp);
            addReqComp.setStyleName("KS-Rules-Standard-Button");            
        } else {
            tempPanelButtons.add(submitReqComp);
            submitReqComp.setStyleName("KS-Rules-Standard-Button"); 
        }                                
        
        addEditRuleView.add(tempPanelButtons);
        addEditRuleView.setStyleName("Content-Margin");
        mainPanel.clear();
        mainPanel.add(addEditRuleView);
        
        //updateExampleContext(); TODO - download all necessary contexts?
    }
    
    private void displayReqComponentTypes(Panel container) {
        //show requirement types
        VerticalPanel rbPanel = new VerticalPanel();
        rbRuleType1.addFocusHandler(ruleTypeSelectionHandler);
        rbPanel.add(rbRuleType1);
        rbRuleType2.addFocusHandler(ruleTypeSelectionHandler);                
        rbPanel.add(rbRuleType2);
        rbRuleType3.addFocusHandler(ruleTypeSelectionHandler);        
        rbPanel.add(rbRuleType3);
        rbRuleType4.addFocusHandler(ruleTypeSelectionHandler);
        HorizontalPanel hodler = new HorizontalPanel();
        hodler.add(rbRuleType4);
        hodler.add(compReqTypesList);        
        rbPanel.add(hodler);                
        container.add(rbPanel);        
    }
    
    private void displayReqComponentDetails(Panel container) {
        
        //TODO generic function that will retrieve all data required to display details of this req. componenet type...
        
        String reqComponentTypeDescription = "Student must take one of two specified courses";
        String reqComponentTypeComposition = "<reqCompFieldType.clu> or <reqCompFieldType.clu>";
        String reqComponentExample = "Students must have taken one of the following courses: EDUC 100, EDUC 101";
        
        //show heading
        VerticalPanel reqCompDetailsExampleContainerPanel = new VerticalPanel(); 
        KSLabel reqCompTypeName = new KSLabel(reqComponentTypeDescription);
        reqCompTypeName.setStyleName("KS-Rules-BoldText");
        reqCompDetailsExampleContainerPanel.add(reqCompTypeName);

        //show details
        HorizontalPanel reqCompDetailsExamplePanel = new HorizontalPanel();
        SimplePanel reqCompDetailsPanel = new SimplePanel();
        reqCompDetailsPanel.setStyleName("KS-Rules-ReqCompEdit-Width");
        
        
        
        //TODO        
        VerticalPanel holder2a = new VerticalPanel();
        choice2CourseA.setWidth("100px");
        searchPanelChoice2a.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice2CourseA.getText();
                String newFieldValue = combineValues(origFieldValue, searchPanelChoice2a.getSelections());
                choice2CourseA.setText(newFieldValue);
            }
        });         
        holder2a.add(choice2CourseA);
        holder2a.add(searchPanelChoice2a);        
        reqCompDetailsPanel.add(holder2a);
        
        reqCompDetailsExamplePanel.add(reqCompDetailsPanel);
        
        //show example
        VerticalPanel examplePanel = new VerticalPanel();        
        examplePanel.setSpacing(0);   
        KSLabel exampleText1 = new KSLabel("Example:");
        exampleText1.setStyleName("KS-RuleEditor-ExampleText1");
        examplePanel.add(exampleText1);
        exampleText2.setText(reqComponentExample);
        exampleText2.setStyleName("KS-RuleEditor-ExampleText2");
        examplePanel.add(exampleText2);
        reqCompDetailsExamplePanel.add(examplePanel);
        
        reqCompDetailsExampleContainerPanel.add(reqCompDetailsExamplePanel);
        
        /*          
        if (editedReqComp != null) {
            String reqTypeDesc = "";
            selectedReqCompName = "";
            for (ReqComponentTypeInfo reqTypeInfo : reqCompTypeList) {
                if (editedReqComp.getType().equals(reqTypeInfo.getId())) {
                    reqTypeDesc = reqTypeInfo.getDesc();
                    selectedReqCompName = reqTypeInfo.getName();
                }
            }            
            displayReqComponentText(reqTypeDesc, reqCompDesc, editedReqComp.getReqCompField());            
        }           
         

        */
        
        container.add(reqCompDetailsExampleContainerPanel);
    }
    
    public void setReqComponentList() {        
        
        getController().requestModel(ReqComponentTypeInfo.class, new ModelRequestCallback<ReqComponentTypeInfo>() {
            public void onModelReady(Model<ReqComponentTypeInfo> theModel) {
                reqCompTypeList = new ArrayList<ReqComponentTypeInfo>();
                reqCompTypeList.addAll(theModel.getValues());
                
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
                            value = reqCompTypeList.get(index).getName();
                        } catch (Exception e) {
                        }

                        return value;
                    }                    
                    
                    @Override
                    public int getItemCount() {    
                        return reqCompTypeList.size();
                    }

                    @Override
                    public List<String> getItemIds() {
                        List<String> ids = new ArrayList<String>();
                        for(int i=0; i < reqCompTypeList.size(); i++){
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
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });                                               
    }

    private void setupHandlers() {
        
        btnCancelView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.COMPLEX);
            }
        });
        
        addReqComp.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent event) {              
           
                //store new values in the req. component info   
                getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
                    public void onModelReady(Model<PrereqInfo> theModel) {
                                             
                        List<ReqCompFieldInfo> fields = new ArrayList<ReqCompFieldInfo>();
                        for (KSTextBox reqCompWidget : reqCompWidgets) {
                            ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
                            fieldInfo.setId(reqCompWidget.getName());
                            fieldInfo.setValue(reqCompWidget.getText());
                            fields.add(fieldInfo);
                        }
                        ReqComponentInfo newReqComp = new ReqComponentInfo();
                        newReqComp.setReqCompField(fields);
                        newReqComp.setDesc("New Req. Comp. Description");
                        newReqComp.setId("999");  //TODO
                        newReqComp.setType(selectedReqType.getId());

                        //add new req. component (rule) to the top level of the rule
                        PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(theModel);
                        StatementVO statementVO = prereqInfo.getStatementVO();
                        editedReqCompVO = new ReqComponentVO(newReqComp);
                        int index = Integer.valueOf(compReqTypesList.getSelectedItem());
                        ReqComponentTypeInfo reqInfo = reqCompTypeList.get(index);
                        editedReqCompVO.setTypeDesc(reqInfo.getDesc());
                        editedReqCompVO.setDirty(true);
                        statementVO.addReqComponentVO(editedReqCompVO);                                                                      
                        //theModel.add(prereqInfo);                        
                        
                        getController().showView(PrereqViews.COMPLEX); 
                    }

                    public void onRequestFail(Throwable cause) {
                        throw new RuntimeException("Unable to connect to model", cause);
                    }
                });                 
                               
            }
        });   
        
        submitReqComp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {                
                List<ReqCompFieldInfo> fields = editedReqComp.getReqCompField();

                for (KSTextBox reqCompWidget : reqCompWidgets) {
                    if ((fields == null) || (fields.isEmpty())) {
                        fields = new ArrayList<ReqCompFieldInfo>();
                        ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
                        fieldInfo.setId(reqCompWidget.getName());
                        fieldInfo.setValue(reqCompWidget.getText());
                        fields.add(fieldInfo);
                    }
                    else
                    {
                        for (ReqCompFieldInfo field : fields) {
                            if (reqCompWidget.getName().equals(field.getId())) {
                                field.setValue(reqCompWidget.getText());
                            }
                        }
                    }
                }
              
                getController().showView(PrereqViews.COMPLEX);                
            }
        });        
        
        ruleTypeSelectionHandler = new FocusHandler() {

            public void onFocus(FocusEvent event) {
                //TODO
                System.out.println("In focus handler: " + event.getSource());
            }
        };               
        
        compReqTypesList.addSelectionChangeHandler(new SelectionChangeHandler() {  
             public void onSelectionChange(KSSelectItemWidgetAbstract w) {                           
                 List<String> ids = w.getSelectedItems();
                 int index = Integer.valueOf(ids.get(0));
                 selectedReqType = reqCompTypeList.get(index);
                 displayReqComponentText(selectedReqType.getDesc(), reqCompDesc, null);   
                 updateExampleContext();                 
             }});
        
    }
    
    private CaptionPanel setupRuleTypeChoicePanel() {
        
        CaptionPanel capPnlPickRuleType = new CaptionPanel("Student must have taken:");
       // capPnlPickRuleType.setStyleName("KS-RuleEditor-SimplePanel-ReqCompTypes");        
        VerticalPanel pnlChoices = new VerticalPanel(); 
        HorizontalPanel choice1 = new HorizontalPanel(); 
        HorizontalPanel choice2 = new HorizontalPanel(); 
        HorizontalPanel choice3 = new HorizontalPanel(); 
        choice1.setSpacing(5);
        choice2.setSpacing(5);
        choice3.setSpacing(5);    
        
       

        // choice 1
        KSLabel lbChoice1 = new KSLabel("The following course(s):");
        lbChoice1.addStyleName("KS-Rules-Choices");
        choice1.add(rbRuleType1);
        choice1.add(lbChoice1);
        VerticalPanel holder = new VerticalPanel();
        choice1CourseList.setWidth("250px");
        holder.add(choice1CourseList);
        holder.add(searchPanelChoice1);
        searchPanelChoice1.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice1CourseList.getText();
                String newFieldValue = combineValues(origFieldValue, searchPanelChoice1.getSelections());
                choice1CourseList.setText(newFieldValue);
            }
        });
        choice1.add(holder);
                
        // choice 2
        choice2.add(rbRuleType2);
        choice2CourseA.setWidth("100px");
        VerticalPanel holder2a = new VerticalPanel();
        holder2a.add(choice2CourseA);
        holder2a.add(searchPanelChoice2a);
        searchPanelChoice2a.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice2CourseA.getText();
                String newFieldValue = combineValues(origFieldValue, searchPanelChoice2a.getSelections());
                choice2CourseA.setText(newFieldValue);
            }
        });        
        choice2.add(holder2a);
        
        KSLabel lbChoice2 = new KSLabel("or");
        
        lbChoice2.addStyleName("KS-Rules-Choices");
        choice2.add(lbChoice2);
        choice2CourseB.setWidth("100px");        
        VerticalPanel holder2b = new VerticalPanel();
        holder2b.add(choice2CourseB);
        holder2b.add(searchPanelChoice2b);
        searchPanelChoice2b.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice2CourseB.getText();
                String newFieldValue = combineValues(origFieldValue, searchPanelChoice2b.getSelections());
                choice2CourseB.setText(newFieldValue);
            }
        });        
        choice2.add(holder2b);               
        
        // choice 3
        KSLabel lbChoice3 = new KSLabel("credits from the following course(s):");
        lbChoice3.addStyleName("KS-Rules-Choices");
        choice3.add(rbRuleType3);
        choice3NumCredits.setWidth("50px");
        choice3.add(choice3NumCredits);
        choice3.add(lbChoice3);
        choice3CourseList.setWidth("150px");      
        VerticalPanel holder3 = new VerticalPanel();
        holder3.add(choice3CourseList);
        holder3.add(searchPanelChoice3);        
        searchPanelChoice3.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice3CourseList.getText();
                String newFieldValue = combineValues(origFieldValue, searchPanelChoice3.getSelections());
                choice3CourseList.setText(newFieldValue);
            }
        });
        choice3.add(holder3);
        
        pnlChoices.add(choice1);
        pnlChoices.add(choice2);
        pnlChoices.add(choice3);
        capPnlPickRuleType.add(pnlChoices);    
        
        return capPnlPickRuleType;
    }    

    private void displayReqComponentText(String reqInfoDesc, SimplePanel parentWidget, final List<ReqCompFieldInfo> fields) {
        // resets the list of reqCompWidgets to make sure it is created fresh.
        reqCompWidgets.clear();
        parentWidget.clear();
        FlowPanel innerReqComponentTextPanel = new FlowPanel();       
        innerReqComponentTextPanel.setStyleName("KS-Rules-FullWidth");
        final String[] tokens = reqInfoDesc.split("[<>]");
        boolean isValueWidget = true;
        
        for (int i = 0; i < tokens.length; i++) {
            
            isValueWidget = !isValueWidget;
            //this token is a text only
            if (isValueWidget == false) {
                KSLabel text = new KSLabel(tokens[i]);
                text.addStyleName("KS-Rules-FlexPanelFix");  
                innerReqComponentTextPanel.add(text);
                continue;
            }
            
            //TODO use ENUMs and Switch()
            String tag = tokens[i].replaceFirst("reqCompFieldType.", "");
            tag = tag.replaceFirst("reqCompFiledType.", "");

            if ((tag.equals("requiredCount")) || (tag.equals("gpa")) || (tag.equals("clu"))) {
                KSTextBox valueWidget = new KSTextBox();
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tokens[i]);
                valueWidget.setText(getSpecificFieldValue(fields, tokens[i]));
//                valueWidget.setWidth(Integer.toString(parentWidget.getOffsetWidth()));
                valueWidget.setWidth("50px");
                valueWidget.setStyleName("KS-Textbox-Fix");
                SimplePanel tempPanel = new SimplePanel();
                tempPanel.addStyleName("KS-Rules-FlexPanelFix");
                tempPanel.add(valueWidget);                
                innerReqComponentTextPanel.add(tempPanel);
                continue;                                
            }
            
            if (tag.equals("cluSet")) {
                final KSTextBox valueWidget = new KSTextBox();
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tokens[i]);
                final ReqCompFieldInfo reqCompFieldInfo = getReqCompFieldInfo(fields, tokens[i]);
                valueWidget.setText(getSpecificFieldValue(fields, tokens[i]));             
                valueWidget.setWidth("250px");                           
                valueWidget.setStyleName("KS-Textbox-Fix"); 
                valueWidget.addStyleName("KS-Rules-FlexPanelFix");
                SimplePanel tempPanel = new SimplePanel();
                tempPanel.setStyleName("KS-Rules-FlexPanelFix");
                tempPanel.add(valueWidget);                     
                innerReqComponentTextPanel.add(tempPanel);
                final SearchDialog searchDialog = new SearchDialog(getController());
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
                innerReqComponentTextPanel.add(searchDialog);
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
        String result = null;
        
        //if we are showing new req. comp. type then show empty fields
        if (reqCompFieldInfo == null) {
            return "";
        }
        
        result = reqCompFieldInfo.getValue();
        result = (result == null)? "" : result;    
        return result;
    }
    
    private void updateExampleContext() {         
        if (editedReqComp != null) {        
            RequirementsService.Util.getInstance().getNaturalLanguageForReqComponentInfo(editedReqComp, "KUALI.EXAMPLE", new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                    System.out.println(caught.getMessage());
                    caught.printStackTrace();
                }
                
                public void onSuccess(final String nl) {
                    exampleText2.setText(nl);
                } 
            });          
        }
    }          
    
    /**
     * returns a comma delimited string that represents the union of the originalValue and selectedValues
     * @param originalValue
     * @param selectedValues
     * @return
     */
    private String combineValues(String originalValue, List<String> selectedValues) {
        int fieldValueCount = 0;
        String tempOriginalValue = 
            (originalValue == null)? "" : originalValue;
        StringBuilder newFieldValue = new StringBuilder("");
        SortedSet<String> newValues = new TreeSet<String>();
        newValues.addAll(Arrays.asList(tempOriginalValue.split(", +")));
        newValues.addAll(selectedValues);
        for (String newValue : newValues) {
            if (fieldValueCount > 0 && 
                    newFieldValue.toString().trim().length() > 0) {
                newFieldValue.append(", ");
            }
            newFieldValue.append(newValue);
            fieldValueCount++;
        }
        return newFieldValue.toString();
    }    
}
