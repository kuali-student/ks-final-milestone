package org.kuali.student.lum.ui.requirements.client.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.table.TreeTable;
import org.kuali.student.lum.ui.requirements.client.controller.LumApplication;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ComplexView extends ViewComposite {

    private Panel mainPanel = new SimplePanel();
    private KSButton btnEditClause = new KSButton("Edit Clause");
    private KSButton btnRetrieveStatement = new KSButton("Retrieve Rule");
    private TreeTable ruleTable = new TreeTable();
    private Model<PrereqInfo> model;

    public ComplexView(Controller controller) {
        super(controller, "Complex View");
        super.initWidget(mainPanel);
        Panel testPanel = new VerticalPanel();
        testPanel.add(new Label("Complex View"));
        testPanel.add(btnEditClause);
        testPanel.add(btnRetrieveStatement);
        testPanel.add(ruleTable);
        mainPanel.add(testPanel);
    }
    
    @Override
    protected void onLoad() {
        if (model == null) {
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
    }



    private void setupHandlers() {
        btnEditClause.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.CLAUSE_EDITOR);
            }
        });
        btnRetrieveStatement.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RequirementsService.Util.getInstance().getStatementVO(LumApplication.testCluId, 
                        "Pre Req", new AsyncCallback<StatementVO>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        caught.printStackTrace();
                    }
                    
                    public void onSuccess(final StatementVO statementVO) {
                        PrereqInfo prepreqInfo = 
                            model.get(LumApplication.testCluId);
                        prepreqInfo.setStatementVO(statementVO);
                        model.update(prepreqInfo);
                        getController().showView(PrereqViews.COMPLEX);
                    } 
                });
            }
        });
        model.addModelChangeHandler(new ModelChangeHandler<PrereqInfo>() {
            public void onModelChange(ModelChangeEvent<PrereqInfo> event) {
                redraw();
            }
        });
    }
    
    private void redraw() {
        PrereqInfo prereqInfo = 
            model.get(LumApplication.testCluId);
        if (prereqInfo != null) {
            ruleTable.buildTable(prereqInfo.getStatementTree());
        }
    }

}
