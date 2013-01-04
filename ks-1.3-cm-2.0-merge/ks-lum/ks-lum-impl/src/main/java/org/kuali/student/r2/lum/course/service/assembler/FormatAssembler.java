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

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Assembler for FormatInfo. Assembles/Disassemble FormatInfo from CluInfo and
 * other structures.
 * 
 * @author Kuali Student Team
 * 
 */
public class FormatAssembler implements BOAssembler<FormatInfo, CluInfo> {
	final static Logger LOG = Logger.getLogger(FormatAssembler.class);

	private BOAssembler<ActivityInfo, CluInfo> activityAssembler;
	private CluService luService;

	@Override
	public FormatInfo assemble(CluInfo clu, FormatInfo formatInfo,
			boolean shallowBuild,ContextInfo contextInfo) throws AssemblyException {

		if (clu == null) {
			return null;
		}

		FormatInfo format = (null != formatInfo) ? formatInfo
				: new FormatInfo();

		// Copy base properties
		format.setId(clu.getId());
		format.setTypeKey(clu.getTypeKey());
		format.setStateKey(clu.getStateKey());
		format.setMeta(clu.getMeta());
		format.setAttributes(clu.getAttributes());
	    format.setDuration(clu.getStdDuration());
	    format.setTermsOffered(clu.getOfferedAtpTypes());

		// Don't make any changes to nested datastructures if this is
		if (!shallowBuild) {
			// Use the luService to find activities, then convert and add to the
			// format
			try {
				List<CluInfo> activities = luService.getRelatedClusByCluAndRelationType(
										format.getId(),
										CourseAssemblerConstants.COURSE_ACTIVITY_RELATION_TYPE,
                                        contextInfo);
				for (CluInfo activity : activities) {
					ActivityInfo activityInfo = activityAssembler.assemble(
							activity, null, false,contextInfo);
					format.getActivities().add(activityInfo);
				}

			} catch (Exception e) {
				throw new AssemblyException("Error getting related activities", e);
			} 
		}
		return format;
	}

	@Override
	public BaseDTOAssemblyNode<FormatInfo, CluInfo> disassemble(
			FormatInfo format, NodeOperation operation,ContextInfo contextInfo)
			throws AssemblyException {
		BaseDTOAssemblyNode<FormatInfo, CluInfo> result = new BaseDTOAssemblyNode<FormatInfo, CluInfo>(
				this);
		if (format == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
			throw new AssemblyException("Format can not be null");
		}
		if (NodeOperation.CREATE != operation && null == format.getId()) {
			throw new AssemblyException("Course Format Shell's id can not be null");
		}

		CluInfo clu;
        try {
        	clu = null;
          
        	clu = (NodeOperation.UPDATE == operation) ? clu = luService.getClu(format.getId(),contextInfo) : new CluInfo();
        } catch (Exception e) {
            throw new AssemblyException("Error retrieving course format shell during update", e);
        } 

		// Copy all fields
		clu.setId(UUIDHelper.genStringUUID(format.getId()));// Create the id if
															// it's not there
															// already(important
															// for creating
															// relations)

		clu.setTypeKey(CourseAssemblerConstants.COURSE_FORMAT_TYPE);
		clu.setStateKey(format.getStateKey());
		clu.setIsEnrollable(false);
	
		clu.setMeta(format.getMeta());

		clu.setStdDuration(format.getDuration());
		clu.setOfferedAtpTypes(format.getTermsOffered());

		// Add the Clu to the result
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(format);

		// Use the Activity assembler to disassemble the activities and
		// relations
		List<BaseDTOAssemblyNode<?, ?>> activityResults;
        try {
            activityResults = disassembleActivities(clu.getId(),
            		format, operation,contextInfo);
            result.getChildNodes().addAll(activityResults);
            
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling format", e);
        }
        
		return result;
	}

	/**
	 * This method will return assembly nodes representing activities and
	 * activity->format relations for a format based on the operation CREATE:
	 * all activities and format-> activity relations will be created UPDATE:
	 * activities will be taken from the luService and compared with the
	 * incomming format's activities. Any new activites will be created with a
	 * corresponding CluCluRelation Any existing activities will be Updated All
	 * leftover activities and their CluCluRelations will be deleted DELETE: all
	 * activities and their CluCluRelations will be deleted
	 * 
	 * If the Operation
	 * 
     * @param nodeId
	 * @param format
	 * @param operation
	 * @return List of Assembly nodes
	 * @throws AssemblyException
	 * @throws OperationFailedException 
	 * @throws MissingParameterException 
	 * @throws InvalidParameterException 
	 * @throws DoesNotExistException 
	 * @throws PermissionDeniedException 
	 */
	private List<BaseDTOAssemblyNode<?, ?>> disassembleActivities(String nodeId,
			FormatInfo format, NodeOperation operation,ContextInfo contextInfo)
			throws AssemblyException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

