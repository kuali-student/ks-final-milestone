/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 9/25/2014
 */
package org.kuali.student.enrollment.class2.coursewaitlist2.service.impl;


import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.coursewaitlist.dao.CourseWaitListDao;
import org.kuali.student.enrollment.class2.coursewaitlist.model.CourseWaitListAttributeEntity;
import org.kuali.student.enrollment.class2.coursewaitlist.model.CourseWaitListEntity;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist2.dto.ActivityOfferingWaitListConfigInfo;
import org.kuali.student.enrollment.coursewaitlist2.dto.AltActivityWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist2.dto.AltCourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist2.dto.WaitlistInfo;
import org.kuali.student.enrollment.coursewaitlist2.infc.ActivityOfferingWaitListConfig;
import org.kuali.student.enrollment.coursewaitlist2.infc.WaitListConfig;
import org.kuali.student.enrollment.coursewaitlist2.service.AltCourseWaitListService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An alternate API for the Course Wait List Service
 *
 * @author Kuali Student Team
 */
public class AltCourseWaitListServiceImpl implements AltCourseWaitListService {
    public static final Logger LOG = LoggerFactory.getLogger(AltCourseWaitListServiceImpl.class);
    private LprService lprService;
    private CourseWaitListDao courseWaitListDao;
    private CourseOfferingService courseOfferingService;

