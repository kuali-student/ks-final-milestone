package org.kuali.student.lum.program.server;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.program.client.ProgramClientConstants;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * @author Igor
 */
public class MajorDisciplineDataService extends AbstractDataService {

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
        MajorDisciplineInfo returnDTO;
        if (null == id || id.length() == 0) {
            returnDTO = new MajorDisciplineInfo();
            returnDTO.setType(ProgramClientConstants.MAJOR_PROGRAM);
            returnDTO.setState(DtoConstants.STATE_DRAFT);
        } else {
            returnDTO = programService.getMajorDiscipline(id);
        }
        return returnDTO;
    }

    @Override
    protected Object save(Object dto, Map<String, Object> properties) throws Exception {
        if (dto instanceof MajorDisciplineInfo) {
            MajorDisciplineInfo mdInfo = (MajorDisciplineInfo) dto;
            if (mdInfo.getId() == null && mdInfo.getVersionInfo() != null) {
            	String majorVersionIndId = mdInfo.getVersionInfo().getVersionIndId();
            	mdInfo = programService.createNewMajorDisciplineVersion(majorVersionIndId, "New major discipline version");
            } else if (mdInfo.getId() == null){
                mdInfo = programService.createMajorDiscipline(mdInfo);
            } else {
                mdInfo = programService.updateMajorDiscipline(mdInfo);
            }
            return mdInfo;
        } else {
            throw new InvalidParameterException("Only persistence of MajorDiscipline is supported by this DataService implementation.");
        }
    }  

    
    @Override
	protected List<ValidationResultInfo> validate(Object dto) throws Exception {
		return programService.validateMajorDiscipline("OBJECT", (MajorDisciplineInfo)dto);
	}

	@Override
    protected Class<?> getDtoClass() {
        return MajorDisciplineInfo.class;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

}
