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

import org.kuali.student.common.assembly.BOAssembler;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.common.assembly.data.AssemblyException;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

/**
 * Assembles/Disassembles ActivityInfo DTO from/To CluInfo 
 * 
 * @author Kuali Student Team
 *
 */
public class ActivityAssembler implements BOAssembler<ActivityInfo, CluInfo> {

    private LuService luService;

    @Override
    public ActivityInfo assemble(CluInfo baseDTO, ActivityInfo businessDTO, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException {
		if(baseDTO == null){
			return null;
		}
		
		ActivityInfo activityInfo = (null != businessDTO) ? businessDTO : new ActivityInfo();
	    
		activityInfo.setId(baseDTO.getId());
		// TODO KSCM		activityInfo.setActivityType(clu.getType());
		// TODO KSCMactivityInfo.setState(clu.getState());
		activityInfo.setDefaultEnrollmentEstimate(baseDTO.getDefaultEnrollmentEstimate());
		activityInfo.setDuration(baseDTO.getStdDuration());
		activityInfo.setContactHours(baseDTO.getIntensity());
		activityInfo.setMeta(baseDTO.getMetaInfo());
		// TODO KSCM        activityInfo.setAttributes(clu.getAttributes());
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
		
		CluInfo clu = null;
        try {
        	// TODO KSCM            clu = (NodeOperation.UPDATE == operation) ? luService.getClu(activity.getId()) : new CluInfo();
        } catch (Exception e) {
            throw new AssemblyException("Error retrieving activity learning unit during update", e);
        }
	
		//Copy all fields 
		clu.setId(UUIDHelper.genStringUUID(businessDTO.getId()));//Create the id if it's not there already(important for creating relations)
		// TODO KSCM		clu.setType(activity.getActivityType());
		clu.setState(businessDTO.getState());
		clu.setDefaultEnrollmentEstimate(businessDTO.getDefaultEnrollmentEstimate());
		clu.setStdDuration(businessDTO.getDuration());
		clu.setIntensity(businessDTO.getContactHours());
		clu.setMetaInfo(businessDTO.getMeta());
		// TODO KSCM		clu.setAttributes(activity.getAttributes());
				
		//Add the Clu to the result 
		result.setNodeData(clu);

		// Add refernce to Activity
		result.setBusinessDTORef(businessDTO);
		
		result.setOperation(operation);

		return result;
	}

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public LuService getLuService() {
        return luService;
    }


}
