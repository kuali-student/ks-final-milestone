package org.kuali.student.enrollment.class2.acal.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.jws.WebParam;

import org.apache.commons.lang.StringUtils;
import org.joda.time.Days;
import org.joda.time.Period;
import org.joda.time.Weeks;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.AcalEventInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.service.assembler.AcademicCalendarAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.AcalEventAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.HolidayAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.HolidayCalendarAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.KeyDateAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.TermAssembler;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.type.service.TypeService;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class AcademicCalendarServiceImpl implements AcademicCalendarService {
    private AtpService atpService;
    private StateService stateService;
    private TypeService typeService;
    private AcademicCalendarAssembler acalAssembler;
    private TermAssembler termAssembler;
    private DataDictionaryService dataDictionaryService;
    private HolidayCalendarAssembler holidayCalendarAssembler;
    private HolidayAssembler holidayAssembler;
    private KeyDateAssembler keyDateAssembler;
    private AcalEventAssembler acalEventAssembler;

    public AcalEventAssembler getAcalEventAssembler() {
        return acalEventAssembler;
    }

    public void setAcalEventAssembler(AcalEventAssembler acalEventAssembler) {
        this.acalEventAssembler = acalEventAssembler;
    }

    public KeyDateAssembler getKeyDateAssembler() {
        return keyDateAssembler;
    }

    public void setKeyDateAssembler(KeyDateAssembler keyDateAssembler) {
        this.keyDateAssembler = keyDateAssembler;
    }

    public HolidayAssembler getHolidayAssembler() {
        return holidayAssembler;
    }

    public void setHolidayAssembler(HolidayAssembler holidayAssembler) {
        this.holidayAssembler = holidayAssembler;
    }

    public HolidayCalendarAssembler getHolidayCalendarAssembler() {
        return holidayCalendarAssembler;
    }

    public void setHolidayCalendarAssembler(HolidayCalendarAssembler holidayCalendarAssembler) {
        this.holidayCalendarAssembler = holidayCalendarAssembler;
    }

    public DataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<TypeInfo>();
    }

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private List<StateInfo> getAtpStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateInfo> results;
        try {
            results = stateService.getStatesByLifecycle(AtpServiceConstants.ATP_PROCESS_KEY, context);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException(AtpServiceConstants.ATP_PROCESS_KEY, ex);
        }

        return results;
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getAtpStates(context);
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(academicCalendarKey, context);
        AcademicCalendarInfo acal;

        try {
            acal = acalAssembler.assemble(atp, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return acal;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByIds(List<String> academicCalendarKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();
        List<AtpInfo> atps = atpService.getAtpsByIds(academicCalendarKeyList, context);
        for (AtpInfo atp : atps) {
            try {
                academicCalendars.add(acalAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        return academicCalendars;
    }

    @Override
    public List<String> getAcademicCalendarIdsByType(String academicCalendarTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByStartYear(Integer year, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        final Date yearBegin, yearEnd;

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, 0, 1);
        yearBegin = cal.getTime(); // XXXX-01-01 00:00:00.000
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.MILLISECOND, -1);
        yearEnd = cal.getTime(); // XXXX-12-31 23:59:59.999

        Set<AtpInfo> atpInfos = new TreeSet<AtpInfo>(new Comparator<AtpInfo>() {
            @Override
            public int compare(AtpInfo atpInfo1, AtpInfo atpInfo2) {
                return atpInfo1.getId().compareTo(atpInfo2.getId());
            }
        });

        atpInfos.addAll(atpService.getAtpsByStartDateRangeAndType(yearBegin, yearEnd, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, context));

        List<AcademicCalendarInfo> acalInfos = new ArrayList<AcademicCalendarInfo>();
        for (AtpInfo atpInfo : atpInfos) {
            try {
                acalInfos.add(acalAssembler.assemble(atpInfo, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        return acalInfos;
    }

    private void processAcalToCcalRelation(String academicCalendarKey, List<String> holidayCalendarIds, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (holidayCalendarIds != null && !holidayCalendarIds.isEmpty()) {
            List<String> validCcalKeys = new ArrayList<String>();
            for (String ccKey : holidayCalendarIds) {
                try {
                    AtpInfo cCal = atpService.getAtp(ccKey, context);
                    if (cCal != null)
                        validCcalKeys.add(ccKey);
                    else
                        throw new OperationFailedException("The HolidayCalendar does not exist. " + ccKey);
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException("The HolidayCalendar Does Not Exist. " + ccKey);
                }
            }

            try {
                disassemble(academicCalendarKey, validCcalKeys, AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY, context);
            } catch (VersionMismatchException e) {
                throw new OperationFailedException();
            }
        }
    }

    @Override
    @Transactional
    public AcademicCalendarInfo createAcademicCalendar(String academicCalendarTypeKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        try {
            AtpInfo toCreate = acalAssembler.disassemble(academicCalendarInfo, context);
            AtpInfo createdAtp = atpService.createAtp(toCreate, context);

            processAcalToCcalRelation(createdAtp.getId(), academicCalendarInfo.getHolidayCalendarIds(), context);
            return acalAssembler.assemble(createdAtp, context);

        } catch (AlreadyExistsException e) {
            throw new OperationFailedException("Error creating academic calendar.", e);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Error creating academic calendar.", e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Error creating academic calendar.", e);
        }

    }

    @Override
    @Transactional
    public AcademicCalendarInfo updateAcademicCalendar(String academicCalendarKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        try {
            AtpInfo existing = atpService.getAtp(academicCalendarKey, context);
            if (existing != null) {
                try {
                    AtpInfo toUpdate = acalAssembler.disassemble(academicCalendarInfo, context);
                    AtpInfo updated = atpService.updateAtp(academicCalendarKey, toUpdate, context);
                    if (updated != null) {
                        processAcalToCcalRelation(academicCalendarKey, academicCalendarInfo.getHolidayCalendarIds(), context);
                        return acalAssembler.assemble(updated, context);
                    } else {
                        throw new OperationFailedException("Failed to update atp for Academic calendar with id = " + academicCalendarKey);
                    }
                } catch (AssemblyException e) {
                    throw new OperationFailedException("AssemblyException : " + e.getMessage());
                } catch (AlreadyExistsException e) {
                    throw new OperationFailedException("Errors in processAcalToCcalRelation. " + e.getMessage());
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException("Failed to update ATP - " + e.getMessage(), e);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException("Failed to update ATP - " + e.getMessage(), e);
                }
            } else {
                throw new DoesNotExistException("The AcademicCalendar is null: " + academicCalendarKey);
            }
        } catch (DoesNotExistException e1) {
            throw new DoesNotExistException("The AcademicCalendar does not exist: " + academicCalendarKey);
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AtpInfo atp = atpService.getAtp(academicCalendarKey, context);

        if (atp != null) {
            // delete atp/acal
            atpService.deleteAtp(academicCalendarKey, context);
        }

        return status;
    }

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method implemented in calculation decorator.");
    }

    @Override
    public HolidayCalendarInfo getHolidayCalendar(String holidayCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(holidayCalendarId, contextInfo);
        HolidayCalendarInfo ccal;
        try {
            ccal = holidayCalendarAssembler.assemble(atp, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return ccal;
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByIds(List<String> holidayCalendarIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<AtpInfo> atps = atpService.getAtpsByIds(holidayCalendarIds, context);
        List<HolidayCalendarInfo> holidayCalendarInfos = new ArrayList<HolidayCalendarInfo>();
        for (AtpInfo atp : atps) {
            try {
                holidayCalendarInfos.add(holidayCalendarAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        return holidayCalendarInfos;
    }

    @Override
    @Transactional
    public HolidayCalendarInfo createHolidayCalendar(String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        AtpInfo atpInfo;
        HolidayCalendarInfo newHolidayCalendar = null;
        try {
            atpInfo = holidayCalendarAssembler.disassemble(holidayCalendarInfo, context);

            atpInfo = atpService.createAtp(atpInfo, context);

            newHolidayCalendar = holidayCalendarAssembler.assemble(atpInfo, context);

        } catch (AssemblyException e) {
            throw new OperationFailedException("Error creating holiday calendar.", e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Error creating holiday calendar.", e);
        }
        return newHolidayCalendar;
    }

    @Override
    public HolidayCalendarInfo copyHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional
    public HolidayCalendarInfo updateHolidayCalendar(String holidayCalendarId, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        try {
            AtpInfo toUpdate = holidayCalendarAssembler.disassemble(holidayCalendarInfo, contextInfo);
            AtpInfo updated = atpService.updateAtp(holidayCalendarId, toUpdate, contextInfo);
            return holidayCalendarAssembler.assemble(updated, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Failed to update ATP - " + e.getMessage(), e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Failed to update ATP - " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteHolidayCalendar(String holidayCalendarId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return atpService.deleteAtp(holidayCalendarId, context);
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        TypeInfo type = typeService.getType(termTypeKey, context);

        if (!checkTypeForTermType(termTypeKey, context)) {
            throw new InvalidParameterException(termTypeKey + " is not a Term type");
        }

        return type;
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<TypeTypeRelationInfo> relations = null;

        try {
            relations = typeService.getTypeTypeRelationsByOwnerType(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e.getMessage(), e);
        }

        if (relations != null) {
            List<TypeInfo> results = new ArrayList<TypeInfo>(relations.size());
            for (TypeTypeRelationInfo rel : relations) {
                try {
                    results.add(typeService.getType(rel.getRelatedTypeKey(), context));
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException(e.getMessage(), e);
                }
            }

            return results;
        }

        return null;
    }

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        TypeInfo acalType = typeService.getType(academicCalendarTypeKey, context);

        return typeService.getAllowedTypesForType(acalType.getKey(), AtpServiceConstants.REF_OBJECT_URI_ATP, context);
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        TypeInfo termType = getTermType(termTypeKey, context);

        return typeService.getAllowedTypesForType(termType.getKey(), AtpServiceConstants.REF_OBJECT_URI_ATP, context);
    }

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        StateInfo termState = stateService.getState(termStateKey, context);

        return termState;
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<StateInfo> results;
        try {
            results = stateService.getStatesByLifecycle(AtpServiceConstants.ATP_PROCESS_KEY, context);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException(AtpServiceConstants.ATP_PROCESS_KEY, ex);
        }

        return results;
    }

    @Override
    public TermInfo getTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(termId, context);
        TermInfo term = null;

        if (atp != null && checkTypeForTermType(atp.getTypeKey(), context))
            try {
                term = termAssembler.assemble(atp, context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        else
            throw new DoesNotExistException("This is either not valid Atp or not valid Term. " + termId);

        return term;
    }

    @Override
    public List<TermInfo> getTermsByIds(List<String> termIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<AtpInfo> results = atpService.getAtpsByIds(termIdList, context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpInfo atp : results) {
            try {
                terms.add(termAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return terms;
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByTypeAndAtp(academicCalendarKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getAtpId().equals(academicCalendarKey)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getRelatedAtpId(), context);

                if (checkTypeForTermType(possibleTerm.getTypeKey(), context)) {
                    try {
                        terms.add(termAssembler.assemble(possibleTerm, context));
                    } catch (AssemblyException e) {
                        throw new OperationFailedException("AssemblyException : " + e.getMessage());
                    }
                }
            }

        }

        return terms;
    }

    @Override
    public List<TermInfo> getCurrentTerms(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<AcademicCalendarInfo> currentACInfos = getAcademicCalendarsByStartYear(new Integer(Calendar.getInstance().get(Calendar.YEAR)), context);
        List<TermInfo> terms = getTermsForAcademicCalendar(currentACInfos.get(0).getId(), context);
        if (terms == null || terms.size() == 0) {
            throw new DoesNotExistException("This academic calendar doesn't contain any terms : " + currentACInfos.get(0).getId());
        }
        return terms;
    }

    @Override
    public List<TermInfo> getIncludedTermsInTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // check for a valid term
        TermInfo parentTerm = getTerm(termId, context);

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByTypeAndAtp(parentTerm.getId(), AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getAtpId().equals(termId)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getRelatedAtpId(), context);

                if (checkTypeForTermType(possibleTerm.getTypeKey(), context)) {
                    try {
                        terms.add(termAssembler.assemble(possibleTerm, context));
                    } catch (AssemblyException e) {
                        throw new OperationFailedException("AssemblyException : " + e.getMessage());
                    }
                }
            }

        }

        return terms;
    }

    @Override
    public List<TermInfo> getContainingTerms(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // check for a valid term
        TermInfo term = getTerm(termId, context);

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtp(term.getId(), context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        // check that the relations we found have the given termId as the
        // "related" atp, and that the owning atp is a term
        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getRelatedAtpId().equals(termId)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getAtpId(), context);

                if (checkTypeForTermType(possibleTerm.getTypeKey(), context)) {
                    try {
                        terms.add(termAssembler.assemble(possibleTerm, context));
                    } catch (AssemblyException e) {
                        throw new OperationFailedException("AssemblyException : " + e.getMessage());
                    }
                }
            }

        }

        return terms;
    }

    @Override
    public List<ValidationResultInfo> validateTerm(String validationTypeKey, String termTypeKey, TermInfo termInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public TermInfo createTerm(String termTypeKey, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpInfo atp;

        if (checkTypeForTermType(termTypeKey, context)) {
            try {
                atp = termAssembler.disassemble(termInfo, context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }

            try {
                AtpInfo newAtp = atpService.createAtp(atp, context);
                termInfo = termAssembler.assemble(newAtp, context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling term", e);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Error assembling term", e);
            }
        } else {
            throw new InvalidParameterException("Term type not found: '" + termTypeKey + "'");
        }

        return termInfo;
    }

    @Override
    @Transactional
    public TermInfo updateTerm(String termId, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {

        AtpInfo atp = atpService.getAtp(termId, context);

        if (atp == null) {
            throw new DoesNotExistException(termId);
        }

        if (!checkTypeForTermType(atp.getTypeKey(), context)) {
            throw new InvalidParameterException("Invalid termId: " + termId + "  Given key does not map to a Term");
        }

        TermInfo updatedTerm;

        try {
            AtpInfo toUpdate = termAssembler.disassemble(termInfo, context);

            AtpInfo updated = atpService.updateAtp(termId, toUpdate, context);

            updatedTerm = termAssembler.assemble(updated, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Error updating ATP - " + e.getMessage(), e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Error updating ATP - " + e.getMessage(), e);
        }

        return updatedTerm;
    }

    @Override
    @Transactional
    public StatusInfo deleteTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        AtpInfo atp = atpService.getAtp(termId, context);

        if (atp == null) {
            throw new DoesNotExistException(termId);
        }

        if (!checkTypeForTermType(atp.getTypeKey(), context)) {
            throw new InvalidParameterException("Invalid termId: " + termId + "  Given key does not map to a Term");
        }

        StatusInfo result = atpService.deleteAtp(termId, context);

        return result;
    }

    @Override
    @Transactional
    public StatusInfo addTermToAcademicCalendar(String academicCalendarKey, String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AtpInfo acal = atpService.getAtp(academicCalendarKey, context);
        if (acal != null) {
            if (isAcademicCalendar(acal.getTypeKey())) {
                AtpInfo term = atpService.getAtp(termId, context);
                if (term != null) {
                    if (checkTypeForTermType(term.getTypeKey(), context)) {
                        if (!termAlreadyExists(academicCalendarKey, termId, context)) {
                            try {
                                createAtpAtpRelations(academicCalendarKey, termId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
                            } catch (DataValidationErrorException e) {
                                status.setSuccess(Boolean.FALSE);
                            }
                        } else
                            throw new AlreadyExistsException("Term with id = " + termId + " already exists.");
                    } else
                        throw new InvalidParameterException("Term with id = " + termId + " has invalid type: " + term.getTypeKey());
                } else
                    throw new DoesNotExistException("Term with id = " + termId + " does not exist.");
            } else
                throw new InvalidParameterException("AcademicCalendar with id = " + academicCalendarKey + " has invalid type: " + acal.getTypeKey());
        } else
            throw new DoesNotExistException("AcademicCalendar with id = " + academicCalendarKey + " does not exist.");

        return status;
    }

    private boolean isAcademicCalendar(String acalType) {
        return null != acalType ? acalType.equals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY) : false;
    }

    private boolean termAlreadyExists(String atpId, String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        boolean found = false;
        List<AtpAtpRelationInfo> atpRels = atpService.getAtpAtpRelationsByTypeAndAtp(atpId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

        if (atpRels != null && !atpRels.isEmpty()) {
            for (AtpAtpRelationInfo atpRelInfo : atpRels) {
                if (atpRelInfo.getAtpId().equals(atpId)) {
                    if (atpRelInfo.getRelatedAtpId().equals(termId)) {
                        found = true;
                        break;
                    }
                }
            }
        }

        return found;
    }

    @Override
    @Transactional
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        AtpInfo acal = atpService.getAtp(academicCalendarKey, context);

        if (acal == null) {
            throw new InvalidParameterException("Invalid academicCalendarKey: " + academicCalendarKey);
        }

        AtpInfo term = atpService.getAtp(termId, context);

        if (term == null) {
            throw new InvalidParameterException("Invalid termId: " + termId);
        }

        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByTypeAndAtp(academicCalendarKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if (relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between academic calendar: " + academicCalendarKey + " and term: " + termId);
        }

        AtpAtpRelationInfo relationToRemove = null;

        for (AtpAtpRelationInfo rel : relations) {
            if (rel.getAtpId().equals(academicCalendarKey)) {
                if (rel.getRelatedAtpId().equals(termId)) {
                    // if the relation represents an "includes" relationship
                    // from the AcademicCalendar to the Term,
                    // then it is the one we need to remove
                    relationToRemove = rel;
                    break;
                }
            }
        }

        if (relationToRemove == null) {
            throw new DoesNotExistException("No relationship exists between academic calendar: " + academicCalendarKey + " and term: " + termId);
        }

        StatusInfo resultStatus = atpService.deleteAtpAtpRelation(relationToRemove.getId(), context);

        return resultStatus;
    }

    @Override
    @Transactional
    public StatusInfo addTermToTerm(String termId, String includedTermId, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        TermInfo term = getTerm(termId, context);

        TermInfo includedTerm = getTerm(includedTermId, context);

        // check if the relationship already exists
        List<TermInfo> terms = getIncludedTermsInTerm(term.getId(), context);
        for (TermInfo t : terms) {
            if (t.getId().equals(includedTerm.getId())) {
                throw new AlreadyExistsException("A relationship already exists exists between term: " + termId + " and included term: " + includedTermId);
            }
        }

        StatusInfo resultStatus = new StatusInfo();

        try {
            createAtpAtpRelations(termId, includedTermId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        } catch (DataValidationErrorException e) {
            resultStatus.setSuccess(false);
            resultStatus.setMessage("Creation of AtpAtpRelation failed due to DataValidationErrorExecption: " + e.getMessage());
        }

        return resultStatus;
    }

    @Override
    @Transactional
    public StatusInfo removeTermFromTerm(String termId, String includedTermId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        try {
            atpService.getAtp(termId, context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Invalid termId: " + termId);
        }

        try {
            atpService.getAtp(includedTermId, context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Invalid includedTermId: " + includedTermId);
        }

        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByTypeAndAtp(termId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if (relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between term: " + termId + " and included term: " + includedTermId);
        }

        AtpAtpRelationInfo relationToRemove = null;

        for (AtpAtpRelationInfo rel : relations) {
            if (rel.getAtpId().equals(termId)) {
                if (rel.getRelatedAtpId().equals(includedTermId)) {
                    // if the relation represents an "includes" relationship
                    // from the Term to the included Term,
                    // then it is the one we need to remove
                    relationToRemove = rel;
                    break;
                }
            }
        }

        if (relationToRemove == null) {
            throw new DoesNotExistException("No relationship exists between term: " + termId + " and included term: " + includedTermId);
        }

        StatusInfo resultStatus = atpService.deleteAtpAtpRelation(relationToRemove.getId(), context);

        return resultStatus;
    }

    @Override
    public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<TypeInfo>();
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MilestoneInfo ms = atpService.getMilestone(keyDateId, context);
        return (null != ms) ? fromMilestoneInfo(ms) : null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByIds(List<String> keyDateIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        AtpInfo termAtp = atpService.getAtp(termId, context);
        if (termAtp == null) {
            throw new DoesNotExistException(termId);
        }

        List<MilestoneInfo> milestones = atpService.getMilestonesForAtp(termId, context);

        if (milestones == null || milestones.isEmpty()) {
            return Collections.emptyList();
        }

        List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>(milestones.size());

        for (MilestoneInfo milestone : milestones) {
            keyDates.add(fromMilestoneInfo(milestone));
        }

        return keyDates;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termId, Date startDate, Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByDatesForAtp(termId, startDate, endDate, context);
        List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            keyDates.add(fromMilestoneInfo(milestoneInfo));
        }
        return keyDates;
    }

    @Override
    public List<KeyDateInfo> getImpactedKeyDates(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<KeyDateInfo> impactedKeyDates = new ArrayList<KeyDateInfo>();
        List<MilestoneInfo> impactedMilestones = atpService.getImpactedMilestones(keyDateId, contextInfo);
        for (MilestoneInfo impactedMilestone : impactedMilestones) {
            impactedKeyDates.add(fromMilestoneInfo(impactedMilestone));
        }
        return impactedKeyDates;
    }

    private MilestoneInfo toMilestoneInfo(KeyDateInfo keyDateInfo) {
        if (keyDateInfo != null) {
            MilestoneInfo msInfo = new MilestoneInfo();
            msInfo.setIsAllDay(keyDateInfo.getIsAllDay());
            msInfo.setAttributes(keyDateInfo.getAttributes());
            msInfo.setIsDateRange(keyDateInfo.getIsDateRange());
            msInfo.setDescr(keyDateInfo.getDescr());
            msInfo.setId(keyDateInfo.getId());
            msInfo.setMeta(keyDateInfo.getMeta());
            msInfo.setName(keyDateInfo.getName());
            msInfo.setStartDate(keyDateInfo.getStartDate());
            msInfo.setEndDate(keyDateInfo.getEndDate());
            msInfo.setStateKey(keyDateInfo.getStateKey());
            msInfo.setTypeKey(keyDateInfo.getTypeKey());

            return msInfo;
        } else
            return null;
    }

    private KeyDateInfo fromMilestoneInfo(MilestoneInfo milestoneInfo) {
        if (milestoneInfo != null) {
            KeyDateInfo keyInfo = new KeyDateInfo();
            keyInfo.setIsAllDay(milestoneInfo.getIsAllDay());
            keyInfo.setAttributes(milestoneInfo.getAttributes());
            keyInfo.setIsDateRange(milestoneInfo.getIsDateRange());
            keyInfo.setDescr(milestoneInfo.getDescr());
            keyInfo.setId(milestoneInfo.getId());
            keyInfo.setMeta(milestoneInfo.getMeta());
            keyInfo.setName(milestoneInfo.getName());
            keyInfo.setStartDate(milestoneInfo.getStartDate());
            keyInfo.setEndDate(milestoneInfo.getEndDate());
            keyInfo.setStateKey(milestoneInfo.getStateKey());
            keyInfo.setTypeKey(milestoneInfo.getTypeKey());
            keyInfo.setIsRelativeToKeyDate(milestoneInfo.getIsRelative());
            keyInfo.setRelativeAnchirKeyDateId(milestoneInfo.getRelativeAnchorMilestoneId());

            return keyInfo;
        } else
            return null;
    }

    @Override
    @Transactional
    public KeyDateInfo updateKeyDate(String keyDateId, KeyDateInfo keyDateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        MilestoneInfo toUpdate = toMilestoneInfo(keyDateInfo);
        MilestoneInfo newMilestone = null;
        try {
            newMilestone = atpService.updateMilestone(keyDateId, toUpdate, context);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Error updating milestone", e);
        }

        return fromMilestoneInfo(newMilestone);
    }

    @Override
    @Transactional
    public StatusInfo deleteKeyDate(String keyDateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.deleteMilestone(keyDateId, context);
    }

    @Override
    public KeyDateInfo calculateKeyDate(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MilestoneInfo milestone = atpService.calculateMilestone(keyDateId, contextInfo);
        return fromMilestoneInfo(milestone);
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
                MissingParameterException, OperationFailedException, PermissionDeniedException {
        TypeInfo type = typeService.getType(holidayTypeKey, context);


        if (!checkTypeForHolidayType(holidayTypeKey, context)) {
            throw new InvalidParameterException(holidayTypeKey + " is not a Holiday type");
        }

        return type;
    }

    @Override
    @Transactional
    public HolidayInfo updateHoliday(String holidayId, HolidayInfo holidayInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        try {
            MilestoneInfo toUpdate = holidayAssembler.disassemble(holidayInfo, context);
            MilestoneInfo updated = null;
            try {
                updated = atpService.updateMilestone(holidayId, toUpdate, context);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Error updating milestone", e);
            }

            return holidayAssembler.assemble(updated, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteHoliday(String holidayId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.deleteMilestone(holidayId, context);
    }

    @Override
    public HolidayInfo calculateHoliday(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Sambit - THIS METHOD NEEDS JAVADOCS
        return getHoliday(holidayId, contextInfo);
    }

    /*
     * reg date groups removed from service 12/16
     * @Override public RegistrationDateGroupInfo
     * getRegistrationDateGroup(String termId, ContextInfo context) throws
     * DoesNotExistException, InvalidParameterException,
     * MissingParameterException, OperationFailedException,
     * PermissionDeniedException { RegistrationDateGroupInfo regDateGroup = new
     * RegistrationDateGroupInfo(); regDateGroup.setTermId(termId);
     * regDateGroup.setRegistrationDateDerivationGroup(new
     * RegistrationDateDerivationGroupInfo()); List<KeyDateInfo> keyDates =
     * getKeyDatesForTerm(termId, context); Map<String, KeyDateInfo> keyDateMap
     * = new HashMap<String, KeyDateInfo>(); for (KeyDateInfo keyDate :
     * keyDates) { keyDateMap.put(keyDate.getTypeKey(), keyDate); }
     * populateRegistrationDateGroup(regDateGroup, keyDateMap); return
     * regDateGroup; } private void
     * populateRegistrationDateGroup(RegistrationDateGroupInfo
     * registrationDateGroup, Map<String, KeyDateInfo> keyDates) { KeyDateInfo
     * registrationPeriod =
     * keyDates.get(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
     * registrationDateGroup
     * .setRegistrationDateRange(getDateRangeFromKeyDate(registrationPeriod));
     * registrationDateGroup.setAddDate((registrationPeriod != null) ?
     * registrationPeriod.getStartDate() : null); KeyDateInfo
     * instructionalPeriod =
     * keyDates.get(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY
     * ); registrationDateGroup.setClassDateRange(getDateRangeFromKeyDate(
     * instructionalPeriod)); KeyDateInfo dropDate =
     * keyDates.get(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY);
     * registrationDateGroup.setDropDate((dropDate != null) ?
     * dropDate.getEndDate() : null); KeyDateInfo finalExamPeriod =
     * keyDates.get(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY);
     * registrationDateGroup
     * .setFinalExamDateRange(getDateRangeFromKeyDate(finalExamPeriod));
     * KeyDateInfo gradingPeriod =
     * keyDates.get(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
     * registrationDateGroup
     * .setGradingDateRange(getDateRangeFromKeyDate(gradingPeriod)); }
     */

    private DateRangeInfo getDateRangeFromKeyDate(KeyDateInfo keyDate) {
        DateRangeInfo dateRange = null;
        if (keyDate != null) {
            dateRange = new DateRangeInfo();
            dateRange.setStart(keyDate.getStartDate());
            dateRange.setEnd(keyDate.getEndDate());
        }
        return dateRange;
    }

    private KeyDateInfo getKeyDatePrepairedFromDateRange(KeyDateInfo keyDate, DateRangeInfo dateRange) {
        if (null == dateRange) {
            return null;
        }
        if (keyDate == null) {
            keyDate = new KeyDateInfo();
        }
        keyDate.setStartDate(dateRange.getStart());
        keyDate.setEndDate(dateRange.getEnd());
        keyDate.setIsDateRange(Boolean.TRUE);
        return keyDate;
    }

    private KeyDateInfo getKeyDatePrepairedFromDate(KeyDateInfo keyDate, Date date) {
        if (null == date) {
            return null;
        }
        if (keyDate == null) {
            keyDate = new KeyDateInfo();
        }
        keyDate.setStartDate(date);
        keyDate.setEndDate(date);
        keyDate.setIsAllDay(Boolean.FALSE);
        keyDate.setIsDateRange(Boolean.FALSE);
        return keyDate;
    }

    /*
     * reg date groups removed from service
     * @Override public RegistrationDateGroupInfo
     * updateRegistrationDateGroup(String termId, RegistrationDateGroupInfo
     * registrationDateGroupInfo, ContextInfo context) throws
     * DataValidationErrorException, DoesNotExistException,
     * InvalidParameterException, MissingParameterException,
     * OperationFailedException, PermissionDeniedException,
     * VersionMismatchException { // TODO needs to be move to validation layer?
     * Date addDate = registrationDateGroupInfo.getAddDate(); DateRangeInfo
     * registrationDateRange =
     * registrationDateGroupInfo.getRegistrationDateRange(); if (null != addDate
     * && null != registrationDateRange &&
     * !addDate.equals(registrationDateRange.getStart())) { throw new
     * DataValidationErrorException
     * ("Add date is not the same as the start of registraion period."); }
     * List<KeyDateInfo> termIdDates = getKeyDatesForTerm(termId, context);
     * Map<String, KeyDateInfo> termIdDateByType = new HashMap<String,
     * KeyDateInfo>(); for (KeyDateInfo keyDate : termIdDates) {
     * termIdDateByType.put(keyDate.getTypeKey(), keyDate); } Map<String,
     * KeyDateInfo> registrationDatesGroupKeyDates = new HashMap<String,
     * KeyDateInfo>(); KeyDateInfo existingRegistrationPeriodKeyDate =
     * termIdDateByType
     * .get(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
     * KeyDateInfo updatedRegistrationPeriodDate =
     * getKeyDatePrepairedFromDateRange(existingRegistrationPeriodKeyDate,
     * registrationDateGroupInfo.getRegistrationDateRange()); // TODO // change
     * // namee // to // update updateRegistrationDateGroupKeyDate(termId,
     * existingRegistrationPeriodKeyDate, updatedRegistrationPeriodDate,
     * AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, context);
     * registrationDatesGroupKeyDates
     * .put(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY,
     * updatedRegistrationPeriodDate); KeyDateInfo
     * existingInstructionalPeriodKeyDate =
     * termIdDateByType.get(AtpServiceConstants
     * .MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY); KeyDateInfo
     * updatedInstructionalPeriodDate =
     * getKeyDatePrepairedFromDateRange(existingInstructionalPeriodKeyDate,
     * registrationDateGroupInfo.getClassDateRange());
     * updateRegistrationDateGroupKeyDate(termId,
     * existingInstructionalPeriodKeyDate, updatedInstructionalPeriodDate,
     * AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, context);
     * registrationDatesGroupKeyDates
     * .put(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY,
     * updatedInstructionalPeriodDate); KeyDateInfo existingDropKeyDate =
     * termIdDateByType.get(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY);
     * KeyDateInfo updatedDropDate =
     * getKeyDatePrepairedFromDate(existingDropKeyDate,
     * registrationDateGroupInfo.getDropDate());
     * updateRegistrationDateGroupKeyDate(termId, existingDropKeyDate,
     * updatedDropDate, AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY,
     * context); registrationDatesGroupKeyDates.put(AtpServiceConstants.
     * MILESTONE_DROP_DATE_TYPE_KEY, updatedDropDate); KeyDateInfo
     * existingFinalExamKeyDate =
     * termIdDateByType.get(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY
     * ); KeyDateInfo updatedFinalExamDate =
     * getKeyDatePrepairedFromDateRange(existingFinalExamKeyDate,
     * registrationDateGroupInfo.getFinalExamDateRange());
     * updateRegistrationDateGroupKeyDate(termId, existingFinalExamKeyDate,
     * updatedFinalExamDate,
     * AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, context);
     * registrationDatesGroupKeyDates
     * .put(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY,
     * updatedFinalExamDate); KeyDateInfo existingGradingKeyDate =
     * termIdDateByType.get(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
     * KeyDateInfo updatedGradingKeyDate =
     * getKeyDatePrepairedFromDateRange(existingGradingKeyDate,
     * registrationDateGroupInfo.getGradingDateRange());
     * updateRegistrationDateGroupKeyDate(termId, existingGradingKeyDate,
     * updatedGradingKeyDate, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY,
     * context); registrationDatesGroupKeyDates.put(AtpServiceConstants.
     * MILESTONE_GRADES_DUE_TYPE_KEY, updatedGradingKeyDate);
     * populateRegistrationDateGroup(registrationDateGroupInfo,
     * registrationDatesGroupKeyDates); return registrationDateGroupInfo; }
     * private void updateRegistrationDateGroupKeyDate(String termId,
     * KeyDateInfo existingKeyDate, KeyDateInfo updatedKeyDate, String typeKey,
     * ContextInfo context) throws InvalidParameterException,
     * DataValidationErrorException, MissingParameterException,
     * DoesNotExistException, VersionMismatchException,
     * PermissionDeniedException, OperationFailedException { if (null !=
     * updatedKeyDate && null != existingKeyDate) { // update date
     * updatedKeyDate.setId(existingKeyDate.getId());
     * updatedKeyDate.setTypeKey(existingKeyDate.getTypeKey());
     * updatedKeyDate.setStateKey(existingKeyDate.getStateKey());
     * updateKeyDate(existingKeyDate.getId(), updatedKeyDate, context); } else
     * if (null != updatedKeyDate && null == existingKeyDate) { // add date
     * updatedKeyDate.setId(typeKey + "." + termId + "." +
     * RandomStringUtils.randomAlphanumeric(4)); // TODO // properly // generate
     * // new // key updatedKeyDate.setTypeKey(typeKey);
     * updatedKeyDate.setStateKey
     * (AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY); try {
     * createKeyDate(termId, updatedKeyDate.getId(), updatedKeyDate, context); }
     * catch (ReadOnlyException e) { throw new OperationFailedException(
     * "Could not create KeyDate for Term - ReadOnlyException" + termId + "'-'"
     * + updatedKeyDate.getId() + "'", e); } } else if (null == updatedKeyDate
     * && null != existingKeyDate) {
     * atpService.deleteMilestone(existingKeyDate.getId(), context); } }
     */

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public AcademicCalendarAssembler getAcalAssembler() {
        return acalAssembler;
    }

    public void setAcalAssembler(AcademicCalendarAssembler acalAssembler) {
        this.acalAssembler = acalAssembler;
    }

    public TermAssembler getTermAssembler() {
        return termAssembler;
    }

    public void setTermAssembler(TermAssembler termAssembler) {
        this.termAssembler = termAssembler;
    }

    @Override
    public List<AcademicCalendarInfo> searchForAcademicCalendars(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        
        List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();
        List<AtpInfo> atps = atpService.searchForAtps(criteria, context);
        
        for (AtpInfo atp : atps) {
            try {
                academicCalendars.add(acalAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        
        return academicCalendars;
    }

    @Override
    public List<TermInfo> searchForTerms(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        
        List<AtpInfo> results = atpService.searchForAtps(criteria, context);
        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpInfo atp : results) {
            try {
                terms.add(termAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return terms;
    }

    @Override
    public List<KeyDateInfo> searchForKeyDates(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<HolidayInfo> searchForHolidays(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        
        List<MilestoneInfo> milestoneInfos = atpService.searchForMilestones(criteria, context);
        List<HolidayInfo> holidayInfos = new ArrayList<HolidayInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                holidayInfos.add(holidayAssembler.assemble(milestoneInfo, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling holiday with Id " + milestoneInfo.getId(), e);
            }
        }
        
        return holidayInfos;
    }

    private boolean checkTypeForTermType(String typeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> types = getTermTypes(context);
        return checkTypeInTypes(typeKey, types);
    }

    private boolean checkTypeForHolidayType(String typeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> types = getHolidayTypes(context);
        return checkTypeInTypes(typeKey, types);
    }

    private boolean checkTypeInTypes(String typeKey, List<TypeInfo> types){
        if(types != null && !types.isEmpty()){
            for (TypeInfo type : types) {
                if (type.getKey().equals(typeKey)) {
                    return true;
                }
            }
        }

        return false;
    }

    public StatusInfo disassemble(String atpId, List<String> relatedAtpIds, String relatedAtpType, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        disassembleAtpAtpRelations(atpId, relatedAtpIds, relatedAtpType, context);

        return status;
    }

    private void disassembleAtpAtpRelations(String atpId, List<String> relatedAtpIds, String relatedAtpType, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        try {
            List<AtpAtpRelationInfo> atpRels = atpService.getAtpAtpRelationsByAtp(atpId, context);
            Map<String, String> currentRelIds = new HashMap<String, String>();

            if (atpRels != null && !atpRels.isEmpty()) {
                for (AtpAtpRelationInfo atpRelInfo : atpRels) {
                    if (atpRelInfo.getAtpId().equals(atpId)) {
                        if (AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY.equals(atpRelInfo.getTypeKey())) {
                            AtpInfo thisAtp = atpService.getAtp(atpRelInfo.getRelatedAtpId(), context);
                            if (thisAtp != null && thisAtp.getTypeKey().equals(relatedAtpType))
                                currentRelIds.put(atpRelInfo.getRelatedAtpId(), atpRelInfo.getId());

                        }
                    }
                }
            }

            for (String relatedKey : relatedAtpIds) {
                if (!currentRelIds.containsKey(relatedKey))
                    createAtpAtpRelations(atpId, relatedKey, AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, context);
                else
                    updateAtpAtpRelations(currentRelIds.get(relatedKey), context);
            }

        } catch (DoesNotExistException e) {
            // if not exist, create relations
            for (String relatedKey : relatedAtpIds) {
                createAtpAtpRelations(atpId, relatedKey, AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, context);
            }
        }
    }

    private void createAtpAtpRelations(String atpId, String relatedAtpId, String relationType, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpId(atpId);
        atpRel.setRelatedAtpId(relatedAtpId);
        atpRel.setTypeKey(relationType);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());
        try {
            atpService.createAtpAtpRelation(atpId, relatedAtpId, atpRel, context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Error creating atp-atp relation", e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Error creating atp-atp relation", e);
        }
    }

    private void updateAtpAtpRelations(String atpAtpRelationId, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
        AtpAtpRelationInfo atpRel;
        try {
            atpRel = new AtpAtpRelationInfo(atpService.getAtpAtpRelation(atpAtpRelationId, context));
            // TODO:what to update? should state same as atpRel.atp?
            // atpRel.setStateKey(state);
            try {
                atpService.updateAtpAtpRelation(atpAtpRelationId, atpRel, context);
            } catch (DoesNotExistException e) {
                throw new DoesNotExistException(atpAtpRelationId);
            } catch (VersionMismatchException e) {
                throw new VersionMismatchException(atpAtpRelationId);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Error upating ATP-ATP relation", e);
            }
        } catch (DoesNotExistException e1) {
            throw new DoesNotExistException(atpAtpRelationId);
        }
    }

    @Override
    public List<String> searchForAcademicCalendarIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(String validationTypeKey, String academicCalendarTypeKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getHolidayCalendarType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        TypeInfo type;
        try{
            type = typeService.getType(holidayCalendarTypeKey, contextInfo);
        }catch (PermissionDeniedException ex){
            throw new OperationFailedException("Permission Denied");
        }

        return type;

    }

    @Override
    public List<TypeInfo> getHolidayCalendarTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getHolidayCalendarState(String holidayCalendarStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException,
            OperationFailedException {
        StateInfo hcState = stateService.getState(holidayCalendarStateKey, contextInfo);

        return hcState;
    }

    @Override
    public List<StateInfo> getHolidayCalendarStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getAtpStates(contextInfo);
    }

    @Override
    public List<String> getHolidayCalendarIdsByType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByStartYear(Integer year, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        //return null;
        final Date yearBegin, yearEnd;

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, 0, 1);
        yearBegin = cal.getTime(); // XXXX-01-01 00:00:00.000
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.MILLISECOND, -1);
        yearEnd = cal.getTime(); // XXXX-12-31 23:59:59.999

        Set<AtpInfo> atpInfos = new TreeSet<AtpInfo>(new Comparator<AtpInfo>() {
            @Override
            public int compare(AtpInfo atpInfo1, AtpInfo atpInfo2) {
                return atpInfo1.getId().compareTo(atpInfo2.getId());
            }
        });

        atpInfos.addAll(atpService.getAtpsByStartDateRangeAndType(yearBegin, yearEnd, AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY, contextInfo));

        List<HolidayCalendarInfo> hcalInfos = new ArrayList<HolidayCalendarInfo>();
        for (AtpInfo atpInfo : atpInfos) {
            try {
                hcalInfos.add(holidayCalendarAssembler.assemble(atpInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        return hcalInfos;
    }

    @Override
    public List<String> searchForHolidayCalendarIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayCalendarInfo> searchForHolidayCalendars(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<HolidayCalendarInfo> holidayCalendars = new ArrayList<HolidayCalendarInfo>();
        List<AtpInfo> atps = atpService.searchForAtps(criteria, contextInfo);

        for (AtpInfo atp : atps) {
            try {
                holidayCalendars.add(holidayCalendarAssembler.assemble(atp, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return holidayCalendars;
    }

    @Override
    public List<ValidationResultInfo> validateHolidayCalendar(String validationTypeKey, String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getTermIdsByType(String termTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO validate type key is a valid term type
        return atpService.getAtpIdsByType(termTypeKey, contextInfo);
    }

    @Override
    public List<TermInfo> getTermsByCode(String code, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForTermIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getKeyDateTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getKeyDateState(String keyDateStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getKeyDateStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getKeyDateIdsByType(String keyDateTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForKeyDateIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationTypeKey, String termId, String keyDateTypeKey, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public KeyDateInfo createKeyDate(String termId, String keyDateTypeKey, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        KeyDateInfo newKeyDateInfo = null;
        MilestoneInfo milestoneInfo = null;

        try {
            milestoneInfo = keyDateAssembler.disassemble(keyDateInfo, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException in disassembling: " + e.getMessage());
        }

        if (milestoneInfo != null) {
            if (StringUtils.isBlank(milestoneInfo.getTypeKey())) {
                milestoneInfo.setTypeKey(keyDateTypeKey);
            }
            MilestoneInfo newMilestone = null;
            try {
                newMilestone = atpService.createMilestone(milestoneInfo, contextInfo);
                newKeyDateInfo = keyDateAssembler.assemble(newMilestone, contextInfo);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Error creating milestone", e);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException in assembling: " + e.getMessage());
            }

            try {
                atpService.addMilestoneToAtp(newMilestone.getId(), termId, contextInfo);
            } catch (AlreadyExistsException e) {
                throw new OperationFailedException("Error creating ATP-Milestone relation", e);
            }

        }

        return newKeyDateInfo;
    }

    @Override
    public TypeInfo getAcalEventType(String acalEventTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAcalEventTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        List<TypeTypeRelationInfo> relations = null;

        try {
            //MILESTONE_EVENT_GROUPING_TYPE_KEY = "kuali.milestone.type.group.event";
            //TYPE_TYPE_RELATION_GROUP_TYPE_KEY = "kuali.type.type.relation.type.group";
            relations = typeService.getTypeTypeRelationsByOwnerType(AtpServiceConstants.MILESTONE_EVENT_GROUPING_TYPE_KEY,
                    TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, contextInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e.getMessage(), e);
        }

        if (relations != null) {
            List<TypeInfo> results = new ArrayList<TypeInfo>(relations.size());
            for (TypeTypeRelationInfo rel : relations) {
                try {
                    results.add(typeService.getType(rel.getRelatedTypeKey(), contextInfo));
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException(e.getMessage(), e);
                }
            }

            return results;
        }

        return null;
    }

    @Override
    public List<TypeInfo> getAcalEventTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getAcalEventState(String acalEventStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getAcalEventStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AcalEventInfo getAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MilestoneInfo milestoneInfo = atpService.getMilestone(acalEventId, contextInfo);

        if (milestoneInfo == null) {
            throw new DoesNotExistException(acalEventId);
        }

        try {
            return acalEventAssembler.assemble(milestoneInfo, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Error assembling AcalEvent", e);
        }
    }

    @Override
    public List<AcalEventInfo> getAcalEventsByIds(List<String> acalEventIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByIds(acalEventIds, contextInfo);
        List<AcalEventInfo> acalEventInfos = new ArrayList<AcalEventInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                acalEventInfos.add(acalEventAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling AcalEvent", e);
            }
        }

        return acalEventInfos;
    }

    @Override
    public List<String> getAcalEventIdsByType(String acalEventTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.getMilestoneIdsByType(acalEventTypeKey, contextInfo);
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendar(String academicCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesForAtp(academicCalendarId, contextInfo);
        List<AcalEventInfo> acalEventInfos = new ArrayList<AcalEventInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                acalEventInfos.add(acalEventAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling AcalEvent", e);
            }
        }

        return acalEventInfos;
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendarByDate(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByDatesForAtp(academicCalendarId, startDate, endDate, contextInfo);
        List<AcalEventInfo> acalEventInfos = new ArrayList<AcalEventInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                acalEventInfos.add(acalEventAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling AcalEvent", e);
            }
        }

        return acalEventInfos;
    }

    @Override
    public List<AcalEventInfo> getImpactedAcalEvents(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<AcalEventInfo>();
    }

    @Override
    public List<String> searchForAcalEventIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<AcalEventInfo> searchForAcalEvents(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        
        List<MilestoneInfo> milestoneInfos = atpService.searchForMilestones(criteria, contextInfo);
        List<AcalEventInfo> acalEventInfos = new ArrayList<AcalEventInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                acalEventInfos.add(acalEventAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling AcalEvent", e);
            }
        }
        
        return acalEventInfos;
    }

    @Override
    public List<ValidationResultInfo> validateAcalEvent(String validationTypeKey, String termId, String acalEventTypeKey, AcalEventInfo acalEventInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public AcalEventInfo createAcalEvent(String academicCalendarId, String acalEventTypeKey, AcalEventInfo acalEventInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        try {
            MilestoneInfo milestoneInfo = acalEventAssembler.disassemble(acalEventInfo, contextInfo);
            if (StringUtils.isBlank(milestoneInfo.getTypeKey())) {
                milestoneInfo.setTypeKey(acalEventTypeKey);
            }

            MilestoneInfo newMilestone = atpService.createMilestone(milestoneInfo, contextInfo);

            atpService.addMilestoneToAtp(newMilestone.getId(), academicCalendarId, contextInfo);
            return acalEventAssembler.assemble(newMilestone, contextInfo);

        } catch (AssemblyException e) {
            throw new OperationFailedException("Error disassembling AcalEvent", e);
        } catch (AlreadyExistsException e) {
            throw new OperationFailedException("Error associating AcalEvent with AcademicCalendar", e);
        }

    }

    @Override
    @Transactional
    public AcalEventInfo updateAcalEvent(String acalEventId, AcalEventInfo acalEventInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        MilestoneInfo existingMilestone = atpService.getMilestone(acalEventId, contextInfo);

        if (existingMilestone == null) {
            throw new DoesNotExistException("AcalEvent doesnt exists " + acalEventId);
        }

        try {
            MilestoneInfo milestoneInfo = acalEventAssembler.disassemble(acalEventInfo, contextInfo);
            MilestoneInfo newMilestone = atpService.updateMilestone(acalEventId, milestoneInfo, contextInfo);
            return acalEventAssembler.assemble(newMilestone, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Error assembling AcalEvent", e);
        }

    }

    @Override
    @Transactional
    public StatusInfo deleteAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MilestoneInfo existingMilestone = atpService.getMilestone(acalEventId, contextInfo);

        if (existingMilestone == null) {
            throw new DoesNotExistException("AcalEvent doesnt exists " + acalEventId);
        }

        StatusInfo status = atpService.deleteMilestone(acalEventId, contextInfo);
        return status;

    }

    @Override
    public AcalEventInfo calculateAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Sambit - THIS METHOD NEEDS JAVADOCS
        return getAcalEvent(acalEventId, contextInfo);
    }

    @Override
    public List<TypeInfo> getHolidayTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<TypeTypeRelationInfo> relations = null;

        try {
            relations = typeService.getTypeTypeRelationsByOwnerType(AtpServiceConstants.MILESTONE_HOLIDAY_GROUPING_TYPE_KEY,
                            TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, contextInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e.getMessage(), e);
        }

        if (relations != null) {
            List<TypeInfo> results = new ArrayList<TypeInfo>(relations.size());
            for (TypeTypeRelationInfo rel : relations) {
                try {
                    results.add(typeService.getType(rel.getRelatedTypeKey(), contextInfo));
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException(e.getMessage(), e);
                }
            }

            return results;
        }

        return null;
    }


    @Override
    public List<TypeInfo> getHolidayTypesForHolidayCalendarType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getHolidayState(String holidayStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StateInfo holidayState = stateService.getState(holidayStateKey, contextInfo);

        return holidayState;
    }

    @Override
    public List<StateInfo> getHolidayStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateInfo> results;
        try {
            results = stateService.getStatesByLifecycle(AtpServiceConstants.MILESTONE_PROCESS_KEY, contextInfo);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException(AtpServiceConstants.MILESTONE_PROCESS_KEY, ex);
        }

        return results;

    }

    @Override
    public HolidayInfo getHoliday(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        MilestoneInfo milestoneInfo = atpService.getMilestone(holidayId, contextInfo);

        if (milestoneInfo == null) {
            throw new DoesNotExistException(holidayId);
        }

        try {
            return holidayAssembler.assemble(milestoneInfo, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Error assembling Holiday", e);
        }
    }

    @Override
    public List<HolidayInfo> getHolidaysByIds(List<String> holidayIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByIds(holidayIds, contextInfo);

        List<HolidayInfo> holidayInfos = new ArrayList<HolidayInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                holidayInfos.add(holidayAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling holiday with Id " + milestoneInfo.getId(), e);
            }
        }

        return holidayInfos;
    }

    @Override
    public List<String> getHolidayIdsByType(String holidayTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.getMilestoneIdsByType(holidayTypeKey, contextInfo);
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendar(String holidayCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesForAtp(holidayCalendarId, contextInfo);
        List<HolidayInfo> holidayInfos = new ArrayList<HolidayInfo>();
        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                holidayInfos.add(holidayAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling holiday" + milestoneInfo.getId(), e);
            }
        }

        return holidayInfos;
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendarByDate(String holidayCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByDatesForAtp(holidayCalendarId, startDate, endDate, contextInfo);
        List<HolidayInfo> holidayInfos = new ArrayList<HolidayInfo>();
        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                holidayInfos.add(holidayAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling holiday" + milestoneInfo.getId(), e);
            }
        }

        return holidayInfos;
    }

    @Override
    public List<HolidayInfo> getImpactedHolidays(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<HolidayInfo>();
    }

    @Override
    public List<String> searchForHolidayIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationTypeKey, String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        HolidayInfo newHolidayInfo = null;
        MilestoneInfo milestoneInfo = null;

        try {
            milestoneInfo = holidayAssembler.disassemble(holidayInfo, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException in disassembling: " + e.getMessage());
        }

        if (milestoneInfo != null) {
            if (StringUtils.isBlank(milestoneInfo.getTypeKey())) {
                milestoneInfo.setTypeKey(holidayTypeKey);
            }
            MilestoneInfo newMilestone = null;
            try {
                newMilestone = atpService.createMilestone(milestoneInfo, contextInfo);
                newHolidayInfo = holidayAssembler.assemble(newMilestone, contextInfo);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Error creating milestone", e);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException in assembling: " + e.getMessage());
            }

            try {
                atpService.addMilestoneToAtp(newMilestone.getId(), holidayCalendarId, contextInfo);
            } catch (AlreadyExistsException e) {
                throw new OperationFailedException("Error creating ATP-Milestone relation", e);
            }

        }

        return newHolidayInfo;
    }

    @Override
    public String getAcademicCalendarData(String academicCalendarId, String calendarDataFormatTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        KeyDateInfo instructionalPeriodKeyDate = null;

        List<KeyDateInfo> keyDates = getKeyDatesForTerm(termId, contextInfo);

        for (KeyDateInfo keyDate : keyDates) {
            if (keyDate.getTypeKey().equals(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY)) {

                instructionalPeriodKeyDate = new KeyDateInfo(keyDate);

            }

        }

        Period instructionalPeriodInterval = new Period(instructionalPeriodKeyDate.getStartDate().getTime(), instructionalPeriodKeyDate.getEndDate().getTime());

        Days totalDays = instructionalPeriodInterval.toStandardDays();

        Weeks weeks = instructionalPeriodInterval.toStandardWeeks();

        int approxWeekends = weeks.getWeeks() * 2;

        int totalDaysInTerm = totalDays.getDays();

        return totalDaysInTerm - (approxWeekends + getNumberOfHolidayDatesInTerm(termId, instructionalPeriodKeyDate, contextInfo));

    }

    private Integer getNumberOfHolidayDatesInTerm(String termId, KeyDateInfo instructionalPeriodKeyDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AcademicCalendarInfo> acalsForTerm = getAcademicCalendarsForTerm(termId, contextInfo);
        int numberOfHolidayDays = 0;
        for (AcademicCalendarInfo acal : acalsForTerm) {

            List<HolidayInfo> holidays = getHolidaysByDateForAcademicCalendar(acal.getId(), instructionalPeriodKeyDate.getStartDate(), instructionalPeriodKeyDate.getEndDate(), contextInfo);

            for (HolidayInfo holiday : holidays) {

                Period holidayPeriod = new Period(holiday.getStartDate().getTime(), holiday.getEndDate().getTime());

                numberOfHolidayDays = numberOfHolidayDays + holidayPeriod.toStandardDays().getDays();

            }
        }
        return numberOfHolidayDays;

    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsForTerm(String termId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {

        List<AtpAtpRelationInfo> atpAtpRelationsForTerm = this.atpService.getAtpAtpRelationsByAtps(termId, contextInfo);

        List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();

        for (AtpAtpRelationInfo atpRelationForTerm : atpAtpRelationsForTerm) {
            if (atpRelationForTerm.getTypeKey().equals(AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY)) {
                try {
                    AtpInfo acalAtp = this.atpService.getAtp(atpRelationForTerm.getAtpId(), contextInfo);
                    if (acalAtp.getTypeKey().equals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY))
                        academicCalendars.add(this.acalAssembler.assemble(acalAtp, contextInfo));
                } catch (AssemblyException e) {
                    throw new OperationFailedException(e.getMessage());
                }
            }
        }

        return academicCalendars;
    }

    @Override
    public List<HolidayInfo> getHolidaysByDateForAcademicCalendar(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AcademicCalendarInfo acInfo = getAcademicCalendar(academicCalendarId, contextInfo);
        List<HolidayInfo> holidaysForAcal = new ArrayList<HolidayInfo>();
        for (String holidayCalendarId : acInfo.getHolidayCalendarIds()) {

            List<HolidayInfo> holidays = getHolidaysForHolidayCalendar(holidayCalendarId, contextInfo);

            for (HolidayInfo holiday : holidays) {
                if (holiday.getStartDate().after(startDate) && holiday.getEndDate().before(endDate)) {
                    holidaysForAcal.add(holiday);
                }
            }

        }

        return holidaysForAcal;
    }

}
