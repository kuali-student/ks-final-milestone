package org.kuali.student.lum.program.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;

import java.util.List;

public interface StatementRpcServiceAsync extends BaseRpcServiceAsync {
    public void getStatementTypesForStatementTypeForCourse(String statementTypeKey, AsyncCallback<List<StatementTypeInfo>> callback);
    public void getStatementTypesForStatementType(String statementTypeKey, AsyncCallback<List<StatementTypeInfo>> callback);    
    public void getReqComponentTypesForStatementType(String luStatementTypeKey, AsyncCallback<List<ReqComponentTypeInfo>> callback);
    public void translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language, AsyncCallback<String> callback);
    public void translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language, AsyncCallback<String> callback);
}
