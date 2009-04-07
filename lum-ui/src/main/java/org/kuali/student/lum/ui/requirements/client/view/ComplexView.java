package org.kuali.student.lum.ui.requirements.client.view;

import java.util.Collection;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.table.TreeTable;
import org.kuali.student.lum.ui.requirements.client.RequirementsEntryPoint;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ComplexView extends ViewComposite {

    private Panel mainPanel = new SimplePanel();
    private KSButton btnEditClause = new KSButton("Edit Clause");
    private KSButton btnSearchView = new KSButton("Search");
    private KSButton btnRetrieveStatement = new KSButton("Retrieve Rule");
    private KSButton btnSimpleView = new KSButton("Simple View");
    private TreeTable ruleTable = new TreeTable();
    private Model<PrereqInfo> model;

    public ComplexView(Controller controller) {
        super(controller, "Complex View");
        super.initWidget(mainPanel);
        Panel testPanel = new VerticalPanel();
        testPanel.add(new Label("Complex View"));
        testPanel.add(btnEditClause);
        testPanel.add(btnSearchView);
        testPanel.add(btnRetrieveStatement);
        testPanel.add(ruleTable);
        testPanel.add(btnSimpleView);
        mainPanel.add(testPanel);
    }
    
    @Override
    protected void onLoad() {
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
                /*
                RequirementsService.Util.getInstance().getStatementVO(LumApplication.testCluId, "Pre Req", new AsyncCallback<LuStatementInfo>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        caught.printStackTrace();
                    }
                    
                    public void onSuccess(final LuStatementInfo statementVO) {
                        PrereqInfo prepreqInfo = model.get(LumApplication.testCluId);
                        prepreqInfo.setStatementVO(statementVO);
                        model.update(prepreqInfo);
                        getController().showView(PrereqViews.COMPLEX);
                    } 
                }); */
                redraw();
            } 
        });
        model.addModelChangeHandler(new ModelChangeHandler<PrereqInfo>() {
            public void onModelChange(ModelChangeEvent<PrereqInfo> event) {
                redraw();
            }
        });
    }
    
    private void redraw() {
        PrereqInfo prereqInfo = model.get(RequirementsEntryPoint.testCluId);     
        if (prereqInfo != null) {
            System.out.println("statement tree: " + prereqInfo.getStatementTree());
            ruleTable.buildTable(prereqInfo.getStatementTree());
        }
    }
    
    public static void printModel(Model model) {
        Collection<PrereqInfo> values = model.getValues();
        
        System.out.println("Model objects:");
        for (PrereqInfo t : values)
            System.out.println("object: " + t.getCluId() + ", " + t.getId());
    }
}
