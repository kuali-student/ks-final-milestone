/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Charles on 8/15/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.FormatOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generates registration codes that roughly mimic the 4-digit format used at University of Maryland (UMD)
 * In particular, the first digit will represent a format value starting at 1, 2, up to 9, thus
 * allowing for up to 9 format offerings.  The next three digits will start at 001, 002, up to 999, and will
 * be the value that distinguishes between registration groups within a particular format offering.
 * Because most RG codes do not end in 00, this generator will skip them, thus 1000, 1100, 1200, etc. won't
 * be valid codes.
 * This is generating for unconstrained reg groups and by AOCs.
 * @author Charles
 */
public class FourDigitRegistrationGroupCodeGenerator implements RegistrationGroupCodeGenerator {
    String prefix = null;
    int regGroupSuffix = 1;
    boolean isValid = true;

    public FourDigitRegistrationGroupCodeGenerator() {
    }

    public FourDigitRegistrationGroupCodeGenerator(String prefix) {
        this.prefix = prefix;
    }

    private boolean _isValidRegGroupCode(String code) {
        if (code == null) {
            return false;
        }
        if (code.length() != 4) { // Allow 5 digits because some course can create
            // more than 100 RGs
            return false;
        }
        for (int i = 0; i < code.length(); i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private Set<Integer> _computePrefixUsed(CourseOfferingService coService, ContextInfo context,
                                           List<FormatOfferingInfo> foInfos) throws
            InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException,
            OperationFailedException {
        // foreach FO in a CO (associated with fo)
        //    get list of RGs
        //    pick first RG
        //    extract first two characters from RG code
        //    convert to an integer
        //    store in prefixUsed, a set of prefixes already seen
        Set<Integer> prefixUsed = new HashSet<Integer>();
        for (FormatOfferingInfo info: foInfos) {
            List<RegistrationGroupInfo> rgInfos = coService.getRegistrationGroupsByFormatOffering(info.getId(), context);
            if (rgInfos != null && !rgInfos.isEmpty()) {
                int firstRegistrationGroupInfo = 0;
                RegistrationGroupInfo rgInfo = rgInfos.get(firstRegistrationGroupInfo);
                // Assume 4 digit string
                String regGroupCode = rgInfo.getName();  // Name field stores reg group code
                if (!_isValidRegGroupCode(regGroupCode)) {
                    throw new RuntimeException("Invalid reg group code: " + regGroupCode);
                }
                String prefixStr = regGroupCode.substring(0, 1); // Get first digit (this is the format offering)
                int val = Integer.parseInt(prefixStr);
                prefixUsed.add(val);
            }
        }
        return prefixUsed;
    }

    private String _findSmallestUnusedPrefix(Set<Integer> prefixUsed) {
        String search =  null;
        for (int i = 1; i < 10; i++) {
            if (!prefixUsed.contains(i)) {
                search = "" + i; // Convert prefix from number to string
                break;
            }
        }
        return search;
    }

    @Override
    public void initializeGenerator(CourseOfferingService coService, FormatOffering fo, ContextInfo context,
                                    Map<String, Object> keyValues) {
        if (keyValues != null && keyValues.containsKey(CourseOfferingServiceBusinessLogicImpl.FIRST_REG_GROUP_CODE)) {
            // Should be an integer version of a code like "0101" (which would be 101)
            int val = Integer.parseInt(keyValues.get(CourseOfferingServiceBusinessLogicImpl.FIRST_REG_GROUP_CODE).toString());
            if (val < 1000 || val > 9999) {
                throw new RuntimeException("Val should be exactly 4 digits long");
            }
            // Reg group suffix is last 3 digits of 4 digit RG code
            regGroupSuffix = val % 1000;
            if (regGroupSuffix == 0) {
                throw new RuntimeException("Suffix is not allowed to start at 0");
            }
            prefix  = "" + (val / 1000); // Prefix is first digit of 4-digit number (should not be 0).
            if (prefix.length() > 1) {
                // This assumes 9 FO's max per CO.
                throw new RuntimeException("Prefix should be 1-9");
            }
            return; // Exit function
        }
        try {
            CourseOfferingInfo coInfo = coService.getCourseOffering(fo.getCourseOfferingId(), context);
            List<FormatOfferingInfo> foInfos = coService.getFormatOfferingsByCourseOffering(coInfo.getId(), context);
            int index = 0;
            // Remove the fo passed in
            for (FormatOfferingInfo info: foInfos) {
                if (info.getId().equals(fo.getId())) {
                    foInfos.remove(index);
                    break;
                }
                index++;
            }
            // Basic algorithm: Assume all RGs in a FO have the same two digit prefix.  Get a list of the
            // foreach FO in a CO (associated with fo)
            //    get list of RGs
            //    pick first RG
            //    extract first two characters from RG code
            //    convert to an integer
            //    store in prefixUsed, a set of prefixes already seen
            // then, loop from 1 to 99 to find the smallest unused prefix and initialize prefix with that value
            // throw exception if you reach 100
            Set<Integer> prefixUsed = _computePrefixUsed(coService, context, foInfos);
            // Now look for a free prefix to use
            prefix = _findSmallestUnusedPrefix(prefixUsed);
            if (prefix == null) {
                throw new RuntimeException("Unable to find a free prefix--all 999 are used");
            }
        } catch (Exception e) {
            isValid = false;
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param fo the format Offering
     * @param activities The list of Activities in the registration group
     * @param keyValues In this implementation, a prefix will be sent in.  The key is "umd.registration.code.prefix"
     *                  And the value is a String that should be one of {"1", "2", ..., "9"}
     *                  Also,
     * @return
     */
    @Override
    public String generateRegistrationGroupCode(FormatOffering fo, List<ActivityOfferingInfo> activities,
                                                Map<String, Object> keyValues) {
        // Run TestCourseOfferingServiceImplM4 if this changes
        if (regGroupSuffix >= 1000) {
            throw new RuntimeException("No more reg codes left to use");
        }
        String suffix = "" + regGroupSuffix;
        while (suffix.length() < 3) {
            // Pad with leading 0's to create 3-digit suffix
            suffix = "0" + suffix;
        }
        regGroupSuffix++; // Get ready for next reg code
        if (regGroupSuffix % 100 == 0) {
            regGroupSuffix++; // Avoid suffixes that end in 00 since it's uncommon in UMD reg group codes

        }
        String regCode = prefix + suffix;
        return regCode;
    }
}
