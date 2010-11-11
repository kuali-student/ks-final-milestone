package org.kuali.student.lum.program.server;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.client.ProgramClientConstants;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * @author Igor
 */
public class CredentialProgramDataService extends AbstractDataService {

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
        if (null == id || id.length() == 0 || ! ProgramClientConstants.CREDENTIAL_PROGRAM_TYPES.contains(id)) {
            throw new InvalidParameterException("No valid id for retrieving CredentialProgram");
        } else {
            List<String> credIds = luService.getCluIdsByLuType(id, ProgramClientConstants.STATE_ACTIVE);
            if (null == credIds || credIds.size() != 1) {
                throw new OperationFailedException("A single credential program of type " + id + " is required; database contains " +
                                                    (null == credIds ? "0" : credIds.size() +
                                                    "."));
            }
            return programService.getCredentialProgram(credIds.get(0));
        }
    }

    @Override
    protected Object save(Object dto, Map<String, Object> properties) throws Exception {
        if (dto instanceof CredentialProgramInfo) {
            CredentialProgramInfo cpInfo = (CredentialProgramInfo) dto;
            if (cpInfo.getId() == null) {
                cpInfo = programService.createCredentialProgram(cpInfo);
            } else {
                cpInfo = programService.updateCredentialProgram(cpInfo);
            }
            return cpInfo;
        } else {
            throw new InvalidParameterException("Only persistence of CredentialProgram is supported by this DataService implementation.");
        }
    }

    @Override
    protected Class<?> getDtoClass() {
        return CredentialProgramInfo.class;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }
}
