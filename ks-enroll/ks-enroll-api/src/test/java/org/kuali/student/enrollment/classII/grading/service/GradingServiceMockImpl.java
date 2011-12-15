package org.kuali.student.enrollment.classII.grading.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.dto.GradeValuesGroupInfo;
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
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;

public class GradingServiceMockImpl implements GradingService {

    public static Map<String, GradeRosterInfo> gradeRostersCache = new HashMap<String, GradeRosterInfo>();

    public static Map<String, GradeRosterEntryInfo> gradeRosterEntriesCache = new HashMap<String, GradeRosterEntryInfo>();

    public static Map<String, List<String>> termCourseOfferingsCache = new HashMap<String, List<String>>();

    private static Map<String, GradeValuesGroupInfo> validGradesCache = new HashMap<String, GradeValuesGroupInfo>();

    public GradingServiceMockImpl() {
        loadCaches();
    }

    public void loadCaches() {
        GradeRosterEntryInfo gradeRosterEntryInfo1 = new GradeRosterEntryInfo();
        gradeRosterEntryInfo1.setId("1");
        gradeRosterEntryInfo1.setStudentId("1100");
        gradeRosterEntriesCache.put(gradeRosterEntryInfo1.getId(), gradeRosterEntryInfo1);

        GradeRosterEntryInfo gradeRosterEntryInfo2 = new GradeRosterEntryInfo();
        gradeRosterEntryInfo2.setId("2");
        gradeRosterEntryInfo2.setStudentId("1101");
        gradeRosterEntriesCache.put(gradeRosterEntryInfo2.getId(), gradeRosterEntryInfo2);

        GradeRosterEntryInfo gradeRosterEntryInfo3 = new GradeRosterEntryInfo();
        gradeRosterEntryInfo3.setId("3");
        gradeRosterEntryInfo3.setStudentId("1102");
        gradeRosterEntriesCache.put(gradeRosterEntryInfo3.getId(), gradeRosterEntryInfo3);

        GradeRosterEntryInfo gradeRosterEntryInfo4 = new GradeRosterEntryInfo();
        gradeRosterEntryInfo4.setId("4");
        gradeRosterEntryInfo4.setStudentId("1103");
        gradeRosterEntriesCache.put(gradeRosterEntryInfo4.getId(), gradeRosterEntryInfo4);

        GradeRosterInfo gradeRosterInfo1 = new GradeRosterInfo();
        gradeRosterInfo1.setCourseOfferingId("PHYS121");
        gradeRosterInfo1.setId("PHYS121");
        List<String> gradeRosterEntryIds = new ArrayList();
        gradeRosterEntryIds.add("1");
        gradeRosterEntryIds.add("2");
        gradeRosterEntryIds.add("3");

        gradeRosterInfo1.setGradeRosterEntryIds(gradeRosterEntryIds);
        gradeRosterInfo1.setName("Fundamentals of Physics I");
        gradeRosterInfo1.setTypeKey("FINAL_TYPE_KEY");
        List grader = new ArrayList();
        grader.add("admin");
        gradeRosterInfo1.setGraderIds(grader);
        gradeRostersCache.put(gradeRosterInfo1.getId(), gradeRosterInfo1);

        /*
         * GradeRosterInfo gradeRosterInfo2 = new GradeRosterInfo();
         * gradeRosterInfo2 = new GradeRosterInfo();
         * gradeRosterInfo2.setCourseOfferingId("PHYS122");
         * gradeRosterInfo2.setId("PHYS122");
         * gradeRosterInfo2.setTypeKey("FINAL_TYPE_KEY");
         * gradeRosterInfo2.setName("Fundamentals of Physics II");
         */

        List<String> courseOfferings = new ArrayList();
        courseOfferings.add("PHYS121");
        // courseOfferings.add(gradeRosterInfo2.getCourseOfferingId());
        termCourseOfferingsCache.put("201108", courseOfferings);

        loadValidGradesCache();

    }

    private ResultValueInfo createResultValue(String value) {
        ResultValueInfo rv = new ResultValueInfo();
        rv.setValue(value);
        rv.setKey("kuali.student.rv." + Math.random());
        return rv;
    }

