package org.kuali.student.lum.program.server;

import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * @author Igor
 */
public class ProgramRpcServlet extends DataGwtServlet implements ProgramRpcService {

    private static final long serialVersionUID = 1L;
    
    @Override
    public ProgramRequirementInfo getProgramRequirement(String programRequirementId) throws Exception {
        return ((ProgramService) getDataService()).getProgramRequirement(programRequirementId, null, null);
    }    
}
