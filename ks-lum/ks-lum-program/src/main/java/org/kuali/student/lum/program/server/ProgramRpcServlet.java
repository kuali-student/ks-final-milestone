package org.kuali.student.lum.program.server;

import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * @author Igor
 */
public class ProgramRpcServlet extends AbstractBaseDataOrchestrationRpcGwtServlet implements ProgramRpcService {

    private static final long serialVersionUID = 1L;
    
    private ProgramService programService;
    
    @Override
    protected String getDefaultWorkflowDocumentType() {
        return null;
    }

    @Override
    protected String getDefaultMetaDataState() {
        return null;
    }

    @Override
    protected Object get(String id) throws Exception {
    	//TODO Just Major Discipline for now - need to check for other types later
        return programService.getMajorDiscipline(id);
    }

    @Override
    protected Object save(Object dto) throws Exception {
    	//TODO Just Major Discipline for now - need to check for other types later
        return programService.createMajorDiscipline((MajorDisciplineInfo)dto);
    }

    @Override
    protected Class<?> getDtoClass() {
        return MajorDisciplineInfo.class;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }
}
