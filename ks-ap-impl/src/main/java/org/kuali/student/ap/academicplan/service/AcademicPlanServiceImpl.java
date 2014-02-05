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
import org.kuali.student.ap.academicplan.model.LearningPlanAttributeEntity;
import org.kuali.student.ap.academicplan.model.LearningPlanEntity;
import org.kuali.student.ap.academicplan.model.LearningPlanRichTextEntity;
import org.kuali.student.ap.academicplan.model.PlanItemAttributeEntity;
import org.kuali.student.ap.academicplan.model.PlanItemEntity;
import org.kuali.student.ap.academicplan.model.PlanItemRichTextEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
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
import org.kuali.student.r2.common.infc.ValidationResult;
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
	@Transactional
	public LearningPlanInfo createLearningPlan(@WebParam(name = "learningPlan") LearningPlanInfo learningPlan,
			@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		LearningPlanEntity lpe = new LearningPlanEntity();
		
		lpe.setId(UUIDHelper.genStringUUID());

        TypeInfo type = null;
        try {
            type = KsapFrameworkServiceLocator.getTypeService().getType(learningPlan.getTypeKey(), context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(String.format("Unknown type [%s].", learningPlan.getTypeKey()));
        }

		lpe.setTypeId(type.getKey());
		lpe.setStateKey(learningPlan.getStateKey());

		lpe.setStudentId(learningPlan.getStudentId());
		lpe.setDescr(new LearningPlanRichTextEntity(learningPlan.getDescr()));

		//  Item meta
		lpe.setCreateId(context.getPrincipalId());
		lpe.setCreateTime(new Date());
		lpe.setUpdateId(context.getPrincipalId());
		lpe.setUpdateTime(new Date());
		lpe.setShared(learningPlan.getShared());

		// Update attributes.
		Set<LearningPlanAttributeEntity> attributeEntities = new HashSet<LearningPlanAttributeEntity>();
		for (Attribute att : learningPlan.getAttributes())
			attributeEntities.add(new LearningPlanAttributeEntity(att, lpe));
		lpe.setAttributes(attributeEntities);

		learningPlanDao.persist(lpe);
		
		learningPlanDao.getEm().flush();

		return learningPlanDao.find(lpe.getId()).toDto();
	}

	@Override
	@Transactional
	public PlanItemInfo createPlanItem(@WebParam(name = "planItem") PlanItemInfo planItem,
			@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		//  For a given plan there should be only one planned course item per course id. So, do a lookup to see
		//  if a plan item exists if the type is "planned" and do an update of ATPid instead of creating a new plan item.
		PlanItemEntity pie = new PlanItemEntity();
		String planItemId = UUIDHelper.genStringUUID();
		pie.setId(planItemId);

		pie.setRefObjectId(planItem.getRefObjectId());
		pie.setRefObjectTypeKey(planItem.getRefObjectType());
        if (planItem.getCategory()==null) {
            throw new MissingParameterException("Plan item category is missing(/null)");
        }
        pie.setCategory(planItem.getCategory().toString());

        TypeInfo type = null;
        try {
            KsapFrameworkServiceLocator.getTypeService().getType(planItem.getTypeKey(), context);
        } catch (DoesNotExistException e) {
			throw new InvalidParameterException(String.format("Unknown plan item type id [%s].", planItem.getTypeKey()));
		}
		pie.setTypeId(planItem.getTypeKey());

		//  Convert the List of plan periods to a Set.
		pie.setPlanPeriods(new HashSet<String>(planItem.getPlanPeriods()));

		//  Set attributes.
		pie.setAttributes(new HashSet<PlanItemAttributeEntity>());
		if (planItem.getAttributes() != null) {
			for (Attribute att : planItem.getAttributes()) {
				PlanItemAttributeEntity attEntity = new PlanItemAttributeEntity(att, pie);
				pie.getAttributes().add(attEntity);
			}
		}

		//  Create text entity.
		pie.setDescr(new PlanItemRichTextEntity(planItem.getDescr()));

		//  Set meta data.
		pie.setCreateId(context.getPrincipalId());
		pie.setCreateTime(new Date());
		pie.setUpdateId(context.getPrincipalId());
		pie.setUpdateTime(new Date());

		// Set credits
		if (planItem.getCredit() != null) {
			pie.setCredit(planItem.getCredit());
		}

		//  Set the learning plan.
		String planId = planItem.getLearningPlanId();
		if (planId == null) {
			throw new InvalidParameterException("Learning plan id was null.");
		}
		LearningPlanEntity plan = learningPlanDao.find(planItem.getLearningPlanId());
		if (plan == null) {
			throw new InvalidParameterException(String.format("Unknown learning plan id [%s]",
					planItem.getLearningPlanId()));
		}
		pie.setLearningPlan(plan);

		//  Save the new plan item.
		planItemDao.persist(pie);
		
		planItemDao.getEm().flush();

		plan.setEntityUpdated(context);
		
		//  Update the metadata (timestamp, updated-by) on the plan.
		plan.setUpdateId(context.getPrincipalId());
		plan.setUpdateTime(new Date());
		
		try {
			plan = learningPlanDao.merge(plan);
		} catch (VersionMismatchException e) {
			throw new OperationFailedException("Failed to update learning plan("+plan.getId() +") on learning plan item change ("+planItem.getId()+")");
		}

		learningPlanDao.getEm().flush();
		
		return planItemDao.find(planItemId).toDto();
	}

	@Override
	public PlanItemSetInfo createPlanItemSet(@WebParam(name = "planItemSet") PlanItemSetInfo planItemSet,
			@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new RuntimeException("Not implemented.");
	}

	/**
	 *
	 * @param createNewPlanItem
	 * @param attrSource
	 * @param attributeEntities
	 * @return
	 */
	private List<Attribute> mergeAttributes(boolean createNewPlanItem, HasAttributes attrSource,
			Set<? extends AttributeEntity> attributeEntities) {
		if (attrSource.getAttributes() == null)
			return null;

		Map<String, List<Attribute>> attributeMap = new LinkedHashMap<String, List<Attribute>>();
		for (Attribute att : attrSource.getAttributes()) {
			String key = att.getKey();
			List<Attribute> attl = attributeMap.get(key);
			if (attl == null)
				attributeMap.put(key, attl = new LinkedList<Attribute>());
			attl.add(att);
		}

		if (attributeEntities != null) {
			if (createNewPlanItem) {
				attributeEntities.clear();
			} else {
				Iterator<? extends AttributeEntity> ai = attributeEntities.iterator();
			while (ai.hasNext()) {
					AttributeEntity attrEntity = ai.next();
				String key = attrEntity.getKey();
				if (attributeMap.containsKey(key)) {
					List<Attribute> attl = attributeMap.get(key);
					if (attl.isEmpty()) {
						ai.remove();
					} else {
						Iterator<Attribute> atti = attl.iterator();
						Attribute att = null;
						while (att == null && atti.hasNext()) {
							Attribute attc = atti.next();
							if (attc.getId() != null && attc.getId().equals(attrEntity.getId())) {
								att = attc;
								atti.remove();
							}
						}
						if (att == null) {
							att = attl.remove(0);
						}
						attrEntity.setValue(att.getValue());
					}
					if (attl.isEmpty())
						attributeMap.remove(key);
				} else {
					ai.remove();
				}
			}
		}
		}

		List<Attribute> rv = new LinkedList<Attribute>();
		for (List<Attribute> attl : attributeMap.values())
			rv.addAll(attl);
		return rv;
	}

	@Override
	@Transactional
	public LearningPlanInfo updateLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "learningPlan") LearningPlanInfo learningPlan,
			@WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
			DoesNotExistException {

		LearningPlanEntity lpe = learningPlanDao.find(learningPlanId);
		if (lpe == null) {
			throw new DoesNotExistException(learningPlanId);
		}

        TypeInfo type = null;
        try {
            type = KsapFrameworkServiceLocator.getTypeService().getType(learningPlan.getTypeKey(), context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(String.format("Unknown type [%s].", learningPlan.getTypeKey()));
        }
        lpe.setTypeId(type.getKey());
		lpe.setStateKey(learningPlan.getStateKey());

		lpe.setStudentId(learningPlan.getStudentId());

		//  Update text entity.
		RichTextInfo descrInfo = learningPlan.getDescr();
		if (descrInfo == null) {
			lpe.setDescr(null);
		} else {
			LearningPlanRichTextEntity descr = lpe.getDescr();
			if (descr == null) {
				descr = new LearningPlanRichTextEntity(descrInfo);
			} else {
				descr.setPlain(descrInfo.getPlain());
				descr.setFormatted(descrInfo.getFormatted());
			}
		}

		//  Update attributes.
		List<Attribute> createAttrs = mergeAttributes(false, learningPlan, lpe.getAttributes());
		if (createAttrs != null) {
			Set<LearningPlanAttributeEntity> attributeEntities = lpe.getAttributes();
			if (attributeEntities == null) {
				lpe.setAttributes(attributeEntities = new HashSet<LearningPlanAttributeEntity>());
			}
			for (Attribute att : createAttrs)
				attributeEntities.add(new LearningPlanAttributeEntity(att, lpe));
		}

		//  Plan meta
		lpe.setUpdateId(context.getPrincipalId());
		lpe.setUpdateTime(new Date());
		lpe.setShared(learningPlan.getShared());

		try {
			lpe = learningPlanDao.merge(lpe);
		} catch (VersionMismatchException e) {
			throw new OperationFailedException("Failed to update Learning Plan for id = "+ learningPlanId);
		}
		
		learningPlanDao.getEm().flush();
		
		return learningPlanDao.find(learningPlanId).toDto();
	}

	@Override
	@Transactional
	public PlanItemInfo updatePlanItem(@WebParam(name = "planItemId") String planItemId,
			@WebParam(name = "planItem") PlanItemInfo planItem, @WebParam(name = "context") ContextInfo context)
			throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException, PermissionDeniedException {

		//  See if the plan item exists before trying to update it.
		PlanItemEntity planItemEntity = planItemDao.find(planItemId);

		// If Plan type changes, create a new one and update the old one's state to DELETED
		String origPlanItemId = null;

		if (planItemEntity == null) {
			throw new DoesNotExistException(planItemId);
		}

		planItemEntity.setRefObjectId(planItem.getRefObjectId());
		planItemEntity.setRefObjectTypeKey(planItem.getRefObjectType());

		//  Update the plan item category if it has changed.
		boolean createNewPlanItem = false;
		if (!planItemEntity.getCategory().equals(planItem.getCategory())
				&& planItemEntity.getCategory()
						.equals(AcademicPlanServiceConstants.ItemCategory.WISHLIST)) {
			createNewPlanItem = true;
		}

		if (!planItemEntity.getCategory().equals(planItem.getCategory())) {
            planItemEntity.setCategory(planItem.getCategory().toString());
			origPlanItemId = planItemEntity.getId();
		}

		//  Update plan periods.
		if (planItem.getPlanPeriods() != null) {
			//  Convert from List to Set.
			planItemEntity.setPlanPeriods(new HashSet<String>(planItem.getPlanPeriods()));
		}

		//  Update attributes.
		List<Attribute> createAttrs = mergeAttributes(createNewPlanItem, planItem, planItemEntity.getAttributes());
		if (createAttrs != null) {
			Set<PlanItemAttributeEntity> attributeEntities = planItemEntity.getAttributes();
			if (attributeEntities == null) {
				planItemEntity.setAttributes(attributeEntities = new HashSet<PlanItemAttributeEntity>());
			}
			for (Attribute att : createAttrs)
				attributeEntities.add(new PlanItemAttributeEntity(att, planItemEntity));
		}

		//  Update text entity.
		RichTextInfo descrInfo = planItem.getDescr();
		if (descrInfo == null) {
			planItemEntity.setDescr(null);
		} else {
			PlanItemRichTextEntity descr = planItemEntity.getDescr();
			if (descr == null) {
				planItemEntity.setDescr(new PlanItemRichTextEntity(planItem.getDescr()));
			} else {
				descr.setPlain(descrInfo.getPlain());
				descr.setFormatted(descrInfo.getFormatted());
			}
		}

		//  Update meta data.
		planItemEntity.setUpdateId(context.getPrincipalId());
		planItemEntity.setUpdateTime(new Date());

		//   If the the learning plan has changed update the plan item and update the meta data (update date, user) on the old plan.
		LearningPlanEntity originalPlan = learningPlanDao.find(planItem.getLearningPlanId());
		if (originalPlan == null) {
			throw new InvalidParameterException(String.format("Unknown learning plan id [%s]",
					planItem.getLearningPlanId()));
		}

		LearningPlanEntity newPlan = null;
		if (!planItemEntity.getLearningPlan().getId().equals(planItem.getLearningPlanId())) {
			String planId = planItem.getLearningPlanId();
			if (planId == null) {
				throw new InvalidParameterException("Learning plan id was null.");
			}
			newPlan = learningPlanDao.find(planItem.getLearningPlanId());
			if (newPlan == null) {
				throw new InvalidParameterException(String.format("Unknown learning plan id [%s]",
						planItem.getLearningPlanId()));
			}
			planItemEntity.setLearningPlan(newPlan);
		}

        // update credits
        if (planItem.getCredit() != null) {
            planItemEntity.setCredit(planItem.getCredit());
        }

		// If plan type changes create a new one and delete
		String updatePlanItemId = null;
		if (createNewPlanItem) {
			try {
				PlanItemInfo newpiInfo = createPlanItem(planItemEntity.toDto(), context);
				updatePlanItemId = newpiInfo.getId();
			} catch (AlreadyExistsException e) {
				throw new OperationFailedException(e.getMessage());
			}
			deletePlanItem(origPlanItemId, context);
		} else {
			updatePlanItemId = planItemEntity.getId();
			try {
				planItemEntity = planItemDao.merge(planItemEntity);
			} catch (VersionMismatchException e) {
				throw new OperationFailedException("Failed to merge planItem for id = " + planItemId);
			}
		}

		//  Update meta data on the original plan.
		originalPlan.setUpdateId(context.getPrincipalId());
		originalPlan.setUpdateTime(new Date());
		try {
			originalPlan = learningPlanDao.merge(originalPlan);
		} catch (VersionMismatchException e) {
			throw new OperationFailedException("Failed to merge original plan for id = " + originalPlan.getId());
		}

		//  Update the new plan meta data if the plan changed.
		if (newPlan != null) {
			newPlan.setUpdateId(context.getPrincipalId());
			newPlan.setUpdateTime(new Date());
			try {
				newPlan = learningPlanDao.merge(newPlan);
			} catch (VersionMismatchException e) {
				throw new OperationFailedException("Failed to merge new plan for id = " + newPlan.getId());
			}
		}
		
		



		return planItemDao.find(updatePlanItemId).toDto();
	}

	@Override
	public PlanItemSetInfo updatePlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId,
			@WebParam(name = "planItemSet") PlanItemSetInfo planItemSet, @WebParam(name = "context") ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException, PermissionDeniedException {
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

		List<ValidationResultInfo> validationResultInfos = new ArrayList<ValidationResultInfo>();

		/*
		 * Validate that the course exists. TODO: KSAP-752 Move this validation to the
		 * data dictionary.
		 */
		try {
			if (KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(planItemInfo.getRefObjectId()) == null) {
				validationResultInfos.add(makeValidationResultInfo(
						String.format("Could not find course with ID [%s].", planItemInfo.getRefObjectId()),
						"refObjectId", ValidationResult.ErrorLevel.ERROR));
			}
		} catch (RuntimeException e) {
			validationResultInfos.add(makeValidationResultInfo(e.getLocalizedMessage(), "refObjectId",
					ValidationResult.ErrorLevel.ERROR));
		}

		//  TODO: KSAP-752 This validation should be implemented in the data dictionary when that possibility manifests.
		//  Make sure a plan period exists if category is planned course.
        if (planItemInfo.getCategory()==null) {
            throw new MissingParameterException("plan item category is missing (should be PLANNED, BACKUP,...");
        }
		if (planItemInfo.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.PLANNED)
				|| planItemInfo.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.BACKUP)) {
			if (planItemInfo.getPlanPeriods() == null || planItemInfo.getPlanPeriods().size() == 0) {
				validationResultInfos.add(makeValidationResultInfo(
						String.format("Plan Item category was [%s], but no plan periods were defined.",
								planItemInfo.getCategory()), "category", ValidationResult.ErrorLevel.ERROR));
			} else {
				//  Make sure the plan periods are valid. Note: There should never be more than one item in the collection.
				for (String atpId : planItemInfo.getPlanPeriods()) {
					boolean valid = false;
					try {
						valid = isValidTerm(atpId);
						if (!valid) {
							validationResultInfos.add(makeValidationResultInfo(
									String.format("ATP ID [%s] was not valid.", atpId), "atpId",
									ValidationResult.ErrorLevel.ERROR));
						}
					} catch (Exception e) {
						validationResultInfos.add(makeValidationResultInfo("ATP ID lookup failed.", "typeKey",
								ValidationResult.ErrorLevel.ERROR));
					}
				}
			}
		}

		/*
		 * Check for duplicate list items: Make sure a saved courses item with
		 * this course id doesn't already exist in the plan. Make sure a planned
		 * course item with the same ATP id doesn't exist in the plan.
		 * 
		 * Note: This validation is last to insure that all of the other
		 * validations are performed on "update" operations. The duplicate check
		 * throw an AlreadyExistsException on updates.
		 * 
		 *
		 * 
		 * TODO: KSAP-752 Move these validations to the data dictionary.
		 */
		checkPlanItemDuplicate(planItemInfo);

		return validationResultInfos;
	}

	@Override
	public List<ValidationResultInfo> validatePlanItemSet(@WebParam(name = "validationType") String validationType,
			@WebParam(name = "planItemInfo") PlanItemSetInfo planItemSetInfo,
			@WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return new ArrayList<ValidationResultInfo>();
	}

	/**
	 * @throws AlreadyExistsException
	 *             If the plan item is a duplicate.
	 */
	private void checkPlanItemDuplicate(PlanItemInfo planItem) throws AlreadyExistsException {

		String planItemId = planItem.getLearningPlanId();
		String courseId = planItem.getRefObjectId();
		AcademicPlanServiceConstants.ItemCategory category = planItem.getCategory();

		/**
		 * See if a duplicate item exits in the plan. If the type is wishlist
		 * then only the course id has to match to make it a duplicate. If the
		 * type is planned course then the ATP must match as well.
		 */
		List<PlanItemEntity> planItems = this.planItemDao.getLearningPlanItems(planItemId, category);
		for (PlanItemEntity p : planItems) {
			if (p.getRefObjectId().equals(courseId)) {
				if (category.equals(AcademicPlanServiceConstants.ItemCategory.PLANNED)
						|| category.equals(AcademicPlanServiceConstants.ItemCategory.BACKUP)
						|| category.equals(AcademicPlanServiceConstants.ItemCategory.CART)) {
					for (String atpId : planItem.getPlanPeriods()) {
						if (p.getPlanPeriods().contains(atpId)) {
							throw new AlreadyExistsException(String.format(
									"A plan item for plan [%s], course id [%s], and term [%s] already exists.", p
											.getLearningPlan().getId(), courseId, atpId));
						}
					}
				} else {
					throw new AlreadyExistsException(String.format(
							"A plan item for plan [%s] and course id [%s] already exists.",
							p.getLearningPlan().getId(), courseId));
				}
			}
		}
	}

	private ValidationResultInfo makeValidationResultInfo(String errorMessage, String element,
			ValidationResult.ErrorLevel errorLevel) {
		ValidationResultInfo vri = new ValidationResultInfo();
		vri.setError(errorMessage);
		vri.setElement(element);
		vri.setLevel(errorLevel);
		return vri;
	}

	private boolean isValidTerm(String atpId) {
		try {
			return KsapFrameworkServiceLocator.getTermHelper().getTerm(atpId) != null;
		} catch (Exception e) {
			throw new RuntimeException("Query to ATP service failed.", e);
		}
	}

}