    private void loadValidGradesCache() {

        List<ResultValueInfo> letterGrades = new ArrayList<ResultValueInfo>();
        letterGrades.add(createResultValue("A+"));
        letterGrades.add(createResultValue("A"));
        letterGrades.add(createResultValue("A-"));
        letterGrades.add(createResultValue("B+"));
        letterGrades.add(createResultValue("B"));
        letterGrades.add(createResultValue("B-"));
        letterGrades.add(createResultValue("C+"));
        letterGrades.add(createResultValue("C"));
        letterGrades.add(createResultValue("C-"));
        letterGrades.add(createResultValue("D+"));
        letterGrades.add(createResultValue("D"));
        letterGrades.add(createResultValue("D-"));
        letterGrades.add(createResultValue("F"));

        List<ResultValueInfo> completionNotationGrades = new ArrayList<ResultValueInfo>();
        completionNotationGrades.add(createResultValue("Completed"));
        completionNotationGrades.add(createResultValue("Not-Completed"));
        completionNotationGrades.add(createResultValue("In-Progress"));

        List<ResultValueInfo> passFailGrades = new ArrayList<ResultValueInfo>();
        passFailGrades.add(createResultValue("Pass"));
        passFailGrades.add(createResultValue("Fail"));

        List<ResultValueInfo> satisfactoryGrades = new ArrayList<ResultValueInfo>();
        satisfactoryGrades.add(createResultValue("Satisfactory"));
        satisfactoryGrades.add(createResultValue("Not-Satisfactory"));

        GradeValuesGroupInfo letterGradesResultValuesGroupInfo = new GradeValuesGroupInfo();
        letterGradesResultValuesGroupInfo.setResultValueInfos(letterGrades);
        letterGradesResultValuesGroupInfo.setResultValueRange(null);

        GradeValuesGroupInfo completionNotationResultValuesGroupInfo = new GradeValuesGroupInfo();
        completionNotationResultValuesGroupInfo.setResultValueInfos(completionNotationGrades);
        completionNotationResultValuesGroupInfo.setResultValueRange(null);

        GradeValuesGroupInfo passFailResultValuesGroupInfo = new GradeValuesGroupInfo();
        passFailResultValuesGroupInfo.setResultValueInfos(passFailGrades);
        passFailResultValuesGroupInfo.setResultValueRange(null);

        GradeValuesGroupInfo completionNotationGradesResultValuesGroupInfo = new GradeValuesGroupInfo();
        completionNotationGradesResultValuesGroupInfo.setResultValueInfos(completionNotationGrades);
        completionNotationGradesResultValuesGroupInfo.setResultValueRange(null);

        GradeValuesGroupInfo passFailGradesResultValuesGroupInfo = new GradeValuesGroupInfo();
        passFailGradesResultValuesGroupInfo.setResultValueInfos(passFailGrades);
        passFailGradesResultValuesGroupInfo.setResultValueRange(null);

        GradeValuesGroupInfo satisfactoryGradesResultValuesGroupInfo = new GradeValuesGroupInfo();
        satisfactoryGradesResultValuesGroupInfo.setResultValueInfos(satisfactoryGrades);
        satisfactoryGradesResultValuesGroupInfo.setResultValueRange(null);

        ResultValueRangeInfo resultValueRangeInfo = new ResultValueRangeInfo();
        resultValueRangeInfo.setMaxValue("100");
        resultValueRangeInfo.setMinValue("0");
        resultValueRangeInfo.setIncrement(".01");
        GradeValuesGroupInfo percentGradesResultValuesGroupInfo = new GradeValuesGroupInfo();
        percentGradesResultValuesGroupInfo.setResultValueInfos(null);
        percentGradesResultValuesGroupInfo.setResultValueRange(resultValueRangeInfo);

        validGradesCache.put("letter", letterGradesResultValuesGroupInfo);
        validGradesCache.put("completionNotation", completionNotationGradesResultValuesGroupInfo);
        validGradesCache.put("passFail", passFailGradesResultValuesGroupInfo);
        validGradesCache.put("satisfactory", satisfactoryGradesResultValuesGroupInfo);
        validGradesCache.put("percent", percentGradesResultValuesGroupInfo);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
        return new ArrayList<String>();
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        return null;
    }

    @Override
    public TypeInfo getGradeRosterType(String gradeRosterTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
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
        if (termCourseOfferingsCache.get(termKey) != null) {
            for (GradeRosterInfo gradeRoster : gradeRostersCache.values()) {
                if (gradeRoster.getGraderIds().contains(graderId)
                        && termCourseOfferingsCache.get(termKey).contains(gradeRoster.getCourseOfferingId())) {
                    gradeRosters.add(gradeRoster);
                }

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
                GradeRosterEntryInfo gradeRosterEntry = gradeRosterEntriesCache.get(gradeRosterEntryId);
                if (gradeRosterEntry.getActivityOfferingId().equals(activityOfferingId)) {

                    allGradeRostersForActivty.add(gradeRoster);
                    break;
                }
            }

        }
        return allGradeRostersForActivty;
    }

    @Override
    public GradeRosterInfo buildInterimGradeRosterByType(String courseOfferingId, List<String> activityOfferingIdList,
            String rosterTypeKey, ContextInfo context) throws AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

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
        return new ArrayList<ValidationResultInfo>();
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
    public GradeRosterEntryInfo getFinalGradeForStudentInCourseOffering(String studentId, String courseOfferingId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
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
    public boolean updateGrade(String gradeRosterEntryId, String assignedGradeKey, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        GradeRosterEntryInfo geInfo = gradeRosterEntriesCache.get(gradeRosterEntryId);
        geInfo.setAssignedGradeKey(assignedGradeKey);
        return true;
    }

    @Override
    public boolean updateCredit(String gradeRosterEntryId, String creditKey, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        GradeRosterEntryInfo geInfo = gradeRosterEntriesCache.get(gradeRosterEntryId);
        geInfo.setCreditsEarnedKey(creditKey);
        return true;
    }

    @Override
    public List<GradeValuesGroupInfo> getGradeGroupsByKeyList(List<String> gradeGroupIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public boolean updateNumberGrade(String gradeRosterEntryId, String numberGradeValue, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return false;
    }

}
