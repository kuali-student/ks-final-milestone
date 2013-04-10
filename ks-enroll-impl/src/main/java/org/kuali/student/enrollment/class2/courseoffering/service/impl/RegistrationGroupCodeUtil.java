/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Charles on 4/10/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.AttributeInfo;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class RegistrationGroupCodeUtil {
    public static final String REG_CODE_PREFIX_DYN_ATTR = "regCodePrefix";
    public static int computeRegCodePrefixForFo(List<FormatOfferingInfo> existingFos, CourseOfferingService coService, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        List<FormatOfferingInfo> needToAssignPrefix = new ArrayList<FormatOfferingInfo>();
        // See if these FOs already have a prefix
        Set<Integer> usedPrefixes = new HashSet<Integer>();
        for (FormatOfferingInfo existingFo: existingFos) {
            List<AttributeInfo> attrs = existingFo.getAttributes();
            boolean found = false;
            for (AttributeInfo attr: attrs) {
                if (REG_CODE_PREFIX_DYN_ATTR.equals(attr.getKey())) {
                    String prefixStr = attr.getValue();
                    int prefix = Integer.parseInt(prefixStr);
                    usedPrefixes.add(prefix);
                    found = true;
                    break;
                }
            }
            if (!found) {
                // Try to find reg groups already generated
                List<RegistrationGroupInfo> rgInfos = coService.getRegistrationGroupsByFormatOffering(existingFo.getId(), context);
                if (rgInfos != null && !rgInfos.isEmpty()) {
                    RegistrationGroupInfo sample = rgInfos.get(0);
                    String rgCodePrefixStr = sample.getName().substring(0, 1);
                    int rgCodePrefix = Integer.parseInt(rgCodePrefixStr);
                    usedPrefixes.add(rgCodePrefix);
                    addRegCodePrefixAttributeToFo(rgCodePrefixStr, existingFo);
                    // Store this information
                    coService.updateFormatOffering(existingFo.getId(), existingFo, context);
                } else {
                    // Couldn't find a prefix, store to assing it later.
                    needToAssignPrefix.add(existingFo);
                }
            }
        }
        // Go through list of FOs and assign a prefix (from 1-9)
        for (FormatOfferingInfo unassigned: needToAssignPrefix) {
            for (int i = 1; i < 10; i++) {
                if (!usedPrefixes.contains(i)) {
                    addRegCodePrefixAttributeToFo("" + i, unassigned);
                    coService.updateFormatOffering(unassigned.getId(), unassigned, context);
                    usedPrefixes.add(i); // Now i is "used"
                }
            }
        }
        // Finally, return smallest unused number to use as a prefix
        for (int i = 1; i < 10; i++) {
            if (!usedPrefixes.contains(i)) {
                return i;
            }
        }
        // All prefixes used up, so throw exception
        throw new OperationFailedException("ERROR! Ran out of prefixes!");
    }

    /**
     * Store information in fo about prefix
     * @param rgCodePrefixStr One of {"1" , "2", ..., "9"}
     * @param fo the format offering to add this dynamic attribute to
     */
    public static void addRegCodePrefixAttributeToFo(String rgCodePrefixStr, FormatOfferingInfo fo) {
        AttributeInfo newAttr = new AttributeInfo();
        newAttr.setKey(REG_CODE_PREFIX_DYN_ATTR);
        newAttr.setValue(rgCodePrefixStr);
        if (fo.getAttributes() == null) {
            fo.setAttributes(new ArrayList<AttributeInfo>());
        }
        fo.getAttributes().add(newAttr);
    }

    public static int getRegCodePrefixFromFo(FormatOfferingInfo fetched) throws OperationFailedException {
        for (AttributeInfo attr: fetched.getAttributes()) {
            if (REG_CODE_PREFIX_DYN_ATTR.equals(attr.getKey())) {
                return Integer.parseInt(attr.getValue());
            }
        }
        throw new OperationFailedException("Unable to find dynamic attribute for: " + REG_CODE_PREFIX_DYN_ATTR);
    }
}
