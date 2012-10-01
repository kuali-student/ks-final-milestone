package org.kuali.student.lum.program.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.assembly.transform.ProposalWorkflowFilter;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.client.ProgramClientConstants;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.ProgramServiceConstants;

/**
 * @author Igor
 */
public class MajorDisciplineProposalDataService extends AbstractDataService {

    private static final long serialVersionUID = 1L;
    
    private ProgramService programService;
    private LuService luService;
    private AtpService atpService; 

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
            returnDTO.setCredentialProgramId(getCredentialId());
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
            	
            	//Get the current Major Dicipline from the service
            	VersionDisplayInfo mdVersionInfo = programService.getCurrentVersion(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, majorVersionIndId);
            	mdInfo = programService.getMajorDiscipline(mdVersionInfo.getId());
            	
            	//set the prev start term to be the most recent of the major and all variations
				AtpInfo latestStartAtp = atpService.getAtp(mdInfo.getStartTerm());
				for (ProgramVariationInfo variation:mdInfo.getVariations()){
					AtpInfo variationAtp = atpService.getAtp(variation.getStartTerm());
					if(variationAtp!=null && variationAtp.getStartDate()!=null && variationAtp.getStartDate().compareTo(latestStartAtp.getStartDate())>0){
						latestStartAtp = variationAtp;
					}
				}

				//Save the start and end terms from the old version and put into filter properties
				String startTerm = latestStartAtp.getId();
		    	String endTerm = mdInfo.getEndTerm();
		    	String endProgramEntryTerm = mdInfo.getEndProgramEntryTerm();
		    	String endInstAdmitTerm = mdInfo.getAttributes().get(ProgramConstants.END_INSTITUTIONAL_ADMIT_TERM);
		    	Map<String,String> proposalAttributes = new HashMap<String,String>();
		    	if(startTerm!=null)
		    		proposalAttributes.put("prevStartTerm",startTerm);
		    	if(endTerm!=null)
		    		proposalAttributes.put("prevEndTerm",endTerm);
		    	if(endProgramEntryTerm!=null)
		    		proposalAttributes.put("prevEndProgramEntryTerm",endProgramEntryTerm);
		    	if(endInstAdmitTerm!=null)
		    		proposalAttributes.put("prevEndInstAdmitTerm",endInstAdmitTerm);
		    	properties.put(ProposalWorkflowFilter.PROPOSAL_ATTRIBUTES, proposalAttributes);
            	
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

    private String getCredentialId() throws Exception {

            List<String> credIds = luService.getCluIdsByLuType(ProgramClientConstants.CREDENTIAL_BACCALAUREATE_PROGRAM, DtoConstants.STATE_ACTIVE);
            if (null == credIds || credIds.size() != 1) {
                throw new OperationFailedException("A single credential program of type " + ProgramClientConstants.CREDENTIAL_BACCALAUREATE_PROGRAM + " is required; database contains " +
                                                    (null == credIds ? "0" : credIds.size() +
                                                    "."));
            }
            return credIds.get(0);
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	@Override
	protected boolean checkDocumentLevelPermissions() {
		// TODO Auto-generated method stub
		return  true;
	}

}
