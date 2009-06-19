/*
 * Copyright 2009 The Kuali Foundation
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

package org.kuali.student.lum.lo.service.impl;

import java.util.List;

import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoHierarchyInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoTypeInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;

/**
 * @author Kuali Student team
 *
 */
public class LearningObjectiveServiceImpl implements LearningObjectiveService {

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#addChildLoToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public StatusInfo addChildLoToLo(String loId, String parentLoId)
			throws AlreadyExistsException, CircularReferenceException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#addEquivalentLoToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public StatusInfo addEquivalentLoToLo(String loId, String equivalentLoId)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#addLoCategoryToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public StatusInfo addLoCategoryToLo(String loCategoryId, String loId)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#createLo(java.lang.String, java.lang.String, org.kuali.student.lum.lo.dto.LoInfo)
	 */
	@Override
	public LoInfo createLo(String parentLoId, String loType, LoInfo loInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#createLoCategory(java.lang.String, org.kuali.student.lum.lo.dto.LoCategoryInfo)
	 */
	@Override
	public LoCategoryInfo createLoCategory(String loHierarchyKey,
			LoCategoryInfo loCategoryInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#deleteLo(java.lang.String)
	 */
	@Override
	public StatusInfo deleteLo(String loId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#deleteLoCategory(java.lang.String)
	 */
	@Override
	public StatusInfo deleteLoCategory(String loCategoryId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getAllDescendants(java.lang.String)
	 */
	@Override
	public List<String> getAllDescendants(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getAncestors(java.lang.String)
	 */
	@Override
	public List<String> getAncestors(String loId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getEquivalentLos(java.lang.String)
	 */
	@Override
	public List<LoInfo> getEquivalentLos(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLo(java.lang.String)
	 */
	@Override
	public LoInfo getLo(String loId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoByIdList(java.util.List)
	 */
	@Override
	public List<LoInfo> getLoByIdList(List<String> loId)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoCategories(java.lang.String)
	 */
	@Override
	public List<LoCategoryInfo> getLoCategories(String loHierarchyKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoCategoriesForLo(java.lang.String)
	 */
	@Override
	public List<LoCategoryInfo> getLoCategoriesForLo(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoCategory(java.lang.String)
	 */
	@Override
	public LoCategoryInfo getLoCategory(String loCategoryId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoChildren(java.lang.String)
	 */
	@Override
	public List<LoInfo> getLoChildren(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoEquivalents(java.lang.String)
	 */
	@Override
	public List<LoInfo> getLoEquivalents(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoHierarchies()
	 */
	@Override
	public List<LoHierarchyInfo> getLoHierarchies()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoHierarchy(java.lang.String)
	 */
	@Override
	public LoHierarchyInfo getLoHierarchy(String loHierarchyKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoParents(java.lang.String)
	 */
	@Override
	public List<LoInfo> getLoParents(String loId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoType(java.lang.String)
	 */
	@Override
	public LoTypeInfo getLoType(String loTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoTypes()
	 */
	@Override
	public List<LoTypeInfo> getLoTypes() throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLosByLoCategory(java.lang.String)
	 */
	@Override
	public List<LoInfo> getLosByLoCategory(String loCategoryId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#isDescendant(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean isDescendant(String loId, String descendantLoId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#isEquivalent(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean isEquivalent(String loId, String equivalentLoId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#removeChildLoFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public StatusInfo removeChildLoFromLo(String loId, String parentLoId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#removeEquivalentLoFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public StatusInfo removeEquivalentLoFromLo(String loId,
			String equivalentLoId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#removeLoCategoryFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public StatusInfo removeLoCategoryFromLo(String loCategoryId, String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#updateLo(java.lang.String, org.kuali.student.lum.lo.dto.LoInfo)
	 */
	@Override
	public LoInfo updateLo(String loId, LoInfo loInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#updateLoCategory(java.lang.String, org.kuali.student.lum.lo.dto.LoCategoryInfo)
	 */
	@Override
	public LoCategoryInfo updateLoCategory(String loCategoryId,
			LoCategoryInfo loCategoryInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#validateLo(java.lang.String, org.kuali.student.lum.lo.dto.LoInfo)
	 */
	@Override
	public List<ValidationResult> validateLo(String validationType,
			LoInfo loInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#validateLoCategory(java.lang.String, org.kuali.student.lum.lo.dto.LoCategoryInfo)
	 */
	@Override
	public List<ValidationResult> validateLoCategory(String validationType,
			LoCategoryInfo loCategoryInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

}
