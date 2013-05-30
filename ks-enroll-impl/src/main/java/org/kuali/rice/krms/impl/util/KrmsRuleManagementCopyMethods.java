/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.util;

import java.util.List;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;

/**
 * These are the copy methods to be used to copy rules from one object to another.
 * 
 * For now this will just be a simple interface for a hunk of code that does the copying.
 * Later we will evaluate if we can/should push this into the KRMS rules service proper.
 * 
 * Note: This file refers to multiple methods but there is just one right now.
 * We will see as we move through the use cases if this single method is fine grained enough
 * to copy the rules we need to copy and just those rules.
 * 
 * @author nwright
 */
public interface KrmsRuleManagementCopyMethods {

    /**
     * Does a deep copy of all the bindings and rules associated with those 
     * bindings.
     * 
     * This is expected to be used in the following use cases:
     * (1) Copy rules from Canonical course to course offering
     * (2) Rollover rules from previous course offering to newly rolled over course offering
     * (3) Copy rules down from course offering to activity offering so they can be overridden at the activity offering level
     * 
     * @param fromReferenceDiscriminatorType the simple class name of the existing reference object
     * @param fromReferenceObjectId the id of the reference object
     * @param toReferenceDiscriminatorType the simple class name of the new reference object
     * @param toReferenceObjectId the id of the reference object
     * @param optionKeys configuration defined keys that control details of the copying
     * @return the list of newly created reference object bindings
     * @throws RiceIllegalArgumentException if any of the parameters are null or invalid
     * @throws RiceIllegalStateException if they copy run into a data condition that it cannot handle
     */
    public List<ReferenceObjectBinding> deepCopyReferenceObjectBindingsFromTo(String fromReferenceDiscriminatorType,
            String fromReferenceObjectId,
            String toReferenceDiscriminatorType,
            String toReferenceObjectId,
            List<String> optionKeys)
            throws RiceIllegalArgumentException, RiceIllegalStateException;
}
