/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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

public class CourseOfferingSetServiceMockImpl implements CourseOfferingSetService {

    private CourseOfferingService coService;
    private CourseService courseService;
    private AcademicCalendarService acalService;

    public CourseOfferingService getCoService() {
        return coService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    // implement the methods
    @Override
    public SocInfo getSoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.socMap.containsKey(socId)) {
            throw new DoesNotExistException(socId);
        }
        return this.socMap.get(socId);
    }

    @Override
    public List<SocInfo> getSocsByIds(List<String> socIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<SocInfo> list = new ArrayList<SocInfo>();
        for (String id : socIds) {
            list.add(this.getSoc(id, context));
        }
        return list;
    }

    @Override
    public List<String> getSocIdsByTerm(String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (SocInfo info : socMap.values()) {
            if (termId.equals(info.getTermId())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getSocIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (SocInfo info : socMap.values()) {
            if (termId.equals(info.getTermId())) {
                if (subjectArea.equals(info.getSubjectArea())) {
                    list.add(info.getId());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getSocIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (SocInfo info : socMap.values()) {
            if (termId.equals(info.getTermId())) {
                if (unitsContentOwnerId.equals(info.getUnitsContentOwnerId())) {
                    list.add(info.getId());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getSocIdsByType(String typeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (SocInfo info : socMap.values()) {
            if (typeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, SocInfo> socMap = new LinkedHashMap<String, SocInfo>();

    @Override
    public SocInfo createSoc(String termId, String socTypeKey, SocInfo socInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        if (!socTypeKey.equals(socInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        SocInfo copy = new SocInfo(socInfo);
        if (copy.getId() == null) {
            copy.setId(socMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        socMap.put(copy.getId(), copy);
        return new SocInfo(copy);
    }

    @Override
    public SocInfo updateSoc(String socId, SocInfo socInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        if (!socId.equals(socInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        SocInfo copy = new SocInfo(socInfo);
        SocInfo old = this.getSoc(socInfo.getId(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.socMap.put(socInfo.getId(), copy);
        return new SocInfo(copy);
    }

    @Override
    public StatusInfo deleteSoc(String socId, ContextInfo context)
            throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (this.socMap.remove(socId) == null) {
            throw new DoesNotExistException(socId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateSoc(String validationType, SocInfo socInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<String> getSocIdsByCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (SocInfo info : socMap.values()) {
            if (this.isCourseOfferingInSoc(info.getId(), courseOfferingId, context)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getCourseOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        SocInfo soc = this.getSoc(socId, context);
        if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
            return coService.getCourseOfferingIdsByTerm(soc.getTermId(), Boolean.TRUE, context);
        }
        throw new OperationFailedException(soc.getTypeKey() + " is an unsupported type for this implementation");
//        List<String> list = new ArrayList<String>();
//        for (CourseOfferingInfo info : courseOfferingMap.values()) {
//            if (socId.equals(info.getSocId())) {
//                list.add(info.getId());
//            }
//        }
//        return list;
    }

    @Override
    public Integer deleteCourseOfferingsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> ids = this.getCourseOfferingIdsBySoc(socId, context);
        for (String id : ids) {
            this.coService.deleteCourseOffering(socId, context);
        }
        return ids.size();
    }

    @Override
    public Boolean isCourseOfferingInSoc(String socId, String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> ids = this.getCourseOfferingIdsBySoc(socId, context);
        return ids.contains(courseOfferingId);
    }

    @Override
    public List<String> getPublishedCourseOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        List<String> list2 = this.getCourseOfferingIdsBySoc(socId, context);
        for (CourseOfferingInfo info : this.coService.getCourseOfferingsByIds(list2, context)) {
            // TODO: add the published course offering state to the constants 
//            if (info.getStateKey().equals(CourseOfferingServiceConstants.PUBLISHED_STATE_KEY) {
            list.add(info.getId());
//            }
        }
        return list;
    }

    @Override
    public List<String> getUnpublishedCourseOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        List<String> list2 = this.getCourseOfferingIdsBySoc(socId, context);
        for (CourseOfferingInfo info : this.coService.getCourseOfferingsByIds(list2, context)) {
            // TODO: add the published course offering state to the constants 
//            if (info.getStateKey().equals(CourseOfferingServiceConstants.PUBLISHED_STATE_KEY) {
            list.add(info.getId());
//            }
        }
        return list;
    }

    @Override
    public List<String> getUnpublishedActivityOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        List<String> list2 = this.getCourseOfferingIdsBySoc(socId, context);
        for (String coId : list2) {
            for (ActivityOfferingInfo ao : this.coService.getActivityOfferingsByCourseOffering(coId, context)) {
                // TODO: add the published course offering state to the constants 
//            if (!ao.getStateKey().equals(CourseOfferingServiceConstants.PUBLISHED_STATE_KEY) {
                list.add(ao.getId());
//            }
            }
        }
        return list;
    }

    @Override
    public List<String> getUnscheduledActivityOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        List<String> list2 = this.getCourseOfferingIdsBySoc(socId, context);
        for (String coId : list2) {
            for (ActivityOfferingInfo ao : this.coService.getActivityOfferingsByCourseOffering(coId, context)) {
                // TODO: add the published course offering state to the constants 
//            if (!ao.getStateKey().equals(CourseOfferingServiceConstants.SCHEDULED_STATE_KEY) {
                list.add(ao.getId());
//            }
            }
        }
        return list;
    }

    @Override
    public List<String> getCourseOfferingIdsWithUnscheduledFinalExamsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("scheduleSoc has not been implemented");
    }

    @Override
    public StatusInfo scheduleSoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("scheduleSoc has not been implemented");
    }

    @Override
    public SocInfo rolloverSoc(String sourceSocId, String targetTermId, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("reverseRollover has not been implemented");
    }

    @Override
    public SocRolloverResultInfo getSocRolloverResult(String rolloverResultId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.socRolloverResultMap.containsKey(rolloverResultId)) {
            throw new DoesNotExistException(rolloverResultId);
        }
        return this.socRolloverResultMap.get(rolloverResultId);
    }

    @Override
    public List<SocRolloverResultInfo> getSocRolloverResultsByIds(List<String> rolloverResultIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<SocRolloverResultInfo> list = new ArrayList<SocRolloverResultInfo>();
        for (String id : rolloverResultIds) {
            list.add(this.getSocRolloverResult(id, context));
        }
        return list;
    }

    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultId(String socRolloverResultId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<SocRolloverResultItemInfo> list = new ArrayList<SocRolloverResultItemInfo>();
        for (SocRolloverResultItemInfo info : socRolloverResultItemMap.values()) {
            if (socRolloverResultId.equals(info.getSocRolloverResultId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultIdAndSourceCourseOfferingId(String socRolloverResultId, String targetCourseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<SocRolloverResultItemInfo> list = new ArrayList<SocRolloverResultItemInfo>();
        for (SocRolloverResultItemInfo info : socRolloverResultItemMap.values()) {
            if (socRolloverResultId.equals(info.getSocRolloverResultId())) {
                if (targetCourseOfferingId.equals(info.getTargetCourseOfferingId())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultIdAndTargetCourseOfferingId(String socRolloverResultId, String targetCourseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<SocRolloverResultItemInfo> list = new ArrayList<SocRolloverResultItemInfo>();
        for (SocRolloverResultItemInfo info : socRolloverResultItemMap.values()) {
            if (socRolloverResultId.equals(info.getSocRolloverResultId())) {
                if (targetCourseOfferingId.equals(info.getTargetCourseOfferingId())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getSocRolloverResultIdsByTargetSoc(String targetSocId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (SocRolloverResultInfo info : socRolloverResultMap.values()) {
            if (targetSocId.equals(info.getTargetSocId())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getSocRolloverResultIdsBySourceSoc(String sourceSocId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (SocRolloverResultInfo info : socRolloverResultMap.values()) {
            if (sourceSocId.equals(info.getSourceSocId())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public SocRolloverResultInfo reverseRollover(String rolloverResultId, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("reverseRollover has not been implemented");
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, SocRolloverResultInfo> socRolloverResultMap = new LinkedHashMap<String, SocRolloverResultInfo>();

    @Override
    public SocRolloverResultInfo createSocRolloverResult(String socRolloverResultTypeKey, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        if (!socRolloverResultTypeKey.equals(socRolloverResultInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        SocRolloverResultInfo copy = new SocRolloverResultInfo(socRolloverResultInfo);
        if (copy.getId() == null) {
            copy.setId(socRolloverResultMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        socRolloverResultMap.put(copy.getId(), copy);
        return new SocRolloverResultInfo(copy);
    }

    @Override
    public SocRolloverResultInfo updateSocRolloverResult(String socRolloverResultId, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        if (!socRolloverResultId.equals(socRolloverResultInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        SocRolloverResultInfo copy = new SocRolloverResultInfo(socRolloverResultInfo);
        SocRolloverResultInfo old = this.getSocRolloverResult(socRolloverResultInfo.getId(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.socRolloverResultMap.put(socRolloverResultInfo.getId(), copy);
        return new SocRolloverResultInfo(copy);
    }

    @Override
    public SocRolloverResultInfo updateSocRolloverProgress(String socRolloverResultId, Integer itemsProcessed, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("updateSocRolloverProgress has not been implemented");
    }

    @Override
    public StatusInfo deleteSocRolloverResult(String socRolloverResultId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.socRolloverResultMap.remove(socRolloverResultId) == null) {
            throw new DoesNotExistException(socRolloverResultId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateSocRolloverResult(String validationType, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public SocRolloverResultItemInfo getSocRolloverResultItem(String socRolloverResultItemId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.socRolloverResultItemMap.containsKey(socRolloverResultItemId)) {
            throw new DoesNotExistException(socRolloverResultItemId);
        }
        return this.socRolloverResultItemMap.get(socRolloverResultItemId);
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, SocRolloverResultItemInfo> socRolloverResultItemMap = new LinkedHashMap<String, SocRolloverResultItemInfo>();

    @Override
    public SocRolloverResultItemInfo createSocRolloverResultItem(String socRolloverResultId, String socRolloverResultItemTypeKey, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        if (!socRolloverResultItemTypeKey.equals(socRolloverResultItemInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        SocRolloverResultItemInfo copy = new SocRolloverResultItemInfo(socRolloverResultItemInfo);
        if (copy.getId() == null) {
            copy.setId(socRolloverResultItemMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        socRolloverResultItemMap.put(copy.getId(), copy);
        return new SocRolloverResultItemInfo(copy);
    }

    @Override
    public Integer createSocRolloverResultItems(String socRolloverResultId, List<SocRolloverResultItemInfo> socRolloverResultItemInfos, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new OperationFailedException("createSocRolloverResultItems has not been implemented");
    }

    @Override
    public SocRolloverResultItemInfo updateSocRolloverResultItem(String socRolloverResultItemId, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        if (!socRolloverResultItemId.equals(socRolloverResultItemInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        SocRolloverResultItemInfo copy = new SocRolloverResultItemInfo(socRolloverResultItemInfo);
        SocRolloverResultItemInfo old = this.getSocRolloverResultItem(socRolloverResultItemInfo.getId(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.socRolloverResultItemMap.put(socRolloverResultItemInfo.getId(), copy);
        return new SocRolloverResultItemInfo(copy);
    }

    @Override
    public StatusInfo deleteSocRolloverResultItem(String socRolloverResultItemId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.socRolloverResultItemMap.remove(socRolloverResultItemId) == null) {
            throw new DoesNotExistException(socRolloverResultItemId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateSocRolloverResultItem(String validationType, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
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

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
