package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.dto.*;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.service.assembler.AcademicCalendarAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.HolidayCalendarAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.HolidayAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.TermAssembler;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.jws.WebParam;

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class AcademicCalendarServiceImpl implements AcademicCalendarService {
    private AtpService atpService;
    private AcademicCalendarAssembler acalAssembler;
    private TermAssembler termAssembler;
    private DataDictionaryService dataDictionaryService;
    private HolidayCalendarAssembler holidayCalendarAssembler;
    private HolidayAssembler holidayAssembler;

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

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return dataDictionaryService.getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        return dataDictionaryService.getDataDictionaryEntry(entryKey, context);
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

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<StateInfo>();
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

    private void processAcalToCcalRelation(String academicCalendarKey, List<String> holidayCalendarKeys, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (holidayCalendarKeys != null && !holidayCalendarKeys.isEmpty()) {
            List<String> validCcalKeys = new ArrayList<String>();
            for (String ccKey : holidayCalendarKeys) {
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
            AtpInfo createdAtp = atpService.createAtp(academicCalendarTypeKey, toCreate, context);

            processAcalToCcalRelation(createdAtp.getId(), academicCalendarInfo.getHolidayCalendarIds(), context);
            return acalAssembler.assemble(createdAtp, context);

        } catch (AlreadyExistsException e) {
            throw new OperationFailedException(e.getMessage());
        } catch (AssemblyException ex) {
            throw new OperationFailedException(ex.getMessage());
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
    /*
     * TODO - Rewrite this method completely after copy logic
     */
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarId, Integer startYear, Integer endYear, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        AcademicCalendarInfo templateAcademicCalendar = getAcademicCalendar(academicCalendarId, contextInfo);
        AcademicCalendarInfo academicCalendar = new AcademicCalendarInfo(templateAcademicCalendar);

        academicCalendar.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        academicCalendar.setName(templateAcademicCalendar.getName());
        academicCalendar.setDescr(new RichTextInfo(templateAcademicCalendar.getDescr()));
        academicCalendar.setTypeKey(templateAcademicCalendar.getTypeKey());

        try {
            academicCalendar = createAcademicCalendar(academicCalendar.getId(), academicCalendar, contextInfo);
        } catch (DataValidationErrorException e) {
            throw new OperationFailedException("Could not create AcademicCalendar '" + academicCalendar.getId() + "'", e);
        }

        Map<String, KeyDateInfo> oldDatesToNewDates = new HashMap<String, KeyDateInfo>();

        List<TermInfo> templateTerms = getTermsForAcademicCalendar(templateAcademicCalendar.getId(), contextInfo);
        for (TermInfo templateTerm : templateTerms) {
            String termKey = templateTerm.getId() + "." + RandomStringUtils.randomAlphanumeric(4); // TODO
                                                                                                   // properly
                                                                                                   // generate
                                                                                                   // new
                                                                                                   // key
            TermInfo term;
            term = copyTerm(templateTerm.getId(), termKey, oldDatesToNewDates, contextInfo);
            try {
                addTermToAcademicCalendar(academicCalendar.getId(), term.getId(), contextInfo);
            } catch (AlreadyExistsException e) {
                throw new OperationFailedException("AlreadyExistsException  thrown from addTermToAcademicCalendar :" + e.getMessage());
            }
        }

        return academicCalendar;
    }

    private TermInfo copyTerm(String templateTermKey, String newTermKey, Map<String, KeyDateInfo> templateDatesToNewDates, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        TermInfo templateTerm = getTerm(templateTermKey, context);

        TermInfo term = new TermInfo(templateTerm);
        term.setId(templateTerm.getId() + "." + RandomStringUtils.randomAlphanumeric(4)); // TODO
                                                                                          // properly
                                                                                          // generate
                                                                                          // new
                                                                                          // key
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setName(templateTerm.getName());
        term.setDescr(new RichTextInfo(templateTerm.getDescr()));
        term.setTypeKey(templateTerm.getTypeKey());

        try {
            term = createTerm(term.getId(), term, context);
        } catch (DataValidationErrorException e) {
            throw new OperationFailedException("Could not create Term '" + term.getId() + "'", e);
        }

        /*
         * Copy KeyDates of Term TODO Currently cannot reuse keydates in the
         * acal service, but the design concept was that a term and subterm may
         * share dates. A mapping of all KeyDates to their newly created
         * counterparts is used to determine whether a new KeyDate has already
         * been created. However, it is not being used at this time and a new
         * KeyDate will be created for each relationship.
         */
        List<KeyDateInfo> templateKeyDates = getKeyDatesForTerm(templateTermKey, context);
        for (KeyDateInfo templateKeyDate : templateKeyDates) {
            KeyDateInfo keyDate = templateDatesToNewDates.get(templateKeyDate.getId());
            keyDate = null; // TODO Disabling usage of mapping until service
                            // supports the reuse of dates

            if (null == keyDate) {
                keyDate = new KeyDateInfo(templateKeyDate);
                keyDate.setId(templateKeyDate.getId() + "." + RandomStringUtils.randomAlphanumeric(4)); // TODO
                                                                                                        // properly
                                                                                                        // generate
                                                                                                        // new
                                                                                                        // key
                keyDate.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
                keyDate.setName(templateKeyDate.getName());
                keyDate.setDescr(new RichTextInfo(templateKeyDate.getDescr()));
                keyDate.setTypeKey(templateKeyDate.getTypeKey());

                try {

                    createKeyDate(term.getId(), keyDate.getId(), keyDate, context); // TODO
                                                                                    // Need
                                                                                    // a
                                                                                    // way
                                                                                    // to
                                                                                    // only
                                                                                    // create
                                                                                    // a
                                                                                    // KeyDate
                                                                                    // in
                                                                                    // order
                                                                                    // to
                                                                                    // associate
                                                                                    // it
                                                                                    // with
                                                                                    // multiple
                                                                                    // Terms
                    templateDatesToNewDates.put(templateKeyDate.getId(), keyDate);
                } catch (DataValidationErrorException e) {
                    throw new OperationFailedException("Could not create KeyDate '" + keyDate.getId() + "'", e);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException("ReadOnlyException " + keyDate.getId() + "'", e);
                }

            }
            // TODO Need a way to associate a KeyDate with multiple Terms
        }

        // Recursive call to copy subTerms
        List<TermInfo> templateSubTerms = getContainingTerms(templateTermKey, context);
        for (TermInfo templateSubTerm : templateSubTerms) {
            String subTermKey = templateSubTerm.getId() + "." + RandomStringUtils.randomAlphanumeric(4); // TODO
                                                                                                         // properly
                                                                                                         // generate
                                                                                                         // new
                                                                                                         // key
            TermInfo subTerm = copyTerm(templateSubTerm.getId(), subTermKey, templateDatesToNewDates, context);
            try {
                addTermToTerm(term.getId(), subTerm.getId(), context);
            } catch (AlreadyExistsException e) {
                throw new OperationFailedException("AlreadyExistsException : " + e.getMessage());
            }
        }

        return term;
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
    public HolidayCalendarInfo createHolidayCalendar(String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        AtpInfo atpInfo;
        HolidayCalendarInfo newHolidayCalendar = null;
        try {
            atpInfo = holidayCalendarAssembler.disassemble(holidayCalendarInfo, context);

            atpInfo = atpService.createAtp(atpInfo.getId(), atpInfo, context);

            newHolidayCalendar = holidayCalendarAssembler.assemble(atpInfo, context);

        } catch (AssemblyException e) {
            e.printStackTrace();
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        return newHolidayCalendar;
    }

    @Override
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
    public StatusInfo deleteHolidayCalendar(String holidayCalendarId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return atpService.deleteAtp(holidayCalendarId, context);
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        TypeInfo type = atpService.getType(termTypeKey, context);

        if (!checkTypeForTermType(termTypeKey, context)) {
            throw new InvalidParameterException(termTypeKey + " is not a Term type");
        }

        return type;
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<TypeTypeRelationInfo> relations = null;

        try {
            relations = atpService.getTypeRelationsByOwnerType(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e.getMessage(), e);
        }

        if (relations != null) {
            List<TypeInfo> results = new ArrayList<TypeInfo>(relations.size());
            for (TypeTypeRelationInfo rel : relations) {
                try {
                    results.add(atpService.getType(rel.getRelatedTypeKey(), context));
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
            OperationFailedException {

        TypeInfo acalType = atpService.getType(academicCalendarTypeKey, context);

        return atpService.getAllowedTypesForType(acalType.getKey(), AtpServiceConstants.REF_OBJECT_URI_ATP, context);
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        TypeInfo termType = getTermType(termTypeKey, context);

        return atpService.getAllowedTypesForType(termType.getKey(), AtpServiceConstants.REF_OBJECT_URI_ATP, context);
    }

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        StateInfo termState = atpService.getState(AtpServiceConstants.ATP_PROCESS_KEY, termStateKey, context);

        return termState;
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        List<StateInfo> results;
        try {
            results = atpService.getStatesByProcess(AtpServiceConstants.ATP_PROCESS_KEY, context);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException(AtpServiceConstants.ATP_PROCESS_KEY, ex);
        }

        return results;
    }

    @Override
    public TermInfo getTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(termKey, context);
        TermInfo term = null;

        if (atp != null && checkTypeForTermType(atp.getTypeKey(), context))
            try {
                term = termAssembler.assemble(atp, context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        else
            throw new DoesNotExistException("This is either not valid Atp or not valid Term. " + termKey);

        return term;
    }

    @Override
    public List<TermInfo> getTermsByIds(List<String> termKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<AtpInfo> results = atpService.getAtpsByIds(termKeyList, context);

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
    public List<TermInfo> getIncludedTermsInTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // check for a valid term
        TermInfo parentTerm = getTerm(termKey, context);

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByTypeAndAtp(parentTerm.getId(), AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getAtpId().equals(termKey)) {
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
    public List<TermInfo> getContainingTerms(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // check for a valid term
        TermInfo term = getTerm(termKey, context);

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtp(term.getId(), context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        // check that the relations we found have the given termKey as the
        // "related" atp, and that the owning atp is a term
        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getRelatedAtpId().equals(termKey)) {
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
                AtpInfo newAtp = atpService.createAtp(termTypeKey, atp, context);
                termInfo = termAssembler.assemble(newAtp, context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling term", e);
            } catch (AlreadyExistsException e) {
                e.printStackTrace();
            }
        }

        return termInfo;
    }

    @Override
    @Transactional
    public TermInfo updateTerm(String termKey, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        AtpInfo atp = atpService.getAtp(termKey, context);

        if (atp == null) {
            throw new DoesNotExistException(termKey);
        }

        if (!checkTypeForTermType(atp.getTypeKey(), context)) {
            throw new InvalidParameterException("Invalid termKey: " + termKey + "  Given key does not map to a Term");
        }

        TermInfo updatedTerm;

        try {
            AtpInfo toUpdate = termAssembler.disassemble(termInfo, context);

            AtpInfo updated = atpService.updateAtp(termKey, toUpdate, context);

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
    public StatusInfo deleteTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        AtpInfo atp = atpService.getAtp(termKey, context);

        if (atp == null) {
            throw new DoesNotExistException(termKey);
        }

        if (!checkTypeForTermType(atp.getTypeKey(), context)) {
            throw new InvalidParameterException("Invalid termKey: " + termKey + "  Given key does not map to a Term");
        }

        StatusInfo result = atpService.deleteAtp(termKey, context);

        return result;
    }

    @Override
    @Transactional
    public StatusInfo addTermToAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AtpInfo acal = atpService.getAtp(academicCalendarKey, context);
        if (acal != null) {
            if (isAcademicCalendar(acal.getTypeKey())) {
                AtpInfo term = atpService.getAtp(termKey, context);
                if (term != null) {
                    if (checkTypeForTermType(term.getTypeKey(), context)) {
                        if (!termAlreadyExists(academicCalendarKey, termKey, context)) {
                            try {
                                createAtpAtpRelations(academicCalendarKey, termKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
                            } catch (DataValidationErrorException e) {
                                status.setSuccess(Boolean.FALSE);
                            }
                        } else
                            throw new AlreadyExistsException("Term with id = " + termKey + " already exists.");
                    } else
                        throw new InvalidParameterException("Term with id = " + termKey + " has invalid type: " + term.getTypeKey());
                } else
                    throw new DoesNotExistException("Term with id = " + termKey + " does not exist.");
            } else
                throw new InvalidParameterException("AcademicCalendar with id = " + academicCalendarKey + " has invalid type: " + acal.getTypeKey());
        } else
            throw new DoesNotExistException("AcademicCalendar with id = " + academicCalendarKey + " does not exist.");

        return status;
    }

    private boolean isAcademicCalendar(String acalType) {
        return null != acalType ? acalType.equals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY) : false;
    }

    private boolean termAlreadyExists(String atpId, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        boolean found = false;
        List<AtpAtpRelationInfo> atpRels = atpService.getAtpAtpRelationsByTypeAndAtp(atpId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

        if (atpRels != null && !atpRels.isEmpty()) {
            for (AtpAtpRelationInfo atpRelInfo : atpRels) {
                if (atpRelInfo.getAtpId().equals(atpId)) {
                    if (atpRelInfo.getRelatedAtpId().equals(termKey)) {
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
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        AtpInfo acal = atpService.getAtp(academicCalendarKey, context);

        if (acal == null) {
            throw new InvalidParameterException("Invalid academicCalendarKey: " + academicCalendarKey);
        }

        AtpInfo term = atpService.getAtp(termKey, context);

        if (term == null) {
            throw new InvalidParameterException("Invalid termKey: " + termKey);
        }

        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByTypeAndAtp(academicCalendarKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if (relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between academic calendar: " + academicCalendarKey + " and term: " + termKey);
        }

        AtpAtpRelationInfo relationToRemove = null;

        for (AtpAtpRelationInfo rel : relations) {
            if (rel.getAtpId().equals(academicCalendarKey)) {
                if (rel.getRelatedAtpId().equals(termKey)) {
                    // if the relation represents an "includes" relationship
                    // from the AcademicCalendar to the Term,
                    // then it is the one we need to remove
                    relationToRemove = rel;
                    break;
                }
            }
        }

        if (relationToRemove == null) {
            throw new DoesNotExistException("No relationship exists between academic calendar: " + academicCalendarKey + " and term: " + termKey);
        }

        StatusInfo resultStatus = atpService.deleteAtpAtpRelation(relationToRemove.getId(), context);

        return resultStatus;
    }

    @Override
    @Transactional
    public StatusInfo addTermToTerm(String termKey, String includedTermKey, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        TermInfo term = getTerm(termKey, context);

        TermInfo includedTerm = getTerm(includedTermKey, context);

        // check if the relationship already exists
        List<TermInfo> terms = getIncludedTermsInTerm(term.getId(), context);
        for (TermInfo t : terms) {
            if (t.getId().equals(includedTerm.getId())) {
                throw new AlreadyExistsException("A relationship already exists exists between term: " + termKey + " and included term: " + includedTermKey);
            }
        }

        StatusInfo resultStatus = new StatusInfo();

        try {
            createAtpAtpRelations(termKey, includedTermKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        } catch (DataValidationErrorException e) {
            resultStatus.setSuccess(false);
            resultStatus.setMessage("Creation of AtpAtpRelation failed due to DataValidationErrorExecption: " + e.getMessage());
        }

        return resultStatus;
    }

    @Override
    @Transactional
    public StatusInfo removeTermFromTerm(String termKey, String includedTermKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        try {
            atpService.getAtp(termKey, context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Invalid termKey: " + termKey);
        }

        try {
            atpService.getAtp(includedTermKey, context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Invalid includedTermKey: " + includedTermKey);
        }

        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByTypeAndAtp(termKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if (relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between term: " + termKey + " and included term: " + includedTermKey);
        }

        AtpAtpRelationInfo relationToRemove = null;

        for (AtpAtpRelationInfo rel : relations) {
            if (rel.getAtpId().equals(termKey)) {
                if (rel.getRelatedAtpId().equals(includedTermKey)) {
                    // if the relation represents an "includes" relationship
                    // from the Term to the included Term,
                    // then it is the one we need to remove
                    relationToRemove = rel;
                    break;
                }
            }
        }

        if (relationToRemove == null) {
            throw new DoesNotExistException("No relationship exists between term: " + termKey + " and included term: " + includedTermKey);
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
    public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MilestoneInfo ms = atpService.getMilestone(keyDateKey, context);
        return (null != ms) ? fromMilestoneInfo(ms) : null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByIds(List<String> keyDateKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        AtpInfo termAtp = atpService.getAtp(termKey, context);
        if (termAtp == null) {
            throw new DoesNotExistException(termKey);
        }

        List<MilestoneInfo> milestones = atpService.getMilestonesForAtp(termKey, context);

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
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey, Date startDate, Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByDatesForAtp(termKey, startDate, endDate, context);
        List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            keyDates.add(fromMilestoneInfo(milestoneInfo));
        }
        return keyDates;
    }

    @Override
    public List<KeyDateInfo> getImpactedKeyDates(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<KeyDateInfo>();
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

            return keyInfo;
        } else
            return null;
    }

    @Override
    public KeyDateInfo updateKeyDate(String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        MilestoneInfo toUpdate = toMilestoneInfo(keyDateInfo);
        MilestoneInfo newMilestone = null;
        try {
            newMilestone = atpService.updateMilestone(keyDateKey, toUpdate, context);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Error updating milestone", e);
        }

        return fromMilestoneInfo(newMilestone);
    }

    @Override
    @Transactional
    public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.deleteMilestone(keyDateKey, context);
    }

    @Override
    public KeyDateInfo calculateKeyDate(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Sambit - THIS METHOD NEEDS JAVADOCS
        return getKeyDate(keyDateId, contextInfo);
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public HolidayInfo updateHoliday(String holidayKey, HolidayInfo holidayInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        try {
            MilestoneInfo toUpdate = holidayAssembler.disassemble(holidayInfo, context);
            MilestoneInfo updated = null;
            try {
                updated = atpService.updateMilestone(holidayKey, toUpdate, context);
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
    public StatusInfo deleteHoliday(String holidayKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.deleteMilestone(holidayKey, context);
    }

    @Override
    public HolidayInfo calculateHoliday(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Sambit - THIS METHOD NEEDS JAVADOCS
        return getHoliday(holidayId, contextInfo);
    }

    /*
     * reg date groups removed from service 12/16

    @Override
    public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        RegistrationDateGroupInfo regDateGroup = new RegistrationDateGroupInfo();
        regDateGroup.setTermKey(termKey);
        regDateGroup.setRegistrationDateDerivationGroup(new RegistrationDateDerivationGroupInfo());

        List<KeyDateInfo> keyDates = getKeyDatesForTerm(termKey, context);
        Map<String, KeyDateInfo> keyDateMap = new HashMap<String, KeyDateInfo>();
        for (KeyDateInfo keyDate : keyDates) {
            keyDateMap.put(keyDate.getTypeKey(), keyDate);
        }
        populateRegistrationDateGroup(regDateGroup, keyDateMap);
        return regDateGroup;
    }

    private void populateRegistrationDateGroup(RegistrationDateGroupInfo registrationDateGroup, Map<String, KeyDateInfo> keyDates) {
        KeyDateInfo registrationPeriod = keyDates.get(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        registrationDateGroup.setRegistrationDateRange(getDateRangeFromKeyDate(registrationPeriod));
        registrationDateGroup.setAddDate((registrationPeriod != null) ? registrationPeriod.getStartDate() : null);

        KeyDateInfo instructionalPeriod = keyDates.get(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY);
        registrationDateGroup.setClassDateRange(getDateRangeFromKeyDate(instructionalPeriod));

        KeyDateInfo dropDate = keyDates.get(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY);
        registrationDateGroup.setDropDate((dropDate != null) ? dropDate.getEndDate() : null);

        KeyDateInfo finalExamPeriod = keyDates.get(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY);
        registrationDateGroup.setFinalExamDateRange(getDateRangeFromKeyDate(finalExamPeriod));

        KeyDateInfo gradingPeriod = keyDates.get(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
        registrationDateGroup.setGradingDateRange(getDateRangeFromKeyDate(gradingPeriod));
    }

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

    /* reg date groups removed from service

    @Override
    public RegistrationDateGroupInfo updateRegistrationDateGroup(String termKey, RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        // TODO needs to be move to validation layer?
        Date addDate = registrationDateGroupInfo.getAddDate();
        DateRangeInfo registrationDateRange = registrationDateGroupInfo.getRegistrationDateRange();
        if (null != addDate && null != registrationDateRange && !addDate.equals(registrationDateRange.getStart())) {
            throw new DataValidationErrorException("Add date is not the same as the start of registraion period.");
        }

        List<KeyDateInfo> termKeyDates = getKeyDatesForTerm(termKey, context);
        Map<String, KeyDateInfo> termKeyDateByType = new HashMap<String, KeyDateInfo>();
        for (KeyDateInfo keyDate : termKeyDates) {
            termKeyDateByType.put(keyDate.getTypeKey(), keyDate);
        }
        Map<String, KeyDateInfo> registrationDatesGroupKeyDates = new HashMap<String, KeyDateInfo>();

        KeyDateInfo existingRegistrationPeriodKeyDate = termKeyDateByType.get(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        KeyDateInfo updatedRegistrationPeriodDate = getKeyDatePrepairedFromDateRange(existingRegistrationPeriodKeyDate, registrationDateGroupInfo.getRegistrationDateRange()); // TODO
                                                                                                                                                                               // change
                                                                                                                                                                               // namee
                                                                                                                                                                               // to
                                                                                                                                                                               // update
        updateRegistrationDateGroupKeyDate(termKey, existingRegistrationPeriodKeyDate, updatedRegistrationPeriodDate, AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, context);
        registrationDatesGroupKeyDates.put(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, updatedRegistrationPeriodDate);

        KeyDateInfo existingInstructionalPeriodKeyDate = termKeyDateByType.get(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY);
        KeyDateInfo updatedInstructionalPeriodDate = getKeyDatePrepairedFromDateRange(existingInstructionalPeriodKeyDate, registrationDateGroupInfo.getClassDateRange());
        updateRegistrationDateGroupKeyDate(termKey, existingInstructionalPeriodKeyDate, updatedInstructionalPeriodDate, AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, context);
        registrationDatesGroupKeyDates.put(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, updatedInstructionalPeriodDate);

        KeyDateInfo existingDropKeyDate = termKeyDateByType.get(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY);
        KeyDateInfo updatedDropDate = getKeyDatePrepairedFromDate(existingDropKeyDate, registrationDateGroupInfo.getDropDate());
        updateRegistrationDateGroupKeyDate(termKey, existingDropKeyDate, updatedDropDate, AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY, context);
        registrationDatesGroupKeyDates.put(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY, updatedDropDate);

        KeyDateInfo existingFinalExamKeyDate = termKeyDateByType.get(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY);
        KeyDateInfo updatedFinalExamDate = getKeyDatePrepairedFromDateRange(existingFinalExamKeyDate, registrationDateGroupInfo.getFinalExamDateRange());
        updateRegistrationDateGroupKeyDate(termKey, existingFinalExamKeyDate, updatedFinalExamDate, AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, context);
        registrationDatesGroupKeyDates.put(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, updatedFinalExamDate);

        KeyDateInfo existingGradingKeyDate = termKeyDateByType.get(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY);
        KeyDateInfo updatedGradingKeyDate = getKeyDatePrepairedFromDateRange(existingGradingKeyDate, registrationDateGroupInfo.getGradingDateRange());
        updateRegistrationDateGroupKeyDate(termKey, existingGradingKeyDate, updatedGradingKeyDate, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, context);
        registrationDatesGroupKeyDates.put(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, updatedGradingKeyDate);

        populateRegistrationDateGroup(registrationDateGroupInfo, registrationDatesGroupKeyDates);
        return registrationDateGroupInfo;
    }

    private void updateRegistrationDateGroupKeyDate(String termKey, KeyDateInfo existingKeyDate, KeyDateInfo updatedKeyDate, String typeKey, ContextInfo context) throws InvalidParameterException,
            DataValidationErrorException, MissingParameterException, DoesNotExistException, VersionMismatchException, PermissionDeniedException, OperationFailedException {
        if (null != updatedKeyDate && null != existingKeyDate) {
            // update date
            updatedKeyDate.setId(existingKeyDate.getId());
            updatedKeyDate.setTypeKey(existingKeyDate.getTypeKey());
            updatedKeyDate.setStateKey(existingKeyDate.getStateKey());
            updateKeyDate(existingKeyDate.getId(), updatedKeyDate, context);
        } else if (null != updatedKeyDate && null == existingKeyDate) {
            // add date
            updatedKeyDate.setId(typeKey + "." + termKey + "." + RandomStringUtils.randomAlphanumeric(4)); // TODO
                                                                                                           // properly
                                                                                                           // generate
                                                                                                           // new
                                                                                                           // key
            updatedKeyDate.setTypeKey(typeKey);
            updatedKeyDate.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
            try {
                createKeyDate(termKey, updatedKeyDate.getId(), updatedKeyDate, context);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Could not create KeyDate for Term - ReadOnlyException" + termKey + "'-'" + updatedKeyDate.getId() + "'", e);
            }
        } else if (null == updatedKeyDate && null != existingKeyDate) {
            atpService.deleteMilestone(existingKeyDate.getId(), context);
        }
    }
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

        List<AcademicCalendarInfo> acalInfos = new ArrayList<AcademicCalendarInfo>();

        return acalInfos;
    }

    @Override
    public List<TermInfo> searchForTerms(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<TermInfo>();
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
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<HolidayInfo>();
    }

    private boolean checkTypeForTermType(String typeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<TypeInfo> types = getTermTypes(context);

        for (TypeInfo type : types) {
            if (type.getKey().equals(typeKey)) {
                return true;
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
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getHolidayCalendarTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getHolidayCalendarState(String holidayCalendarStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getHolidayCalendarStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
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
        return null;
    }

    @Override
    public List<String> searchForHolidayCalendarIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayCalendarInfo> searchForHolidayCalendars(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateHolidayCalendar(String validationTypeKey, String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getTermIdsByType(String termTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
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
    public KeyDateInfo createKeyDate(String termId, String keyDateTypeKey, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getAcalEventType(String acalEventTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAcalEventTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
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
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<AcalEventInfo> getAcalEventsByIds(List<String> acalEventIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getAcalEventIdsByType(String acalEventTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendar(String academicCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendarByDate(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<AcalEventInfo> getImpactedAcalEvents(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateAcalEvent(String validationTypeKey, String termId, String acalEventTypeKey, AcalEventInfo acalEventInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AcalEventInfo createAcalEvent(String termId, String acalEventTypeKey, AcalEventInfo acalEventInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AcalEventInfo updateAcalEvent(String acalEventId, AcalEventInfo acalEventInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AcalEventInfo calculateAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Sambit - THIS METHOD NEEDS JAVADOCS
        return getAcalEvent(acalEventId, contextInfo);
    }

    @Override
    public List<TypeInfo> getHolidayTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getHolidayTypesForHolidayCalendarType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getHolidayState(String holidayStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getHolidayStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public HolidayInfo getHoliday(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayInfo> getHolidaysByIds(List<String> holidayIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getHolidayIdsByType(String holidayTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendar(String holidayCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendarByDate(String holidayCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayInfo> getImpactedHolidays(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /* reg date groups removed from service

    @Override
    public List<ValidationResultInfo> validateRegistrationDateGroup(String validationTypeKey, String termId, RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    */

    @Override
    public String getAcademicCalendarData(String academicCalendarId, String calendarDataFormatTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
