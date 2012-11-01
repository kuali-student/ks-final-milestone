package org.kuali.student.r2.lum.program.service;

import java.util.Date;
import java.util.List;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.dto.ProgramVariationInfo;

public class ProgramServiceDecorator implements ProgramService {
    private ProgramService nextDecorator;

    public ProgramService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    @Override
    public CredentialProgramInfo getCredentialProgram(String credentialProgramId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCredentialProgram(credentialProgramId, contextInfo);
    }

    @Override
    public List<CredentialProgramInfo> getCredentialProgramsByIds(List<String> credentialProgramId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCredentialProgramsByIds(credentialProgramId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCredentialProgram(String validationType, CredentialProgramInfo credentialProgramInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().validateCredentialProgram(validationType, credentialProgramInfo, contextInfo);
    }

    @Override
    public CredentialProgramInfo createCredentialProgram(String credentialProgramTypeKey, CredentialProgramInfo credentialProgramInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().createCredentialProgram(credentialProgramTypeKey, credentialProgramInfo, contextInfo);
    }

    @Override
    public CredentialProgramInfo createNewCredentialProgramVersion(String credentialProgramId, String versionComment, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException {
        return this.getNextDecorator().createNewCredentialProgramVersion(credentialProgramId, versionComment, contextInfo);
    }

    @Override
    public StatusInfo setCurrentCredentialProgramVersion(String credentialProgramId, Date currentVersionStart, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return this.getNextDecorator().setCurrentCredentialProgramVersion(credentialProgramId, currentVersionStart, contextInfo);
    }

    @Override
    public CredentialProgramInfo updateCredentialProgram(String credentialProgramId, CredentialProgramInfo credentialProgramInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().updateCredentialProgram(credentialProgramId, credentialProgramInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCredentialProgram(String credentialProgramId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteCredentialProgram(credentialProgramId, contextInfo);
    }

    @Override
    public MajorDisciplineInfo getMajorDiscipline(String majorDisciplineId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getMajorDiscipline(majorDisciplineId, contextInfo);
    }

    @Override
    public List<String> getMajorDisciplineIdsByCredentialProgramType(String programType, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().getMajorDisciplineIdsByCredentialProgramType(programType, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateMajorDiscipline(String validationType, MajorDisciplineInfo majorDisciplineInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().validateMajorDiscipline(validationType, majorDisciplineInfo, contextInfo);
    }

    @Override
    public MajorDisciplineInfo createMajorDiscipline(String majorDisciplineTypeKey, MajorDisciplineInfo majorDisciplineInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().createMajorDiscipline(majorDisciplineTypeKey, majorDisciplineInfo, contextInfo);
    }

    @Override
    public MajorDisciplineInfo updateMajorDiscipline(String majorDisciplineId, MajorDisciplineInfo majorDisciplineInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().updateMajorDiscipline(majorDisciplineId, majorDisciplineInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteMajorDiscipline(String majorDisciplineId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteMajorDiscipline(majorDisciplineId, contextInfo);
    }

    @Override
    public MajorDisciplineInfo createNewMajorDisciplineVersion(String majorDisciplineId, String versionComment, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException {
        return this.getNextDecorator().createNewMajorDisciplineVersion(majorDisciplineId, versionComment, contextInfo);
    }

    @Override
    public HonorsProgramInfo getHonorsProgram(String honorsProgramId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getHonorsProgram(honorsProgramId, contextInfo);
    }

    @Override
    public List<String> getHonorProgramIdsByCredentialProgramType(String programType, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().getHonorProgramIdsByCredentialProgramType(programType, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateHonorsProgram(String validationType, HonorsProgramInfo honorsProgramInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().validateHonorsProgram(validationType, honorsProgramInfo, contextInfo);
    }

    @Override
    public HonorsProgramInfo createHonorsProgram(String honorsProgramTypeKey, HonorsProgramInfo honorsProgramInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().createHonorsProgram(honorsProgramTypeKey, honorsProgramInfo, contextInfo);
    }

    @Override
    public HonorsProgramInfo updateHonorsProgram(String honorsProgramId, String honorsProgramTypeKey, HonorsProgramInfo honorsProgramInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {

        return this.getNextDecorator().updateHonorsProgram(honorsProgramId, honorsProgramTypeKey, honorsProgramInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteHonorsProgram(String honorsProgramId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteHonorsProgram(honorsProgramId, contextInfo);
    }

    @Override
    public CoreProgramInfo getCoreProgram(String coreProgramId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCoreProgram(coreProgramId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCoreProgram(String validationType, CoreProgramInfo coreProgramInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().validateCoreProgram(validationType, coreProgramInfo, contextInfo);
    }

    @Override
    public CoreProgramInfo createCoreProgram(String coreProgramTypeKey, CoreProgramInfo coreProgramInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        return this.getNextDecorator().createCoreProgram(coreProgramTypeKey, coreProgramInfo, contextInfo);
    }

    @Override
    public CoreProgramInfo createNewCoreProgramVersion(String coreProgramId, String versionComment, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException {
        return this.getNextDecorator().createNewCoreProgramVersion(coreProgramId, versionComment, contextInfo);
    }

    @Override
    public StatusInfo setCurrentCoreProgramVersion(String coreProgramId, Date currentVersionStart, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return this.getNextDecorator().setCurrentCoreProgramVersion(coreProgramId, currentVersionStart, contextInfo);
    }

    @Override
    public CoreProgramInfo updateCoreProgram(String coreProgramId, String coreProgramTypeKey, CoreProgramInfo coreProgramInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().updateCoreProgram(coreProgramId, coreProgramTypeKey, coreProgramInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCoreProgram(String coreProgramId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteCoreProgram(coreProgramId, contextInfo);
    }

    @Override
    public ProgramRequirementInfo getProgramRequirement(String programRequirementId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getProgramRequirement(programRequirementId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateProgramRequirement(String validationType, ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().validateProgramRequirement(validationType, programRequirementInfo, contextInfo);
    }

    @Override
    public ProgramRequirementInfo createProgramRequirement(String programRequirementTypeKey, ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().createProgramRequirement(programRequirementTypeKey, programRequirementInfo, contextInfo);
    }

    @Override
    public ProgramRequirementInfo updateProgramRequirement(String programRequirementId, String programRequirementTypeKey, ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().updateProgramRequirement(programRequirementId, programRequirementTypeKey, programRequirementInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteProgramRequirement(String programRequirementId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteProgramRequirement(programRequirementId, contextInfo);
    }

    @Override
    public StatusInfo setCurrentMajorDisciplineVersion(String majorDisciplineId, Date currentVersionStart, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return this.getNextDecorator().setCurrentMajorDisciplineVersion(majorDisciplineId, currentVersionStart, contextInfo);
    }

    @Override
    public MinorDisciplineInfo getMinorDiscipline(String minorDisciplineId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getMinorDiscipline(minorDisciplineId, contextInfo);
    }

    @Override
    public List<String> getMinorsByCredentialProgramType(String programType, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().getMinorsByCredentialProgramType(programType, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateMinorDiscipline(String validationType, MinorDisciplineInfo minorDisciplineInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().validateMinorDiscipline(validationType, minorDisciplineInfo, contextInfo);
    }

    @Override
    public MinorDisciplineInfo createMinorDiscipline(String minorDisciplineTypeKey, MinorDisciplineInfo minorDisciplineInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().createMinorDiscipline(minorDisciplineTypeKey, minorDisciplineInfo, contextInfo);
    }

    @Override
    public MinorDisciplineInfo updateMinorDiscipline(String minorDisciplineId, String minorDisciplineTypeKey, MinorDisciplineInfo minorDisciplineInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().updateMinorDiscipline(minorDisciplineId, minorDisciplineTypeKey, minorDisciplineInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteMinorDiscipline(String minorDisciplineId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteMinorDiscipline(minorDisciplineId, contextInfo);
    }

    @Override
    public List<MajorDisciplineInfo> getMajorDisciplinesByIds(List<String> majorDisciplineIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HonorsProgramInfo> getHonorsProgramsByIds(List<String> honorsProgramIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getHonorsProgramsByIds(honorsProgramIds, contextInfo);
    }

    @Override
    public List<CoreProgramInfo> getCoreProgramsByIds(List<String> coreProgramIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCoreProgramsByIds(coreProgramIds, contextInfo);
    }

    @Override
    public List<ProgramRequirementInfo> getProgramRequirementsByIds(List<String> programRequirementIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getProgramRequirementsByIds(programRequirementIds, contextInfo);
    }

    @Override
    public List<ProgramVariationInfo> getProgramVariationsByMajorDiscipline(String majorDisciplineId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getProgramVariationsByMajorDiscipline(majorDisciplineId, contextInfo);
    }

    @Override
    @Deprecated
    public VersionDisplayInfo getCurrentVersion(String programNamespaceMajorDisciplineUri, String majorVersionIndId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCurrentVersion(programNamespaceMajorDisciplineUri, majorVersionIndId, contextInfo);
    }

    @Override
    @Deprecated
    public List<VersionDisplayInfo> getVersions(String programNamespaceMajorDisciplineUri, String versionIndId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getVersions(programNamespaceMajorDisciplineUri, versionIndId, contextInfo);
    }

    @Override
    @Deprecated
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return this.getNextDecorator().getSearchTypes(contextInfo);
    }

    @Override
    @Deprecated
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    @Deprecated
    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().getSearchTypesByResult(searchResultTypeKey, contextInfo);
    }

    @Override
    @Deprecated
    public List<TypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().getSearchTypesByCriteria(searchCriteriaTypeKey, contextInfo);
    }

    @Override
    @Deprecated
    public List<TypeInfo> getSearchResultTypes(ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return this.getNextDecorator().getSearchResultTypes(contextInfo);
    }

    @Override
    @Deprecated
    public List<TypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return this.getNextDecorator().getSearchCriteriaTypes(contextInfo);
    }

    @Override
    @Deprecated
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException {

        SearchResultInfo sr = null;
        try {
            sr = this.getNextDecorator().search(searchRequestInfo, contextInfo);
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }

        return sr;
    }

    @Override
    @Deprecated
    public VersionDisplayInfo getFirstVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getFirstVersion(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    @Deprecated
    public VersionDisplayInfo getLatestVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getLatestVersion(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    @Deprecated
    public VersionDisplayInfo getVersionBySequenceNumber(String refObjectUri, String refObjectId, Long sequence, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getVersionBySequenceNumber(refObjectUri, refObjectId, sequence, contextInfo);
    }

    @Override
    @Deprecated
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectUri, String refObjectId, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCurrentVersionOnDate(refObjectUri, refObjectId, date, contextInfo);
    }

    @Override
    @Deprecated
    public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectUri, String refObjectId, Date from, Date to, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getVersionsInDateRange(refObjectUri, refObjectId, from, to, contextInfo);
    }

    @Override
    @Deprecated
    public List<String> getObjectTypes() {
        List<String> ls = null;
        try {
            ls = this.getNextDecorator().getObjectTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return ls;
    }

    @Override
    @Deprecated
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        ObjectStructureDefinition osd = null;
        try {
            return this.getNextDecorator().getObjectStructure(objectTypeKey);
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return osd;
    }

    @Override
    @Deprecated
    public List<ProgramVariationInfo> getVariationsByMajorDisciplineId(String majorDisciplineId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().getVariationsByMajorDisciplineId(majorDisciplineId, contextInfo);
    }

}
