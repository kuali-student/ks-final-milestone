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

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
/**
 * Assembles/Disassembles CourseJointInfo DTO from/to CluCluRelationInfo 
 * 
 * @author Kuali Student Team
 *
 */
public class CourseJointAssembler implements BOAssembler<CourseJointInfo, CluCluRelationInfo> {
		
	CluService cluService;		
	
	/**
	 * @return the luService
	 */
	public CluService getCluService() {
		return cluService;
	}

	/**
	 * @param luService the luService to set
	 */
	public void setCluService(CluService cluService) {
		this.cluService = cluService;
	}

	@Override
	public CourseJointInfo assemble(CluCluRelationInfo cluRel, CourseJointInfo jointInfo, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException {
		if(null == cluRel) {
			return null;
		}
		
		CourseJointInfo joint = (jointInfo != null) ? jointInfo : new CourseJointInfo();

		CluInfo clu = null;
		try {
			clu = cluService.getClu(cluRel.getRelatedCluId() , contextInfo);

			joint.setCourseId(clu.getId());

			joint.setTypeKey(clu.getTypeKey());//FIXME is this ever used?
			joint.setSubjectArea(clu.getOfficialIdentifier().getDivision());
			joint.setCourseTitle(clu.getOfficialIdentifier().getLongName());
			joint.setCourseNumberSuffix(clu.getOfficialIdentifier().getSuffixCode());
			joint.setRelationId(cluRel.getId());

		} catch (Exception e) {
			throw new AssemblyException("Error getting related clus", e);
		} 
		
		return joint;
	}

	
	public CourseJointInfo assemble(CluCluRelationInfo cluRel, String cluId, CourseJointInfo jointInfo, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException {
		if(null == cluRel) {
			return null;
		}
		
		CourseJointInfo joint = (jointInfo != null) ? jointInfo : new CourseJointInfo();

		CluInfo clu = null;
		try {
			clu = cluService.getClu(cluRel.getRelatedCluId() , contextInfo);
			
			joint.setCourseId(clu.getId());
            joint.setTypeKey(clu.getTypeKey());
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
			CourseJointInfo joint, NodeOperation operation,ContextInfo contextInfo) throws AssemblyException {
		
		if(null == joint){
			//FIXME Unsure now if this is an exception or just return null or empty assemblyNode 
			throw new AssemblyException("Activity can not be null");
		}
				
		BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo> result = new BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo>(this);
		result.setBusinessDTORef(joint);
		result.setOperation(operation);
		
		CluCluRelationInfo cluRel = new CluCluRelationInfo();
		cluRel.setId(UUIDHelper.genStringUUID(joint.getRelationId()));
		cluRel.setRelatedCluId(joint.getCourseId());
				
		cluRel.setTypeKey(CourseAssemblerConstants.JOINT_RELATION_TYPE);
		result.setNodeData(cluRel);
		// The caller is required to set the CluId on the cluCluRelation
		
		return result;
	}
}
