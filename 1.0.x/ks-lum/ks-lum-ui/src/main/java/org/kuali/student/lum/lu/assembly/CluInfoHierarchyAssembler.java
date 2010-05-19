/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.assembly;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy.ModificationState;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

/**
 * 
 * @deprecated - Functionality moved to CourseAssembler
 *
 */public class CluInfoHierarchyAssembler implements Assembler<CluInfoHierarchy, Void> {
	public static class RelationshipHierarchy {
		private final String relationshipType;
		private final String relationshipState;
		private final List<RelationshipHierarchy> children = new ArrayList<RelationshipHierarchy>();  
		public RelationshipHierarchy() {
			this.relationshipType = null;
			this.relationshipState = null;
		}
		public RelationshipHierarchy(final String relationshipType, final String relationshipState) {
			this.relationshipType = relationshipType;
			this.relationshipState = relationshipState;
		}
		public RelationshipHierarchy addChild(RelationshipHierarchy child) {
			children.add(child);
			return this;
		}
		public String getRelationshipType() {
			return relationshipType;
		}
		public List<RelationshipHierarchy> getChildren() {
			return children;
		}
		public String getRelationshipState() {
			return relationshipState;
		}
		
	}
	private LuService luService;
	private RelationshipHierarchy hierarchy;
	public static final String JOINT_RELATION_TYPE = "kuali.lu.relation.type.co-located";

	public RelationshipHierarchy getHierarchy() {
		return hierarchy;
	}


	public void setHierarchy(RelationshipHierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}


	public void setLuService(LuService luService) {
		this.luService = luService;
	}


	@Override
	public CluInfoHierarchy assemble(Void input) throws AssemblyException {
		throw new UnsupportedOperationException("assembly from target type not supported");
	}


	@Override
	public Void disassemble(CluInfoHierarchy input) throws AssemblyException {
		throw new UnsupportedOperationException("disassembly to target type not supported");
	}


	@Override
	public CluInfoHierarchy get(String id) throws AssemblyException {
		CluInfoHierarchy result = null;
		try {
			CluInfo clu = luService.getClu(id);
			if (clu != null) {
				result = new CluInfoHierarchy();
				result.setCluInfo(clu);
				for (RelationshipHierarchy h : hierarchy.getChildren()) {
					build(result, h);
				}
			}
		} catch (Exception e) {
			throw new AssemblyException(e);
		}
		return result;
	}

	private void build(CluInfoHierarchy currentClu, RelationshipHierarchy currentRel) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		System.out.println("Retrieving relation: " + currentClu.getCluInfo().getId() + "\t" + currentRel.getRelationshipType());
		List<CluCluRelationInfo> children = luService.getCluCluRelationsByClu(currentClu.getCluInfo().getId());
		if (children != null) {
			for (CluCluRelationInfo rel : children) {
				if(rel.getType().equals(CourseAssembler.JOINT_RELATION_TYPE)){
					// if the cluclu realtion is of type jointCourses than dont add that as a child to the CluInfoHierarchy object
					return;
				}
				CluInfo clu = luService.getClu(rel.getRelatedCluId());
				CluInfoHierarchy c = new CluInfoHierarchy();
				c.setParentRelationType(currentRel.getRelationshipType());
				c.setParentRelationState(currentRel.getRelationshipState());
				c.setCluInfo(clu);
				currentClu.getChildren().add(c);
				for (RelationshipHierarchy h : currentRel.getChildren()) {
					build(c, h);
				}
			}
		}
	}

	@Override
	public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
		throw new UnsupportedOperationException("Assembler is not type/state specific");
	}


	@Override
	public SaveResult<CluInfoHierarchy> save(CluInfoHierarchy input)
			throws AssemblyException {
		try {
			List<ValidationResultInfo> val = validate(input);
			SaveResult<CluInfoHierarchy> result = new SaveResult<CluInfoHierarchy>();
			result.setValidationResults(val);
			
			if (isValid(val)) {
				saveClus(input, null);
				saveRelations(null, input);
				result.setValue(removeOrphans(input));
			} else {
				result.setValue(input);
			}
			
			return result;
		} catch (Exception e) {
			throw new AssemblyException("Unable to save CluInfoHierarchy", e);
		}
	}
	
	private CluInfoHierarchy removeOrphans(CluInfoHierarchy input) {
		if (input.getModificationState() == ModificationState.DELETED) {
			return null;
		} 
		List<CluInfoHierarchy> children = new ArrayList<CluInfoHierarchy>();
		for (CluInfoHierarchy c : input.getChildren()) {
			c = removeOrphans(c);
			if (c != null) {
				children.add(c);
			}
		}
		input.setChildren(children);
		return input;
	}


	private void saveClus(CluInfoHierarchy input, String parentId) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException {
		CluInfo result = null;
		CluInfo clu = input.getCluInfo();
		if (input.getModificationState() != null) {
			switch (input.getModificationState()) {
				case CREATED:
					result = luService.createClu(clu.getType(), clu);
					break;
				case UPDATED:
					result = luService.updateClu(clu.getId(), clu);
					break;
				case DELETED:
					// back out any relationships in case of RI
					if (parentId != null){
						List<CluCluRelationInfo> relations = luService.getCluCluRelationsByClu(parentId);
						for (CluCluRelationInfo rel : relations) {
							luService.deleteCluCluRelation(rel.getId());
						}
						luService.deleteClu(clu.getId());
					}
					break;
				default:
					// do nothing
			}
		}
		if (result != null) {
			input.setCluInfo(result);
		}
		for (CluInfoHierarchy child : input.getChildren()) {
			saveClus(child, input.getCluInfo().getId());
		}
	}
	
	private void saveRelations(String parentId, CluInfoHierarchy input) throws AlreadyExistsException, CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		if (parentId != null && input.getModificationState() == ModificationState.CREATED) {
			CluCluRelationInfo rel = new CluCluRelationInfo();
			rel.setCluId(parentId);
			rel.setRelatedCluId(input.getCluInfo().getId());
			rel.setType(input.getParentRelationType());
			rel.setState(input.getParentRelationState());
			System.out.println("Creating relation: " + rel.getCluId() + "\t" + rel.getType() + "\t" + rel.getRelatedCluId());
			try {
                luService.createCluCluRelation(rel.getCluId(), rel.getRelatedCluId(), rel.getType(), rel);
            } catch (CircularRelationshipException e) {
                throw new CircularReferenceException(e);
            }
		}
		for (CluInfoHierarchy h : input.getChildren()) {
			saveRelations(input.getCluInfo().getId(), h);
		}
	}

	private boolean isValid(List<ValidationResultInfo> val) {
		boolean result = true;
		if (val != null) {
			for (ValidationResultInfo v : val) {
				if (v.getLevel() == ErrorLevel.ERROR) {
					result = false;
					break;
				}
			}
		}
		return result;
	}
	

	@Override
	public List<ValidationResultInfo> validate(CluInfoHierarchy input)
			throws AssemblyException {
		// TODO validate against service
		return null;
	}

	@Override
	public Metadata getDefaultMetadata() throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}
}
