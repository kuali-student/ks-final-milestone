package org.kuali.student.lum.program.server.transform;

import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Data.DataType;
import org.kuali.student.r1.common.assembly.data.Data.StringKey;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.common.assembly.transform.AbstractDataFilter;
import org.kuali.student.r1.common.assembly.transform.MetadataFilter;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

/**
 * Generating metadata and populating data model values for previous end term and enroll term
 * fields to be displayed on activate dialog.  These values do not exist in the MajorDiscipline
 * object. 
 * so we need to g
 * 
 * 
 * 
 * @author Will
 *
 */
public class VersionProgramFilter extends AbstractDataFilter implements MetadataFilter{
	public static final String PREVIOUS_VERSION_DATA = "VersionProgramFilter.PreviousVersionData";
	public static final String PREVIOUS_VERSION_INFO = "proposal";
	
	private Metadata previousVersionMetadata;
	protected ProgramService programService; 
	protected MetadataServiceImpl metadataService; 
	protected AtpService atpService;
	protected String proposalObjectType;
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
			if (previousVersionData == null){
				MajorDisciplineInfo previousVersionMajorInfo = programService.getMajorDiscipline(versionFromId, ContextUtils.getContextInfo());
				
				//This is an initial get. Create previous version data to send back to client 
				previousVersionData = new Data();
				previousVersionData.set(ProgramConstants.ID, previousVersionMajorInfo.getId());
				previousVersionData.set(ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM, previousVersionMajorInfo.getEndProgramEntryTerm());
				previousVersionData.set(ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM, previousVersionMajorInfo.getEndTerm());
				previousVersionData.set(ProgramConstants.PREV_END_INST_ADMIN_TERM, previousVersionMajorInfo.getEndTerm());
				
				//set the prev start term to be the earliest of the major and all variations
                ContextInfo contextInfo = ContextUtils.getContextInfo();
				AtpInfo latestStartAtp = atpService.getAtp(previousVersionMajorInfo.getStartTerm(),contextInfo);
				for (ProgramVariationInfo variation:previousVersionMajorInfo.getVariations()){
					AtpInfo variationAtp = atpService.getAtp(variation.getStartTerm(),contextInfo);
					if(variationAtp!=null && variationAtp.getStartDate()!=null && variationAtp.getStartDate().compareTo(latestStartAtp.getStartDate())>0){
						latestStartAtp = variationAtp;
					}
				}
				
				previousVersionData.set(ProgramConstants.PREV_START_TERM, latestStartAtp.getId());
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
		//FIXME possible synchronization issue here with instance variable previousVersionMetadata
		if (previousVersionMetadata == null){
			//Get proposal metadata
			Metadata proposalMetadata = metadataService.getMetadata(proposalObjectType);
			
			//Pull in metadata for specific fields (some from proposal definition and some from program
			DataModelDefinition modelDef = new DataModelDefinition(metadata);
			Metadata programIdMeta = modelDef.getMetadata(QueryPath.parse(ProgramConstants.ID));
			Metadata startTermMeta = proposalMetadata.getProperties().get(ProgramConstants.PREV_START_TERM);
			Metadata endEntryTermMeta = proposalMetadata.getProperties().get(ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM);
			Metadata endEnrollTermMeta = proposalMetadata.getProperties().get(ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM);
			Metadata endInstAdminMeta = proposalMetadata.getProperties().get(ProgramConstants.PREV_END_INST_ADMIN_TERM);
			
			endEntryTermMeta.getConstraints().get(0).setRequiredForNextState(true);
			endEnrollTermMeta.getConstraints().get(0).setRequiredForNextState(true);
			endInstAdminMeta.getConstraints().get(0).setRequiredForNextState(true);
			
			endEntryTermMeta.getConstraints().get(0).setMinOccurs(1);
			endEnrollTermMeta.getConstraints().get(0).setMinOccurs(1);
			endInstAdminMeta.getConstraints().get(0).setMinOccurs(1);
			
			previousVersionMetadata = new Metadata();
			previousVersionMetadata.setDataType(DataType.DATA);
					
			Map<String, Metadata> properties = previousVersionMetadata.getProperties();
			properties.put(ProgramConstants.ID, programIdMeta);
			properties.put(ProgramConstants.PREV_START_TERM, startTermMeta);
			properties.put(ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM, endEntryTermMeta);
			properties.put(ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM, endEnrollTermMeta);
			properties.put(ProgramConstants.PREV_END_INST_ADMIN_TERM, endInstAdminMeta);
		}
		
		return previousVersionMetadata;
	}
	
	public void setProgramService(ProgramService programService) {
		this.programService = programService;
	}

	public void setMetadataService(MetadataServiceImpl metadataService) {
		this.metadataService = metadataService;
	}

	public void setProposalObjectType(String proposalObjectType) {
		this.proposalObjectType = proposalObjectType;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}	
}
