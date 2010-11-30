package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/statementRpcService")
public interface StatementRpcService extends RemoteService {
    List<StatementTypeInfo> getStatementTypesForStatementType(String statementTypeKey) throws Exception;
    List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String luStatementTypeKey) throws Exception;
    String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language) throws Exception;
    String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language) throws Exception;   
}
