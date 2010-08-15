package org.kuali.student.lum.program.client.requirements;

import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProgramRequirementsSummaryView extends VerticalSectionView {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);
    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);

    LayoutController parentController;

    LinkedHashMap<StatementTypeInfo, ProgramRequirementInfo> rules = new LinkedHashMap<StatementTypeInfo, ProgramRequirementInfo>();
    StatementTreeViewInfo ruleTree;

    public ProgramRequirementsSummaryView(LayoutController parentController, Enum<?> viewEnum, String name, String modelId, StatementTreeViewInfo ruleTree) {
        super(viewEnum, name, modelId);

        this.parentController = parentController;
        this.ruleTree = ruleTree;

        //retrieve available program requirement types and display them
        statementRpcServiceAsync.getStatementTypesForStatementType("kuali.luStatementType.program", new AsyncCallback<List<StatementTypeInfo>>() {
            @Override
            public void onFailure(Throwable caught) {
	            Window.alert(caught.getMessage());
	            GWT.log("getStatementTypes failed", caught);
            }

            @Override
            public void onSuccess(List<StatementTypeInfo> stmtTypes) {
                for (StatementTypeInfo stmtType : stmtTypes) {
                    rules.put(stmtType, null);
                }

                //retrieve program requirements
                /* TODO test when program service is ready
                programRemoteService.getProgramRequirement(null, new AsyncCallback<ProgramRequirementInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        GWT.log("getProgramRequirement failed", caught);
                    }

                    @Override
                    public void onSuccess(ProgramRequirementInfo programReqInfo) {
                        //update list with new program requirement
                        for (StatementTypeInfo stmtInfo : rules.keySet()) {
                            if (stmtInfo.getId() == programReqInfo.getType()) {
                                if (rules.get(stmtInfo) != null) {
                                    Window.alert("Found 2 same program requirements? ("+programReqInfo.getType());
                                    GWT.log("Found 2 same program requirements? ("+programReqInfo.getType());                                    
                                }
                                rules.put(stmtInfo, programReqInfo);
                                break;
                            }
                        }
                    }
                }); */
            }
        });

    //    SectionTitle temp = SectionTitle.generateH5Title("Fundamental Studies");
//        temp.addStyleName("KS-Program-Requirements-TopHeader");
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        addWidget(SectionTitle.generateH2Title(ProgramProperties.get().programRequirements_summaryViewPageTitle()));        

        for (StatementTypeInfo stmtInfo : rules.keySet()) {
            addWidget(SectionTitle.generateH3Title(stmtInfo.getName()));

            KSButton addCompletionReqBtn = new KSButton("Add a Completion Requirement", KSButtonAbstract.ButtonStyle.FORM_SMALL);
        //   addCompletionReqBtn.addStyleName("KS-Program-Requirements-TopHeaderButton");
            addWidget(addCompletionReqBtn);

            //TODO remove after testing
            if (stmtInfo.getId().equals("kuali.luStatementType.programEntrance")) {
                addWidget(new RequirementDisplayWidget(parentController, ruleTree));
            }
        }

        onReadyCallback.exec(true);
    }
}