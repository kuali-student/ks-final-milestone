package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.List;

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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ClauseEditorView extends ViewComposite {

    public enum reqCompFieldDefinitions {
        TODO
    }
    
    //view's widgets
    private Panel mainPanel = new SimplePanel();
    private KSButton btnToComplexView = new KSButton("Cancel");
    private KSButton submitClause = new KSButton("Submit");
    KSListBox compReqTypesList = new KSListBox();
    HorizontalPanel reqCompDesc = new HorizontalPanel();
    
    //view's data
    private ReqComponentInfo editedReqComp;
    ListItems listItemReqCompTypes;
    private List<ReqComponentTypeInfo> reqCompTypeList;
    private List<KSTextBox> reqCompWidgets = new ArrayList<KSTextBox>(); 

    public ClauseEditorView(Controller controller) {
        super(controller, "Clause Editor View");
        super.initWidget(mainPanel);
        Panel testPanel = new VerticalPanel();
        testPanel.add(new KSLabel("Clause Editor View"));
        testPanel.add(compReqTypesList);
        testPanel.add(reqCompDesc);
        testPanel.add(submitClause);
        testPanel.add(btnToComplexView);
        mainPanel.add(testPanel);
        setupHandlers();
        layoutWidgets();
    }
    
    public void beforeShow() {        
        if (editedReqComp != null) {

            compReqTypesList.setVisible(false);
            
            String reqTypeDesc = "";
            for (ReqComponentTypeInfo reqTypeInfo : reqCompTypeList) {
                if (editedReqComp.getType().equals(reqTypeInfo.getId())) {
                    reqTypeDesc = reqTypeInfo.getDesc();
                }
            }
         
            displayReqComponentText(reqTypeDesc, reqCompDesc, editedReqComp.getReqCompField());              
        }                 
    }
    
    public void layoutWidgets() {        
        reqCompDesc.setSpacing(5);

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
        btnToComplexView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.COMPLEX);
            }
        });
        submitClause.addClickHandler(new ClickHandler() {
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
                 List<String> ids = w.getSelectedItems();
                 int index = Integer.valueOf(ids.get(0));
                 ReqComponentTypeInfo reqInfo = reqCompTypeList.get(index);
                 displayReqComponentText(reqInfo.getDesc(), reqCompDesc, null);                  
             }});          
    }
    
    private void displayReqComponentText(String reqInfoDesc, Panel parentWidget, List<ReqCompFieldInfo> fields) {
        
        parentWidget.clear();
        
        String[] tokens = reqInfoDesc.split("[<>]");
        boolean isValueWidget = true;
        for (int i = 0; i < tokens.length; i++) {
            isValueWidget = !isValueWidget;
            //this token is a text only
            if (isValueWidget == false) {
                KSLabel text = new KSLabel(tokens[i]);
                parentWidget.add(text);
                continue;
            }
            
            //TODO use ENUMs and Switch()
            //System.out.println(tokens[i]); 
            String tag = tokens[i].replaceFirst("reqCompFieldType.", "");
            tag = tag.replaceFirst("reqCompFiledType.", "");
            //System.out.println("TAG: " + tag);
            if ((tag.equals("requiredCount")) || (tag.equals("gpa")) || (tag.equals("clu"))) {
                KSTextBox valueWidget = new KSTextBox();
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tokens[i]);
                valueWidget.setText(getSpecificFieldValue(fields, tokens[i]));
                valueWidget.setWidth("50px");
                parentWidget.add(valueWidget);
                continue;                                
            }
            if (tag.equals("cluSet")) {
                KSTextBox valueWidget = new KSTextBox();
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tokens[i]);                
                valueWidget.setText(getSpecificFieldValue(fields, tokens[i]));
                valueWidget.setWidth("250px");
                parentWidget.add(valueWidget);
                continue;                                
            }       
        }                       
    }
    
    private String getSpecificFieldValue(List<ReqCompFieldInfo> fields, String key) {
        
        //if we are showing new req. comp. type then show empty fields
        if (fields == null) {
            System.out.println("Fields null");
            return "";
        }
    
        for (ReqCompFieldInfo fieldInfo : fields) {
            if (fieldInfo.getId().equals(key)) {
                return fieldInfo.getValue();
            }
        }
        
        System.out.println("DID NOT FIND GIVEN KEY ??? key: " + key);
        return "";
    }

    public ReqComponentInfo getEditedReqComp() {
        return editedReqComp;
    }

    public void setEditedReqComp(ReqComponentInfo editedReqComp) {
        this.editedReqComp = editedReqComp;
    }       
}
