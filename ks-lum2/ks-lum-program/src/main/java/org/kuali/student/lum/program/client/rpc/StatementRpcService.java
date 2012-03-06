package org.kuali.student.lum.program.client.rpc;

import java.util.List;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r1.common.ui.client.service.BaseRpcService;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.r2.lum.clu.dto.CluInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/statementRpcService")
public interface StatementRpcService extends BaseRpcService {
    List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(String statementTypeKey, ContextInfo contextInfo) throws Exception;
    List<StatementTypeInfo> getStatementTypesForStatementType(String statementTypeKey, ContextInfo contextInfo) throws Exception;
    List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String luStatementTypeKey, ContextInfo contextInfo) throws Exception;
    String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language, ContextInfo contextInfo) throws Exception;
    String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language, ContextInfo contextInfo) throws Exception;
    List<String> translateReqComponentToNLs(ReqComponentInfoUi reqComponentInfo, String[] nlUsageTypeKeys, String language, ContextInfo contextInfo) throws Exception;
    CluInfo getClu(String cluId, ContextInfo contextInfo) throws Exception;
    VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws Exception;
}