    public LprService getLprService() {
        if (lprService == null) {
            lprService = GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE,
                    LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public void setCourseWaitListDao(CourseWaitListDao courseWaitListDao) {
        this.courseWaitListDao = courseWaitListDao;
    }

    @Override
    public AltCourseWaitListEntryInfo getCourseWaitListEntry(String courseWaitListEntryId,
                                                             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public AltActivityWaitListEntryInfo getActivityWaitListEntry(String activityWaitListEntryId,
                                                                 ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public WaitListConfig getGlobalWaitListConfig(ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public ActivityOfferingWaitListConfigInfo getActivityOfferingWaitListConfig(String activityOfferingWaitListConfigId,
                                                                                ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        CourseWaitListEntity courseWaitListEntity = courseWaitListDao.find(activityOfferingWaitListConfigId);
        if (null == courseWaitListEntity) {
            throw new DoesNotExistException(activityOfferingWaitListConfigId);
        }
        ActivityOfferingWaitListConfigInfo configInfo = convertCourseWaitListEntity(courseWaitListEntity);
        return configInfo;
    }

    /**
     * Basically, a copy of toDto() from CourseWaitListEntity minus a bunch of fields
     * @param entity The JPA entity associated with a CourseWaitList
     * @return ActivityOfferingWaitListConfigInfo object with data filled in
     */
    private ActivityOfferingWaitListConfigInfo convertCourseWaitListEntity(CourseWaitListEntity entity) {
        ActivityOfferingWaitListConfigInfo configInfo = new ActivityOfferingWaitListConfigInfo();
        configInfo.setId(entity.getId());
        configInfo.setStateKey(entity.getState());
        configInfo.setTypeKey(entity.getType());
        configInfo.setMaxSize(entity.getMaxSize());

        configInfo.setEffectiveDate(entity.getEffectiveDate());
        configInfo.setExpirationDate(entity.getExpirationDate());
        configInfo.setMeta(entity.toDTO());
        configInfo.setAttributes(new ArrayList<AttributeInfo>());
        for(CourseWaitListAttributeEntity att : entity.getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            configInfo.getAttributes().add(attInfo);
        }
        if (entity.getActivityOfferingIds() != null) {
            configInfo.setActivityOfferingIds(new ArrayList<String>());
            configInfo.getActivityOfferingIds().addAll(entity.getActivityOfferingIds());
        }
        if (entity.getFormatOfferingIds() != null) {
            configInfo.setFormatOfferingIds(new ArrayList<String>());
            configInfo.getFormatOfferingIds().addAll(entity.getFormatOfferingIds());
        }

        return configInfo;
    }

    @Override
    public List<ActivityOfferingWaitListConfigInfo>
    getActivityOfferingWaitListConfigsByActivityOffering(String activityOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> aoIds = new ArrayList<>();
        aoIds.add(activityOfferingId);
        List<CourseWaitListEntity> entities = courseWaitListDao.getCourseWaitListsByActivityOfferingIds(aoIds);
        List<ActivityOfferingWaitListConfigInfo> infoList = new ArrayList<>();
        for (CourseWaitListEntity entity : entities) {
            ActivityOfferingWaitListConfigInfo configInfo = convertCourseWaitListEntity(entity);
            infoList.add(configInfo);
        }
        return infoList;
    }

    @Override
    public List<ActivityOfferingWaitListConfigInfo>
    getActivityOfferingWaitListConfigsByFormatOffering(String formatOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> foIds = new ArrayList<>();
        foIds.add(formatOfferingId);
        List<CourseWaitListEntity> entities = courseWaitListDao.getCourseWaitListsByFormatOfferingIds(foIds);
        List<ActivityOfferingWaitListConfigInfo> infoList = new ArrayList<>();
        for (CourseWaitListEntity entity : entities) {
            ActivityOfferingWaitListConfigInfo configInfo = convertCourseWaitListEntity(entity);
            infoList.add(configInfo);
        }
        return infoList;
    }

    @Override
    public ActivityOfferingWaitListConfigInfo
    createActivityOfferingWaitListConfig(String activityOfferingWaitListConfigTypeKey,
                                         ActivityOfferingWaitListConfigInfo activityOfferingWaitListConfigInfo,
                                         ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        CourseWaitListInfo courseWaitListInfo = makeCourseWaitListInfoFromAOWLCInfo(activityOfferingWaitListConfigInfo);

        CourseWaitListEntity courseWaitListEntity = new CourseWaitListEntity(courseWaitListInfo);
        courseWaitListEntity.setEntityCreated(contextInfo);
        courseWaitListEntity.setType(activityOfferingWaitListConfigTypeKey);
        courseWaitListDao.persist(courseWaitListEntity);

        courseWaitListDao.getEm().flush();

        return convertCourseWaitListEntity(courseWaitListEntity);
    }

    private CourseWaitListInfo
    makeCourseWaitListInfoFromAOWLCInfo(ActivityOfferingWaitListConfigInfo activityOfferingWaitListConfigInfo) {
        CourseWaitListInfo courseWaitListInfo = new CourseWaitListInfo();
        courseWaitListInfo.setId(activityOfferingWaitListConfigInfo.getId());
        courseWaitListInfo.setTypeKey(activityOfferingWaitListConfigInfo.getTypeKey());
        courseWaitListInfo.setStateKey(activityOfferingWaitListConfigInfo.getStateKey());
        courseWaitListInfo.setEffectiveDate(activityOfferingWaitListConfigInfo.getEffectiveDate());
        courseWaitListInfo.setExpirationDate(activityOfferingWaitListConfigInfo.getExpirationDate());
        courseWaitListInfo.setMaxSize(activityOfferingWaitListConfigInfo.getMaxSize());
        courseWaitListInfo.setMeta(activityOfferingWaitListConfigInfo.getMeta());
        return courseWaitListInfo;
    }

    @Override
    public ActivityOfferingWaitListConfig
    updateActivityOfferingWaitListConfig(String activityOfferingWaitListConfigId,
                                         ActivityOfferingWaitListConfigInfo activityOfferingWaitListConfigInfo,
                                         ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        if (null != activityOfferingWaitListConfigId) {
            CourseWaitListEntity courseWaitListEntity = courseWaitListDao.find(activityOfferingWaitListConfigId);
            if (null != courseWaitListEntity) {
                CourseWaitListInfo courseWaitListInfo
                        = makeCourseWaitListInfoFromAOWLCInfo(activityOfferingWaitListConfigInfo);
                courseWaitListEntity.fromDto(courseWaitListInfo);
                courseWaitListEntity.setEntityUpdated(contextInfo);

                courseWaitListEntity = courseWaitListDao.merge(courseWaitListEntity);

                courseWaitListDao.getEm().flush();

                return convertCourseWaitListEntity(courseWaitListEntity);
            } else {
                throw new DoesNotExistException("No ActivityOfferingWaitListConfig for id = " + activityOfferingWaitListConfigId);
            }
        } else {
            throw new InvalidParameterException("activityOfferingWaitListConfig can not be null");
        }
    }

    @Override
    public List<WaitlistInfo> getPeopleToProcessFromWaitlist(List<String> aoIds,
                                                             Map<String, Integer> aoid2openSeatsMap,
                                                             ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {
        throw new UnsupportedOperationException("getPeopleToProcessFromWaitlist");
    }
}
