package org.kuali.student.ap.academicplan.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebParam;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.ap.academicplan.dao.LearningPlanDao;
import org.kuali.student.ap.academicplan.dao.PlanItemDao;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemSetInfo;
import org.kuali.student.ap.academicplan.model.AttributeEntity;
import org.kuali.student.ap.academicplan.model.LearningPlanEntity;
import org.kuali.student.ap.academicplan.model.PlanItemAttributeEntity;
import org.kuali.student.ap.academicplan.model.PlanItemEntity;
import org.kuali.student.ap.academicplan.model.PlanItemRichTextEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.HasAttributes;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Academic Plan Service Implementation.
 */
@Transactional(readOnly = true, noRollbackFor = { AlreadyExistsException.class, DoesNotExistException.class }, rollbackFor = { Throwable.class })
public class AcademicPlanServiceImpl implements AcademicPlanService {

	private LearningPlanDao learningPlanDao;
	private PlanItemDao planItemDao;

	public PlanItemDao getPlanItemDao() {
		return planItemDao;
	}

	public void setPlanItemDao(PlanItemDao planItemDao) {
		this.planItemDao = planItemDao;
	}

	public LearningPlanDao getLearningPlanDao() {
		return learningPlanDao;
	}

	public void setLearningPlanDao(LearningPlanDao learningPlanDao) {
		this.learningPlanDao = learningPlanDao;
	}

	@Override
	public LearningPlanInfo getLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		LearningPlanEntity lpe = learningPlanDao.find(learningPlanId);
		if (null == lpe) {
			throw new DoesNotExistException(learningPlanId);
		}

		LearningPlanInfo dto = lpe.toDto();
		return dto;
	}

    @Override
    public List<LearningPlanInfo> getLearningPlansByIds(@WebParam(name = "learningPlanIds") List<String> learningPlanIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LearningPlanEntity> entityList = learningPlanDao.findByIds(learningPlanIds);
        List<LearningPlanInfo> infoList = new ArrayList<LearningPlanInfo>();


        for (LearningPlanEntity planEntity : entityList) {

            infoList.add(planEntity.toDto());
        }

        return infoList;
    }

