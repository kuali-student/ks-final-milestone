/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.datadictionary;

import java.util.List;
import org.kuali.student.common.infc.ValidationResultInfc;

/**
 *
 * @author nwright
 */
public interface ValidatorInfc {

    public static enum ValidationType {
        FULL_VALIDATION, SKIP_REQUREDNESS_VALIDATIONS;
    }

    public List<ValidationResultInfc> validate (ValidationType validationType, Object info);
}
