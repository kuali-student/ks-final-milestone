package org.kuali.student.lum.program.client.rpc;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Igor
 */
@RemoteServiceRelativePath("rpcservices/programRpcService")
public interface ProgramRpcService extends BaseDataOrchestrationRpcService {
    public ProgramRequirementInfo getProgramRequirement(String programRequirementId) throws Exception;
}
