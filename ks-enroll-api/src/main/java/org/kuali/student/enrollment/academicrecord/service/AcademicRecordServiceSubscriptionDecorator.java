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
        implements AcademicRecordSubscrptionService {

    private static class Selector {

        public Selector(SubscriptionActionEnum action, AcademicRecordCallbackService callback) {
            this.action = action;
            this.callback = callback;
        }

        String studentCourseRecordId = null;
        String termId = null;
        String courseCode = null;
        String studentCourseRecordTypeKey = null;
        SubscriptionActionEnum action;
        AcademicRecordCallbackService callback;
    }
    private final transient Map<String, Selector> callbacks
            = new LinkedHashMap<String, Selector>();

    @Override
    public String subscribeToStudentCourseRecords(
            SubscriptionActionEnum action,
            AcademicRecordCallbackService academicRecordCallbackService,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String id = UUID.randomUUID().toString();
        Selector selector = new Selector(action, academicRecordCallbackService);
        callbacks.put(id, selector);
        return id;
    }

    @Override
    public String subscribeToStudentCourseRecordsByTerm(
            SubscriptionActionEnum action,
            String termId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String id = UUID.randomUUID().toString();
        Selector selector = new Selector(action, academicRecordCallbackService);
        selector.termId = termId;
        callbacks.put(id, selector);
        return id;
    }

    @Override
    public String subscribeToStudentCourseRecordsByCourse(
            SubscriptionActionEnum action,
            String courseCode,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String id = UUID.randomUUID().toString();
        Selector selector = new Selector(action, academicRecordCallbackService);
        selector.courseCode = courseCode;
        callbacks.put(id, selector);
        return id;
    }

    @Override
    public String subscribeToStudentCourseRecordsByType(
            SubscriptionActionEnum action,
            String studentCourseRecordTypeKey,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String id = UUID.randomUUID().toString();
        Selector selector = new Selector(action, academicRecordCallbackService);
        selector.studentCourseRecordTypeKey = studentCourseRecordTypeKey;
        callbacks.put(id, selector);
        return id;
    }

    @Override
    public String subscribeToStudentCourseRecord(
            SubscriptionActionEnum action,
            String studentCourseRecordId,
            AcademicRecordCallbackService academicRecordCallbackService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String id = UUID.randomUUID().toString();
        Selector selector = new Selector(action, academicRecordCallbackService);
        selector.studentCourseRecordId = studentCourseRecordId;
        callbacks.put(id, selector);
        return id;
    }

    @Override
    public StatusInfo removeSubscription(String subscriptionId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        this.callbacks.remove(subscriptionId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    protected void fireSelectedCallbacks(Selector target, String id, ContextInfo contextInfo) {
        // consider running this in a different thread so it does not block the main call
        for (Selector selector : this.callbacks.values()) {
            if (matches(selector, target)) {
                callCallback(selector.callback, target.action, id, contextInfo);
            }
        }
    }

    protected void callCallback(AcademicRecordCallbackService callback,
            SubscriptionActionEnum action,
            String id,
            ContextInfo contextInfo) {
        switch (action) {
            case CREATE:
                callback.createStudentCourseRecords(Arrays.asList(id), contextInfo);
                break;
            case UPDATE:
                callback.updateStudentCourseRecords(Arrays.asList(id), contextInfo);
                break;
            case DELETE:
                callback.deleteStudentCourseRecords(Arrays.asList(id), contextInfo);
                break;
        }
    }

    protected boolean matches(Selector selector, Selector target) {
        if (!matches(selector.action, target.action)) {
            return true;
        }
        if (selector.termId != null) {
            if (!selector.termId.equals(target.termId)) {
                return false;
            }
        }
        if (selector.courseCode != null) {
            if (!selector.courseCode.equals(target.courseCode)) {
                return false;
            }
        }
        if (selector.studentCourseRecordTypeKey != null) {
            if (!selector.studentCourseRecordTypeKey.equals(target.studentCourseRecordTypeKey)) {
                return false;
            }
        }
        if (selector.studentCourseRecordId != null) {
            if (!selector.studentCourseRecordId.equals(target.studentCourseRecordId)) {
                return false;
            }
        }
        return true;
    }

    protected boolean matches(SubscriptionActionEnum selector, SubscriptionActionEnum target) {
        if (selector.equals(target)) {
            return true;
        }
        if (selector.equals(SubscriptionActionEnum.ANY)) {
            return true;
        }
        return false;
    }

    // now override methods to actually call the callbacks
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
        String id = info.getId();
        Selector target = new Selector(SubscriptionActionEnum.CREATE, null);
        target.courseCode = info.getCourseCode();
        target.studentCourseRecordId = id;
        target.studentCourseRecordTypeKey = info.getTypeKey();
        target.termId = info.getTermId();
        this.fireSelectedCallbacks(target, id, contextInfo);
        return info;
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
        String id = info.getId();
        Selector target = new Selector(SubscriptionActionEnum.UPDATE, null);
        target.courseCode = info.getCourseCode();
        target.studentCourseRecordId = id;
        target.studentCourseRecordTypeKey = info.getTypeKey();
        target.termId = info.getTermId();
        this.fireSelectedCallbacks(target, id, contextInfo);
        return info;
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
        Selector target = new Selector(SubscriptionActionEnum.UPDATE, null);
        String id = info.getId();
        target.courseCode = info.getCourseCode();
        target.studentCourseRecordId = id;
        target.studentCourseRecordTypeKey = info.getTypeKey();
        target.termId = info.getTermId();
        this.fireSelectedCallbacks(target, id, contextInfo);
        return status;
    }

}