		// Get the current activities and put them in a map of activity
		// id/relation id
		Map<String, String> currentActivityIds = new HashMap<String, String>();

		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<CluCluRelationInfo> activityRelationships = luService.getCluCluRelationsByClu(format.getId(),contextInfo);
				
				for (CluCluRelationInfo activityRelation : activityRelationships) {
										if (CourseAssemblerConstants.COURSE_ACTIVITY_RELATION_TYPE
												.equals(activityRelation.getTypeKey())) {
											currentActivityIds.put(activityRelation
					.getRelatedCluId(), activityRelation.getId());
										}
				}

			} catch (Exception e) {
				throw new AssemblyException("Error getting related activities",	e);
			}
		}

		// Loop through all the activities in this format
		for (ActivityInfo activity : format.getActivities()) {

			// If this is a format create/new activity update then all activities will be created
		    if (NodeOperation.CREATE == operation
		            || (NodeOperation.UPDATE == operation &&  !currentActivityIds.containsKey(activity.getId()))) {
		    			    	activity.setStateKey(format.getStateKey());
                // the activity does not exist, so create
                // Assemble and add the activity
                BaseDTOAssemblyNode<ActivityInfo, CluInfo> activityNode = activityAssembler
                        .disassemble(activity, NodeOperation.CREATE,contextInfo);
                results.add(activityNode);

                // Create the relationship and add it as well
                CluCluRelationInfo relation = new CluCluRelationInfo();
                relation.setCluId(nodeId);
                relation.setRelatedCluId(activityNode.getNodeData().getId());// this should
                                                            // already be set
                                                            // even if it's a
                                                            // create

                relation.setTypeKey(CourseAssemblerConstants.COURSE_ACTIVITY_RELATION_TYPE);
                relation.setStateKey(format.getStateKey());

                BaseDTOAssemblyNode<FormatInfo, CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<FormatInfo, CluCluRelationInfo>(
                        null);
                relationNode.setNodeData(relation);
                relationNode.setOperation(NodeOperation.CREATE);

                results.add(relationNode);
            } else if (NodeOperation.UPDATE == operation
					&& currentActivityIds.containsKey(activity.getId())) {
				// If the format already has this activity, then just update the
				// activity
            	            	activity.setStateKey(format.getStateKey());
				BaseDTOAssemblyNode<ActivityInfo, CluInfo> activityNode = activityAssembler
						.disassemble(activity, NodeOperation.UPDATE,contextInfo);
				results.add(activityNode);

				// remove this entry from the map so we can tell what needs to
				// be deleted at the end
				currentActivityIds.remove(activity.getId());
			} else if (NodeOperation.DELETE == operation
                    && currentActivityIds.containsKey(activity.getId())) {
			    
                // Delete the Format and its relation
                CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
                relationToDelete.setId( currentActivityIds.get(activity.getId()) );
                BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo>(
                        null);
                relationToDeleteNode.setNodeData(relationToDelete);
                relationToDeleteNode.setOperation(NodeOperation.DELETE);
                results.add(relationToDeleteNode);
            
                BaseDTOAssemblyNode<ActivityInfo, CluInfo> formatNode = activityAssembler
                .disassemble(activity, NodeOperation.DELETE,contextInfo);
                results.add(formatNode);                                

                // remove this entry from the map so we can tell what needs to
                // be deleted at the end
                currentActivityIds.remove(activity.getId());			    
			}
		}         

        // Now any leftover activity ids are no longer needed, so delete
        // activities and relations
        for (Entry<String, String> entry : currentActivityIds.entrySet()) {
            // Create a new relation with the id of the relation we want to
            // delete
            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
            relationToDelete.setId(entry.getValue());
            BaseDTOAssemblyNode<FormatInfo, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<FormatInfo, CluCluRelationInfo>(
                    null);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);

            CluInfo activityCluToDelete = luService.getClu(entry.getKey(), contextInfo);

            luService.getClu(entry.getKey(),contextInfo);
            ActivityInfo activityToDelete = activityAssembler.assemble(activityCluToDelete, null, false,contextInfo);
            BaseDTOAssemblyNode<ActivityInfo, CluInfo> activityNode = activityAssembler
            .disassemble(activityToDelete, NodeOperation.DELETE,contextInfo);
            results.add(activityNode);                                            
        }
       
		
		return results;
	}

	public BOAssembler<ActivityInfo, CluInfo> getActivityAssembler() {
		return activityAssembler;
	}

	public void setActivityAssembler(
			BOAssembler<ActivityInfo, CluInfo> activityAssembler) {
		this.activityAssembler = activityAssembler;
	}

	public CluService getCluService() {
		return luService;
	}

	public void setCluService(CluService luService) {
		this.luService = luService;
	}
}
