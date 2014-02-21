/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.academicrecord.service.decorators;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class AcademicRecordClassStandingCalculationDecorator extends AcademicRecordServiceDecorator {

    private GesService gesService;

    public GesService getGesService() {
        return gesService;
    }

    public void setGesService(GesService gesService) {
        this.gesService = gesService;
    }

    class ValueInfoDecimalValueComparator implements Comparator<ValueInfo> {
        public int compare(ValueInfo valueInfo1, ValueInfo valueInfo2) {
            KualiDecimal decimal1 = valueInfo1.getDecimalValue();
            KualiDecimal decimal2 = valueInfo2.getDecimalValue();
            return decimal2.compareTo(decimal1); // for class standing we need descending order
        }
    }

    @Override
    public StudentProgramRecordInfo createStudentProgramRecord(String studentProgramRecordTypeKey,
                                                               String personId,
                                                               StudentProgramRecordInfo studentProgramRecord,
                                                               ContextInfo contextInfo) throws
            DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ValueInfo classStanding = calculateClassStanding(studentProgramRecord, contextInfo);
        // ValueInfo.parameterKey is the class standing long name
        // ValueInfo.stringValue is the numeric threshold
        studentProgramRecord.setClassStanding(classStanding.getParameterKey());
        return getNextDecorator().createStudentProgramRecord(studentProgramRecordTypeKey, personId,
                studentProgramRecord, contextInfo);
    }

    @Override
    public StudentProgramRecordInfo updateStudentProgramRecord(String studentProgramRecordId,
                                                               StudentProgramRecordInfo studentProgramRecord,
                                                               ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {

        ValueInfo classStanding = calculateClassStanding(studentProgramRecord, contextInfo);
        studentProgramRecord.setClassStanding(classStanding.getParameterKey());
        return getNextDecorator().updateStudentProgramRecord(studentProgramRecordId, studentProgramRecord, contextInfo);
    }

    /**
     * get the class standing threshold names from GesService
     */
    private List<ValueInfo> getClassStandingThresholdNames(ContextInfo contextInfo)
            throws  InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        List<ValueInfo> thresholds = gesService.getValuesByParameter(
                GesServiceConstants.PARAMETER_KEY_CLASS_STANDING_CREDIT_THRESHOLDS, contextInfo);

        if(thresholds == null || thresholds.size() < 1) {
            throw new OperationFailedException("AcademicRecordService could not calculate class standing " +
                    "because class standing threshold name was not found: '"
                    + GesServiceConstants.PARAMETER_KEY_CLASS_STANDING_CREDIT_THRESHOLDS + "'");
        }
        return thresholds;
    }

    private ValueInfo getClassStandingThreshold(String classStandingThresholdName, ContextInfo contextInfo)
            throws  InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        List<ValueInfo> values = gesService.getValuesByParameter(classStandingThresholdName, contextInfo);
        if(values == null || values.size() < 1) {
            throw new OperationFailedException("AcademicRecordService could not calculate class standing because class" +
                    " standing threshold value was not found for threshold: '"
                    + classStandingThresholdName + "'");
        }

        //should only be one value per threshold so return the first one; ignore the rest if any
        return values.get(0);
    }

    /*
     * calculate class standing based on credits earned
     */
    private ValueInfo calculateClassStanding(StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        int creditsEarned = studentProgramRecord.getCreditsEarned().intValue();
        List<ValueInfo> thresholdNames = getClassStandingThresholdNames(contextInfo);
        List<ValueInfo> thresholds = new ArrayList<ValueInfo>();
        for(ValueInfo thresholdName : thresholdNames) {
            thresholds.add(getClassStandingThreshold(thresholdName.getStringValue(), contextInfo));
        }
        Collections.sort(thresholds, new ValueInfoDecimalValueComparator());

        for(ValueInfo threshold : thresholds) {
            Integer thresholdValue = threshold.getDecimalValue().intValue();
            if(creditsEarned >= thresholdValue) {
                return threshold;
            }
        }
        throw new OperationFailedException("Could not calculate class standing: '"
                + creditsEarned + "' for personId:" + studentProgramRecord.getPersonId());
    }
}
