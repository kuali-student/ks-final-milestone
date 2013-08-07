package org.kuali.student.lum.program.server;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.lum.program.client.ProgramClientConstants;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

/**
 * @author Igor
 */
public class CredentialProgramDataService extends AbstractDataService {

    private static final long serialVersionUID = 1L;
    
    private ProgramService programService;
    private CluService cluService;
    
    @Override
    protected String getDefaultWorkflowDocumentType() {
        return null;
    }

    @Override
    protected String getDefaultMetaDataState() {
        return null;
    }

    @Override
    protected Object get(String id,ContextInfo contextInfo) throws Exception {
    	if (ProgramClientConstants.CREDENTIAL_PROGRAM_TYPES.contains(id)){
            List<String> credIds = cluService.getCluIdsByLuType(id, DtoConstants.STATE_ACTIVE,ContextUtils.getContextInfo());
            if (null == credIds || credIds.size() != 1) {
                throw new OperationFailedException("A single credential program of type " + id + " is required; database contains " +
                                                    (null == credIds ? "0" : credIds.size() +
                                                    "."));
            }
            return programService.getCredentialProgram(credIds.get(0),ContextUtils.getContextInfo());
        } else {
        	return programService.getCredentialProgram(id,ContextUtils.getContextInfo());
        }
    }

    @Override
    protected Object save(Object dto, Map<String, Object> properties,ContextInfo contextInfo) throws Exception {
        if (dto instanceof CredentialProgramInfo) {
            CredentialProgramInfo cpInfo = (CredentialProgramInfo) dto;
            if (cpInfo.getId() == null && cpInfo.getVersion() != null) {
            	String credentialVersionIndId = cpInfo.getVersion().getVersionIndId();
            	cpInfo = programService.createNewCredentialProgramVersion(credentialVersionIndId, "New credential program version", ContextUtils.getContextInfo());
            } else if (cpInfo.getId() == null) {
                cpInfo = programService.createCredentialProgram(cpInfo.getId(), cpInfo, ContextUtils.getContextInfo());
            } else {
            	cpInfo = programService.updateCredentialProgram(cpInfo.getId(), cpInfo, ContextUtils.getContextInfo());
            }
            return cpInfo;
        } else {
            throw new InvalidParameterException("Only persistence of CredentialProgram is supported by this DataService implementation.");
        }
    }

    @Override
	protected List<ValidationResultInfo> validate(Object dto,ContextInfo contextInfo) throws Exception {
		return programService.validateCredentialProgram("OBJECT", (CredentialProgramInfo)dto,ContextUtils.getContextInfo());
	}
    
    @Override
    protected Class<?> getDtoClass() {
        return CredentialProgramInfo.class;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

   
}
