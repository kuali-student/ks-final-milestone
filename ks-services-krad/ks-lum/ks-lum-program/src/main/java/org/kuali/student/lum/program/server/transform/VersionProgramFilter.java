package org.kuali.student.lum.program.server.transform;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Data.StringKey;
import org.kuali.student.core.assembly.transform.AbstractDataFilter;
import org.kuali.student.core.assembly.transform.MetadataFilter;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * When program is activated this filter sets the previous version to superseded and updates the end program
 * entry and and enroll terms. 
 * 
 * @author Will
 *
 */
public class VersionProgramFilter extends AbstractDataFilter implements MetadataFilter{
	public static final String PREVIOUS_VERSION_DATA = "VersionProgramFilter.PreviousVersionData";
	public static final String PREVIOUS_VERSION_INFO = "previousVersionInfo";
	
	private Metadata previousVersionMetadata;
	protected ProgramService programService; 
	
	/**
	 * Save previousVersionInfo from incoming data to be used by outbound filter
	 */
	@Override
	public void applyInboundDataFilter(Data data, Metadata metadata,
			Map<String, Object> properties) throws Exception {
		Data previousVersionData = data.query(PREVIOUS_VERSION_INFO);
		data.remove(new StringKey(PREVIOUS_VERSION_INFO));
				
		if (previousVersionData != null){	
			properties.put(PREVIOUS_VERSION_DATA, previousVersionData);
		}			
	}

	/**
	 * Adds previous version information to the data if it exists and updates the previous version state
	 * if state changed (this happens when program is activated)
	 */
	@Override
	public void applyOutboundDataFilter(Data data, Metadata metadata,
			Map<String, Object> properties) throws Exception {
		Data previousVersionData = (Data)properties.get(PREVIOUS_VERSION_DATA);
		String versionFromId = data.query(ProgramConstants.VERSION_FROM_ID);
		
		if (versionFromId != null){
			MajorDisciplineInfo previousVersionMajorInfo = programService.getMajorDiscipline(versionFromId);
			
			if (previousVersionData == null){
				//This is an initial get. Create previous version data to send back to client 
				previousVersionData = new Data();
				previousVersionData.set(ProgramConstants.ID, previousVersionMajorInfo.getId());
				previousVersionData.set(ProgramConstants.END_PROGRAM_ENTRY_TERM, previousVersionMajorInfo.getEndProgramEntryTerm());
				previousVersionData.set(ProgramConstants.END_PROGRAM_ENROLL_TERM, previousVersionMajorInfo.getEndTerm());
				previousVersionData.set(ProgramConstants.STATE, previousVersionMajorInfo.getState());
			} else {
				//This is a save operation. Check state field change for previous version state, indicating an "Activate" action,
				//which requires updating previous program with new states and end terms and setting activated program
				//to be the current version.
				
				String state = previousVersionData.get(ProgramConstants.STATE);
				if (state!= null && !state.equals(previousVersionMajorInfo.getState())){
					//Update previous program version with new state and terms
					String endEntryTerm = previousVersionData.get(ProgramConstants.END_PROGRAM_ENTRY_TERM); 
					String endEnrollTerm = previousVersionData.get(ProgramConstants.END_PROGRAM_ENROLL_TERM);

					previousVersionMajorInfo.setState(state);
					previousVersionMajorInfo.setEndProgramEntryTerm(endEntryTerm);
					previousVersionMajorInfo.setEndTerm(endEnrollTerm);
					
					//Update states on all variations for this program
			        List<ProgramVariationInfo> variationList = previousVersionMajorInfo.getVariations();
			        for (ProgramVariationInfo variation:variationList){
			        	variation.setState(state);			        	
			        }

					programService.updateMajorDiscipline(previousVersionMajorInfo);
				
			        
					//Set "activated" program to be the current version
					String activatedMajorId = data.get(ProgramConstants.ID);
					programService.setCurrentMajorDisciplineVersion(activatedMajorId, null);
				}

			}
			
			data.set(PREVIOUS_VERSION_INFO, previousVersionData);
		}		
	}

	/**
	 * Adds previousVersionInfo metadata to metadata returned to client
	 */
	@Override
	public void applyMetadataFilter(String dtoName, Metadata metadata,
			Map<String, Object> filterProperties) {		
		metadata.getProperties().put(PREVIOUS_VERSION_INFO, getPreviousVersionMetadata(metadata));
	}

	/**
	 * This generates a phantom "previousVersionInfo" metadata using dictionary definitions for fields already
	 * defined for majorDisicplineInfo.
	 * 
	 * @param metadata
	 * @return
	 */
	protected Metadata getPreviousVersionMetadata(Metadata metadata){
		if (previousVersionMetadata == null){
			DataModelDefinition modelDef = new DataModelDefinition(metadata);
			Metadata programIdMeta = modelDef.getMetadata(QueryPath.parse(ProgramConstants.ID));
			Metadata programStateMeta = modelDef.getMetadata(QueryPath.parse(ProgramConstants.STATE));
			Metadata endEntryTermMeta = modelDef.getMetadata(QueryPath.parse(ProgramConstants.END_PROGRAM_ENTRY_TERM));
			Metadata endEnrollTermMeta = modelDef.getMetadata(QueryPath.parse(ProgramConstants.END_PROGRAM_ENROLL_TERM));
			
			previousVersionMetadata = new Metadata();
			previousVersionMetadata.setDataType(DataType.DATA);
					
			Map<String, Metadata> properties = previousVersionMetadata.getProperties();
			properties.put(ProgramConstants.ID, programIdMeta);
			properties.put(ProgramConstants.STATE, programStateMeta);
			properties.put(ProgramConstants.END_PROGRAM_ENTRY_TERM, endEntryTermMeta);
			properties.put(ProgramConstants.END_PROGRAM_ENROLL_TERM, endEnrollTermMeta);
		}
		
		return previousVersionMetadata;
	}
	
	public void setProgramService(ProgramService programService) {
		this.programService = programService;
	}	
}
