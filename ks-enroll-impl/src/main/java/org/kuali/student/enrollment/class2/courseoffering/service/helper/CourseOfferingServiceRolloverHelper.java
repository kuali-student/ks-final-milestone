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
public class CourseOfferingServiceRolloverHelper {
    public static final String FIRST_REG_GROUP_CODE = "firstRegGroupCode";

    public static List<BulkStatusInfo> generateRegGroupsForClusterHelper(String activityOfferingClusterId,
                                                                         ContextInfo contextInfo,
                                                                         CourseOfferingService coService,
                                                                         RegistrationGroupCodeGeneratorFactory registrationCodeGeneratorFactory)
            throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException,
            OperationFailedException, MissingParameterException, DoesNotExistException {
        return generateRegGroupsForClusterHelper(activityOfferingClusterId, contextInfo, coService, registrationCodeGeneratorFactory,
                false, null, null, null, null);
    }


    public static List<BulkStatusInfo> generateRegGroupsForClusterHelper(String activityOfferingClusterId,
                                                                         ContextInfo contextInfo,
                                                                         CourseOfferingService coService,
                                                                         RegistrationGroupCodeGeneratorFactory registrationCodeGeneratorFactory,
                                                                         boolean useCaching,
                                                                         List<RegistrationGroupInfo> regGroupCache,
                                                                         ActivityOfferingClusterInfo clusterCache,
                                                                         FormatOfferingInfo foForCluster,
                                                                         List<ActivityOfferingInfo> aosInClusterCache)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<BulkStatusInfo> rgChanges = new ArrayList<BulkStatusInfo>();
        ActivityOfferingClusterInfo cluster = clusterCache;
        if (clusterCache == null) {
            cluster = coService.getActivityOfferingCluster(activityOfferingClusterId, contextInfo);
        }
        // If any of the AO sets is empty, we'll bail out and not generate.  This is more the expected behavior.
        if (_hasEmptyAoSets(cluster)) {
            return new ArrayList<BulkStatusInfo>(); // KSENROLL-6193, KSENROLL-6181
        }

        List<RegistrationGroupInfo> existingRegistrationGroups = regGroupCache;
        if (!useCaching) {
            // Pass in an empty list if you have no existing reg groups
            existingRegistrationGroups = coService.getRegistrationGroupsByFormatOffering(cluster.getFormatOfferingId(), contextInfo);
        }
        int prefix = 1;
        if (useCaching) {
            // Assume if useCaching == true, then prefix exists for FO (due to rollover setting it)
            // Throws an exception if it can't be found
            prefix = RegistrationGroupCodeUtil.getRegCodePrefixFromFo(foForCluster);
        } else if (existingRegistrationGroups.isEmpty()) {
            // Default case for setting FOs to have a prefix used to generate reg codes
            // A bit tedious to fetch all the FOs
            String foId = cluster.getFormatOfferingId();
            FormatOfferingInfo clusterFo = coService.getFormatOffering(foId, contextInfo);
            CourseOfferingInfo co = coService.getCourseOffering(clusterFo.getCourseOfferingId(), contextInfo);
            List<FormatOfferingInfo> foInfos = coService.getFormatOfferingsByCourseOffering(co.getId(), contextInfo);

            try {
                RegistrationGroupCodeUtil.computeRegCodePrefixForFo(foInfos, coService, contextInfo);
                // Refetch the FO
                FormatOfferingInfo fetched = coService.getFormatOffering(foId, contextInfo);
                prefix = RegistrationGroupCodeUtil.getRegCodePrefixFromFo(fetched);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("ERROR in generating reg groups (ReadOnlyException) " + e.getMessage());
            } catch (VersionMismatchException e) {
                throw new OperationFailedException("ERROR in generating reg groups (VersionMismatchException) " + e.getMessage());
            }
        }
        Integer firstRegGroupCode = _gRGFC_computeFirstRegGroupCode(existingRegistrationGroups, prefix);

        // Calculate the set of "set of AO IDs" from which to generate reg groups.

        Set<List<String>> regGroupAoIds =
                PermutationCounter.computeMissingRegGroupAoIdsInCluster(cluster, existingRegistrationGroups);

        FormatOfferingInfo fo = foForCluster;
        if (!useCaching) {
            fo = coService.getFormatOffering(cluster.getFormatOfferingId(), contextInfo);
        }
        List<ActivityOfferingInfo> aoList = aosInClusterCache;
        if (!useCaching) {
            aoList = coService.getActivityOfferingsByCluster(activityOfferingClusterId, contextInfo);
        }
        // New instance created each time if desired
        RegistrationGroupCodeGenerator generator =
                registrationCodeGeneratorFactory.makeCodeGenerator();
        Map<String, Object> keyValues = null;
        if (firstRegGroupCode != null) {
            keyValues = new HashMap<String, Object>();
            keyValues.put(FIRST_REG_GROUP_CODE, firstRegGroupCode);
        }
        generator.initializeGenerator(coService, fo, contextInfo, keyValues);

        //Sort the lists in order to be in sequence with RGIDs when generating
        //Sort aoList

