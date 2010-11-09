package org.kuali.student.lum.program.client.rpc;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.common.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;

import java.util.List;

@RemoteServiceRelativePath("rpcservices/statementRpcService")
public interface StatementRpcService extends BaseRpcService {
    List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(String statementTypeKey) throws Exception;
    List<StatementTypeInfo> getStatementTypesForStatementType(String statementTypeKey) throws Exception;
    List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String luStatementTypeKey) throws Exception;
    String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language) throws Exception;
    String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language) throws Exception;
    List<String> translateReqComponentToNLs(ReqComponentInfoUi reqComponentInfo, String[] nlUsageTypeKeys, String language) throws Exception;
}
