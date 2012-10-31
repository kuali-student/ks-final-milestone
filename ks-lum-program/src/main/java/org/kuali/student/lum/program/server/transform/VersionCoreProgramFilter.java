package org.kuali.student.lum.program.server.transform;

import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;

/**
 * When core program is activated this filter sets the previous version to superseded and updates the end program
 * entry and and enroll terms. 
 * 
 * @author Will
 *
 */
public class VersionCoreProgramFilter extends VersionProgramFilter {

	@Override
	public void applyOutboundDataFilter(Data data, Metadata metadata,
			Map<String, Object> properties) throws Exception {
		Data previousVersionData = (Data)properties.get(VersionProgramFilter.PREVIOUS_VERSION_DATA);
		String versionFromId = data.query(ProgramConstants.VERSION_FROM_ID);
		
		if (versionFromId != null){
			CoreProgramInfo previousVersionCoreInfo = programService.getCoreProgram(versionFromId, ContextUtils.getContextInfo());
			
			if (previousVersionData == null){
				//This is an initial get. Create previous version data to send back to client 
				previousVersionData = new Data();
				previousVersionData.set(ProgramConstants.ID, previousVersionCoreInfo.getId());
				previousVersionData.set(ProgramConstants.END_PROGRAM_ENTRY_TERM, previousVersionCoreInfo.getEndProgramEntryTerm());
				previousVersionData.set(ProgramConstants.END_PROGRAM_ENROLL_TERM, previousVersionCoreInfo.getEndTerm());
				previousVersionData.set(ProgramConstants.STATE, previousVersionCoreInfo.getStateKey());
			}
			
			data.set(PREVIOUS_VERSION_INFO, previousVersionData);
		}		
	}

}
