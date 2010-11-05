package org.kuali.student.lum.program.server;

import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.service.ProgramService;

import java.util.Map;

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
            // TODO: need to move these from ProgramAssemblerConstants to
            // a location that ks-lum-program can depend on
            returnDTO.setType("kuali.lu.type.MajorDiscipline");
            returnDTO.setState("draft");
            // TODO: hard-coded to a Baccalaureate right now, but we need to figure out
            // how to find the correct ID (https://jira.kuali.org/browse/KSLUM-393)
            returnDTO.setCredentialProgramId("d02dbbd3-20e2-410d-ab52-1bd6d362748b");
        } else {
            returnDTO = programService.getMajorDiscipline(id);
        }
        return returnDTO;
    }

    @Override
    protected Object save(Object dto, Map<String, Object> properties) throws Exception {
        if (dto instanceof MajorDisciplineInfo) {
            MajorDisciplineInfo mdInfo = (MajorDisciplineInfo) dto;
            if (mdInfo.getId() == null) {
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
    protected Class<?> getDtoClass() {
        return MajorDisciplineInfo.class;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }
}
