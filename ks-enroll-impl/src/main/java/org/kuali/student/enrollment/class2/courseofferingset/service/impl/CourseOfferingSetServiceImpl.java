/**
 * Copyright 2012 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Created by Charles on 2/28/12
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class2.courseofferingset.dao.SocDao;
import org.kuali.student.enrollment.class2.courseofferingset.dao.SocRolloverResultDao;
import org.kuali.student.enrollment.class2.courseofferingset.dao.SocRolloverResultItemDao;
import org.kuali.student.enrollment.class2.courseofferingset.model.SocAttributeEntity;
import org.kuali.student.enrollment.class2.courseofferingset.model.SocEntity;
import org.kuali.student.enrollment.class2.courseofferingset.model.SocRolloverResultAttributeEntity;
import org.kuali.student.enrollment.class2.courseofferingset.model.SocRolloverResultEntity;
import org.kuali.student.enrollment.class2.courseofferingset.model.SocRolloverResultItemEntity;
import org.kuali.student.enrollment.class2.courseofferingset.model.SocRolloverResultOptionEntity;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetServiceBusinessLogic;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.state.service.StateTransitionsHelper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseOfferingSetServiceImpl implements CourseOfferingSetService {
    final static Logger LOG = Logger.getLogger(CourseOfferingSetServiceImpl.class);

    @Resource
    private SocDao socDao;
    @Resource
    private SocRolloverResultDao socRorDao;
    @Resource
    private SocRolloverResultItemDao socRorItemDao;
    private CourseOfferingSetServiceBusinessLogic businessLogic;
    private CriteriaLookupService criteriaLookupService;
    private StateTransitionsHelper stateTransitionsHelper;
    private StateService stateService;

    public CourseOfferingSetServiceBusinessLogic getBusinessLogic() {
        return businessLogic;
    }

    public void setBusinessLogic(CourseOfferingSetServiceBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public SocDao getSocDao() {
        return socDao;
    }

    public void setSocDao(SocDao socDao) {
        this.socDao = socDao;
    }

    public SocRolloverResultDao getSocRorDao() {
        return socRorDao;
    }

    public void setSocRorDao(SocRolloverResultDao socRorDao) {
        this.socRorDao = socRorDao;
    }

    public SocRolloverResultItemDao getSocRorItemDao() {
        return socRorItemDao;
    }

    public void setSocRorItemDao(SocRolloverResultItemDao socRorItemDao) {
        this.socRorItemDao = socRorItemDao;
    }

    public StateTransitionsHelper getStateTransitionsHelper() {
        return stateTransitionsHelper;
    }

    public void setStateTransitionsHelper(StateTransitionsHelper stateTransitionsHelper) {
        this.stateTransitionsHelper = stateTransitionsHelper;
    }

    ////
    //// implement service methods
    ////
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocInfo createSoc(String termId, String typeKey, SocInfo info, ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        if (!termId.equals(info.getTermId())) {
            throw new InvalidParameterException("termId does not match the value in the info object");
        }
        if (!typeKey.equals(info.getTypeKey())) {
            throw new InvalidParameterException("typeKey does not match the value in the info object");
        }
        // verify socInfo has the the inital state of the Soc
         List<String> initSocStates = stateService.getInitialStatesByLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, context);
        if(!initSocStates.isEmpty()) {
            if(!initSocStates.contains(info.getStateKey())) {
                throw new InvalidParameterException("Wrong initial SOC state key");
            }
        }

        SocEntity entity = new SocEntity(info);
        this.logStateChange(entity, entity.getSocState(), context);
        entity.setEntityCreated(context);
        socDao.persist(entity);
        socDao.getEm().flush();
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocRolloverResultInfo createSocRolloverResult(String typeKey, SocRolloverResultInfo info, ContextInfo context) throws
            DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        if (!typeKey.equals(info.getTypeKey())) {
            throw new InvalidParameterException("TypeKey does not match the value in the info object");
        }
        SocRolloverResultEntity entity = new SocRolloverResultEntity(info);
        entity.setId(info.getId());
        entity.setSocRorType(typeKey);

        entity.setEntityCreated(context);

        socRorDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocRolloverResultItemInfo createSocRolloverResultItem(String socRorId, String typeKey, SocRolloverResultItemInfo info, ContextInfo context) throws
            DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        if (!typeKey.equals(info.getTypeKey())) {
            throw new InvalidParameterException("TypeKey does not match the value in the info object");
        }
        SocRolloverResultItemEntity entity = new SocRolloverResultItemEntity(info);
        entity.setId(info.getId());
        entity.setSocRorType(typeKey);

        entity.setEntityCreated(context);

        socRorItemDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public Integer createSocRolloverResultItems(String socRorId, String typeKey, List<SocRolloverResultItemInfo> infos, ContextInfo context)
            throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        int count = 0;
        for (SocRolloverResultItemInfo info : infos) {
            count++;
            if (!typeKey.equals(info.getTypeKey())) {
                throw new InvalidParameterException("TypeKey does not match the value in the info object " + count);
            }
            if (!socRorId.equals(info.getSocRolloverResultId())) {
                throw new InvalidParameterException("rollover result id does not match the value in the info object " + count);
            }
            SocRolloverResultItemEntity entity = new SocRolloverResultItemEntity(info);
            entity.setId(info.getId());
            entity.setSocRorType(typeKey);

            entity.setEntityCreated(context);

            socRorItemDao.persist(entity);
        }
        return Integer.valueOf(count);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public Integer deleteCourseOfferingsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.businessLogic.deleteCourseOfferingsBySoc(socId, context);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteSoc(String id, ContextInfo context) throws DependentObjectsExistException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        SocEntity entity = socDao.find(id);
        if (null == entity) {
            throw new DoesNotExistException(id);
        }
        socDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteSocRolloverResult(String id, ContextInfo context) throws DoesNotExistException,
            DependentObjectsExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        SocRolloverResultEntity entity = socRorDao.find(id);
        if (null == entity) {
            throw new DoesNotExistException(id);
        }
        List<SocRolloverResultItemInfo> items = this.getSocRolloverResultItemsByResultId(id, context);
        if (!items.isEmpty()) {
            throw new DependentObjectsExistException(items.size() + " items exist");
        }
        socRorDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteSocRolloverResultItem(String id, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        SocRolloverResultItemEntity entity = socRorItemDao.find(id);
        if (null == entity) {
            throw new DoesNotExistException(id);
        }
        socRorItemDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.businessLogic.getCourseOfferingIdsBySoc(socId, context);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCourseOfferingIdsWithUnscheduledFinalExamsBySoc(String socId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Configuration error Implemented in the calculuation layer");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getPublishedCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.businessLogic.getPublishedCourseOfferingIdsBySoc(socId, context);
    }

    @Override
    @Transactional(readOnly = true)
    public SocInfo getSoc(String id, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        SocEntity entity = socDao.find(id);
        if (null == entity) {
            throw new DoesNotExistException(id);
        }
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSocIdsByCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Configuration error Implemented in the calculuation layer");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSocIdsByTerm(String termId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return socDao.getSocIdsByTerm(termId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSocIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return socDao.getSocIdsByTermAndSubjectArea(termId, subjectArea);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSocIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return socDao.getSocIdsByTermAndUnitsContentOwner(termId, unitsContentOwnerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSocIdsByType(String typeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return socDao.getSocIdsByType(typeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocInfo> searchForSocs(QueryByCriteria criteria,
                                       ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        GenericQueryResults<SocEntity> results = criteriaLookupService.lookup(SocEntity.class, criteria);
        List<SocInfo> socInfos = new ArrayList<SocInfo>(results.getResults().size());
        for (SocEntity socEntity : results.getResults()) {
            socInfos.add(socEntity.toDto());
        }
        return socInfos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForSocIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<String> results = criteriaLookupService.lookupIds(SocEntity.class, criteria);
        return results.getResults();
    }

    @Override
    @Transactional(readOnly = true)
    public SocRolloverResultInfo getSocRolloverResult(String id, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        SocRolloverResultEntity entity = socRorDao.find(id);
        if (null == entity) {
            throw new DoesNotExistException(id);
        }
        SocRolloverResultInfo info = entity.toDto();
        this.updateCalculatedFields(info, context);
        return info;
    }

    // TODO: implement this logic with direct counts for efficiency once the logic for the counts settles down.
    // My GUT says that they may want more counts than just the 2 we are getting now... I.e. count of warnings?    
    private void updateCalculatedFields(SocRolloverResultInfo info, ContextInfo context) throws OperationFailedException {
        try {
            if (info.getSourceSocId() != null) {
                SocInfo sourceSoc = this.getSoc(info.getSourceSocId(), context);
                info.setSourceTermId(sourceSoc.getTermId());
            }
            // only do the calc once finished or the querying while running will be too long
            if (info.getStateKey().equals(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY)) {
                List<SocRolloverResultItemInfo> items = this.getSocRolloverResultItemsByResultId(info.getId(), context);
                int success = 0;
                int failure = 0;
                for (SocRolloverResultItemInfo item : items) {
                    if (CourseOfferingSetServiceConstants.SUCCESSFUL_RESULT_ITEM_STATES.contains(item.getStateKey())) {
                        success++;
                    } else {
                        failure++;
                    }
                }
                info.setCourseOfferingsCreated(success);
                info.setCourseOfferingsSkipped(failure);
            }
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    @Override
    public List<SocRolloverResultInfo> getSocRolloverResultsBySourceAndTargetSocs(String sourceSocId, String targetSocId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO: implement this as a JPQL search
        List<SocRolloverResultInfo> list = new ArrayList<SocRolloverResultInfo>();
        List<String> ids = this.getSocRolloverResultIdsBySourceSoc(sourceSocId, context);
        for (String id : ids) {
            SocRolloverResultInfo info = this.getSocRolloverResult(id, context);
            if (targetSocId.equals(info.getTargetSocId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSocRolloverResultIdsBySourceSoc(String sourceSocId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return socRorDao.getSocRolloverResultIdsBySourceSocId(sourceSocId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSocRolloverResultIdsByTargetSoc(String targetSocId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return socRorDao.getSocRolloverResultIdsByTargetSocId(targetSocId);
    }

    @Override
    @Transactional(readOnly = true)
    public SocRolloverResultItemInfo getSocRolloverResultItem(String id, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        SocRolloverResultItemEntity entity = socRorItemDao.find(id);
        if (null == entity) {
            throw new DoesNotExistException(id);
        }
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultId(String socRolloverResultId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<SocRolloverResultItemEntity> entities = socRorItemDao.getBySocRolloverResultId(socRolloverResultId);
        List<SocRolloverResultItemInfo> list = new ArrayList<SocRolloverResultItemInfo>(entities.size());
        for (SocRolloverResultItemEntity entity : entities) {
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultIdAndSourceCourseOfferingId(String socRolloverResultId, String sourceCourseOfferingId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<SocRolloverResultItemEntity> entities = socRorItemDao.getBySocRolloverResultIdAndSourceCourseOfferingId(
                socRolloverResultId, sourceCourseOfferingId);
        List<SocRolloverResultItemInfo> list = new ArrayList<SocRolloverResultItemInfo>(entities.size());
        for (SocRolloverResultItemEntity entity : entities) {
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultIdAndTargetCourseOfferingId(String socRolloverResultId, String targetCourseOfferingId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<SocRolloverResultItemEntity> entities = socRorItemDao.getBySocRolloverResultIdAndTargetCourseOfferingId(
                socRolloverResultId, targetCourseOfferingId);
        List<SocRolloverResultItemInfo> list = new ArrayList<SocRolloverResultItemInfo>(entities.size());
        for (SocRolloverResultItemEntity entity : entities) {
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocRolloverResultInfo> getSocRolloverResultsByIds(List<String> ids, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<SocRolloverResultEntity> entities = socRorDao.findByIds(ids);
        List<SocRolloverResultInfo> list = new ArrayList<SocRolloverResultInfo>(entities.size());
        for (SocRolloverResultEntity entity : entities) {
            if (entity == null) {
                // if one of the entities from "findByIds" is returned as null,
                // then one of the keys in the list was not found
                throw new DoesNotExistException(ids.get(list.size()));
            }
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByIds(List<String> ids, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<SocRolloverResultItemEntity> entities = socRorItemDao.findByIds(ids);
        List<SocRolloverResultItemInfo> list = new ArrayList<SocRolloverResultItemInfo>(entities.size());
        for (SocRolloverResultItemEntity entity : entities) {
            if (entity == null) {
                // if one of the entities from "findByIds" is returned as null,
                // then one of the keys in the list was not found
                throw new DoesNotExistException(ids.get(list.size()));
            }
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocInfo> getSocsByIds(List<String> ids, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<SocEntity> entities = socDao.findByIds(ids);
        List<SocInfo> list = new ArrayList<SocInfo>(entities.size());
        for (SocEntity entity : entities) {
            if (entity == null) {
                // if one of the entities from "findByIds" is returned as null,
                // then one of the keys in the list was not found
                throw new DoesNotExistException(ids.get(list.size()));
            }
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getUnpublishedActivityOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Configuration error Implemented in the calculuation layer");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getUnpublishedCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.businessLogic.getUnpublishedCourseOfferingIdsBySoc(socId, context);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getUnscheduledActivityOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.businessLogic.getUnscheduledActivityOfferingIdsBySoc(socId, context);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isCourseOfferingInSoc(String socId, String courseOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.businessLogic.isCourseOfferingInSoc(socId, courseOfferingId, context);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocRolloverResultInfo reverseRollover(String rolloverResultId, List<String> optionKeys, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.businessLogic.reverseRollover(rolloverResultId, optionKeys, context);
    }

    @Override
    // Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocInfo rolloverSoc(String sourceSocId, String targetTermId, List<String> optionKeys, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.businessLogic.rolloverSoc(sourceSocId, targetTermId, optionKeys, context);
    }

    @Override
    public StatusInfo startScheduleSoc(String socId, List<String> optionKeys, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        return this.businessLogic.startScheduleSoc(socId, optionKeys, context);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocInfo updateSoc(String id, SocInfo info, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        SocEntity entity = socDao.find(id);
        if (entity == null) {
            throw new DoesNotExistException(id);
        }
        if (!entity.getSocState().equals (info.getStateKey())) {
            throw new ReadOnlyException ("state key can only be changed by calling changeSocState");
        }
        entity.fromDTO(info);

        entity.setEntityUpdated(context);

        return socDao.merge(entity).toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocRolloverResultInfo updateSocRolloverProgress(String id, Integer itemsProcessed, ContextInfo context) throws
            DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        SocRolloverResultEntity entity = socRorDao.find(id);
        if (entity == null) {
            throw new DoesNotExistException(id);
        }
        entity.setItemsProcessed(itemsProcessed);

        entity.setEntityUpdated(context);

        Set<SocRolloverResultAttributeEntity> resultAttributeEntities = entity.getAttributes();
        for (SocRolloverResultAttributeEntity attr : resultAttributeEntities) {
            if (CourseOfferingSetServiceConstants.DATE_COMPLETED_RESULT_DYNATTR_KEY.equals(attr.getKey())) {
                // Update the date completed
                attr.setValue(TransformUtility.dateTimeToDynamicAttributeString(new Date()));
            }
        }
        return socRorDao.merge(entity).toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocRolloverResultInfo updateSocRolloverResult(String id, SocRolloverResultInfo info, ContextInfo context) throws
            DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        SocRolloverResultEntity entity = socRorDao.find(id);
        if (entity == null) {
            throw new DoesNotExistException(id);
        }
        // remove any options that are no longer part of the group
        // Adding additional ones is accomplished in the SockRolloverResultEntity
        // But had to do this here because needed access to the entity manager
        List<SocRolloverResultOptionEntity> notDeletedOptions = new ArrayList<SocRolloverResultOptionEntity>(
                entity.getOptions().size());
        for (SocRolloverResultOptionEntity optionEntity : entity.getOptions()) {
            if (!info.getOptionKeys().contains(optionEntity.getOptionId())) {
                socDao.getEm().remove(optionEntity);
            } else {
                notDeletedOptions.add(optionEntity);
            }
        }
        entity.setOptions(new HashSet<SocRolloverResultOptionEntity>(notDeletedOptions));
        entity.fromDTO(info);

        entity.setEntityUpdated(context);

        return socRorDao.merge(entity).toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocRolloverResultItemInfo updateSocRolloverResultItem(String id, SocRolloverResultItemInfo info, ContextInfo context) throws
            DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        SocRolloverResultItemEntity entity = socRorItemDao.find(id);
        if (entity == null) {
            throw new DoesNotExistException(id);
        }
        entity.fromDTO(info);

        entity.setEntityUpdated(context);

        return socRorItemDao.merge(entity).toDto();
    }

    @Override
    public List<ValidationResultInfo> validateSoc(String validationType, SocInfo socInfo, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateSocRolloverResult(String validationType, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateSocRolloverResultItem(String validationType, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForSocRolloverResultIds(QueryByCriteria criteria,  ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException { 
        GenericQueryResults<String> results = criteriaLookupService.lookupIds(SocRolloverResultEntity.class,
                criteria);
        return results.getResults();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocRolloverResultInfo> searchForSocRolloverResults(QueryByCriteria criteria,  ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<SocRolloverResultEntity> results = criteriaLookupService.lookup(SocRolloverResultEntity.class,
                criteria);
        List<SocRolloverResultInfo> socRolloverResultInfos = new ArrayList<SocRolloverResultInfo>(results.getResults().size());
        for (SocRolloverResultEntity socRolloverResult : results.getResults()) {
            SocRolloverResultInfo socRolloverResultInfo = socRolloverResult.toDto();
            socRolloverResultInfos.add(socRolloverResultInfo);
        }
        return socRolloverResultInfos;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }


    private void _todoHandleInvalidSocState(String nextStateKey) throws InvalidParameterException {
        // determine if the state key given is a SOC lifecycle state or a scheduling state
        boolean isSchedulingState = Arrays.asList(CourseOfferingSetServiceConstants.ALL_SOC_SCHEDULING_STATES).contains(nextStateKey);

        if (isSchedulingState) {  // Should not need to test this--let decorator do the work
            throw new InvalidParameterException(nextStateKey + " is an invalid SOC state");
        }
    }

    @Override
    @Transactional(readOnly = false, timeout=6168000, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeSocState(String socId,
                                     String nextStateKey,
                                     ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        org.springframework.transaction.PlatformTransactionManager mgr;
        socDao.getEm().flush(); // need to flush to get the version ind to update

        SocEntity entity = socDao.find(socId);
        if (entity == null) {
            throw new DoesNotExistException(socId);
        }
        String thisStateKey = entity.getSocState();
        _todoHandleInvalidSocState(nextStateKey); // May want to get rid of this

        if (!StringUtils.isEmpty(nextStateKey) && !thisStateKey.equals(nextStateKey)) {
            StatusInfo scStatus = stateTransitionsHelper.processStateConstraints(socId, nextStateKey, contextInfo);
            if(scStatus.getIsSuccess()) {
                entity.setSocState(nextStateKey);
                // Log the state change
                logStateChange(entity, nextStateKey, contextInfo);
                LOG.warn(String.format("Updated SOC [%s] state to [%s].", socId, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY));

                entity.setEntityUpdated(contextInfo);
                socDao.merge(entity);
                //socDao.getEm().flush(); // need to flush to get the version ind to update
                //propagation
                Map<String, StatusInfo> spStatusMap = stateTransitionsHelper.processStatePropagations(socId, thisStateKey + ":" + nextStateKey, contextInfo);
                for (StatusInfo statusInfo : spStatusMap.values()) {
                    if (!statusInfo.getIsSuccess()){
                        throw new OperationFailedException(statusInfo.getMessage());
                    }
                }
            }
            else{
                throw new OperationFailedException(scStatus.getMessage());
            }
        }

        StatusInfo status = new StatusInfo ();
        status.setSuccess(Boolean.TRUE);
        return status;
    }


    private void logStateChange(SocEntity entity, String stateKey, ContextInfo contextInfo) {
        // add the state change to the log
        // TODO: consider changing this to a call to a real logging facility instead of stuffing it in the dynamic attributes

        // Add an attribute with a key of the state en
        AttributeInfo attr = new AttributeInfo(stateKey, DateFormatters.STATE_CHANGE_DATE_FORMATTER.format(contextInfo.getCurrentDate()));
        entity.getAttributes().add(new SocAttributeEntity(attr, entity));
    }

    @Override
    public StatusInfo changeSocRolloverResultState(
            String socId,
            String nextStateKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("To be Implemented in M5");
    }

    @Override
    public StatusInfo changeSocRolloverResultItemState(
            String socId,
            String nextStateKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("To be Implemented in M5");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForSocRolloverResultItemIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<String> results = criteriaLookupService.lookupIds(SocRolloverResultItemEntity.class,
                criteria);
        return results.getResults();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocRolloverResultItemInfo> searchForSocRolloverResultItems(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
          GenericQueryResults<SocRolloverResultItemEntity> results = criteriaLookupService.lookup(SocRolloverResultItemEntity.class,
                criteria);
        List<SocRolloverResultItemInfo> infos = new ArrayList<SocRolloverResultItemInfo>(results.getResults().size());
        for (SocRolloverResultItemEntity entity : results.getResults()) {
            SocRolloverResultItemInfo info = entity.toDto();
            infos.add(info);
        }
        return infos;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }
}
