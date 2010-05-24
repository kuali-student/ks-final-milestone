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
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.lum.course.dto.CourseJointInfo;
import org.kuali.student.lum.course.service.assembler.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

/**
 * Assembles/Disassembles ActivityInfo DTO from/To CluInfo 
 * 
 * @author Kuali Student Team
 *
 */
public class CourseJointAssembler implements BOAssembler<CourseJointInfo, CluCluRelationInfo> {
		
	LuService luService;		
	
	/**
	 * @return the luService
	 */
	public LuService getLuService() {
		return luService;
	}

	/**
	 * @param luService the luService to set
	 */
	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	@Override
	public CourseJointInfo assemble(CluCluRelationInfo cluRel, CourseJointInfo jointInfo, boolean shallowBuild) throws AssemblyException {
		if(null == cluRel) {
			return null;
		}
		
		CourseJointInfo joint = (jointInfo != null) ? jointInfo : new CourseJointInfo();

		CluInfo clu;
		try {
			clu = luService.getClu(cluRel.getRelatedCluId());

			joint.setCourseId(clu.getId());
			joint.setType(clu.getType());
			joint.setSubjectArea(clu.getOfficialIdentifier().getDivision());
			joint.setCourseTitle(clu.getOfficialIdentifier().getLongName());
			joint.setCourseNumberSuffix(clu.getOfficialIdentifier().getSuffixCode());
			joint.setRelationId(cluRel.getId());

		} catch (Exception e) {
			throw new AssemblyException("Error getting related clus", e);
		} 
		
		return joint;
	}

	@Override
	public BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo> disassemble(
			CourseJointInfo joint, NodeOperation operation) throws AssemblyException {
		
		if(null == joint){
			//FIXME Unsure now if this is an exception or just return null or empty assemblyNode 
			throw new AssemblyException("Activity can not be null");
		}
				
		BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo> result = new BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo>();
		result.setBusinessDTORef(joint);
		
		CluCluRelationInfo cluRel = new CluCluRelationInfo();
		cluRel.setId(UUIDHelper.genStringUUID(joint.getRelationId()));
		cluRel.setRelatedCluId(joint.getCourseId());
		cluRel.setType(CourseAssemblerConstants.JOINT_RELATION_TYPE);
		
		// The caller is required to set the CluId on the cluCluRelation
		
		return result;
	}
}
