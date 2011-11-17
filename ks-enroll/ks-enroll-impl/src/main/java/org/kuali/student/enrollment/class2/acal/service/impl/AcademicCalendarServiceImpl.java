package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.dto.*;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.service.assembler.AcademicCalendarAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.CampusCalendarAssembler;
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
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class AcademicCalendarServiceImpl implements AcademicCalendarService {
    private AtpService atpService;
    private AcademicCalendarAssembler acalAssembler;
    private TermAssembler termAssembler;
    private DataDictionaryService dataDictionaryService;
    private CampusCalendarAssembler campusCalendarAssembler;
    private HolidayAssembler holidayAssembler;

    public HolidayAssembler getHolidayAssembler() {
        return holidayAssembler;
    }

    public void setHolidayAssembler(HolidayAssembler holidayAssembler) {
        this.holidayAssembler = holidayAssembler;
    }

    public CampusCalendarAssembler getCampusCalendarAssembler() {
        return campusCalendarAssembler;
    }

    public void setCampusCalendarAssembler(CampusCalendarAssembler campusCalendarAssembler) {
        this.campusCalendarAssembler = campusCalendarAssembler;
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
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        return dataDictionaryService.getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<TypeInfo>();
    }

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<StateInfo>();
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(List<String> academicCalendarKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();
        List<AtpInfo> atps = atpService.getAtpsByKeyList(academicCalendarKeyList, context);
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
    public List<String> getAcademicCalendarKeysByType(String academicCalendarTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByStartYear(Integer year, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
                return atpInfo1.getKey().compareTo(atpInfo2.getKey());
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

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(String credentialProgramTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO: this will be replaced by func on searching dynamic attributes

        List<AcademicCalendarInfo> acals = new ArrayList<AcademicCalendarInfo>();
        List<String> atpKeys = atpService.getAtpKeysByType(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, context);
        if (atpKeys != null && !atpKeys.isEmpty()) {
            for (String atpKey : atpKeys) {
                try {
                    AtpInfo atp = atpService.getAtp(atpKey, context);
                    List<AttributeInfo> attributes = atp.getAttributes();
                    if (attributes != null && !attributes.isEmpty()) {
                        for (AttributeInfo attribute : attributes) {
                            if (attribute.getKey().equals("CredentialProgramType") && attribute.getValue().equals(credentialProgramTypeKey)) {
                                AcademicCalendarInfo acal = null;
                                try {
                                    acal = acalAssembler.assemble(atp, context);
                                } catch (AssemblyException e) {
                                    throw new OperationFailedException("AssemblyException : " + e.getMessage());
                                }
                                if (acal != null)
                                    acals.add(acal);
                            }
                        }
                    }
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException("getAtpKeysByType found invalid atp: " + atpKey);
                }
            }
        }

        return acals;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForStartYear(String credentialProgramTypeKey, Integer year, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<AcademicCalendarInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(String validationType, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    private void processAcalToCcalRelation(String academicCalendarKey, List<String> campusCalendarKeys, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (campusCalendarKeys != null && !campusCalendarKeys.isEmpty()) {
            List<String> validCcalKeys = new ArrayList<String>();
            for (String ccKey : campusCalendarKeys) {
                try {
                    AtpInfo cCal = atpService.getAtp(ccKey, context);
                    if (cCal != null)
                        validCcalKeys.add(ccKey);
                    else
                        throw new OperationFailedException("The CampusCalendar does not exist. " + ccKey);
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException("The CampusCalendar Does Not Exist. " + ccKey);
                }
            }

            try {
                acalAssembler.getRelAssembler().disassemble(academicCalendarKey, validCcalKeys, AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY, context);
            } catch (VersionMismatchException e) {
                throw new OperationFailedException();
            }
        }
    }

    @Override
    @Transactional
    public AcademicCalendarInfo createAcademicCalendar(String academicCalendarKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo created = null;
        AtpInfo atp = null;
        try {
            atp = acalAssembler.disassemble(academicCalendarInfo, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        try {
            AtpInfo existing = atpService.getAtp(academicCalendarKey, context);
            if (existing == null) {
                created = atpService.createAtp(academicCalendarKey, atp, context);
                if (created != null)
                    processAcalToCcalRelation(created.getKey(), academicCalendarInfo.getCampusCalendarKeys(), context);
            } else {
                throw new AlreadyExistsException("Academic calendar with id = " + academicCalendarKey + " already exists");
            }
        } catch (DoesNotExistException e1) {
            created = atpService.createAtp(academicCalendarKey, atp, context);
            if (created != null)
                processAcalToCcalRelation(created.getKey(), academicCalendarInfo.getCampusCalendarKeys(), context);
        }

        return academicCalendarInfo;
    }

    @Override
    @Transactional
    public AcademicCalendarInfo updateAcademicCalendar(String academicCalendarKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        try {
            AtpInfo existing = atpService.getAtp(academicCalendarKey, context);
            if (existing != null) {
                AtpInfo atp = null;
                try {
                    atp = acalAssembler.disassemble(academicCalendarInfo, context);
                } catch (AssemblyException e) {
                    throw new OperationFailedException("AssemblyException : " + e.getMessage());
                }

                if (atp != null) {
                    AtpInfo updated = atpService.updateAtp(academicCalendarKey, atp, context);
                    if (updated != null)
                        try {
                            processAcalToCcalRelation(academicCalendarKey, academicCalendarInfo.getCampusCalendarKeys(), context);
                        } catch (AlreadyExistsException e) {
                            throw new OperationFailedException();
                        }
                }
            } else
                throw new DoesNotExistException("The AcademicCalendar does not exist: " + academicCalendarKey);
        } catch (DoesNotExistException e1) {
            throw new DoesNotExistException("The AcademicCalendar does not exist: " + academicCalendarKey);
        }

        return academicCalendarInfo;
    }

    @Override
    @Transactional
    public StatusInfo deleteAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

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
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarKey, String newAcademicCalendarKey, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        AcademicCalendarInfo templateAcademicCalendar = getAcademicCalendar(academicCalendarKey, context);
        AcademicCalendarInfo academicCalendar = new AcademicCalendarInfo(templateAcademicCalendar);
        academicCalendar.setKey(newAcademicCalendarKey);
        academicCalendar.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        academicCalendar.setName(templateAcademicCalendar.getName());
        academicCalendar.setDescr(new RichTextInfo(templateAcademicCalendar.getDescr()));
        academicCalendar.setTypeKey(templateAcademicCalendar.getTypeKey());

        try {
            academicCalendar = createAcademicCalendar(academicCalendar.getKey(), academicCalendar, context);
        } catch (DataValidationErrorException e) {
            throw new OperationFailedException("Could not create AcademicCalendar '" + academicCalendar.getKey() + "'", e);
        }

        Map<String, KeyDateInfo> oldDatesToNewDates = new HashMap<String, KeyDateInfo>();
        List<KeyDateInfo> allOriginalDates = getKeyDatesForAcademicCalendar(templateAcademicCalendar.getKey(), context);
        for (KeyDateInfo date : allOriginalDates) {
            oldDatesToNewDates.put(date.getKey(), null);
        }

        List<TermInfo> templateTerms = getTermsForAcademicCalendar(templateAcademicCalendar.getKey(), context);
        for (TermInfo templateTerm : templateTerms) {
            String termKey = templateTerm.getKey() + "." + RandomStringUtils.randomAlphanumeric(4); // TODO properly generate new key
            TermInfo term = copyTerm(templateTerm.getKey(), termKey, oldDatesToNewDates, context);
            addTermToAcademicCalendar(academicCalendar.getKey(), term.getKey(), context);
        }

        return academicCalendar;
    }

    private TermInfo copyTerm(String templateTermKey, String newTermKey, Map<String, KeyDateInfo> templateDatesToNewDates, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, AlreadyExistsException {
        TermInfo templateTerm = getTerm(templateTermKey, context);

        TermInfo term = new TermInfo(templateTerm);
        term.setKey(templateTerm.getKey() + "." + RandomStringUtils.randomAlphanumeric(4)); // TODO properly generate new key
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setName(templateTerm.getName());
        term.setDescr(new RichTextInfo(templateTerm.getDescr()));
        term.setTypeKey(templateTerm.getTypeKey());

        try {
            term = createTerm(term.getKey(), term, context);
        } catch (DataValidationErrorException e) {
            throw new OperationFailedException("Could not create Term '" + term.getKey() + "'", e);
        }

        /*
            Copy KeyDates of Term
            TODO
            Currently cannot reuse keydates in the acal service, but the design concept was that a term and subterm
            may share dates. A mapping of all KeyDates to their newly created counterparts is used to determine
            whether a new KeyDate has already been created. However, it is not being used at this time and a new
            KeyDate will be created for each relationship.
         */
        List<KeyDateInfo> templateKeyDates = getKeyDatesForTerm(templateTermKey, context);
        for (KeyDateInfo templateKeyDate : templateKeyDates) {
            KeyDateInfo keyDate = templateDatesToNewDates.get(templateKeyDate.getKey());
            keyDate = null; // TODO Disabling usage of mapping until service supports the reuse of dates

            if (null == keyDate) {
                keyDate = new KeyDateInfo(templateKeyDate);
                keyDate.setKey(templateKeyDate.getKey() + "." + RandomStringUtils.randomAlphanumeric(4)); // TODO properly generate new key
                keyDate.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
                keyDate.setName(templateKeyDate.getName());
                keyDate.setDescr(new RichTextInfo(templateKeyDate.getDescr()));
                keyDate.setTypeKey(templateKeyDate.getTypeKey());

                try {
                    createKeyDateForTerm(term.getKey(), keyDate.getKey(), keyDate, context); // TODO Need a way to only create a KeyDate in order to associate it with multiple Terms
                    templateDatesToNewDates.put(templateKeyDate.getKey(), keyDate);
                } catch (DataValidationErrorException e) {
                    throw new OperationFailedException("Could not create KeyDate '" + keyDate.getKey() + "'", e);
                }

            }
            // TODO Need a way to associate a KeyDate with multiple Terms
        }

        // Recursive call to copy subTerms
        List<TermInfo> templateSubTerms = getContainingTerms(templateTermKey, context);
        for (TermInfo templateSubTerm : templateSubTerms) {
            String subTermKey = templateSubTerm.getKey() + "." + RandomStringUtils.randomAlphanumeric(4); // TODO properly generate new key
            TermInfo subTerm = copyTerm(templateSubTerm.getKey(), subTermKey, templateDatesToNewDates, context);
            addTermToTerm(term.getKey(), subTerm.getKey(), context);
        }

        return term;
    }

    @Override
    public String getAcademicCalendarData(String academicCalendarKey, String calendarDataFormatTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getCampusCalendarType(String campusCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return atpService.getType(campusCalendarTypeKey,context);
    }

    @Override
    public List<TypeInfo> getCampusCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<TypeInfo>();
    }

    @Override
    public StateInfo getCampusCalendarState(String campusCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getCampusCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<StateInfo>();
    }

    @Override
    public CampusCalendarInfo getCampusCalendar(String campusCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(campusCalendarKey,context);
        CampusCalendarInfo ccal;
        try {
            ccal = campusCalendarAssembler.assemble(atp,context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return ccal;
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByKeyList(List<String> campusCalendarKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpInfo> atps = atpService.getAtpsByKeyList(campusCalendarKeyList,context);
        List<CampusCalendarInfo> campusCalendarInfos = new ArrayList<CampusCalendarInfo>();
        for (AtpInfo atp : atps) {
            try {
                campusCalendarInfos.add(campusCalendarAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        return campusCalendarInfos;
    }

    @Override
    public List<String> getCampusCalendarKeysByType(String campusCalendarTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByStartYear(Integer year, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<CampusCalendarInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateCampusCalendar(String validationType, CampusCalendarInfo campusCalendarInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public CampusCalendarInfo createCampusCalendar(String campusCalendarKey, CampusCalendarInfo campusCalendarInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        boolean create = false;
        try{
            AtpInfo atp = atpService.getAtp(campusCalendarKey,context);
            if(atp != null){
                throw new AlreadyExistsException(campusCalendarKey);
            } else {
                create = true;
            }
        }catch (DoesNotExistException e) {
            create = true;
        }

        if (create){
            try{
                AtpInfo atpInfo = campusCalendarAssembler.disassemble(campusCalendarInfo,context);
                atpInfo = atpService.createAtp(atpInfo.getKey(),atpInfo,context);
                return campusCalendarAssembler.assemble(atpInfo,context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return null;
    }

    @Override
    public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey, CampusCalendarInfo campusCalendarInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        AtpInfo existing = atpService.getAtp(campusCalendarKey,context);

        try{
            AtpInfo toUpdate = campusCalendarAssembler.disassemble(campusCalendarInfo,context);
            AtpInfo updated = atpService.updateAtp(campusCalendarKey,toUpdate,context);
            return campusCalendarAssembler.assemble(updated,context);
        } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }
    }

    @Override
    public StatusInfo deleteCampusCalendar(String campusCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        //Make sure it exists.. If not, this method will throw an exception
        AtpInfo existing = atpService.getAtp(campusCalendarKey,context);

        StatusInfo status = atpService.deleteAtp(campusCalendarKey,context);
        if (status.getIsSuccess()){
            List<AtpMilestoneRelationInfo> relationInfos = atpService.getAtpMilestoneRelationsByAtp(campusCalendarKey,context);
            for (AtpMilestoneRelationInfo relationInfo : relationInfos){
                atpService.deleteAtpMilestoneRelation(relationInfo.getId(),context);
            }
        }

        return status;
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
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

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
    public TermInfo getTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<TermInfo> getTermsByKeyList(List<String> termKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<AtpInfo> results = atpService.getAtpsByKeyList(termKeyList, context);

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
    public List<String> getTermKeysByType(String termTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return atpService.getAtpKeysByType(termTypeKey, context);
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar( String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtpAndRelationType(academicCalendarKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getAtpKey().equals(academicCalendarKey)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getRelatedAtpKey(), context);

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
    public List<TermInfo> getCurrentTerms(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<AcademicCalendarInfo>  currentACInfos =  getAcademicCalendarsByStartYear( new Integer( Calendar.getInstance().get(Calendar.YEAR)), context );
        List<TermInfo> terms = getTermsForAcademicCalendar(currentACInfos.get(0).getKey(), context);
        if (terms == null || terms.size() == 0) {
            throw new DoesNotExistException("This academic calendar doesn't contain any terms : " + currentACInfos.get(0).getKey());
        }
        return terms;
    }

    @Override
    public List<TermInfo> getIncludedTermsInTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // check for a valid term
        TermInfo parentTerm = getTerm(termKey, context);

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtpAndRelationType(parentTerm.getKey(), AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getAtpKey().equals(termKey)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getRelatedAtpKey(), context);

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
    public List<TermInfo> getContainingTerms(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // check for a valid term
        TermInfo term = getTerm(termKey, context);

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtp(term.getKey(), context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        // check that the relations we found have the given termKey as the
        // "related" atp, and that the owning atp is a term
        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getRelatedAtpKey().equals(termKey)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getAtpKey(), context);

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
    public List<ValidationResultInfo> validateTerm(String validationType, TermInfo termInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional
    public TermInfo createTerm(String termKey, TermInfo termInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atp;

        if (checkTypeForTermType(termInfo.getTypeKey(), context)) {
            try {
                atp = termAssembler.disassemble(termInfo, context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }

            try {
                AtpInfo existing = atpService.getAtp(termKey, context);
                if (existing == null)
                    atpService.createAtp(termKey, atp, context);
                else
                    throw new AlreadyExistsException("Term with id = " + termKey + " already exists");
            } catch (DoesNotExistException e1) {
                atpService.createAtp(termKey, atp, context);
            }
        } else
            throw new InvalidParameterException("Term with id = " + termKey + " has invalid term type: " + termInfo.getTypeKey());

        return termInfo;
    }

    @Override
    @Transactional
    public TermInfo updateTerm(String termKey, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        AtpInfo atp = atpService.getAtp(termKey, context);

        if (atp == null) {
            throw new DoesNotExistException(termKey);
        }

        if (!checkTypeForTermType(atp.getTypeKey(), context)) {
            throw new InvalidParameterException("Invalid termKey: " + termKey + "  Given key does not map to a Term");
        }

        TermInfo updatedTerm;

        try{
            AtpInfo toUpdate = termAssembler.disassemble(termInfo, context);

            AtpInfo updated = atpService.updateAtp(termKey, toUpdate, context);

            updatedTerm = termAssembler.assemble(updated, context);
        } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return updatedTerm;
    }

    @Override
    @Transactional
    public StatusInfo deleteTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

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
    public StatusInfo addTermToAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException {
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
                                acalAssembler.getRelAssembler().createAtpAtpRelations(academicCalendarKey, termKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
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

    private boolean termAlreadyExists(String atpKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        boolean found = false;
        List<AtpAtpRelationInfo> atpRels = atpService.getAtpAtpRelationsByAtpAndRelationType(atpKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

        if (atpRels != null && !atpRels.isEmpty()) {
            for (AtpAtpRelationInfo atpRelInfo : atpRels) {
                if (atpRelInfo.getAtpKey().equals(atpKey)) {
                    if (atpRelInfo.getRelatedAtpKey().equals(termKey)) {
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
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        AtpInfo acal = atpService.getAtp(academicCalendarKey, context);

        if (acal == null) {
            throw new InvalidParameterException("Invalid academicCalendarKey: " + academicCalendarKey);
        }

        AtpInfo term = atpService.getAtp(termKey, context);

        if (term == null) {
            throw new InvalidParameterException("Invalid termKey: " + termKey);
        }

        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByAtpAndRelationType(academicCalendarKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if (relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between academic calendar: " + academicCalendarKey + " and term: " + termKey);
        }

        AtpAtpRelationInfo relationToRemove = null;

        for (AtpAtpRelationInfo rel : relations) {
            if (rel.getAtpKey().equals(academicCalendarKey)) {
                if (rel.getRelatedAtpKey().equals(termKey)) {
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
    public StatusInfo addTermToTerm(String termKey, String includedTermKey, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        TermInfo term = getTerm(termKey, context);

        TermInfo includedTerm = getTerm(includedTermKey, context);

        // check if the relationship already exists
        List<TermInfo> terms = getIncludedTermsInTerm(term.getKey(), context);
        for (TermInfo t : terms) {
            if (t.getKey().equals(includedTerm.getKey())) {
                throw new AlreadyExistsException("A relationship already exists exists between term: " + termKey + " and included term: " + includedTermKey);
            }
        }

        StatusInfo resultStatus = new StatusInfo();

        try {
            acalAssembler.getRelAssembler().createAtpAtpRelations(termKey, includedTermKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        } catch (DataValidationErrorException e) {
            resultStatus.setSuccess(false);
            resultStatus.setMessage("Creation of AtpAtpRelation failed due to DataValidationErrorExecption: " + e.getMessage());
        }

        return resultStatus;
    }

    @Override
    @Transactional
    public StatusInfo removeTermFromTerm(String termKey, String includedTermKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

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

        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByAtpAndRelationType(termKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if (relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between term: " + termKey + " and included term: " + includedTermKey);
        }

        AtpAtpRelationInfo relationToRemove = null;

        for (AtpAtpRelationInfo rel : relations) {
            if (rel.getAtpKey().equals(termKey)) {
                if (rel.getRelatedAtpKey().equals(includedTermKey)) {
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
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<TypeInfo>();
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MilestoneInfo ms = atpService.getMilestone(keyDateKey, context);
        return (null != ms) ? new KeyDateInfo(ms) : null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByKeyList(List<String> keyDateKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<String> getKeyDateKeysByType(String keyDateTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(String academicCalendarKey, Date startDate, Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        AtpInfo termAtp = atpService.getAtp(termKey, context);
        if (termAtp == null) {
            throw new DoesNotExistException(termKey);
        }

        List<MilestoneInfo> milestones = atpService.getMilestonesByAtp(termKey, context);

        if (milestones == null || milestones.isEmpty()) {
            return Collections.emptyList();
        }

        List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>(milestones.size());

        for (MilestoneInfo milestone : milestones) {
            keyDates.add(new KeyDateInfo(milestone));
        }

        return keyDates;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey, Date startDate, Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<KeyDateInfo> getAllKeyDatesForTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAllTermsByDate(String termKey, Date startDate, Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationType, KeyDateInfo keyDateInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    private boolean isRelationExists(String atpKey, String milestoneKey, String relationType, ContextInfo context) throws OperationFailedException {
        boolean ret = false;
        List<AtpMilestoneRelationInfo> amRels;
        try {
            amRels = atpService.getAtpMilestoneRelationsByAtp(atpKey, context);
        } catch (Exception e) {
            throw new OperationFailedException("Error getting ATP-Milestone relation for ATP key " + atpKey, e);
        }

        if (amRels != null && !amRels.isEmpty()) {
            for (AtpMilestoneRelationInfo amRel : amRels) {
                if (StringUtils.equals(amRel.getAtpKey(), atpKey)) {
                    if (StringUtils.equals(amRel.getTypeKey(), relationType)) {
                        if (StringUtils.equals(amRel.getMilestoneKey(), milestoneKey)) {
                            return true;
                        }
                    }
                }
            }
        }

        return ret;
    }

    @Override
    @Transactional
    public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        KeyDateInfo keyDate = null;
        if (!isRelationExists(termKey, keyDateKey, AtpServiceConstants.ATP_MILESTONE_RELATION_OWNS_TYPE_KEY, context)) {
            MilestoneInfo msInfo = toMilestoneInfo(keyDateInfo);
            if (msInfo != null) {
                // create keydate
                keyDate = new KeyDateInfo(atpService.createMilestone(keyDateKey, msInfo, context));

                // create atp-milestone relation
                AtpMilestoneRelationInfo amRelInfo = new AtpMilestoneRelationInfo();
                amRelInfo.setAtpKey(termKey);
                amRelInfo.setId(UUIDHelper.genStringUUID());
                amRelInfo.setMilestoneKey(keyDateKey);
                amRelInfo.setStateKey(AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
                amRelInfo.setTypeKey(AtpServiceConstants.ATP_MILESTONE_RELATION_OWNS_TYPE_KEY);
                amRelInfo.setEffectiveDate(new Date());
                atpService.createAtpMilestoneRelation(amRelInfo, context);
            }
        }

        return keyDate;
    }

    private MilestoneInfo toMilestoneInfo(KeyDateInfo keyDateInfo) {
        if (keyDateInfo != null) {
            MilestoneInfo msInfo = new MilestoneInfo();
            msInfo.setIsAllDay(keyDateInfo.getIsAllDay());
            msInfo.setAttributes(keyDateInfo.getAttributes());
            msInfo.setIsDateRange(keyDateInfo.getIsDateRange());
            msInfo.setDescr(keyDateInfo.getDescr());
            msInfo.setKey(keyDateInfo.getKey());
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

    @Override
    public KeyDateInfo updateKeyDate(String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getHolidayTypesForCampusCalendarType(String campusCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<TypeInfo>();
    }

    @Override
    public List<HolidayInfo> getHolidaysForAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<HolidayInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationType, HolidayInfo holidayInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey, String holidayKey, HolidayInfo holidayInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        HolidayInfo newHolidayInfo = null;
        boolean relationExists = isRelationExists(campusCalendarKey, holidayKey, AtpServiceConstants.ATP_MILESTONE_RELATION_OWNS_TYPE_KEY,context);

        if (!relationExists) {
            MilestoneInfo milestoneInfo = null;
            try {
                milestoneInfo = holidayAssembler.disassemble(holidayInfo,context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException in disassembling: " + e.getMessage());
            }

            if (milestoneInfo != null) {
                MilestoneInfo newMilestone = atpService.createMilestone(holidayKey, milestoneInfo, context);
                try {
                    newHolidayInfo = holidayAssembler.assemble(newMilestone,context);
                } catch (AssemblyException e) {
                    throw new OperationFailedException("AssemblyException in assembling: " + e.getMessage());
                }

                // create relation
                AtpMilestoneRelationInfo amRelInfo = new AtpMilestoneRelationInfo();
                amRelInfo.setAtpKey(campusCalendarKey);
                amRelInfo.setId(UUIDHelper.genStringUUID());
                amRelInfo.setMilestoneKey(holidayKey);
                amRelInfo.setStateKey(AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
                amRelInfo.setTypeKey(AtpServiceConstants.ATP_MILESTONE_RELATION_OWNS_TYPE_KEY);
                amRelInfo.setEffectiveDate(new Date());

                atpService.createAtpMilestoneRelation(amRelInfo, context);
            }
        }

        return newHolidayInfo;
    }

    @Override
    public HolidayInfo updateHoliday(String holidayKey, HolidayInfo holidayInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        MilestoneInfo exists = atpService.getMilestone(holidayKey,context);

        try{
            MilestoneInfo toUpdate = holidayAssembler.disassemble(holidayInfo,context);
            MilestoneInfo updated = atpService.updateMilestone(holidayKey,toUpdate,context);

            return holidayAssembler.assemble(updated,context);
        } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }
    }

    @Override
    public StatusInfo deleteHoliday(String holidayKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        //Make sure it exists. If not exists, this call will throw an exception
        MilestoneInfo exists = atpService.getMilestone(holidayKey,context);

        List<AtpMilestoneRelationInfo> relationInfos = atpService.getAtpMilestoneRelationsByMilestone(holidayKey,context);

        StatusInfo status = atpService.deleteMilestone(holidayKey,context);

        if (status.getIsSuccess()){
            for (AtpMilestoneRelationInfo relationInfo : relationInfos){
                atpService.deleteAtpMilestoneRelation(relationInfo.getId(),context);
            }
        }

        return status;

    }

    @Override
    public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationDateGroup(String validationType, RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public RegistrationDateGroupInfo updateRegistrationDateGroup(String termKey, RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

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
    public List<AcademicCalendarInfo> searchForAcademicCalendars(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<AcademicCalendarInfo> acalInfos = new ArrayList<AcademicCalendarInfo>();

        return acalInfos;
    }

    @Override
    public List<String> searchForAcademicCalendarKeys(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<CampusCalendarInfo> searchForCampusCalendars(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<CampusCalendarInfo>();
    }

    @Override
    public List<String> searchForCampusCalendarKeys(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<String> searchForTermKeys(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<TermInfo> searchForTerms(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return new ArrayList<TermInfo>();
    }

    @Override
    public List<KeyDateInfo> searchForKeyDates(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<String> searchForKeyDateKeys(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
    }

    @Override
    public List<HolidayInfo> searchForHolidays(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<HolidayInfo>();
    }

    @Override
    public List<String> searchForHolidayKeys(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<String>();
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

}
