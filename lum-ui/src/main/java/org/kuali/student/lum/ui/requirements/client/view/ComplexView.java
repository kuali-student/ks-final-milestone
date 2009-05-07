package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class ComplexView extends ViewComposite {

    //view's widgets
    private Panel mainPanel = new SimplePanel();
    VerticalPanel complexView = new VerticalPanel();
    private KSLabel linkToSimpleView = new KSLabel("Simple Rules");    
    private KSButton btnAddRule = new KSButton("+ Add new rule");
    private KSButton btnAddAND = new KSButton("AND");
    private KSButton btnAddOR = new KSButton("OR");
    private KSButton btnAddToGroup = new KSButton("Add to group");
    private KSButton btnUndo = new KSButton("Undo");
    private KSButton btnRedo = new KSButton("Redo");
    private KSButton btnDelete = new KSButton("Delete");
    private KSButton btnDuplicateRule = new KSButton("Duplicate Rule");
    private KSButton btnMoveRuleDown = new KSButton();
    private KSButton btnMoveRuleUp = new KSButton();
    private KSButton btnSaveRule = new KSButton("Save");    
    private KSLabel naturalLanguage = new KSLabel();
    private RuleTable ruleTable = new RuleTable();
    //private ClickHandler ruleTableClickHandler = null;
    private ClickHandler ruleTableSelectionHandler = null;
    private ClickHandler ruleTableToggleClickHandler = null;
    private ClickHandler ruleTableEditClauseHandler = null;
    
    //view's data
    private Model<PrereqInfo> model;
    private boolean isInitialized = false;

    public ComplexView(Controller controller) {
        super(controller, "Complex View");
        super.initWidget(mainPanel);                
    }
    
    public void beforeShow() {

        if (isInitialized == false) {
            setupHandlers();            
        }            
            
        getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
            public void onModelReady(Model<PrereqInfo> theModel) {
                model = theModel;    
                
                if (isInitialized == false) {                    
                    model.addModelChangeHandler(new ModelChangeHandler<PrereqInfo>() {
                        public void onModelChange(ModelChangeEvent<PrereqInfo> event) {
                            //redraw();
                        }
                    });                          
                }
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        }); 

        isInitialized = true;
        
        redraw();
    }

    private void setupHandlers() {
        
        linkToSimpleView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.SIMPLE);
            }
        });         
               
        ruleTableToggleClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = ruleTable.getCellForEvent(event); 
                if (cell == null) {
                    System.out.println("Cell is NULL");
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());                 
                Object userObject = widget.getNode().getUserObject();
                
                if (userObject instanceof StatementVO) {
                    StatementVO statementVO = (StatementVO) userObject;
                    if (statementVO != null) {
                        StatementOperatorTypeKey currentOp =
                            (statementVO.getLuStatementInfo().getOperator());
                        StatementOperatorTypeKey newOp = null;
                        if (currentOp == StatementOperatorTypeKey.AND) {
                            newOp = StatementOperatorTypeKey.OR;
                        } else {
                            newOp = StatementOperatorTypeKey.AND;
                        }
                        statementVO.getLuStatementInfo().setOperator(newOp);
                    }
                }
            }
        };
        
        ruleTableSelectionHandler = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                              
                Cell cell = ruleTable.getCellForEvent(event);
                if (cell == null) {
                    System.out.println("Cell is NULL 0");
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());
                Object userObject = widget.getNode().getUserObject();
                if (userObject instanceof StatementVO) { 
                    StatementVO statementVO = (StatementVO) userObject;
                    statementVO.setCheckBoxOn(!statementVO.isCheckBoxOn());
                } else if (userObject instanceof ReqComponentVO) {                    
                    ReqComponentVO reqComponentVO = (ReqComponentVO) userObject;
                    reqComponentVO.setCheckBoxOn(!reqComponentVO.isCheckBoxOn());
                }
                updateRulesTable();
            }
        };    
        ruleTable.addClickHandler(ruleTableSelectionHandler);
        
     /*   ruleTableClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                Cell cell = ruleTable.getCellForEvent(event); 
                if (cell == null) {
                    System.out.println("Cell is NULL 1");
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());                                  
                Object userObject = widget.getNode().getUserObject();
                if (userObject instanceof ReqComponentVO) {
                    ReqComponentVO rule = (ReqComponentVO) userObject;
                    rule.setCheckBoxOn(!rule.isCheckBoxOn());                        
                } else {
                    StatementVO statementVO = (StatementVO) userObject;
                    statementVO.setCheckBoxOn(!statementVO.isCheckBoxOn());                                               
                }
                updateRulesTable();
            }

        };  */
        
        ruleTableEditClauseHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = ruleTable.getCellForEvent(event); 
            
                if (cell == null) {
                    System.out.println("Cell is NULL 2");
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());                 
                Object userObject = widget.getNode().getUserObject();   
                if (userObject instanceof ReqComponentVO) {
                    final ReqComponentVO rule = (ReqComponentVO) userObject;
                
                    getController().requestModel(ReqComponentVO.class, new ModelRequestCallback<ReqComponentVO>() {
                        public void onModelReady(Model<ReqComponentVO> theModel) {
                            RulesUtilities.clearModel(theModel);
                            theModel.add(rule);                            
                        }
        
                        public void onRequestFail(Throwable cause) {
                            throw new RuntimeException("Unable to connect to model", cause);
                        }
                    });                
                                    
                    getController().showView(PrereqViews.CLAUSE_EDITOR);
                }
            }
        };
        
        btnAddRule.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {      
                
                getController().requestModel(ReqComponentVO.class, new ModelRequestCallback<ReqComponentVO>() {
                    public void onModelReady(Model<ReqComponentVO> theModel) {
                        if (theModel != null) {
                            RulesUtilities.clearModel(theModel);
                        }                    
                      //  redraw();
                    }

                    public void onRequestFail(Throwable cause) {
                        throw new RuntimeException("Unable to connect to model", cause);
                    }
                });                  
                
                getController().showView(PrereqViews.CLAUSE_EDITOR);
            }
        });                
        
        btnMoveRuleDown.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                /*
                if (selectedReqCompVO != null) {
                    Object[] temp = model.getValues().toArray();
                    PrereqInfo prereqInfo = (PrereqInfo)temp[0];
                    StatementVO enclosingStatementVO = 
                        prereqInfo.getStatementVO().getEnclosingStatementVO(
                                prereqInfo.getStatementVO(), selectedReqCompVO);
                    enclosingStatementVO.shiftReqComponent("RIGHT", selectedReqCompVO);
                    redraw();
                } */
            }
        });

        btnMoveRuleUp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                /*
                if (selectedReqCompVO != null) {
                    Object[] temp = model.getValues().toArray();
                    PrereqInfo prereqInfo = (PrereqInfo)temp[0];
                    StatementVO enclosingStatementVO = 
                        prereqInfo.getStatementVO().getEnclosingStatementVO(
                                prereqInfo.getStatementVO(), selectedReqCompVO);
                    enclosingStatementVO.shiftReqComponent("LEFT", selectedReqCompVO);
                    redraw();
                } */
            }
        });
        
        btnAddOR.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Object[] temp = model.getValues().toArray();
                PrereqInfo prereqInfo = (PrereqInfo)temp[0];
                prereqInfo.insertOR();
                redraw();
            }
        });
        
        btnAddAND.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Object[] temp = model.getValues().toArray();
                PrereqInfo prereqInfo = (PrereqInfo)temp[0];
                prereqInfo.insertAND();
                redraw();
            }
        });
        
        btnDelete.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Object[] temp = model.getValues().toArray();
                PrereqInfo prereqInfo = (PrereqInfo)temp[0];
                prereqInfo.deleteItem();
                redraw();
            }
        });
    }
       
    private void redraw() {
        complexView.clear();
        
        HorizontalPanel tempPanel = new HorizontalPanel();
        tempPanel.setStyleName("KS-Rules-FullWidth");
        KSLabel preReqHeading = new KSLabel("Manage Prerequisite Rules");
        preReqHeading.setStyleName("KS-ReqMgr-Heading");
        tempPanel.add(preReqHeading);
        tempPanel.add(linkToSimpleView);
        linkToSimpleView.addStyleName("KS-Rules-Link-Right");
        complexView.add(tempPanel);
        
        //setup top rules table buttons
        HorizontalPanel topButtonsPanel = new HorizontalPanel();
        btnAddRule.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnAddRule);
        SimplePanel spacer1 = new SimplePanel();
        spacer1.setWidth("30px");
        topButtonsPanel.add(spacer1);       
        btnAddAND.addStyleName("KS-Rules-Tight-Grey-Button");
        topButtonsPanel.add(btnAddAND);   
        btnAddOR.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnAddOR);        
        btnAddToGroup.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnAddToGroup);
        SimplePanel spacer2 = new SimplePanel();
        spacer2.setWidth("30px");
        topButtonsPanel.add(spacer2);
        btnUndo.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnUndo);    
        btnRedo.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnRedo);
        SimplePanel spacer3 = new SimplePanel();
        spacer3.setWidth("30px");
        topButtonsPanel.add(spacer3);         
        btnDelete.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnDelete);        
        complexView.add(topButtonsPanel);        
        
        SimplePanel verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        complexView.add(verticalSpacer);        
        
        HorizontalPanel tempPanel2 = new HorizontalPanel();
        HorizontalPanel tableButtonsPanel = new HorizontalPanel();
        VerticalPanel arrowButtonsPanel = new VerticalPanel();
        tempPanel2.add(ruleTable);
        arrowButtonsPanel.add(btnMoveRuleUp);
        btnMoveRuleUp.setStyleName("KS-RuleTable-UpArrow");
        arrowButtonsPanel.add(btnMoveRuleDown);
        btnMoveRuleDown.setStyleName("KS-RuleTable-DownArrow");
        tableButtonsPanel.add(arrowButtonsPanel);
        tempPanel2.add(tableButtonsPanel);
        complexView.add(tempPanel2);
               
        SimplePanel verticalSpacer2 = new SimplePanel();
        verticalSpacer2.setHeight("30px");
        complexView.add(verticalSpacer2);             
        
        //RulesUtilities ruleUtil = new RulesUtilities();
        //RulesUtilities.RowBreak rowBreak = ruleUtil.new RowBreak();        
        //complexView.add(rowBreak);
        KSLabel nlHeading = new KSLabel("Natural Language");
        nlHeading.setStyleName("KS-ReqMgr-SubHeading"); 
        complexView.add(nlHeading);        
        complexView.add(naturalLanguage); 
        
        SimplePanel verticalSpacer3 = new SimplePanel();
        verticalSpacer3.setHeight("20px");
        complexView.add(verticalSpacer3);          
        
        btnSaveRule.addStyleName("KS-Rules-Standard-Button");        
        complexView.add(btnSaveRule);         
        complexView.setStyleName("Content-Margin");
        mainPanel.clear();
        mainPanel.add(complexView);        
        
        updateRulesTable();        
        updateNaturalLanguage();
    }
    
    private void updateRulesTable() {
        PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);               

        btnAddAND.setEnabled(prereqInfo.statementVOIsGroupAble());
        btnAddOR.setEnabled(prereqInfo.statementVOIsGroupAble());  
        btnAddToGroup.setEnabled(false);  
        btnUndo.setEnabled(false);  
        btnRedo.setEnabled(false);          
        btnDuplicateRule.setEnabled(false);        
        btnAddRule.setEnabled(true);
        btnDelete.setEnabled(prereqInfo.statementVOIsDegroupAble());
        btnDuplicateRule.setEnabled(false); 
        btnSaveRule.setEnabled(false);
        ruleTable.clear();
        if (prereqInfo != null) {
            System.out.println("statement is: " +
                    prereqInfo.getStatementVO().getPrintableStatement());
            Node tree = prereqInfo.getStatementTree();
            
            if (tree != null) {
                ruleTable.buildTable(tree);
                ruleTable.addCheckBoxHandler(ruleTableSelectionHandler);
                ruleTable.addToggleHandler(ruleTableToggleClickHandler);
                ruleTable.addEditClauseHandler(ruleTableEditClauseHandler);                
            }
        }        
    }        
    
    private void updateNaturalLanguage() {
                
        naturalLanguage.setText("");      
        /*RequirementsService.Util.getInstance().getNaturalLanguageForLuStatementInfo(RulesUtilities.getPrereqInfoModelObject(model).getCluId(),
                                RulesUtilities.getPrereqInfoModelObject(model).getStatementVO().getLuStatementInfo(), "KUALI.CATALOG", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                caught.printStackTrace();
            }
            
            public void onSuccess(final String statementNaturalLanguage) {                               
                naturalLanguage.setText(statementNaturalLanguage);  
            } 
        });*/
    }           
}
