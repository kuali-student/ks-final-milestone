package org.kuali.student.lum.program.server;

import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;

/**
 * @author Igor
 */
public class ProgramRpcServlet extends AbstractBaseDataOrchestrationRpcGwtServlet implements ProgramRpcService {

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
        return null;
    }

    @Override
    protected Object save(Object dto) throws Exception {
        return null;
    }

    @Override
    protected Class<?> getDtoClass() {
        return MajorDisciplineInfo.class;
    }
}
