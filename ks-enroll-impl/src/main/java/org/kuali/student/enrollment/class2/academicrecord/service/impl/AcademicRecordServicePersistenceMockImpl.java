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
 * Created by pauldanielrichardson on 8/15/14
 */
package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.decorators.AcademicRecordServiceDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is a temporary mock of the AcademicRecordService for use until full
 * persistence is ready.
 *
 * @author Kuali Student Team
 */
public class AcademicRecordServicePersistenceMockImpl extends AcademicRecordServiceDecorator implements MockService {

    private Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap;

    @Override
    public void clear() {
        studentToCourseRecordsMap.clear();
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException,
            MissingParameterException,
            OperationFailedException,
            InvalidParameterException,
            PermissionDeniedException {

        List<StudentCourseRecordInfo> studentCourseRecordsFiltered = new ArrayList<>();

        // Get course records from the map
        List<StudentCourseRecordInfo> studentCourseRecords = studentToCourseRecordsMap.get(personId);

        if (studentCourseRecords != null && !studentCourseRecords.isEmpty()) {
            for (StudentCourseRecordInfo studentCourseRecord : studentCourseRecords) {
                if (studentCourseRecord.getCourseOfferingId().equals(courseId)) {
                    studentCourseRecordsFiltered.add(studentCourseRecord);
                }
            }
        } else {
            throw new DoesNotExistException("No course records for student Id = " + personId);
        }

        return studentCourseRecordsFiltered;
    }

    public void setStudentToCourseRecordsMap(Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap) {
        this.studentToCourseRecordsMap = studentToCourseRecordsMap;
    }

}
