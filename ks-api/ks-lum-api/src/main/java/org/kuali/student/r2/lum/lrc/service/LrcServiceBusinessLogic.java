/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.lum.lrc.service;

import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

/**
 *
 * @author nwright
 */
public interface LrcServiceBusinessLogic {

    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(String creditValue,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(String creditValueMin,
            String creditValueMax,
            String creditValueIncrement,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    public ResultValuesGroupInfo getCreateMultipleCreditResultValuesGroup(List<String> creditValues,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    public ResultValueInfo getCreateResultValueForScale(String resultValue,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}
