package org.kuali.student.lum.lu.ui.dependency.server;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcService;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DependencyAnalysisRpcServlet extends RemoteServiceServlet implements DependencyAnalysisRpcService {

    private static final long serialVersionUID = 1L;

    StatementService statementService;
    ProgramService programService;

    @Override
    public String getRequirementComponentNL(String reqComponentId) {
        return null;
    }

    @Override
    public List<String> getRequirementComponentNL(List<String> reqComponentIds) throws Exception {
        List<String> reqComponents = new ArrayList<String>();
        try
        {
        for (String reqComponentId : reqComponentIds) {
            String reqCompNL = statementService.getNaturalLanguageForReqComponent(reqComponentId, "KUALI.RULE", "en");
            reqComponents.add(reqCompNL);
        }

        return reqComponents;
        }
        catch(Exception ex){
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ProgramRequirementInfo getProgramRequirement(String reqId) throws Exception {
        //TODO fix null parameters?
        try
        {
            ProgramRequirementInfo programRequirementInfo = programService.getProgramRequirement(reqId,
                    ContextUtils.getContextInfo());

            setProgReqNL(programRequirementInfo);
            return programRequirementInfo;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public StatementService getStatementService() {
        return statementService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public ProgramService getProgramService() {
        return programService;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    private void setProgReqNL(ProgramRequirementInfo programRequirementInfo) throws Exception {
        setReqCompNL(programRequirementInfo.getStatement());
    }

    private void setReqCompNL(StatementTreeViewInfo tree) throws Exception {
        List<StatementTreeViewInfo> statements = tree.getStatements();
        List<ReqComponentInfo> reqComponentInfos = tree.getReqComponents();

        if ((statements != null) && (statements.size() > 0)) {
            // retrieve all statements
            for (StatementTreeViewInfo statement : statements) {
                setReqCompNL(statement); // inside set the children of this statementTreeViewInfo
            }
        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
            // retrieve all req. component LEAFS
            for (int i = 0; i < reqComponentInfos.size(); i++) {
                ReqComponentInfoUi reqUi = RulesUtil.clone(reqComponentInfos.get(i));
                reqUi.setNaturalLanguageTranslation(statementService.translateReqComponentToNL(reqUi, "KUALI.RULE",
                        "en"));
                reqUi.setPreviewNaturalLanguageTranslation(statementService.translateReqComponentToNL(reqUi,
                        "KUALI.RULE.PREVIEW", "en"));
                reqComponentInfos.set(i, reqUi);
            }
        }
    }

}
