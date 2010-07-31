package org.kuali.student.lum.course.service.assembler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;


public class LoAssembler implements BOAssembler<LoDisplayInfo, LoInfo> {

	private LearningObjectiveService loService;
	
	@Override
	public LoDisplayInfo assemble(LoInfo lo, LoDisplayInfo loDisplayInfo,
			boolean shallowBuild) throws AssemblyException {
		
		LoDisplayInfo loDisplay = (null != loDisplayInfo) ? loDisplayInfo : new LoDisplayInfo();
		
		loDisplay.setLoInfo(lo);

		if (!shallowBuild) {
			String loId = lo.getId();
			try {
				List<LoCategoryInfo> loCategories = loService.getLoCategoriesForLo(loId);
				loDisplay.setLoCategoryInfoList(loCategories);
			} catch (DoesNotExistException e) {
			} catch (Exception e) {
				throw new AssemblyException("Error getting learning objective categories", e);
			}
			try {
				List<LoInfo> childLos = loService.getRelatedLosByLoId(loId,CourseAssemblerConstants.COURSE_LO_RELATION_INCLUDES);
				for(LoInfo childLo:childLos){
					LoDisplayInfo childLoDisplay = assemble(childLo, null, shallowBuild);
					childLoDisplay.setParentLoRelationid(lo.getId());
					childLoDisplay.setParentRelType(CourseAssemblerConstants.COURSE_LO_RELATION_INCLUDES);
					loDisplay.getLoDisplayInfoList().add(childLoDisplay);
				}
				if(loDisplay.getLoDisplayInfoList().size()>1){
					Collections.sort(loDisplay.getLoDisplayInfoList(), LoDisplayComparator.getInstance());
				}
			} catch (DoesNotExistException e) {
			} catch (Exception e) {
				throw new AssemblyException("Error getting learning objective", e);
			}

		}
		return loDisplay;
	}

	@Override
	//Creation of categories is done in the LoRpcGwtServlet
	public BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> disassemble(
			LoDisplayInfo loDisplay, NodeOperation operation)
			throws AssemblyException {
		
		BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> result = new BaseDTOAssemblyNode<LoDisplayInfo, LoInfo>(this);
		
		//see if this is a new LuInfo
		if (loDisplay == null || loDisplay.getLoInfo() == null) {
			throw new AssemblyException("Lo can not be null");
		}
		if (NodeOperation.CREATE != operation && null == loDisplay.getLoInfo().getId()) {
			throw new AssemblyException("Lo id can not be null");
		}
		
		//set the id if it's not there already(important for creating relations)
		loDisplay.getLoInfo().setId(UUIDHelper.genStringUUID(loDisplay.getLoInfo().getId()));
		
		//Default these values
		loDisplay.getLoInfo().setType(CourseAssemblerConstants.COURSE_LO_TYPE);
		loDisplay.getLoInfo().setLoRepositoryKey(CourseAssemblerConstants.COURSE_LO_REPOSITORY_KEY);
		
		
		//Populate the node
		result.setBusinessDTORef(loDisplay);
		result.setNodeData(loDisplay.getLoInfo());
		result.setOperation(operation);
		
		//Process the child los
		try {
			List<BaseDTOAssemblyNode<?, ?>> childLoNodes = disassembleChildLos(loDisplay, operation);
			result.getChildNodes().addAll(childLoNodes);
		} catch (DoesNotExistException e) {
		} catch (Exception e) {
			throw new AssemblyException("Error disassembling child los", e);
		} 

		//Process the categories
		try {
			List<BaseDTOAssemblyNode<?, ?>> categoryNodes = disassembleCategories(loDisplay, operation);
			result.getChildNodes().addAll(categoryNodes);
		} catch (Exception e) {
			throw new AssemblyException("Error disassembling categories", e);
		} 
		
		return result;
	}

