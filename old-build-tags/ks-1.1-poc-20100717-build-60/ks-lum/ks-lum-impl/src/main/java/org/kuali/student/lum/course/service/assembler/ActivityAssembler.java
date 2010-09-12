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

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
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
	public ActivityInfo assemble(CluInfo clu, ActivityInfo activity, boolean shallowBuild) {
		if(clu == null){
			return null;
		}
		
		ActivityInfo activityInfo = (null != activity) ? activity : new ActivityInfo();
		activityInfo.setId(clu.getId());
		activityInfo.setActivityType(clu.getType());
		activityInfo.setState(clu.getState());
		activityInfo.setDefaultEnrollmentEstimate(clu.getDefaultEnrollmentEstimate());
		activityInfo.setDuration(clu.getStdDuration());
		activityInfo.setContactHours(clu.getIntensity());
		activityInfo.setMetaInfo(clu.getMetaInfo());
		
		return activityInfo;
	}

	@Override
	public BaseDTOAssemblyNode<ActivityInfo,CluInfo> disassemble(
			ActivityInfo activity, NodeOperation operation) throws AssemblyException {
		if (activity==null) {
			//FIXME Unsure now if this is an exception or just return null or empty assemblyNode 
			throw new AssemblyException("Activity can not be null");
		}
		if (NodeOperation.CREATE != operation && null == activity.getId()) {
			throw new AssemblyException("Activity's id can not be null");
		}
		
		BaseDTOAssemblyNode<ActivityInfo,CluInfo> result = new BaseDTOAssemblyNode<ActivityInfo,CluInfo>(this);
		
		CluInfo clu;
        try {
            clu = (NodeOperation.UPDATE == operation) ? luService.getClu(activity.getId()) : new CluInfo();
        } catch (Exception e) {
            throw new AssemblyException("Error retrieving activity learning unit during update", e);
        }
	
		//Copy all fields 
		clu.setId(UUIDHelper.genStringUUID(activity.getId()));//Create the id if it's not there already(important for creating relations)
		clu.setType(activity.getActivityType());
		clu.setState(activity.getState());
		clu.setDefaultEnrollmentEstimate(activity.getDefaultEnrollmentEstimate());
		clu.setStdDuration(activity.getDuration());
		clu.setIntensity(activity.getContactHours());
		clu.setMetaInfo(activity.getMetaInfo());
				
		//Add the Clu to the result 
		result.setNodeData(clu);

		// Add refernce to Activity
		result.setBusinessDTORef(activity);
		
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
