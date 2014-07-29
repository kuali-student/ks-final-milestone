/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.program.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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
import org.kuali.student.r2.lum.program.dto.TrackInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

import javax.jws.WebParam;


public class ProgramServiceMapImpl implements MockService, ProgramService
{
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, CredentialProgramInfo> credentialProgramMap = new LinkedHashMap<String, CredentialProgramInfo>();
    private Map<String, MajorDisciplineInfo> majorDisciplineMap = new LinkedHashMap<String, MajorDisciplineInfo>();
    private Map<String, HonorsProgramInfo> honorsProgramMap = new LinkedHashMap<String, HonorsProgramInfo>();
    private Map<String, CoreProgramInfo> coreProgramMap = new LinkedHashMap<String, CoreProgramInfo>();
    private Map<String, ProgramRequirementInfo> programRequirementMap = new LinkedHashMap<String, ProgramRequirementInfo>();
    private Map<String, MinorDisciplineInfo> minorDisciplineMap = new LinkedHashMap<String, MinorDisciplineInfo>();
    private Map<String, TrackInfo> trackMap = new LinkedHashMap<String, TrackInfo>();

    @Override
    public void clear()
    {
        this.credentialProgramMap.clear ();
        this.majorDisciplineMap.clear ();
        this.honorsProgramMap.clear ();
        this.coreProgramMap.clear ();
        this.programRequirementMap.clear ();
        this.minorDisciplineMap.clear ();
        this.trackMap.clear ();
    }


    @Override
    public CredentialProgramInfo getCredentialProgram(String credentialProgramId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        if (!this.credentialProgramMap.containsKey(credentialProgramId)) {
            throw new DoesNotExistException(credentialProgramId);
        }
        return new CredentialProgramInfo(this.credentialProgramMap.get (credentialProgramId));
    }

