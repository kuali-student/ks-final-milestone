package org.kuali.student.ap.academicplan.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebParam;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.ap.academicplan.dao.LearningPlanDao;
import org.kuali.student.ap.academicplan.dao.PlanItemDao;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.model.LearningPlanEntity;
import org.kuali.student.ap.academicplan.model.PlanItemEntity;
import org.kuali.student.r2.common.dto.AttributeInfo;
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
    public List<LearningPlanInfo> getLearningPlansByIds(@WebParam(name="learningPlanIds") List<String> learningPlanIds,
                                                        @WebParam(name = "contextInfo") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LearningPlanInfo> infoList = new ArrayList<LearningPlanInfo>();

        List<LearningPlanEntity> entityList = null;
        try {
            entityList = learningPlanDao.findByIds(learningPlanIds);
        } catch (DoesNotExistException e) {
            entityList = null;
        }

        if (entityList != null) {
            for (LearningPlanEntity planEntity : entityList) {
                infoList.add(planEntity.toDto());
            }
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
    public List<PlanItemInfo> getPlanItemsByIds(@WebParam(name = "planItemIds") List<String> planItemIds, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByType(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "planItemTypeKey") String planItemTypeKey, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
		List<PlanItemInfo> planItemInfos = new ArrayList<PlanItemInfo>();
		List<PlanItemEntity> planItemEntities = planItemDao.getLearningPlanItemsByType(learningPlanId, planItemTypeKey);
		if (planItemEntities!= null ) {
			for (PlanItemEntity planItemEntity : planItemEntities) {
				planItemInfos.add(planItemEntity.toDto());
			}
		}
		return planItemInfos;
	}

	@Override
    public List<PlanItemInfo> getPlanItemsInPlanByCategory(@WebParam(name = "learningPlanId") String learningPlanId,
            @WebParam(name = "category") AcademicPlanServiceConstants.ItemCategory category, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<PlanItemInfo> planItemInfos = new ArrayList<PlanItemInfo>();
        List<PlanItemEntity> planItemEntities = planItemDao.getLearningPlanItemsByCategory(learningPlanId, category);
        if (planItemEntities!= null ) {
            for (PlanItemEntity planItemEntity : planItemEntities) {
                planItemInfos.add(planItemEntity.toDto());
            }
        }
        return planItemInfos;
    }
	@Override
    public List<PlanItemInfo> getPlanItemsInPlan(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {

		List<PlanItemInfo> dtos = new ArrayList<PlanItemInfo>();

		List<PlanItemEntity> planItems = planItemDao.getLearningPlanItems(learningPlanId);
		for (PlanItemEntity pie : planItems) {
			dtos.add(pie.toDto());
		}
		return dtos;
	}

	@Override
    public List<PlanItemInfo> getPlanItemsInPlanByTermIdByCategory(@WebParam(name = "learningPlanId") String
                                                                               learningPlanId,
                                                                   @WebParam(name = "termId") String termId, @WebParam(name = "category") AcademicPlanServiceConstants.ItemCategory category,
                                                                   @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {

		List<PlanItemEntity> planItemsList = planItemDao.getLearningPlanItemsByCategory(learningPlanId, category);

		List<PlanItemInfo> planItemDtos = new ArrayList<PlanItemInfo>();
		for (PlanItemEntity pie : planItemsList) {
			if (pie.getPlanTermIds().contains(termId)) {
				planItemDtos.add(pie.toDto());
			}
		}

		return planItemDtos;
	}

	@Override
    public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(
			@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "refObjectType") String refObjectType,
			@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {

		List<PlanItemEntity> planItemsList = planItemDao.getLearningPlanItemsByRefObjectIdByRefType(learningPlanId,
                refObjectId,
                refObjectType);

		List<PlanItemInfo> planItemDtos = new ArrayList<PlanItemInfo>();
		for (PlanItemEntity pie : planItemsList) {
			planItemDtos.add(pie.toDto());
		}

		return planItemDtos;
	}

    @Override
    public List<LearningPlanInfo> getLearningPlansForStudentByType(@WebParam(name = "studentId") String studentId,
			@WebParam(name = "planTypeKey") String planTypeKey, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {

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
			@WebParam(name = "context") ContextInfo context) throws
			DataValidationErrorException, InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		LearningPlanEntity lpe = new LearningPlanEntity();

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
    DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
    PermissionDeniedException {

        planItem.setId(UUIDHelper.genStringUUID());

        if(planItem.getRefObjectType().equals(PlanConstants.REG_GROUP_TYPE)){
            String linkedCourseItemId = planItem.getAttributeValue(AcademicPlanServiceConstants.PLAN_ITEM_RELATION_TYPE_RG2COURSE);
            PlanItemInfo linkedCourseItem;
            try{
                linkedCourseItem = KsapFrameworkServiceLocator.getAcademicPlanService().getPlanItem(linkedCourseItemId,context);
                AttributeInfo course2rgLink = new AttributeInfo(AcademicPlanServiceConstants.PLAN_ITEM_RELATION_TYPE_COURSE2RG,planItem.getId());
                linkedCourseItem.getAttributes().add(course2rgLink);
                KsapFrameworkServiceLocator.getAcademicPlanService().updatePlanItem(linkedCourseItem.getId(),linkedCourseItem,context);
            }catch(DoesNotExistException e){
                throw new DataValidationErrorException("Missing link to plan item for related course", e);
            }catch (VersionMismatchException ve){
                throw new OperationFailedException("Unable to link to plan item for related course", ve);
            }

        }

		//  For a given plan there should be only one planned course item per course id. So, do a lookup to see
		//  if a plan item exists if the type is "planned" and do an update of ATPid instead of creating a new plan item.
		PlanItemEntity pie = new PlanItemEntity();
        pie.fromDto(planItem,learningPlanDao);
        pie.setEntityCreated(context);

		planItemDao.persist(pie);
		planItemDao.getEm().flush();

		return pie.toDto();
	}

    @Override
    @Transactional  (readOnly = false, rollbackFor = {Throwable.class})
	public LearningPlanInfo updateLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId,
			@WebParam(name = "learningPlan") LearningPlanInfo learningPlan,
			@WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {

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
    throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {

        //  See if the plan item exists before trying to update it.
        PlanItemEntity planItemEntity = planItemDao.find(planItemId);
        if (planItemEntity == null) {
			throw new DoesNotExistException(planItemId);
		}

        if (!planItemEntity.getTypeId().equals(planItem.getTypeKey())) {
            throw new OperationFailedException("Passed in item type: "+planItem.getTypeKey()
                    +" must match stored typeKey: "+planItemEntity.getTypeId()+" for planItemId: "+planItemId);
        }

        // Check if item is course item moving between terms
        if(planItem.getRefObjectType().equals(PlanConstants.COURSE_TYPE)){
            if(!(CollectionUtils.isEqualCollection(planItem.getPlanTermIds(),planItemEntity.getPlanTermIds()))){
                // If moving between terms delete all linked reg group items
                Iterator<AttributeInfo> iter = planItem.getAttributes().iterator();
                while(iter.hasNext()){
                    AttributeInfo attribute = iter.next();
                    if(attribute.getKey().equals(AcademicPlanServiceConstants.PLAN_ITEM_RELATION_TYPE_COURSE2RG)){
                        String regGroupItem = attribute.getValue();
                        PlanItemEntity pie = planItemDao.find(regGroupItem);
                        if (pie == null) {
                            throw new DoesNotExistException(String.format("Unknown plan item id [%s].", regGroupItem));
                        }
                        planItemDao.remove(pie);
                        iter.remove();
                    }
                }
            }
        }
        planItemEntity.fromDto(planItem, learningPlanDao);

        //  Update meta data.
        planItemEntity.setEntityUpdated(context);

        planItemEntity = planItemDao.merge(planItemEntity);
        planItemDao.getEm().flush();

		return planItemEntity.toDto();
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

        if(pie.getRefObjectTypeKey().equals(PlanConstants.COURSE_TYPE)){
            // If Course type delete all associated Reg group types
            PlanItemInfo planItem = pie.toDto();
            List<String> regGroupItems = planItem.getAttributeValueList(AcademicPlanServiceConstants.
                    PLAN_ITEM_RELATION_TYPE_COURSE2RG);
            for(String regGroupItem : regGroupItems){
                PlanItemEntity regGroup = planItemDao.find(regGroupItem);
                if (regGroup == null) {
                    throw new DoesNotExistException(String.format("Unknown plan item id [%s].", regGroupItem));
                }
                planItemDao.remove(regGroup);
            }
        } else if(pie.getRefObjectTypeKey().equals(PlanConstants.REG_GROUP_TYPE)){
            // If reg group type update associated course object to remove links to reg group item
            PlanItemInfo planItem = pie.toDto();
            String courseItemId = planItem.getAttributeValue(AcademicPlanServiceConstants.
                    PLAN_ITEM_RELATION_TYPE_RG2COURSE);
            PlanItemInfo courseItem = KsapFrameworkServiceLocator.getAcademicPlanService().getPlanItem(courseItemId,context);
            Iterator<AttributeInfo> iter = courseItem.getAttributes().iterator();
            while(iter.hasNext()){
                AttributeInfo attribute = iter.next();
                if(attribute.getKey().equals(AcademicPlanServiceConstants.PLAN_ITEM_RELATION_TYPE_COURSE2RG) && attribute.getValue().equals(planItem.getId())){
                    iter.remove();
                }
            }
            try {
                KsapFrameworkServiceLocator.getAcademicPlanService().updatePlanItem(courseItem.getId(),courseItem,context);
            } catch (DataValidationErrorException e) {
               throw new OperationFailedException("Unable to update related course item",e);
            } catch (VersionMismatchException e) {
               throw new OperationFailedException("Unable to update related course item",e);
            }
        }

		planItemDao.remove(pie);

		return status;
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
                   OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
	}

}
