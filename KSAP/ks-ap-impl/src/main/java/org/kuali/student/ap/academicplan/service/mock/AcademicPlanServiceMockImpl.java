package org.kuali.student.ap.academicplan.service.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemSetInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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

public class AcademicPlanServiceMockImpl implements AcademicPlanService {
    @Override
    public LearningPlanInfo getLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PlanItemInfo getPlanItem(@WebParam(name = "planItemId") String planItemId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        PlanItemInfo plan = new PlanItemInfo();
        plan.setLearningPlanId("learningPlan1");
        plan.setId(planItemId);
        plan.setRefObjectId("0029a117-5691-418e-a11f-9ad46dc83d47");
        String type = "";
        plan.setRefObjectType( type );
        List<String> atpList = new ArrayList<String>();
        String atp = "19843";
        atpList.add( atp );
        plan.setPlanPeriods( atpList );
        plan.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
        plan.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);

        return plan;
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByType(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "planItemTypeKey") String planItemTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByCategory(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "category") AcademicPlanServiceConstants.ItemCategory category, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlan(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId("create1");
        meta.setCreateTime(new Date());
        meta.setUpdateId("update1");
        meta.setUpdateTime(new Date());
        meta.setVersionInd("1");
        List<PlanItemInfo> list = new ArrayList<PlanItemInfo>();
        {
            PlanItemInfo plan = new PlanItemInfo();
            plan.setId( "planItem1" );
            RichTextInfo richText = new RichTextInfo();
            plan.setDescr( richText );
            plan.setLearningPlanId( learningPlanId );
            // ENGL 101
            String cluID = "0029a117-5691-418e-a11f-9ad46dc83d47";
            plan.setRefObjectId( cluID );
            String type = "";
            plan.setRefObjectType( type );
            List<String> atps = new ArrayList<String>();
            atps.add("19843");
            plan.setPlanPeriods( atps );
            plan.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
            plan.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
            plan.setMeta(meta);

            plan.setId( "1" );
            list.add( plan );
        }
        {
            PlanItemInfo plan = new PlanItemInfo();
            plan.setId( "planItem2" );
            RichTextInfo richText = new RichTextInfo();
            plan.setDescr( richText );
            plan.setLearningPlanId( learningPlanId );
            // ENGL 101
            String cluID = "006319d2-f0ef-48d5-bae6-ddfad8a4126f";
            plan.setRefObjectId( cluID );
            String type = "";
            plan.setRefObjectType( type );
            List<String> atpList = new ArrayList<String>();
            String atp = "19843";
            atpList.add( atp );
            plan.setPlanPeriods( atpList );
            plan.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
            plan.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
            plan.setMeta(meta);

            plan.setId( "2" );
            list.add( plan );
        }
        {
            PlanItemInfo plan = new PlanItemInfo();
            plan.setId( "planItem3" );
            RichTextInfo richText = new RichTextInfo();
            plan.setDescr( richText );
            plan.setLearningPlanId( learningPlanId );
            // ENGL 101
            String cluID = "006c881f-2250-4844-8e57-c297049c61f3";
            plan.setRefObjectId( cluID );
            String type = "";
            plan.setRefObjectType( type );
            List<String> atps = new ArrayList<String>();
            atps.add("19843");
            plan.setPlanPeriods( atps );
            plan.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
            plan.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
            plan.setMeta(meta);

            plan.setId( "1" );
            list.add( plan );
        }
        {
            PlanItemInfo plan = new PlanItemInfo();
            plan.setId( "planItem4" );
            RichTextInfo richText = new RichTextInfo();
            plan.setDescr( richText );
            plan.setLearningPlanId( learningPlanId );
            // CHEM 101
            String cluID = "00ecf8e8-333c-41fd-877c-afd2845294e4";
            plan.setRefObjectId( cluID );
            String type = "";
            plan.setRefObjectType( type );
            List<String> atps = new ArrayList<String>();
            atps.add("19843");
            plan.setPlanPeriods( atps );
            plan.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
            plan.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
            plan.setMeta(meta);
            plan.setId( "1" );
            list.add( plan );

        }
        {
            PlanItemInfo plan = new PlanItemInfo();
            plan.setId( "planItem5" );
            RichTextInfo richText = new RichTextInfo();
            plan.setDescr( richText );
            plan.setLearningPlanId( learningPlanId );
            // HIST 101
            String cluID = "011082d2-0822-4c55-a972-0c10c78dbcfa";
            plan.setRefObjectId( cluID );
            String type = "";
            plan.setRefObjectType( type );
            List<String> atps = new ArrayList<String>();
            atps.add("19843");
            plan.setPlanPeriods( atps );
            plan.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
            plan.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);
            plan.setMeta(meta);

            list.add( plan );
        }

