package org.kuali.student.lum.program.client.rpc;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Igor
 */
public interface ProgramRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync {
    public void getProgramRequirement(String programRequirementId, AsyncCallback<ProgramRequirementInfo> callback);
}