    @Override
    public List<String> getCredentialProgramIdsByType(String credentialProgramTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String> ();
        for (CredentialProgramInfo info: credentialProgramMap.values ()) {
            if (credentialProgramTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<CredentialProgramInfo> getCredentialProgramsByIds(List<String> credentialProgramIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<CredentialProgramInfo> list = new ArrayList<CredentialProgramInfo> ();
        for (String id: credentialProgramIds) {
            list.add (this.getCredentialProgram(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateCredentialProgram(String validationType, CredentialProgramInfo credentialProgramInfo, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public CredentialProgramInfo createCredentialProgram(String credentialProgramTypeKey, CredentialProgramInfo credentialProgramInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // CREATE
        if (!credentialProgramTypeKey.equals (credentialProgramInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        CredentialProgramInfo copy = new CredentialProgramInfo(credentialProgramInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        credentialProgramMap.put(copy.getId(), copy);
        return new CredentialProgramInfo(copy);
    }

    @Override
    public CredentialProgramInfo createNewCredentialProgramVersion(String credentialProgramId, String versionComment, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,VersionMismatchException
            ,DataValidationErrorException
            ,ReadOnlyException
    {
        // CREATE
        CredentialProgramInfo credentialProgramInfo = credentialProgramMap.get(credentialProgramId);
        CredentialProgramInfo copy = new CredentialProgramInfo(credentialProgramInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        credentialProgramMap.put(copy.getId(), copy);
        return new CredentialProgramInfo(copy);
    }

    @Override
    public StatusInfo setCurrentCredentialProgramVersion(String credentialProgramId, Date currentVersionStart, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,IllegalVersionSequencingException
            ,OperationFailedException
            ,PermissionDeniedException
            ,DataValidationErrorException
    {
        // UNKNOWN
        throw new OperationFailedException ("setCurrentCredentialProgramVersion has not been implemented");
    }

    @Override
    public CredentialProgramInfo updateCredentialProgram(String credentialProgramId, CredentialProgramInfo credentialProgramInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,VersionMismatchException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UPDATE
        if (!credentialProgramId.equals (credentialProgramInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        CredentialProgramInfo copy = new CredentialProgramInfo(credentialProgramInfo);
        CredentialProgramInfo old = this.getCredentialProgram(credentialProgramInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.credentialProgramMap .put(credentialProgramInfo.getId(), copy);
        return new CredentialProgramInfo(copy);
    }

    @Override
    public StatusInfo deleteCredentialProgram(String credentialProgramId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.credentialProgramMap.remove(credentialProgramId) == null) {
            throw new OperationFailedException(credentialProgramId);
        }
        return newStatus();
    }

    @Override
    public List<String> searchForCredentialProgramIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForCredentialProgramIds has not been implemented");
    }

    @Override
    public List<CredentialProgramInfo> searchForCredentialPrograms(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForCredentialPrograms has not been implemented");
    }

    @Override
    public MajorDisciplineInfo getMajorDiscipline(String majorDisciplineId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        if (!this.majorDisciplineMap.containsKey(majorDisciplineId)) {
            throw new DoesNotExistException(majorDisciplineId);
        }
        return new MajorDisciplineInfo(this.majorDisciplineMap.get (majorDisciplineId));
    }

    @Override
    public List<String> getMajorDisciplineIdsByType(String majorDisciplineTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String> ();
        for (MajorDisciplineInfo info: majorDisciplineMap.values ()) {
            if (majorDisciplineTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<MajorDisciplineInfo> getMajorDisciplinesByIds(List<String> majorDisciplineIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<MajorDisciplineInfo> list = new ArrayList<MajorDisciplineInfo> ();
        for (String id: majorDisciplineIds) {
            list.add (this.getMajorDiscipline(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getMajorDisciplineIdsByCredentialProgramType(String programType, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // GET_IDS_BY_OTHER
        List<String> list = new ArrayList<String> ();
        for (MajorDisciplineInfo info: majorDisciplineMap.values ()) {
            CredentialProgramInfo credentialProgramInfo = credentialProgramMap.get(info.getCredentialProgramId());
            if (programType.equals(credentialProgramInfo.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<ProgramVariationInfo> getProgramVariationsByMajorDiscipline(String majorDisciplineId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // GET_INFOS_BY_OTHER
        List<ProgramVariationInfo> list = new ArrayList<ProgramVariationInfo> ();
        for (MajorDisciplineInfo info: majorDisciplineMap.values ()) {
            if (majorDisciplineId.equals(info.getId())) {
                list.add (new ProgramVariationInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateMajorDiscipline(String validationType, MajorDisciplineInfo majorDisciplineInfo, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public MajorDisciplineInfo createMajorDiscipline(String majorDisciplineTypeKey, MajorDisciplineInfo majorDisciplineInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // CREATE
        if (!majorDisciplineTypeKey.equals (majorDisciplineInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        MajorDisciplineInfo copy = new MajorDisciplineInfo(majorDisciplineInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        majorDisciplineMap.put(copy.getId(), copy);
        return new MajorDisciplineInfo(copy);
    }

    @Override
    public MajorDisciplineInfo updateMajorDiscipline(String majorDisciplineId, MajorDisciplineInfo majorDisciplineInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,VersionMismatchException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UPDATE
        if (!majorDisciplineId.equals (majorDisciplineInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        MajorDisciplineInfo copy = new MajorDisciplineInfo(majorDisciplineInfo);
        MajorDisciplineInfo old = this.getMajorDiscipline(majorDisciplineInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.majorDisciplineMap .put(majorDisciplineInfo.getId(), copy);
        return new MajorDisciplineInfo(copy);
    }

    @Override
    public StatusInfo deleteMajorDiscipline(String majorDisciplineId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.majorDisciplineMap.remove(majorDisciplineId) == null) {
            throw new OperationFailedException(majorDisciplineId);
        }
        return newStatus();
    }

    @Override
    public MajorDisciplineInfo createNewMajorDisciplineVersion(String majorDisciplineId, String versionComment, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,VersionMismatchException
            ,DataValidationErrorException
            ,ReadOnlyException
    {
        // CREATE
        MajorDisciplineInfo majorDisciplineInfo = majorDisciplineMap.get(majorDisciplineId);
        MajorDisciplineInfo copy = new MajorDisciplineInfo(majorDisciplineInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        majorDisciplineMap.put(copy.getId(), copy);
        return new MajorDisciplineInfo(copy);
    }

    @Override
    public List<String> searchForMajorDisciplineIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForMajorDisciplineIds has not been implemented");
    }

    @Override
    public List<MajorDisciplineInfo> searchForMajorDisciplines(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForMajorDisciplines has not been implemented");
    }

    @Override
    public HonorsProgramInfo getHonorsProgram(String honorsProgramId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        if (!this.honorsProgramMap.containsKey(honorsProgramId)) {
            throw new DoesNotExistException(honorsProgramId);
        }
        return new HonorsProgramInfo(this.honorsProgramMap.get (honorsProgramId));
    }

    @Override
    public List<String> getHonorsProgramIdsByType(String honorsProgramTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String> ();
        for (HonorsProgramInfo info: honorsProgramMap.values ()) {
            if (honorsProgramTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<HonorsProgramInfo> getHonorsProgramsByIds(List<String> honorsProgramIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<HonorsProgramInfo> list = new ArrayList<HonorsProgramInfo> ();
        for (String id: honorsProgramIds) {
            list.add (this.getHonorsProgram(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getHonorProgramIdsByCredentialProgramType(String programType, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // GET_IDS_BY_OTHER
        List<String> list = new ArrayList<String> ();
        for (HonorsProgramInfo info: honorsProgramMap.values ()) {
            CredentialProgramInfo credentialProgramInfo = credentialProgramMap.get(info.getCredentialProgramId());
            if (programType.equals(credentialProgramInfo.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateHonorsProgram(String validationType, HonorsProgramInfo honorsProgramInfo, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public HonorsProgramInfo createHonorsProgram(String honorsProgramTypeKey, HonorsProgramInfo honorsProgramInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // CREATE
        if (!honorsProgramTypeKey.equals (honorsProgramInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        HonorsProgramInfo copy = new HonorsProgramInfo(honorsProgramInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        honorsProgramMap.put(copy.getId(), copy);
        return new HonorsProgramInfo(copy);
    }

    @Override
    public HonorsProgramInfo updateHonorsProgram(String honorsProgramId, String honorsProgramTypeKey, HonorsProgramInfo honorsProgramInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,VersionMismatchException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UPDATE
        if (!honorsProgramId.equals (honorsProgramInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        HonorsProgramInfo copy = new HonorsProgramInfo(honorsProgramInfo);
        HonorsProgramInfo old = this.getHonorsProgram(honorsProgramInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.honorsProgramMap .put(honorsProgramInfo.getId(), copy);
        return new HonorsProgramInfo(copy);
    }

    @Override
    public StatusInfo deleteHonorsProgram(String honorsProgramId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.honorsProgramMap.remove(honorsProgramId) == null) {
            throw new OperationFailedException(honorsProgramId);
        }
        return newStatus();
    }

    @Override
    public List<String> searchForHonorsProgramIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForHonorsProgramIds has not been implemented");
    }

    @Override
    public List<HonorsProgramInfo> searchForHonorsPrograms(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForHonorsPrograms has not been implemented");
    }

    @Override
    public CoreProgramInfo getCoreProgram(String coreProgramId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        if (!this.coreProgramMap.containsKey(coreProgramId)) {
            throw new DoesNotExistException(coreProgramId);
        }
        return new CoreProgramInfo(this.coreProgramMap.get (coreProgramId));
    }

    @Override
    public List<String> getCoreProgramIdsByType(String coreProgramTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String> ();
        for (CoreProgramInfo info: coreProgramMap.values ()) {
            if (coreProgramTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<CoreProgramInfo> getCoreProgramsByIds(List<String> coreProgramIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<CoreProgramInfo> list = new ArrayList<CoreProgramInfo> ();
        for (String id: coreProgramIds) {
            list.add (this.getCoreProgram(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateCoreProgram(String validationType, CoreProgramInfo coreProgramInfo, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public CoreProgramInfo createCoreProgram(String coreProgramTypeKey, CoreProgramInfo coreProgramInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,DoesNotExistException
    {
        // CREATE
        if (!coreProgramTypeKey.equals (coreProgramInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        CoreProgramInfo copy = new CoreProgramInfo(coreProgramInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        coreProgramMap.put(copy.getId(), copy);
        return new CoreProgramInfo(copy);
    }

    @Override
    public CoreProgramInfo createNewCoreProgramVersion(String coreProgramId, String versionComment, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,VersionMismatchException
            ,DataValidationErrorException
            ,ReadOnlyException
    {
        // CREATE
        CoreProgramInfo coreProgramInfo = coreProgramMap.get(coreProgramId);
        CoreProgramInfo copy = new CoreProgramInfo(coreProgramInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        coreProgramMap.put(copy.getId(), copy);
        return new CoreProgramInfo(copy);
    }

    @Override
    public StatusInfo setCurrentCoreProgramVersion(String coreProgramId, Date currentVersionStart, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,IllegalVersionSequencingException
            ,OperationFailedException
            ,PermissionDeniedException
            ,DataValidationErrorException
    {
        // UNKNOWN
        throw new OperationFailedException ("setCurrentCoreProgramVersion has not been implemented");
    }

    @Override
    public CoreProgramInfo updateCoreProgram(String coreProgramId, String coreProgramTypeKey, CoreProgramInfo coreProgramInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,VersionMismatchException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UPDATE
        if (!coreProgramId.equals (coreProgramInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        CoreProgramInfo copy = new CoreProgramInfo(coreProgramInfo);
        CoreProgramInfo old = this.getCoreProgram(coreProgramInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.coreProgramMap .put(coreProgramInfo.getId(), copy);
        return new CoreProgramInfo(copy);
    }

    @Override
    public StatusInfo deleteCoreProgram(String coreProgramId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.coreProgramMap.remove(coreProgramId) == null) {
            throw new OperationFailedException(coreProgramId);
        }
        return newStatus();
    }

    @Override
    public List<String> searchForCoreProgramIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForCoreProgramIds has not been implemented");
    }

    @Override
    public List<CoreProgramInfo> searchForCorePrograms(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForCorePrograms has not been implemented");
    }

    @Override
    public ProgramRequirementInfo getProgramRequirement(String programRequirementId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        if (!this.programRequirementMap.containsKey(programRequirementId)) {
            throw new DoesNotExistException(programRequirementId);
        }
        return new ProgramRequirementInfo(this.programRequirementMap.get (programRequirementId));
    }

    @Override
    public List<String> getProgramRequirementIdsByType(String programRequirementTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String> ();
        for (ProgramRequirementInfo info: programRequirementMap.values ()) {
            if (programRequirementTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<ProgramRequirementInfo> getProgramRequirementsByIds(List<String> programRequirementIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<ProgramRequirementInfo> list = new ArrayList<ProgramRequirementInfo> ();
        for (String id: programRequirementIds) {
            list.add (this.getProgramRequirement(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateProgramRequirement(String validationType, ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public ProgramRequirementInfo createProgramRequirement(String programRequirementTypeKey, ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // CREATE
        if (!programRequirementTypeKey.equals (programRequirementInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        ProgramRequirementInfo copy = new ProgramRequirementInfo(programRequirementInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        programRequirementMap.put(copy.getId(), copy);
        return new ProgramRequirementInfo(copy);
    }

    @Override
    public ProgramRequirementInfo updateProgramRequirement(String programRequirementId, String programRequirementTypeKey, ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,VersionMismatchException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UPDATE
        if (!programRequirementId.equals (programRequirementInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        ProgramRequirementInfo copy = new ProgramRequirementInfo(programRequirementInfo);
        ProgramRequirementInfo old = this.getProgramRequirement(programRequirementInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.programRequirementMap .put(programRequirementInfo.getId(), copy);
        return new ProgramRequirementInfo(copy);
    }

    @Override
    public StatusInfo deleteProgramRequirement(String programRequirementId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.programRequirementMap.remove(programRequirementId) == null) {
            throw new OperationFailedException(programRequirementId);
        }
        return newStatus();
    }

    @Override
    public List<String> searchForProgramRequirementIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForProgramRequirementIds has not been implemented");
    }

    @Override
    public List<ProgramRequirementInfo> searchForProgramRequirements(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForProgramRequirements has not been implemented");
    }

    @Override
    public StatusInfo setCurrentMajorDisciplineVersion(String majorDisciplineId, Date currentVersionStart, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,IllegalVersionSequencingException
            ,OperationFailedException
            ,PermissionDeniedException
            ,DataValidationErrorException
    {
        // UNKNOWN
        throw new OperationFailedException ("setCurrentMajorDisciplineVersion has not been implemented");
    }

    @Override
    public MinorDisciplineInfo getMinorDiscipline(String minorDisciplineId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        if (!this.minorDisciplineMap.containsKey(minorDisciplineId)) {
            throw new DoesNotExistException(minorDisciplineId);
        }
        return new MinorDisciplineInfo(this.minorDisciplineMap.get (minorDisciplineId));
    }

    @Override
    public List<MinorDisciplineInfo> getMinorDisciplinesByIds(List<String> minorDisciplineIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<MinorDisciplineInfo> list = new ArrayList<MinorDisciplineInfo> ();
        for (String id: minorDisciplineIds) {
            list.add (this.getMinorDiscipline(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getMinorDisciplineIdsByType(String minorDisciplineTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String> ();
        for (MinorDisciplineInfo info: minorDisciplineMap.values ()) {
            if (minorDisciplineTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<String> getMinorsByCredentialProgramType(String programType, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // GET_IDS_BY_OTHER
        List<String> list = new ArrayList<String> ();
        for (MinorDisciplineInfo info: minorDisciplineMap.values ()) {
            CredentialProgramInfo credentialProgramInfo = credentialProgramMap.get(info.getCredentialProgramId());
            if (programType.equals(credentialProgramInfo.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateMinorDiscipline(String validationType, MinorDisciplineInfo minorDisciplineInfo, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public MinorDisciplineInfo createMinorDiscipline(String minorDisciplineTypeKey, MinorDisciplineInfo minorDisciplineInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // CREATE
        if (!minorDisciplineTypeKey.equals (minorDisciplineInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        MinorDisciplineInfo copy = new MinorDisciplineInfo(minorDisciplineInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        minorDisciplineMap.put(copy.getId(), copy);
        return new MinorDisciplineInfo(copy);
    }

    @Override
    public MinorDisciplineInfo updateMinorDiscipline(String minorDisciplineId, String minorDisciplineTypeKey, MinorDisciplineInfo minorDisciplineInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,VersionMismatchException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UPDATE
        if (!minorDisciplineId.equals (minorDisciplineInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        MinorDisciplineInfo copy = new MinorDisciplineInfo(minorDisciplineInfo);
        MinorDisciplineInfo old = this.getMinorDiscipline(minorDisciplineInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.minorDisciplineMap .put(minorDisciplineInfo.getId(), copy);
        return new MinorDisciplineInfo(copy);
    }

    @Override
    public StatusInfo deleteMinorDiscipline(String minorDisciplineId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.minorDisciplineMap.remove(minorDisciplineId) == null) {
            throw new OperationFailedException(minorDisciplineId);
        }
        return newStatus();
    }

    @Override
    public MinorDisciplineInfo createNewMinorDisciplineVersion(String minorDisciplineId, String versionComment, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,VersionMismatchException
            ,DataValidationErrorException
            ,ReadOnlyException
    {
        // CREATE
        MinorDisciplineInfo minorDisciplineInfo = minorDisciplineMap.get(minorDisciplineId);
        MinorDisciplineInfo copy = new MinorDisciplineInfo(minorDisciplineInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        minorDisciplineMap.put(copy.getId(), copy);
        return new MinorDisciplineInfo(copy);
    }

    @Override
    public List<ProgramVariationInfo> getVariationsByMajorDisciplineId(String majorDisciplineId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
    {
        // GET_INFOS_BY_OTHER
        List<ProgramVariationInfo> list = new ArrayList<ProgramVariationInfo> ();
        MajorDisciplineInfo majorDisciplineInfo = majorDisciplineMap.get(majorDisciplineId);
        List<ProgramVariationInfo> variations = majorDisciplineInfo.getVariations();

        for(ProgramVariationInfo variation : variations) {
            ProgramVariationInfo copy = new ProgramVariationInfo(variation);
            list.add(copy);
        }

        return list;
    }

    @Override
    public List<String> searchForMinorDisciplineIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForMinorDisciplineIds has not been implemented");
    }

    @Override
    public List<MinorDisciplineInfo> searchForMinorDisciplines(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForMinorDisciplines has not been implemented");
    }

    @Override
    public TrackInfo getTrack(String trackId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        if (!this.trackMap.containsKey(trackId)) {
            throw new DoesNotExistException(trackId);
        }
        return new TrackInfo(this.trackMap.get (trackId));
    }

    @Override
    public List<TrackInfo> getTracksByIds(List<String> trackIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<TrackInfo> list = new ArrayList<TrackInfo> ();
        for (String id: trackIds) {
            list.add (this.getTrack(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getTrackIdsByType(String trackTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String> ();
        for (TrackInfo info: trackMap.values ()) {
            if (trackTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<TrackInfo> getTracksByMinor(String minorDisciplineId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_INFOS_BY_OTHER
        List<TrackInfo> list = new ArrayList<TrackInfo> ();
        for (TrackInfo info: trackMap.values ()) {
            if (minorDisciplineId.equals(info.getMinorDisciplineId())) {
                list.add (new TrackInfo(info));
            }
        }
        return list;
    }

    @Override
    public TrackInfo createTrack(String minorDisciplineId, String trackTypeKey, TrackInfo trackInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // CREATE
        if (!trackTypeKey.equals (trackInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        TrackInfo copy = new TrackInfo(trackInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        trackMap.put(copy.getId(), copy);
        return new TrackInfo(copy);
    }

    @Override
    public TrackInfo updateTrack(String trackId, String trackTypeKey, TrackInfo trackInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,VersionMismatchException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UPDATE
        if (!trackId.equals (trackInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        TrackInfo copy = new TrackInfo(trackInfo);
        TrackInfo old = this.getTrack(trackInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.trackMap .put(trackInfo.getId(), copy);
        return new TrackInfo(copy);
    }

    @Override
    public StatusInfo deleteTrack(String trackId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.trackMap.remove(trackId) == null) {
            throw new OperationFailedException(trackId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateTrack(String minorDisciplineId, String validationType, TrackInfo trackInfo, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public List<String> searchForTrackIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForTrackIds has not been implemented");
    }

    @Override
    public List<TrackInfo> searchForTracks(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForTracks has not been implemented");
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
    @Override
    public List<String> getObjectTypes() {
        return null;
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return null;
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<VersionDisplayInfo> getVersions(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public VersionDisplayInfo getFirstVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public VersionDisplayInfo getLatestVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(String refObjectUri, String refObjectId, Long sequence, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectUri, String refObjectId, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectUri, String refObjectId, Date from, Date to, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

}

