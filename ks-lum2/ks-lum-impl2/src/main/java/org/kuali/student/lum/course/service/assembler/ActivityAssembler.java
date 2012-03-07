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
package org.kuali.student.lum.course.service.assembler;

import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r1.lum.course.dto.ActivityInfo;
import org.kuali.student.r1.lum.lu.dto.CluInfo;


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
		// TODO KSCM-322		activityInfo.setActivityType(clu.getType());
		// TODO KSCM-322        activityInfo.setState(clu.getState());
		activityInfo.setDefaultEnrollmentEstimate(baseDTO.getDefaultEnrollmentEstimate());
		activityInfo.setDuration(baseDTO.getStdDuration());
		activityInfo.setContactHours(baseDTO.getIntensity());
		activityInfo.setMetaInfo(baseDTO.getMetaInfo());
		// TODO KSCM-322        activityInfo.setAttributes(clu.getAttributes());
		return activityInfo;
	}

    @Override
    public BaseDTOAssemblyNode<ActivityInfo, CluInfo> disassemble(ActivityInfo businessDTO, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException {
		if (businessDTO==null) {
			//FIXME Unsure now if this is an exception or just return null or empty assemblyNode 
			throw new AssemblyException("Activity can not be null");
		}
		if (NodeOperation.CREATE != operation && null == businessDTO.getId()) {
			throw new AssemblyException("Activity's id can not be null");
		}
		
		BaseDTOAssemblyNode<ActivityInfo,CluInfo> result = new BaseDTOAssemblyNode<ActivityInfo,CluInfo>(this);
		
		org.kuali.student.r2.lum.clu.dto.CluInfo clu = null;
        try {
        	clu = (NodeOperation.UPDATE == operation) ? cluService.getClu(businessDTO.getId(), contextInfo) : new org.kuali.student.r2.lum.clu.dto.CluInfo();
        } catch (Exception e) {
            throw new AssemblyException("Error retrieving activity learning unit during update", e);
        }
	
		//Copy all fields 
		clu.setId(UUIDHelper.genStringUUID(businessDTO.getId()));//Create the id if it's not there already(important for creating relations)
		clu.setType(businessDTO.getActivityType());
		clu.setState(businessDTO.getState());
		clu.setDefaultEnrollmentEstimate(businessDTO.getDefaultEnrollmentEstimate());

		// TODO KSCM-322 convert R2 to R1 clu.setStdDuration(businessDTO.getDuration());
		// TODO KSCM-322 clu.setIntensity(businessDTO.getContactHours());
		// TODO KSCM-322 clu.setMetaInfo(businessDTO.getMetaInfo());
		// TODO KSCM-322 clu.setAttributes(businessDTO.getAttributes());
				
		//Add the Clu to the result 
		// TODO KSCM-322 result.setNodeData(clu);

		// Add refernce to Activity
		result.setBusinessDTORef(businessDTO);
		
		result.setOperation(operation);

		return result;
	}

    public void setLuService(CluService cluService) {
        this.cluService = cluService;
    }

    public CluService getLuService() {
        return cluService;
    }

}
