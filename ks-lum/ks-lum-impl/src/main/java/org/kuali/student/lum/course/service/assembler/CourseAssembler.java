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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.CourseJointInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.assembler.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

/**
 * This is a description of what this class does - Kamal don't forget to fill
 * this in.
 * 
 * @author Kuali Student Team
 * 
 */
public class CourseAssembler implements BOAssembler<CourseInfo, CluInfo> {


	private LuService luService;
	private FormatAssembler formatAssembler;
	private CourseJointAssembler courseJointAssembler;

	@Override
	public CourseInfo assemble(CluInfo clu, CourseInfo courseInfo, boolean shallowBuild) throws AssemblyException {
		
		CourseInfo course = (null != courseInfo) ? courseInfo : new CourseInfo();

		// Copy all the data from the clu to the course
		course.setAcademicSubjectOrgs(clu.getAcademicSubjectOrgs());
		course.setAttributes(clu.getAttributes());
		course.setCampusLocations(clu.getCampusLocations());
		course.setCode(clu.getOfficialIdentifier().getCode());		
		course.setCourseNumberSuffix(clu.getOfficialIdentifier().getSuffixCode());
		
		//TODO: LO
		//course.setCourseSpecificLOs();
		
		course.setCourseTitle(clu.getOfficialIdentifier().getLongName());

		//TODO: CrossListings
		//course.setCrossListings();
		
		course.setDepartment(clu.getPrimaryAdminOrg().getOrgId());
		course.setDescription(clu.getDescr());
		course.setDuration(clu.getStdDuration());
		course.setEffectiveDate(clu.getEffectiveDate());
		course.setExpirationDate(clu.getExpirationDate());
		
		//TODO: Fee
		//course.setFeeInfo(feeInfo)
		
		course.setFirstExpectedOffering(clu.getExpectedFirstAtp());
		course.setId(clu.getId());
		course.setOfferedAtpTypes(clu.getOfferedAtpTypes());
		course.setPrimaryInstructor(clu.getPrimaryInstructor());
		course.setState(clu.getState());
		course.setSubjectArea(clu.getOfficialIdentifier().getDivision());
		course.setTranscriptTitle(clu.getOfficialIdentifier().getShortName());
		
		
		//Don't make any changes to nested datastructures if this is 
		if(!shallowBuild){		
			try {
				// Use the luService to find Joints, then convert and add to the course
				List<CluCluRelationInfo> cluClus = luService.getCluCluRelationsByClu(clu.getId());	
				for (CluCluRelationInfo cluRel : cluClus) {
					if(cluRel.getType().equals(CourseAssemblerConstants.JOINT_RELATION_TYPE)){				
						CourseJointInfo jointInfo = courseJointAssembler.assemble(cluRel, null, false);
						course.getJoints().add(jointInfo);
					}
				}
			} catch (DoesNotExistException e) {
			} catch (Exception e) {
				throw new AssemblyException("Error getting course joints", e);
			} 
				
			try{					
				// Use the luService to find formats, then convert and add to the course			
				List<CluInfo> formats = luService.getRelatedClusByCluId(course
						.getId(), CourseAssemblerConstants.COURSE_FORMAT_RELATION_TYPE);			
				for (CluInfo format : formats) {
					FormatInfo formatInfo = formatAssembler.assemble(format, null, false);
					course.getFormats().add(formatInfo);
				}						
	
			} catch (DoesNotExistException e) {
			} catch (Exception e) {
				throw new AssemblyException("Error getting related formats", e);
			} 
		
		}
		
		//TODO: Variations
		//course.setVariations(variations)
		

		return course;
	}

	@Override
	public BaseDTOAssemblyNode<CourseInfo, CluInfo> disassemble(CourseInfo course,
			NodeOperation operation) throws AssemblyException {

		if (course == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
			throw new AssemblyException("Course can not be null");
		}

		BaseDTOAssemblyNode<CourseInfo, CluInfo> result = new BaseDTOAssemblyNode<CourseInfo, CluInfo>();

		CluInfo clu = new CluInfo();

		// Create the id if it's not there already(important for creating
		// relations)
		clu.setId(UUIDHelper.genStringUUID(course.getId()));
		clu.setType(course.getType());
		clu.setState(course.getState());

		// Add the Clu to the result
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(course);
		
		// Use the Format assembler to disassemble the formats and relations
		List<BaseDTOAssemblyNode<?,?>> formatResults = disassembleFormats(course,
				operation);
		result.getChildNodes().addAll(formatResults);

		return result;
	}