        // UnComment next line if testing for empty list and comment out 'return list';
        //return new ArrayList<PlanItem>();
        return list;
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByAtp(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "category") AcademicPlanServiceConstants.ItemCategory category, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInPlanByRefObjectIdByRefObjectType(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<PlanItemInfo> planItemInfos=new ArrayList<PlanItemInfo>();
        return planItemInfos;
    }

    @Override
    public PlanItemSetInfo getPlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PlanItemInfo> getPlanItemsInSet(@WebParam(name = "planItemSetId") String planItemSetId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<LearningPlanInfo> getLearningPlansForStudentByType(@WebParam(name = "studentId") String studentId, @WebParam(name = "planTypeKey") String planTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LearningPlanInfo> list = new ArrayList<LearningPlanInfo>();
        LearningPlanInfo plan = new LearningPlanInfo();
        plan.setStudentId( studentId );
        plan.setId("learningPlan1");
        plan.setTypeKey(planTypeKey);
        plan.setShared(false);
        list.add( plan );

        return list;
    }

    @Override
    public LearningPlanInfo createLearningPlan(@WebParam(name = "learningPlan") LearningPlanInfo learningPlan, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PlanItemInfo createPlanItem(@WebParam(name = "planItem") PlanItemInfo planItem, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        PlanItemInfo plan = new PlanItemInfo();
        plan.setId( "planItem1" );
        RichTextInfo richText = new RichTextInfo();
        plan.setDescr( richText );
        // ENGL 101
        String cluID = "0114603e-418a-4ac8-bdff-e09ca09fae4a";
        plan.setRefObjectId( cluID );
        String type = "";
        plan.setRefObjectType( type );
        List<String> atps = new ArrayList<String>();
        atps.add("19843");
        plan.setPlanPeriods( atps );
        plan.setCategory(AcademicPlanServiceConstants.ItemCategory.PLANNED);
        plan.setTypeKey(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE);

        plan.setId("1");


        return plan;
    }

    @Override
    public PlanItemSetInfo createPlanItemSet(@WebParam(name = "planItemSet") PlanItemSetInfo planItemSet, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LearningPlanInfo updateLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "learningPlan") LearningPlanInfo learningPlan, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PlanItemInfo updatePlanItem(@WebParam(name = "planItemId") String planItemId, @WebParam(name = "planItem") PlanItemInfo planItem, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PlanItemSetInfo updatePlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId, @WebParam(name = "planItemSet") PlanItemSetInfo planItemSet, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteLearningPlan(@WebParam(name = "learningPlanId") String learningPlanId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deletePlanItem(@WebParam(name = "planItemId") String planItemId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deletePlanItemSet(@WebParam(name = "planItemSetId") String planItemSetId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateLearningPlan(@WebParam(name = "validationType") String validationType, @WebParam(name = "learningPlanInfo") LearningPlanInfo learningPlanInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validatePlanItem(@WebParam(name = "validationType") String validationType, @WebParam(name = "planItemInfo") PlanItemInfo planItemInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validatePlanItemSet(@WebParam(name = "validationType") String validationType, @WebParam(name = "planItemSetInfo") PlanItemSetInfo planItemSetInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
