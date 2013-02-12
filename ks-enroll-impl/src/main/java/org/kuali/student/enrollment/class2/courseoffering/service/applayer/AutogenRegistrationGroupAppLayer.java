package org.kuali.student.enrollment.class2.courseoffering.service.applayer;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;

import java.util.Map;

/**
 * Used to support service calls related to Autogen RGs.
 * Terminology
 *    AO: activity offering
 *    FO: format offering
 *    AOC: activity offering cluster
 *    RG: registration group
 */
public interface AutogenRegistrationGroupAppLayer {
    /**
     * This is adding an AO to an FO for the first time, that is, when the FO contains no AOs underneath.
     * This method has a side effect.  It creates an ActivityOfferingCluster (AOC)
     * @param aoInfo The AO to be added
     * @param foId The FO it should be attached to
     * @return
     */
    Map<String, Object> addActivityOfferingFirstTime(ActivityOfferingInfo aoInfo, String foId)
            throws PermissionDeniedException, MissingParameterException,
            InvalidParameterException, OperationFailedException,
            DoesNotExistException, ReadOnlyException, DataValidationErrorException;
}
