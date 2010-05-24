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
	
	private BOAssembler<ActivityInfo,CluInfo> activityAssembler;
	private LuService luService;
	
	@Override
	public FormatInfo assemble(CluInfo clu, FormatInfo formatInfo, boolean shallowBuild) throws AssemblyException {
		
		if(clu==null){
			return null;
		}

		FormatInfo format = (null != formatInfo) ? formatInfo : new FormatInfo();
		
		//Copy base properties
		format.setId(clu.getId());
		format.setType(clu.getType());
		format.setState(clu.getState());
		format.setMetaInfo(clu.getMetaInfo());
		format.setAttributes(clu.getAttributes());
		
		//Use the luService to find activities, then convert and add to the format
		try {
			List<CluInfo> activities = luService.getRelatedClusByCluId(format.getId(), CourseAssemblerConstants.COURSE_ACTIVITY_RELATION_TYPE);
			for(CluInfo activity:activities){
				ActivityInfo activityInfo = activityAssembler.assemble(activity, null, false);
				format.getActivities().add(activityInfo);
			}
		} catch (DoesNotExistException e) {
		} catch (InvalidParameterException e) {
			throw new AssemblyException("Error getting related activities",e);
		} catch (MissingParameterException e) {
			throw new AssemblyException("Error getting related activities",e);
		} catch (OperationFailedException e) {
			throw new AssemblyException("Error getting related activities",e);
		}
		
		return format;
	}

	@Override
	public BaseDTOAssemblyNode<FormatInfo, CluInfo> disassemble(
			FormatInfo format, NodeOperation operation) throws AssemblyException {
		BaseDTOAssemblyNode<FormatInfo, CluInfo> result = new BaseDTOAssemblyNode<FormatInfo, CluInfo>();
		if(format==null){
			//FIXME Unsure now if this is an exception or just return null or empty assemblyNode 
			throw new AssemblyException("Format can not be null");
		}
		
		CluInfo clu = new CluInfo();
	
		//Copy all fields 
		clu.setId(UUIDHelper.genStringUUID(format.getId()));//Create the id if it's not there already(important for creating relations)
		clu.setType(format.getType());
		clu.setState(format.getState());
		clu.setMetaInfo(format.getMetaInfo());
		clu.setAttributes(format.getAttributes());
		
		//Add the Clu to the result 
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(format);

		//Use the Activity assembler to disassemble the activities and relations
		List<BaseDTOAssemblyNode<?,?>> activityResults = disassembleActivities(format, operation);
		result.getChildNodes().addAll(activityResults);
		
		return result;
	}

	/**
	 * This method will return assembly nodes representing activities and activity->format relations
	 * for a format based on the operation
	 * 	CREATE: all activities and format-> activity relations will be created
	 * 	UPDATE: activities will be taken from the luService and compared with the incomming format's activities.
	 * 		Any new activites will be created with a corresponding CluCluRelation
	 * 		Any existing activities will be Updated
	 * 		All leftover activities and their CluCluRelations will be deleted
	 * 	DELETE: all activities and their CluCluRelations will be deleted
	 * 
	 * If the Operation
	 * @param format
	 * @param result
	 * @param operation
	 * @return List of Assembly nodes 
	 * @throws AssemblyException
	 */
	private List<BaseDTOAssemblyNode<?,?>> disassembleActivities(FormatInfo format, NodeOperation operation) throws AssemblyException {
		List<BaseDTOAssemblyNode<?,?>> results = new ArrayList<BaseDTOAssemblyNode<?,?>>();
		
		//Get the current activities and put them in a map of activity id/relation id 
		Map<String,String> currentActivityIds = new HashMap<String,String>();
		
		if(!NodeOperation.CREATE.equals(operation)){
			try {
				List<CluCluRelationInfo> activityRelationships = luService.getCluCluRelationsByClu(format.getId());
				for(CluCluRelationInfo activityRelation:activityRelationships){
					if(CourseAssemblerConstants.COURSE_ACTIVITY_RELATION_TYPE.equals(activityRelation.getType())){
						currentActivityIds.put(activityRelation.getRelatedCluId(), activityRelation.getId());
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
		}
		
		//Loop through all the activities in this format
		for(ActivityInfo activity:format.getActivities()){
			
			//If this is a format create then all activities will be created
			if(NodeOperation.UPDATE.equals(operation)&&currentActivityIds.containsKey(activity.getId())){
				//If the format already has this activity, then just update the activity
				BaseDTOAssemblyNode<ActivityInfo,CluInfo> activityNode = activityAssembler.disassemble(activity,NodeOperation.UPDATE);
				results.add(activityNode);
				
				//remove this entry from the map so we can tell what needs to be deleted at the end
				currentActivityIds.remove(activity.getId());
			}else if(!NodeOperation.DELETE.equals(operation)){
				//the activity does not exist, so create
				//Assemble and add the activity
				BaseDTOAssemblyNode<ActivityInfo,CluInfo> activityNode = activityAssembler.disassemble(activity,NodeOperation.CREATE);
				results.add(activityNode);
				
				//Create the relationship and add it as well
				CluCluRelationInfo relation = new CluCluRelationInfo();
				relation.setCluId(format.getId());
				relation.setRelatedCluId(activity.getId());//this should already be set even if it's a create
				relation.setType(CourseAssemblerConstants.COURSE_ACTIVITY_RELATION_TYPE);
				relation.setState(format.getState());
				
				BaseDTOAssemblyNode<FormatInfo,CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<FormatInfo,CluCluRelationInfo>();
				relationNode.setNodeData(relation);
				relationNode.setOperation(NodeOperation.CREATE);
				
				results.add(relationNode);
			}
			
			//Now any leftover activity ids are no longer needed, so delete activities and relations
			for(Entry<String, String> entry:currentActivityIds.entrySet()){
				//Create a new relation with the id of the relation we want to delete
				CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
				relationToDelete.setId(entry.getValue());
				BaseDTOAssemblyNode<FormatInfo, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<FormatInfo, CluCluRelationInfo>();
				relationToDeleteNode.setNodeData(relationToDelete);
				relationToDeleteNode.setOperation(NodeOperation.DELETE);
				results.add(relationToDeleteNode);
				
				//Create a new Clu Info with the id of the clu we want to delete
				CluInfo activityToDelete = new CluInfo();
				activityToDelete.setId(entry.getKey());
				BaseDTOAssemblyNode<ActivityInfo, CluInfo> activityToDeleteNode = new BaseDTOAssemblyNode<ActivityInfo, CluInfo>();
				activityToDeleteNode.setNodeData(activityToDelete);
				activityToDeleteNode.setOperation(NodeOperation.DELETE);
				results.add(activityToDeleteNode);
			}
		}
		
		return results;
	}
}
