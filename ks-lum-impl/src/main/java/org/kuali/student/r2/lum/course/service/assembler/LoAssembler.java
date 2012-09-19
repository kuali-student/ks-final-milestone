package org.kuali.student.r2.lum.course.service.assembler;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;

import java.util.*;
import java.util.Map.Entry;


public class LoAssembler implements BOAssembler<LoDisplayInfo, LoInfo> {

	private LearningObjectiveService loService;
	

	@Override
	public LoDisplayInfo assemble(LoInfo lo, LoDisplayInfo loDisplayInfo,
			boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException {
		
		LoDisplayInfo loDisplay = (null != loDisplayInfo) ? loDisplayInfo : new LoDisplayInfo();
		
		loDisplay.setLoInfo(lo);

		if (!shallowBuild) {
			String loId = lo.getId();
			try {
				List<LoCategoryInfo> loCategories = null;
                loService.getLoCategoriesByLo(loId, contextInfo);
				loDisplay.setLoCategoryInfoList(loCategories);
			} catch (DoesNotExistException e) {
			} catch (Exception e) {
				throw new AssemblyException("Error getting learning objective categories", e);
			}
			try {
				List<LoInfo> childLos = loService.getRelatedLosByLoId(loId, CourseAssemblerConstants.COURSE_LO_RELATION_INCLUDES, contextInfo);
				for(LoInfo childLo:childLos){
					LoDisplayInfo childLoDisplay = assemble(childLo, null, shallowBuild,contextInfo);
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


	//Creation of categories is done in the LoCategoryRpcGwtServlet
	public BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> disassemble(
			LoDisplayInfo loDisplay, NodeOperation operation,ContextInfo contextInfo)
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
				
		loDisplay.getLoInfo().setTypeKey(CourseAssemblerConstants.COURSE_LO_TYPE);
		 
		loDisplay.getLoInfo().setLoRepositoryKey(CourseAssemblerConstants.COURSE_LO_REPOSITORY_KEY);
		
		
		//Populate the node
		result.setBusinessDTORef(loDisplay);
		result.setNodeData(loDisplay.getLoInfo());
		result.setOperation(operation);
		
		//Process the child los
		try {
			List<BaseDTOAssemblyNode<?, ?>> childLoNodes = disassembleChildLos(loDisplay, operation,contextInfo);
			result.getChildNodes().addAll(childLoNodes);
		} catch (DoesNotExistException e) {
		} catch (Exception e) {
			throw new AssemblyException("Error disassembling child los", e);
		} 

		//Process the categories
		try {
			List<BaseDTOAssemblyNode<?, ?>> categoryNodes = disassembleCategories(loDisplay, operation,contextInfo);
			result.getChildNodes().addAll(categoryNodes);
		} catch (Exception e) {
			throw new AssemblyException("Error disassembling categories", e);
		} 
		
		return result;
	}

	private List<BaseDTOAssemblyNode<?, ?>> disassembleCategories(
			LoDisplayInfo loDisplay, NodeOperation operation,ContextInfo contextInfo) throws AssemblyException {
		
		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();
		
		//Category relations
		Set<String> currentCategoryIds = new HashSet<String>();
		//Get current relations
		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<LoCategoryInfo> categories = loService.getLoCategoriesByLo(loDisplay.getLoInfo().getId(), contextInfo);
				for (LoCategoryInfo category : categories) {
					currentCategoryIds.add(category.getId());
				}
				
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

	private List<BaseDTOAssemblyNode<?, ?>> disassembleChildLos(LoDisplayInfo loDisplay, NodeOperation operation, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AssemblyException, PermissionDeniedException{
		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();
		Map<String,LoLoRelationInfo> currentLoRelations = new HashMap<String,LoLoRelationInfo>();
		//Make lu lu relations
		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<LoLoRelationInfo> loRelations = loService.getLoLoRelationsByLoId(loDisplay.getLoInfo().getId(), contextInfo);
				for (LoLoRelationInfo loRelation : loRelations) {
					//getLoLoRelationsByLoId returns if the lo is related or if it is the owner(this seems wrong)
					
					if(CourseAssemblerConstants.COURSE_LO_RELATION_INCLUDES.equals(loRelation.getTypeKey())&&
							!loDisplay.getLoInfo().getId().equals(loRelation.getRelatedLoId())){
						currentLoRelations.put(loRelation.getRelatedLoId(), loRelation);
					}
				}
				
			} catch (Exception e) {
				throw new AssemblyException("Error getting categories",	e);
			}
		}
		
		// Loop through all the activities in this format
		for (LoDisplayInfo childDisplay : loDisplay.getLoDisplayInfoList()) {
		    
		    // Set the state of the child LO to match the state of the parent
		    // LO. This end up propagating the program state to all of the LOs,
		    // since we set parent LO state to program state earlier in the code
					    
			childDisplay.getLoInfo().setStateKey(loDisplay.getLoInfo().getStateKey());
		    
			// If this is a format create/new activity update then all activities will be created
		    if (NodeOperation.CREATE == operation
		            || (NodeOperation.UPDATE == operation &&  !currentLoRelations.containsKey(childDisplay.getLoInfo().getId()))) {
		        		    
                // the lo does not exist, so create
                // Assemble and add the lo
		    	childDisplay.getLoInfo().setId(null);
                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = this
                        .disassemble(childDisplay, NodeOperation.CREATE,contextInfo);
                results.add(loNode);

                // Create the relationship and add it as well
                LoLoRelationInfo relation = new LoLoRelationInfo();
                relation.setLoId(loDisplay.getLoInfo().getId());
                relation.setRelatedLoId(loNode.getNodeData().getId());
             
                relation.setTypeKey(CourseAssemblerConstants.COURSE_LO_RELATION_INCLUDES);
                
                // Relations can only have states of Active or SUSPENDED
                // DO NOT use states like Approve, Draft, etc on relations
                // Will default to Active
                        
                relation.setStateKey(DtoConstants.STATE_ACTIVE);
                

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
						.disassemble(childDisplay, NodeOperation.UPDATE,contextInfo);
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
                .disassemble(childDisplay, NodeOperation.DELETE,contextInfo);
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
            
            LoInfo temploTODelete = loService.getLo(entry.getKey(), new ContextInfo());
            LoDisplayInfo loDisplayToDelete = this.assemble(temploTODelete, null, false,contextInfo);
            BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = this.disassemble(loDisplayToDelete, NodeOperation.DELETE,contextInfo);
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
			
		    String o1Sequence = null;
		    for(AttributeInfo attribute : o1.getLoInfo().getAttributes()){
		        if (attribute.getKey().equals(CourseAssemblerConstants.COURSE_LO_SEQUENCE)){
		            o1Sequence = attribute.getValue(); 
		        }
		    }
		    
		    String o2Sequence = null;
            for(AttributeInfo attribute : o2.getLoInfo().getAttributes()){
                if (attribute.getKey().equals(CourseAssemblerConstants.COURSE_LO_SEQUENCE)){
                    o2Sequence = attribute.getValue(); 
                }
            }
			
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
