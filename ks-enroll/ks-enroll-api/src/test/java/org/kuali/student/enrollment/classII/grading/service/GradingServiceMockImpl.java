package org.kuali.student.enrollment.classII.grading.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.enrollment.grading.dto.AssignedGradeInfo;
import org.kuali.student.enrollment.grading.dto.CreditsEarnedInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.lrc.dto.ResultValueGroupInfo;

public class GradingServiceMockImpl implements GradingService {

    private static Map<String, GradeRosterInfo> gradeRostersCache = new HashMap<String, GradeRosterInfo>();

    private static Map<String, GradeRosterEntryInfo> gradeRosterEntriesCache = new HashMap<String, GradeRosterEntryInfo>();

    private static Map<String, List<String>> termCourseOfferingsCache = new HashMap<String, List<String>>();

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeInfo getGradeRosterType(String gradeRosterTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GradeRosterInfo getGradeRoster(String gradeRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return gradeRostersCache.get(gradeRosterId);

    }

    @Override
    public List<GradeRosterInfo> getGradeRostersByGraderAndTerm(String graderId, String termKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<GradeRosterInfo> gradeRosters = new ArrayList<GradeRosterInfo>();
        for (GradeRosterInfo gradeRoster : gradeRostersCache.values()) {

            if (gradeRoster.getGraderIds().contains(graderId)
                    && termCourseOfferingsCache.get(termKey).contains(gradeRoster.getCourseOfferingId())) {
                gradeRosters.add(gradeRoster);

            }

        }
        return gradeRosters;
    }

    @Override
    public List<GradeRosterInfo> getFinalGradeRostersForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<GradeRosterInfo> gradeRostersToReturn = new ArrayList<GradeRosterInfo>();
        for (GradeRosterInfo gradeRoster : gradeRostersCache.values()) {
            if (gradeRoster.getCourseOfferingId().equals(courseOfferingId)
                    && gradeRoster.getTypeKey().equals("FINAL_TYPE_KEY")) {
                gradeRostersToReturn.add(gradeRoster);

            }
        }
        return gradeRostersToReturn;

    }

    @Override
    public List<GradeRosterInfo> getFinalGradeRostersForActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<GradeRosterInfo> allGradeRostersForActivty = new ArrayList<GradeRosterInfo>();

