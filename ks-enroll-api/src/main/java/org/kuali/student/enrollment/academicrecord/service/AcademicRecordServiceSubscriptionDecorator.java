/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.academicrecord.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 *
 * @author nwright
 */
public class AcademicRecordServiceSubscriptionDecorator extends AcademicRecordServiceDecorator
        implements AcademicRecordSubscribeService {

    private transient Map<String, AcademicRecordCallbackService> subscribeToNewStudentCourseRecords
            = new LinkedHashMap<String, AcademicRecordCallbackService>();

    @Override
    public String subscribeToNewStudentCourseRecords(AcademicRecordCallbackService academicRecordCallbackService,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String id = UUID.randomUUID().toString();
        subscribeToNewStudentCourseRecords.put(id, academicRecordCallbackService);
        return id;
    }
    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToNewStudentCourseRecordsByTerm
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToNewStudentCourseRecordsByTerm(String termId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToNewStudentCourseRecordsByTerm.get(termId);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToNewStudentCourseRecordsByTerm.put(termId, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToNewStudentCourseRecordsByCourse
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToNewStudentCourseRecordsByCourse(String courseId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToNewStudentCourseRecordsByCourse.get(courseId);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToNewStudentCourseRecordsByCourse.put(courseId, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToNewStudentCourseRecordsByType
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToNewStudentCourseRecordsByType(String studentCourseRecordTypeKey,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToNewStudentCourseRecordsByType.get(studentCourseRecordTypeKey);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToNewStudentCourseRecordsByType.put(studentCourseRecordTypeKey, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToUpdateStudentCourseRecord
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToUpdateStudentCourseRecord(String studentCourseRecordId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToUpdateStudentCourseRecord.get(studentCourseRecordId);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToUpdateStudentCourseRecord.put(studentCourseRecordId, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private transient Map<String, AcademicRecordCallbackService> subscribeToUpdateStudentCourseRecords
            = new LinkedHashMap<String, AcademicRecordCallbackService>();

    @Override
    public String subscribeToUpdateStudentCourseRecords(AcademicRecordCallbackService academicRecordCallbackService,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String id = UUID.randomUUID().toString();
        subscribeToUpdateStudentCourseRecords.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToUpdateStudentCourseRecordsByTerm
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToUpdateStudentCourseRecordsByTerm(String termId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToUpdateStudentCourseRecordsByTerm.get(termId);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToUpdateStudentCourseRecordsByTerm.put(termId, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToUpdateStudentCourseRecordsByCourse
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToUpdateStudentCourseRecordsByCourse(String courseId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToUpdateStudentCourseRecordsByCourse.get(courseId);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToUpdateStudentCourseRecordsByCourse.put(courseId, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToUpdateStudentCourseRecordsByType
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToUpdateStudentCourseRecordsByType(String studentCourseRecordTypeKey,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToUpdateStudentCourseRecordsByType.get(
                studentCourseRecordTypeKey);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToUpdateStudentCourseRecordsByType.put(studentCourseRecordTypeKey, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToDeleteStudentCourseRecord
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToDeleteStudentCourseRecord(String studentCourseRecordId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToDeleteStudentCourseRecord.get(studentCourseRecordId);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToDeleteStudentCourseRecord.put(studentCourseRecordId, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private transient Map<String, AcademicRecordCallbackService> subscribeToDeleteStudentCourseRecords
            = new LinkedHashMap<String, AcademicRecordCallbackService>();

    @Override
    public String subscribeToDeleteStudentCourseRecords(AcademicRecordCallbackService academicRecordCallbackService,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String id = UUID.randomUUID().toString();
        subscribeToDeleteStudentCourseRecords.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToDeleteStudentCourseRecordsByTerm
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToDeleteStudentCourseRecordsByTerm(String termId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToDeleteStudentCourseRecordsByTerm.get(termId);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToDeleteStudentCourseRecordsByTerm.put(termId, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToDeleteStudentCourseRecordsByCourse
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToDeleteStudentCourseRecordsByCourse(String courseId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToDeleteStudentCourseRecordsByCourse.get(courseId);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToDeleteStudentCourseRecordsByCourse.put(courseId, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    private final Map<String, Map<String, AcademicRecordCallbackService>> subscribeToDeleteStudentCourseRecordsByType
            = new LinkedHashMap<String, Map<String, AcademicRecordCallbackService>>();

    @Override
    public String subscribeToDeleteStudentCourseRecordsByType(String courseOfferingTypeKey,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Map<String, AcademicRecordCallbackService> map = subscribeToDeleteStudentCourseRecordsByType.get(courseOfferingTypeKey);
        if (map == null) {
            map = new LinkedHashMap<String, AcademicRecordCallbackService>();
            subscribeToDeleteStudentCourseRecordsByType.put(courseOfferingTypeKey, map);
        }
        String id = UUID.randomUUID().toString();
        map.put(id, academicRecordCallbackService);
        return id;
    }

    @Override
    public StatusInfo removeSubscription(String subscriptionId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        this.subscribeToNewStudentCourseRecords.remove(subscriptionId);
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToNewStudentCourseRecordsByCourse.values()) {
            map.remove(subscriptionId);
        }
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToNewStudentCourseRecordsByTerm.values()) {
            map.remove(subscriptionId);
        }
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToNewStudentCourseRecordsByType.values()) {
            map.remove(subscriptionId);
        }
        this.subscribeToUpdateStudentCourseRecords.remove(subscriptionId);
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToUpdateStudentCourseRecord.values()) {
            map.remove(subscriptionId);
        }
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToUpdateStudentCourseRecordsByCourse.values()) {
            map.remove(subscriptionId);
        }
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToUpdateStudentCourseRecordsByTerm.values()) {
            map.remove(subscriptionId);
        }
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToUpdateStudentCourseRecordsByType.values()) {
            map.remove(subscriptionId);
        }
        this.subscribeToDeleteStudentCourseRecords.remove(subscriptionId);
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToDeleteStudentCourseRecord.values()) {
            map.remove(subscriptionId);
        }
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToDeleteStudentCourseRecordsByCourse.values()) {
            map.remove(subscriptionId);
        }
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToDeleteStudentCourseRecordsByTerm.values()) {
            map.remove(subscriptionId);
        }
        for (Map<String, AcademicRecordCallbackService> map : this.subscribeToDeleteStudentCourseRecordsByType.values()) {
            map.remove(subscriptionId);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    protected void callCreateCallback(AcademicRecordCallbackService callback, String id, ContextInfo contextInfo) {
        callback.newStudentCourseRecords(Arrays.asList(id), contextInfo);
    }

    @Override
    public StudentCourseRecordInfo createStudentCourseRecord(String studentCourseRecordTypeKey, String personId,
            StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        StudentCourseRecordInfo info = this.getNextDecorator().createStudentCourseRecord(studentCourseRecordTypeKey, personId,
                studentCourseRecord, contextInfo);
        String studentCourseRecordId = info.getId();

        for (AcademicRecordCallbackService callback : this.subscribeToNewStudentCourseRecords.values()) {
            callCreateCallback(callback, studentCourseRecordId, contextInfo);
        }
        Map<String, AcademicRecordCallbackService> map = null;
        // commented out because course id does not exist on the record!!!!
//        map = this.subscribeToUpdateStudentCourseRecordsByCourse.get(info.getCourseId());
//        if (map != null) {
//            for (AcademicRecordCallbackService callback : map.values()) {
//                callCreateCallback(callback, studentCourseRecordId, contextInfo);
//            }
//        }
        map = this.subscribeToNewStudentCourseRecordsByTerm.get(info.getTermId());
        if (map != null) {
            for (AcademicRecordCallbackService callback : map.values()) {
                callCreateCallback(callback, studentCourseRecordId, contextInfo);
            }
        }
        map = this.subscribeToNewStudentCourseRecordsByType.get(info.getTypeKey());
        if (map != null) {
            for (AcademicRecordCallbackService callback : map.values()) {
                callCreateCallback(callback, studentCourseRecordId, contextInfo);
            }
        }
        return info;
    }

    protected void callUpdateCallback(AcademicRecordCallbackService callback, String id, ContextInfo contextInfo) {
        callback.updateStudentCourseRecords(Arrays.asList(id), contextInfo);
    }

    @Override
    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId,
            StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        StudentCourseRecordInfo info = this.getNextDecorator().updateStudentCourseRecord(studentCourseRecordId,
                studentCourseRecord, contextInfo);

        for (AcademicRecordCallbackService callback : this.subscribeToUpdateStudentCourseRecords.values()) {
            callUpdateCallback(callback, studentCourseRecordId, contextInfo);
        }
        Map<String, AcademicRecordCallbackService> map = null;
        map = subscribeToUpdateStudentCourseRecord.get(studentCourseRecordId);
        if (map != null) {
            for (AcademicRecordCallbackService callback : map.values()) {
                callUpdateCallback(callback, studentCourseRecordId, contextInfo);
            }
        }
        // commented out because course id does not exist on the record!!!!
//        map = this.subscribeToUpdateStudentCourseRecordsByCourse.get(info.getCourseId());
//        if (map != null) {
//            for (AcademicRecordCallbackService callback : map.values()) {
//                callUpdateCallback(callback, studentCourseRecordId, contextInfo);
//            }
//        }
        map = this.subscribeToUpdateStudentCourseRecordsByTerm.get(info.getTermId());
        if (map != null) {
            for (AcademicRecordCallbackService callback : map.values()) {
                callUpdateCallback(callback, studentCourseRecordId, contextInfo);
            }
        }
        map = this.subscribeToUpdateStudentCourseRecordsByType.get(info.getTypeKey());
        if (map != null) {
            for (AcademicRecordCallbackService callback : map.values()) {
                callUpdateCallback(callback, studentCourseRecordId, contextInfo);
            }
        }
        return info;
    }

    protected void callDeleteCallback(AcademicRecordCallbackService callback, String id, ContextInfo contextInfo) {
        callback.deleteStudentCourseRecords(Arrays.asList(id), contextInfo);
    }

    @Override
    public StatusInfo deleteStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        StudentCourseRecordInfo info = this.getNextDecorator().getStudentCourseRecord(studentCourseRecordId, contextInfo);
        StatusInfo status = this.getNextDecorator().deleteStudentCourseRecord(studentCourseRecordId, contextInfo);

        for (AcademicRecordCallbackService callback : this.subscribeToDeleteStudentCourseRecords.values()) {
            callDeleteCallback(callback, studentCourseRecordId, contextInfo);
        }
        Map<String, AcademicRecordCallbackService> map = null;
        map = subscribeToDeleteStudentCourseRecord.get(studentCourseRecordId);
        if (map != null) {
            for (AcademicRecordCallbackService callback : map.values()) {
                callDeleteCallback(callback, studentCourseRecordId, contextInfo);
            }
        }
        // commented out because course id does not exist on the record!!!!
//        map = this.subscribeToDeleteStudentCourseRecordsByCourse.get(info.getCourseId());
//        if (map != null) {
//            for (AcademicRecordCallbackService callback : map.values()) {
//                callDeleteCallback(callback, studentCourseRecordId, contextInfo);
//            }
//        }
        map = this.subscribeToDeleteStudentCourseRecordsByTerm.get(info.getTermId());
        if (map != null) {
            for (AcademicRecordCallbackService callback : map.values()) {
                callDeleteCallback(callback, studentCourseRecordId, contextInfo);
            }
        }
        map = this.subscribeToDeleteStudentCourseRecordsByType.get(info.getTypeKey());
        if (map != null) {
            for (AcademicRecordCallbackService callback : map.values()) {
                callDeleteCallback(callback, studentCourseRecordId, contextInfo);
            }
        }
        return status;
    }

}
