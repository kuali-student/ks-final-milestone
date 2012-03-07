package org.kuali.student.lum.program.client.rpc;

import java.util.List;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.lum.lu.dto.CluInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StatementRpcServiceAsync extends BaseRpcServiceAsync {
    public void getStatementTypesForStatementTypeForCourse(String statementTypeKey, ContextInfo contextInfo, AsyncCallback<List<StatementTypeInfo>> callback);
    public void getStatementTypesForStatementType(String statementTypeKey, ContextInfo contextInfo, AsyncCallback<List<StatementTypeInfo>> callback);
    public void getReqComponentTypesForStatementType(String luStatementTypeKey, ContextInfo contextInfo, AsyncCallback<List<ReqComponentTypeInfo>> callback);
    public void translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language, ContextInfo contextInfo, AsyncCallback<String> callback);
    public void translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language, ContextInfo contextInfo, AsyncCallback<String> callback);
	public void translateReqComponentToNLs(ReqComponentInfoUi reqComp, String[] nlUsageTypeKeys, String temlateLanguage, ContextInfo contextInfo, AsyncCallback<List<String>> callback);
    public void getClu(String cluId, ContextInfo contextInfo, AsyncCallback<CluInfo> callback);
    public void getCurrentVersion(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo, AsyncCallback<VersionDisplayInfo> callback);
}