        Collections.sort(aoList, new Comparator<ActivityOfferingInfo>() {
            @Override
            public int compare(ActivityOfferingInfo o1, ActivityOfferingInfo o2) {
                if (o1.getActivityCode() != null && o2.getActivityCode() != null
                        && !o1.getActivityCode().equals("") && !o2.getActivityCode().equals("")) {
                    return o1.getActivityCode().compareTo(o2.getActivityCode());
                } else {
                    return -1;
                }
            }
        });

        //Sort AO IDs within the regGroupAoIds list's arrays
        ArrayList<List<ActivityOfferingInfo>> regGroupAoInfosSorted = new ArrayList<List<ActivityOfferingInfo>>();
        for (List<String> aoIDs : regGroupAoIds) {
            List<ActivityOfferingInfo> aoListSorted = new ArrayList<ActivityOfferingInfo>();
            //loop aoIDs and find the related AO that will store the activityCode to be sorted on
            for (String aoIDinaoIDs: aoIDs) {
                //find the matching ID and associate it to the AOInfo
                for (ActivityOfferingInfo aoInfo :  aoList) {
                    if (aoInfo.getId().equals(aoIDinaoIDs)) {
                        aoListSorted.add(aoInfo); //create a list of AOInfos to be sorted
                        break;
                    }
                }
            }

            //Sort aoListSorted based on activityCode
            Collections.sort(aoListSorted, new Comparator<ActivityOfferingInfo>() {
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

            regGroupAoInfosSorted.add(aoListSorted);
        }
        //Sort regGroupAoInfosSorted
        Collections.sort(regGroupAoInfosSorted, new Comparator <List<ActivityOfferingInfo>>() {
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

        // Loop through each set of AO Ids and create a reg group.
        for (List<ActivityOfferingInfo> aoInfoList : regGroupAoInfosSorted) {
            List<String> activityOfferingPermutation = new ArrayList<String>();
            for (ActivityOfferingInfo aoInfo : aoInfoList) {
                activityOfferingPermutation.add(aoInfo.getId());
            }

            if (!_isValidActivityOfferingPermutation(activityOfferingPermutation)) {
                continue;
            }
            String regGroupCode = generator.generateRegistrationGroupCode(fo, aoList, null);
            RegistrationGroupInfo rg = _gRGFC_makeRegGroup(regGroupCode, activityOfferingPermutation, fo, cluster.getId());

            try {
                RegistrationGroupInfo rgInfo = coService.createRegistrationGroup(cluster.getFormatOfferingId(), cluster.getId(), LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, rg, contextInfo);
                BulkStatusInfo status  = new BulkStatusInfo();
                status.setId(rgInfo.getId());
                status.setSuccess(Boolean.TRUE);
                status.setMessage("Created Registration Group");
                rgChanges.add(status);

                // Now determine if this registration group is in a valid state
                List<ValidationResultInfo> validations =
                        coService.verifyRegistrationGroup(rgInfo.getId(), contextInfo);

                for (ValidationResultInfo validation: validations) {
                    if (validation.isWarn()) {
                        // If any validation is an error, then make this invalid
                        coService.changeRegistrationGroupState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, contextInfo);
                        break;
                    }
                }
                Map<String, ActivityOfferingInfo> aoIdToAoMap = _makeAoIdToAoMap(aosInClusterCache);
                changeClusterRegistrationGroupState(rgInfo, aoIdToAoMap, coService, contextInfo);

            } catch (DataValidationErrorException e) {
                throw new OperationFailedException("Failed to validate registration group", e);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Failed to write registration group", e);
            }
        }

        return rgChanges;
    }

    private static Map<String, ActivityOfferingInfo> _makeAoIdToAoMap(List<ActivityOfferingInfo> aoInfos) {
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
    private static boolean _hasEmptyAoSets(ActivityOfferingClusterInfo cluster) {
        for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
            if (set.getActivityOfferingIds().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static Integer _gRGFC_computeFirstRegGroupCode(List<RegistrationGroupInfo> regGroups, int prefix) {
        List<Integer> rgCodesUsed = new ArrayList<Integer>();
        if (regGroups.isEmpty()) {
            // If no RGs then multiply prefix by 1000 and add 1.  This creates codes like 1001, 2001, 3001, etc.
            // The prefix identifies the reg group
            return prefix * 1000 + 1;
        }
        for (RegistrationGroupInfo rg: regGroups) {
            String regGroupCode = rg.getName(); // The name field stores
            Integer regGroupNum = Integer.parseInt(regGroupCode);
            rgCodesUsed.add(regGroupNum);
        }
        return Collections.max(rgCodesUsed) + 1;
    }

    protected static boolean _isValidActivityOfferingPermutation(List<String> activityOfferingPermutation) {
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
    private static RegistrationGroupInfo _gRGFC_makeRegGroup(String regGroupCode, List<String> activityOfferingPermutation,
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

    private static void changeClusterRegistrationGroupState( RegistrationGroupInfo regGroupInfo,
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

    private static ActivityOfferingInfo getAoFromServiceIfNotFoundInMap( String targetAoId,
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

}
