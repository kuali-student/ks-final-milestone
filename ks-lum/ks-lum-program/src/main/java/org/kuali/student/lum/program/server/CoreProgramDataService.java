package org.kuali.student.lum.program.server;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.client.ProgramClientConstants;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * @author Jim
 */
public class CoreProgramDataService extends AbstractDataService {

    private static final long serialVersionUID = 1L;
    
    private ProgramService programService;
    private LuService luService;
    
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
        List<String> coreIds = luService.getCluIdsByLuType(ProgramClientConstants.CORE_PROGRAM, ProgramClientConstants.STATE_ACTIVE);
        if (null == coreIds || coreIds.size() != 1) {
            throw new OperationFailedException("A single core program is required; database contains " + (null == coreIds ? "0" : coreIds.size() + "."));
        }
        return programService.getCoreProgram(coreIds.get(0));
    }

    @Override
    protected Object save(Object dto, Map<String, Object> properties) throws Exception {
        if (dto instanceof CoreProgramInfo) {
            CoreProgramInfo cpInfo = (CoreProgramInfo) dto;
            if (cpInfo.getId() == null && cpInfo.getVersionInfo() != null) {
            	String coreVersionIndId = cpInfo.getVersionInfo().getVersionIndId();
            	cpInfo = programService.createNewCoreProgramVersion(coreVersionIndId, "New core program version");
            } else if (cpInfo.getId() == null) {
                cpInfo = programService.createCoreProgram(cpInfo);
            } else {
                cpInfo = programService.updateCoreProgram(cpInfo);
            }
            return cpInfo;
        } else {
            throw new InvalidParameterException("Only persistence of CoreProgram is supported by this DataService implementation.");
        }
    }

    @Override
    protected Class<?> getDtoClass() {
        return CoreProgramInfo.class;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }
}
