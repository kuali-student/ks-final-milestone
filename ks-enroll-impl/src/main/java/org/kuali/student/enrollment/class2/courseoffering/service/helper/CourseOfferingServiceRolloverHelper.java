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
 * Created by Charles on 10/15/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.helper;

import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.RegistrationGroupCodeUtil;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupCodeGeneratorFactory;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.poc.eventproc.handler.impl.helper.FoCoRgComputeStateUtil;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.permutation.PermutationCounter;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class consolidates changes related to rollover that can be shared from the service, etc.
 *
 * @author Kuali Student Team
 */
public abstract class CourseOfferingServiceRolloverHelper {

    public static final String FIRST_REG_GROUP_CODE = "firstRegGroupCode";

    public static Map<String, Object> getKeyValues(String firstRegGroupCode) {
        Map<String, Object> keyValues = null;
        if (firstRegGroupCode != null) {
            keyValues = new HashMap<String, Object>();
            keyValues.put(FIRST_REG_GROUP_CODE, firstRegGroupCode);
        }
        return keyValues;
    }

    public static Map<String, ActivityOfferingInfo> _makeAoIdToAoMap(List<ActivityOfferingInfo> aoInfos) {
        Map<String, ActivityOfferingInfo> aoIdToAoMap = new HashMap<String, ActivityOfferingInfo>();
        if (aoInfos == null || aoInfos.isEmpty()) {
            return null; // Can either return null or empty list to indicate there is no map
        }
        for (ActivityOfferingInfo ao: aoInfos) {
            aoIdToAoMap.put(ao.getId(), ao);
        }
        return aoIdToAoMap;
    }

    // Returns true if a cluster has one (or more) AO sets that is empty.
    public static boolean _hasEmptyAoSets(ActivityOfferingClusterInfo cluster) {
        for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
            if (set.getActivityOfferingIds().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static String _gRGFC_computeFirstRegGroupCode(List<RegistrationGroupInfo> regGroups, String prefix) {
        List<String> rgCodesUsed = new ArrayList<String>();
        if (regGroups.isEmpty()) {
            // If no RGs then multiply prefix by 1000 and add 1.  This creates codes like 1001, 2001, 3001, etc.
            // The prefix identifies the reg group
            return Integer.toString(Integer.parseInt(prefix) * 1000 + 1);
        }
        for (RegistrationGroupInfo rg: regGroups) {
            String regGroupCode = rg.getName(); // The name field stores
            String regGroupNum = regGroupCode;
            rgCodesUsed.add(regGroupNum);
        }
        return Integer.toString(Integer.parseInt(Collections.max(rgCodesUsed)) + 1);
    }

    public static boolean _isValidActivityOfferingPermutation(List<String> activityOfferingPermutation) {
        // Needs to be fixed?
        return true;
    }

    /**
     * Note: The Registration Group Code is what the administrators want to see the reg groups on a per course offering basis.
     * Registration codes, which are 5-digit values assigned to an RG and are unique to a term, is not yet implemented as of M5.
     * @param regGroupCode 4-digit value that uniquely identifies a reg group within a course offering
     * @param activityOfferingPermutation Contains a set of AO IDs that form a registration group
     * @param formatOffering The format offering which the reg group belongs to
     * @param activityOfferingClusterId The cluster id which the AO IDs were selected from
     * @return A reg group (to be perssisted via services)
     */
    public static RegistrationGroupInfo _gRGFC_makeRegGroup(String regGroupCode, List<String> activityOfferingPermutation,
                                                      FormatOfferingInfo formatOffering, String activityOfferingClusterId) {
        RegistrationGroupInfo rg = new RegistrationGroupInfo();

        List<String> aoIdsList = new ArrayList<String>(activityOfferingPermutation); // convert to list
        rg.setActivityOfferingIds(aoIdsList);
        rg.setCourseOfferingId(formatOffering.getCourseOfferingId());
        rg.setDescr(new RichTextInfo(regGroupCode, regGroupCode));
        rg.setFormatOfferingId(formatOffering.getId());
        rg.setActivityOfferingClusterId(activityOfferingClusterId);
        rg.setIsGenerated(true);
        rg.setName(regGroupCode);
        rg.setRegistrationCode(null);
        rg.setTermId(formatOffering.getTermId());
        rg.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY);
        rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
        return rg;
    }

    public static void changeClusterRegistrationGroupState( RegistrationGroupInfo regGroupInfo,
                                                             Map<String, ActivityOfferingInfo> aoIdToAoMap,
                                                             CourseOfferingService coService,
                                                             ContextInfo contextInfo )
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        if( LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY.equals( regGroupInfo.getStateKey() ) ) {
            return;
        }

        // build a list of all the AOs in this RG
        List<ActivityOfferingInfo> regGroupAos = new ArrayList<ActivityOfferingInfo>();
        for( String aoId : regGroupInfo.getActivityOfferingIds() ) {
            regGroupAos.add( getAoFromServiceIfNotFoundInMap(aoId, coService, aoIdToAoMap, contextInfo) );
        }

        // set the state of the RG, calculated from the state of it's AOs
        String regGroupStateKey = FoCoRgComputeStateUtil.computeRgState(regGroupAos);
        if(!regGroupInfo.getStateKey().equals(regGroupStateKey)) {
            coService.changeRegistrationGroupState(regGroupInfo.getId(), regGroupStateKey, contextInfo);
        }

    }

