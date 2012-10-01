package org.kuali.student.r2.core.class1.atp.service.impl;

import java.text.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.acal.service.assembler.TermAssembler;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

public class AtpTestDataLoader {

    public AtpTestDataLoader() {
    }

    public AtpTestDataLoader(AtpService atpService) {
        this.atpService = atpService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }
    private AtpService atpService;
    private static String principalId = AtpTestDataLoader.class.getSimpleName();

    public void loadData() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException, AlreadyExistsException {
        loadAtp("testAtpId1", "testAtp1", "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Draft", "Desc 101");
        loadAtp("testAtpId2", "testAtp2", "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0", "kuali.atp.type.HolidayCalendar", "kuali.atp.state.Draft", "Desc 102");
        loadAtp("testDeleteAtpId1", "testDeleteAtp1", "2012-01-01 00:00:00.0", "2100-12-31 00:00:00.0", "kuali.atp.type.HolidayCalendar", "kuali.atp.state.Draft", "Desc 103");
        loadAtp("testDeleteAtpId2", "testDeleteAtp2", "2012-01-01 00:00:00.0", "2100-12-31 00:00:00.0", "kuali.atp.type.HolidayCalendar", "kuali.atp.state.Draft", "Desc 104");
        loadAtp("testTermId1", "testTerm1", "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0", "kuali.atp.type.Fall", "kuali.atp.state.Draft", "Desc 2");
        loadAtp("testTermId2", "testTerm2", "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0", "kuali.atp.type.Spring", "kuali.atp.state.Draft", "Desc 3");

        updAtpDynamicAttribute("CredentialProgramType", "kuali.lu.type.credential.Baccalaureate", "testAtpId1");

        loadAtp("ACADEMICCALENDAR1990", "1990 Academic Calendar", "1990-08-01 00:00:00.0", "1991-12-31 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "1990 Academic Calendar");
        loadAtp("CAMPUSCALENDAR19901991", "Holiday Calendar 1990-1991", "1990-08-01 00:00:00.0", "1991-12-31 00:00:00.0", "kuali.atp.type.HolidayCalendar", "kuali.atp.state.Official", "1990-1991 Holiday Calendar");
        loadAtp("FALLFIRSTBLOCK1990", "Fall First Block 1990", "1990-08-01 00:00:00.0", "1990-12-31 00:00:00.0", "kuali.atp.type.HalfFall1", "kuali.atp.state.Official", "Fall First Block 1990");
        loadAtp("FALLTERM1990", "Fall Term 1990", "1990-08-01 00:00:00.0", "1990-12-31 00:00:00.0", "kuali.atp.type.Fall", "kuali.atp.state.Official", "Fall Term 1990");
        loadAtp("termRelationTestingAcal1", "testingAcal1", "2000-09-01 00:00:00.0", "2001-06-01 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Draft", "Desc term rich text 3");
        loadAtp("termRelationTestingAcal2", "testingAcal2", "2001-09-01 00:00:00.0", "2002-06-01 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Draft", "Desc term rich text 4");

        loadTerm("termRelationTestingTerm1", "testingTerm1", "2000-09-01 00:00:00.0", "2000-12-31 00:00:00.0", "kuali.atp.type.Fall", "kuali.atp.state.Draft", "Desc term rich text 1");
        loadTerm("termRelationTestingTerm2", "testingTerm2", "2001-01-01 00:00:00.0", "2001-05-31 00:00:00.0", "kuali.atp.type.Spring", "kuali.atp.state.Draft", "Desc term rich text 2");
        loadTerm("termRelationTestingTerm3", "testingTerm3", "2000-09-01 00:00:00.0", "2000-12-31 00:00:00.0", "kuali.atp.type.Fall", "kuali.atp.state.Official", "Desc term rich text 7");
        loadTerm("termRelationTestingTerm4", "testingTerm4", "2011-01-01 00:00:00.0", "2011-05-31 00:00:00.0", "kuali.atp.type.HalfFall1", "kuali.atp.state.Draft", "Desc term rich text 8");
        loadTerm("termRelationTestingTerm5", "testingTerm3", "2000-09-01 00:00:00.0", "2000-12-31 00:00:00.0", "kuali.atp.type.Fall", "kuali.atp.state.Official", "Desc term rich text 10");
        loadTerm("termRelationTestingTerm6", "testingTerm4", "2011-01-01 00:00:00.0", "2011-05-31 00:00:00.0", "kuali.atp.type.HalfFall2", "kuali.atp.state.Draft", "Desc term rich text 11");
        loadTerm("termRelationTestingTermDelete", "testingTermDelete", "2031-01-01 00:00:00.0", "2031-05-31 00:00:00.0", "kuali.atp.type.HalfFall1", "kuali.atp.state.Draft", "Desc term rich text 9");

        loadAtp("testEdgeAtpId1", "testEdgeAtpId1", "1980-06-01 00:00:00.0", "1980-06-30 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2001");
        loadAtp("testEdgeAtpId10", "testEdgeAtpId10", "1981-01-01 00:00:00.0", "1981-01-31 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2010");
        loadAtp("testEdgeAtpId2", "testEdgeAtpId2", "1979-12-01 00:00:00.0", "1981-01-31 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2002");
        loadAtp("testEdgeAtpId3", "testEdgeAtpId3", "1979-12-01 00:00:00.0", "1980-01-31 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2003");
        loadAtp("testEdgeAtpId4", "testEdgeAtpId4", "1980-12-01 00:00:00.0", "1981-01-31 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2004");
        loadAtp("testEdgeAtpId5", "testEdgeAtpId5", "1979-12-01 00:00:00.0", "1980-01-01 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2005");
        loadAtp("testEdgeAtpId6", "testEdgeAtpId6", "1980-12-31 00:00:00.0", "1981-01-31 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2006");
        loadAtp("testEdgeAtpId7", "testEdgeAtpId7", "1980-01-01 00:00:00.0", "1980-06-30 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2007");
        loadAtp("testEdgeAtpId8", "testEdgeAtpId8", "1980-06-01 00:00:00.0", "1980-12-31 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2008");
        loadAtp("testEdgeAtpId9", "testEdgeAtpId9", "1979-12-01 00:00:00.0", "1979-12-31 00:00:00.0", "kuali.atp.type.AcademicCalendar", "kuali.atp.state.Official", "Desc 2009");

        loadMilestone("testId", "testId", "2011-07-10 00:00:00.0", "2011-07-20 00:00:00.0", "kuali.atp.milestone.AdvanceRegistrationPeriod", "kuali.milestone.state.Draft", false, false, true, false, null, "Desc 105");
        loadMilestone("testId2", "testId2", "2011-08-01 00:00:00.0", "2011-10-01 00:00:00.0", "kuali.atp.milestone.RegistrationPeriod", "kuali.milestone.state.Draft", false, false, true, false, null, "Desc 106");
        loadMilestone("testId3", "testId3", "2011-11-01 00:00:00.0", "2011-11-01 00:00:00.0", "kuali.atp.milestone.DropDate", "kuali.milestone.state.Draft", false, true, false, false, null, "Desc 107");
        loadMilestone("testDeleteId", "testDeleteId", "2011-11-01 00:00:00.0", "2011-12-01 00:00:00.0", "kuali.atp.milestone.RegistrationPeriod", "kuali.milestone.state.Draft", false, false, false, false, null, "Desc 108");
        loadMilestone("FALLTERM1990REGISTRATION", "Fall Term Registration Period", "1990-08-01 00:00:00.0", "1990-09-09 00:00:00.0", "kuali.atp.milestone.RegistrationPeriod", "kuali.milestone.state.Official", false, false, true, false, null, "Fall 1990 Registration");
        loadMilestone("FALLFIRSTBLOCK1990COURSESELECTIONEND", "Fall First Block Course Selection Period Ends", "1990-09-10 00:00:00.0", "1990-09-10 00:00:00.0", "kuali.atp.milestone.CourseSelectionPeriodEnd", "kuali.milestone.state.Official", false, false, false, false, null, "Fall First Block Course Selection");
        loadMilestone("FALLFIRSTBLOCK1990DROPDATE", "Drop Deadlin", "1990-09-10 00:00:00.0", "1990-09-10 00:00:00.0", "kuali.atp.milestone.DropDate", "kuali.milestone.state.Official", false, false, false, false, null, "Deadline to Drop Fall First Block Classes");
        loadMilestone("FALLFIRSTBLOCK1990GRADESDUE", "Fall Grades Due", "1990-11-01 00:00:00.0", "1990-11-05 00:00:00.0", "kuali.atp.milestone.GradesDue", "kuali.milestone.state.Official", false, false, true, false, null, "First Block Grades Due");
        loadMilestone("FALLFIRSTBLOCK1990INSTRUCTIONPERIOD", "Fall First Block Instructional Period", "1990-09-03 00:00:00.0", "1990-11-01 00:00:00.0", "kuali.atp.milestone.InstructionalPeriod", "kuali.milestone.state.Official", false, true, true, false, null, "Instruction Period");
        loadMilestone("FALLFIRSTBLOCK1990REGISTRATION", "Fall First Block Registration Period", "1990-08-01 00:00:00.0", "1990-09-09 00:00:00.0", "kuali.atp.milestone.RegistrationPeriod", "kuali.milestone.state.Official", false, false, true, false, null, "Fall First Block 1990 Registration");
        loadMilestone("FALLTERM1990CENSUS", "Fall Term Census", "1990-09-24 00:00:00.0", "1990-09-25 00:00:00.0", "kuali.atp.milestone.FinancialAidCensus", "kuali.milestone.state.Official", false, true, false, true, "FALLTERM1990INSTRUCTIONPERIOD", "Census");
        loadMilestone("FALLTERM1990COMMENCEMENT", "Fall Commencement 1990", "1990-12-15 10:00:00.0", "1990-12-15 11:00:00.0", "kuali.atp.milestone.Commencement", "kuali.milestone.state.Official", false, false, false, false, null, "Commencement");
        loadMilestone("FALLTERM1990COURSESELECTIONEND", "Fall Term Course Selection Period Ends", "1990-09-10 00:00:00.0", "1990-09-10 00:00:00.0", "kuali.atp.milestone.CourseSelectionPeriodEnd", "kuali.milestone.state.Official", false, false, false, false, null, "Fall Course Selection");
        loadMilestone("FALLTERM1990DROPDATE", "Drop Deadlin", "1990-09-10 00:00:00.0", "1990-09-10 00:00:00.0", "kuali.atp.milestone.DropDate", "kuali.milestone.state.Official", false, false, false, false, null, "Deadline to Drop Fall Term Classes");
        loadMilestone("FALLTERM1990FINALS", "Fall Finals", "1990-12-10 00:00:00.0", "1990-12-15 00:00:00.0", "kuali.atp.milestone.FinalExamPeriod", "kuali.milestone.state.Official", false, true, true, false, null, "Fall Finals");
        loadMilestone("FALLTERM1990GRADESDUE", "Fall Grades Due", "1990-12-15 00:00:00.0", "1990-12-22 00:00:00.0", "kuali.atp.milestone.GradesDue", "kuali.milestone.state.Official", false, true, true, false, null, "Grades Due");
        loadMilestone("FALLTERM1990INSTRUCTIONPERIOD", "Fall Term Instructional Period", "1990-09-03 00:00:00.0", "1990-12-01 00:00:00.0", "kuali.atp.milestone.InstructionalPeriod", "kuali.milestone.state.Official", false, true, true, false, null, "Instruction Period");
        loadMilestone("testKeyDate1", "testKeyDate1", "2001-09-10 00:00:00.0", "2001-09-20 00:00:00.0", "kuali.atp.milestone.AdvanceRegistrationPeriod", "kuali.milestone.state.Draft", false, false, true, false, null, "Desc term rich text 5");
        loadMilestone("testKeyDate2", "testKeyDate2", "2001-08-01 00:00:00.0", "2001-10-01 00:00:00.0", "kuali.atp.milestone.RegistrationPeriod", "kuali.milestone.state.Draft", false, false, true, false, null, "Desc term rich text 6");
        loadMilestone("THANKSGIVING1990", "Thanksgiving 1990", "1990-11-21 00:00:00.0", "1990-11-26 00:00:00.0", "kuali.atp.milestone.ThanksgivingBreak", "kuali.milestone.state.Official", false, true, true, false, null, "Thanksgiving Break");

        loadAtpAtpRel("ACADEMICCALENDAR1990CAMPUSCALENDAR19901991RELATION", "1988-01-01 00:00:00.0", null, "kuali.atp.atp.relation.state.active", "ACADEMICCALENDAR1990", "kuali.atp.atp.relation.associated", "CAMPUSCALENDAR19901991");
        loadAtpAtpRel("ACADEMICCALENDAR1990FALLTERM1990RELATION", "1988-01-01 00:00:00.0", null, "kuali.atp.atp.relation.state.active", "ACADEMICCALENDAR1990", "kuali.atp.atp.relation.includes", "FALLTERM1990");
        loadAtpAtpRel("FALLFIRSTBLOCK1990FALLFIRSTBLOCK1990RELATION", "1988-01-01 00:00:00.0", null, "kuali.atp.atp.relation.state.active", "FALLFIRSTBLOCK1990", "kuali.atp.atp.relation.includes", "FALLFIRSTBLOCK1990");
        loadAtpAtpRel("termRelationTestingRel-AcalTerm-1", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.atp.atp.relation.state.active", "termRelationTestingAcal1", "kuali.atp.atp.relation.includes", "termRelationTestingTerm1");
        loadAtpAtpRel("termRelationTestingRel-AcalTerm-2", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.atp.atp.relation.state.active", "termRelationTestingAcal2", "kuali.atp.atp.relation.includes", "termRelationTestingTerm2");
        loadAtpAtpRel("termRelationTestingRel-TermTerm-1", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.atp.atp.relation.state.active", "termRelationTestingTerm1", "kuali.atp.atp.relation.includes", "termRelationTestingTerm2");
        loadAtpAtpRel("termRelationTestingRel-TermTerm-2", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.atp.atp.relation.state.active", "termRelationTestingTerm3", "kuali.atp.atp.relation.includes", "termRelationTestingTerm4");
        loadAtpAtpRel("ATPATPREL-1", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.atp.atp.relation.state.active", "testAtpId1", "kuali.atp.atp.relation.associated", "testAtpId2");
        loadAtpAtpRel("ATPATPREL-2", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.atp.atp.relation.state.active", "testAtpId1", "kuali.atp.atp.relation.includes", "testTermId1");
        loadAtpAtpRel("ATPATPREL-3", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.atp.atp.relation.state.active", "testTermId1", "kuali.atp.atp.relation.includes", "testTermId2");
        loadAtpAtpRel("ATPATPREL-4", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.atp.atp.relation.state.active", "testAtpId1", "kuali.atp.atp.relation.includes", "testDeleteAtpId1");
        loadAtpAtpRel("ATPATPREL-5", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.atp.atp.relation.state.active", "testAtpId1", "kuali.atp.atp.relation.includes", "testDeleteAtpId2");


        addM2A("FALLTERM1990", "FALLTERM1990DROPDATE");
        addM2A("testAtpId1", "testId");
        addM2A("testAtpId2", "testId2");
        addM2A("testDeleteAtpId1", "testId");
        addM2A("testDeleteAtpId2", "testId2");
        addM2A("CAMPUSCALENDAR19901991", "THANKSGIVING1990");
        addM2A("FALLFIRSTBLOCK1990", "FALLFIRSTBLOCK1990COURSESELECTIONEND");
        addM2A("FALLFIRSTBLOCK1990", "FALLFIRSTBLOCK1990DROPDATE");
        addM2A("FALLFIRSTBLOCK1990", "FALLFIRSTBLOCK1990GRADESDUE");
        addM2A("FALLFIRSTBLOCK1990", "FALLFIRSTBLOCK1990INSTRUCTIONPERIOD");
        addM2A("FALLFIRSTBLOCK1990", "FALLFIRSTBLOCK1990REGISTRATION");
        addM2A("FALLTERM1990", "FALLTERM1990CENSUS");
        addM2A("FALLTERM1990", "FALLTERM1990COMMENCEMENT");
        addM2A("FALLTERM1990", "FALLTERM1990COURSESELECTIONEND");
        addM2A("FALLTERM1990", "FALLTERM1990FINALS");
        addM2A("FALLTERM1990", "FALLTERM1990GRADESDUE");
        addM2A("FALLTERM1990", "FALLTERM1990INSTRUCTIONPERIOD");
        addM2A("FALLTERM1990", "FALLTERM1990REGISTRATION");
        addM2A("termRelationTestingTerm1", "testKeyDate1");
    }

    private void loadMilestone(String id,
            String name,
            String startDate,
            String endDate,
            String type,
            String state,
            boolean isInstrctDay,
            boolean isAllDay,
            boolean isDateRange,
            boolean isRelative,
            String relativeAnchorMilestoneId,
            String descrPlain)
            throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        MilestoneInfo info = new MilestoneInfo();
        info.setId(id);
        info.setName(name);
        info.setStartDate(str2Date(startDate, id));
        info.setEndDate(str2Date(endDate, id));
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setIsAllDay(isAllDay);
        info.setIsDateRange(isDateRange);
        info.setIsInstructionalDay(isInstrctDay);
        info.setIsRelative(isRelative);
        info.setRelativeAnchorMilestoneId(relativeAnchorMilestoneId);
        info.setDescr(new RichTextHelper().fromPlain(descrPlain));

        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);
        this.atpService.createMilestone(type, info, context);
    }

    private void addM2A(String atpId,
            String milestoneId)
            throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, DoesNotExistException, AlreadyExistsException {

        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);
        this.atpService.addMilestoneToAtp(milestoneId, atpId, context);
    }

    private void loadAtpAtpRel(String id,
            String effectiveDate,
            String expirationDate,
            String state,
            String atpId,
            String type,
            String relatedAtpId)
            throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, DoesNotExistException {
        AtpAtpRelationInfo info = new AtpAtpRelationInfo();
        info.setId(id);
        info.setEffectiveDate(str2Date(effectiveDate, id));
        info.setExpirationDate(str2Date(expirationDate, id));
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setAtpId(atpId);
        info.setRelatedAtpId(relatedAtpId);

        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);
        this.atpService.createAtpAtpRelation(atpId, relatedAtpId, type, info, context);
    }

    private void updAtpDynamicAttribute(String key, String value, String atpId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        AtpInfo info = atpService.getAtp(atpId, context);
        AttributeInfo attr = new AttributeInfo();
        attr.setKey(key);
        attr.setValue(value);
        info.getAttributes().add(attr);
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);
        info = atpService.updateAtp(atpId, info, context);
    }

    public void loadAtp(String id,
            String name,
            String startDate,
            String endDate,
            String type,
            String state,
            String descrPlain)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException {
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setId(id);
        atpInfo.setName(name);
        atpInfo.setTypeKey(type);
        atpInfo.setStateKey(state);
        atpInfo.setStartDate(str2Date(startDate, id));
        atpInfo.setEndDate(str2Date(endDate, id));
        atpInfo.setDescr(new RichTextHelper().fromPlain(descrPlain));
        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);
        atpService.createAtp(atpInfo.getTypeKey(), atpInfo, context);
    }

    private Date str2Date(String str, String context) {
        if (str == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
        try {
            Date date = df.parse(str);
            return date;
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }

    /**
     * Load terms separately from other ATPs because of ATP code calculation
     *
     * @param id
     * @param name
     * @param startDate
     * @param endDate
     * @param type
     * @param state
     * @param descrPlain
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DataValidationErrorException
     * @throws ReadOnlyException
     * @throws AssemblyException
     */
    public void loadTerm(String id,
                         String name,
                         String startDate,
                         String endDate,
                         String type,
                         String state,
                         String descrPlain)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException {
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setId(id);
        atpInfo.setName(name);
        atpInfo.setTypeKey(type);
        atpInfo.setStateKey(state);
        atpInfo.setStartDate(str2Date(startDate, id));
        atpInfo.setEndDate(str2Date(endDate, id));
        atpInfo.setDescr(new RichTextHelper().fromPlain(descrPlain));
        ContextInfo context = new ContextInfo();

        try {
        TermInfo term = new TermAssembler().assemble(atpInfo, context);
        atpInfo.setCode(TermAssembler.buildAtpCodeForTerm(term));
        }
        catch (AssemblyException e) {
            throw new OperationFailedException("Assembly of TermInfo failed", e);
        }

        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);
        atpService.createAtp(atpInfo.getTypeKey(), atpInfo, context);
    }
}
