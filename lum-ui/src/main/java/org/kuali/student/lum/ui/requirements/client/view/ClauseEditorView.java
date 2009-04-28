package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ClauseEditorView extends ViewComposite {

    public enum reqCompFieldDefinitions { TODO }
    
    //view's widgets
    private Panel mainPanel = new SimplePanel();
    private VerticalPanel editorView = new VerticalPanel();
    private KSButton btnCancelView = new KSButton("Cancel");
    private KSButton addReqComp = new KSButton("Add Rule");
    private KSButton submitReqComp = new KSButton("Submit");
    KSListBox compReqTypesList = new KSListBox();
    SimplePanel reqCompDesc = new SimplePanel();  
    HorizontalPanel examplePanel = new HorizontalPanel();
    private KSLabel exampleText2 = new KSLabel();    
    
    //view's data
    private ReqComponentInfo editedReqComp;
    ListItems listItemReqCompTypes;
    private List<ReqComponentTypeInfo> reqCompTypeList;
    private List<KSTextBox> reqCompWidgets = new ArrayList<KSTextBox>(); 
    private String selectedReqCompName;
    private boolean ruleTypeSelected = false;
    
    // model
    private Model<PrereqInfo> model;

    public ClauseEditorView(Controller controller) {
        super(controller, "Clause Editor View");
        super.initWidget(mainPanel);
        setupHandlers();
        setReqComponentList();
    }
    
    public void beforeShow() {
        
        getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
            public void onModelReady(Model<PrereqInfo> theModel) {
                model = theModel;                    
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });                  

        ruleTypeSelected = false;
        String selectedItem = compReqTypesList.getSelectedItem();
        if (selectedItem != null) {
            compReqTypesList.deSelectItem(compReqTypesList.getSelectedItem());
        }
        
        if (editedReqComp != null) {
            String reqTypeDesc = "";
            selectedReqCompName = "";
            for (ReqComponentTypeInfo reqTypeInfo : reqCompTypeList) {
                if (editedReqComp.getType().equals(reqTypeInfo.getId())) {
                    reqTypeDesc = reqTypeInfo.getDesc();
                }
                if (editedReqComp.getType().equals(reqTypeInfo.getId())) {
                    selectedReqCompName = reqTypeInfo.getName();
                }
            }
            
            displayReqComponentText(reqTypeDesc, reqCompDesc, editedReqComp.getReqCompField());            
        }
        redraw();
    }
    
    public void redraw() { 
      
        editorView.clear();
        examplePanel.clear();
        editorView.setStyleName("KS-Rules-FullWidth"); 
               
        KSLabel Heading = new KSLabel((editedReqComp == null ? "Add Rule" : "Edit Rule"));
        Heading.setStyleName("KS-Rules-FullWidth"); 
        Heading.setStyleName("KS-ReqMgr-Heading");
        editorView.add(Heading);
        
        //show requirement type
        HorizontalPanel reqTypePanel = new HorizontalPanel();        
                
        //requirement component label
        SimplePanel labelPanel = new SimplePanel();
        //labelPanel.setStyleName("KS-RuleEditor-LabelPanel");        
        KSLabel reqTypeLabel = new KSLabel("Rule Type");
        reqTypeLabel.setStyleName("KS-RuleEditor-SubHeading"); 
      //  labelPanel.add(nlHeading);
        labelPanel.setStyleName("KS-ReqCompEditor-EditFirstColumn");        
        labelPanel.add(reqTypeLabel);
        reqTypePanel.add(labelPanel);  
        
        //requirement component type name        
        compReqTypesList.setVisible(false);       
        if (editedReqComp == null) {
            compReqTypesList.deSelectItem("0");            
            compReqTypesList.setVisible(true);
            reqTypePanel.add(compReqTypesList);            
        } else {
            KSLabel reqCompType = new KSLabel(selectedReqCompName);
            reqCompType.setStyleName("KS-Rules-BoldText");
            reqTypePanel.add(reqCompType);
        }
        
        //show req. component details
        HorizontalPanel reqCompPanel = new HorizontalPanel();
        SimplePanel emptyPanel = new SimplePanel();
        emptyPanel.setStyleName("KS-ReqCompEditor-EditFirstColumn");        
        VerticalPanel reqCompDetailsPanel = new VerticalPanel(); 
        
        examplePanel.setSpacing(0);
        //examplePanel.setStyleName("KS-Rules-FullWidth");        
        KSLabel exampleText1 = new KSLabel("Example:");
        exampleText1.setStyleName("KS-RuleEditor-ExampleText1");
        examplePanel.add(exampleText1);        
        exampleText2.setStyleName("KS-RuleEditor-ExampleText2");
        examplePanel.add(exampleText2);
        reqCompDetailsPanel.add(examplePanel);                
        reqCompDetailsPanel.add(reqCompDesc);
            
        if (editedReqComp == null) {
            examplePanel.setVisible(false);
            reqCompDesc.setVisible(false);
        }              
        
        reqCompPanel.add(emptyPanel);
        reqCompPanel.add(reqCompDetailsPanel);
        
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
        
        editorView.add(reqTypePanel);
        editorView.add(reqCompPanel);
        editorView.add(tempPanelButtons);
        editorView.setStyleName("Content-Margin");
        mainPanel.clear();
        mainPanel.add(editorView);
        updateExampleContext();
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
                System.out.println("Here...2" + listItemReqCompTypes.getItemIds());                 
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
                //store values in the req. component info
                Object[] temp = model.getValues().toArray();
                PrereqInfo prereqInfo = (PrereqInfo)temp[0];                
                editedReqComp = new ReqComponentInfo();
                List<ReqCompFieldInfo> fields = editedReqComp.getReqCompField();
                fields = (fields == null)? new ArrayList<ReqCompFieldInfo>() : fields;
                // clears all the existing entries to make sure we are populating
                // the fields with the latest field values.
                fields.clear();
                System.out.println("DESC: " + editedReqComp.getDesc());
                //editedReqComp.setDesc("TEST");
                for (KSTextBox reqCompWidget : reqCompWidgets) {
                    System.out.println("Widget " + reqCompWidget.getName() + ", value: " + reqCompWidget.getText());
                    ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
                    fieldInfo.setId(reqCompWidget.getName());
                    fieldInfo.setValue(reqCompWidget.getText());
                    fields.add(fieldInfo);
                }
                editedReqComp.setReqCompField(fields);
                // TODO for now just add the new requirement component
                // to the first level StatmentVO
                StatementVO statementVO = prereqInfo.getStatementVO();
                ReqComponentVO reqComponentVO = new ReqComponentVO(editedReqComp);
                int index = Integer.valueOf(compReqTypesList.getSelectedItem());
                ReqComponentTypeInfo reqInfo = reqCompTypeList.get(index);
                reqComponentVO.setTypeDesc(reqInfo.getDesc());
                reqComponentVO.setDirty(true);
                statementVO.addReqComponentVO(reqComponentVO);
                getController().showView(PrereqViews.COMPLEX);                
            }
        });         
        submitReqComp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //store values in the req. component info
                List<ReqCompFieldInfo> fields = editedReqComp.getReqCompField();
                System.out.println("DESC: " + editedReqComp.getDesc());
                //editedReqComp.setDesc("TEST");
                for (KSTextBox reqCompWidget : reqCompWidgets) {
                    System.out.println("Widget " + reqCompWidget.getName() + ", value: " + reqCompWidget.getText());
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
                            System.out.println("Field " + field.getId() + ", value: " + field.getValue());
                            if (reqCompWidget.getName().equals(field.getId())) {
                                field.setValue(reqCompWidget.getText());
                            }
                        }
                    }
                }
                
                getController().showView(PrereqViews.COMPLEX);                
            }
        });                
        compReqTypesList.addSelectionChangeHandler(new SelectionChangeHandler() {  
             public void onSelectionChange(KSSelectItemWidgetAbstract w) {              
                 ruleTypeSelected = true;   
                 examplePanel.setVisible(true);
                 reqCompDesc.setVisible(true);               
                 List<String> ids = w.getSelectedItems();
                 int index = Integer.valueOf(ids.get(0));
                 ReqComponentTypeInfo reqInfo = reqCompTypeList.get(index);
                 displayReqComponentText(reqInfo.getDesc(), reqCompDesc, null);                  
             }});
        
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
            //System.out.println(tokens[i]); 
            String tag = tokens[i].replaceFirst("reqCompFieldType.", "");
            tag = tag.replaceFirst("reqCompFiledType.", "");
            //System.out.println("TAG: " + tag);
            if ((tag.equals("requiredCount")) || (tag.equals("gpa")) || (tag.equals("clu"))) {
                KSTextBox valueWidget = new KSTextBox();
                //final ReqCompFieldInfo reqCompFieldInfo = getReqCompFieldInfo(fields, tokens[i]);
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
                            if (fieldValueCount > 0 && 
                                    newFieldValue.toString().trim().length() > 0) {
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
    
    /*
    private void setSpecificFieldValue(List<ReqCompFieldInfo> fields, String key, String value) {
        ReqCompFieldInfo reqCompFieldInfo = getReqCompFieldInfo(fields, key);
        
        if (reqCompFieldInfo == null) {
            System.out.println("Fields null");
        }
        
        reqCompFieldInfo.setValue(value);
    }*/
    
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
            RequirementsService.Util.getInstance().getNaturalLanguageForReqComponent(editedReqComp, "KUALI.EXAMPLE", new AsyncCallback<String>() {
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
    
    public ReqComponentInfo getEditedReqComp() {
        return editedReqComp;
    }

    public void setEditedReqComp(ReqComponentInfo editedReqComp) {
        this.editedReqComp = editedReqComp;
    }       
}
