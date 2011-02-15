package org.kuali.student.lum.program.server.transform;

import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;

/**
 * When credential program is activated this filter sets the previous version to superseded and updates the end program
 * entry and and enroll terms. 
 * 
 * @author Will
 *
 */
public class VersionCredentialProgramFilter extends VersionProgramFilter {

	@Override
	public void applyOutboundDataFilter(Data data, Metadata metadata,
			Map<String, Object> properties) throws Exception {
		Data previousVersionData = (Data)properties.get(VersionProgramFilter.PREVIOUS_VERSION_DATA);
		String versionFromId = data.query(ProgramConstants.VERSION_FROM_ID);
		
		if (versionFromId != null){
			CredentialProgramInfo previousVersionCoreInfo = programService.getCredentialProgram(versionFromId);
			
			if (previousVersionData == null){
				//This is an initial get. Create previous version data to send back to client 
				previousVersionData = new Data();
				previousVersionData.set(ProgramConstants.ID, previousVersionCoreInfo.getId());
				previousVersionData.set(ProgramConstants.END_PROGRAM_ENTRY_TERM, previousVersionCoreInfo.getEndProgramEntryTerm());
				previousVersionData.set(ProgramConstants.END_PROGRAM_ENROLL_TERM, previousVersionCoreInfo.getEndTerm());
				previousVersionData.set(ProgramConstants.STATE, previousVersionCoreInfo.getState());
			} else {
				//This is a save operation. Check state field change for previous version state, indicating an "Activate" action,
				//which requires updating previous program with new states and end terms and setting activated program
				//to be the current version.
				
				String state = previousVersionData.get(ProgramConstants.STATE);
				if (state!= null && !state.equals(previousVersionCoreInfo.getState())){
					//Update previous program version with new state and terms
					String endEntryTerm = previousVersionData.get(ProgramConstants.END_PROGRAM_ENTRY_TERM); 
					String endEnrollTerm = previousVersionData.get(ProgramConstants.END_PROGRAM_ENROLL_TERM);

					previousVersionCoreInfo.setState(state);
					previousVersionCoreInfo.setEndProgramEntryTerm(endEntryTerm);
					previousVersionCoreInfo.setEndTerm(endEnrollTerm);
					
					programService.updateCredentialProgram(previousVersionCoreInfo);
							        
					//Set "activated" program to be the current version
					String activatedCoreId = data.get(ProgramConstants.ID);
					programService.setCurrentCredentialProgramVersion(activatedCoreId, null);
				}

			}
			
			data.set(PREVIOUS_VERSION_INFO, previousVersionData);
		}		
	}

}
