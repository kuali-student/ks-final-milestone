package org.kuali.student.lum.program.client.rpc;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.program.client.requirements.StatementVO;

@RemoteServiceRelativePath("rpcservices/statementRpcService")
public interface StatementRpcService extends BaseRpcService {
    String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language) throws Exception;
    String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language) throws Exception;

  //  String getNaturalLanguageForReqComponent(String reqComponentId, String nlUsageTypeKey, String language);
 //   String getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey, String language) throws Exception;
}
