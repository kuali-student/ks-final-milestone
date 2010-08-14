package org.kuali.student.lum.program.client.requirements;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;

import java.util.ArrayList;
import java.util.List;

public class ProgramRequirementsSummaryView extends VerticalSectionView {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);
    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);

    LayoutController parentController;

    public ProgramRequirementsSummaryView(LayoutController parentController, Enum<?> viewEnum, String name, String modelId, StatementTreeViewInfo ruleTree) {
        super(viewEnum, name, modelId);

        this.parentController = parentController;
  
        SectionTitle temp = SectionTitle.generateH5Title("Fundamental Studies");
        temp.addStyleName("KS-Program-Requirements-TopHeader");
     //   KSButton addCompletionReqBtn = new KSButton("Add a Completion Requirement", ButtonStyle.FORM_SMALL);
     //   addCompletionReqBtn.addStyleName("KS-Program-Requirements-TopHeaderButton");
     //   temp.add(addCompletionReqBtn);

        //retrieve program requirements
        //TODO what type of requirements to retrieve
        /*
        programRemoteService.getProgramRequirement(null, new AsyncCallback<ProgramRequirementInfo>() {
            @Override
            public void onFailure(Throwable caught) {
	            Window.alert(caught.getMessage());
	            GWT.log("getProgramRequirement failed", caught);
            }

            @Override
            public void onSuccess(ProgramRequirementInfo programReqInfo) {
                stmtTreeInfo = programReqInfo.getStatement();
            }
        }); */

        RequirementDisplayWidget temp2 = new RequirementDisplayWidget(parentController, ruleTree);
        super.addWidget(temp);
        super.addWidget(temp2);

        
    }

    protected String getLabel(String labelKey) {
        //return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
        return "Completion Requirements";
    }
}