package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ClauseEditorView extends ViewComposite {

    public enum reqCompFieldDefinitions {
        TODO
    }
    
    private Panel mainPanel = new SimplePanel();
    private KSButton btnToComplexView = new KSButton("Complex View");
    KSListBox compReqList = new KSListBox();
    private String statementType = "kuali.luStatementType.prereqAcademicReadiness"; 
    ListItems listItemClus;
    HorizontalPanel reqCompDesc = new HorizontalPanel();
    private List<ReqComponentTypeInfo> reqCompTypeList;

    public ClauseEditorView(Controller controller) {
        super(controller, "Clause Editor View");
        super.initWidget(mainPanel);
        Panel testPanel = new VerticalPanel();
        testPanel.add(new KSLabel("Clause Editor View"));
        testPanel.add(compReqList);
        testPanel.add(reqCompDesc);
        testPanel.add(btnToComplexView);
        mainPanel.add(testPanel);
        setupHandlers();
        layoutWidgets();
    }
    
    public void layoutWidgets() {        

        reqCompDesc.setSpacing(5);
        
        RequirementsService.Util.getInstance().getReqComponentTypesForLuStatementType(statementType, new AsyncCallback<List<ReqComponentTypeInfo>>() {
            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(final List<ReqComponentTypeInfo> reqComponentTypeInfoList) {
                reqCompTypeList = reqComponentTypeInfoList;
                listItemClus = new ListItems() {                    
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
                compReqList.setListItems(listItemClus);
            }
        });
    }

    private void setupHandlers() {
        btnToComplexView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.COMPLEX);
            }
        });
        
        compReqList.addSelectionChangeHandler(new SelectionChangeHandler() {  
             public void onSelectionChange(KSSelectItemWidgetAbstract w) {  
                 List<String> ids = w.getSelectedItems();
                 int index = Integer.valueOf(ids.get(0));
                 ReqComponentTypeInfo reqInfo = reqCompTypeList.get(index);
                 displayReqComponentText(reqInfo.getDesc(), reqCompDesc);
                 
                 /*
                 StringBuffer sb = new StringBuffer("You Have selected:\n");  
                 for (String item:ids){  
                     sb.append(listItemClus.getItemText(item)).append("\n");  
                 }  
                 Window.alert(sb.toString()); */                     
             }});          
    }
    
    private void displayReqComponentText(String reqInfoDesc, Panel parentWidget) {
        
        parentWidget.clear();
        
        String[] tokens = reqInfoDesc.split("[<>]");
        boolean widget = true;
        for (int i = 0; i < tokens.length; i++) {
            widget = !widget;
            //this token is a text only
            if (widget == false) {
                KSLabel text = new KSLabel(tokens[i]);
                parentWidget.add(text);
                continue;
            }
            
            //TODO use ENUMs and Switch()
            //System.out.println(tokens[i]);
            if ((tokens[i].equals("value")) || (tokens[i].equals("Grade"))) {
                KSTextBox value = new KSTextBox();
                value.setWidth("50px");
                parentWidget.add(value);
                continue;                                
            }
            if ((tokens[i].equals("Course")) || (tokens[i].equals("courses"))) {
                KSTextBox value = new KSTextBox();
                value.setWidth("250px");
                parentWidget.add(value);
                continue;                                
            }            
        }                       
    }
}
