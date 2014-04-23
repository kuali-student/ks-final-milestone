/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.ap.service.mock;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;


public class AcademicPlanServiceMockImpl implements MockService, AcademicPlanService
{
	// cache variable
	// The LinkedHashMap is just so the values come back in a predictable order
	private Map<String, LearningPlanInfo> learningPlanMap = new LinkedHashMap<String, LearningPlanInfo>();
	private Map<String, PlanItemInfo> planItemMap = new LinkedHashMap<String, PlanItemInfo>();

    @Override
	public void clear()
	{
		this.learningPlanMap.clear ();
		this.planItemMap.clear ();
	}

	
	@Override
	public LearningPlanInfo getLearningPlan(String learningPlanId, ContextInfo context)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// GET_BY_ID
		if (!this.learningPlanMap.containsKey(learningPlanId)) {
		   throw new DoesNotExistException(learningPlanId);
		}
		return new LearningPlanInfo(this.learningPlanMap.get (learningPlanId));
	}
	
	@Override
	public List<LearningPlanInfo> getLearningPlansByIds(List<String> learningPlanIds, ContextInfo context)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<LearningPlanInfo> list = new ArrayList<LearningPlanInfo> ();
		for (String id: learningPlanIds) {
            try {
                list.add (this.getLearningPlan(id, context));
            } catch (DoesNotExistException e) {
                throw new InvalidParameterException("plan id="+id+" does not exist",e);
            }
        }
		return list;
	}
	
	@Override
	public PlanItemInfo getPlanItem(String planItemId, ContextInfo context)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// GET_BY_ID
		if (!this.planItemMap.containsKey(planItemId)) {
		   throw new DoesNotExistException(planItemId);
		}
		return new PlanItemInfo(this.planItemMap.get (planItemId));
	}
	
	@Override
	public List<PlanItemInfo> getPlanItemsByIds(List<String> planItemIds, ContextInfo context)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// GET_BY_IDS
		List<PlanItemInfo> list = new ArrayList<PlanItemInfo> ();
		for (String id: planItemIds) {
            try {
                list.add (this.getPlanItem(id, context));
            } catch (DoesNotExistException e) {
                throw new InvalidParameterException("plan item id="+id+" does not exist",e);
            }
		}
		return list;
	}
	
	@Override
	public List<PlanItemInfo> getPlanItemsInPlanByType(String learningPlanId, String planItemTypeKey, ContextInfo context)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// GET_IDS_BY_TYPE
		List<PlanItemInfo> list = new ArrayList<PlanItemInfo> ();
		for (PlanItemInfo info: planItemMap.values ()) {
			if (learningPlanId.equals(info.getTypeKey())) {
				if (planItemTypeKey.equals(info.getTypeKey())) {
				    list.add (info);
				}
			}
		}
		return list;
	}

    @Override
	public List<PlanItemInfo> getPlanItemsInPlanByCategory(String learningPlanId, AcademicPlanServiceConstants.ItemCategory category, ContextInfo context)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// GET_INFOS_BY_OTHER
		List<PlanItemInfo> list = new ArrayList<PlanItemInfo> ();
		for (PlanItemInfo info: planItemMap.values ()) {
			if (learningPlanId.equals(info.getLearningPlanId())) {
				if (category.equals(info.getCategory())) {
				    list.add (new PlanItemInfo(info));
				}
			}
		}
		return list;
	}
	
	@Override
	public List<PlanItemInfo> getPlanItemsInPlan(String learningPlanId, ContextInfo context)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
        // GET_INFOS_BY_OTHER
        List<PlanItemInfo> list = new ArrayList<PlanItemInfo> ();
        for (PlanItemInfo info: planItemMap.values ()) {
            if (learningPlanId.equals(info.getLearningPlanId())) {
                list.add (new PlanItemInfo(info));
            }
        }
        return list;
	}

    @Override
	public List<PlanItemInfo> getPlanItemsInPlanByTermIdByCategory(String learningPlanId, String termId, org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants.ItemCategory category, ContextInfo context)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// GET_INFOS_BY_OTHER
		List<PlanItemInfo> list = new ArrayList<PlanItemInfo> ();
		for (PlanItemInfo info: planItemMap.values ()) {
			if (learningPlanId.equals(info.getLearningPlanId())) {
                for (String itemTermId : info.getPlanTermIds()) {
                    if (termId.equals(itemTermId)) {
                        if (category.equals(info.getCategory())) {
                            list.add(new PlanItemInfo(info));
                        }
                    }
                }
			}
		}
		return list;
	}
	
	@Override
	public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(String learningPlanId, String refObjectId, String refObjectType, ContextInfo context)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// GET_INFOS_BY_OTHER
		List<PlanItemInfo> list = new ArrayList<PlanItemInfo> ();
		for (PlanItemInfo info: planItemMap.values ()) {
			if (learningPlanId.equals(info.getLearningPlanId())) {
				if (refObjectId.equals(info.getRefObjectId())) {
					if (refObjectType.equals(info.getRefObjectType())) {
					    list.add (new PlanItemInfo(info));
					}
				}
			}
		}
		return list;
	}
	
	@Override
	public List<LearningPlanInfo> getLearningPlansForStudentByType(String studentId, String planTypeKey, ContextInfo context)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// GET_IDS_BY_TYPE
		List<LearningPlanInfo> list = new ArrayList<LearningPlanInfo> ();
		for (LearningPlanInfo info: learningPlanMap.values ()) {
			if (studentId.equals(info.getStudentId())) {
				if (planTypeKey.equals(info.getTypeKey())) {
				    list.add (info);
				}
			}
		}
		return list;
	}
	
	@Override
	public LearningPlanInfo createLearningPlan(LearningPlanInfo learningPlan, ContextInfo context)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// CREATE
		LearningPlanInfo copy = new LearningPlanInfo(learningPlan);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(context));
		learningPlanMap.put(copy.getId(), copy);
		return new LearningPlanInfo(copy);
	}
	
	@Override
	public PlanItemInfo createPlanItem(PlanItemInfo planItem, ContextInfo context)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// CREATE
		PlanItemInfo copy = new PlanItemInfo(planItem);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(context));
		planItemMap.put(copy.getId(), copy);
		return new PlanItemInfo(copy);
	}
	
	@Override
	public LearningPlanInfo updateLearningPlan(String learningPlanId, LearningPlanInfo learningPlan, ContextInfo context)
		throws DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,DoesNotExistException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!learningPlanId.equals (learningPlan.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		LearningPlanInfo copy = new LearningPlanInfo(learningPlan);
		LearningPlanInfo old = this.getLearningPlan(learningPlan.getId(), context);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}
		copy.setMeta(updateMeta(copy.getMeta(), context));
		this.learningPlanMap .put(learningPlan.getId(), copy);
		return new LearningPlanInfo(copy);
	}
	
	@Override
	public PlanItemInfo updatePlanItem(String planItemId, PlanItemInfo planItem, ContextInfo context)
		throws DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,DoesNotExistException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!planItemId.equals (planItem.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		PlanItemInfo copy = new PlanItemInfo(planItem);
		PlanItemInfo old = this.getPlanItem(planItem.getId(), context);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}
		copy.setMeta(updateMeta(copy.getMeta(), context));
		this.planItemMap .put(planItem.getId(), copy);
		return new PlanItemInfo(copy);
	}
	
	@Override
	public StatusInfo deleteLearningPlan(String learningPlanId, ContextInfo context)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
        List<String> delItemIdList = new ArrayList<String>();
        for (PlanItemInfo info: planItemMap.values ()) {
            if (learningPlanId.equals(info.getLearningPlanId())) {
                delItemIdList.add(info.getId());
            }
        }
        for (String delId :delItemIdList) {
            if (this.planItemMap.remove(delId) == null) {
                throw new OperationFailedException(delId);
            }
        }

        // DELETE
		if (this.learningPlanMap.remove(learningPlanId) == null) {
		   throw new OperationFailedException(learningPlanId);
		}
		return newStatus();
	}
	
	@Override
	public StatusInfo deletePlanItem(String planItemId, ContextInfo context)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		if (this.planItemMap.remove(planItemId) == null) {
		   throw new OperationFailedException(planItemId);
		}
		return newStatus();
	}
	
	@Override
	public List<ValidationResultInfo> validateLearningPlan(String validationType, LearningPlanInfo learningPlanInfo, ContextInfo context)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}
	
	@Override
	public List<ValidationResultInfo> validatePlanItem(String validationType, PlanItemInfo planItemInfo, ContextInfo context)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,AlreadyExistsException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}
	
	private StatusInfo newStatus() {
	     StatusInfo status = new StatusInfo();
	     status.setSuccess(Boolean.TRUE);
	     return status;
	}
	
	private MetaInfo newMeta(ContextInfo context) {
	     MetaInfo meta = new MetaInfo();
	     meta.setCreateId(context.getPrincipalId());
	     meta.setCreateTime(new Date());
	     meta.setUpdateId(context.getPrincipalId());
	     meta.setUpdateTime(meta.getCreateTime());
	     meta.setVersionInd("0");
	     return meta;
	}
	
	private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
	     MetaInfo meta = new MetaInfo(old);
	     meta.setUpdateId(context.getPrincipalId());
	     meta.setUpdateTime(new Date());
	     meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
	     return meta;
	}

}

