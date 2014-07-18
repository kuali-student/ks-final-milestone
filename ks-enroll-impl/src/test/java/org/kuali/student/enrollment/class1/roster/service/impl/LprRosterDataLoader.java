package org.kuali.student.enrollment.class1.roster.service.impl;

import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;
import org.kuali.student.enrollment.roster.service.LprRosterService;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LprRosterDataLoader extends AbstractMockServicesAwareDataLoader {

    @Resource
    private LprRosterService rosterService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public static String LPR_ROSTER_ENTRY_TYPE_GRADE = "kuali.lpr.roster.entry.type.course.grade";
    public static String LPR_ROSTER_ENTRY_TYPE_AUDIT = "kuali.lpr.roster.entry.type.course.audit";

    public LprRosterService getRosterService() {
        return rosterService;
    }

    public void setRosterService(LprRosterService rosterService) {
        this.rosterService = rosterService;
    }

    @Override
    protected void initializeData() throws Exception {
        createRosters();
        createEntries();
    }

     private void createRosters()
             throws DataValidationErrorException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
         TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
         timeAmountInfo.setTimeQuantity(10);
         timeAmountInfo.setAtpDurationTypeKey(AtpServiceConstants.DURATION_HOURS_TYPE_KEY);
         List<String> associatedLuis = new ArrayList<String>();
         associatedLuis.add("1");
         createRoster("A", true, timeAmountInfo, 30, associatedLuis, LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADE_TYPE_KEY, LprServiceConstants.LPRROSTER_READY_STATE_KEY);

         timeAmountInfo = new TimeAmountInfo();
         timeAmountInfo.setTimeQuantity(1);
         timeAmountInfo.setAtpDurationTypeKey(AtpServiceConstants.DURATION_WEEK_TYPE_KEY);
         associatedLuis.add("2");
         associatedLuis.add("3");
         associatedLuis.add("4");
         createRoster("B", false, timeAmountInfo, 1000, associatedLuis, LprServiceConstants.LPRROSTER_COURSE_MIDTERM_GRADE_TYPE_KEY, LprServiceConstants.LPRROSTER_READY_STATE_KEY);

         timeAmountInfo = new TimeAmountInfo();
         timeAmountInfo.setTimeQuantity(5);
         timeAmountInfo.setAtpDurationTypeKey(AtpServiceConstants.DURATION_WEEK_TYPE_KEY);
         associatedLuis = null;
         createRoster("C", false, timeAmountInfo, 1000, associatedLuis, LprServiceConstants.LPRROSTER_COURSE_MIDTERM_GRADE_TYPE_KEY, LprServiceConstants.LPRROSTER_READY_STATE_KEY);

         timeAmountInfo = null;
         associatedLuis = new ArrayList<String>();
         associatedLuis.add("4");
         associatedLuis.add("5");
         associatedLuis.add("6");
         associatedLuis.add("7");
         associatedLuis.add("8");
         associatedLuis.add("9");
         createRoster("D", false, timeAmountInfo, 0, associatedLuis, LprServiceConstants.LPRROSTER_COURSE_MIDTERM_GRADE_TYPE_KEY, LprServiceConstants.LPRROSTER_READY_STATE_KEY);
    }

    private void createRoster(String id, boolean checkInRequired, TimeAmountInfo frequency, Integer maxCapacity, List<String> associatedLuis, String typeKey, String stateKey)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        LprRosterInfo roster = new LprRosterInfo();
        roster.setId(id);
        roster.setName("Roster " + id);
        roster.setDescr(RichTextHelper.buildRichTextInfo("Roster " + id + " plain", "Roster " + id + " formatted"));
        roster.setCheckInRequired(checkInRequired);
        roster.setCheckInFrequency(frequency);
        roster.setMaximumCapacity(maxCapacity);
        roster.setAssociatedLuiIds(associatedLuis);
        roster.setTypeKey(typeKey);
        roster.setStateKey(stateKey);

        rosterService.createLprRoster(typeKey, roster, context);
    }

     private void createEntries ()
             throws ParseException, DataValidationErrorException, PermissionDeniedException, OperationFailedException, InvalidParameterException,
             ReadOnlyException, MissingParameterException, DoesNotExistException {


         //This should create the list with the priorities reversed.
         createEntry("1", 1, "1", "A", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("2", 1, "2", "A", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("3", 1, "3", "A", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("4", 1, "4", "A", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("5", 1, "5", "A", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("6", 1, "6", "A", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("7", 1, "7", "A", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("8", 1, "8", "A", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_AUDIT, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("9", 1, "9", "A", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_AUDIT, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);


         createEntry("10", 1, "10", "B", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);

         createEntry("11", 1, "11", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("12", 2, "12", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("13", 3, "13", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("14", 4, "14", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("15", 5, "15", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("16", 6, "16", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("17", 7, "17", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_GRADE, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("18", 8, "18", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_AUDIT, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         //create last entry with the first position.  This should shift the other entries down.
         createEntry("19", 1, "19", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_AUDIT, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
         createEntry("20", 1, "19", "C", dateFormat.parse("20130611"), dateFormat.parse("20500101"), LPR_ROSTER_ENTRY_TYPE_AUDIT, LprServiceConstants.LPRROSTER_CREATED_STATE_KEY);
    }

    private void createEntry(String id, Integer position, String lprId, String rosterId, Date effDate, Date expDate, String typeKey, String stateKey)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        LprRosterEntryInfo entry = new LprRosterEntryInfo();
        entry.setId(id);
        entry.setPosition(position);
        entry.setLprId(lprId);
        entry.setLprRosterId(rosterId);
        entry.setEffectiveDate(effDate);
        entry.setExpirationDate(expDate);
        entry.setTypeKey(typeKey);
        entry.setStateKey(stateKey);

        rosterService.createLprRosterEntry(rosterId, lprId, typeKey, entry, context);
    }
}