	private List<BaseDTOAssemblyNode<?, ?>> disassembleCategories(
			LoDisplayInfo loDisplay, NodeOperation operation) throws AssemblyException {
		
		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();
		
		//Category relations
		Set<String> currentCategoryIds = new HashSet<String>();
		//Get current relations
		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<LoCategoryInfo> categories = loService.getLoCategoriesForLo(loDisplay.getLoInfo().getId());
				for (LoCategoryInfo category : categories) {
					currentCategoryIds.add(category.getId());
				}
			} catch (DoesNotExistException e) {
			} catch (Exception e) {
				throw new AssemblyException("Error getting categories",	e);
			}
		}
		//Update
		for (LoCategoryInfo category : loDisplay.getLoCategoryInfoList()) {

			// If this is a format create/new activity update then all activities will be created
		    if (NodeOperation.CREATE == operation
		            || (NodeOperation.UPDATE == operation &&  !currentCategoryIds.contains(category.getId()))) {
		    	
		    	LoCategoryRelationInfo loCategoryRelation = new LoCategoryRelationInfo();
		    	loCategoryRelation.setCategoryId(category.getId());
		    	loCategoryRelation.setLoId(loDisplay.getLoInfo().getId());
		    	
                BaseDTOAssemblyNode<LoDisplayInfo, LoCategoryRelationInfo> relationToAddNode = new BaseDTOAssemblyNode<LoDisplayInfo, LoCategoryRelationInfo>(null);
                relationToAddNode.setNodeData(loCategoryRelation);
                relationToAddNode.setOperation(NodeOperation.CREATE);
                results.add(relationToAddNode);

                currentCategoryIds.remove(category.getId());
		    } else if (NodeOperation.UPDATE == operation
					&& currentCategoryIds.contains(category.getId())) {
                currentCategoryIds.remove(category.getId());
			} 
		}
		//Delete leftovers
		for(String categoryId:currentCategoryIds){
	    	LoCategoryRelationInfo loCategoryRelation = new LoCategoryRelationInfo();
	    	loCategoryRelation.setCategoryId(categoryId);
	    	loCategoryRelation.setLoId(loDisplay.getLoInfo().getId());
	    	
            BaseDTOAssemblyNode<LoDisplayInfo, LoCategoryRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<LoDisplayInfo, LoCategoryRelationInfo>(null);
            relationToDeleteNode.setNodeData(loCategoryRelation);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);
		}
		
		return results;
	}

	private List<BaseDTOAssemblyNode<?, ?>> disassembleChildLos(LoDisplayInfo loDisplay, NodeOperation operation) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AssemblyException{
		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();
		Map<String,LoLoRelationInfo> currentLoRelations = new HashMap<String,LoLoRelationInfo>();
		//Make lu lu relations
		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<LoLoRelationInfo> loRelations = loService.getLoLoRelationsByLoId(loDisplay.getLoInfo().getId());
				for (LoLoRelationInfo loRelation : loRelations) {
					//getLoLoRelationsByLoId returns if the lo is related or if it is the owner(this seems wrong)
					if(CourseAssemblerConstants.COURSE_LO_RELATION_INCLUDES.equals(loRelation.getType())&&
							!loDisplay.getLoInfo().getId().equals(loRelation.getRelatedLoId())){
						currentLoRelations.put(loRelation.getRelatedLoId(), loRelation);
					}
				}
			} catch (DoesNotExistException e) {
			} catch (Exception e) {
				throw new AssemblyException("Error getting categories",	e);
			}
		}
		
		// Loop through all the activities in this format
		for (LoDisplayInfo childDisplay : loDisplay.getLoDisplayInfoList()) {

			// If this is a format create/new activity update then all activities will be created
		    if (NodeOperation.CREATE == operation
		            || (NodeOperation.UPDATE == operation &&  !currentLoRelations.containsKey(childDisplay.getLoInfo().getId()))) {
		        
                // the lo does not exist, so create
                // Assemble and add the lo
                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = this
                        .disassemble(childDisplay, NodeOperation.CREATE);
                results.add(loNode);

                // Create the relationship and add it as well
                LoLoRelationInfo relation = new LoLoRelationInfo();
                relation.setLoId(loDisplay.getLoInfo().getId());
                relation.setRelatedLoId(loNode.getNodeData().getId());
                relation.setType(CourseAssemblerConstants.COURSE_LO_RELATION_INCLUDES);
                relation.setState(loDisplay.getLoInfo().getState());
                

                BaseDTOAssemblyNode<LoDisplayInfo, LoLoRelationInfo> relationNode = new BaseDTOAssemblyNode<LoDisplayInfo, LoLoRelationInfo>(
                        null);
                relationNode.setNodeData(relation);
                relationNode.setOperation(NodeOperation.CREATE);

                results.add(relationNode);
            } else if (NodeOperation.UPDATE == operation
					&& currentLoRelations.containsKey(childDisplay.getLoInfo().getId())) {
				// If the lo already has this child lo, then just update the
				// child lo
				BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = this
						.disassemble(childDisplay, NodeOperation.UPDATE);
				results.add(loNode);

				// remove this entry from the map so we can tell what needs to
				// be deleted at the end
				currentLoRelations.remove(childDisplay.getLoInfo().getId());
			} else if (NodeOperation.DELETE == operation
                    && currentLoRelations.containsKey(childDisplay.getLoInfo().getId())) {
			    
                // Delete the Format and its relation
				LoLoRelationInfo relationToDelete = currentLoRelations.get(childDisplay.getLoInfo().getId());
                BaseDTOAssemblyNode<LoDisplayInfo, LoLoRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<LoDisplayInfo, LoLoRelationInfo>(
                        null);
                relationToDeleteNode.setNodeData(relationToDelete);
                relationToDeleteNode.setOperation(NodeOperation.DELETE);
                results.add(relationToDeleteNode);
            
                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = this
                .disassemble(childDisplay, NodeOperation.DELETE);
                results.add(loNode);                                

                // remove this entry from the map so we can tell what needs to
                // be deleted at the end
                currentLoRelations.remove(childDisplay.getLoInfo().getId());			    
			}
		}         

        // Now any leftover lo ids are no longer needed, so delete
        // los and relations
        for (Entry<String, LoLoRelationInfo> entry : currentLoRelations.entrySet()) {
            // Create a new relation with the id of the relation we want to
            // delete
        	LoLoRelationInfo relationToDelete = entry.getValue();
            BaseDTOAssemblyNode<LoDisplayInfo, LoLoRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<LoDisplayInfo, LoLoRelationInfo>(
                    null);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);

            LoInfo loToDelete = loService.getLo(entry.getKey());
            LoDisplayInfo loDisplayToDelete = this.assemble(loToDelete, null, false);
            BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = this.disassemble(loDisplayToDelete, NodeOperation.DELETE);
            results.add(loNode);                                            
        }
		return results;
		
	}

	public void setLoService(LearningObjectiveService loService) {
		this.loService = loService;
	}

	public static class LoDisplayComparator implements Comparator<LoDisplayInfo>{
		private static LoDisplayComparator instance = new LoDisplayComparator();
		@Override
		public int compare(LoDisplayInfo o1, LoDisplayInfo o2) {
			String o1Sequence = o1.getLoInfo().getAttributes().get(CourseAssemblerConstants.COURSE_LO_SEQUENCE);
			String o2Sequence = o1.getLoInfo().getAttributes().get(CourseAssemblerConstants.COURSE_LO_SEQUENCE);
			if(o1Sequence!=null){
				return o1Sequence.compareTo(o2Sequence);
			}else if(o2Sequence!=null){
				return -1;
			}
			return 0;
		}
		public static LoDisplayComparator getInstance() {
			return instance;
		}
	}
}