        for (GradeRosterInfo gradeRoster : gradeRostersCache.values()) {

            if (gradeRoster.getTypeKey().equals("FINAL_TYPE_KEY")) {
                for (String gradeRosterEntryId : gradeRoster.getGradeRosterEntryIds()) {
                    GradeRosterEntryInfo gradeRosterENtry = gradeRosterEntriesCache.get(gradeRosterEntryId);
                    if (gradeRosterENtry.getActivityOfferingId().equals(activityOfferingId)) {

                        allGradeRostersForActivty.add(gradeRoster);
                        break;
                    }
                }
            }
        }
        return allGradeRostersForActivty;

    }

    @Override
    public List<GradeRosterInfo> getGradeRostersForActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<GradeRosterInfo> allGradeRostersForActivty = new ArrayList<GradeRosterInfo>();

        for (GradeRosterInfo gradeRoster : gradeRostersCache.values()) {

            for (String gradeRosterEntryId : gradeRoster.getGradeRosterEntryIds()) {
                GradeRosterEntryInfo gradeRosterENtry = gradeRosterEntriesCache.get(gradeRosterEntryId);
                if (gradeRosterENtry.getActivityOfferingId().equals(activityOfferingId)) {

                    allGradeRostersForActivty.add(gradeRoster);
                    break;
                }
            }

        }
        return allGradeRostersForActivty;
    }

    @Override
    public GradeRosterInfo buildInterimGradeRosterByType(List<String> activityOfferingIdList, String rosterTypeKey,
            ContextInfo context) throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        GradeRosterInfo gradeRoster = new GradeRosterInfo();
        gradeRoster.setId(String.valueOf(Math.random()));
        gradeRoster.setTypeKey(rosterTypeKey);
        List<GradeRosterEntryInfo> gradeEntries = new ArrayList<GradeRosterEntryInfo>(activityOfferingIdList.size());
        int i = 0;
        for (GradeRosterEntryInfo gradeEntry : gradeEntries) {
            gradeEntry = new GradeRosterEntryInfo();
            gradeEntry.setId(String.valueOf(Math.random()));
            gradeEntry.setActivityOfferingId(activityOfferingIdList.get(i));
            i++;
            gradeRosterEntriesCache.put(gradeEntry.getId(), gradeEntry);
            gradeRoster.getGradeRosterEntryIds().add(gradeEntry.getId());
        }

        gradeRostersCache.put(gradeRoster.getId(), gradeRoster);

        return gradeRoster;

    }

    @Override
    public GradeRosterInfo updateInterimGradeRoster(GradeRosterInfo gradeRoster, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        gradeRostersCache.put(gradeRoster.getId(), gradeRoster);
        return gradeRoster;
    }

    @Override
    public StatusInfo deleteInterimGradeRoster(String gradeRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        gradeRostersCache.remove(gradeRosterId);

        return new StatusInfo();
    }

    @Override
    public GradeRosterInfo updateFinalGradeRosterState(String gradeRosterId, String stateKey, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        GradeRosterInfo gradeRoster = gradeRostersCache.get(gradeRosterId);
        gradeRoster.setStateKey(stateKey);
        return gradeRoster;

    }

    @Override
    public List<ValidationResultInfo> validateGradeRoster(GradeRosterInfo gradeRoster, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GradeRosterEntryInfo getGradeRosterEntry(String gradeRosterEntryId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        return gradeRosterEntriesCache.get(gradeRosterEntryId);
    }

    @Override
    public List<GradeRosterEntryInfo> getGradeRosterEntriesByIdList(List<String> gradeRosterEntryIdList,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<GradeRosterEntryInfo> allRosterEntries = new ArrayList<GradeRosterEntryInfo>();
        for (String id : gradeRosterEntryIdList) {
            allRosterEntries.add(getGradeRosterEntry(id, context));

        }

        return allRosterEntries;
    }

    @Override
    public List<GradeRosterEntryInfo> getGradeRosterEntriesByRosterId(String gradeRosterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        GradeRosterInfo gradeRoster = gradeRostersCache.get(gradeRosterId);
        return getGradeRosterEntriesByIdList(gradeRoster.getGradeRosterEntryIds(), context);

    }

    @Override
    public List<ResultValueGroupInfo> getValidGradesForStudentByRoster(String studentId, String rosterId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
       
            List<ResultValueGroupInfo> rvgInfo =  new ArrayList<ResultValueGroupInfo>();
            List<GradeRosterEntryInfo>  gradeRosterEntries = getGradeRosterEntriesByRosterId(rosterId, context);
            for(GradeRosterEntryInfo geInfo:gradeRosterEntries){
               if( geInfo.getStudentId().equals(studentId))
                   rvgInfo.add(new ResultValueGroupInfo());
               
                   
            }
            
            return rvgInfo;
    }

    @Override
    public GradeRosterEntryInfo getFinalGradeForStudentInCourseOffering(String studentId, String courseOfferingId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GradeRosterEntryInfo addEntrytoInterimRoster(GradeRosterEntryInfo gradeRosterEntry, String gradeRosterId,
            ContextInfo context) throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
      
        gradeRosterEntriesCache.put(gradeRosterEntry.getId(), gradeRosterEntry);
        GradeRosterInfo gInfo = gradeRostersCache.get(gradeRosterId);
        gInfo.getGradeRosterEntryIds().add(gradeRosterEntry.getId());
        
        return gradeRosterEntry;
    }

    @Override
    public StatusInfo removeEntryFromInterimRoster(String gradeRosterEntryId, String gradeRosterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
     
        gradeRosterEntriesCache.remove(gradeRosterEntryId);
        GradeRosterInfo gInfo = gradeRostersCache.get(gradeRosterId);
        gInfo.getGradeRosterEntryIds().remove(gradeRosterEntryId);
        return new StatusInfo();
        
    }

    @Override
    public AssignedGradeInfo updateAssignedGrade(String gradeRosterEntryId, AssignedGradeInfo assignedGrade,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        GradeRosterEntryInfo geInfo =    gradeRosterEntriesCache.gete(gradeRosterEntryId);
        geInfo.setAssignedGrade(assignedGrade);
        return assignedGrade;
    }

    @Override
    public CreditsEarnedInfo updateCredit(String gradeRosterEntryId, CreditsEarnedInfo assignedGrade,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO Auto-generated method stub
        return null;
    }

}
