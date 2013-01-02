/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.course.service.assembler;

import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.common.util.UUIDHelper;


/**
 * Assembles/Disassembles ActivityInfo DTO from/To CluInfo 
 * 
 * @author Kuali Student Team
 *
 */
public class ActivityAssembler implements BOAssembler<ActivityInfo, CluInfo> {

    private CluService cluService;

    @Override
    public ActivityInfo assemble(CluInfo baseDTO, ActivityInfo businessDTO, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException {
    	if(baseDTO == null){
			return null;
		}
		
		ActivityInfo activityInfo = (null != businessDTO) ? businessDTO : new ActivityInfo();
	    
		activityInfo.setId(baseDTO.getId());
		activityInfo.setTypeKey(baseDTO.getTypeKey());
		activityInfo.setStateKey(baseDTO.getStateKey());
		activityInfo.setDefaultEnrollmentEstimate(baseDTO.getDefaultEnrollmentEstimate());
		activityInfo.setDuration(baseDTO.getStdDuration());
		activityInfo.setContactHours(baseDTO.getIntensity());
		activityInfo.setMeta(baseDTO.getMeta());
        activityInfo.setAttributes(baseDTO.getAttributes());
		return activityInfo;
	}

    @Override
    public BaseDTOAssemblyNode<ActivityInfo, CluInfo> disassemble(ActivityInfo businessDTO, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException {
		
    	if (businessDTO == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
			throw new AssemblyException("Activity can not be null");
		}
		if (NodeOperation.CREATE != operation && null == businessDTO.getId()) {
			throw new AssemblyException("Activity's id can not be null");
		}

		BaseDTOAssemblyNode<ActivityInfo, CluInfo> result = new BaseDTOAssemblyNode<ActivityInfo, CluInfo>(
				this);

		CluInfo clu;
		try {
			clu = (NodeOperation.UPDATE == operation) ? cluService.getClu(
							businessDTO.getId(), contextInfo) : new CluInfo();
		} catch (Exception e) {
			throw new AssemblyException(
					"Error retrieving activity learning unit during update", e);
		}

		// Copy all fields
		clu.setId(UUIDHelper.genStringUUID(businessDTO.getId()));
		clu.setTypeKey(businessDTO.getTypeKey());
		clu.setStateKey(businessDTO.getStateKey());
		clu.setIsEnrollable(false);
		
		clu.setDefaultEnrollmentEstimate(businessDTO
				.getDefaultEnrollmentEstimate() != null ? businessDTO
		                .getDefaultEnrollmentEstimate() : 0);
		clu.setStdDuration(businessDTO.getDuration());
		clu.setIntensity(businessDTO.getContactHours());
		clu.setMeta(businessDTO.getMeta());
		clu.setAttributes(businessDTO.getAttributes());

		// Add the Clu to the result
		result.setNodeData(clu);

		// Add refernce to Activity
		result.setBusinessDTORef(businessDTO);

		result.setOperation(operation);

		return result;
	}

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public CluService getCluService() {
        return cluService;
    }

}