//    @Override
//    public List<String> getLearningPlanIdsByType(@WebParam(name = "planTypeKey") planTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
//    }

    @Override
	public PlanItemInfo getPlanItem(@WebParam(name = "planItemId") String planItemId,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		PlanItemEntity planItem = planItemDao.find(planItemId);
		if (null == planItem) {
			throw new DoesNotExistException(String.format("Plan item with Id [%s] does not exist", planItemId));
		}

		return planItem.toDto();
	}

    @Override
    public List<PlanItemInfo> getPlanItemsByIds(@WebParam(name = "planItemIds") List<String> planItemIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
	public List<PlanItemInfo> getPlanItemsInPlanByType(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "planItemTypeKey") String planItemTypeKey, @WebParam(name = "context") ContextInfo context)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<PlanItemInfo> planItemInfos = new ArrayList<PlanItemInfo>();
		List<PlanItemEntity> planItemEntities = planItemDao.getLearningPlanItems(learningPlanId, planItemTypeKey);
		if (null == planItemEntities) {
			throw new DoesNotExistException(String.format("Plan item with learning plan Id [%s] does not exist",
					learningPlanId));
		} else {
			for (PlanItemEntity planItemEntity : planItemEntities) {
				planItemInfos.add(planItemEntity.toDto());
			}
		}
		return planItemInfos;
	}

	@Override
    public List<PlanItemInfo> getPlanItemsInPlanByCategory(@WebParam(name = "learningPlanId") String learningPlanId,
            @WebParam(name = "category") AcademicPlanServiceConstants.ItemCategory category, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<PlanItemInfo> planItemInfos = new ArrayList<PlanItemInfo>();
        List<PlanItemEntity> planItemEntities = planItemDao.getLearningPlanItems(learningPlanId, category);
        if (null == planItemEntities) {
            throw new DoesNotExistException(String.format("Plan item with learning plan Id [%s] and category (%s) does not exist",
                    learningPlanId,category.toString()));
        } else {
            for (PlanItemEntity planItemEntity : planItemEntities) {
                planItemInfos.add(planItemEntity.toDto());
            }
        }
        return planItemInfos;
    }
	@Override
	public List<PlanItemInfo> getPlanItemsInPlan(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		List<PlanItemInfo> dtos = new ArrayList<PlanItemInfo>();

		List<PlanItemEntity> planItems = planItemDao.getLearningPlanItems(learningPlanId);
		for (PlanItemEntity pie : planItems) {
			dtos.add(pie.toDto());
		}
		return dtos;
	}

	@Override
	public List<PlanItemInfo> getPlanItemsInPlanByAtp(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "atpKey") String atpKey, @WebParam(name = "category") AcademicPlanServiceConstants.ItemCategory category,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		List<PlanItemEntity> planItemsList = planItemDao.getLearningPlanItems(learningPlanId, category);

		List<PlanItemInfo> planItemDtos = new ArrayList<PlanItemInfo>();
		for (PlanItemEntity pie : planItemsList) {
			if (pie.getPlanPeriods().contains(atpKey)) {
				planItemDtos.add(pie.toDto());
			}
		}

		return planItemDtos;
	}

	@Override
	public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(
			@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "refObjectType") String refObjectType,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		List<PlanItemEntity> planItemsList = planItemDao.getLearningPlanItemsByRefObjectId(learningPlanId, refObjectId,
				refObjectType);

		List<PlanItemInfo> planItemDtos = new ArrayList<PlanItemInfo>();
		for (PlanItemEntity pie : planItemsList) {
			planItemDtos.add(pie.toDto());
		}

		return planItemDtos;
	}

	@Override
	public PlanItemSetInfo getPlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public List<PlanItemInfo> getPlanItemsInSet(@WebParam(name = "planItemSetId") String planItemSetId,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new RuntimeException("Not implemented.");
	}

    @Override
    public List<PlanItemSetInfo> getPlanItemSetsByIds(@WebParam(name = "planItemSetIds") List<String> planItemSetIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
	public List<LearningPlanInfo> getLearningPlansForStudentByType(@WebParam(name = "studentId") String studentId,
			@WebParam(name = "planTypeKey") String planTypeKey, @WebParam(name = "context") ContextInfo context)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException,
			OperationFailedException {

		List<LearningPlanEntity> lpeList = learningPlanDao.getLearningPlansByType(studentId, planTypeKey);

		List<LearningPlanInfo> learningPlanDtos = new ArrayList<LearningPlanInfo>();
		for (LearningPlanEntity lpe : lpeList) {
			learningPlanDtos.add(lpe.toDto());
		}
		return learningPlanDtos;
	}

	@Override
	@Transactional (readOnly = false, rollbackFor = {Throwable.class})
	public LearningPlanInfo createLearningPlan(@WebParam(name = "learningPlan") LearningPlanInfo learningPlan,
			@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		LearningPlanEntity lpe = new LearningPlanEntity();

        TypeInfo type = null;
        try {
            type = KsapFrameworkServiceLocator.getTypeService().getType(learningPlan.getTypeKey(), context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(String.format("Unknown type [%s].", learningPlan.getTypeKey()));
        }

        lpe.setId(UUIDHelper.genStringUUID());
        lpe.fromDto(learningPlan);
        lpe.setEntityCreated(context);

		learningPlanDao.persist(lpe);
		learningPlanDao.getEm().flush();
		return lpe.toDto();
	}

	@Override
	@Transactional (readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
	public PlanItemInfo createPlanItem(@WebParam(name = "planItem") PlanItemInfo planItem,
			@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {

		//  For a given plan there should be only one planned course item per course id. So, do a lookup to see
		//  if a plan item exists if the type is "planned" and do an update of ATPid instead of creating a new plan item.
		PlanItemEntity pie = new PlanItemEntity();
		planItem.setId(UUIDHelper.genStringUUID());
        pie.fromDto(planItem,learningPlanDao);
        pie.setEntityCreated(context);

		planItemDao.persist(pie);
		planItemDao.getEm().flush();

        //Update LearningPlan's update-timeStamp & userId
        LearningPlanEntity learningPlan = pie.getLearningPlan();
        learningPlan.setEntityUpdated(context);
        learningPlan = learningPlanDao.merge(learningPlan);
		learningPlanDao.getEm().flush();
		return pie.toDto();
	}

	@Override
	public PlanItemSetInfo createPlanItemSet(@WebParam(name = "planItemSet") PlanItemSetInfo planItemSet,
			@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new RuntimeException("Not implemented.");
	}

	@Override
    @Transactional  (readOnly = false, rollbackFor = {Throwable.class})
	public LearningPlanInfo updateLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "learningPlan") LearningPlanInfo learningPlan,
			@WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {

        if (!learningPlanId.equals(learningPlan.getId())) {
            throw new InvalidParameterException(learningPlanId + " does not match the id on the object " + learningPlan.getId());
        }

		LearningPlanEntity lpe = learningPlanDao.find(learningPlanId);
		if (lpe == null) {
			throw new DoesNotExistException(learningPlanId);
		}

        if (!lpe.getTypeId().equals(learningPlan.getTypeKey())) {
            throw new OperationFailedException("Passed in plan Type: "+learningPlan.getTypeKey()+" must match stored typeKey: "+lpe.getTypeId()+" for planId: "+learningPlanId);
        }

        lpe.fromDto(learningPlan);
        lpe.setEntityUpdated(context);
        lpe = learningPlanDao.merge(lpe);
		learningPlanDao.getEm().flush();
		return lpe.toDto();
	}

    @Override
	@Transactional  (readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
	public PlanItemInfo updatePlanItem(@WebParam(name = "planItemId") String planItemId,
			@WebParam(name = "planItem") PlanItemInfo planItem, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        if (!planItemId.equals(planItem.getId())) {
            throw new InvalidParameterException(planItemId + " does not match the id on the object " + planItem.getId());
        }

        //  See if the plan item exists before trying to update it.
        PlanItemEntity planItemEntity = planItemDao.find(planItemId);
        if (planItemEntity == null) {
			throw new DoesNotExistException(planItemId);
		}

        if (!planItemEntity.getTypeId().equals(planItem.getTypeKey())) {
            throw new OperationFailedException("Passed in item type: "+planItem.getTypeKey()+" must match stored typeKey: "+planItemEntity.getTypeId()+" for planItemId: "+planItemId);
        }

        //Get originalPlandId so that if it has changed we can later update the plan item and update the meta data (update date, user) on the old plan.
        String originalPlanId = planItem.getLearningPlanId();

        planItemEntity.fromDto(planItem, learningPlanDao);

        //  Update meta data.
        planItemEntity.setEntityUpdated(context);

        planItemEntity = planItemDao.merge(planItemEntity);

        //  Update the plan meta data if the plan changed.
        planItemEntity.getLearningPlan().setEntityUpdated(context);
        planItemEntity.setLearningPlan(learningPlanDao.merge(planItemEntity.getLearningPlan()));

		//  If the item's planId has changed, then Update meta data on the original plan
        if (!originalPlanId.equals(planItemEntity.getLearningPlan().getId())) {
            LearningPlanEntity originalPlan = learningPlanDao.find(originalPlanId);
            if (originalPlan != null) {
                originalPlan.setEntityUpdated(context);
                originalPlan = learningPlanDao.merge(originalPlan);
            }
        }
        learningPlanDao.getEm().flush();
		return planItemEntity.toDto();
	}

    @Override
    public PlanItemSetInfo updatePlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId,
			@WebParam(name = "planItemSet") PlanItemSetInfo planItemSet, @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		return null; //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	@Transactional
	public StatusInfo deleteLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException, PermissionDeniedException {
		StatusInfo status = new StatusInfo();
		status.setSuccess(Boolean.TRUE);

		LearningPlanEntity lpe = learningPlanDao.find(learningPlanId);
		if (lpe == null) {
			throw new DoesNotExistException(learningPlanId);
		}

		//  Delete plan items.
		List<PlanItemEntity> pies = planItemDao.getLearningPlanItems(learningPlanId);
		for (PlanItemEntity pie : pies) {
			planItemDao.remove(pie);
		}

		learningPlanDao.remove(lpe);

		return status;

	}

	@Override
	@Transactional
	public StatusInfo deletePlanItem(@WebParam(name = "planItemId") String planItemId,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException, PermissionDeniedException {

		StatusInfo status = new StatusInfo();
		status.setSuccess(true);

		PlanItemEntity pie = planItemDao.find(planItemId);
		if (pie == null) {
			throw new DoesNotExistException(String.format("Unknown plan item id [%s].", planItemId));
		}

		planItemDao.remove(pie);

		return status;
	}

	@Override
	public StatusInfo deletePlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException, PermissionDeniedException {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public List<ValidationResultInfo> validateLearningPlan(@WebParam(name = "validationType") String validationType,
			@WebParam(name = "learningPlanInfo") LearningPlanInfo learningPlanInfo,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return new ArrayList<ValidationResultInfo>();
	}

	@Override
	public List<ValidationResultInfo> validatePlanItem(@WebParam(name = "validationType") String validationType,
			@WebParam(name = "planItemInfo") PlanItemInfo planItemInfo, @WebParam(name = "context") ContextInfo context)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException,
			OperationFailedException, AlreadyExistsException {
        return new ArrayList<ValidationResultInfo>();
	}

	@Override
	public List<ValidationResultInfo> validatePlanItemSet(@WebParam(name = "validationType") String validationType,
			@WebParam(name = "planItemInfo") PlanItemSetInfo planItemSetInfo,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return new ArrayList<ValidationResultInfo>();
	}

}
