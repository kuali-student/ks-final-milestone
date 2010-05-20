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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.course.dto.ActivityInfo;
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
public class FormatAssembler implements BOAssembler<FormatInfo, CluInfo> {
	final static Logger LOG = Logger.getLogger(FormatAssembler.class);
	
	private static final String COURSE_ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";
	
	private BOAssembler<ActivityInfo,CluInfo> activityAssembler;
	private LuService luService;
	
	@Override
	public FormatInfo assemble(CluInfo clu) throws AssemblyException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public BaseDTOAssemblyNode<CluInfo> disassemble(
			FormatInfo format, NodeOperation operation) throws AssemblyException {
		BaseDTOAssemblyNode<CluInfo> result = new BaseDTOAssemblyNode<CluInfo>();
		
		CluInfo clu = new CluInfo();
	
		clu.setId(format.getId());
		//Create the id if its not there already
		if(clu.getId()==null){
			clu.setId(UUIDHelper.genStringUUID());
		}
		clu.setType(format.getType());
		clu.setState(format.getState());
		clu.setMetaInfo(format.getMetaInfo());
		
		//TODO dynamic attributes
		
		//Use the Activity assembler to disassemble the activites and relations
		disassembleActivities(format, result);

		result.setNodeData(clu);
		result.setOperation(operation);
		
		return result;
	}

	private void disassembleActivities(FormatInfo format,
			BaseDTOAssemblyNode<CluInfo> result) throws AssemblyException {
		//Get the current activities and put them in a map of activity id/relation id 
		Map<String,String> currentActivityIds = new HashMap<String,String>();
		try {
			List<CluCluRelationInfo> currentActivities = luService.getCluCluRelationsByClu(format.getId());
			for(CluCluRelationInfo relation:currentActivities){
				if(COURSE_ACTIVITY_RELATION_TYPE.equals(relation.getType())){
					currentActivityIds.put(relation.getRelatedCluId(), relation.getId());
				}
			}
		} catch (DoesNotExistException e) {
		} catch (InvalidParameterException e) {
			throw new AssemblyException("Error getting related activities",e);
		} catch (MissingParameterException e) {
			throw new AssemblyException("Error getting related activities",e);
		} catch (OperationFailedException e) {
			throw new AssemblyException("Error getting related activities",e);
		}
		
		//Loop through all the activities in this format
		for(ActivityInfo activity:format.getActivities()){
			
			//See if this is an update, or an add(create)
			if(currentActivityIds.containsKey(activity.getId())){
				//If the format already has this activity, then just update the activity
				BaseDTOAssemblyNode<CluInfo> activityNode = activityAssembler.disassemble(activity,NodeOperation.UPDATE);
				result.getChildNodes().add(activityNode);
				
				//remove this entry from the map so we can tell what needs to be deleted at the end
				currentActivityIds.remove(activity.getId());
			}else{
				//the activity does not exist, so create
				//Assemble and add the activity
				BaseDTOAssemblyNode<CluInfo> activityNode = activityAssembler.disassemble(activity,NodeOperation.CREATE);
				result.getChildNodes().add(activityNode);
				
				//Create the relationship and add it as well
				CluCluRelationInfo relation = new CluCluRelationInfo();
				relation.setCluId(format.getId());
				relation.setRelatedCluId(activity.getId());//this should already be set even if it's a create
				relation.setType(COURSE_ACTIVITY_RELATION_TYPE);
				relation.setState(format.getState());
				
				BaseDTOAssemblyNode<CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<CluCluRelationInfo>();
				relationNode.setNodeData(relation);
				relationNode.setOperation(NodeOperation.CREATE);
				
				result.getChildNodes().add(relationNode);
			}
			
			//Now any leftover activity ids are no longer needed, so delete activities and relations
			for(Entry<String, String> entry:currentActivityIds.entrySet()){
				//Create a new relation with the id of the relation we want to delete
				CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
				relationToDelete.setId(entry.getValue());
				BaseDTOAssemblyNode<CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<CluCluRelationInfo>();
				relationToDeleteNode.setNodeData(relationToDelete);
				relationToDeleteNode.setOperation(NodeOperation.DELETE);
				result.getChildNodes().add(relationToDeleteNode);
				
				//Create a new Clu Info with the id of the clu we want to delete
				CluInfo activityToDelete = new CluInfo();
				activityToDelete.setId(entry.getKey());
				BaseDTOAssemblyNode<CluInfo> activityToDeleteNode = new BaseDTOAssemblyNode<CluInfo>();
				activityToDeleteNode.setNodeData(activityToDelete);
				activityToDeleteNode.setOperation(NodeOperation.DELETE);
				result.getChildNodes().add(activityToDeleteNode);
			}
			
		}
	}
}
