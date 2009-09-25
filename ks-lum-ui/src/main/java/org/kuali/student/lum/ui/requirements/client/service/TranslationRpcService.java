package org.kuali.student.lum.ui.requirements.client.service;

import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/TranslationRpcService")
public interface TranslationRpcService extends BaseRpcService {
	public String getNaturalLanguageForReqComponentInfo(ReqComponentInfo compInfo, String nlUsageTypeKey) throws Exception;
    public String getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey) throws Exception;  
}
