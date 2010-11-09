package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StatementRpcServiceAsync {
    public void getStatementTypesForStatementType(String statementTypeKey, AsyncCallback<List<StatementTypeInfo>> callback);
    public void getReqComponentTypesForStatementType(String luStatementTypeKey, AsyncCallback<List<ReqComponentTypeInfo>> async);
    public void translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language, AsyncCallback<String> callback);
    public void translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language, AsyncCallback<String> callback);
}
