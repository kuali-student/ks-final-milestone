package org.kuali.student.lum.program.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.program.client.requirements.StatementVO;

public interface StatementRpcServiceAsync extends BaseRpcServiceAsync {
    public void translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language, AsyncCallback<String> callback);
    public void translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language, AsyncCallback<String> callback);

  //  public void getNaturalLanguageForReqComponent(String reqComponentId, String nlUsageTypeKey, String language, AsyncCallback<String> callback);
//    public void getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey, String language, AsyncCallback<String> callback);
}
