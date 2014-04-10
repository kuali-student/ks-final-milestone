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
 * Created by Charles on 2/5/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.facade;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.class2.courseoffering.dao.ActivityOfferingClusterDaoApi;
import org.kuali.student.enrollment.class2.courseoffering.model.ActivityOfferingClusterEntity;
import org.kuali.student.enrollment.class2.courseoffering.service.extender.CourseOfferingServiceExtender;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.issue.ActivityOfferingNotInAocSubissue;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.issue.CourseOfferingAutogenIssue;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.issue.FormatOfferingAutogenIssue;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.issue.InvalidRegGroupSubissue;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.issue.RegGroupNotGeneratedByAocSubissue;
import org.kuali.student.enrollment.class2.coursewaitlist.service.facade.CourseWaitListServiceFacade;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingServiceFacade;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.permutation.PermutationCounter;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the Application Service Layer to provide the functionally specified functionality
 * using several service calls. (Used to be AutogenRegGroupServiceAdapterImpl)
 *
 *
 * @author Kuali Student Team
 */
public class CourseOfferingServiceFacadeImpl implements CourseOfferingServiceFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseOfferingServiceFacadeImpl.class);

    @Resource (name="CourseOfferingService")
    private CourseOfferingService coService;
    
    @Resource (name="SearchService")
    private SearchService searchService;

    private TypeService typeService;

    private CourseService courseService;
    
    private CourseWaitListService courseWaitListService;

    private ActivityOfferingClusterDaoApi activityOfferingClusterDao;

    private CourseWaitListServiceFacade waitListServiceFacade;

    private ExamOfferingServiceFacade examOfferingServiceFacade;

    private CourseOfferingServiceExtender courseOfferingServiceExtender;

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.adapter.CourseOfferingServiceFacade#getDefaultClusterName(int)
     */
    @Override
    public String getDefaultClusterName(int numberOfExistingClusters) {

        String clusterName = String.format("CL %d", (numberOfExistingClusters + 1));

        return clusterName;
    }

    @Override
    public String getDefaultClusterNamePerCO(String courseOfferingId, ContextInfo context) {
        List<String> foIds = new ArrayList<String>();
        int prefixNum = 1;
        String defaultClusterNameToCreate = String.format("CL %d", prefixNum);
        Set<String> clusterNames = new HashSet<String>();

        try {
            //retrieve all the FOs associated with the given CO
            List<FormatOfferingInfo> formatOfferingList = coService.getFormatOfferingsByCourseOffering(courseOfferingId, context);
            for(FormatOfferingInfo foInfo:formatOfferingList){
                foIds.add(foInfo.getId());
            }

            if (!foIds.isEmpty()) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.in("formatOfferingId", foIds.toArray()));
                QueryByCriteria criteria = qbcBuilder.build();

                //retrieve all the clusters associated with the given CO (by fetching clusters for all the FOs in the given CO)
                List<ActivityOfferingClusterInfo> aoClusterList = coService.searchForActivityOfferingClusters(criteria, context);

                for (ActivityOfferingClusterInfo aoCluster : aoClusterList) {
                    clusterNames.add(aoCluster.getPrivateName());
                }

                //assign the default cluster a name that hasn't been used within the given CO
                while (clusterNames.contains(defaultClusterNameToCreate)) {
                    prefixNum++;
                    defaultClusterNameToCreate = String.format("CL %d", prefixNum);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return defaultClusterNameToCreate;
    }

    public void setCourseOfferingService(CourseOfferingService coService) {
        this.coService = coService;
    }

    @Override
    public int getAoClusterCountByFoId(String foId, ContextInfo contextInfo) throws MissingParameterException,
            InvalidParameterException,
            OperationFailedException,
            PermissionDeniedException{

        int iRet = 0;

        SearchRequestInfo request = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AO_CLUSTER_COUNT_BY_FO_SEARCH_KEY);

        request.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.FO_ID, foId);

        SearchResultInfo searchResult = getSearchService().search(request, contextInfo);
        for (SearchResultRowInfo row : searchResult.getRows()) {
            for (SearchResultCellInfo cell : row.getCells()) {
                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CLUSTER_COUNT.equals(cell.getKey())) {
                    String aoClusterCount = cell.getValue();

                    if(aoClusterCount != null){
                        iRet = Integer.valueOf(aoClusterCount);
                    }
                }
            }
        }

        return iRet;

    }

    @Override
    public List<KeyValue> getAoIdAndAoTypeByFO(String foId, ContextInfo contextInfo) throws MissingParameterException,
            InvalidParameterException,
            OperationFailedException,
            PermissionDeniedException{

        List<KeyValue> lRet = new ArrayList<KeyValue>();

        SearchRequestInfo request = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AO_ID_AND_TYPE_BY_FO_SEARCH_KEY);

        request.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.FO_ID, foId);

        SearchResultInfo searchResult = getSearchService().search(request, contextInfo);
        for (SearchResultRowInfo row : searchResult.getRows()) {
            String aoId = null;
            String aoType = null;

            for (SearchResultCellInfo cell : row.getCells()) {

                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())) {
                    aoId = cell.getValue();
                }  else  if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_TYPE.equals(cell.getKey())) {
                    aoType = cell.getValue();
                }
            }

            if(aoId != null && aoType != null ){
                KeyValue kv = new ConcreteKeyValue(aoId, aoType);
                lRet.add(kv);
            }
        }

        return lRet;

    }

    @Override
    public ActivityOfferingClusterInfo createDefaultCluster(String foId, ContextInfo context)
            throws PermissionDeniedException,
            MissingParameterException,
            InvalidParameterException,
            OperationFailedException,
            DoesNotExistException,
            ReadOnlyException,
            DataValidationErrorException {

        int clusterCount = getAoClusterCountByFoId(foId, context);
        if (clusterCount != 0 ) {
            throw new OperationFailedException("Cluster already exists");
        }
        FormatOfferingInfo fo = coService.getFormatOffering(foId, context);
        String coId = fo.getCourseOfferingId();

        try {
            _addActivityOfferingTypesToFormatOffering(fo, context);
        } catch (VersionMismatchException e) {
            LOGGER.warn("VersionMismatchException thrown in createDefaultCluster, part 1");
            throw new OperationFailedException(e.getMessage());
        }

        List<KeyValue> aoKVList = getAoIdAndAoTypeByFO(foId, context);

        if (aoKVList != null && !aoKVList.isEmpty()) {
            LOGGER.warn("There are AOs without an AOC for this format ({}).  Indicates bad ref data.", foId);
        }
        // Now we're good...create the AOC
        ActivityOfferingClusterInfo clusterInfo = new ActivityOfferingClusterInfo();
        clusterInfo.setFormatOfferingId(foId);
        
        String defaultClusterName = getDefaultClusterNamePerCO(coId, context);
        
        clusterInfo.setPrivateName(defaultClusterName);
        clusterInfo.setName(defaultClusterName);
        clusterInfo.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        clusterInfo.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
        ActivityOfferingClusterInfo aoc =
                coService.createActivityOfferingCluster(foId, CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, clusterInfo, context);
        if (aoKVList != null && !aoKVList.isEmpty()) {
            List<ActivityOfferingSetInfo> sets = aoc.getActivityOfferingSets();
            // Put stray AOs into this cluster (considered a problem)
            LOGGER.warn("Adding stray AOs to this cluster--shouldn't happen");
            for (KeyValue aoIdType: aoKVList) {
                boolean added = false;
                String aoId = aoIdType.getKey();
                String aoType = aoIdType.getValue();
                // Iterate over each set to find a matching AO set to put the stray AOs into
                for (ActivityOfferingSetInfo set: sets) {
                    if (set.getActivityOfferingType().equals(aoType)) {
                        // Add AO ID to correct AO set
                        set.getActivityOfferingIds().add(aoId);
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    throw new OperationFailedException("Unable to find correct AO set for AO ID (" + aoId + ").  Bad data");
                }
            }
            try {
                coService.updateActivityOfferingCluster(foId, aoc.getId(), aoc, context);
            } catch (VersionMismatchException e) {
                LOGGER.warn("VersionMismatchException thrown in createDefaultCluster");
                throw new OperationFailedException(e.getMessage());
            }
        }
        return aoc;
    }

    private void _addActivityOfferingTypesToFormatOffering(FormatOfferingInfo fo, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        if (fo.getActivityOfferingTypeKeys() != null && !fo.getActivityOfferingTypeKeys().isEmpty()) {
            // Only bother with this if there are no AO type keys
            return;
        }

        // Get the activity types
        List<String> activityTypes = courseOfferingServiceExtender.getActivityTypesForFormatId(fo.getFormatId(), context);
        if (activityTypes.isEmpty()) {
            throw new OperationFailedException("No format could be found to match id: " + fo.getFormatId() + " or Formats contains no activities.  Error!");
        }

        // Use type service to find corresponding AO types--assumes 1-1 mapping of Activity types to AO types
        TypeService typeService = getTypeService();
        List<String> aoTypeKeys = new ArrayList<String>();
        for (String activityType: activityTypes) {
            List<TypeTypeRelationInfo> typeTypeRels =
                    typeService.getTypeTypeRelationsByOwnerAndType(activityType,
                            TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY,
                            context);
            if (typeTypeRels.size() != 1) {
                // Ref data currently only has a 1-1 mapping between Activity types (CLU) and AO types (LUI)
                // The UI screens only support this.  Should there be a many-to-1 relation between AO types and Activity
                // types (as they were originally envisioned), then this exception will be thrown.
                throw new UnsupportedOperationException("Can't handle Activity Type -> AO Type that isn't 1-1.  Search for this message in Java code");
            } else {
                String aoType = KSCollectionUtils.getRequiredZeroElement(typeTypeRels).getRelatedTypeKey();
                aoTypeKeys.add(aoType);
            }
        }
        // Finally, set the ao types for this fo
        fo.setActivityOfferingTypeKeys(aoTypeKeys);

        getCoService().updateFormatOffering(fo.getId(), fo, context);
    }

    /**
     * User Story 2: I need the system to automatically create reg group(s) when I add a CO via Copy to eliminate
     * the need to manually create them
     * Note: this only handles copying an existing CO to a term, not creating from canonical which will
     *       be a separate method (not written as of now).
     * Note: not yet unit tested
     * Note: rollover will handle generation of RGs (not yet implemented)
     */
    @Override
    public CourseOfferingInfo copyCourseOfferingToTargetTerm(CourseOfferingInfo coInfo, TermInfo targetTerm, List<String> optionKeys, ContextInfo context)
            throws InvalidParameterException, PermissionDeniedException, DataValidationErrorException,
                   AlreadyExistsException, ReadOnlyException, OperationFailedException, MissingParameterException,
                   DoesNotExistException {
        // Impl based on CourseOfferingManagementController::copyCourseOfferingCreateCopy
        if (optionKeys == null) {
            optionKeys = new ArrayList<String>();
        }

        optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);

        // Copy CO by using rollover.  Note that rollover will take care of generating RGs
        SocRolloverResultItemInfo item = coService.rolloverCourseOffering(
                coInfo.getId(),
                targetTerm.getId(),
                optionKeys,
                context);
        CourseOfferingInfo targetCo = coService.getCourseOffering(item.getTargetCourseOfferingId(), context);
        return targetCo;
    }

    @Override
    public ActivityOfferingResult createActivityOffering(ActivityOfferingInfo aoInfo, String aocId, ContextInfo context)
            throws PermissionDeniedException, DataValidationErrorException,
            InvalidParameterException, ReadOnlyException, OperationFailedException,
            MissingParameterException, DoesNotExistException, VersionMismatchException {
        // Fetch cluster first, so if it fails, we don't continue on
        ActivityOfferingClusterInfo cluster =
                coService.getActivityOfferingCluster(aocId, context);
        // Make sure FO IDs match up
        if (!cluster.getFormatOfferingId().equals(aoInfo.getFormatOfferingId())) {
            throw new DataValidationErrorException("Format Offering Ids do not match");
        }
        ActivityOfferingInfo created = coService.createActivityOffering(aoInfo.getFormatOfferingId(), aoInfo.getActivityId(),
                aoInfo.getTypeKey(), aoInfo, context);

        ActivityOfferingResult aoResult = _addActivityOfferingToClusterCommon(created, cluster, context);

        //generate final exam offerings if the exam period exists
        String examPeriodID = null;
        try {
            examPeriodID = this.getExamOfferingServiceFacade().getExamPeriodId(aoInfo.getTermId(), context);
        } catch (DoesNotExistException e) {
            LOGGER.warn("The Term {} doesn't have an exam period to create exam offerings.", aoInfo.getTermId());
        }
        if (examPeriodID != null) {
            //create if Final Exam Driver has been selected for the FO
            TypeInfo typeAO = getTypeService().getType(created.getTypeKey(), context);
            FormatOfferingInfo fo = getCoService().getFormatOffering(aoInfo.getFormatOfferingId(), context);
            if (fo.getFinalExamLevelTypeKey() != null) {
                TypeInfo typeFEO = getTypeService().getType(fo.getFinalExamLevelTypeKey(), context);
                if (typeAO.getName().equals(typeFEO.getName())){
                    this.getExamOfferingServiceFacade().generateFinalExamOfferingForAO(created, aoInfo.getTermId(), examPeriodID, new ArrayList<String>(), context);
                } else {
                    aoResult.getExamOfferingsGenerated().setSuccess(Boolean.FALSE);
                }
            } else {
                aoResult.getExamOfferingsGenerated().setSuccess(Boolean.FALSE);
            }
        } else{
            aoResult.getExamPeriodStatus().setSuccess(Boolean.FALSE);
        }


        //create and persist a WaitlistInfo for AO
        CourseWaitListInfo theWaitListInfo = getWaitListServiceFacade().createDefaultCourseWaitlist(created, context);

        aoResult.setWaitListInfo(theWaitListInfo);

        return aoResult;

    }

    /**
     * Used by both createActivityOffering and copyActivityOfferingToCluster
     * @param aoInfo AO info just created/copied
     * @param cluster The cluster to place it in
     * @param context context of service call
     * @return A result with RGs generated
     */
    private ActivityOfferingResult _addActivityOfferingToClusterCommon(ActivityOfferingInfo aoInfo, ActivityOfferingClusterInfo cluster, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        // Now add the AO ID to the correct AOC set
        boolean checkType = false;
        for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
            if (set.getActivityOfferingType().equals(aoInfo.getTypeKey())) {
                set.getActivityOfferingIds().add(aoInfo.getId()); // Found set, add the ID
                checkType = true;
                break;
            }
        }
        // if missing set of certain type in cluster -> have to create it
        if (!checkType) {
            ActivityOfferingSetInfo set = new ActivityOfferingSetInfo();
            set.setActivityOfferingType(aoInfo.getTypeKey());
            set.getActivityOfferingIds().add(aoInfo.getId());
            cluster.getActivityOfferingSets().add(set);
        }

        // Update the AOC
        ActivityOfferingClusterInfo updated =
                coService.updateActivityOfferingCluster(cluster.getFormatOfferingId(), cluster.getId(), cluster, context);
        // Note: this may generate RGs that do NOT include the AO just added
        ActivityOfferingResult aoResult = new ActivityOfferingResult();
        aoResult.setCreatedActivityOffering(aoInfo);
        if (_clusterCanGenerateRgs(updated)) {
            List<BulkStatusInfo> status =
                    coService.generateRegistrationGroupsForCluster(updated.getId(), context);
            aoResult.setGeneratedRegistrationGroups(status);
        } else {
            aoResult.setGeneratedRegistrationGroups(new ArrayList<BulkStatusInfo>());
            aoResult.getClusterstatus().setSuccess(Boolean.FALSE);
            aoResult.getClusterstatus().setMessage("Error: empty AO sets exist--can't generate reg groups");
        }
        return aoResult;
    }

    private boolean _clusterCanGenerateRgs(ActivityOfferingClusterInfo updated) {
        for (ActivityOfferingSetInfo set: updated.getActivityOfferingSets()) {
            if (set.getActivityOfferingIds().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ActivityOfferingResult copyActivityOfferingToCluster(String origAoId, String aocId, ContextInfo context)
            throws PermissionDeniedException, DataValidationErrorException,
            InvalidParameterException, ReadOnlyException, OperationFailedException,
            MissingParameterException, DoesNotExistException, VersionMismatchException {
        // Fetch cluster first, so if it fails, we don't continue on
        ActivityOfferingClusterInfo cluster =
                coService.getActivityOfferingCluster(aocId, context);
        // Make a new copy of AO
        ActivityOfferingInfo copyAoInfo = coService.copyActivityOffering(origAoId, context);
        // Make a new copy of the associated WL
        copyToCreateWL(copyAoInfo, origAoId, context);
        // Make sure FO IDs match up
        if (!cluster.getFormatOfferingId().equals(copyAoInfo.getFormatOfferingId())) {
            throw new DataValidationErrorException("Format Offering Ids do not match");
        }

        ActivityOfferingResult aoResult = _addActivityOfferingToClusterCommon(copyAoInfo, cluster, context);

        //generate final exam offerings if the exam period exists
        String examPeriodID = null;
        try {
            examPeriodID = this.getExamOfferingServiceFacade().getExamPeriodId(copyAoInfo.getTermId(), context);
        } catch (DoesNotExistException e) {
            LOGGER.warn("The Term {} doesn't have an exam period to create exam offerings.", copyAoInfo.getTermId());
        }
        if (examPeriodID != null) {
            //create if Final Exam Driver has been selected for the FO
            TypeInfo typeAO = getTypeService().getType(copyAoInfo.getTypeKey(), context);
            FormatOfferingInfo fo = getCoService().getFormatOffering(copyAoInfo.getFormatOfferingId(), context);
            if (fo.getFinalExamLevelTypeKey() != null) {
                TypeInfo typeFEO = getTypeService().getType(fo.getFinalExamLevelTypeKey(), context);
                if (typeAO.getName().equals(typeFEO.getName())){
                    this.getExamOfferingServiceFacade().generateFinalExamOfferingForAO(copyAoInfo, copyAoInfo.getTermId(), examPeriodID, new ArrayList<String>(), context);
                    aoResult.getExamOfferingsGenerated().setSuccess(Boolean.TRUE);
                } else {
                    aoResult.getExamOfferingsGenerated().setSuccess(Boolean.FALSE);
                }
            } else {
                aoResult.getExamOfferingsGenerated().setSuccess(Boolean.FALSE);
            }
        } else{
            aoResult.getExamPeriodStatus().setSuccess(Boolean.FALSE);
        }

        return aoResult;
    }

    private CourseWaitListInfo copyToCreateWL(ActivityOfferingInfo newAOInfo, String origAoId, ContextInfo context)
                        throws InvalidParameterException, MissingParameterException, OperationFailedException,
                               PermissionDeniedException, DoesNotExistException, DataValidationErrorException,
                               ReadOnlyException{

        List<CourseWaitListInfo> waitListInfos = getCourseWaitListService().getCourseWaitListsByActivityOffering(origAoId, context);
        CourseWaitListInfo origWaitListInfo, newWaitListInfo;        
        if (!waitListInfos.isEmpty()){

            // waitListInfos can return more than two values
            origWaitListInfo = KSCollectionUtils.getRequiredZeroElement(waitListInfos);
            newWaitListInfo = new CourseWaitListInfo(origWaitListInfo);
            if(origWaitListInfo.getActivityOfferingIds().size()>1){
                origWaitListInfo.getActivityOfferingIds().add(newAOInfo.getId());
                try {
                    origWaitListInfo=getCourseWaitListService().updateCourseWaitList(origWaitListInfo.getId(), origWaitListInfo, context);
                } catch (VersionMismatchException e) {
                    throw new OperationFailedException(e);
                }
                return origWaitListInfo;

            } else {
                newWaitListInfo.setId(null);
                newWaitListInfo.setActivityOfferingIds(new ArrayList<String>());
                newWaitListInfo.setFormatOfferingIds(new ArrayList<String>());
                newWaitListInfo.getActivityOfferingIds().add(newAOInfo.getId());
                newWaitListInfo.getFormatOfferingIds().add(newAOInfo.getFormatOfferingId());
                newWaitListInfo = getCourseWaitListService().createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                        newWaitListInfo, context);
            }

        }
        else{
            // Assume that every AO should have an associated WL. if not, treat it as a reference data problem.
            // We will just create a new WL
            newWaitListInfo = getWaitListServiceFacade().createDefaultCourseWaitlist(newAOInfo, context);
        }
        return newWaitListInfo;

    }

    public CourseWaitListInfo createUncolocatedWaitList(CourseWaitListInfo courseWaitListInfo, String waitlistType, boolean hasWaitlist, boolean limitWaitlistSize, String aoId, String foId, ContextInfo context) {

        setAutoProcConfReq(courseWaitListInfo, waitlistType, hasWaitlist, limitWaitlistSize);

        try {
            if(courseWaitListInfo.getActivityOfferingIds().size() == 1) {   // only current AO - meaning no sharing of WL
                courseWaitListInfo = getCourseWaitListService().updateCourseWaitList(courseWaitListInfo.getId(), courseWaitListInfo, context);
            } else {
                // remove un-colocated AO from shared WL, do NOT want to save new parameters
                CourseWaitListInfo courseWaitListInfoShared = getCourseWaitListService().getCourseWaitList(courseWaitListInfo.getId(), context);
                courseWaitListInfoShared.getActivityOfferingIds().remove(aoId);
                courseWaitListInfoShared.getFormatOfferingIds().remove(foId);
                getCourseWaitListService().updateCourseWaitList(courseWaitListInfo.getId(), courseWaitListInfoShared, context);

                // create new WL for un-colo AO
                courseWaitListInfo.setId(null);
                courseWaitListInfo.setActivityOfferingIds(new ArrayList<String>());
                courseWaitListInfo.setFormatOfferingIds(new ArrayList<String>());
                courseWaitListInfo.getActivityOfferingIds().add(aoId);
                courseWaitListInfo.getFormatOfferingIds().add(foId);
                courseWaitListInfo = getCourseWaitListService().createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                        courseWaitListInfo, context);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return courseWaitListInfo;
    }

    public CourseWaitListInfo createColocatedWaitList(CourseWaitListInfo courseWaitListInfo, String waitlistType, boolean hasWaitlist, boolean limitWaitlistSize, boolean isColocatedAO, boolean isMaxEnrollmentShared,
                                        HashMap<String, String> aoIdfoIdMap, ContextInfo context) {

        setAutoProcConfReq(courseWaitListInfo, waitlistType, hasWaitlist, limitWaitlistSize);

        try {
            // whether co-locating or already existing colos
            if (!aoIdfoIdMap.isEmpty() && isColocatedAO) {
                CourseWaitListInfo courseWaitListInfoShared = new CourseWaitListInfo();
                if (!isMaxEnrollmentShared){ // want to keep old params for the new WLs for the rest of AOs
                    courseWaitListInfoShared = getCourseWaitListService().getCourseWaitList(courseWaitListInfo.getId(), context);
                }

                for (Map.Entry<String, String> entry : aoIdfoIdMap.entrySet()){
                    String aoId = entry.getKey();
                    String foId = entry.getValue();
                    if (isMaxEnrollmentShared){   // create shared WL
                        // Put WL logic in. Shared WL ONLY when max enrollement is shared. Adding new aoID and foID(s)
                        if (!courseWaitListInfo.getActivityOfferingIds().contains(aoId)) {
                            courseWaitListInfo.getActivityOfferingIds().add(aoId);
                            // Delete WL for other AO
                            List<CourseWaitListInfo> courseWaitLists  = getCourseWaitListService().getCourseWaitListsByActivityOffering(aoId, context);
                            for (CourseWaitListInfo courseWaitlist : courseWaitLists) {
                                if (!StringUtils.equals(courseWaitlist.getId(), courseWaitListInfo.getId())) {
                                    getCourseWaitListService().deleteCourseWaitList(courseWaitlist.getId(), context);
                                }
                            }
                        }
                        if (!courseWaitListInfo.getFormatOfferingIds().contains(foId)) {
                            courseWaitListInfo.getFormatOfferingIds().add(foId);
                        }
                    } else {  // each AO keeps (or have to split shared) own WL
                        if (courseWaitListInfo.getActivityOfferingIds().contains(aoId)) {
                            courseWaitListInfo.getActivityOfferingIds().remove(aoId);
                            // creating new (non-shared) WL per AO
                            CourseWaitListInfo courseWaitListInfoNew = new CourseWaitListInfo(courseWaitListInfoShared);
                            courseWaitListInfoNew.setId(null);
                            courseWaitListInfoNew.setActivityOfferingIds(new ArrayList<String>());
                            courseWaitListInfoNew.setFormatOfferingIds(new ArrayList<String>());
                            courseWaitListInfoNew.getActivityOfferingIds().add(aoId);
                            courseWaitListInfoNew.getFormatOfferingIds().add(foId);
                            getCourseWaitListService().createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                                    courseWaitListInfoNew, context);
                        }
                        if (courseWaitListInfo.getFormatOfferingIds().contains(foId)) {
                            courseWaitListInfo.getFormatOfferingIds().remove(foId);
                        }
                    }
                }
            }

            courseWaitListInfo = getCourseWaitListService().updateCourseWaitList(courseWaitListInfo.getId(), courseWaitListInfo, context);
        } catch (Exception e) {
                throw new RuntimeException(e);
        }

        return courseWaitListInfo;
    }

    private void setAutoProcConfReq(CourseWaitListInfo courseWaitListInfo, String waitlistType, boolean hasWaitlist, boolean limitWaitlistSize){

        if(waitlistType != null) {
            if(waitlistType.equals(LuiServiceConstants.AUTOMATIC_WAITLIST_TYPE_KEY)) {
                courseWaitListInfo.setAutomaticallyProcessed(true);
                courseWaitListInfo.setConfirmationRequired(false);
            } else if(waitlistType.equals(LuiServiceConstants.CONFIRMATION_WAITLIST_TYPE_KEY)){
                courseWaitListInfo.setAutomaticallyProcessed(true);
                courseWaitListInfo.setConfirmationRequired(true);
            } else if(waitlistType.equals(LuiServiceConstants.MANUAL_WAITLIST_TYPE_KEY)) {
                courseWaitListInfo.setAutomaticallyProcessed(false);
                courseWaitListInfo.setConfirmationRequired(false);
            }
        }

        if(hasWaitlist) {
            courseWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_ACTIVE_STATE_KEY);
        } else {
            courseWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY);
        }

        if(!limitWaitlistSize) {
            courseWaitListInfo.setMaxSize(null);
        }
    }

    public  ActivityOfferingResult updateRegistrationGroups(ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException, VersionMismatchException {
        try{
            ActivityOfferingResult aoResult = new ActivityOfferingResult();
            aoResult.setCreatedActivityOffering(activityOfferingInfo);

            // fetch the associated RGs
            String aoId = activityOfferingInfo.getId();
            List<RegistrationGroupInfo> rgs = coService.getRegistrationGroupsByActivityOffering(aoId, context);

            if (rgs != null && !rgs.isEmpty()) {
                //fetch the associated AOC
                ActivityOfferingClusterInfo cluster = getActivityOfferingClusterDao().getByActivityOffering(activityOfferingInfo.getId()).toDto();


                if (cluster != null) {
                    // Make sure FO IDs match up
                    if (!cluster.getFormatOfferingId().equals(activityOfferingInfo.getFormatOfferingId())) {
                        throw new DataValidationErrorException("Format Offering Ids do not match");
                    }

                    //check the max enroll number of the AOC
                    BulkStatusInfo clusterStatusInfo = new BulkStatusInfo();
                    clusterStatusInfo.setId(cluster.getId());
                    clusterStatusInfo.setSuccess(true);

                    List<ValidationResultInfo> validations = coService.validateActivityOfferingCluster(
                            DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), activityOfferingInfo.getFormatOfferingId(), cluster, context);
                    for (ValidationResultInfo validation : validations) {
                        if (validation.isWarn()) {
                            // If any validation is an error, then make this invalid
                            clusterStatusInfo.setSuccess(false);
                            break;
                        }
                    }
                    aoResult.setClusterstatus(clusterStatusInfo);

                }

                //check time conflict
                List<BulkStatusInfo> rgStatusInfos = new ArrayList<BulkStatusInfo>(rgs.size());

                for (RegistrationGroupInfo rgInfo : rgs) {
                    BulkStatusInfo rgStatusInfo = new BulkStatusInfo();
                    rgStatusInfo.setId(rgInfo.getId());
                    rgStatusInfo.setSuccess(true);

                    //Canceled state handling is out of scope. We ignore the state for now
                    // TODO: KSENROLL-9934 this checking will be removed when canceled state handling is available.
                    if (!rgInfo.getStateKey().equals(LuiServiceConstants.REGISTRATION_GROUP_CANCELED_STATE_KEY)) {
                        if (_areRegistrationGroupAoIdsValid(rgInfo.getActivityOfferingIds(), context)) {
                            // We don't know what the next state should be for this registration group, but the state
                            // service kinda knows. So, try to send it to the highest state, offered.
                            // If that doesn't work, try to send it to pending
                            // if that doesn't work, the state change has failed and log. (suspended aos should not get moved.
                            if (!(coService.changeRegistrationGroupState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY, context).getIsSuccess()
                                    || coService.changeRegistrationGroupState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY, context).getIsSuccess())) {
                                LOGGER.warn("State change failed for RG: {} From state: {}", rgInfo.getId(), rgInfo.getStateKey());
                            }
                        } else {
                            coService.changeRegistrationGroupState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, context);
                            rgStatusInfo.setSuccess(false);
                        }
                    }

                    rgStatusInfos.add(rgStatusInfo);
                }
                aoResult.setGeneratedRegistrationGroups(rgStatusInfos);

            }
            return aoResult;

        } catch (Exception e) {
        throw new RuntimeException(e);
        }

    }

    @Override
    public ActivityOfferingResult updateActivityOffering(ActivityOfferingInfo aoInfo, ContextInfo context) throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException, VersionMismatchException {
        try {
            //update AO
            ActivityOfferingInfo activityOfferingInfo = coService.updateActivityOffering(aoInfo.getId(), aoInfo, context);
            return this.updateRegistrationGroups(activityOfferingInfo,context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean _areRegistrationGroupAoIdsValid(List<String> aoIds, ContextInfo context) {
        try {
            List<ValidationResultInfo> validations = courseOfferingServiceExtender.verifyRegistrationGroup(aoIds, context);
            for (ValidationResultInfo validation : validations) {
                if (!validation.isOk()) {
                    // If any validation is an error, then make this invalid
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteActivityOfferingCascaded(String aoId,
                                               ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        coService.deleteActivityOfferingCascaded(aoId, context);
    }

    @Override
    public List<BulkStatusInfo> moveActivityOffering(String aoId,
                                                            String sourceAocId,
                                                            String targetAocId,
                                                            ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        if(coService == null) {
            coService = getCoService();
        }
        // Fetch the AOInfo
        ActivityOfferingInfo aoInfo = coService.getActivityOffering(aoId, context);
        // Fetch the source AOC
        ActivityOfferingClusterInfo sourceAoc = coService.getActivityOfferingCluster(sourceAocId, context);
        // Verify that aoId is in this AOC
        ActivityOfferingSetInfo setWithAoId = null;
        for (ActivityOfferingSetInfo set: sourceAoc.getActivityOfferingSets()) {
            if (set.getActivityOfferingType().equals(aoInfo.getTypeKey()) &&
                    set.getActivityOfferingIds().contains(aoId)) {
                setWithAoId = set;
                break;
            }
        }
        if (setWithAoId == null) {
            // Not in source AOC
            throw new InvalidParameterException("aoId, " + aoId + ", does not appear in cluster, " + sourceAocId);
        }
        // Fetch target AOC
        ActivityOfferingClusterInfo targetAoc = coService.getActivityOfferingCluster(targetAocId, context);
        // Verify the FOs of source/target match up
        if (!sourceAoc.getFormatOfferingId().equals(targetAoc.getFormatOfferingId())) {
            throw new InvalidParameterException("Source/target AOCs do not have matching format offering IDs");
        }
        // Also, check for trivial case of the source/target AOC being identical
        if (sourceAocId.equals(targetAocId)) {
            return null; // exit if same
        }
        setWithAoId.getActivityOfferingIds().remove(aoId); // Delete the AO ID
        // This will delete RGs
        coService.updateActivityOfferingCluster(aoInfo.getFormatOfferingId(), sourceAocId, sourceAoc, context);
        // Now, add the AO ID to the target AOC
        boolean inserted = false;
        for (ActivityOfferingSetInfo set: targetAoc.getActivityOfferingSets()) {
            // Pick first AO set with matching type
            if (set.getActivityOfferingType().equals(aoInfo.getTypeKey())) {
                if (set.getActivityOfferingIds().contains(aoId)) {
                    throw new OperationFailedException("aoId already exists in target AOC--shouldn't happen");
                }
                set.getActivityOfferingIds().add(aoId);
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            // Didn't actually add it
            throw new OperationFailedException("Unable to find AO set in target AOC with correct type: " + aoInfo.getTypeKey());
        }
        // update target AOC
        ActivityOfferingClusterInfo updated =
                coService.updateActivityOfferingCluster(aoInfo.getFormatOfferingId(), targetAocId, targetAoc, context);
        // Generate missing RGs
        if (_clusterCanGenerateRgs(updated)) {
            List<BulkStatusInfo> created =
                    coService.generateRegistrationGroupsForCluster(updated.getId(), context);
            return created;  //To change body of implemented methods use File | Settings | File Templates.
        } else {
            return new ArrayList<BulkStatusInfo>(); // Unable to generate so return empty list
        }
    }

    @Override
    public void deleteActivityOfferingCluster(String aocId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, DependentObjectsExistException {
        List<ActivityOfferingInfo> aoInfos = coService.getActivityOfferingsByCluster(aocId, context);
        coService.deleteActivityOfferingClusterCascaded(aocId, context);
        // Delete each AO
        for (ActivityOfferingInfo aoInfo: aoInfos) {
            // get seat pools to delete
            List<SeatPoolDefinitionInfo> seatPools = coService.getSeatPoolDefinitionsForActivityOffering(aoInfo.getId(), context);

            // remove seat pool reference  to AO then delete orphaned seat pool
            for (SeatPoolDefinitionInfo seatPool : seatPools) {
                coService.removeSeatPoolDefinitionFromActivityOffering(seatPool.getId(), aoInfo.getId(), context);
                coService.deleteSeatPoolDefinition(seatPool.getId(), context);
            }

            // delete AO
            coService.deleteActivityOffering(aoInfo.getId(), context);
        }
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.applayer.CourseOfferingServiceFacade#getMaxEnrollmentByCourseOffering(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public Integer getSeatCountByCourseOffering(String courseOfferingId,
            ContextInfo contextInfo) throws OperationFailedException, PermissionDeniedException {
        
        try {
            CourseOfferingInfo co = coService.getCourseOffering(courseOfferingId, contextInfo);
            
            return co.getMaximumEnrollment();
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
        } catch (InvalidParameterException e) {
            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
        } catch (MissingParameterException e) {
            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
        }
        
//        try {
//            List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByCourseOffering(courseOfferingId, contextInfo);
//            
//            return _computeMaxEnrollment(aos);
//        } catch (DoesNotExistException e) {
//           throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
//           
//        } catch (InvalidParameterException e) {
//            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
//        } catch (MissingParameterException e) {
//            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
//        }
    }
    
   
    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.applayer.CourseOfferingServiceFacade#getMaxEnrollmentByActivityOfferingCluster(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public Integer getSeatCountByActivityOfferingCluster(String aocId,
            ContextInfo contextInfo) throws OperationFailedException, PermissionDeniedException {
        
        try {
            List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByCluster(aocId, contextInfo);
            
            Map<String, ActivityOfferingInfo>aoMap = new HashMap<String, ActivityOfferingInfo>();
            
            for (ActivityOfferingInfo activityOfferingInfo : aos) {
                aoMap.put(activityOfferingInfo.getId(), activityOfferingInfo);
            }
            
            ActivityOfferingClusterInfo aoc = coService.getActivityOfferingCluster(aocId, contextInfo);
            List<ActivityOfferingSetInfo> aoSets = aoc.getActivityOfferingSets();
            int maxAOCEnrollment = Integer.MAX_VALUE;
            
            for (ActivityOfferingSetInfo activityOfferingSetInfo : aoSets) {
                int maxActivityTypeEnrollment =
                        _computeMaxEnrollment(activityOfferingSetInfo.getActivityOfferingIds(), aoMap);
                
                if (maxActivityTypeEnrollment < maxAOCEnrollment) {
                    maxAOCEnrollment = maxActivityTypeEnrollment;
                }
            }
            
            FormatOfferingInfo fo = coService.getFormatOffering(aoc.getFormatOfferingId(), contextInfo);
            CourseOfferingInfo co = coService.getCourseOffering(fo.getCourseOfferingId(), contextInfo);
            int maxCOEnrollment = co.getMaximumEnrollment();
            
            // This assumes that the seat count for an aoc is the smallest ao.maxEnrollment number in this cluster.
            List<RegistrationGroupInfo> rgs = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);

            int maxAOEnrollment = _computeMaxEnrollment(aos);
            
            // cap is the smaller of the CO or AO or AOC enrollment limit
            int maxEnrollment = Math.min(Math.min(maxCOEnrollment, maxAOEnrollment), maxAOCEnrollment);
            int minEnrollment = Integer.MAX_VALUE;
            
            for (RegistrationGroupInfo registrationGroupInfo : rgs) {
                List<ActivityOfferingInfo>rgAOList = new ArrayList<ActivityOfferingInfo>();
                
                for (String aoId : registrationGroupInfo.getActivityOfferingIds()) {
                    rgAOList.add(aoMap.get(aoId));
                }
                
                int currentSeats = _computeMaxEnrollment(rgAOList);
                if (minEnrollment > currentSeats) {
                    minEnrollment = currentSeats;
                }
            }

            // actual seats can be smaller but not larger than max enrollment. 
            return Math.min(minEnrollment, maxEnrollment);

        } catch (DoesNotExistException e) {
            throw new OperationFailedException("getSeatCountByActivityOfferingCluster (aocId=" + aocId + "): failed", e);
        } catch (InvalidParameterException e) {
            throw new OperationFailedException("getSeatCountByActivityOfferingCluster (aocId=" + aocId + "): failed", e);
        } catch (MissingParameterException e) {
            throw new OperationFailedException("getSeatCountByActivityOfferingCluster (aocId=" + aocId + "): failed", e);
        }
        
    }


    private Integer _computeMaxEnrollment(List<String> aoIds, Map<String, ActivityOfferingInfo> aoMap) {
        
        List<ActivityOfferingInfo>aoList = new ArrayList<ActivityOfferingInfo>();
        for (String aoId : aoIds) {
            aoList.add(aoMap.get(aoId));
        }
        return _computeMaxEnrollment(aoList);
        
    }
    /*
     * The maxEnrollment of a list of Activity Offering's is the smallest max enrollment number.
     * 
     * This method will extract that number.
     * 
     */
    private Integer _computeMaxEnrollment(List<ActivityOfferingInfo> aos) {
        
        int minEnrollment = Integer.MAX_VALUE;
        
        for (ActivityOfferingInfo activityOfferingInfo : aos) {
            Integer maxEnrollment = activityOfferingInfo.getMaximumEnrollment();
            if (maxEnrollment != null) {
                if (minEnrollment > maxEnrollment) {
                    minEnrollment = maxEnrollment;
                }
            }
        }
        
        if (minEnrollment == Integer.MAX_VALUE) {
            // a data error that none of the AO's have a max enrollment specified
            return null;
        }
        else {
            return minEnrollment;
        }

    }
    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.applayer.CourseOfferingServiceFacade#getMaxEnrollmentByRegistrationGroup(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public Integer getSeatCountByRegistrationGroup(
            String registrationGroupId, ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException {
        
        try {
            RegistrationGroupInfo rg = coService.getRegistrationGroup(registrationGroupId, contextInfo);
            
            List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByIds(rg.getActivityOfferingIds(), contextInfo);

            return _computeMaxEnrollment(aos);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("getSeatCountByRegistrationGroup (registrationGroupId=" + registrationGroupId + "): failed", e);
        } catch (InvalidParameterException e) {
            throw new OperationFailedException("getSeatCountByRegistrationGroup (registrationGroupId=" + registrationGroupId + "): failed", e);
        } catch (MissingParameterException e) {
            throw new OperationFailedException("getSeatCountByRegistrationGroup (registrationGroupId=" + registrationGroupId + "): failed", e);
        }
    }

    private Set<Set<String>> _convertToSetOfSetOfStrings(Set<List<String>> setOfRgAoIdLists) {
        Set<Set<String>> rgAoIdSets = new HashSet<Set<String>>();
        for (List<String> rgAoIds: setOfRgAoIdLists) {
            rgAoIdSets.add(new HashSet<String>(rgAoIds));
        }
        return rgAoIdSets;
    }

    private Set<Set<String>> _generatePotentialRgAoIdSets(ActivityOfferingClusterInfo cluster) {
        try {
            Set<List<String>> setOfRgAoIdLists =
                    PermutationCounter.computeMissingRegGroupAoIdsInCluster(cluster, new ArrayList<RegistrationGroupInfo>());
            Set<Set<String>> setOfRgAoIdSets =
                    _convertToSetOfSetOfStrings(setOfRgAoIdLists);
            return setOfRgAoIdSets;
        } catch (DataValidationErrorException e) {
            return null;
        }
    }

    @Override
    public List<CourseOfferingAutogenIssue> findAutogenIssuesByTerm(String termId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException {
        List<String> coIds = coService.getCourseOfferingIdsByTerm(termId, Boolean.TRUE, context);
        List<CourseOfferingAutogenIssue> termCoIssues = new ArrayList<CourseOfferingAutogenIssue>();
        for (String coId: coIds) {
            CourseOfferingAutogenIssue coIssue = findAutogenIssuesByCourseOffering(coId, context);
            if (coIssue != null) {
                termCoIssues.add(coIssue);
            }
        }
        return termCoIssues;
    }

    @Override
    public CourseOfferingAutogenIssue findAutogenIssuesByCourseOffering(String courseOfferingId, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        List<FormatOfferingInfo> fos = coService.getFormatOfferingsByCourseOffering(courseOfferingId, context);
        CourseOfferingAutogenIssue coIssue = new CourseOfferingAutogenIssue(courseOfferingId);
        for (FormatOfferingInfo fo: fos) {
            String foId = fo.getId();
            FormatOfferingAutogenIssue foIssue = new FormatOfferingAutogenIssue(foId);
            List<ActivityOfferingInfo> aoInfos =
                    coService.getActivityOfferingsByFormatOffering(fo.getId(), context);
            Set<String> aoIdSet = new HashSet<String>();
            for (ActivityOfferingInfo ao: aoInfos) {
                aoIdSet.add(ao.getId());
            }
            // Find AOs without clusters
            List<ActivityOfferingInfo> aosWoClusters =
                    coService.getActivityOfferingsWithoutClusterByFormatOffering(foId, context);
            // Gather only the IDs
            Set<String> aoIdsWoClusters = new HashSet<String>();
            for (ActivityOfferingInfo aoInfo: aosWoClusters) {
                aoIdsWoClusters.add(aoInfo.getId());
            }
            // Then create issues associated with it
            if (!aoIdsWoClusters.isEmpty()) {
                ActivityOfferingNotInAocSubissue aoNotInAoc = new ActivityOfferingNotInAocSubissue(courseOfferingId, fo.getId());
                aoNotInAoc.getActivityOfferingIds().addAll(aoIdsWoClusters);
                foIssue.getSubIssues().add(aoNotInAoc);  // Add the issue
            }
            // --------------------
            // Now verify RGs have correct AOs
            // First create a map
            List<Set<String>> aocAoIdList = new ArrayList<Set<String>>();
            List<ActivityOfferingClusterInfo> clusters =
                    coService.getActivityOfferingClustersByFormatOffering(foId, context);
            Set<Set<String>> possibleRgAOIds = new HashSet<Set<String>>();
            Set<Set<String>> actualRgAOIds = new HashSet<Set<String>>();
            for (ActivityOfferingClusterInfo cluster: clusters) {
                Set<String> clusterAoIds = new HashSet<String>();
                for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
                    clusterAoIds.addAll(set.getActivityOfferingIds());
                }
                aocAoIdList.add(clusterAoIds);
                Set<Set<String>> possibleRgAoIdsByAoc =
                        _generatePotentialRgAoIdSets(cluster);
                // Add to list of possible RgAOIds for this format offering
                possibleRgAOIds.addAll(possibleRgAoIdsByAoc);
            }
            // Now go through the RGs to check for invalid ones
            List<RegistrationGroupInfo> rgInfos =
                    coService.getRegistrationGroupsByFormatOffering(fo.getId(), context);
            for (RegistrationGroupInfo rg: rgInfos) {
                boolean found = false;
                List<String> rgAoIds = rg.getActivityOfferingIds();
                for (Set<String> aocAoIds: aocAoIdList) {
                    if (aocAoIds.containsAll(rgAoIds)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // No AOC contains this RG
                    InvalidRegGroupSubissue rgIssue = new InvalidRegGroupSubissue(courseOfferingId, foId);
                    foIssue.getSubIssues().add(rgIssue);  // Add the issue
                } else {
                    // Valid RG, store that info
                    Set<String> rgAOIds = new HashSet<String>(rg.getActivityOfferingIds());
                    actualRgAOIds.add(rgAOIds);
                }
            }
            // Now find RGs that should have been created, but weren't
            possibleRgAOIds.removeAll(actualRgAOIds);
            Set<Set<String>> missingRgAOIds // renaming to make it easier to see what's going on
                    = new HashSet<Set<String>>(possibleRgAOIds);
            for (Set<String> rgAoIdSet: missingRgAOIds) {
                // Create an issue
                RegGroupNotGeneratedByAocSubissue subissue =
                        new RegGroupNotGeneratedByAocSubissue(courseOfferingId, foId);
                subissue.getActivityOfferingIds().addAll(rgAoIdSet);
                foIssue.getSubIssues().add(subissue);
            }
            if (!foIssue.getSubIssues().isEmpty()) {
                coIssue.getFormatOfferingIssues().add(foIssue);
            }
        }
        if (coIssue.getFormatOfferingIssues().isEmpty()) {
            return null;
        } else {
            return coIssue;
        }
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.adapter.CourseOfferingServiceFacade#getAutogenCountByCourseOffering(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public AutogenCount getAutogenCountByCourseOffering(
            String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        SearchRequestInfo request = new SearchRequestInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_CO);
        
        request.addParam(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_CO_ID_PARAM, courseOfferingId);
        
        return runAutogenCountSearch (request, context); 
    }

    /*
     * Helper to dispatch the search request and accumulate the results into an AutogenCount object.
     */
    private AutogenCount runAutogenCountSearch(SearchRequestInfo request, ContextInfo context) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        request.setMaxResults(1);

        SearchResultInfo results = getSearchService().search(request, context);

        if (results.getRows().size() == 0) {
            // handle the no match case (This probably should never happen but this guards against such a case causing problems)
            throw new DoesNotExistException("No Results");
        }
        // else:
        SearchResultRowInfo row = KSCollectionUtils.getRequiredZeroElement(results.getRows());

        AutogenCount count = new AutogenCount();

        // setup the defaults
        count.setNumberOfActivityOfferingClusters(0);
        count.setNumberOfActivityOfferings(0);
        count.setNumberOfInvalidRegistrationGroups(0);
        count.setNumberOfRegistrationGroups(0);
        
        List<SearchResultCellInfo> cells = row.getCells();

        for (SearchResultCellInfo cellInfo : cells) {
            String cellKey = cellInfo.getKey();

            if (cellKey
                    .equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_AOS)) {
                count.setNumberOfActivityOfferings(Integer.parseInt(cellInfo
                        .getValue()));
            } else if (cellKey
                    .equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_AOCS)) {
                count.setNumberOfActivityOfferingClusters(Integer
                        .parseInt(cellInfo.getValue()));
            } else if (cellKey
                    .equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_RGS)) {
                count.setNumberOfRegistrationGroups(Integer.parseInt(cellInfo
                        .getValue()));
            } else if (cellKey
                    .equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_INVALID_RGS)) {
                count.setNumberOfInvalidRegistrationGroups(Integer
                        .parseInt(cellInfo.getValue()));
            }
        }

        return count;
    }
    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.adapter.CourseOfferingServiceFacade#getAutogenCountByFormatOffering(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public AutogenCount getAutogenCountByFormatOffering(
            String formatOfferingId, ContextInfo context) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
       
        SearchRequestInfo request = new SearchRequestInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_FO);
        
        request.addParam(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_FO_ID_PARAM, formatOfferingId);
        
        return runAutogenCountSearch (request, context); 
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.adapter.CourseOfferingServiceFacade#getAutogenCountByActivtyOfferingCluster(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public AutogenCount getAutogenCountByActivtyOfferingCluster(
            String activiyOfferingClusterId, ContextInfo context) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        
        SearchRequestInfo request = new SearchRequestInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_AOC);
        
        request.addParam(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_AOC_ID_PARAM, activiyOfferingClusterId);
        
        return runAutogenCountSearch (request, context); 
        
    }

    /**
     * Returns all ActivityOfferingClusterInfos that map back to a single course offering
     *
     * @param courseOfferingId CO id search param
     * @return all ActivityOfferingClusterInfos that map back to a single course offering
     */
    @Override
    public List<ActivityOfferingClusterInfo> getActivityOfferingClusterByCourseOffering(String courseOfferingId) {

        List<ActivityOfferingClusterInfo> lRet = new ArrayList<ActivityOfferingClusterInfo>();
        List<ActivityOfferingClusterEntity> aoClusters = getActivityOfferingClusterDao().getByCourseOffering(courseOfferingId);

        for(ActivityOfferingClusterEntity aoc : aoClusters){
            lRet.add(aoc.toDto());
        }
        return lRet;
    }

    public CourseOfferingService getCoService() {
        if(coService == null) {
            coService =  GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return coService;
    }

    public CourseWaitListService getCourseWaitListService() {
        if(courseWaitListService == null) {
            courseWaitListService =  GlobalResourceLoader.getService(new QName(CourseWaitListServiceConstants.NAMESPACE,
                    CourseWaitListServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return courseWaitListService;
    }

    public void setCourseWaitListService(CourseWaitListService courseWaitListService) {
        this.courseWaitListService = courseWaitListService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public TypeService getTypeService() {
        if (typeService == null) {
            QName qname = new QName(TypeServiceConstants.NAMESPACE,
                                    TypeServiceConstants.SERVICE_NAME_LOCAL_PART);
            typeService =  GlobalResourceLoader.getService(qname);
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public CourseService getCourseService() {
        if (courseService == null) {
            QName qname = new QName(CourseServiceConstants.NAMESPACE,
                    CourseServiceConstants.SERVICE_NAME_LOCAL_PART);
            courseService = GlobalResourceLoader.getService(qname);
        }
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public ActivityOfferingClusterDaoApi getActivityOfferingClusterDao() {
        return activityOfferingClusterDao;
    }

    public void setActivityOfferingClusterDao(ActivityOfferingClusterDaoApi activityOfferingClusterDao) {
        this.activityOfferingClusterDao = activityOfferingClusterDao;
    }

    public CourseWaitListServiceFacade getWaitListServiceFacade() {
        return waitListServiceFacade;
    }

    public void setWaitListServiceFacade(CourseWaitListServiceFacade waitListServiceFacade) {
        this.waitListServiceFacade = waitListServiceFacade;
    }

    public ExamOfferingServiceFacade getExamOfferingServiceFacade() {
        return examOfferingServiceFacade;
    }

    public void setExamOfferingServiceFacade(ExamOfferingServiceFacade examOfferingServiceFacade) {
        this.examOfferingServiceFacade = examOfferingServiceFacade;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setCourseOfferingServiceExtender(CourseOfferingServiceExtender courseOfferingServiceExtender) {
        this.courseOfferingServiceExtender = courseOfferingServiceExtender;
    }

    public CourseOfferingServiceExtender getCourseOfferingServiceExtender() {
        return courseOfferingServiceExtender;
    }
}
