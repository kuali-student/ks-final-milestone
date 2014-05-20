package org.kuali.student.enrollment.class2.coursewaitlist.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.class2.coursewaitlist.dao.CourseWaitListDaoApi;
import org.kuali.student.enrollment.class2.coursewaitlist.model.CourseWaitListEntity;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.WaitListPositionInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.xml.namespace.QName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the CourseWaitList Service
 *
 * @author Kuali Student Team
 */
public class CourseWaitListServiceImpl implements CourseWaitListService {
    @Resource
    private CourseWaitListDaoApi courseWaitListDao;
    @Resource
    private LprService lprService;

    @Override
    @Transactional(readOnly = true)
    public CourseWaitListInfo getCourseWaitList(String courseWaitListId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        CourseWaitListEntity courseWaitListEntity = courseWaitListDao.find(courseWaitListId);
        if (null == courseWaitListEntity) {
            throw new DoesNotExistException(courseWaitListId);
        }
        return courseWaitListEntity.toDto();
    }
    
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CourseWaitListInfo createCourseWaitList(String courseWaitListTypeKey,
                                                   CourseWaitListInfo courseWaitListInfo,
                                                   ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        CourseWaitListEntity courseWaitListEntity = new CourseWaitListEntity(courseWaitListInfo);
        courseWaitListEntity.setEntityCreated(contextInfo);
        courseWaitListEntity.setType(courseWaitListTypeKey);
        courseWaitListDao.persist(courseWaitListEntity);
        
        courseWaitListDao.getEm().flush();
        
        return courseWaitListEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CourseWaitListInfo updateCourseWaitList(String courseWaitListId,
                                                   CourseWaitListInfo courseWaitListInfo,
                                                   ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {

        if (null != courseWaitListId) {
            CourseWaitListEntity courseWaitListEntity = courseWaitListDao.find(courseWaitListId);
            if (null != courseWaitListEntity) {
                courseWaitListEntity.fromDto(courseWaitListInfo);
                courseWaitListEntity.setEntityUpdated(contextInfo);
            
            courseWaitListEntity = courseWaitListDao.merge(courseWaitListEntity);
            
            courseWaitListDao.getEm().flush();
            
                return courseWaitListEntity.toDto();
            } else {
            throw new DoesNotExistException("No CourseWaitList for id = " + courseWaitListId);
            }
        } else {
            throw new InvalidParameterException("courseWaitListId can not be null");
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeCourseWaitListState(String courseWaitListId, String stateKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        CourseWaitListEntity courseWaitListEntity = courseWaitListDao.find(courseWaitListId);
        if (null != courseWaitListEntity) {
            courseWaitListEntity.setState(stateKey);
            courseWaitListEntity.setEntityUpdated(contextInfo);
            try {
                courseWaitListDao.merge(courseWaitListEntity);
            } catch (VersionMismatchException e) {
                throw new OperationFailedException("version mismatch for courseWaitList.id=" + courseWaitListId, e);
            }
        } else {
            throw new DoesNotExistException(courseWaitListId);
        }
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteCourseWaitList(String courseWaitListId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        CourseWaitListEntity courseWaitListEntity = courseWaitListDao.find(courseWaitListId);
        if (null != courseWaitListEntity) {
            courseWaitListDao.remove(courseWaitListEntity);
        } else {
            throw new DoesNotExistException(courseWaitListId);
        }
        return status;
    }   

    @Override
    @Transactional(readOnly = true)
    public List<CourseWaitListInfo> getCourseWaitListsByActivityOffering(String activityOfferingId,
                                                                         ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(activityOfferingId);
        List<CourseWaitListEntity> entities = courseWaitListDao.getCourseWaitListsByActivityOfferingIds(aoIds);
        List<CourseWaitListInfo> infoList = new ArrayList<CourseWaitListInfo>();
        for (CourseWaitListEntity entity : entities) {
            CourseWaitListInfo courseWaitListInfo = entity.toDto();
            infoList.add(courseWaitListInfo);
        }
        return infoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValidationResultInfo> validateCourseWaitList(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                             @WebParam(name = "courseWaitListTypeKey") String courseWaitListTypeKey,
                                                             @WebParam(name = "courseWaitListInfo") CourseWaitListInfo courseWaitListInfo,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseWaitListInfo> getCourseWaitListsByIds(@WebParam(name = "courseWaitListIds") List<String> courseWaitListIds,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCourseWaitListIdsByType(@WebParam(name = "courseWaitListTypeKey") String courseWaitListTypeKey,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }



    @Override
    @Transactional(readOnly = true)
    public List<CourseWaitListInfo> getCourseWaitListsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId,
                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        List<String> foIds = new ArrayList<String>();
        foIds.add(formatOfferingId);
        List<CourseWaitListEntity> entities = courseWaitListDao.getCourseWaitListsByFormatOfferingIds(foIds);
        List<CourseWaitListInfo> infoList = new ArrayList<CourseWaitListInfo>();
        for (CourseWaitListEntity entity : entities) {
            CourseWaitListInfo courseWaitListInfo = entity.toDto();
            infoList.add(courseWaitListInfo);
        }
        return infoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForCourseWaitListIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseWaitListInfo> searchForCourseWaitLists(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public CourseWaitListEntryInfo getCourseWaitListEntry(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByIds(@WebParam(name = "courseWaitListEntryIds") List<String> courseWaitListEntryIds,
                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCourseWaitListEntryIdsByType(@WebParam(name = "courseWaitListEntryTypeKey") String courseWaitListEntryTypeKey,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByStudent(@WebParam(name = "studentId") String studentId,
                                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByCourseWaitList(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByCourseWaitListAndStudent(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                                                                            @WebParam(name = "studentId") String studentId,
                                                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> searchForCourseWaitListEntryIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseWaitListEntryInfo> searchForCourseWaitListEntries(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValidationResultInfo> validateCourseWaitListEntry(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                  @WebParam(name = "courseWaitListId") String courseWaitListId,
                                                                  @WebParam(name = "studentId") String studentId,
                                                                  @WebParam(name = "courseWaitListEntryTypeKey") String courseWaitListEntryTypeKey,
                                                                  @WebParam(name = "courseWaitListEntryInfo") CourseWaitListEntryInfo courseWaitListEntryInfo,
                                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = true)
    public CourseWaitListEntryInfo createCourseWaitListEntry(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                                             @WebParam(name = "studentId") String studentId,
                                                             @WebParam(name = "courseWaitListEntryTypeKey") String courseWaitListEntryTypeKey,
                                                             @WebParam(name = "courseWaitListEntryInfo") CourseWaitListEntryInfo courseWaitListEntryInfo,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public CourseWaitListEntryInfo updateCourseWaitListEntry(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                                             @WebParam(name = "courseWaitListEntryInfo") CourseWaitListEntryInfo courseWaitListEntryInfo,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        throw new OperationFailedException("not implemented");
    }


    @Override
    @Transactional(readOnly = true)
    public StatusInfo changeCourseWaitListEntryState(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                                     @WebParam(name = "stateKey") String stateKey,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public StatusInfo deleteCourseWaitListEntry(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public StatusInfo reorderCourseWaitListEntries(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                                   @WebParam(name = "courseWaitListEntryIds") List<String> courseWaitListEntryIds,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public StatusInfo moveCourseWaitListEntryToOrder(@WebParam(name = "courseWaitListEntryId") String courseWaitListEntryId,
                                                     @WebParam(name = "order") Integer order,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException{
        throw new OperationFailedException("not implemented");
    }

    public void setCourseWaitListDao(CourseWaitListDaoApi courseWaitListDao) {
        this.courseWaitListDao = courseWaitListDao;
    }

    @Override
    public WaitListPositionInfo getWaitListPositionForStudent(@WebParam(name = "studentId") String studentId,
                                                              @WebParam(name = "courseWaitListId") String courseWaitListId,
                                                              @WebParam(name = "activityOfferingId") String activityOfferingId,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<CourseWaitListEntryInfo> getTopCourseWaitListEntries(@WebParam(name = "courseWaitListId") String courseWaitListId,
                                                                     @WebParam(name = "activityOfferingId") String activityOfferingId,
                                                                     @WebParam(name = "count") Integer count,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<CourseRegistrationInfo> getCourseWaitListRegistrationsByStudentAndTerm(String studentId, String termId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // Do a query for all lprs that have matching person and atp ids. Limit to RG and CO Lprs that are in state
        // registrant(might want to change to active in future)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("personId", studentId),
                PredicateFactory.equal("atpId", termId),
                PredicateFactory.in("personRelationTypeId", LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY,
                        LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY),
                PredicateFactory.equal("personRelationStateId", LprServiceConstants.ACTIVE_STATE_KEY));
        QueryByCriteria lprCriteria = qbcBuilder.build();
        List<LprInfo> lprs = getLprService().searchForLprs(lprCriteria, contextInfo);

        // Get a mapping from the master lprid to the COid since CourseRegistrationInfo has both CO and RG ids
        Map<String,String> masterLprIdToCoIdMap = new HashMap<String, String>();
        for (LprInfo lpr:lprs) {
            if (LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY.equals(lpr.getTypeKey())){
                masterLprIdToCoIdMap.put(lpr.getMasterLprId(), lpr.getLuiId());
            }
        }

        //FOr each RG lpr, greate a new courseRegistrationInfo and lookup the corresponding CO id
        List<CourseRegistrationInfo> courseRegistrations = new ArrayList<CourseRegistrationInfo>(lprs.size());
        for (LprInfo lpr: lprs) {
            if (LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY.equals(lpr.getTypeKey())) {
                courseRegistrations.add(toCourseRegistration(lpr, masterLprIdToCoIdMap.get(lpr.getMasterLprId())));
            }
        }
        return courseRegistrations;
    }

    private CourseRegistrationInfo toCourseRegistration(LprInfo rgLpr, String courseOfferingId) {
        CourseRegistrationInfo courseRegistration = new CourseRegistrationInfo();
        for(String rvgKey:rgLpr.getResultValuesGroupKeys()){
            if (rvgKey.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_BASE)) {
                courseRegistration.setGradingOptionId(rvgKey);
            } else if (rvgKey.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE)) {
                //This will be replaced with just the key in the future
                courseRegistration.setCredits(new KualiDecimal(rvgKey.substring(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE.length() + 1)));
            }
        }
        courseRegistration.setId(rgLpr.getId());
        courseRegistration.setStateKey(rgLpr.getStateKey());
        courseRegistration.setTypeKey(rgLpr.getTypeKey());
        courseRegistration.setRegistrationGroupId(rgLpr.getLuiId());
        courseRegistration.setCourseOfferingId(courseOfferingId);
        courseRegistration.setPersonId(rgLpr.getPersonId());
        courseRegistration.setTermId(rgLpr.getAtpId());
        courseRegistration.setAttributes(rgLpr.getAttributes());
        courseRegistration.setMeta(rgLpr.getMeta());
        courseRegistration.setEffectiveDate(rgLpr.getEffectiveDate());
        courseRegistration.setExpirationDate(rgLpr.getExpirationDate());
        return courseRegistration;
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityWaitListRegistrationsForCourseRegistration(String courseRegistrationId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Do a search for AO lprs with the masterLPR matching the course registration
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("masterLprId", courseRegistrationId),
                PredicateFactory.in("personRelationTypeId", LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY),
                PredicateFactory.equal("personRelationStateId", LprServiceConstants.ACTIVE_STATE_KEY));
        QueryByCriteria lprCriteria = qbcBuilder.build();
        List<LprInfo> lprs = getLprService().searchForLprs(lprCriteria, contextInfo);

        //Convert these lprs to ActivityRegistrationInfos
        List<ActivityRegistrationInfo> activityRegistrations = new ArrayList<ActivityRegistrationInfo>(lprs.size());
        for(LprInfo lpr:lprs){
            ActivityRegistrationInfo activityRegistration = new ActivityRegistrationInfo();
            activityRegistration.setTypeKey(lpr.getTypeKey());
            activityRegistration.setStateKey(lpr.getStateKey());
            activityRegistration.setId(lpr.getId());
            activityRegistration.setEffectiveDate(lpr.getEffectiveDate());
            activityRegistration.setExpirationDate(lpr.getExpirationDate());
            activityRegistration.setTermId(lpr.getAtpId());
            activityRegistration.setAttributes(lpr.getAttributes());
            activityRegistration.setActivityOfferingId(lpr.getLuiId());
            activityRegistration.setPersonId(lpr.getPersonId());
            activityRegistration.setCourseRegistrationId(lpr.getMasterLprId());
            activityRegistration.setMeta(lpr.getMeta());
            activityRegistrations.add(activityRegistration);
        }
        return activityRegistrations;
    }

    public LprService getLprService() {
        if (lprService == null) {
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }
}
