package org.kuali.student.lum.program.client.rpc;

import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.common.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/statementRpcService")
public interface StatementRpcService extends BaseRpcService {
    List<StatementTypeInfo> getStatementTypesForStatementTypeForCourse(String statementTypeKey) throws Exception;
    List<StatementTypeInfo> getStatementTypesForStatementType(String statementTypeKey) throws Exception;
    List<ReqComponentTypeInfo> getReqComponentTypesForStatementType(String luStatementTypeKey) throws Exception;
    String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language) throws Exception;
    String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language) throws Exception;
    List<String> translateReqComponentToNLs(ReqComponentInfoUi reqComponentInfo, String[] nlUsageTypeKeys, String language) throws Exception;
    CluInfo getClu(String cluId) throws Exception;
    VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId) throws Exception;
}
