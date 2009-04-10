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
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.ui.requirements.client.RequirementsEntryPoint;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class ComplexView extends ViewComposite {

    //displayed widgets
    private Panel mainPanel = new SimplePanel();
    private KSButton btnEditClause = new KSButton("Edit Clause");
    private KSButton btnSearchView = new KSButton("Search");
    private KSButton btnRetrieveStatement = new KSButton("Retrieve Rule");
    private KSButton btnSimpleView = new KSButton("Simple View");
    private KSLabel selectedClause = new KSLabel();
    private TreeTable ruleTable = new TreeTable();
    
    //view data
    private Model<PrereqInfo> model;
    private ReqComponentInfo selectedReqComp;

    public ComplexView(Controller controller) {
        super(controller, "Complex View");
        super.initWidget(mainPanel);
        Panel testPanel = new VerticalPanel();
        testPanel.add(new Label("Complex View"));
        testPanel.add(btnEditClause);
        testPanel.add(btnSearchView);
        testPanel.add(btnRetrieveStatement);
        testPanel.add(ruleTable);
        testPanel.add(selectedClause);
        testPanel.add(btnSimpleView);
        mainPanel.add(testPanel);
    }
    
    public void beforeShow() { 
  
    //}
    
    
    //@Override
    //protected void onLoad() {
        if (model == null) {
            getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
                public void onModelReady(Model<PrereqInfo> theModel) {
                    printModel(theModel);
                    model = theModel;                    
                    setupHandlers();
                    redraw();
                }

                public void onRequestFail(Throwable cause) {
                    throw new RuntimeException("Unable to connect to model", cause);
                }
            });
        }
        else
        {
            redraw();
        }
    }

    private void setupHandlers() {
        btnSearchView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.SEARCH);
            }
        });
        btnSimpleView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.SIMPLE);
            }
        });
        btnEditClause.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.CLAUSE_EDITOR);
            }
        });
        btnRetrieveStatement.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RequirementsService.Util.getInstance().getStatementVO(RequirementsEntryPoint.testCluId, "kuali.luStatementType.createCourseAcademicReadiness", new AsyncCallback<StatementVO>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        caught.printStackTrace();
                    }
                    
                    public void onSuccess(final StatementVO statementVO) {
                        PrereqInfo prepreqInfo = model.get(RequirementsEntryPoint.testCluId);
                        prepreqInfo.setStatementVO(statementVO);                                               
                        model.update(prepreqInfo);
                        getController().showView(PrereqViews.COMPLEX);
                    } 
                });
                redraw();
            } 
        });
        model.addModelChangeHandler(new ModelChangeHandler<PrereqInfo>() {
            public void onModelChange(ModelChangeEvent<PrereqInfo> event) {
                redraw();
            }
        });
        
        ruleTable.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
               // ruleTable.get
            }
        });
        
               
        ruleTable.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // TODO zzraly - THIS METHOD NEEDS JAVADOCS
                
                Cell cell = ruleTable.getCellForEvent(event);
                NodeWidget widget = (NodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());
                
                if (widget != null) {
                    ReqComponentVO clause = (ReqComponentVO) widget.getNode().getUserObject();
                    selectedReqComp = clause.getReqComponentInfo();
                    selectedClause.setText("SELECTED: " + clause.getTypeDesc());
                }
            }

         });
    }
       
    private void redraw() {
        PrereqInfo prereqInfo = model.get(RequirementsEntryPoint.testCluId);     
        ruleTable.clear();
        Node tree = null;
        if (prereqInfo != null) {
            tree = prereqInfo.getStatementTree();
            if (tree != null) {
                ruleTable.buildTable(tree);
            }
        }
    }
    
    public static void printModel(Model model) {
        Collection<PrereqInfo> values = model.getValues();
        
        System.out.println("Model objects:");
        for (PrereqInfo t : values)
            System.out.println("object: " + t.getCluId() + ", " + t.getId());
    }

    public ReqComponentInfo getSelectedReqComp() {       
        return selectedReqComp;
    }
    
}