	// TODO This is pretty much a copy of the FormatAssembler's
	// disassembleActivities code... maybe can be made generic
	private List<BaseDTOAssemblyNode<?,?>> disassembleFormats(CourseInfo course,
			NodeOperation operation) throws AssemblyException {

		List<BaseDTOAssemblyNode<?,?>> results = new ArrayList<BaseDTOAssemblyNode<?,?>>();

		// Get the current formats and put them in a map of format id/relation
		// id
		Map<String, String> currentformatIds = new HashMap<String, String>();

		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<CluCluRelationInfo> formatRelationships = luService
						.getCluCluRelationsByClu(course.getId());
				for (CluCluRelationInfo formatRelation : formatRelationships) {
					if (CourseAssemblerConstants.COURSE_FORMAT_RELATION_TYPE.equals(formatRelation
							.getType())) {
						currentformatIds.put(formatRelation.getRelatedCluId(),
								formatRelation.getId());
					}
				}
			} catch (DoesNotExistException e) {
			} catch (InvalidParameterException e) {
				throw new AssemblyException("Error getting related formats", e);
			} catch (MissingParameterException e) {
				throw new AssemblyException("Error getting related formats", e);
			} catch (OperationFailedException e) {
				throw new AssemblyException("Error getting related formats", e);
			}
		}

		// Loop through all the formats in this course
		for (FormatInfo format : course.getFormats()) {

			// If this is a course create then all formats will be created
			if (NodeOperation.UPDATE.equals(operation)
					&& currentformatIds.containsKey(format.getId())) {
				// If the course already has this format, then just update the
				// format
				BaseDTOAssemblyNode<FormatInfo,CluInfo> formatNode = formatAssembler
						.disassemble(format, NodeOperation.UPDATE);
				results.add(formatNode);

				// remove this entry from the map so we can tell what needs to
				// be deleted at the end
				currentformatIds.remove(format.getId());
			} else if (!NodeOperation.DELETE.equals(operation)) {
				// the format does not exist, so create
				// Assemble and add the format
				BaseDTOAssemblyNode<FormatInfo,CluInfo> formatNode = formatAssembler
						.disassemble(format, NodeOperation.CREATE);
				results.add(formatNode);

				// Create the relationship and add it as well
				CluCluRelationInfo relation = new CluCluRelationInfo();
				relation.setCluId(course.getId());
				relation.setRelatedCluId(format.getId());// this should already
															// be set even if
															// it's a create
				relation.setType(CourseAssemblerConstants.COURSE_FORMAT_RELATION_TYPE);
				relation.setState(course.getState());

				BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo>();
				relationNode.setNodeData(relation);
				relationNode.setOperation(NodeOperation.CREATE);

				results.add(relationNode);
			}

			// Now any leftover format ids are no longer needed, so delete
			// formats and relations
			for (Entry<String, String> entry : currentformatIds.entrySet()) {
				// Create a new relation with the id of the relation we want to
				// delete
				CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
				relationToDelete.setId(entry.getValue());
				BaseDTOAssemblyNode<CourseInfo,CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<CourseInfo,CluCluRelationInfo>();
				relationToDeleteNode.setNodeData(relationToDelete);
				relationToDeleteNode.setOperation(NodeOperation.DELETE);
				results.add(relationToDeleteNode);

				// Create a new Clu Info with the id of the clu we want to
				// delete
				CluInfo formatToDelete = new CluInfo();
				formatToDelete.setId(entry.getKey());
				BaseDTOAssemblyNode<FormatInfo,CluInfo> formatToDeleteNode = new BaseDTOAssemblyNode<FormatInfo,CluInfo>();
				formatToDeleteNode.setNodeData(formatToDelete);
				formatToDeleteNode.setOperation(NodeOperation.DELETE);
				results.add(formatToDeleteNode);
			}
		}

		return results;
	}

			
	// TODO This is pretty much a copy of the disassembleJoints 
	// code... maybe can be made generic
	private List<BaseDTOAssemblyNode<?,?>> disassembleJoints(CourseInfo course,
			NodeOperation operation) throws AssemblyException {

		List<BaseDTOAssemblyNode<?,?>> results = new ArrayList<BaseDTOAssemblyNode<?,?>>();

		// Get the current joints and put them in a map of joint id/relation
		// id
		Map<String, String> currentJointIds = new HashMap<String, String>();

		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<CluCluRelationInfo> jointRelationships = luService
						.getCluCluRelationsByClu(course.getId());
				for (CluCluRelationInfo jointRelation : jointRelationships) {
					if (CourseAssemblerConstants.JOINT_RELATION_TYPE.equals(jointRelation
							.getType())) {
						currentJointIds.put(jointRelation.getRelatedCluId(),
								jointRelation.getId());
					}
				}
			} catch (DoesNotExistException e) {
			} catch (InvalidParameterException e) {
				throw new AssemblyException("Error getting related formats", e);
			} catch (MissingParameterException e) {
				throw new AssemblyException("Error getting related formats", e);
			} catch (OperationFailedException e) {
				throw new AssemblyException("Error getting related formats", e);
			}
		}

		// Loop through all the formats in this course
		for (CourseJointInfo joint : course.getJoints()) {

			// If this is a course create then all joints will be created
			if (NodeOperation.UPDATE.equals(operation)
					&& currentJointIds.containsKey(joint.getRelationId())) {				
				// remove this entry from the map so we can tell what needs to
				// be deleted at the end
				currentJointIds.remove(joint.getRelationId());
			} else if (!NodeOperation.DELETE.equals(operation)) {
				// the format does not exist, so create cluclurelation
				BaseDTOAssemblyNode<CourseJointInfo,CluCluRelationInfo> jointNode = courseJointAssembler
						.disassemble(joint, NodeOperation.CREATE);
				jointNode.getNodeData().setCluId(course.getId());
				results.add(jointNode);
			}

			// Now any leftover joint ids are no longer needed, so delete
			// joint relations
			for (Entry<String, String> entry : currentJointIds.entrySet()) {
				// Create a new relation with the id of the relation we want to
				// delete
				CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
				relationToDelete.setId(entry.getValue());
				BaseDTOAssemblyNode<CourseJointInfo,CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<CourseJointInfo,CluCluRelationInfo>();
				relationToDeleteNode.setNodeData(relationToDelete);
				relationToDeleteNode.setOperation(NodeOperation.DELETE);
				results.add(relationToDeleteNode);
			}
		}

		return results;
	}
	
	public LuService getLuService() {
		return luService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	public void setFormatAssembler(FormatAssembler formatAssembler) {
		this.formatAssembler = formatAssembler;
	}

	public FormatAssembler getFormatAssembler() {
		return formatAssembler;
	}
}
