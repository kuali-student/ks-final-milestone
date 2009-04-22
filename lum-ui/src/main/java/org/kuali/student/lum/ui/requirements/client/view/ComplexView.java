package org.kuali.student.lum.ui.requirements.client.view;

import java.util.Collection;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.NodeWidget;
import org.kuali.student.common.ui.client.widgets.table.TreeTable;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
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
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class ComplexView extends ViewComposite {

    //view's widgets
    private Panel mainPanel = new SimplePanel();
    private KSLabel linkToSimpleView = new KSLabel("Simple Rules");    
    private KSButton btnAddClause = new KSButton("Add Clause");
    private KSButton btnEditClause = new KSButton("Edit Clause");
    private KSButton btnDeleteClause = new KSButton("Delete Clause");
    private KSButton btnDuplicateClause = new KSButton("Duplicate Clause");    
    private KSLabel naturalLanguage = new KSLabel();
    private TreeTable ruleTable = new TreeTable();    
    
    //view's data
    private Model<PrereqInfo> model;
    private ReqComponentInfo selectedReqComp;
    private Widget selectedTableCellWidget = null;
    //private boolean reqComponentSelected = false;

    public ComplexView(Controller controller) {
        super(controller, "Complex View");
        super.initWidget(mainPanel);
    }
    
    public void beforeShow() {
        if (model != null) {
            redraw();
            return;
        }
        
        getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
            public void onModelReady(Model<PrereqInfo> theModel) {
                model = theModel;                    
                setupHandlers();
                redraw();
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        }); 
    }

    private void setupHandlers() {
        linkToSimpleView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.SIMPLE);
            }
        });
        btnAddClause.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.CLAUSE_EDITOR);
            }
        });     
        btnEditClause.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.CLAUSE_EDITOR);
            }
        });               
        model.addModelChangeHandler(new ModelChangeHandler<PrereqInfo>() {
            public void onModelChange(ModelChangeEvent<PrereqInfo> event) {
                redraw();
            }
        });                       
        ruleTable.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = ruleTable.getCellForEvent(event);
                
                if (selectedTableCellWidget != null) {
                    selectedTableCellWidget.setStyleName("KS-ReqComp-DeSelected");
                    selectedTableCellWidget = null;
                }                
                
                NodeWidget widget = (NodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());                 
                if (widget == null) {
                   // reqComponentSelected = false;
                    selectedReqComp = null;
                    btnAddClause.setEnabled(true);
                    btnEditClause.setEnabled(false);                    
                } else {
                    selectedTableCellWidget = widget;
                    widget.setStyleName("KS-ReqComp-Selected");
                    ReqComponentVO clause = (ReqComponentVO) widget.getNode().getUserObject();                    
                    selectedReqComp = clause.getReqComponentInfo();
                    updateNaturalLanguage();
                    btnAddClause.setEnabled(false);
                    btnEditClause.setEnabled(true);                    
                   // reqComponentSelected = true;
                }
            }

         });
    }
       
    private void redraw() {
        VerticalPanel complexView = new VerticalPanel();
        HorizontalPanel tempPanel = new HorizontalPanel();
        tempPanel.setStyleName("KS-Rules-FullWidth");
        KSLabel preReqHeading = new KSLabel("Pre-requisite Rule");
        preReqHeading.setStyleName("KS-ReqMgr-Heading");
        tempPanel.add(preReqHeading);
        tempPanel.add(linkToSimpleView);
        linkToSimpleView.addStyleName("KS-Rules-Link-Right");
        complexView.add(tempPanel);        
        complexView.add(ruleTable);
        
        HorizontalPanel tempPanelButtons1 = new HorizontalPanel();
        tempPanelButtons1.setStyleName("KS-Rules-FullWidth");        
        tempPanelButtons1.add(btnAddClause);
        btnAddClause.setStyleName("KS-Rules-Standard-Button");        
        tempPanelButtons1.add(btnEditClause);
        btnEditClause.setStyleName("KS-Rules-Standard-Button"); 
        HorizontalPanel tempPanelButtons2 = new HorizontalPanel(); 
        tempPanelButtons2.setStyleName("KS-Rules-FullWidth");         
        tempPanelButtons2.add(btnDeleteClause);
        btnDeleteClause.setStyleName("KS-Rules-Standard-Button");  
        tempPanelButtons2.add(btnDuplicateClause);
        btnDuplicateClause.setStyleName("KS-Rules-Standard-Button");          
        complexView.add(tempPanelButtons1);
        complexView.add(tempPanelButtons2);
        
        btnAddClause.setEnabled(false);
        btnEditClause.setEnabled(false);  
        btnDeleteClause.setEnabled(false);
        btnDuplicateClause.setEnabled(false);
        if (selectedReqComp == null) {
            btnAddClause.setEnabled(true);
        } else {
            btnAddClause.setEnabled(true);            
            btnEditClause.setEnabled(true);
            btnDeleteClause.setEnabled(true);
            btnDuplicateClause.setEnabled(true);
        }
        
        RulesUtilities ruleUtil = new RulesUtilities();
        RulesUtilities.RowBreak rowBreak = ruleUtil.new RowBreak();
        complexView.add(rowBreak);
        KSLabel nlHeading = new KSLabel("Natural Language");
        nlHeading.setStyleName("KS-ReqMgr-SubHeading"); 
        complexView.add(nlHeading);        
        complexView.add(naturalLanguage);        
        complexView.setStyleName("Content-Margin");
        mainPanel.clear();
        mainPanel.add(complexView);        

        Object[] temp = model.getValues().toArray();
        PrereqInfo prereqInfo = (PrereqInfo)temp[0];                

        ruleTable.clear();
        if (prereqInfo != null) {
            Node tree = prereqInfo.getStatementTree();
            if (tree != null) {              
                ruleTable.buildTable(tree);
            }
        }
        updateNaturalLanguage();        
    }
    
    private void updateNaturalLanguage() {         
        if (selectedReqComp != null) {        
            RequirementsService.Util.getInstance().getNaturalLanguageForReqComponent(selectedReqComp, "KUALI.CATALOG", new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                    System.out.println(caught.getMessage());
                    caught.printStackTrace();
                }
                
                public void onSuccess(final String nl) {
                    naturalLanguage.setText(nl);
                } 
            });          
        }
    }    

    public ReqComponentInfo getSelectedReqComp() {       
        return selectedReqComp;
    }   
}
