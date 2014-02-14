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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class AcademicRecordClassStandingCalculationDecorator extends AcademicRecordServiceDecorator {

    GesService gesService;

    public GesService getGesService() {
        return gesService;
    }

    public void setGesService(GesService gesService) {
        this.gesService = gesService;
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
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
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

    /**
     * sort the thresholds in descending order
     * @param thresholds
     * @return
     */
    private List<ValueInfo> sortThresholds(List<ValueInfo> thresholds) {
        List<ValueInfo> sortedThresholds = new ArrayList<ValueInfo>();
        List<Integer> sortedValues = new ArrayList<Integer>();
        for(ValueInfo threshold : thresholds) {
            sortedValues.add(Integer.valueOf(threshold.getStringValue()));
        }
        Collections.sort(sortedValues);
        Collections.reverse(sortedValues);
        for(Integer sortedValue : sortedValues) {
            for(ValueInfo threshold : thresholds) {
                if(threshold.getStringValue().equals(sortedValue.toString())) {
                    sortedThresholds.add(threshold);
                }
            }
        }
        return sortedThresholds;
    }

    /*
     * calculate class standing based on credits earned
     */
    private ValueInfo calculateClassStanding(StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        int creditsEarned = Integer.valueOf(studentProgramRecord.getCreditsEarned());
        List<ValueInfo> thresholdNames = getClassStandingThresholdNames(contextInfo);
        List<ValueInfo> thresholds = new ArrayList<ValueInfo>();
        for(ValueInfo thresholdName : thresholdNames) {
            thresholds.add(getClassStandingThreshold(thresholdName.getStringValue(), contextInfo));
        }
        thresholds = sortThresholds(thresholds);

        for(ValueInfo threshold : thresholds) {
            Integer thresholdValue = Integer.valueOf(threshold.getStringValue());
            if(creditsEarned >= thresholdValue) {
//                String thresholdName = threshold.getParameterKey();
                return threshold;
            }
        }
        throw new OperationFailedException("Could not calculate class standing: '"
                + creditsEarned + "' for personId:" + studentProgramRecord.getPersonId());
    }
}