    protected static ActivityOfferingInfo getAoFromServiceIfNotFoundInMap( String targetAoId,
                                                         CourseOfferingService courseOfferingService,
                                                         Map<String, ActivityOfferingInfo> aoIdToAoMap,
                                                         ContextInfo contextInfo ) {


        ActivityOfferingInfo activityOfferingInfo = null;
        try {

            if( aoIdToAoMap != null && !aoIdToAoMap.isEmpty() ) {
                activityOfferingInfo = aoIdToAoMap.get( targetAoId );

                // something is wrong with the input data if the AO wasn't found in the map
                if( activityOfferingInfo == null ) {
                    throw new OperationFailedException("Missing AO in map");
                }
            }
            else { // if a map wasn't provided, we get the AO from the service
                activityOfferingInfo = courseOfferingService.getActivityOffering( targetAoId, contextInfo );
            }

        } catch( Exception e ) {
            throw new RuntimeException(e);
        }

        return activityOfferingInfo;
    }

    public static void sortActivityOfferingInfoByActivityCode(List<ActivityOfferingInfo> activityOfferingInfoList) {

        Collections.sort(activityOfferingInfoList, new Comparator<ActivityOfferingInfo>() {
            @Override
            public int compare(ActivityOfferingInfo o1, ActivityOfferingInfo o2) {
                if (o1.getActivityCode() != null && o2.getActivityCode() != null
                        && !o1.getActivityCode().equals("") && !o2.getActivityCode().equals("")){
                    return o1.getActivityCode().compareTo(o2.getActivityCode());
                } else {
                    return -1;
                }
            }
        });
    }

    public static void sortRegGroupAOInfosByActivityCode(ArrayList<List<ActivityOfferingInfo>> regGroupAOInfos) {

        Collections.sort(regGroupAOInfos, new Comparator<List<ActivityOfferingInfo>>() {
            @Override
            public int compare(List<ActivityOfferingInfo> o1, List<ActivityOfferingInfo> o2) {
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                for (ActivityOfferingInfo aoInfo1 : o1) { //build o1 code
                    sb1.append(aoInfo1.getActivityCode());
                }
                for (ActivityOfferingInfo aoInfo2 : o2) { //build o2 code
                    sb2.append(aoInfo2.getActivityCode());
                }
                if (!sb1.toString().equals("") && !sb2.toString().equals("")){
                    return sb1.toString().compareTo(sb2.toString());
                } else {
                    return -1;
                }
            }
        });
    }
}
