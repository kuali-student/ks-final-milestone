/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.datadictionary;

import java.util.List;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;

/**
 *
 * @author nwright
 */
public interface DataDictionaryValidatorInfc {

    public static enum ValidationType {
        FULL_VALIDATION, SKIP_REQUREDNESS_VALIDATIONS;

        public static ValidationType fromString (String str)
          throws InvalidParameterException {
            for (ValidationType vt : ValidationType.values()) {
                if (vt.name().equals(str)) {
                    return vt;
                }
            }
            throw new InvalidParameterException ("validationType");
        }
    }

    /**
     * Generic validation interface used to implement the individual validateXXXX (xxxxx) methods
     * 
     * This is not supposed to remotable because it's object is an Object.
     *
     * @param validationType, FULL_VALIDATION or SKIP_REQUIREDNESS_VALIDATIONS
     * @param info object to be validated
     * @param context of user and locale information
     * @return list of validation results
     */
    public List<ValidationResultInfo> validate (ValidationType validationType, Object info, ContextInfo context)
      throws OperationFailedException, MissingParameterException, InvalidParameterException, PermissionDeniedException;
}
