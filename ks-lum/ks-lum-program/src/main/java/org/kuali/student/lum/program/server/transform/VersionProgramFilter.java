package org.kuali.student.lum.program.server.transform;

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
import org.kuali.student.lum.program.service.ProgramService;

/**
 * When program is activated this filter sets the previous version to superseded and updates the end program
 * entry and and enroll terms. 
 * 
 * @author Will
 *
 */
public class VersionProgramFilter extends AbstractDataFilter implements MetadataFilter{
	private static final String PREVIOUS_VERSION_DATA = "VersionProgramFilter.PreviousVersionData";
	private static final String PREVIOUS_VERSION_INFO = "previousVersionInfo";
	
	private Metadata previousVersionMetadata;
	private ProgramService programService; 
	
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
			MajorDisciplineInfo majorInfo = programService.getMajorDiscipline(versionFromId);
			
			if (previousVersionData == null){
				previousVersionData = new Data();
				previousVersionData.set(ProgramConstants.ID, majorInfo.getId());
				previousVersionData.set(ProgramConstants.END_PROGRAM_ENTRY_TERM, majorInfo.getEndProgramEntryTerm());
				previousVersionData.set(ProgramConstants.END_PROGRAM_ENROLL_TERM, majorInfo.getEndTerm());
				previousVersionData.set(ProgramConstants.STATE, majorInfo.getState());
			} else {
				String state = previousVersionData.get(ProgramConstants.STATE);

				if (!state.equals(majorInfo.getState())){
					String endEntryTerm = previousVersionData.get(ProgramConstants.END_PROGRAM_ENTRY_TERM); 
					String endEnrollTerm = previousVersionData.get(ProgramConstants.END_PROGRAM_ENROLL_TERM);

					majorInfo.setState(state);
					majorInfo.setEndProgramEntryTerm(endEntryTerm);
					majorInfo.setEndTerm(endEnrollTerm);
					
					programService.updateMajorDiscipline(majorInfo);
				}

			}
			
			data.set(PREVIOUS_VERSION_INFO, previousVersionData);
		}		
	}

	@Override
	public void applyMetadataFilter(String dtoName, Metadata metadata,
			Map<String, Object> filterProperties) {		
		metadata.getProperties().put(PREVIOUS_VERSION_INFO, getPreviousVersionMetadata(metadata));
	}

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
